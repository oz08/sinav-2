package com.example.odemeuygulamasi_sinav2.Model

import java.util.*
import kotlin.collections.ArrayList

data class Odeme(var odemeTutari : Float, var odemeTarihi: String, var id:Int ?= null)  {
    lateinit var odemeTipi : OdemeTipi
    //lateinit var odemeTipiArray: ArrayList<OdemeTipi>


}