package com.example.beskorsravniosago.network

data class Coefficients (
    val title: String,
    val headerValue: String,
    val value: String,
    val name: String,
    val detailText: String,
)

data class Factors (
    val factors: List<Coefficients>
)

var coefficients = Factors(listOf(
    Coefficients(
        "БТ",
        "БТ",
        "2 754 - 4 432 Р",
        "базовый тариф",
        "Устанавливает страховая" + "\n" + "компания"
    ),
    Coefficients(
        "КМ",
        "КМ",
        "0,6 - 1,6",
        "коэфф. мощности",
        "Чем мощнее автомобиль," + "\n" + "тем дороже сраховой полис"
    ),
    Coefficients(
        "КТ",
        "КТ",
        "0,64 - 1,99",
        "территориальный коэфф.",
        "Определяется по прописке" + "\n" + "собственника автомобиля"
    ),
    Coefficients(
        "КБМ",
        "КБМ",
        "0,5 - 2,45",
        "коэфф. безаварифности",
        "Учитывается только самый" + "\n" + "высокий коэффициент из всех" + "\n" + "водителей"
    ),
    Coefficients(
        "КВС",
        "КВС",
        "0,90 - 1,93",
        "коэфф. возраст/стаж",
        "Чем больше возраст и стаж" + "\n" + "у вписанного в полис водителя," + "\n" + "тем дешевле будет полис"
    ),
    Coefficients(
        "КО",
        "КО",
        "1 или 1,99",
        "коэфф. ограничений",
        "Полис с ограниченным списком" + "\n" + "водителей будет стоить дешевле"
    )))