package com.example.odemeuygulamasi_sinav2.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.odemeuygulamasi_sinav2.BLL.OdemeLogic
import com.example.odemeuygulamasi_sinav2.BLL.OdemeTipiLogic
import com.example.odemeuygulamasi_sinav2.Model.OdemeTipi

import com.example.odemeuygulamasi_sinav2.Model.Periyot
import com.example.odemeuygulamasi_sinav2.R
import com.example.odemeuygulamasi_sinav2.databinding.ActivityOdemeEkleBinding

class OdemeEkleActivity : AppCompatActivity() {
    lateinit var binding : ActivityOdemeEkleBinding
    //val periyotlar = resources.getStringArray(R.array.periyotlar)
    val periyotlar : Array<String> = arrayOf("HAFTALIK","AYLIK","YILLIK")
    var gelenId :Int ?= null

    lateinit var secilenPeriyot : Periyot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOdemeEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        duzenle()
        spinnerDuzenle()
        spinnerClickListener()
        btnClickListener()
        btnSilClickListener()

    }

    private fun btnSilClickListener() {
        binding.btnSil.setOnClickListener {
           adbSil()
        }
    }


    fun duzenle() {
        gelenId = intent.getIntExtra("odemeTipiId",-1)
        if(gelenId != -1) { // DÜZENLE BUTONUYLA BURAYA GELİNDİYSE
            binding.btnSil.visibility = View.VISIBLE
            var odemeTipi = OdemeTipiLogic.idyeGoreGetir(this,gelenId!!)
            binding.ptBaslik.setText(odemeTipi.odemeBaslik)
            binding.ptOdemeGunu.setText(odemeTipi.periyotGunu!!.toString())

            //ARTIK GEÇERLİ ID'YE GÖRE ODEME TİPİNE UPDATE VEYA SİLME İŞLEMİ UYGUAYACAĞIZ.
            //EĞER SİLERSEK, FK'SI GELENID OLAN TÜM ODEME'LERİ DE SİLECEĞİZ.
        }
    }

    fun btnClickListener() {
        binding.btnKaydet.setOnClickListener {
            var baslik = binding.ptBaslik.text.toString()
            var periyot = secilenPeriyot
            var pg = binding.ptOdemeGunu.text.toString()

            //var periyotGunu:Int = binding.ptOdemeGunu.text.toString().toInt()
            //Log.e("Seçilen periyot?", binding.ptOdemeGunu.text.toString().isEmpty().toString())



            if(baslik.isEmpty()){ // başlık boş olamaz
                gecersizBaslikAdb()
            }else{
                if(pg.isEmpty()){ // periyot günü boşsa
                    var odeme = OdemeTipi(baslik,periyot,null)


                    if(gelenId !=-1){//düzenle butonuyla gelinmişse
                        OdemeTipiLogic.odemeTipiUpdate(this,odeme,gelenId!!)
                    }else{
                        OdemeTipiLogic.ekle(this,odeme)
                    }

                    var intent = Intent()
                    intent.putExtra("updatedOdemeTipiId",gelenId)
                    setResult(RESULT_OK, intent)
                    finish()
                }else{
                    var periyotGunu:Int = pg.toInt()
                    //PERİYOT GÜN KONTROLÜ
                    if((periyotGunu>7 || periyotGunu<=0) && periyot == Periyot.HAFTALIK){
                        adbOlustur()
                    }else if((periyotGunu>31 || periyotGunu<=0) && periyot == Periyot.AYLIK ){
                        adbOlustur()
                    }else if((periyotGunu>365 || periyotGunu<=0) && periyot == Periyot.YILLIK ){
                        adbOlustur()
                    }else{

                        var odeme = OdemeTipi(baslik,periyot,periyotGunu)

                        if(gelenId !=-1){//düzenle butonuyla gelinmişse
                            OdemeTipiLogic.odemeTipiUpdate(this,odeme,gelenId!!)
                        }else{
                            OdemeTipiLogic.ekle(this,odeme)
                        }

                        var intent = Intent()
                        intent.putExtra("updatedOdemeTipiId",gelenId)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }



            }







        }
    }

    fun adbOlustur() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Geçersiz Periyot Günü")
        alertDialogBuilder.setMessage("Girdiğiniz periyot günü, seçtiğiniz ödeme periyodu ile uyumlu değil.")
        alertDialogBuilder.setPositiveButton("Tamam") { dialog, which -> }
        alertDialogBuilder.show()

    }
    fun gecersizBaslikAdb() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Geçersiz Başlık")
        alertDialogBuilder.setMessage("Girdiğiniz başlık geçersiz.")
        alertDialogBuilder.setPositiveButton("Tamam") { dialog, which -> }
        alertDialogBuilder.show()

    }
    fun adbSil(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Ödeme Tipini Sil")
        alertDialogBuilder.setMessage("Silmek istediğinizden emin misiniz?")
        alertDialogBuilder.setPositiveButton("Evet") { dialog, which ->

            if(gelenId != -1){ //düzenleden gelindi demektir.
                //id si gelenId olan OdemeTipi verilerini sil
                // FK si gelenId olan Odeme verilerini sil
                OdemeTipiLogic.odemeTipiSil(this,gelenId!!)
                OdemeLogic.odemeleriSil(this,gelenId!!)
                val intent = Intent()
                intent.putExtra("silindi",true)
                setResult(RESULT_OK,intent)
                finish()

            }

        }
        alertDialogBuilder.setNegativeButton("Hayır") { dialog, which ->

        }
        alertDialogBuilder.show()
    }

    fun spinnerDuzenle() {
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, periyotlar)
        binding.spPeriyot.adapter = adapter
    }

    fun spinnerClickListener() {
        binding.spPeriyot.onItemSelectedListener = object :  //languages[position]
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
               if(periyotlar[position] == Periyot.YILLIK.toString()){
                    secilenPeriyot = Periyot.YILLIK
               }else if(periyotlar[position] == Periyot.AYLIK.toString()){
                   secilenPeriyot = Periyot.AYLIK
               }else{
                   secilenPeriyot = Periyot.HAFTALIK
               }
                Toast.makeText(this@OdemeEkleActivity,secilenPeriyot.toString(),Toast.LENGTH_SHORT).show()
        }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
                secilenPeriyot = Periyot.YILLIK
            }
        }






    }
}