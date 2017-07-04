package com.ebnbin.gank.feature.category

import android.os.Bundle
import android.view.View
import com.ebnbin.ebapplication.net.NetModelCallback
import com.ebnbin.gank.feature.data.Category
import com.ebnbin.gank.feature.data.DataEntity
import com.ebnbin.gank.feature.data.DataFragment
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class CategoryPageFragment : DataFragment() {
    private val category: Category by lazy {
        arguments.getSerializable(ARG_CATEGORY) as Category
    }

    private var currentPage = 1
    private var loadMoreEnd = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setLoadMoreView(EBLoadMoreView())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        currentPage = savedInstanceState?.getInt(STATE_CURRENT_PAGE, 1) ?: 1
        loadMoreEnd = savedInstanceState?.getBoolean(STATE_LOAD_MORE_END) ?: false
        adapter.setOnLoadMoreListener({
            if (loadMoreEnd) {
                adapter.loadMoreEnd()
            } else {
                netGetCategory(false)
            }
        }, listRecyclerView)
        // TODO Saves and restores state of StateView.
        if (!restoreCategory(savedInstanceState) && !loadMoreEnd || adapter.data.size == 0) {
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

        val data = savedInstanceState.getParcelableArrayList<DataEntity>(STATE_DATA) ?: return false
        adapter.setNewData(data)

        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState == null) {
            return
        }

        outState.putInt(STATE_CURRENT_PAGE, currentPage)
        outState.putBoolean(STATE_LOAD_MORE_END, loadMoreEnd)
        outState.putParcelableArrayList(STATE_DATA, ArrayList<DataEntity>(adapter.data))
    }

    //*****************************************************************************************************************
    // Net.

    private fun getCategoryUrl(): String {
        return "http://gank.io/api/data/%s/%d/%d".format(category.url, COUNT, currentPage)
    }

    /**
     * Gets [CategoryModel] model and sets data.
     */
    private fun netGetCategory(stateViewEnabled: Boolean = true) {
        netGet(getCategoryUrl(), object : NetModelCallback<CategoryModel>() {
            override fun onSuccess(call: Call, model: CategoryModel, response: Response,
                    byteArray: ByteArray) {
                super.onSuccess(call, model, response, byteArray)

                adapter.addData(convertData(model))

                currentPage++

                adapter.loadMoreComplete()

                if (model.getCount() < COUNT) {
                    adapter.loadMoreEnd()
                    loadMoreEnd = true
                }
            }

            override fun onFailure(call: Call, errorCode: Int, e: IOException?, response: Response?) {
                super.onFailure(call, errorCode, e, response)

                if (stateViewEnabled) return

                adapter.loadMoreFail()
            }
        }, stateViewEnabled)
    }

    private fun convertData(model: CategoryModel): List<DataEntity> {
        val entities = ArrayList<DataEntity>()

        if (model.results == null) {
            return entities
        }

        model.results!!.mapTo(entities) { DataEntity.Data(it) }

        return entities
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        private const val COUNT = 20

        fun newInstance(category: Category): CategoryPageFragment {
            val fragment = CategoryPageFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARG_CATEGORY, category)
            fragment.arguments = arguments
            return fragment
        }

        //*************************************************************************************************************
        // Instance state.

        private val STATE_DATA = "data"
        private val STATE_CURRENT_PAGE = "current_page"
        private val STATE_LOAD_MORE_END = "load_more_end"
    }
}
