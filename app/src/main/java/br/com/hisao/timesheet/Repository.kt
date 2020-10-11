package br.com.hisao.timesheet

import androidx.lifecycle.MutableLiveData
import br.com.hisao.timesheet.db.TimeSheetDao
import br.com.hisao.timesheet.model.Resource
import br.com.hisao.timesheet.model.TimeSheetData
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

private var viewModelJob = Job()
private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

@Singleton
class Repository @Inject constructor(
    private val timeSheetDao: TimeSheetDao
) {

    val allTimeSheetRepositoryMutableLiveData = MutableLiveData<Resource<List<TimeSheetData>>>()
    val lastEntryRepositoryMutableLiveData = MutableLiveData<Resource<TimeSheetData>>()

    fun fetchAllTimeSheetData() {
        allTimeSheetRepositoryMutableLiveData.postValue(Resource.loading(null))
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    timeSheetDao.getAllTimeSheetData()
                }
                allTimeSheetRepositoryMutableLiveData.postValue(Resource.success(result))
            } catch (ex: Exception) {
                allTimeSheetRepositoryMutableLiveData.postValue(
                    Resource.error(
                        ex.localizedMessage ?: "",
                        null
                    )
                )
            }
        }
    }

    fun addTimeSheet(timeSheetData: TimeSheetData) {
        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    timeSheetDao.insert(timeSheetData)
                }
            } catch (ex: Exception) {
                //TODO
            }
        }
    }

    fun clearAllDatabase() {
        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    timeSheetDao.clear()
                }
            } catch (ex: Exception) {
                //TODO
            }
        }
    }

    fun fetchLastEntry() {
        lastEntryRepositoryMutableLiveData.postValue(Resource.loading(null))
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    timeSheetDao.getLastEntry()
                }
                lastEntryRepositoryMutableLiveData.postValue(Resource.success(result))
            } catch (ex: Exception) {
                //TODO
            }
        }
    }


}