package com.baksara.app.ui.profil.laporkanmasalah

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.baksara.app.R
import com.baksara.app.databinding.ActivityLanggananBinding
import com.baksara.app.databinding.ActivityLaporkanMasalahBinding

class LaporkanMasalahActivity : AppCompatActivity() {
    private var _binding: ActivityLaporkanMasalahBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLaporkanMasalahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Laporkan Masalah"
    }

    private fun showDialogLapor(context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView: View = inflater.inflate(R.layout.item_dialog_information, null)


        val imgInformation: ImageView = dialogView.findViewById(R.id.img_information)
        val textTitle: TextView = dialogView.findViewById(R.id.tv_information_title)
        val textDesc: TextView = dialogView.findViewById(R.id.tv_information_description)
        val buttonInformation: Button = dialogView.findViewById(R.id.btn_information)

        imgInformation.setImageResource(R.drawable.img_logo_confirmation)
        textTitle.text = "Laporan Terkirim"
        textDesc.text = "Laporan anda telah terkirim ke tim baksara. Permasalahan anda akan segera kami atasi."
        buttonInformation.text = "Mengerti"
        buttonInformation.background.setTint(resources.getColor(R.color.success))

        builder.setView(dialogView)
        val dialog: AlertDialog = builder.create()
        buttonInformation.setOnClickListener {
            dialog.dismiss()
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
        }

        dialog.show()
    }
}