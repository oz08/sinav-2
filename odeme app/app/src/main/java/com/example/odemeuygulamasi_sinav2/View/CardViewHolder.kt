package com.example.odemeuygulamasi_sinav2.View

import androidx.recyclerview.widget.RecyclerView
import com.example.odemeuygulamasi_sinav2.BLL.OdemeTipiLogic

import com.example.odemeuygulamasi_sinav2.Model.OdemeTipi

import com.example.odemeuygulamasi_sinav2.databinding.ListItemBinding

class CardViewHolder(val listItemBinding: ListItemBinding,var detayListener:(Int)->Unit, var btnListener:(Int)->Unit): RecyclerView.ViewHolder(listItemBinding.root) {

    var p:Int ?= null

    fun bindOdeme(odemeTipi:OdemeTipi, position:Int) {
        p = position
        var periyotGunu = odemeTipi.periyotGunu
        var odemePeriyodu = odemeTipi.odemePeriyodu
        listItemBinding.tvBaslik.text = odemeTipi.odemeBaslik

        if(periyotGunu != null){
            listItemBinding.tvOdemePeriyotGunu.text = "Periyot Günü: $periyotGunu"
        }else{
            listItemBinding.tvOdemePeriyotGunu.text = "Periyot günü seçilmedi."
        }
        if(odemePeriyodu != null){
            listItemBinding.tvOdemePeriyodu.text = odemeTipi.odemePeriyodu.toString()
        }else{
            listItemBinding.tvOdemePeriyotGunu.text = "Ödeme Periyodu Seçilmedi"
        }

    }

    init{
        listItemBinding.cLayout.setOnClickListener {

            detayListener(p!!)
        }

        listItemBinding.btnOdemeEkle.setOnClickListener {
            btnListener(p!!)
        }
    }



}