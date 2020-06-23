package com.negusoft.tictactoe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.negusoft.tictactoe.databinding.FragmentTictactoeBinding

class TicTacToeFragment : Fragment() {

    val viewModel by viewModels<TicTacToeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTictactoeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

}