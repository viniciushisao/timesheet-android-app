package br.com.hisao.timesheet.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.hisao.timesheet.Repository
import br.com.hisao.timesheet.db.TimeSheetDatabase

class EditViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            val timeSheetDatabase = TimeSheetDatabase.getInstance(application).dao
            val repository = Repository(timeSheetDatabase)
            return EditViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}