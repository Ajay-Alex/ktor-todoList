package com.example.repository

import com.example.database.DatabaseManager
import com.example.entities.ToDo
import com.example.entities.ToDoDraft

class MySQLToDoRepository:ToDoRepository {

    private val database = DatabaseManager()

    override fun getAllToDos(): List<ToDo> {
        return database.getAllTodos()
            .map { ToDo(it.id, it.title, it.done) }
    }

    override fun getToDo(id: Int): ToDo? {
        return database.getToDo(id)
            ?.let{ ToDo(it.id,it.title,it.done) }

    }

    override fun addToDo(draft: ToDoDraft): ToDo {
        return database.addToDo(draft)
    }

    override fun removeToDo(id: Int): Boolean {
        return database.removeToDo(id)
    }

    override fun updateToDo(id: Int, draft: ToDoDraft): Boolean {
        return database.updateToDo(id,draft)
    }
}