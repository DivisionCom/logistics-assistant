package com.example.logistics_assistant.models

data class TasksModel(
    val taskNum: String,
    val price: String,
    val dateTask: String,
    val timeTask: String,
    val status: String,
    val placeA: String,
    val placeB: String,
    val datePlaceA: String,
    val timePlaceA: String,
    val datePlaceB: String,
    val timePlaceB: String,
)