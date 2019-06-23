package jp.ac.tsukuba.cs.iplab.todolist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), TaskFragment.OnFragmentInteractionListener,
    DoneFragment.OnFragmentInteractionListener {
    private val todoPagerAdapter = TodoPagerAdapter(supportFragmentManager)

    companion object {
        private val EDIT_REQUEST_CODE = 0
        val ADD_TASK_CODE = 1
        val UPDATE_TASK_CODE = 2
    }

    override fun onTaskFragmentInteraction(task: TaskModel?) {
        openEditor(task)
    }

    override fun onDoneFragmentInteraction(task: TaskModel) {
        openEditor(task)
    }

    // 引数にTaskModelオブジェクトを受け取って詳細画面に遷移する関数
    fun openEditor(task: TaskModel?) {
        val intent = Intent(this, TaskEditorActivity::class.java)
        intent.putExtra(TaskEditorActivity.PUT_KEY, task)
        startActivityForResult(intent, EDIT_REQUEST_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        todo_pager.adapter = todoPagerAdapter
        todo_tab.setupWithViewPager(todo_pager)

        // fabが押されたら詳細画面にnullを渡して遷移
        add_task_fab.setOnClickListener {
            openEditor(null)
        }
    }

    // 編集画面から返ってきたTaskModelオブジェクトでTaskListHolderを更新して各リストを持つfragmentを更新する
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_REQUEST_CODE) {
            when (resultCode) {
                ADD_TASK_CODE -> { // タスクが追加された場合の処理
                    val newTask: TaskModel = data!!.getSerializableExtra(TaskEditorActivity.PUT_KEY) as TaskModel
                    TaskListHolder.addTask(newTask) // 共有するタスクリストにタスクを追加
                    todoPagerAdapter.refresh() // ViewPager下のfragmentを更新
                }
                UPDATE_TASK_CODE -> { // タスクが更新された場合の処理
                    try {
                        val updateTask: TaskModel = data!!.getSerializableExtra(TaskEditorActivity.PUT_KEY) as TaskModel
                        TaskListHolder.updateTask(updateTask)
                        todoPagerAdapter.refresh()
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, "更新に失敗しました.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

class TodoPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    val titles = arrayOf("TASK", "DONE")
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(p0: Int): Fragment {
        when (p0) {
            0 -> return TaskFragment()
            else -> return DoneFragment()
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun refresh() {
        notifyDataSetChanged()
    }
}
