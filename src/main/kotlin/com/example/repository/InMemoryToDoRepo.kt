package com.example.repository

import com.example.entities.ToDo
import com.example.entities.ToDoDraft

class InMemoryToDoRepo:ToDoRepository {
    private val todos= mutableListOf<ToDo>(
        ToDo(1,"todo1",true),
        ToDo(2,"todo2",false),
        ToDo(3,"todo3",false)
    )

    override fun getAllToDos(): List<ToDo> {
        return todos
    }

    override fun getToDo(id: Int): ToDo? {
        return todos.firstOrNull{it.id==id}
    }

    override fun addToDo(draft: ToDoDraft): ToDo {
        val todo=ToDo(todos.size+1, draft.title, draft.done )
        todos.add(todo)
        return todo
    }

    override fun removeToDO(id: Int): Boolean {
        return todos.removeIf { it.id == id }
    }

    override fun updateToDo(id: Int, draft: ToDoDraft): Boolean {
        val todo=todos.firstOrNull{it.id==id}
            ?:return false
        todo.title=draft.title
        todo.done=draft.done
        return true
    }

}