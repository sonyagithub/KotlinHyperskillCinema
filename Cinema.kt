fun charFromInt(n: Int): Char {
    return (n + 48).toChar()
}

fun initCinema(rows: Int, seats: Int): Array<CharArray> {
    var cinema = emptyArray<CharArray>()
    var r: CharArray = charArrayOf()
    for(i in 0..seats) {
        r += charFromInt(i)
    }
    cinema += r

    for(i in 1..rows) {
        r = charArrayOf()
        for(j in 0..seats) {
            r += if (j == 0) charFromInt(i) else 'S'
        }
        cinema += r
    }
    return cinema
}

fun printCinema(cinema: Array<CharArray>, rows: Int, seats: Int) {
    println()
    println("Cinema:")
    for(i in 0..rows) {
        for(j in 0..seats) {
            print(if ((i == 0) && (j == 0)) {
                ' '
            } else {
                cinema[i][j]
            })
            print(" ")
        }
        println()
    }
}

fun buyTicket(cinema: Array<CharArray>, rows: Int, seats: Int) {
    while (true) {
        println()
        println("Enter a row number:")
        val row = readLine()!!.toInt()

        println("Enter a seat number in that row:")
        val seat = readLine()!!.toInt()

        println()
        try {
            if (row == 0 || seat == 0) {
                throw ArrayIndexOutOfBoundsException()
            }
            if (cinema[row][seat] == 'B') {
                   println("That ticket has already been purchased!")
            } else {
                println("Ticket price: $" + price(rows, seats, row))
                cinema[row][seat] = 'B'
                return
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            println("Wrong input!")
        }
    }
    println()
}

fun price(rows: Int, seats: Int, row: Int): Int {
    return if (rows * seats <= 60) {
        10
    } else {
        if (row <= rows / 2) {
            10
        } else {
            8
        }
    }
}

fun countTix(cin: Array<CharArray>, rows: Int, seats: Int): Int {
    var cnt = 0
    for (i in 1..rows) {
        for (j in 1..seats) {
            cnt += if (cin[i][j] == 'B') 1 else 0
        }
    }
    return cnt
}

fun income(cin: Array<CharArray>, all: Boolean, rows: Int, seats: Int): Int {
    var total: Int
    return if (all) {
        total = rows * seats
        if (total <= 60) {
            total * 10
        } else {
            (rows / 2) * seats * 10 + (rows - rows / 2) * seats * 8
        }
    } else {
        total = 0
        for (i in 1..rows) {
            for (j in 1..seats) {
                total += price(rows, seats, i) * if (cin[i][j] == 'B') 1 else 0
            }
        }
        total
    }
}

fun statistics(cin: Array<CharArray>, rows: Int, seats: Int) {
    val cnt = countTix(cin, rows, seats)
    println("\n" +
            "Number of purchased tickets: $cnt \n" +
            "Percentage: ${String.format("%.2f", 100.00 * cnt / (rows * seats))}%\n" +
            "Current income: \$${income(cin, false, rows, seats)}\n" +
            "Total income: \$${income(cin, true, rows, seats)}")
}

fun menu(): Int {
    println("1. Show the seats\n" +
            "2. Buy a ticket\n" +
            "3. Statistics\n" +
            "0. Exit")
    return readLine()!!.toInt()
}

fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seats = readLine()!!.toInt()

    val cin = initCinema(rows, seats)

    do {
        println()
        when (menu()) {
            1 -> printCinema(cin, rows, seats)
            2 -> buyTicket(cin, rows, seats)
            3 -> statistics(cin, rows, seats)
            else -> return
        }
    } while(true)
}
