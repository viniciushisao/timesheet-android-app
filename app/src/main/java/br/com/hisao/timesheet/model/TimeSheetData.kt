package br.com.hisao.timesheet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_sheet_data")

data class TimeSheetData(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    //TODO transform it in enum
    val type: String,

    val year: Int,
    val month: Int,
    val day: Int,
    val hour24: Int,
    val minute: Int

)
