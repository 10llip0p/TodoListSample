package jp.ac.tsukuba.cs.iplab.todolist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_done.view.*


// 実装はTaskFragmentとほぼ同じなのでソースを共有するべきだった
class DoneFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_done, container, false)
        val doneTaskList = TaskListHolder.getDoneList() // 完了済みタスク一覧を取得
        if (doneTaskList.size > 0) { // 完了済みのタスク一覧が空でなければテキストを消してListViewを初期化
            view.tmp_text.visibility = View.GONE
            // 完了済みタスク一覧をListViewにセット
            val adapter = TaskListAdapter(
                this.context!!,
                doneTaskList
            )
            view.done_list.adapter = adapter

            view.done_list.setOnItemClickListener { parent, view, position, id ->
                onButtonPressed(adapter.getItem(position))
            }
        }

        return view
    }

    fun onButtonPressed(task: TaskModel) {
        listener?.onDoneFragmentInteraction(task)
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
        fun onDoneFragmentInteraction(task: TaskModel)
    }
}
