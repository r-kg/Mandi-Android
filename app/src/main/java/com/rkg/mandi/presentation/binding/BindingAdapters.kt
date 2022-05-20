package com.rkg.mandi.presentation.binding

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rkg.mandi.R
import java.util.*

@BindingAdapter("hasFixedSize")
fun setHasFixedSize(recyclerView: RecyclerView, hasFixedSize: Boolean) {
    recyclerView.setHasFixedSize(hasFixedSize)
}

@BindingAdapter("lastUpdatedTime")
fun setLastUpdatedTime(textView: TextView, time: Double?) {
    val context = textView.context

    if (time == null) {
        textView.text = context.getString(R.string.no_record)
        return
    }

    val date = Calendar.getInstance().apply { timeInMillis = (time * 1000).toLong() }
    val today = Calendar.getInstance()

    val dayDiff = today.get(Calendar.DAY_OF_YEAR) - date.get(Calendar.DAY_OF_YEAR)

    val dateTimeString = when {
        today.get(Calendar.YEAR) != date.get(Calendar.YEAR) -> {
            DateUtils.formatDateTime(
                context,
                date.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
            )
        }
        dayDiff == 0 -> {
            "${context.getString(R.string.today)} " + DateUtils.formatDateTime(
                context,
                date.timeInMillis,
                DateUtils.FORMAT_SHOW_TIME
            )
        }
        dayDiff == 1 -> {
            context.getString(R.string.yesterday)
        }
        dayDiff <= 3 -> {
            context.getString(R.string.days_ago, dayDiff)
        }
        else -> {
            DateUtils.formatDateTime(
                context,
                date.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_YEAR or DateUtils.FORMAT_SHOW_TIME
            )
        }
    }

    textView.text = dateTimeString
}