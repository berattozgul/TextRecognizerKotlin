package com.example.textrecognizerkotlin

import java.util.*

data class HistoryModel(var id: Int = getAutoID(), var text: String = "",var time: String="") {
    companion object {
        fun getAutoID(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}