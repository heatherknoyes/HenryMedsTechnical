package com.reservations.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalTime

// Note: With more time I would prefer not to use LocalTime and use Instant with conversion instead such that everything
// is in UTC
@Document(collection = "reservations")
data class Reservation(
    @Id val id: String,
    val doctorName: String,
    val patientName: String? = null,
    val appointmentDate: LocalDate,
    val appointmentTime: LocalTime,
    val requestedTime: LocalTime = LocalTime.now(),
    val bookingInProcess: Boolean = false,
    val confirmed: Boolean = false
)
