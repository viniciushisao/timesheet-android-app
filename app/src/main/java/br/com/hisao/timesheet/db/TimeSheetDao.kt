package br.com.hisao.timesheet.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.hisao.timesheet.model.TimeSheetData

@Dao
interface TimeSheetDao {

    @Insert
    fun insert(data: TimeSheetData)

    @Update
    fun update(data: TimeSheetData)

    @Query("DELETE FROM time_sheet_data")
    fun clear()

    @Query("SELECT * FROM time_sheet_data ORDER BY id DESC LIMIT :limit")
    fun getLimitTimeSheetData(limit: Int): List<TimeSheetData>

    @Query("SELECT * FROM time_sheet_data WHERE id LIKE :id")
    fun getTimeSheetData(id: Long): TimeSheetData

    @Query("SELECT count(*) FROM time_sheet_data")
    fun getArticlesCount(): Int

    @Query("SELECT * FROM time_sheet_data ORDER BY id DESC LIMIT 1")
    fun getLastEntry(): TimeSheetData

    @Query("SELECT * FROM time_sheet_data WHERE day LIKE :myDay AND month LIKE :myMonth AND year LIKE :myYear")
    fun getTimeSheetDataList(myDay: Int, myMonth: Int, myYear: Int): List<TimeSheetData>

    @Query("DELETE FROM time_sheet_data WHERE id LIKE :id")
    fun delete(id: Long)
}