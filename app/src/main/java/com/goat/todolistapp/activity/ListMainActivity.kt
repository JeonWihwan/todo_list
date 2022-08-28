package com.goat.todolistapp.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.goat.todolistapp.adapter.TodoAdapter
import com.goat.todolistapp.database.TodoDatabase
import com.goat.todolistapp.databinding.ActivityListMainBinding
import com.goat.todolistapp.databinding.DialogEditBinding
import com.goat.todolistapp.model.TodoInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var roomDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // 어댑터 인스턴스 생성
        todoAdapter = TodoAdapter()

        // 리사이클러뷰에 어댑터 세팅
        binding.rvTodo.adapter = todoAdapter

        // 룸 데이터베이스 초기화
        roomDatabase = TodoDatabase.getInstance(applicationContext)!! // !! : null이 아닐것이다

        // 전체 데이터 load (비동기)
        CoroutineScope(Dispatchers.IO).launch {
            val lstTodo = roomDatabase.todoDao().getAllReadData() as ArrayList<TodoInfo>
            for(todoItem in lstTodo) {
                todoAdapter.addListItem(todoItem)
            }
            // UI Thread에서 처리해야됨
            runOnUiThread{
                todoAdapter.notifyDataSetChanged()
            }
        }



        // 작성하기 버튼 클릭
        binding.btnWrite.setOnClickListener {
            // val binding = ListItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context), binding.root, false)

            AlertDialog.Builder(this)
                .setTitle("To-Do 남기기")
                .setView(bindingDialog.root)
                .setPositiveButton("작성완료", DialogInterface.OnClickListener { dialogInterface, i ->
                    val todoItem = TodoInfo()
                    todoItem.todoContent = bindingDialog.etMemo.text.toString()
                    todoItem.todoDate = SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(Date())
                    todoAdapter.addListItem(todoItem)

                    // DB에 삽입
                    CoroutineScope(Dispatchers.IO).launch {
                        roomDatabase.todoDao().insertTodoData(todoItem)
                        runOnUiThread{
                            todoAdapter.notifyDataSetChanged() // 리스트 새로고침
                        }
                    }
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->

                })
                .show()
        }
    }
}