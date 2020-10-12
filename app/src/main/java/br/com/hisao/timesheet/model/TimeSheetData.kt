package br.com.hisao.timesheet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_sheet_data")

data class TimeSheetData(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var type: String,
    var year: Int,
    var month: Int,
    var day: Int,
    var hour24: Int,
    var minute: Int
) {
    companion object {
        fun getDummy(): TimeSheetData {
            return TimeSheetData(0, "", 0, 0, 0, 0, 0)
        }
    }
}


