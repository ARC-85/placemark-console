package org.setu.placemark.console.main

import mu.KotlinLogging
import org.setu.placemark.console.models.PlacemarkModel

private val logger = KotlinLogging.logger {}
var placemark = PlacemarkModel()
val placemarks = ArrayList<PlacemarkModel>()

fun main(args: Array<String>) {
    logger.info { "Launching Placemark Console App" }
    println("Placemark Kotlin App Version 1.0")

    var input: Int

    do {
        input = menu()
        when (input) {
            1 -> addPlacemark()
            2 -> updatePlacemark()
            3 -> listAllPlacemarks()
            4 -> searchPlacemarks()
            -99 -> dummyData()
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)
    logger.info { "Shutting Down Placemark Console App" }
}

fun menu(): Int {

    var option: Int
    var input: String? = null

    println("Main Menu")
    println(" 1. Add Placemark")
    println(" 2. Update Placemark")
    println(" 3. List All Placemarks")
    println(" 4. Search Placemarks")
    println("-1. Exit")
    println()
    print("Enter an integer : ")
    input = readLine()!!
    option = if (input.toIntOrNull() != null && !input.isEmpty())
        input.toInt()
    else
        -9
    return option
}

fun addPlacemark(){
    println("Add Placemark")
    println()
    print("Enter a Title : ")
    placemark.title = readLine()!!
    print("Enter a Description : ")
    placemark.description = readLine()!!

    if (placemark.title.isNotEmpty() && placemark.description.isNotEmpty()) {
        placemarks.add(placemark.copy())
        placemark.id++
        logger.info("Placemark Added : [ $placemark ]")
    }
    else
        logger.info("Placemark Not Added")
}

fun updatePlacemark() {
    println("Update Placemark")
    println()
    listAllPlacemarks()
    var searchId = getId()
    val searchedPlacemark = search(searchId)

    if(searchedPlacemark != null) {
        var counter = 1
        while (counter == 1) {
            println("Enter a new Title for \"" + searchedPlacemark.title + "\": ")
            var titleAnswer = readLine()!!
            if(titleAnswer != "") {
                searchedPlacemark.title = titleAnswer
                counter = 0
            } else {
                println("You didn't enter a title")
                println()
                counter = 1
            }
        }

        while (counter == 0) {
            println("Enter a new Description for \"" + searchedPlacemark.description + "\": ")
            var descriptionAnswer = readLine()!!
            if(descriptionAnswer != "") {
                searchedPlacemark.description = descriptionAnswer
                counter = 2
            } else {
                println("You didn't enter a description")
                println()
                counter = 0
            }
        }
        println("You updated \"" + searchedPlacemark.title + "\" for title and \"" + searchedPlacemark.description + "\" for a description")
    }
    else
        println("Placemark Not Updated...")

}

fun listAllPlacemarks() {
    println("List All Placemarks")
    println()
    placemarks.forEach { logger.info("${it}") }
}

fun getId() : Long {
    var strId : String? // String to hold user input
    var searchId : Long // Long to hold converted id
    print("Enter id to Search/Update : ")
    strId = readLine()!!
    searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())
        strId.toLong()
    else
        -9
    return searchId
}

fun search(id: Long) : PlacemarkModel? {
    var foundPlacemark: PlacemarkModel? = placemarks.find { p -> p.id == id }
    return foundPlacemark
}

fun searchPlacemarks() {
    var searchId = getId()
    var searchedPlacemark = search(searchId)
    if(searchedPlacemark != null)
        println("Placemark Details [ $searchedPlacemark ]")
    else
        println("Placemark Not Found...")
}

fun dummyData() {
    placemarks.add(PlacemarkModel(1, "New York New York", "So Good They Named It Twice"))
    placemarks.add(PlacemarkModel(2, "Ring of Kerry", "Some place in the Kingdom"))
    placemarks.add(PlacemarkModel(3, "Waterford City", "You get great Blaas Here!!"))
}