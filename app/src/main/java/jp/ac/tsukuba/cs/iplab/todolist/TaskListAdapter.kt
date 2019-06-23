package jp.ac.tsukuba.cs.iplab.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TaskListAdapter (val context: Context, val taskList: List<TaskModel>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = null
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        }
        else {
            view = convertView
        }
        view!!.findViewById<TextView>(R.id.task_title).text = taskList[position].title
        view!!.findViewById<TextView>(R.id.task_date).text = taskList[position].date?.let { it.toString() } ?: ""

        return view!!
    }

    override fun getItem(position: Int): TaskModel {
        return taskList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return taskList.count()
    }
}