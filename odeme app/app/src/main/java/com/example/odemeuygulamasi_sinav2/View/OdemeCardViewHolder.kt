package com.example.odemeuygulamasi_sinav2.View

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.odemeuygulamasi_sinav2.Model.Odeme
import com.example.odemeuygulamasi_sinav2.databinding.OdemeGecmisiItemBinding

class OdemeCardViewHolder(val listItemBinding: OdemeGecmisiItemBinding, var cListener:(Int)->Unit): RecyclerView.ViewHolder(listItemBinding.root) {

    var position:Int ?= null

    fun bindOdemeGecmisi(odeme: Odeme, p:Int) {
        position = p
        listItemBinding.tvTarih.text = "Ödeme Tarihi: ${odeme.odemeTarihi}"
        listItemBinding.tvTutar.text = "Ödeme Tutarı: ${odeme.odemeTutari}"

    }

    init{
        listItemBinding.constraintLayout.setOnClickListener {
           cListener(position!!)

        }

    }
}