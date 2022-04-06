package com.example.odemeuygulamasi_sinav2.BLL

import android.content.Context
import com.example.odemeuygulamasi_sinav2.DAL.Operation
import com.example.odemeuygulamasi_sinav2.Model.Odeme
import com.example.odemeuygulamasi_sinav2.Model.OdemeTipi

class OdemeLogic {

    companion object{
        fun ekle(context: Context, odeme: Odeme, odemeTipiId:Int) {
            val ot = Operation(context)
            ot.odemeEkle(odeme, odemeTipiId)
        }

        fun fkyeGoreOdemeGetir(context: Context, id:Int) : ArrayList<Odeme>{
            val ot = Operation(context)
            return ot.odemeleriGetirFK(id)

        }

        fun odemeyiSil(context:Context, id:Int){
            val ot = Operation(context)
            ot.odemeSil(id)
        }


        fun odemeleriSil(context: Context, fkId:Int) {
            val ot = Operation(context)
            ot.odemeleriSil(fkId)
        }




    }
}