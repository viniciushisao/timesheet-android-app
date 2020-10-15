package br.com.hisao.timesheet.util

import br.com.hisao.timesheet.model.TimeSheetData
import br.com.hisao.timesheet.model.TimeSheetDataType
import org.junit.Assert.*

class TimeSheetDataUtilTest {

    @org.junit.Test
    fun formatMinutes() {

        var mins: Long = 19000
        var res = TimeSheetDataUtil().formatMinutes(mins)
        System.out.println(res)
        assertTrue(res.contains("days") && res.contains("hours") && res.contains("minutes"))

        mins = Long.MAX_VALUE
        res = TimeSheetDataUtil().formatMinutes(mins)
        System.out.println(res)
        assertTrue(res.contains("days") && res.contains("hours") && res.contains("minutes"))

        mins = 0
        res = TimeSheetDataUtil().formatMinutes(mins)
        System.out.println(res)
        assertTrue(!res.contains("days") && !res.contains("hours") && res.contains("minutes"))

    }

    @org.junit.Test
    fun diffMinutes() {
        var t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 0)
        var t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        var result: Long = 1
        assertEquals(result, TimeSheetDataUtil().diffMinutes(t1, t2))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 3, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 0)
        result = 120
        assertEquals(result, TimeSheetDataUtil().diffMinutes(t1, t2))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 0, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 23, 59)
        result = (23 * 60) + 59
        assertEquals(result, TimeSheetDataUtil().diffMinutes(t1, t2))

        t1 = TimeSheetDataUtil().getFirstTimeOfDay(t1)
        t2 = TimeSheetDataUtil().getLastTimeOfDay(t2)
        result = (23 * 60) + 59
        assertEquals(result, TimeSheetDataUtil().diffMinutes(t1, t2))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 0, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 0, 0)
        result = 0
        assertEquals(result, TimeSheetDataUtil().diffMinutes(t1, t2))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 2, 0, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 0, 0)
        result = 1440
        assertEquals(result, TimeSheetDataUtil().diffMinutes(t1, t2))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 2, 12, 13)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 12, 13)
        result = 1440
        assertEquals(result, TimeSheetDataUtil().diffMinutes(t1, t2))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1901, 1, 1, 1, 1)
        result = 525600
        assertEquals(result, TimeSheetDataUtil().diffMinutes(t1, t2))
    }


    @org.junit.Test
    fun isConsistent() {
        var t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        var t2 = TimeSheetData(3, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 2)
        var t3 = TimeSheetData(1, TimeSheetDataType.START.name, 1900, 1, 1, 1, 3)
        val list = ArrayList<TimeSheetData>()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        var sortedList = TimeSheetDataUtil().sortList(list.toList())
        assertTrue(TimeSheetDataUtil().isConsistent(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        t2 = TimeSheetData(3, TimeSheetDataType.START.name, 1900, 1, 1, 1, 2)
        t3 = TimeSheetData(1, TimeSheetDataType.START.name, 1900, 1, 1, 1, 3)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        assertFalse(TimeSheetDataUtil().isConsistent(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        list.clear()
        list.add(t1)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        assertTrue(TimeSheetDataUtil().isConsistent(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        list.clear()
        list.add(t1)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        assertTrue(TimeSheetDataUtil().isConsistent(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        t2 = TimeSheetData(3, TimeSheetDataType.START.name, 1900, 1, 1, 1, 2)
        t3 = TimeSheetData(1, TimeSheetDataType.START.name, 1900, 1, 1, 1, 3)
        var t4 = TimeSheetData(1, TimeSheetDataType.START.name, 1900, 1, 1, 1, 4)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        list.add(t4)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        assertFalse(TimeSheetDataUtil().isConsistent(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        t2 = TimeSheetData(3, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 3)
        t3 = TimeSheetData(1, TimeSheetDataType.START.name, 1900, 1, 1, 1, 2)
        t4 = TimeSheetData(1, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 4)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        list.add(t4)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        assertFalse(TimeSheetDataUtil().isConsistent(sortedList))

        t1 = TimeSheetData(34, TimeSheetDataType.START.name, 1900, 1, 1, 1, 1)
        t2 = TimeSheetData(3, TimeSheetDataType.START.name, 1900, 1, 1, 1, 3)
        t3 = TimeSheetData(1, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 2)
        t4 = TimeSheetData(1, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 4)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        list.add(t4)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        assertTrue(TimeSheetDataUtil().isConsistent(sortedList))
    }

    @org.junit.Test
    fun diffMinutesList() {

        var t1 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 0, 10)
        val list = ArrayList<TimeSheetData>()
        list.add(t1)
        var sortedList = TimeSheetDataUtil().sortList(list.toList())
        var result: Long = 10
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 23, 0)
        list.clear()
        list.add(t1)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 59
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        list.clear()
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 0
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 23, 0)
        var t2 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 23, 23)
        list.clear()
        list.add(t1)
        list.add(t2)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 23
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 23, 0)
        list.clear()
        list.add(t1)
        list.add(t2)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 119
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 11, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 11, 10)
        var t3 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 23, 0)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 69
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 0, 10)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 23, 0)
        t3 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 23, 10)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 20
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 0, 10)
        t2 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 23, 0)
        t3 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 23, 10)
        var t4 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 23, 19)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        list.add(t4)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 60
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 0, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 0)
        t3 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 22, 0)
        t4 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 23, 0)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        list.add(t4)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 120
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 0)
        t3 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 0)
        t4 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 1, 0)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        list.add(t4)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 0
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))

        t1 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 1, 0)
        t2 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 2, 0)
        t3 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 2, 0)
        t4 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 3, 0)
        val t5 = TimeSheetData(0, TimeSheetDataType.START.name, 1900, 1, 1, 3, 0)
        val t6 = TimeSheetData(0, TimeSheetDataType.STOP.name, 1900, 1, 1, 4, 0)
        list.clear()
        list.add(t1)
        list.add(t2)
        list.add(t3)
        list.add(t4)
        list.add(t5)
        list.add(t6)
        sortedList = TimeSheetDataUtil().sortList(list.toList())
        result = 180
        assertEquals(result, TimeSheetDataUtil().diffMinutesList(sortedList))
    }

    @org.junit.Test
    fun getLastTimeOfDay() {
    }
}