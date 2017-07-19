package com.ebnbin.gank.feature.data

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ebnbin.eb.context.EBActionBarFragment
import com.ebnbin.eb.context.EBFragment
import com.ebnbin.eb.util.EBUtil
import com.ebnbin.gank.R

abstract class DataFragment : EBFragment() {
    override fun overrideContentViewLayout(): Int {
        return R.layout.data_fragment
    }

    protected val listRecyclerView: RecyclerView by lazy {
        stateView.findViewById(R.id.data) as RecyclerView
    }

    private val layoutManager: DataLayoutManager by lazy {
        DataLayoutManager(context)
    }

    protected val adapter: DataAdapter by lazy {
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
        listRecyclerView.setItemViewCacheSize(16)
        adapter.bindToRecyclerView(listRecyclerView)
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
}
