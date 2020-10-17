package br.com.hisao.timesheet.report


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.hisao.timesheet.databinding.ListReportadapterItemBinding
import br.com.hisao.timesheet.util.getFormattedDateType
import br.com.hisao.timesheet.model.TimeSheetData


class ReportAdapter(private val reportAdapterCallback: ReportAdapterCallback) :
    ListAdapter<TimeSheetData, ViewHolder>(TimeSheetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), reportAdapterCallback)
    }
}

class ViewHolder private constructor(private val dataBinding: ListReportadapterItemBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(item: TimeSheetData, reportAdapterCallback: ReportAdapterCallback) {
        dataBinding.txtDate.text = item.getFormattedDateType()
        dataBinding.btnDelete.setOnClickListener {
            reportAdapterCallback.onClickDelete(item.id)
        }
        dataBinding.btnEdit.setOnClickListener {
            reportAdapterCallback.onClickEdit(item.id)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val viewBinding = ListReportadapterItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(viewBinding)
        }
    }
}

class TimeSheetDiffCallback : DiffUtil.ItemCallback<TimeSheetData>() {
    override fun areItemsTheSame(oldItem: TimeSheetData, newItem: TimeSheetData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimeSheetData, newItem: TimeSheetData): Boolean {
        return oldItem == newItem
    }
}

interface ReportAdapterCallback {
    fun onClickDelete(id: Long)
    fun onClickEdit(id: Long)
}