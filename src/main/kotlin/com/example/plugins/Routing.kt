package com.example.plugins

import com.example.entities.ToDoDraft
import com.example.repository.InMemoryToDoRepo
import com.example.repository.ToDoRepository
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {

        val repository:ToDoRepository= InMemoryToDoRepo()

        get("/") {
            call.respondText("Hello ToDo List!")
        }

        get("/todos"){
            call.respond(repository.getAllToDos())
        }
        get("/todos/{id}"){
            val id=call.parameters["id"]?.toIntOrNull()
            if(id==null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number"
                )
                return@get
            }
            val todo=repository.getToDo(id)
            if(todo==null){
                call.respond(
                    HttpStatusCode.NotFound,
                    "ToDo with id:$id Not Found")
            }else{
                call.respond(todo)
            }
        }
        post("/todos"){
            val toDoDraft= call.receive<ToDoDraft>()
            val todo = repository.addToDo(toDoDraft)
            call.respond(todo)
        }
        put("/todos/{id}"){
            val toDoDraft= call.receive<ToDoDraft>()
            val id = call.parameters["id"]?.toIntOrNull()
            if(id==null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number"
                )
                return@put
            }
            val updated = repository.updateToDo(id,toDoDraft)
            if(updated){
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond(
                    HttpStatusCode.NotFound,
                    "ToDo with id:$id Not Found"
                )
            }
        }
        delete("/todos/{id}"){
            val id=call.parameters["id"]?.toIntOrNull()
            if(id==null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number"
                )
                return@delete
            }
            val removed=repository.removeToDO(id)
            if(!removed){
                call.respond(
                    HttpStatusCode.NotFound,
                    "ToDo with id:$id Not Found")
            }else{
                call.respond(HttpStatusCode.OK)
            }
        }

    }
}
