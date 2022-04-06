package com.example.odemeuygulamasi_sinav2.Model



//var odemeList = mutableListOf<OdemeTipi>()

data class OdemeTipi (var odemeBaslik :String,
                      var odemePeriyodu : Periyot?,
                      var periyotGunu : Int?,
                      var id:Int ?= null) {



}