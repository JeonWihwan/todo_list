package com.goat.todolistapp.database

import androidx.room.*
import com.goat.todolistapp.model.TodoInfo


@Dao
interface TodoDao {

    // database table에 삽입(추가)
    @Insert
    fun insertTodoData(todoInfo: TodoInfo) // Entity를 넣어준다.

    // database table에 기존에 존재하는 Data를 수정
    @Update
    fun updateTodoData(todoInfo: TodoInfo)

    // database table에 기존에 존재하는 Data를 삭제
    @Delete
    fun deleteTodoData(todoInfo: TodoInfo)

    // database table에 전체 데이터를 가지고 옴. (조회)
    @Query("SELECT * FROM TodoInfo ORDER BY todoDate")
    fun getAllReadData(): List<TodoInfo>



}