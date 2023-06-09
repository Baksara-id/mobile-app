package com.baksara.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baksara.app.database.Kamus
import com.baksara.app.databinding.ItemKamusAksaraBinding
import com.baksara.app.ui.pustaka.ContohKamusActivity

class ListKamusDetailAdapter (private val kamus: List<Kamus>): RecyclerView.Adapter<ListKamusDetailAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemKamusAksaraBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(kamus: Kamus, position: Int) {
            binding.tvAksara.text = kamus.aksara
            binding.tvLatin.text = kamus.latin

            binding.cardViewKamusAksara.setOnClickListener {
                val intent = Intent(itemView.context, ContohKamusActivity::class.java)
                intent.putExtra(ContohKamusActivity.AKSARAJAWA_ID, kamus.id)
                intent.putExtra(ContohKamusActivity.JAWA_AKSARA, kamus.aksara)
                intent.putExtra(ContohKamusActivity.JAWA_LATIN, kamus.latin)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListKamusDetailAdapter.ListViewHolder {
        val binding = ItemKamusAksaraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListKamusDetailAdapter.ListViewHolder, position: Int) {
        val materiKamus = kamus[position]
        holder.bind(materiKamus, position)
    }

    override fun getItemCount(): Int = kamus.size
}