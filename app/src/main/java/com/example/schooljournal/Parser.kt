package com.example.schooljournal

class Parser(name: String) {

    var currentIndex = 0
    val parsingName = parsingName(name)

    private fun parsingName(name: String): String {
        when (name) {
            "Понедельник" -> {
                currentIndex = 1
                return "пн"
            }
            "Вторник" -> {
                currentIndex = 2
                return "вт"
            }
            "Среда" -> {
                currentIndex = 3
                return "ср"
            }
            "Четверг" -> {
                currentIndex = 4
                return "чт"
            }
            "Пятница" -> {
                currentIndex = 5
                return "пт"
            }
            "Суббота" -> {
                currentIndex = 6
                return "сб"
            }
            "Воскресенье" -> {
                currentIndex = 0
                return "вс"
            }
        }
        return "null"
    }
}