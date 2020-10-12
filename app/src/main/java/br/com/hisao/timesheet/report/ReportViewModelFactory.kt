package br.com.hisao.timesheet.report

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.hisao.timesheet.Repository
import br.com.hisao.timesheet.db.TimeSheetDatabase

class ReportViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            val timeSheetDatabase = TimeSheetDatabase.getInstance(application).dao
            val repository = Repository(timeSheetDatabase)
            return ReportViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}