package com.example.schooljournal

class Parser(nameOfDay: String) {

    var currentIndex = 0
    val parsingName = parsingName(nameOfDay)

    private fun parsingName(name: String): Int {
        when (name) {
            "Понедельник" -> {
                currentIndex = 1
                return 1
            }
            "Вторник" -> {
                currentIndex = 2
                return 2
            }
            "Среда" -> {
                currentIndex = 3
                return 3
            }
            "Четверг" -> {
                currentIndex = 4
                return 4
            }
            "Пятница" -> {
                currentIndex = 5
                return 5
            }
            "Суббота" -> {
                currentIndex = 6
                return 6
            }
            "Воскресенье" -> {
                currentIndex = 0
                return 7
            }
        }
        return 0
    }

    fun reverseParsingName(name: Int): String {
        when (name) {
            1 -> {
                return "Понедельник"
            }
            2 -> {
                return "Вторник"
            }
            3 -> {
                return "Среда"
            }
            4 -> {
                return "Четверг"
            }
            5 -> {
                return "Пятница"
            }
            6 -> {
                return "Суббота"
            }
            7 -> {
                return "Воскресенье"
            }
        }
        return "null"
    }
}