package br.com.hisao.timesheet.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import br.com.hisao.timesheet.R
import br.com.hisao.timesheet.databinding.FragmentMainBinding
import br.com.hisao.timesheet.util.getFormattedDateType
import br.com.hisao.timesheet.util.getTimeSheetData
import br.com.hisao.timesheet.model.Status
import br.com.hisao.timesheet.model.TimeSheetDataType

class MainFragment : Fragment() {

    private var currentTimeSheetDataType = TimeSheetDataType.START
    private val limit = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMainBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val mainViewModelFactory = MainViewModelFactory(application)

        val viewModel: MainViewModel by lazy {
            ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        }

        setListeners(binding, viewModel)
        setObserves(binding, viewModel)

        //TODO
        //viewModel.clearAllTimeSheetData()

        viewModel.fetchLastEntry()
        viewModel.fetchLimitTimeSheetData(limit)

        return binding.root
    }

    private fun getNextTimeSheetType(): TimeSheetDataType {
        currentTimeSheetDataType =
            if (currentTimeSheetDataType == TimeSheetDataType.STOP) TimeSheetDataType.START else TimeSheetDataType.STOP
        return currentTimeSheetDataType
    }

    private fun setEnabledBtnStartstop(binding: FragmentMainBinding, enable: Boolean) {
        binding.btnStartstop.isClickable = enable
        binding.btnStartstop.isEnabled = enable
    }

    private fun setObserves(binding: FragmentMainBinding, viewModel: MainViewModel) {

        viewModel.lastEntryLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.let {
                        when {
                            it.data == null -> {
                                currentTimeSheetDataType = TimeSheetDataType.STOP
                            }
                            it.data.type == "START" -> {
                                currentTimeSheetDataType = TimeSheetDataType.START
                            }
                            it.data.type == "STOP" -> {
                                currentTimeSheetDataType = TimeSheetDataType.STOP
                            }
                        }
                        setEnabledBtnStartstop(binding, true)
                        binding.btnStartstop.text = getNextTimeSheetType().name
                    }
                }
                Status.ERROR -> {
                    //TODO
                    currentTimeSheetDataType = TimeSheetDataType.START
                    setEnabledBtnStartstop(binding, false)
                }
                Status.LOADING -> {
                    setEnabledBtnStartstop(binding, false)
                    currentTimeSheetDataType = TimeSheetDataType.START
                }
            }
        }

        viewModel.timeSheetLimitDataLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.let {

                        val sb = StringBuffer()
                        for (i in it.data!!) {
                            sb.append("id: ")
                            sb.append(i.id)
                            sb.append(" ")
                            sb.append(i.getFormattedDateType())
                            sb.append("\n")
                        }
                        binding.txtShowingmessages.text = it.data.size.toString()
                        binding.txtAll.text = sb.toString()
                    }
                }
                Status.ERROR -> {
                    //TODO
                }
                Status.LOADING -> {
                    //TODO
                }
            }
        }
    }

    private fun setListeners(binding: FragmentMainBinding, viewModel: MainViewModel) {
        binding.openCalendar.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_reportFragment)
        }
        binding.btnStartstop.setOnClickListener {
            val current = System.currentTimeMillis()
            viewModel.addTimeSheetData(current.getTimeSheetData(currentTimeSheetDataType))
            viewModel.fetchLimitTimeSheetData(limit)
            binding.btnStartstop.text = getNextTimeSheetType().name
        }
    }

}