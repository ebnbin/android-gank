package com.ebnbin.gank.feature.category

import android.os.Bundle
import com.ebnbin.ebapplication.net.NetModelCallback
import com.ebnbin.gank.feature.data.Category
import com.ebnbin.gank.feature.data.DataEntity
import com.ebnbin.gank.feature.data.DataFragment
import com.ebnbin.gank.feature.days.day.DayModel
import okhttp3.Call
import okhttp3.Response

class CategoryItemFragment : DataFragment() {
    private val category: Category by lazy {
        arguments.getSerializable(ARG_CATEGORY) as Category
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

        setNewData(convertData(categoryModel!!))

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

                setNewData(convertData(this@CategoryItemFragment.categoryModel!!))
            }
        })
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
