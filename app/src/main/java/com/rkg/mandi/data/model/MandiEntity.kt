package com.rkg.mandi.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class MandiEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) : Parcelable