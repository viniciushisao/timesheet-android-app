package br.com.hisao.timesheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import javax.inject.Inject

class ReportViewModel @Inject constructor(
) : ViewModel() {

    private val _currentDate = MutableLiveData<Calendar>()
    val currentDate: LiveData<Calendar>
        get() = _currentDate


    fun onPickDate(calendar : Calendar){
        _currentDate.value = calendar
    }

}