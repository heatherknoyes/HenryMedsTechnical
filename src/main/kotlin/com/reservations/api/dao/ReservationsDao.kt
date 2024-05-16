package com.reservations.api.dao

import com.reservations.api.models.Reservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service

//@Repository
interface ReservationCollection :
    MongoRepository<Reservation?, String?> {
}

@Service
class ReservationsDao() {

    @Autowired
    private val collection: ReservationCollection? = null

    fun save(reservation: Reservation) {
        collection?.save(reservation)
    }

    fun getReservation(id: String): Reservation? {
        return collection?.findById(id)?.orElse(null)
    }

    fun getAllReservations(): List<Reservation?> {
        return collection?.findAll()?.toList() ?: emptyList()
    }

    fun deleteReservation(id: String) {
        collection?.deleteById(id)
    }
}