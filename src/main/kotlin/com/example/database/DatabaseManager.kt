package com.example.database

import com.example.entities.ToDo
import com.example.entities.ToDoDraft
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {
    //config
    private val hostname="localhost"
    private val databaseName= "ktor-todo"
    private val username= "root"
    private val password= "password"

    private val ktormDatabase: Database

    init{
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase=Database.connect(jdbcUrl)
    }

    fun getAllTodos(): List<DBTodoEntity> {
        return ktormDatabase.sequenceOf(DBTodoTable).toList()
    }

    fun getToDo(id:Int):DBTodoEntity?{
        return ktormDatabase.sequenceOf(DBTodoTable)
            .firstOrNull{it.id eq id}
    }

    fun addToDo(draft: ToDoDraft):ToDo{
        val id= ktormDatabase.insertAndGenerateKey(DBTodoTable){
            set(DBTodoTable.title,draft.title)
            set(DBTodoTable.done,draft.done)
        } as Int

        return ToDo(id,draft.title,draft.done)
    }

    fun updateToDo(id:Int,draft: ToDoDraft):Boolean{
        val updatedRows= ktormDatabase.update(DBTodoTable){
            set(it.title,draft.title)
            set(it.done,draft.done)
            where{
                it.id eq id
            }
        }
        return updatedRows>0
    }

    fun removeToDo(id: Int): Boolean{
        val deletedRows= ktormDatabase.delete(DBTodoTable){it.id eq id}
        return deletedRows>0
    }

}