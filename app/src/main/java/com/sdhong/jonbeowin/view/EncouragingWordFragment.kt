package com.sdhong.jonbeowin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.databinding.FragmentEncouragingWordBinding

class EncouragingWordFragment : Fragment() {

    private var _binding : FragmentEncouragingWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEncouragingWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}