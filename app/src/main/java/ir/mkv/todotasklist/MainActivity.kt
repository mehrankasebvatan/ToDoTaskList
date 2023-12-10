package ir.mkv.todotasklist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.mkv.todotasklist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter: MainAdapter? = null
    private var db: SQLightHelper? = null
    private var list = mutableListOf<TaskModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = SQLightHelper(this)
        handleList()
        events()


    }

    private fun events() {
        binding.apply {
            btnAdd.setOnClickListener {
                val sheet = AddTaskSheet()
                sheet.addNewTask {
                    val res = db?.addTask(it)
                    if (res != -1L) {
                        sheet.dismiss()
                        handleList()
                    } else Toast.makeText(this@MainActivity, "Error!", Toast.LENGTH_SHORT).show()

                }
                sheet.show(supportFragmentManager, "add")
            }
        }
    }

    private fun handleList() {
        list = db?.getTasks() ?: mutableListOf()
        if (list.isEmpty()) {
            binding.apply {
                txtNoData.visibility = View.VISIBLE
                rvData.visibility = View.GONE
            }
        } else {
            binding.apply {
                adapter = MainAdapter(list) { data ->

                }
                rvData.adapter = adapter
                txtNoData.visibility = View.GONE
                rvData.visibility = View.VISIBLE
            }

        }
    }
}