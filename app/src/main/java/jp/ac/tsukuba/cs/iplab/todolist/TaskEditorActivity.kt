package jp.ac.tsukuba.cs.iplab.todolist

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_task_editor.*

class TaskEditorActivity : AppCompatActivity() {
    var isEditMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_editor)

        var task = intent.getSerializableExtra(PUT_KEY)

        if (task is TaskModel) {
            setTitle("詳細と編集")
            edit_task_title.setText(task.title, TextView.BufferType.NORMAL)
            edit_task_title.keyListener = null
            edit_task_contents.setText(task.contents, TextView.BufferType.NORMAL)
            edit_task_date.setText(task.date, TextView.BufferType.NORMAL)
            edit_done_check.isChecked = task.isDone
        }
        else {
            // 編集モードを止めて完了状態の切り替えを非表示にする
            isEditMode = false
            done_field.visibility = View.GONE
            setTitle("新規作成")
        }

        edit_save_btn.setOnClickListener {
            if (isEditMode) { // 送られてきたTaskModelオブジェクトの各プロパティを更新して返す
                task as TaskModel
                task.contents = edit_task_contents.text.toString()
                task.date = edit_task_date.text.toString() // 日付選択にはDatePickerなどを使いたかった
                task.isDone = edit_done_check.isChecked
                intent.putExtra(PUT_KEY, task)
                setResult(MainActivity.UPDATE_TASK_CODE, intent)
            }
            else { // 編集モードでない場合はTaskModelオブジェクトを新規作成して返す
                val newTask = TaskModel(edit_task_title.text.toString(), edit_task_contents.text.toString(), edit_task_date.text.toString())
                intent.putExtra(PUT_KEY, newTask)
                setResult(MainActivity.ADD_TASK_CODE, intent)
            }
            finish()
        }

        edit_cancel_btn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }

    companion object {
        val PUT_KEY = "hoge"
    }
}
