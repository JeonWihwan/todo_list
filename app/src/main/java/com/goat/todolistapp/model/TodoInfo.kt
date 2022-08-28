package com.goat.todolistapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TodoInfo {
    var todoContent: String = "" // 메모 내용
    var todoDate: String = "" // 메모 일자

    @PrimaryKey(autoGenerate = true) // 자동으로 id 생성 true
    var id: Int = 0 // 자동으로 1씩 증가
}