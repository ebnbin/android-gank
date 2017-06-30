package com.ebnbin.gank.feature.category

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ebnbin.eb.util.EBUtil
import com.ebnbin.ebapplication.context.EBActionBarFragment
import com.ebnbin.ebapplication.context.EBFragment
import com.ebnbin.ebapplication.net.NetModelCallback
import com.ebnbin.gank.R
import com.ebnbin.gank.feature.data.*
import com.ebnbin.gank.feature.days.day.DayModel
import okhttp3.Call
import okhttp3.Response

class CategoryItemFragment : EBFragment() {
    private val category: Category by lazy {
        arguments.getSerializable(ARG_CATEGORY) as Category
    }

    override fun overrideContentViewLayout(): Int {
        return R.layout.category_item_fragment
    }

    private val listRecyclerView: RecyclerView by lazy {
        stateView.findViewById(R.id.list) as RecyclerView
    }

    private val layoutManager: DataLayoutManager by lazy {
        DataLayoutManager(context)
    }

    private val adapter: DataAdapter by lazy {
        DataAdapter()
    }

    private val itemDecoration: DataItemDecoration by lazy {
        DataItemDecoration(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listRecyclerView.layoutManager = layoutManager
        adapter.listeners.add(object : DataAdapter.Listener() {
            override fun onDataClick(data: DataEntity.Data) {
                super.onDataClick(data)

                if (data.dataModel.validUrl != null) {
                    ebActivity.loadUrl(data.dataModel.validUrl!!)
                }
            }
        })
        listRecyclerView.adapter = adapter
        listRecyclerView.addItemDecoration(itemDecoration)
        listRecyclerView.setItemViewCacheSize(32)

        if (bottomNavigationParentFragment != null) {
            bottomNavigationParentFragment!!.addScrollableView(listRecyclerView)
        }
    }

    override fun onDestroyView() {
        if (bottomNavigationParentFragment != null) {
            bottomNavigationParentFragment!!.removeScrollableView(listRecyclerView)
        }

        super.onDestroyView()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (!restoreCategory(savedInstanceState)) {
            netGetCategory()
        }
    }

    /**
     * 恢复 [categoryModel].

     * @return 是否成功恢复.
     */
    private fun restoreCategory(savedInstanceState: Bundle?): Boolean {
        if (savedInstanceState == null) {
            return false
        }

        categoryModel = savedInstanceState.getSerializable(STATE_CATEGORY_MODEL) as CategoryModel
        if (categoryModel == null) {
            return false
        }

        adapter.setNewData(categoryModel)

        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState == null) {
            return
        }

        outState.putSerializable(STATE_CATEGORY_MODEL, categoryModel)
    }

    //*****************************************************************************************************************
    // Net.

    private var categoryModel: CategoryModel? = null

    private val categoryUrl: String by lazy {
        "http://gank.io/api/data/%s/%d/%d".format(category.url, 100, 1)
    }

    /**
     * Gets [DayModel] model and sets data.
     */
    private fun netGetCategory() {
        netGet(categoryUrl, object : NetModelCallback<CategoryModel>() {
            override fun onSuccess(call: Call, model: CategoryModel, response: Response,
                    byteArray: ByteArray) {
                super.onSuccess(call, model, response, byteArray)

                categoryModel = model

                adapter.setNewData(categoryModel)
            }
        })
    }

    @CallSuper override fun onFront() {
        super.onFront()

        EBUtil.handler.post {
            if (actionBarParentFragment != null) {
                actionBarParentFragment!!.setNestedScrollingChild(listRecyclerView)
                actionBarParentFragment!!.setActionBarMode(EBActionBarFragment.ActionBarMode.SCROLL, false, null,
                        false)
            }
        }
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: Category): CategoryItemFragment {
            val fragment = CategoryItemFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARG_CATEGORY, category)
            fragment.arguments = arguments
            return fragment
        }

        //*************************************************************************************************************
        // Instance state.

        private val STATE_CATEGORY_MODEL = "category_model"
    }
}
