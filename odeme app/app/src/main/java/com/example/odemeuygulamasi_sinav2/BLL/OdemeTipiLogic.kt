package com.example.odemeuygulamasi_sinav2.BLL

import android.content.Context
import com.example.odemeuygulamasi_sinav2.DAL.Operation
import com.example.odemeuygulamasi_sinav2.Model.OdemeTipi

class OdemeTipiLogic {
    companion object{

        fun ekle(context: Context, odemeTipi:OdemeTipi) {
            val ot = Operation(context)
            ot.odemeTipiEkle(odemeTipi)
        }


        fun tumOdemeTipleriniGetir(context: Context): ArrayList<OdemeTipi>{
            return Operation(context).yapilacaklarGetir()
        }

        fun odemeTipiUpdate(context:Context, odemeTipi:OdemeTipi, id:Int){
            val ot = Operation(context)
            ot.odemeTipiGuncelle(odemeTipi,id)
        }

        fun idyeGoreGetir(context: Context, id:Int): OdemeTipi{
            val ot = Operation(context)
            return ot.odemeTipiniIdyeGoreGetir(id)
        }

        fun odemeTipiSil(context: Context, id:Int) {
            val ot = Operation(context)
            ot.odemeTipiSil(id)
        }
    }
}