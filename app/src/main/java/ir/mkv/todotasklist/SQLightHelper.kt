package ir.mkv.todotasklist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

class SQLightHelper(
    context: Context
) : SQLiteOpenHelper(context, "task_db", null, 1) {
    private val tbl_name = "task_tbl"
    private val TAG = "SQLightHelper"

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL("CREATE TABLE $tbl_name (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, isCompleted BOOLEAN)")
        } catch (e: SQLException) {
            Log.i(TAG, "onCreate: $e")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addTask(taskModel: TaskModel): Long {
        val db: SQLiteDatabase = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("title", taskModel.title)
        contentValues.put("isCompleted", false)
        val res = db.insert(tbl_name, null, contentValues)
        db.close()
        return res
    }

    @SuppressLint("Recycle")
    fun getTasks(): MutableList<TaskModel> {
        val db: SQLiteDatabase = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $tbl_name ORDER BY title ", null)
        val taskList = mutableListOf<TaskModel>()
        if (cursor.moveToFirst()) {
            do {
                val taskModel = TaskModel(
                    id = cursor.getLong(0),
                    title = cursor.getString(1),
                    isCompleted = cursor.getInt(2) == 1
                )
                taskList.add(taskModel)

            } while (cursor.moveToNext())
        }
        db.close()
        return taskList
    }

    fun editTask(taskModel: TaskModel): Int {
        val db = writableDatabase
        val content = ContentValues()
        content.put("title", taskModel.title)
        content.put("isCompleted", taskModel.isCompleted)
        val result = db.update(tbl_name, content, "id = ?", arrayOf("${taskModel.id}"))
        db.close()
        return result
    }

    fun deleteTask(taskModel: TaskModel): Int {
        val db = writableDatabase
        val res = db.delete(tbl_name, "id = ?", arrayOf("${taskModel.id}"))
        db.close()
        return res
    }


}