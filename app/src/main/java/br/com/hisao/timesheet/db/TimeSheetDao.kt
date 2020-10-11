package br.com.hisao.timesheet.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.hisao.timesheet.model.TimeSheetData

@Dao
interface TimeSheetDao {

    @Insert
    fun insert(data: TimeSheetData)

    @Query("DELETE FROM time_sheet_data")
    fun clear()

    @Query("SELECT * FROM time_sheet_data")
    fun getAllTimeSheetData(): List<TimeSheetData>

    @Query("SELECT count(*) FROM time_sheet_data")
    fun getArticlesCount(): Int

    @Query("SELECT * FROM time_sheet_data ORDER BY id DESC LIMIT 1")
    fun getLastEntry(): TimeSheetData
}