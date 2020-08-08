package com.aman.wall.paper.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.aman.wall.paper.R
import com.aman.wall.paper.base.BasePhotoFragment.OnFragmentInteractionListener
import com.aman.wall.paper.model.Category
import com.aman.wall.paper.network.ApiError
import com.aman.wall.paper.ui.dialog.search.SearchDialogFragment
import com.aman.wall.paper.ui.dialog.search.SearchDialogFragment.OnSearchListener
import com.aman.wall.paper.ui.main.fragment.category.CategoriesFragment
import com.aman.wall.paper.ui.main.fragment.category.CategoriesFragment.OnCategoryListener
import com.aman.wall.paper.ui.main.fragment.error.ErrorFragment
import com.aman.wall.paper.ui.main.fragment.history.HistoryFragment
import com.aman.wall.paper.ui.main.fragment.search.SearchFragment
import com.aman.wall.paper.ui.main.fragment.today.TodayFragment
import com.aman.wall.paper.util.Adsbuilder
import com.aman.wall.paper.util.makeStatusBarTransparent
import com.aman.wall.paper.util.setMarginTop
import com.facebook.ads.AdView
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSearchListener, OnCategoryListener, OnFragmentInteractionListener {
    private val mainFragment: TodayFragment =               TodayFragment.newInstance()
    private val categoriesFragment: CategoriesFragment =    CategoriesFragment.newInstance()
    private val searchFragment: SearchFragment =            SearchFragment.newInstance()
    private val historyFragment: HistoryFragment =          HistoryFragment.newInstance()
    private val errorFragment: ErrorFragment =              ErrorFragment.newInstance()
    lateinit var mAdView : AdView
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var adsbuilder:Adsbuilder

    private lateinit var activeFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupStatusbar()
        setupFBAds()
        setSupportActionBar(toolbar)
        setupToolbar()
        setupFragments()
        setupButtonNavigation()
    }

    private fun setupFBAds() { // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this)
        // Find the Ad Container
        val adContainer =
            findViewById<View>(R.id.banner_container) as LinearLayout
        adsbuilder = Adsbuilder(this, adContainer)
        adsbuilder.buildAdsListner()
        adsbuilder.loadAds()
        adsbuilder.loadBannerAds()
    }
    override fun onDestroy() {
        adsbuilder.destroyInterstialAds()
        adsbuilder.destroyBannerAds()
        super.onDestroy()
    }
    private fun setupStatusbar() {
        val color = ContextCompat.getColor(this, R.color.uiTransparent)
        makeStatusBarTransparent(color)
        ViewCompat.setOnApplyWindowInsetsListener(mainContainer) { _, insets ->
            mainCardView.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }

    private fun setupToolbar() {
        supportActionBar?.title = null
        toolbar.setOnClickListener {
            val dialog = SearchDialogFragment()
            val ft = supportFragmentManager.beginTransaction()
            dialog.show(ft, SearchDialogFragment.TAG)
        }
    }
    private fun setupFragments() {
        with(supportFragmentManager) {
            val ft = beginTransaction()

            if (supportFragmentManager.findFragmentByTag(TodayFragment.TAG) == null) {
                ft.add(R.id.mainFragment, mainFragment, TodayFragment.TAG)
            }

            if (supportFragmentManager.findFragmentByTag(SearchFragment.TAG) == null) {
                ft.add(R.id.mainFragment, searchFragment, SearchFragment.TAG)
                ft.hide(searchFragment)
            }

            if(supportFragmentManager.findFragmentByTag(CategoriesFragment.TAG) == null) {
                ft.add(R.id.mainFragment, categoriesFragment, CategoriesFragment.TAG)
                ft.hide(categoriesFragment)
            }

            if(supportFragmentManager.findFragmentByTag(HistoryFragment.TAG) == null) {
                ft.add(R.id.mainFragment, historyFragment, HistoryFragment.TAG)
                ft.hide(historyFragment)
            }

            if(supportFragmentManager.findFragmentByTag(ErrorFragment.TAG) == null) {
                ft.add(R.id.mainFragment, errorFragment, ErrorFragment.TAG)
                ft.hide(errorFragment)
            }

            activeFragment = mainFragment
            ft.commit()
        }
    }

    private fun setupButtonNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navbar_home -> {
                    adsbuilder.showAds()
                    if(activeFragment == mainFragment) {
                        mainFragment.scrollToUp()
                    } else {
                        supportFragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(mainFragment)
                            .commit()
                        activeFragment = mainFragment
                    }
                }
                R.id.navbar_categories -> {
                    adsbuilder.showAds()
                    if (activeFragment == categoriesFragment) {
                        categoriesFragment.scrollToUp()
                    } else {
                        supportFragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(categoriesFragment)
                            .commit()
                        activeFragment = categoriesFragment
                    }
                }
                R.id.navbar_history -> {
                    adsbuilder.showAds()
                    if (activeFragment == historyFragment) {
                        historyFragment.scrollToUp()
                    } else {
                        supportFragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(historyFragment)
                            .commit()
                        activeFragment = historyFragment
                        historyFragment.reloadHistory()
                    }
                }

            }
            true
        }
    }

    override fun onSearch(term: String) {
        supportFragmentManager
            .beginTransaction()
            .hide(activeFragment)
            .show(searchFragment)
            .commit()
        searchFragment.newSearchQuery(term)
        activeFragment = searchFragment
    }

    override fun onCategoryClick(category: Category) {
        supportFragmentManager
            .beginTransaction()
            .hide(activeFragment)
            .show(searchFragment)
            .commit()
        searchFragment.newSearchQuery(category.query)
        activeFragment = searchFragment
    }

    override fun onFragmentDataError(error: ApiError) {
        supportFragmentManager
            .beginTransaction()
            .hide(activeFragment)
            .show(errorFragment)
            .commit()
        errorFragment.setErrorMessage(error)
        activeFragment = errorFragment
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}
