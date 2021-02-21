package com.example.keephabits.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Calculations {

    fun calcultateTimeBetweenDates(startDate: String): String{

        val endDate = timeStampToString(System.currentTimeMillis())

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date1 = sdf.parse(startDate)
        val date2 = sdf.parse(endDate)

        var isNegative = false

        var difference: Long = date2.time - date1.time

        if (difference < 0){
            difference = -(difference)
            isNegative = true
        }

        val minutes: Long = difference / 60 / 1000
        val hours: Long = difference / 60 / 1000 / 60
        val days: Long = (difference / 60 / 1000 / 60) / 24
        val months: Long = (difference / 60 / 1000 / 60) / 24 / (365/12)
        val years: Long = difference / 60 / 1000 / 60 / 24 / 365

        if(isNegative){
            return when{
                minutes < 240 -> "Commence dans $minutes minutes"
                hours < 48 -> "Commence dans $hours heures"
                days < 61 -> "Commence dans $days jours"
                months < 24 -> "Commence dans $months mois"
                else -> "Commence dans $years années"

            }

        }

        return when{
            minutes < 240 -> "Il y a $minutes minutes"
            hours < 48 -> "Il y a $hours heures"
            days < 61 -> "Il y a $days jours"
            months < 24 -> "Il y a $months mois"
            else -> "Il y a $years années"

        }

    }

    private fun timeStampToString(timeStamp: Long): String{
        val stamp = Timestamp(timeStamp)
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date = sdf.format((Date(stamp.time)))

        return date.toString()
    }

    fun cleanDate(_day: Int, _month: Int, _year: Int): String{
        var day = _day.toString()
        var month = _month.toString()

        if (_day < 10){
            day = "0$_day"
        }

        if (_month < 9){
            month = "0${_month+1}"
        }

        return "$day/$month/$_year"

    }

    fun cleanTime(_hour: Int, _minute: Int): String {
        var hour= _hour.toString()
        var minute = _minute.toString()

        if(_hour < 10){
            hour = "0$_hour"
        }

        if(_minute < 10){
            hour = "0$_minute"
        }

        return "$hour:$minute"
    }

}