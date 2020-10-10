package br.com.hisao.timesheet

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
