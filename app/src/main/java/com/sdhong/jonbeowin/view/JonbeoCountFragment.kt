package com.sdhong.jonbeowin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.databinding.FragmentJonbeoCountBinding

class JonbeoCountFragment : Fragment() {

    private var _binding : FragmentJonbeoCountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJonbeoCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}