package com.goat.todolistapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.goat.todolistapp.model.TodoInfo

// 추상 클래스
@Database(entities = [TodoInfo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao


    // 고정된 메모리로 최초에 1번만 호출하면 이후에 효율적으로 사용 가능 ( 싱글톤 디자인패턴 )
    companion object {
        private var instance: TodoDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : TodoDatabase? {
            if(instance == null) {
                synchronized(TodoDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo-database"
                    ).build()
                }
            }
            return instance
        }
    }

}