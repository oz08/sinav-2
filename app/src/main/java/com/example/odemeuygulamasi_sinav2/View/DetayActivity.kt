package com.example.odemeuygulamasi_sinav2.View


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import androidx.recyclerview.widget.GridLayoutManager
import com.example.odemeuygulamasi_sinav2.BLL.OdemeLogic
import com.example.odemeuygulamasi_sinav2.BLL.OdemeTipiLogic
import com.example.odemeuygulamasi_sinav2.Model.Odeme
import com.example.odemeuygulamasi_sinav2.Model.OdemeTipi
import com.example.odemeuygulamasi_sinav2.R
import com.example.odemeuygulamasi_sinav2.databinding.ActivityDetayBinding

class DetayActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetayBinding
    lateinit var odemeGecmisiArray : ArrayList<Odeme> // Tüm Odeme' tableındai verilerde FK Idsi, bizim listedne seçtiğimiz item'ın idsine eşit oalnları listeye atacağız.
    var gelenId : Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detayGetir()
        rvCalistir()
        btnOdemeEkleListener()
        btnDuzenleListener()

    }

    private fun btnDuzenleListener() {
        binding.btnDuzenle.setOnClickListener {
            val intent = Intent(this,OdemeEkleActivity::class.java)
            intent.putExtra("odemeTipiId",gelenId)
            resultLauncher.launch(intent)
        }
    }

    private fun btnOdemeEkleListener() {
        binding.brnYeniOdemeEkle.setOnClickListener {
            val intent = Intent(this,OdemeTutariEkle::class.java)
            intent.putExtra("odemeTipiId",gelenId)
            resultLauncher.launch(intent)

        }
    }

    fun rvCalistir() {
        binding.rvOdemeGecmisi.apply{
            gelenId = intent.getIntExtra("Id",-1)

            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = OdemeCardAdapter(applicationContext,odemeGecmisiArray, ::rvClickListener)
        }
    }

    fun detayGetir() {

            var detayBaslik = intent.getStringExtra("Baslik")
            var detayPeriyot = intent.getSerializableExtra("Periyot")

            gelenId = intent.getIntExtra("Id",-1)

            if(gelenId!= -1){
                odemeGecmisiArray = OdemeLogic.fkyeGoreOdemeGetir(this,gelenId!!)

                if(odemeGecmisiArray.size == 0){
                    odemeGecmisiArray= arrayListOf()
                }
                //Toast.makeText(this,odemeGecmisiArray[0].odemeTarihi, Toast.LENGTH_LONG).show()
                // ARRAY GELİYOR TAMAM SIKINTI YOK. ŞİMDİ BUNU RV YE BAĞLAYACAĞIZ.
            }else{
                //
            //Toast.makeText(this,"Veri Gelmedi", Toast.LENGTH_LONG).show()

            }

            binding.tvDetayBaslik.text = "Ödeme Tipi: ${detayBaslik}"
            binding.tvDetayPeriyot.text = "Ödeme Periyodu: ${detayPeriyot.toString()}"

    }


    fun rvClickListener(position:Int) {
        var secilenOdemeId = odemeGecmisiArray[position].id
        adbOlustur(secilenOdemeId!!)
        //BU SECİLEN ODEME OBJESİNİN ID'SİNE GÖRE VERİTABANINDAN SİLECEĞİZ ŞİMDİ.
        //SONRA RV Yİ YENİLEYECEĞİZ.
    }

    fun adbOlustur(id:Int) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Ödemeyi Sil")
        alertDialogBuilder.setMessage("Seçtiğiniz ödeme silinecek.")

        alertDialogBuilder.setPositiveButton("Sil") { dialog, which ->
            OdemeLogic.odemeyiSil(this,id)
            odemeGecmisiArray = OdemeLogic.fkyeGoreOdemeGetir(this,gelenId!!)
            binding.rvOdemeGecmisi.adapter = OdemeCardAdapter(applicationContext,odemeGecmisiArray, ::rvClickListener)

        }

        alertDialogBuilder.setNegativeButton("İptal") { dialog, which ->

        }
        alertDialogBuilder.show()

    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            var silindiMi = data!!.getBooleanExtra("silindi",false)
           // Toast.makeText(this,silindiMi.toString(),Toast.LENGTH_LONG).show()
            if(silindiMi){
                val intent = Intent()
                setResult(RESULT_OK,intent)
                finish()
            }


            odemeGecmisiArray = OdemeLogic.fkyeGoreOdemeGetir(this,gelenId!!)
            binding.rvOdemeGecmisi.adapter = OdemeCardAdapter(applicationContext,odemeGecmisiArray, ::rvClickListener)
            var updateOdemeTipiId:Int = data!!.getIntExtra("updatedOdemeTipiId",-5)
            //Toast.makeText(this,updateOdemeTipiId.toString(),Toast.LENGTH_LONG).show()

            if(updateOdemeTipiId != -5) {
                var odemeTipi:OdemeTipi = OdemeTipiLogic.idyeGoreGetir(this,updateOdemeTipiId)
                binding.tvDetayBaslik.text = odemeTipi.odemeBaslik
                binding.tvDetayPeriyot.text = odemeTipi.odemePeriyodu.toString()
            }

        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(RESULT_OK,intent)
        finish()
    }

}