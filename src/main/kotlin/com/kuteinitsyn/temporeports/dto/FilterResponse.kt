package com.kuteinitsyn.temporeports.dto

import kotlinx.serialization.Serializable

@Serializable
data class FilterResponse(
    val filterKey: String
)