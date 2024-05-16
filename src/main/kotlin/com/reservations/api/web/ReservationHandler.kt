package com.reservations.api.web

import com.reservations.api.models.Reservation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime


@RestController
class ReservationHandler {
    @GetMapping("/reservations")
    fun getReservation(): List<Reservation> {
        return listOf(
            Reservation(
                id = "Dood:${LocalDate.now()}:${LocalTime.now()}",
                doctorName = "Dood",
                patientName = "Chris",
                appointmentTime = LocalTime.now(),
                appointmentDate = LocalDate.now()
            )
        )
    }

    @PutMapping("/newReservation")
    fun insertReservation(@RequestBody reservation: Reservation): Reservation? {
        return if (reservation.doctorName == "Dood") {
            Reservation(
                id = "Dood:${LocalDate.now()}:${LocalTime.now()}",
                doctorName = reservation.doctorName,
                patientName = reservation.patientName,
                appointmentTime = reservation.appointmentTime,
                appointmentDate = reservation.appointmentDate
            )
        } else { null }
    }

//    fun deleteAppointment(reservation: Reservation) {
//        if (reservation.confirmed == false &&
//            Duration.between(Instant.now(), reservation.requestedTime).toMinutes() > 30 ) {
//            // delete this appointment
//        }
//    }
}