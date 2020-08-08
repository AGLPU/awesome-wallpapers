package com.aman.wall.paper.ui.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.aman.wall.paper.R
import com.aman.wall.paper.config.Constants.PERMISSION_REQUEST_CODE
import com.aman.wall.paper.manager.history.HistoryManager
import com.aman.wall.paper.model.Photo
import com.aman.wall.paper.network.ApiError
import com.aman.wall.paper.service.NotificationService
import com.aman.wall.paper.ui.dialog.DetailUserFragment
import com.aman.wall.paper.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.ads.AdView
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_photo_detail.*
import kotlinx.android.synthetic.main.progress_layout.*

class PhotoDetailActivity : AppCompatActivity(), OnSetAsWallpaperListener,
    PhotoDetailViewContract {
    private lateinit var detailFragment: DetailUserFragment
    private lateinit var mCurrentPhoto: Photo
    private lateinit var mDownloadLink: String
    private val presenter = PhotoDetailPresenterImpl(this)
    private val historyManager = HistoryManager()
    lateinit var mAdView : AdView
    lateinit var adsbuilder:Adsbuilder
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        setupFBAds()
        setSupportActionBar(toolbarDetail)
        setupStatusbar()
        getExtraInfo()
        setupFragment()
        setupEvents()
        showImage()
    }

    private fun setupStatusbar() {
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val color = ContextCompat.getColor(this, android.R.color.transparent)
        makeStatusBarTransparent(color)
        ViewCompat.setOnApplyWindowInsetsListener(detailContainer) { _, insets ->
            toolbarDetail.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
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
    private fun setupEvents() {
        fabWallpaper.setOnClickListener {
            presenter.getDownloadLink(mCurrentPhoto.id)
            historyManager.addToHistory(mCurrentPhoto)
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun startImageDownload() {
        adsbuilder.showAds()
        val intent = Intent(this, NotificationService::class.java)
        intent.putExtra(NotificationService.URL_EXTRA, mDownloadLink)
        intent.putExtra(NotificationService.ID_PHOTO_EXTRA, mCurrentPhoto.id)
        startService(intent)
    }


    override fun onDestroy() {
        adsbuilder.destroyInterstialAds()
        adsbuilder.destroyBannerAds()
        super.onDestroy()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startImageDownload()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showImage() {
        Glide.with(this)
            .load(mCurrentPhoto.urls.regular)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    finish()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress?.visibility = View.GONE
                    return false
                }
            })
            .thumbnail(0.01f)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(bigImageView)
    }

    private fun getExtraInfo() {
        mCurrentPhoto = intent?.extras?.getSerializable(EXTRA_OBJECT) as Photo
    }

    private fun setupFragment() {
        detailFragment = DetailUserFragment( mCurrentPhoto)
    }

    override fun onSetWallpaper() {
       adsbuilder.showAds()
        presenter.getDownloadLink(mCurrentPhoto.id)
    }

    override fun onSuccess(image: String) {
        mDownloadLink = image
        if (checkPermission()) {
            startImageDownload()
            fabWallpaper.hideWithAnimation(R.anim.collapse)
            bigImageView.showWithAnimation()
        } else {
            requestPermission()
        }
    }

    override fun onError(error: ApiError) {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.showMore -> {
                detailFragment.show(supportFragmentManager, detailFragment.tag)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_OBJECT = "PhotoObject"
    }
}

interface OnSetAsWallpaperListener {
    fun onSetWallpaper()
}