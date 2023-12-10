package ir.mkv.todotasklist

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.mkv.todotasklist.databinding.ItemTaskListBinding


class MainAdapter(
    private var list: MutableList<TaskModel>,
    private var callback: (TaskModel, String) -> Unit
) : RecyclerView.Adapter<MainAdapter.VH>() {
    class VH(val binding: ItemTaskListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            ItemTaskListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = list[position]
        holder.binding.apply {
            txtTitle.text = " ${data.title} "
            cbComplete.isChecked = data.isCompleted
            txtDateTime.text = data.dateTime

            txtTitle.paintFlags = if (data.isCompleted)
                txtTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else 0

                imgDelete.setOnClickListener {
                    callback.invoke(data, "D")
                }

            holder.itemView.setOnClickListener {
                callback.invoke(data, "E")
            }

            cbComplete.setOnClickListener {
                callback.invoke(data, "C")
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun search(newList: MutableList<TaskModel>) {
        list = newList
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update() {
        this.notifyDataSetChanged()
    }


}