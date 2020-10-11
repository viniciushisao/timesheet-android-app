package br.com.hisao.timesheet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.hisao.timesheet.model.TimeSheetData

@Database(entities = [TimeSheetData::class], version = 1, exportSchema = false)
abstract class TimeSheetDatabase : RoomDatabase() {

    abstract val dao: TimeSheetDao

    companion object {
        @Volatile
        private var INSTANCE: TimeSheetDatabase? = null

        fun getInstance(context: Context): TimeSheetDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimeSheetDatabase::class.java,
                        "time_sheet_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}

