package dev.valvassori.domain

import kotlinx.serialization.Serializable

@Serializable
data class WorkingHours(
    val dayOfTheWeek: Int, // 0 = Sunday, 1 = Monday, ..., 6 = Saturday
    val startTime: Int, // Minutes since midnight/In Range (0..1439)
    val endTime: Int, // Minutes since midnight/In Range (0..1440)
)
