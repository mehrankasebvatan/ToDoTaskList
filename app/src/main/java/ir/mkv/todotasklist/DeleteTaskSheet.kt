package ir.mkv.todotasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DeleteTaskSheet(val title: String) : BottomSheetDialogFragment() {
    private var callback: (() -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sheet_delete_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val tvAdd: TextView = view.findViewById(R.id.tvAdd)

        txtTitle.text = title
        tvAdd.setOnClickListener {
                callback?.invoke()
        }

    }


    fun deleteTask(callback: () -> Unit) {
        this.callback = callback
    }

}