package org.setu.placemark.console.main

import mu.KotlinLogging
import org.setu.placemark.console.controllers.PlacemarkController
import org.setu.placemark.console.models.PlacemarkMemStore
import org.setu.placemark.console.models.PlacemarkModel
import org.setu.placemark.console.views.PlacemarkView

private val logger = KotlinLogging.logger {}

val placemarks = PlacemarkMemStore()
val placemarkView = PlacemarkView()
val controller = PlacemarkController()

fun main(args: Array<String>) {
    PlacemarkController().start()
}

fun addPlacemark(){
    val aPlacemark = PlacemarkModel()

    if (placemarkView.addPlacemarkData(aPlacemark))
        placemarks.create(aPlacemark)
    else
        logger.info("Placemark Not Added")
}

fun updatePlacemark() {

    placemarkView.listPlacemarks(placemarks)
    var searchId = placemarkView.getId()
    val aPlacemark = search(searchId)

    if(aPlacemark != null) {
        if(placemarkView.updatePlacemarkData(aPlacemark)) {
            placemarks.update(aPlacemark)
            placemarkView.showPlacemark(aPlacemark)
            logger.info("Placemark Updated : [ $aPlacemark ]")
        }
        else
            logger.info("Placemark Not Updated")
    }
    else
        println("Placemark Not Updated...")
}

fun searchPlacemark() {
    val aPlacemark = search(placemarkView.getId())!!
    placemarkView.showPlacemark(aPlacemark)
}


fun search(id: Long) : PlacemarkModel? {
    var foundPlacemark = placemarks.findOne(id)
    return foundPlacemark
}

fun dummyData() {
    placemarks.create(PlacemarkModel(title = "New York New York", description = "So Good They Named It Twice"))
    placemarks.create(PlacemarkModel(title= "Ring of Kerry", description = "Some place in the Kingdom"))
    placemarks.create(PlacemarkModel(title = "Waterford City", description = "You get great Blaas Here!!"))
}