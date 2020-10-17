package br.com.hisao.timesheet.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.hisao.timesheet.Repository
import br.com.hisao.timesheet.model.Resource
import br.com.hisao.timesheet.model.Status
import br.com.hisao.timesheet.model.TimeSheetData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var viewModelJob = Job()
    private var viewModelCoroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var fetchLimit = 20

    val timeSheetLimitDataLiveData: LiveData<Resource<List<TimeSheetData>>>
        get() = repository.timeSheetLimitListRepositoryMutableLiveData

    val lastEntryLiveData: LiveData<Resource<TimeSheetData>>
        get() = repository.lastEntryRepositoryMutableLiveData

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addTimeSheetData(timeSheetData: TimeSheetData) {
        viewModelCoroutineScope.launch(Dispatchers.IO) {
            val res = repository.addTimeSheet(timeSheetData)
            if (res.status == Status.SUCCESS) {
                fetchLimitTimeSheetData()
            }
        }
    }

    fun fetchLimitTimeSheetData() {
        repository.fetchLimitTimeSheetData(fetchLimit)
    }

    fun fetchLastEntry() {
        repository.fetchLastEntry()
    }

}