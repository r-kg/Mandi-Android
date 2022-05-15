package com.rkg.mandi.presentation.binding

sealed class MandiTapEvent {
    data class PlantTap(val id: Int) : MandiTapEvent()
}