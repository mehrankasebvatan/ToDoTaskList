package ir.mkv.todotasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.mkv.todotasklist.databinding.ItemTaskListBinding

class MainAdapter(
    private var list: MutableList<TaskModel>,
    private var callback: (TaskModel) -> Unit
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
            txtTitle.text = data.title
            cbComplete.isChecked = data.isCompleted
        }
    }
}