package br.com.hisao.timesheet.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

class ReportViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var viewModelJob = Job()
    private var viewModelCoroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _currentPickDate = MutableLiveData<TimeSheetData>()
    val currentDate: LiveData<TimeSheetData>
        get() = _currentPickDate

    val timeSheetDataListLiveData: LiveData<Resource<List<TimeSheetData>>>
        get() = repository.timeSheetDataListRepositoryMutableLiveData


    fun onPickDate(timeSheetData: TimeSheetData) {
        _currentPickDate.value = timeSheetData
        fetchTimeSheetDataList(timeSheetData)
    }

    fun delete(id: Long) {
        viewModelCoroutineScope.launch(Dispatchers.IO) {
            val res = repository.delete(id)
            if (res.status == Status.SUCCESS) {
                fetchTimeSheetDataList(_currentPickDate.value!!)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun fetchTimeSheetDataList(timeSheetData: TimeSheetData) {
        repository.fetchTimeSheetData(timeSheetData)
    }
}