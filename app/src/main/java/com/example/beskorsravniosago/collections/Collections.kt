package com.example.beskorsravniosago.collections

import com.example.beskorsravniosago.network.*

var coefficients = Factors(listOf(
    Factor(
        "БТ",
        "БТ",
        "2 754 - 4 432 Р",
        "базовый тариф",
        "Устанавливает страховая" + "\n" + "компания"
    ),
    Factor(
        "КМ",
        "КМ",
        "0,6 - 1,6",
        "коэфф. мощности",
        "Чем мощнее автомобиль," + "\n" + "тем дороже сраховой полис"
    ),
    Factor(
        "КТ",
        "КТ",
        "0,64 - 1,99",
        "территориальный коэфф.",
        "Определяется по прописке" + "\n" + "собственника автомобиля"
    ),
    Factor(
        "КБМ",
        "КБМ",
        "0,5 - 2,45",
        "коэфф. безаварифности",
        "Учитывается только самый" + "\n" + "высокий коэффициент из всех" + "\n" + "водителей"
    ),
    Factor(
        "КВС",
        "КВС",
        "0,90 - 1,93",
        "коэфф. возраст/стаж",
        "Чем больше возраст и стаж" + "\n" + "у вписанного в полис водителя," + "\n" + "тем дешевле будет полис"
    ),
    Factor(
        "КО",
        "КО",
        "1 или 1,99",
        "коэфф. ограничений",
        "Полис с ограниченным списком" + "\n" + "водителей будет стоить дешевле"
    )
))

val branding = Branding(
    "",
    "",
    "",
    "",
    "",
    ""
)

var offers = Offers(listOf(
    Offer(
        "",
    0.0,
    0,
        branding
    ),
    Offer(
        "",
        0.0,
        0,
        branding
    ),
    Offer(
        "",
        0.0,
        0,
        branding
    ),Offer(
        "",
        0.0,
        0,
        branding
    ),Offer(
        "",
        0.0,
        0,
        branding
    ),Offer(
        "",
        0.0,
        0,
        branding
    )),
    "")

