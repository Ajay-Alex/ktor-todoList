package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello ToDo List!")
        }

        get("/todos"){

        }
        get("/todos/{id}"){
            val id=call.parameters["id"]
            call.respondText("ToDo List Details for item id: $id")
        }
        post("/todos"){

        }
        put("/todos/{id}"){

        }
        delete("/todos/{id}"){

        }

    }
}
