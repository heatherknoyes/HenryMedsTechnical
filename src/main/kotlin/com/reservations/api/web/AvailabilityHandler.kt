package com.reservations.api.web

import com.reservations.api.models.DayAvailability
import com.reservations.api.models.Reservation
import com.reservations.api.dao.ReservationsDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@RestController
class AvailabilityHandler(@Autowired private val reservationsDao: ReservationsDao) {

    @PutMapping("/add_availability")
    fun addAvailabilities(@RequestBody availabilities: List<DayAvailability>) {
        availabilities.forEach {
            val roundedStartTime = roundToNextQuarterHour(it.startTime)
            val roundedEndTime = roundToNextQuarterHour(it.endTime)
            var currentTime = roundedStartTime

            while (currentTime.isBefore(roundedEndTime) || currentTime == roundedEndTime) {
                val id = "${it.doctorName}:${it.day}:${currentTime}"
                roundToNextQuarterHour(roundedStartTime)
                val existingReservation = reservationsDao.getReservation(id)
                if (existingReservation == null) {
                    reservationsDao.save(
                        Reservation(
                            id = id,
                            doctorName = it.doctorName,
                            appointmentTime = currentTime,
                            appointmentDate = it.day
                        )
                    )
                }
                currentTime = currentTime.plus(15, ChronoUnit.MINUTES)
            }
        }
    }

    private fun roundToNextQuarterHour(time: LocalTime): LocalTime {
        val minute = time.minute
        val nextQuarterMinute = ((minute / 15) + 1) * 15
        val extraHour = nextQuarterMinute / 60
        val roundedMinute = nextQuarterMinute % 60
        return time.withMinute(0).withSecond(0).plusHours(extraHour.toLong()).plusMinutes(roundedMinute.toLong())
    }
}