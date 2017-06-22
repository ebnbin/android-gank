package com.ebnbin.gank.feature.days.dayviewpager

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import com.ebnbin.eb.util.EBRuntimeException
import com.ebnbin.eb.util.Timestamp
import com.ebnbin.gank.R
import com.ebnbin.recyclercalendarview.RecyclerCalendarView

/**
 * Shows history [AlertDialog].
 */
internal class DayViewPagerHistoryDialogFragment : DialogFragment() {
    private val callback: Callback by lazy {
        if (parentFragment !is Callback) {
            throw EBRuntimeException()
        }

        parentFragment as Callback
    }

    internal interface Callback {
        fun onSelected(date: Timestamp)
    }

    private val timestamps: ArrayList<Timestamp> by lazy {
        arguments.getParcelableArrayList<Timestamp>(ARG_TIMESTAMPS)
    }

    private val selectedTimestamp: Timestamp by lazy {
        arguments.getParcelable<Timestamp>(ARG_SELECTED_TIMESTAMP)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = Dialog(context)

        val rootView = View.inflate(context, R.layout.days_day_view_pager_dialog_fragment_calendar, null) as ViewGroup
        val recyclerCalendarView = rootView.findViewById<RecyclerCalendarView>(R.id.recycler_calendar_view)
        recyclerCalendarView.setRange(timestamps.first(), timestamps.last())
        recyclerCalendarView.selectDate(selectedTimestamp, true)
        recyclerCalendarView.listeners.add(object : RecyclerCalendarView.Listener {
            override fun onSelected(date: Timestamp) {
                callback.onSelected(date)
                dismiss()
            }
        })

        builder.setContentView(rootView)

        return builder
    }

    companion object {
        private val ARG_TIMESTAMPS = "timestamps"
        private val ARG_SELECTED_TIMESTAMP = "selected_timestamp"

        fun showDialog(fm: FragmentManager, timestamps: ArrayList<Timestamp>, selectedTimestamp: Timestamp) {
            val args = Bundle()
            args.putParcelableArrayList(ARG_TIMESTAMPS, timestamps)
            args.putParcelable(ARG_SELECTED_TIMESTAMP, selectedTimestamp)

            val fragment = DayViewPagerHistoryDialogFragment()
            fragment.arguments = args

            fragment.show(fm, null)
        }
    }
}
