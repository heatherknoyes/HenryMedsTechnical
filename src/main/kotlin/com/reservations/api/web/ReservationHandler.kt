package com.reservations.api.web

import com.reservations.api.dao.ReservationsDao
import com.reservations.api.models.Reservation
import com.reservations.api.models.ReservationRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime


@RestController
class ReservationHandler(@Autowired private val reservationsDao: ReservationsDao) {
    @GetMapping("/reservations")
    fun getReservation(): List<Reservation?> {
        return reservationsDao.getAllOpenReservations()
    }

    @PutMapping("/confirm_reservation")
    fun confirmReservation(@RequestBody reservation: ReservationRequest) {
        return reservationsDao.confirmReservation(
            Reservation(
                id = "${reservation.doctor}:${LocalDate.now()}:${LocalTime.now()}",
                doctorName = reservation.doctor,
                patientName = reservation.patient,
                appointmentTime = reservation.time,
                appointmentDate = reservation.day,
                confirmed = true
            )
        )
    }

    // Note: with more time I would adjust for time and make sure that the user is requesting a truly available time
    // I would also check that they aren't requesting an appointment time that is already taken as right now it would
    // override another patient
    @PutMapping("/new_reservation")
    fun insertReservation(@RequestBody reservation: ReservationRequest): Reservation? {
        val foundReservation = reservationsDao.getReservation("${reservation.doctor}:${reservation.day}:${reservation.time}")
        return if (foundReservation?.confirmed == true) {
            Reservation(
                id = "${reservation.doctor}:${LocalDate.now()}:${LocalTime.now()}",
                doctorName = reservation.doctor,
                patientName = reservation.patient,
                appointmentTime = reservation.time,
                appointmentDate = reservation.day,
                bookingInProcess = true
            )
        } else { null }
    }
}