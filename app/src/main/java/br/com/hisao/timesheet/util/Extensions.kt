package br.com.hisao.timesheet.util

import br.com.hisao.timesheet.model.TimeSheetData
import br.com.hisao.timesheet.model.TimeSheetDataType
import java.util.*

fun Calendar.getTimeSheetData(): TimeSheetData {
    return TimeSheetData(
        0,
        TimeSheetDataType.START.name,
        this.get(Calendar.YEAR),
        this.get(Calendar.MONTH) + 1,
        this.get(Calendar.DAY_OF_MONTH),
        this.get(Calendar.HOUR_OF_DAY),
        this.get(Calendar.MINUTE)
    )
}

/**
 * E.g. 23:58
 */
fun TimeSheetData?.getFormattedTime(): String {
    if (this != null) {
        return "" + this.hour24 + ":" + this.minute
    }
    return ""
}

/**
 * E.g. START 23/Sep/2020 23:58
 */
fun TimeSheetData?.getFormattedDateType(): String {
    if (this != null) {
        val day = this.day
        val month = this.month
        val year = this.year
        var type = this.type

        if (type == "STOP") type = "$type  "

        return this.type + " " + day.toString() + "/" + month.getMonth() + "/" + year.toString() + " " + this.hour24 + ":" + this.minute
    }
    return ""
}

/**
 * E.g 23/Oct/2020
 */
fun TimeSheetData?.getFormattedDate(): String {
    if (this != null) {
        val day = this.day
        val month = this.month
        val year = this.year
        var type = this.type

        if (type == "STOP") type = "$type  "

        return day.toString() + "/" + month.getMonth() + "/" + year.toString()
    }
    return ""
}

fun Int?.getMonth(): String {

    if (this != null) {

        when (this) {
            1 -> return "Jan"
            2 -> return "Feb"
            3 -> return "Mar"
            4 -> return "Apr"
            5 -> return "May"
            6 -> return "Jun"
            7 -> return "Jul"
            8 -> return "Aug"
            9 -> return "Sep"
            10 -> return "Oct"
            11 -> return "Nov"
            12 -> return "Dec"
        }
    }
    return ""
}

fun Long.getTimeSheetData(type: TimeSheetDataType): TimeSheetData {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val timeSheetData = calendar.getTimeSheetData()
    val day = timeSheetData.day
    val month = timeSheetData.month
    val year = timeSheetData.year
    val hour24 = timeSheetData.hour24
    val minute = timeSheetData.minute
    return TimeSheetData(0, type.name, year, month, day, hour24, minute)
}


