package br.com.hisao.timesheet.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.hisao.timesheet.Repository
import br.com.hisao.timesheet.model.Resource
import br.com.hisao.timesheet.model.TimeSheetData
import javax.inject.Inject

class EditViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val currentTimeSheetData: LiveData<Resource<TimeSheetData>>
        get() = repository.entryRepositoryMutableLiveData

    fun getTimeSheetData(id: Long) {
        repository.fetchTimeSheetData(id)
    }

    fun updateTimeSheet(timeSheetData: TimeSheetData){
        repository.updateTimeSheet(timeSheetData)
    }
}