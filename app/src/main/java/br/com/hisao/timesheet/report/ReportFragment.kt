package br.com.hisao.timesheet.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import br.com.hisao.timesheet.DatePickerFragment
import br.com.hisao.timesheet.OnDateSetListener
import br.com.hisao.timesheet.databinding.FragmentReportBinding
import br.com.hisao.timesheet.model.Status
import br.com.hisao.timesheet.model.TimeSheetData
import br.com.hisao.timesheet.util.TimeSheetDataUtil
import br.com.hisao.timesheet.util.getFormattedDate

class ReportFragment : Fragment(), ReportAdapterCallback {

    private lateinit var mViewModel: ReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReportBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val reportViewModelFactory = ReportViewModelFactory(application)

        val viewModel: ReportViewModel by lazy {
            ViewModelProvider(this, reportViewModelFactory).get(ReportViewModel::class.java)
        }

        mViewModel = viewModel

        val adapter = ReportAdapter(this)
        binding.reportList.adapter = adapter

        setObservables(viewModel, binding, adapter)
        setListeners(viewModel, binding)

        if (viewModel.currentDate.value == null){
            openDayPicker(viewModel)
        }else{
            viewModel.onPickDate(viewModel.currentDate.value!!)
        }

        return binding.root
    }


    private fun setObservables(
        viewModel: ReportViewModel,
        binding: FragmentReportBinding,
        adapter: ReportAdapter
    ) {
        viewModel.currentDate.observe(viewLifecycleOwner) {
            binding.lblSelecteddate.text = it.getFormattedDate()
        }

        viewModel.timeSheetDataListLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.let {
                        val sortedList = TimeSheetDataUtil().sortList(it.data)
                        if (TimeSheetDataUtil().isConsistent(sortedList)){
                            val minutes = TimeSheetDataUtil().diffMinutesList(sortedList)
                            binding.txtTimeworked.text = minutes.toString()
                        }else{
                            binding.txtTimeworked.text = "DATA NOT CONSISTENT"
                        }

                        adapter.submitList(sortedList)
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

    private fun setListeners(viewModel: ReportViewModel, binding: FragmentReportBinding) {

        binding.btnSelectdate.setOnClickListener {
            openDayPicker(viewModel)
        }
    }

    private fun openDayPicker(viewModel: ReportViewModel) {

        val listener: OnDateSetListener = object : OnDateSetListener {
            override fun onDateSet(year: Int, month: Int, day: Int) {
                val timeSheetData = TimeSheetData.getDummy()
                timeSheetData.year = year
                timeSheetData.month = month
                timeSheetData.day = day
                viewModel.onPickDate(timeSheetData)
            }
        }
        val newFragment = DatePickerFragment(listener)
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        newFragment.show(fragmentManager, "datePicker")
    }

    private fun openEditFragment(id: Long) {
        view?.findNavController()
            ?.navigate(ReportFragmentDirections.actionReportFragmentToEditFragment(id))
    }

    override fun onDeleteItem(id: Long) {
        mViewModel.delete(id)
        Toast.makeText(context, "id: $id deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onClickEdit(id: Long) {
        openEditFragment(id)
    }

}