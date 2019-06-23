package jp.ac.tsukuba.cs.iplab.todolist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_task.view.*
import java.util.*

class TaskFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        val wipTaskList = TaskListHolder.getWIPList() // 完了前のタスク一覧を取得
        if (wipTaskList.size > 0) { // 完了前のタスク一覧が空でなければテキストを消してListViewを初期化
            view.tmp_text.visibility = View.GONE
            // 完了前のタスク一覧をListViewにセット
            val adapter = TaskListAdapter(
                this.context!!,
                wipTaskList
            )
            view.task_list.adapter = adapter

            // タップされたListViewの要素を更新するためにMainActivityに通知して編集画面に遷移
            view.task_list.setOnItemClickListener { parent, view, position, id ->
                onButtonPressed(adapter.getItem(position))
            }
        }

        // タスク追加を行なうためにMainActivityにnullを渡して編集画面に遷移
        /*
        view.add_task_fab.setOnClickListener {
            onButtonPressed(null)
        }
        */


        return view
    }

    fun onButtonPressed(task: TaskModel?) {
        listener?.onTaskFragmentInteraction(task)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onTaskFragmentInteraction(task: TaskModel?)
    }
}
