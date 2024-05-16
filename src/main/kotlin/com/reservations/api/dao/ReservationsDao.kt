package com.reservations.api.dao

import com.reservations.api.models.Reservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

interface ReservationCollection :
    MongoRepository<Reservation?, String?> {
    fun findByConfirmedFalseAndBookingInProcessFalse(): List<Reservation?>
}

@Service
class ReservationsDao() {

    @Autowired
    private val collection: ReservationCollection? = null

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    fun save(reservation: Reservation) {
        collection?.save(reservation)
    }

    fun confirmReservation(reservation: Reservation) {
        collection?.save(reservation.copy(confirmed = true))
    }

    fun getReservation(id: String): Reservation? {
        val reservation = collection?.findById(id)?.orElse(null)
        val currentTime = LocalTime.now().minusMinutes(30)

        if (reservation?.confirmed == false &&
            (reservation.bookingInProcess && currentTime.isAfter(reservation.requestedTime))) {
            save(reservation.copy(bookingInProcess = false))
        }
        return collection?.findById(id)?.orElse(null)
    }

    // Note: With more time I would like to not get all of the reservations, but get only the reservations that are
    // applicable which you see the logic for in the forEach loop below
    fun getAllOpenReservations(): List<Reservation?> {
        val reservations = collection?.findAll()?.toList() ?: emptyList()
        val currentTime = LocalTime.now().minusMinutes(30)

        reservations.forEach {
            if (it?.confirmed == false && (it.bookingInProcess && currentTime.isAfter(it.requestedTime))) {
                save(it.copy(bookingInProcess = false))
            }
        }

        return collection?.findByConfirmedFalseAndBookingInProcessFalse() ?: emptyList()
    }

//    fun deleteReservation(id: String) {
//        val reservation = getReservation(id)
//        reservation?.copy(bookingInProcess = false)?.let { collection?.save(it) }
//    }
}