package com.goat.todolistapp.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AlertDialogLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import com.goat.todolistapp.database.TodoDatabase
import com.goat.todolistapp.databinding.DialogEditBinding
import com.goat.todolistapp.databinding.ListItemTodoBinding
import com.goat.todolistapp.model.TodoInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TodoAdapter: RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var lstTodo : ArrayList<TodoInfo> = ArrayList()
    private lateinit var roomDatabase: TodoDatabase

    init {
        // 샘플 리스트 아이템 인스턴스 생성
        /*
        val todoItem = TodoInfo()
        todoItem.todoContent = "컴퓨터 사용시간 줄이기"
        todoItem.todoDate = "2022-06-01 22:23"
        lstTodo.add(todoItem)
        */

    }

    fun addListItem(todoItem: TodoInfo) {
        lstTodo.add(0, todoItem)
    }

    inner class TodoViewHolder(private val binding: ListItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todoItem: TodoInfo) {
            // 리스트 뷰 데이터를 UI에 연동
            binding.tvContent.setText(todoItem.todoContent)
            binding.tvDate.setText(todoItem.todoDate)

            // 리스트 삭제 버튼 클릭 연동
            binding.btnRemove.setOnClickListener {
                // 쓰레기통 이미지 클릭 시 내부 로직 수행

                AlertDialog.Builder(binding.root.context)
                    .setTitle("[주의]")
                    .setMessage("제거하시겠습니까?")
                    .setPositiveButton("삭제", DialogInterface.OnClickListener { dialogInterface, i ->
                        CoroutineScope(Dispatchers.IO).launch {
                            val innerLstTodo = roomDatabase.todoDao().getAllReadData()
                            for (item in innerLstTodo) {
                                if(item.todoContent == todoItem.todoContent && item.todoDate == todoItem.todoDate) {
                                    // delete database item
                                    roomDatabase.todoDao().deleteTodoData(item)
                                }
                            }

                            lstTodo.remove(todoItem)

                            // UI Thread 실행시키기
                            (binding.root.context as Activity).runOnUiThread{
                                notifyDataSetChanged() // 리스트 새로고침
                                // 토스트 팝업 메세지
                                Toast.makeText(binding.root.context, "제거되었습니다.", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }


                        

                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->

                    })
                    .show()
            }

            // 수정
            binding.root.setOnClickListener {
                val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context), binding.root, false)

                // 기존 작성된 데이터 보여주기
                bindingDialog.etMemo.setText(todoItem.todoContent)

                AlertDialog.Builder(binding.root.context)
                    .setTitle("To-Do 남기기")
                    .setView(bindingDialog.root)
                    .setPositiveButton("수정완료", DialogInterface.OnClickListener { dialogInterface, i ->
                        CoroutineScope(Dispatchers.IO).launch {
                            val innerLstTodo = roomDatabase.todoDao().getAllReadData()
                            for (item in innerLstTodo) {
                                if(item.todoContent == todoItem.todoContent && item.todoDate == todoItem.todoDate) {
                                    item.todoContent = bindingDialog.etMemo.text.toString()
                                    item.todoDate = SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(Date())
                                    roomDatabase.todoDao().updateTodoData(item)
                                }
                            }
                            //val todoItem = TodoInfo()
                            todoItem.todoContent = bindingDialog.etMemo.text.toString()
                            todoItem.todoDate = SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(Date())

                            // Array List 수정
                            lstTodo.set(adapterPosition, todoItem)
                            // UI Thread 실행시키기
                            (binding.root.context as Activity).runOnUiThread{
                                notifyDataSetChanged() // 리스트 새로고침
                            }
                        }


                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->

                    })
                    .show()
            }
        }

    }

    // 뷰홀더가 생성됨. (각 리스트 아이템 1개씩 구성될 때마다 이 오버라이드 메소드가 호출 됨.)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        val binding = ListItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        roomDatabase = TodoDatabase.getInstance(binding.root.context)!! // !! : null이 아닐것이다
        return TodoViewHolder(binding)
    }

    // 뷰홀더가 바인딩(결합) 이루어질 때 해줘야 될 처리들을 구현
    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        holder.bind(lstTodo[position])
    }

    // 리스트 총 갯수
    override fun getItemCount(): Int {
        return lstTodo.size
    }

}