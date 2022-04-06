package com.example.odemeuygulamasi_sinav2.DAL

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.odemeuygulamasi_sinav2.Model.Odeme
import com.example.odemeuygulamasi_sinav2.Model.OdemeTipi
import com.example.odemeuygulamasi_sinav2.Model.Periyot

class Operation(context:Context) {

    var OdemeDatabase : SQLiteDatabase?= null
    var dbOpenHelper : DatabaseOpenHelper

    init {
        dbOpenHelper = DatabaseOpenHelper(context,"OdemeDb",null,1)
    }

    fun open() {
        OdemeDatabase = dbOpenHelper.writableDatabase  // yazmak üzere aç demek, hem yazıp hem okuyabilirsin. readableDatabase ise sadece okuyaiblir.
    }

    fun close() {
        if(OdemeDatabase != null && OdemeDatabase!!.isOpen) {
            OdemeDatabase!!.close()
        }
    }


    fun odemeTipiEkle(odemeTipi: OdemeTipi) : Long{

        val cv = ContentValues()
        cv.put("Baslik",odemeTipi.odemeBaslik)
        cv.put("OdemePeriyodu",odemeTipi.odemePeriyodu.toString())
        cv.put("PeriyotGunu",odemeTipi.periyotGunu)

        open()
        val etkilenenKayit  = OdemeDatabase!!.insert("OdemeTipi",null,cv)
        close()

        return etkilenenKayit
    }

    fun odemeEkle(odeme: Odeme, odemeTipiClassId:Int) : Long {
        val cv = ContentValues()
        cv.put("OdemeTutari",odeme.odemeTutari)
        cv.put("OdemeTarihi",odeme.odemeTarihi)
        cv.put("OdemeTipiFK", odemeTipiClassId)

        open()
        val etkilenenKayit  = OdemeDatabase!!.insert("Odeme",null,cv)
        close()

        return etkilenenKayit

    }

    private fun tumYapilacaklarGetir() : Cursor { // bu dışarıdan çağrılmayacak
        val sorgu = "Select * from OdemeTipi"

        return OdemeDatabase!!.rawQuery(sorgu,null)
    }
    private fun tumOdemeleriGetir() : Cursor { // bu dışarıdan çağrılmayacak
        val sorgu = "Select * from Odeme"

        return OdemeDatabase!!.rawQuery(sorgu,null)
    }

    @SuppressLint("Range")
    fun yapilacaklarGetir() :ArrayList<OdemeTipi> {
        val otList = ArrayList<OdemeTipi>()
        var odemeTipi : OdemeTipi

        open()
        var c : Cursor = tumYapilacaklarGetir()


        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var op = c.getString(c.getColumnIndex("OdemePeriyodu"))
                var periyot :Periyot
                if(op == Periyot.YILLIK.toString()){
                    periyot = Periyot.YILLIK
                }else if(op == Periyot.AYLIK.toString()){
                    periyot = Periyot.AYLIK
                }else{
                    periyot = Periyot.HAFTALIK
                }
                var pg = c.getInt(c.getColumnIndex("PeriyotGunu"))
                odemeTipi = OdemeTipi(c.getString(c.getColumnIndex("Baslik")), periyot, pg, id)
                otList.add(odemeTipi)

            }while (c.moveToNext())

        }

        close()

        return otList
    }
    fun odemeSil(id:Int) {

        open()
        OdemeDatabase!!.delete("Odeme","Id = ?", arrayOf(id.toString()))
        close()
    }

    fun odemeleriSil(fkId:Int){

        open()
        OdemeDatabase!!.delete("Odeme","OdemeTipiFK = ?", arrayOf(fkId.toString()))
        close()
    }
    fun odemeTipiSil(id:Int){

        open()
        OdemeDatabase!!.delete("OdemeTipi","Id = ?", arrayOf(id.toString()))
        close()

    }

    @SuppressLint("Range")
    fun odemeleriGetir() :ArrayList<Odeme> {
        val oList = ArrayList<Odeme>()
        var odeme : Odeme

        open()
        var c : Cursor = tumOdemeleriGetir()


        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var ot = c.getFloat(c.getColumnIndex("OdemeTutari"))
                var oTarihi = c.getString(c.getColumnIndex("OdemeTarihi"))
                odeme = Odeme(ot,oTarihi,id)
                oList.add(odeme)

            }while (c.moveToNext())

        }

        close()

        return oList
    }



    fun odemeTipiGuncelle(odemeTipi: OdemeTipi, odemeId:Int){
        //odemeTipi aslında yeni güncellenen verinin girdileri.
        val cv = ContentValues()

        cv.put("Baslik",odemeTipi.odemeBaslik)
        cv.put("OdemePeriyodu",odemeTipi.odemePeriyodu.toString())
        cv.put("PeriyotGunu",odemeTipi.periyotGunu)


        open()
        OdemeDatabase!!.update("OdemeTipi",cv,"Id = ?", arrayOf(odemeId.toString()))
        close()

    }



    fun odemeTipiIdyeGoreGetir(id:Int): Cursor {
        val sorgu = "Select * from OdemeTipi Where Id = ?"
        return OdemeDatabase!!.rawQuery(sorgu,arrayOf(id.toString()))
    }

    @SuppressLint("Range")
    fun odemeTipiniIdyeGoreGetir(id:Int) :OdemeTipi  {

        var odemeTipi :OdemeTipi
        open()
        var c : Cursor = odemeTipiIdyeGoreGetir(id)

        if(c.moveToFirst()){

                    var id  = c.getInt(0) // bu 0 aslında sütun demek. gelen verinin birinci sütununu ver diyoruz.
                    var baslik = c.getString(c.getColumnIndex("Baslik")) // bu da kolon isime göre indexi yakalar. bu daha güvenli çünkü bir kolon güncelenip kaldırılırsa, index verdiğin zaman artık o kolonda başka bir veri olacak. ama bu yöntemle problem olmadan kolonu alabiliyoruz.
                    var odemePeriyodu = c.getString(c.getColumnIndex("OdemePeriyodu"))
                    var periyotGunu = c.getInt(c.getColumnIndex("PeriyotGunu"))
                    var odemePeriyoduP : Periyot

                    if(odemePeriyodu == Periyot.HAFTALIK.toString()){
                        odemePeriyoduP = Periyot.HAFTALIK
                    }else if(odemePeriyodu == Periyot.AYLIK.toString()){
                        odemePeriyoduP = Periyot.AYLIK
                    }else{
                        odemePeriyoduP = Periyot.YILLIK
                    }
                    odemeTipi = OdemeTipi(baslik, odemePeriyoduP ,periyotGunu,id)
        }else{
            odemeTipi = OdemeTipi("as",Periyot.YILLIK,5,-1)
        }

        close()

        return odemeTipi
    }



    private fun tumOdemeleriForeignKeyeGetir(id:Int) : Cursor { // bu dışarıdan çağrılmayacak
        val sorgu = "Select * from Odeme Where OdemeTipiFK = ?"
        //val sorgu = "Select * from Yapilacak Where Baslik = "+baslik // bu da aynı üstteki ile ama üsttekini yaptıysan rawQueryde parametreyi vermelisin.
        // bir üstteki satır daha performanslı ama kod okunabilirliği açısından diğerini tercih ediyoruz.
        return OdemeDatabase!!.rawQuery(sorgu,arrayOf(id.toString()))
    }
    @SuppressLint("Range")
    fun odemeleriGetirFK(id:Int) :ArrayList<Odeme> {
        val oList = ArrayList<Odeme>()
        var odeme :Odeme

        open()
        var c : Cursor = tumOdemeleriForeignKeyeGetir(id)


        if(c.moveToFirst()){
            do{

                var id  = c.getInt(0) // bu 0 aslında sütun demek. gelen verinin birinci sütununu ver diyoruz.
                var tutar = c.getFloat(c.getColumnIndex("OdemeTutari")) // bu da kolon isime göre indexi yakalar. bu daha güvenli çünkü bir kolon güncelenip kaldırılırsa, index verdiğin zaman artık o kolonda başka bir veri olacak. ama bu yöntemle problem olmadan kolonu alabiliyoruz.
                var tarih = c.getString(c.getColumnIndex("OdemeTarihi"))
                odeme = Odeme(tutar,tarih,id)
                oList.add(odeme)
            }while (c.moveToNext()) // ilk defasında koşulsuz giriyor, sonra while eğer bir sonraki adımda veri varsa devam ediyor, yoksa etmiyor.
            // while ile deolur, foreach de olur ama bu en uygunu.

        }

        close()


        return oList
    }
}