package com.reservations.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Document(collection = "reservations")
data class Reservation(
    @Id val id: String,
    val doctorName: String,
    val patientName: String? = null,
    val appointmentDate: LocalDate,
    val appointmentTime: LocalTime,
    val requestedTime: Instant = Instant.now(),
    val bookingInProcess: Boolean = false,
    val confirmed: Boolean = false
)
