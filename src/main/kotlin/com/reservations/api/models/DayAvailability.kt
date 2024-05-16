package com.reservations.api.models

import java.time.LocalDate
import java.time.LocalTime

data class DayAvailability(
    val doctorName: String,
    val day: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
)