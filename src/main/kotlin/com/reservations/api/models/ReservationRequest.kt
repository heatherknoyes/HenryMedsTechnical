package com.reservations.api.models

import java.time.LocalDate
import java.time.LocalTime

data class ReservationRequest(
    val doctor: String,
    val patient: String,
    val day : LocalDate,
    val time: LocalTime
)