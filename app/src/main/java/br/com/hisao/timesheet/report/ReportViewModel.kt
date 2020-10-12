package br.com.hisao.timesheet.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.hisao.timesheet.Repository
import br.com.hisao.timesheet.model.Resource
import br.com.hisao.timesheet.model.TimeSheetData
import javax.inject.Inject

class ReportViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _currentPickDate = MutableLiveData<TimeSheetData>()
    val currentDate: LiveData<TimeSheetData>
        get() = _currentPickDate

    val timeSheetDataListRepositoryLiveData: LiveData<Resource<List<TimeSheetData>>>
        get() = repository.timeSheetDataListRepositoryMutableLiveData

    fun onPickDate(timeSheetData: TimeSheetData) {
        _currentPickDate.value = timeSheetData
        fetchTimeSheetDataList(timeSheetData)
    }

    fun delete(id : Long){
        repository.delete(id)
        fetchTimeSheetDataList(_currentPickDate.value!!)
    }

    private fun fetchTimeSheetDataList(timeSheetData: TimeSheetData) {
        repository.fetchTimeSheetData(timeSheetData)
    }
}