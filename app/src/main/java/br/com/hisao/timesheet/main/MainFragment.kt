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
import br.com.hisao.timesheet.getFormattedDateType
import br.com.hisao.timesheet.getTimeSheetData
import br.com.hisao.timesheet.model.Status
import br.com.hisao.timesheet.model.TimeSheetDataType

class MainFragment : Fragment() {

    private var currentTimeSheetDataType = TimeSheetDataType.START

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

        viewModel.clearAllTimeSheetData()
        viewModel.fetchLastEntry()

        return binding.root
    }

    private fun getNextTimeSheetType(): TimeSheetDataType {
        return if (currentTimeSheetDataType == TimeSheetDataType.STOP) TimeSheetDataType.START else TimeSheetDataType.STOP
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
                        binding.btnStartstop.text = getNextTimeSheetType().name
                        binding.btnStartstop.isClickable = true
                        binding.btnStartstop.isEnabled = true
                    }
                }
                Status.ERROR -> {
                    currentTimeSheetDataType = TimeSheetDataType.START
                    binding.btnStartstop.text = currentTimeSheetDataType.name

                }
                Status.LOADING -> {
                    currentTimeSheetDataType = TimeSheetDataType.START
                }
            }
        }

        viewModel.allTimeSheetDataLiveData.observe(viewLifecycleOwner) {

            when (it.status) {
                Status.SUCCESS -> {
                    it.let {

                        val sb = StringBuffer()
                        for (i in it.data!!) {
                            sb.append(i.getFormattedDateType())
                            sb.append("\n")
                        }

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
        binding.btnStartstop.setOnClickListener(View.OnClickListener {
            binding.btnStartstop.isClickable = false
            binding.btnStartstop.isEnabled = false
            val current = System.currentTimeMillis()
            viewModel.addTimeSheetData(current.getTimeSheetData(getNextTimeSheetType()))
            viewModel.fetchAllTimeSheetData()
            viewModel.fetchLastEntry()
        })
    }

}