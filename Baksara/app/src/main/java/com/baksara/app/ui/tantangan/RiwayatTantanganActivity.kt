package com.baksara.app.ui.tantangan

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.baksara.app.utils.ViewModelFactory
import com.baksara.app.adapter.ListTantanganWideRiwayatAdapter
import com.baksara.app.databinding.ActivityRiwayatTantanganBinding
import com.baksara.app.response.Langganan
import com.baksara.app.response.RiwayatBelajar
import com.baksara.app.response.Tantangan
import com.baksara.app.response.User
import com.baksara.app.ui.MainActivity

class RiwayatTantanganActivity : AppCompatActivity() {
    private var _binding: ActivityRiwayatTantanganBinding? = null
    private val binding get() = _binding!!
    private lateinit var tantanganViewModel: TantanganViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRiwayatTantanganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Riwayat Tantangan"

        tantanganViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this@RiwayatTantanganActivity))[TantanganViewModel::class.java]

        val userPref = getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE)
        val userLogin = getUser(userPref)

        userLogin.let{
            tantanganViewModel.fetchRiwayatTantanganUser(it.id ?: -1)
        }

        tantanganViewModel.liveDataTantanganSudah.observe(this){ result ->
            result.onSuccess { tantangans->
                val listTantanganSudah = tantangans.data?.riwayatTantangan ?: emptyList()
                if(listTantanganSudah.isEmpty()){
                    binding.rvTantanganRiwayat.visibility = View.GONE
                    binding.tvTantanganRiwayatKosong.visibility = View.VISIBLE
                }
                else{
                    binding.rvTantanganRiwayat.visibility = View.VISIBLE
                    binding.tvTantanganRiwayatKosong.visibility = View.GONE
                }
                setupTantanganAdapter(listTantanganSudah)
            }

            result.onFailure {
                //Kalau gagal
            }
        }

        tantanganViewModel.liveDataIsLoading.observe(this){
            showLoading(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        finish()
        return true
    }

    private fun setupTantanganAdapter(listTantanganSudah: List<Tantangan>){
        val layoutManager = LinearLayoutManager(this)
        binding.rvTantanganRiwayat.layoutManager = layoutManager

        val adapter = ListTantanganWideRiwayatAdapter(listTantanganSudah)
        binding.rvTantanganRiwayat.adapter = adapter
        binding.inputTantanganRiwayatSearch.addTextChangedListener (object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                val filteredList = listTantanganSudah.filter {
                    it.nama?.lowercase()?.contains(query) == true
                }
                adapter.setListTantangan(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    fun showLoading(isLoading: Boolean){
        binding.tantanganRiwayatLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    fun getUser(userPref: SharedPreferences): User {
        val name = userPref.getString(MainActivity.FULLNAME,"")
        val email = userPref.getString(MainActivity.EMAIL,"")
        val avatar = userPref.getString(MainActivity.AVATAR,"")
        val id = userPref.getInt(MainActivity.UNIQUEID,0)
        val exp = userPref.getInt(MainActivity.EXP,0)
        val level = userPref.getInt(MainActivity.LEVEL,0)
        val limit = userPref.getInt(MainActivity.CURRENTLIMIT,0)
        val kelas = userPref.getInt(MainActivity.KELAS,0)
        val modul = userPref.getInt(MainActivity.MODUL,0)
        val token = userPref.getString(MainActivity.TOKEN,"")
        val langganan = userPref.getInt(MainActivity.PREMIUM,1)
        val _langgananObject = Langganan(langganan,"",0.0f,0)
        var listOfRiwayat = mutableListOf<RiwayatBelajar>()
        val _riwayatBelajarObject = RiwayatBelajar(0,id,modul,kelas)
        listOfRiwayat.add(_riwayatBelajarObject)
        return User(id,_langgananObject,name,email,token,avatar, exp,level,limit,kadaluarsa = null,null,listOfRiwayat)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}