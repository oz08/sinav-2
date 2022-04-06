package com.example.odemeuygulamasi_sinav2.View

import android.annotation.SuppressLint
import android.app.Activity
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

import com.example.odemeuygulamasi_sinav2.Model.Periyot

import com.example.odemeuygulamasi_sinav2.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var odemeListesi: ArrayList<OdemeTipi>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvCalistir()
        btnOdemeEkleListener()

    }


    private fun btnOdemeEkleListener() {
        binding.btnOdemeTipiEkle.setOnClickListener {
            val intent = Intent(this, OdemeEkleActivity::class.java)
            resultLauncher.launch(intent)

        }
    }


    fun rvCalistir() {
        binding.rvOdemeler.apply{
            odemeListesi = OdemeTipiLogic.tumOdemeTipleriniGetir(this@MainActivity)
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = CardAdapter(applicationContext,odemeListesi, ::odemeDetayListener, ::btnOdemeTutariEkle)
        }
    }



    fun odemeDetayListener(odemeTipiIndex:Int) {

        val intent = Intent(this, DetayActivity::class.java)
        var arr = OdemeTipiLogic.tumOdemeTipleriniGetir(this)
        var item = arr[odemeTipiIndex]
        intent.putExtra("Baslik",item.odemeBaslik) //Intentler ile class göndermek performansta düşüş yaratabileceğinden
        intent.putExtra("Periyot",item.odemePeriyodu) //Teker teker 4 veri göndermeyi tercih ettim.
        intent.putExtra("Id",item.id)
        //intent.putExtra("Tutar",odeme.odemeTutari)  //DAHA SONRA DATA CLASS İLE YAPILABİLİR.
        //intent.putExtra("Tarih",odeme.odemeTarihi)

        resultLauncher.launch(intent)
    }

    private fun btnOdemeTutariEkle(position:Int){
        val intent = Intent(this, OdemeTutariEkle::class.java)
        var secilenOdemeTipiClassi = odemeListesi[position]
        intent.putExtra("odemeTipiId",secilenOdemeTipiClassi.id)

        resultLauncher.launch(intent)

    }



    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            //odemeListesi.clear()

            //binding.rvOdemeler.adapter!!.notifyDataSetChanged()
            odemeListesi = OdemeTipiLogic.tumOdemeTipleriniGetir(this@MainActivity)
            binding.rvOdemeler.adapter = CardAdapter(applicationContext,odemeListesi, ::odemeDetayListener, ::btnOdemeTutariEkle)
            Toast.makeText(this,odemeListesi.size.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}