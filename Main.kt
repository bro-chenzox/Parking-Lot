package parking

class Spot(var full: Boolean = false, var regN: String = "", var color: String = "")

class ParkingLot(val size: Int) {

    var parkingLot = Array(size) { Spot(false) }

    fun create(size: Int): ParkingLot {
        println("Created a parking lot with $size spots.")
        return ParkingLot(size)
    }

    fun park(carReg: String, carColor: String) {
        /*to find the nearest free spot -> return index of first element matching predicate `{ it.full == false }`
         or `-1` if there is no one*/
        val handySpot = parkingLot.indexOfFirst { !it.full }
        if (handySpot != -1) {
            parkingLot[handySpot] = Spot(true, carReg, carColor)
            println("$carColor car parked on the spot ${handySpot + 1}.")
        } else println("Sorry, parking lot is full.")
    }

    fun leave(spot: Int) {
        if (parkingLot[spot-1].full) {
            parkingLot[spot-1] = Spot(false, "", "")
            println("Spot $spot is free.")
        } else println("There is no car in the spot $spot.")
    }

    fun status() {
        var fullSpots = 0
        parkingLot.forEachIndexed { i, it -> if (it.full) {
            println("${i+1} ${it.regN} ${it.color}")
            fullSpots++
        } }
        if (fullSpots == 0) println("Parking lot is empty.")
    }

    fun reg_by_color(carColor: String) {
        val colorUpCs = carColor.toUpperCase()
        val regList = mutableListOf<String>()
        parkingLot.forEach {
            if (it.color.toUpperCase() == colorUpCs) regList.add(it.regN)
        }
        return if (regList.isNotEmpty())println(regList.joinToString(", ", "", ""))
        else println("No cars with color $carColor were found.")
    }

    fun spot_by_color(carColor: String) {
        val colorUpCs = carColor.toUpperCase()
        val spotList = mutableListOf<Int>()
        parkingLot.forEachIndexed { i, it ->
            if (it.color.toUpperCase() == colorUpCs) spotList.add(i+1)
        }
        return if (spotList.isNotEmpty()) println(spotList.joinToString(", ", "", ""))
        else println("No cars with color $carColor were found.")
    }

    fun spot_by_reg(carReg: String) {//Not working
        val spotList = mutableListOf<Int>()
        parkingLot.forEachIndexed { i, it ->
            if (it.regN == carReg) spotList.add(i + 1)
        }
        return if (spotList.isNotEmpty()) println(spotList.joinToString(", ", "", ""))
        else println("No cars with registration number $carReg were found.")
    }
}

fun main() {
    var parkingLot = ParkingLot(0)
    val error = "Sorry, parking lot is not created."
    do {
        val input = readLine()!!.split(" ")
        val userCmd = input[0]
        if (userCmd == "create") parkingLot = parkingLot.create(input[1].toInt())
        else if (parkingLot.size != 0 || userCmd == "exit") {
            when (userCmd) {
                "park" -> parkingLot.park(input[1], input[2])
                "leave" -> parkingLot.leave(input[1].toInt())
                "status" -> parkingLot.status()
                "reg_by_color" -> parkingLot.reg_by_color(input[1])
                "spot_by_color" -> parkingLot.spot_by_color(input[1])
                "spot_by_reg" -> parkingLot.spot_by_reg(input[1])
            }
        } else println(error)
    } while (userCmd != "exit")
}