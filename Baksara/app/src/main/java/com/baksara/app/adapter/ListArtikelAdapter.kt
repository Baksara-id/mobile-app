package com.baksara.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baksara.app.R
import com.baksara.app.database.Artikel
import com.baksara.app.databinding.ItemArtikelBinding
import com.baksara.app.ui.artikel.DetailArtikelActivity
import com.bumptech.glide.Glide

class ListArtikelAdapter(private val artikels: List<Artikel>): RecyclerView.Adapter<ListArtikelAdapter.ListViewHolder>()  {
    inner class ListViewHolder(private val binding: ItemArtikelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artikel: Artikel, position: Int) {
            binding.tvJudulArtikel.text = artikel.judul
            binding.tvDeskripsiArtikel.text = artikel.deskripsi

            Glide.with(this.itemView)
                .load(artikel.url_img)
                .placeholder(R.drawable.arjunadummy2)
                .fitCenter()
                .into(binding.imageView);

            binding.cardViewArtikel.setOnClickListener {
                val intent = Intent(itemView.context, DetailArtikelActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListArtikelAdapter.ListViewHolder {
        val binding = ItemArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListArtikelAdapter.ListViewHolder, position: Int) {
        val artikel = artikels[position]
        holder.bind(artikel, position)
    }

    override fun getItemCount(): Int = artikels.size
}