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

    val imeSheetLimitListRepositoryMutableLiveData = MutableLiveData<Resource<List<TimeSheetData>>>()
    val lastEntryRepositoryMutableLiveData = MutableLiveData<Resource<TimeSheetData>>()
    val timeSheetDataListRepositoryMutableLiveData =
        MutableLiveData<Resource<List<TimeSheetData>>>()
    val entryRepositoryMutableLiveData = MutableLiveData<Resource<TimeSheetData>>()


    fun fetchLimitTimeSheetData(limit: Int) {
        imeSheetLimitListRepositoryMutableLiveData.postValue(Resource.loading(null))
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    timeSheetDao.getAllTimeSheetData()
                }
                imeSheetLimitListRepositoryMutableLiveData.postValue(Resource.success(result))
            } catch (ex: Exception) {
                imeSheetLimitListRepositoryMutableLiveData.postValue(
                    Resource.error(
                        ex.localizedMessage ?: "",
                        null
                    )
                )
            }
        }
    }

    fun fetchTimeSheetData(timeSheetData: TimeSheetData) {
        timeSheetDataListRepositoryMutableLiveData.postValue(Resource.loading(null))
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    timeSheetDao.getTimeSheetDataList(
                        timeSheetData.day,
                        timeSheetData.month,
                        timeSheetData.year
                    )
                }
                timeSheetDataListRepositoryMutableLiveData.postValue(Resource.success(result))
            } catch (ex: Exception) {
                timeSheetDataListRepositoryMutableLiveData.postValue(
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

    fun updateTimeSheet(timeSheetData: TimeSheetData) {
        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    timeSheetDao.update(timeSheetData)
                }
            } catch (ex: Exception) {
                //TODO
            }
        }
    }

    fun fetchTimeSheetData(id: Long) {
        entryRepositoryMutableLiveData.postValue(Resource.loading(null))
        coroutineScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    timeSheetDao.getTimeSheetData(id)
                }
                entryRepositoryMutableLiveData.postValue(Resource.success(result))
            } catch (ex: Exception) {
                entryRepositoryMutableLiveData.postValue(
                    Resource.error(
                        ex.localizedMessage ?: "",
                        null
                    )
                )
            }
        }
    }

    fun delete(id: Long) {
        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    timeSheetDao.delete(id)
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