package jp.ac.tsukuba.cs.iplab.todolist

import java.io.Serializable
import java.util.*

// タスクのモデル
class TaskModel(
    val title: String,
    var contents: String? = null,
    var date: String? = null
) : Serializable {
    val uuid = UUID.randomUUID()
    var isDone: Boolean = false
}

// タスク一覧を保持するオブジェクト
object TaskListHolder {
    private val taskList: MutableList<TaskModel> = mutableListOf()

    fun addTask(task: TaskModel) {
        taskList.add(task)
    }

    // タスク一覧からuuidが一致したタスクを更新する
    fun updateTask(task: TaskModel) {
        // MutableListのbinarySearchByだとインデックスの検索に失敗することがあるため素朴に実装
        // 検証時点では問題なし
        run outer@{
            taskList.forEachIndexed { index, eTask ->
                if (eTask.uuid == task.uuid) {
                    taskList[index] = task
                    return@outer
                }
            }
        }
    }

    // 完了前のタスク一覧を返す
    fun getWIPList(): List<TaskModel> {
        return taskList.filter { it.isDone == false }
    }

    // 完了済みのタスク一覧を返す
    fun getDoneList(): List<TaskModel> {
        return taskList.filter { it.isDone == true }
    }
}