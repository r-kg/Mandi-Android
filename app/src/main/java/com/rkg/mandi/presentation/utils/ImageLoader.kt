package com.rkg.mandi.presentation.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

object ImageLoader {

    @JvmStatic
    fun with(context: Context): RequestManager? {
        return try {
            Glide.with(context)
        } catch (ignored: Exception) {
            null
        }
    }

    @JvmStatic
    fun get(context: Context): Glide? {
        return try {
            Glide.get(context)
        } catch (ignored: Exception) {
            null
        }
    }
}