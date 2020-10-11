package br.com.hisao.timesheet

import br.com.hisao.timesheet.model.TimeSheetData
import br.com.hisao.timesheet.model.TimeSheetDataType
import java.util.*

fun Calendar?.getFormattedDate(): String {
    if (this != null) {
        val day = this.get(Calendar.DATE)
        val month = this.get(Calendar.MONTH) + 1
        val year = this.get(Calendar.YEAR)

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

fun Long.getTimeSheetData(type : TimeSheetDataType): TimeSheetData {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val day = calendar.get(Calendar.DATE)
    val month = calendar.get(Calendar.MONTH)
    val year = calendar.get(Calendar.YEAR)
    val hour24 = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    return TimeSheetData(0, type.name, year, month, day, hour24, minute)

}


