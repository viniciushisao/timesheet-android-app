package br.com.hisao.timesheet.util

import br.com.hisao.timesheet.model.TimeSheetData
import br.com.hisao.timesheet.model.TimeSheetDataType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

open class TimeSheetDataUtil {

    fun sortList(list: List<TimeSheetData>?): List<TimeSheetData> {
        if (list == null) {
            return ArrayList()
        }
        return list.sortedWith(compareBy<TimeSheetData> { it.year }.thenBy { it.month }
            .thenBy { it.day }
            .thenBy { it.hour24 }.thenBy { it.minute }.thenBy { it.id })
    }

    /**
     * List must be ordered
     * List must be consistent
     */

    fun diffMinutesList(list: List<TimeSheetData>): Long {

        if (list.isEmpty()) {
            return 0
        }
        var result: Long = 0
        var begin = 0
        var end = list.size - 1

        //check the first
        if (TimeSheetDataType.getId(list[begin].type) == TimeSheetDataType.STOP.id) {
            result += diffMinutes(getFirstTimeOfDay(list[begin]), list[begin])
            begin++
        }
        //check the last
        if (TimeSheetDataType.getId(list[end].type) == TimeSheetDataType.START.id) {
            result += diffMinutes(getLastTimeOfDay(list[end]), list[end])
            end--
        }

        if (end - begin < 1)
            return result

        for (i in begin until end step 2) {
            result += diffMinutes(list[i], list[i + 1])
        }

        return result
    }

    /**
     * an empty list is a valid list
     */
    fun isConsistent(list: List<TimeSheetData>): Boolean {

        var currentType = TimeSheetDataType.getId(list[0].type)
        for (i in 1 until list.size) {
            val type = TimeSheetDataType.getId(list[i].type)
            if (currentType == type)
                return false
            currentType = type

        }
        return true
    }

    /**
     *
     */
    fun diffMinutes(t1: TimeSheetData, t2: TimeSheetData): Long {

        val c1 = Calendar.getInstance()
        c1.set(t1.year, t1.month, t1.day, t1.hour24, t1.minute)

        val c2 = Calendar.getInstance()
        c2.set(t2.year, t2.month, t2.day, t2.hour24, t2.minute)

        val min1 = (c1.timeInMillis / 1000) / 60
        val min2 = (c2.timeInMillis / 1000) / 60

        return abs(min1 - min2)
    }

    /**
     * 00:00 same year, month, day
     */
    fun getFirstTimeOfDay(timeSheetData: TimeSheetData): TimeSheetData {
        return TimeSheetData(
            timeSheetData.id,
            timeSheetData.type,
            timeSheetData.year,
            timeSheetData.month,
            timeSheetData.day,
            0,
            0
        )
    }

    /**
     * 23:59  same year, month, day
     */
    fun getLastTimeOfDay(timeSheetData: TimeSheetData): TimeSheetData {
        return TimeSheetData(
            timeSheetData.id,
            timeSheetData.type,
            timeSheetData.year,
            timeSheetData.month,
            timeSheetData.day,
            23,
            59
        )
    }


}
