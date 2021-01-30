package com.kuteinitsyn.temporeports.dto

import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.LocalDate

@Serializable
class Filter(
    val projectKey: MutableList<String> = ArrayList(),
) {

    val from: String
    val to: String


    init {
        val nowDate = LocalDate.now()
        val day = nowDate.dayOfWeek.value

        if (day >= DayOfWeek.MONDAY.value && day <= DayOfWeek.THURSDAY.value) {
            this.from = nowDate.with(DayOfWeek.MONDAY).minusDays(7).toString()
            this.to = nowDate.with(DayOfWeek.FRIDAY).minusDays(7).toString()
        } else {
            this.from = nowDate.with(DayOfWeek.MONDAY).toString()
            this.to = nowDate.with(DayOfWeek.FRIDAY).toString()
        }
    }
}