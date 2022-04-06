package com.example.odemeuygulamasi_sinav2.View

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.odemeuygulamasi_sinav2.BLL.OdemeLogic
import com.example.odemeuygulamasi_sinav2.Model.Odeme
import com.example.odemeuygulamasi_sinav2.R
import com.example.odemeuygulamasi_sinav2.databinding.ActivityOdemeTutariEkleBinding
import java.time.LocalDate
import java.util.*

class OdemeTutariEkle : AppCompatActivity() {
    lateinit var binding: ActivityOdemeTutariEkleBinding
    lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOdemeTutariEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        btnTarihSecListener()
        btnClicklistener()

    }


    fun btnClicklistener() {
        binding.btnOdemeTutariKaydet.setOnClickListener {
            if(this::date.isInitialized){
                var odemeTutari = binding.ptOdemeTutari.text.toString().toFloat()
                var odemeTipiClassId = intent.getIntExtra("odemeTipiId",-1)
                var odeme = Odeme(odemeTutari,date) //BUNU VERİTABANINA YAZMAMIZ LAZIM ARTIK.
                OdemeLogic.ekle(this,odeme,odemeTipiClassId)//ŞİMDİ DE UPDATE YAPMAMIZ LAZIM, HANGİ ÖDEME TİPİNİN ÖDEME DEĞİŞKENİ OLDUGUNU
                var intent = Intent()
                setResult(RESULT_OK,intent)
                finish()
            }else{

                var odemeTutari = binding.ptOdemeTutari.text.toString().toFloat()
                var odemeTipiClassId = intent.getIntExtra("odemeTipiId",-1)
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                date = "$day-$month-$year"
                var odeme = Odeme(odemeTutari,date) //BUNU VERİTABANINA YAZMAMIZ LAZIM ARTIK.
                OdemeLogic.ekle(this,odeme,odemeTipiClassId)//ŞİMDİ DE UPDATE YAPMAMIZ LAZIM, HANGİ ÖDEME TİPİNİN ÖDEME DEĞİŞKENİ OLDUGUNU
                var intent = Intent()
                setResult(RESULT_OK,intent)
                finish()
            }




        }
    }

    private fun adbOlustur() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Tarih Seçilmedi")
        alertDialogBuilder.setMessage("Devam etmek için ödeme tarihi seçiniz.")

        alertDialogBuilder.setPositiveButton("Tamam") { dialog, which ->

        }
        alertDialogBuilder.show()
    }


    fun btnTarihSecListener() {
        binding.btnTarihSec.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
                Toast.makeText(this, "$y-${m + 1}-$d",Toast.LENGTH_LONG).show()
                date = "$d-${m + 1}-$y"

            },year,month,day)
            dpd.datePicker.maxDate = Calendar.getInstance().timeInMillis
            dpd.show()
            }

    }




}