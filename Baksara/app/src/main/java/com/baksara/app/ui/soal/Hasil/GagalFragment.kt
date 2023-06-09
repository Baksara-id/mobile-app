package com.baksara.app.ui.soal.Hasil

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import com.baksara.app.databinding.FragmentGagalBinding

class GagalFragment : Fragment() {

    private var _binding: FragmentGagalBinding? = null
    private val binding get() = _binding!!
    private lateinit var handler: Handler
    private lateinit var delayedRunnable: Runnable
    private lateinit var fadeInAnimator: ObjectAnimator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGagalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler()

        fadeInAnimator = ObjectAnimator.ofFloat(binding.tvGagalDesc, "alpha", 0f, 1f)
            .apply {
                duration = 500 // Animation duration in milliseconds
                doOnEnd { binding.tvGagalDesc.visibility = View.VISIBLE }
            }

        delayedRunnable = Runnable {
            fadeInAnimator.start()
        }

        handler.postDelayed(delayedRunnable, 1000)

        binding.btnKembaliKelas.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(delayedRunnable)
    }
}