package com.example.checkpoint2

object DataRepository {
    val pessoas = mutableListOf<Pessoa>()
}

data class Pessoa(
    var nome: String,
    var sobrenome: String,
    var email: String,
    var telefone: String,
    var endereco: String
)
