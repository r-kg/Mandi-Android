package com.rkg.mandi.utils

import java.util.*
import java.util.concurrent.TimeUnit

object CalendarUtil {
    private val secondInMillis = TimeUnit.SECONDS.toMillis(1)
    private val minuteInMillis = TimeUnit.MINUTES.toMillis(1)

    fun currentTimeInMillis() = Calendar.getInstance().timeInMillis

    fun currentTimeInSeconds() = (Calendar.getInstance().timeInMillis / secondInMillis)

    fun currentTimeInMinutes() = (Calendar.getInstance().timeInMillis / minuteInMillis)
}