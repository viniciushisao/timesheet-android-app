package br.com.hisao.timesheet.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.hisao.timesheet.Repository
import br.com.hisao.timesheet.model.Resource
import br.com.hisao.timesheet.model.TimeSheetData
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val timeSheetLimitDataLiveData: LiveData<Resource<List<TimeSheetData>>>
        get() = repository.timeSheetLimitListRepositoryMutableLiveData

    val lastEntryLiveData: LiveData<Resource<TimeSheetData>>
        get() = repository.lastEntryRepositoryMutableLiveData

    fun addTimeSheetData(timeSheetData: TimeSheetData) {
        repository.addTimeSheet(timeSheetData)
    }

    fun fetchLimitTimeSheetData(limit: Int) {
        repository.fetchLimitTimeSheetData(limit)
    }

    fun fetchLastEntry() {
        repository.fetchLastEntry()
    }

}