package com.ebnbin.gank.feature.category

import com.ebnbin.ebapplication.model.EBModel
import com.ebnbin.gank.feature.data.DataModel

class CategoryModel : EBModel() {
    private var error: Boolean = false
    var results: List<DataModel>? = null
        private set

    fun getCount(): Int {
        return results?.size ?: 0
    }

    override val isValid: Boolean
        get() = !error// && results != null && results!!.isNotEmpty()
}
