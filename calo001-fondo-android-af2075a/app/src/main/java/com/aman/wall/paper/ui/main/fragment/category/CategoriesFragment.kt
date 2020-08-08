package com.aman.wall.paper.ui.main.fragment.category


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aman.wall.paper.R
import com.aman.wall.paper.adapter.CategoryAdapter
import com.aman.wall.paper.adapter.CategoryAdapter.OnCategoryInteraction
import com.aman.wall.paper.model.Category
import com.aman.wall.paper.repository.CategoriesRepositoryProvider
import kotlinx.android.synthetic.main.fragment_categories.*

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CategoriesFragment : Fragment(), OnCategoryInteraction {
    private var listener: OnCategoryListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val manager = GridLayoutManager(activity, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return manager.spanCount
                } else {
                    return 1
                }
            }

        }

        rvCategories.layoutManager = manager
        rvCategories.adapter = CategoryAdapter(
            CategoriesRepositoryProvider.provideCategoriesRepository(),
            this,
            context
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategoryListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCategoryListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCategoryInteraction(category: Category) {
        listener?.onCategoryClick(category)
    }

    fun scrollToUp(){
        rvCategories.smoothScrollToPosition(0)
    }

    interface OnCategoryListener {
        fun onCategoryClick(category: Category)
    }

    companion object {
        const val TAG = "CategoriesFragment"

        @JvmStatic
        fun newInstance() =
            CategoriesFragment()
    }
}
