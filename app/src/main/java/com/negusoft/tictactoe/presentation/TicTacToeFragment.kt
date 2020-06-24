package com.negusoft.tictactoe.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.negusoft.tictactoe.domain.MoveResult
import com.negusoft.tictactoe.R
import com.negusoft.tictactoe.databinding.FragmentTictactoeBinding
import com.negusoft.tictactoe.utils.databinding.observeEvent

class TicTacToeFragment : Fragment() {

    val viewModel by viewModels<TicTacToeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTictactoeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorEvent.observeEvent(this, Observer<MoveResult.Reason>(::handleError))
    }

    private fun handleError(error: MoveResult.Reason) {
        val message = when(error) {
            MoveResult.Reason.SQUARE_NOT_EMPTY -> R.string.game_error_square_not_empty
            MoveResult.Reason.GAME_ALREADY_FINISHED -> R.string.game_error_game_finished
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}