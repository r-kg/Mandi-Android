package com.rkg.mandi.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rkg.mandi.domain.model.Mandi
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class MandiEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val title: String,
    val description: String,
    val updatedAt: Double,
    val streakStartedAt: Double?
) : Parcelable

fun MandiEntity.toDomain() = Mandi(
    id = id,
    title = title,
    description = description,
    updatedAt = updatedAt,
    streakStartedAt = streakStartedAt
)