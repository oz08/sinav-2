package com.example.odemeuygulamasi_sinav2.View

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.odemeuygulamasi_sinav2.Model.OdemeTipi
import com.example.odemeuygulamasi_sinav2.databinding.ListItemBinding

class CardAdapter(var context: Context, val odemeler : List<OdemeTipi>, var listener:(Int)->Unit, var btnListener:(Int)->Unit) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(context)
        val binding = ListItemBinding.inflate(from,parent,false)
        return CardViewHolder(binding, listener, btnListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindOdeme(odemeler[position], position)
    }

    override fun getItemCount(): Int {
        return odemeler.size
    }
}