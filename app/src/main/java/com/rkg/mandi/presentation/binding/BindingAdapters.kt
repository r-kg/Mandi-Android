package com.rkg.mandi.presentation.binding

import android.net.Uri
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rkg.mandi.R
import com.rkg.mandi.presentation.utils.ImageLoader
import java.util.*

@BindingAdapter("isVisible")
fun setVisible(view: View, value: Boolean?) {
    view.isVisible = value ?: false
}

@BindingAdapter("isInvisible")
fun setInvisible(view: View, value: Boolean?) {
    view.isInvisible = value ?: false
}

@BindingAdapter("isGone")
fun setGone(view: View, value: Boolean?) {
    view.isGone = value ?: false
}

@BindingAdapter("hasFixedSize")
fun setHasFixedSize(recyclerView: RecyclerView, hasFixedSize: Boolean) {
    recyclerView.setHasFixedSize(hasFixedSize)
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, imageUrl: String?) {
    ImageLoader.with(imageView.context)?.run {
        load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}

@BindingAdapter("imageUri")
fun setImageUri(imageView: ImageView, imageUri: Uri?) {
    ImageLoader.with(imageView.context)?.run {
        load(imageUri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
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

@BindingAdapter("streakDay")
fun setStreakDay(textView: TextView, time: Double?) {
    textView.text = time?.let {
        val date = Calendar.getInstance().apply { timeInMillis = (it * 1000).toLong() }
        val today = Calendar.getInstance()

        val dayDiff = today.get(Calendar.DAY_OF_YEAR) - date.get(Calendar.DAY_OF_YEAR)
        "${dayDiff + 1}"
    } ?: "0"
}

