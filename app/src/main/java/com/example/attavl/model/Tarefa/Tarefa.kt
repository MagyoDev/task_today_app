package com.example.attavl.model.Tarefa

import java.util.*

class Tarefa(val nome: String, val detalhes: String, val createdDate: Date, val pzoFinal: Date, status: Double) {

    var status = 0.0
        get() = field
        set(value) {
            field = value
        }

}
