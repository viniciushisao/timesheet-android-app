package br.com.hisao.timesheet.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.hisao.timesheet.Repository
import br.com.hisao.timesheet.db.TimeSheetDatabase

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val timeSheetDatabase = TimeSheetDatabase.getInstance(application).dao
            val repository = Repository(timeSheetDatabase)
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}