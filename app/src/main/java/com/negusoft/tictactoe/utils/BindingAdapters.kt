package com.negusoft.tictactoe.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.negusoft.tictactoe.domain.GameResult
import com.negusoft.tictactoe.R
import com.negusoft.tictactoe.data.Player

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:playerForeground")
    fun View.setPlayerForeground(player: Player?) {
        foreground = player.drawableRes?.let {
            resources.getDrawable(it, null)
        }
    }

    @JvmStatic
    @BindingAdapter("app:gameResult")
    fun TextView.setGameResult(result: GameResult?) {
        text = result.textRes?.let {
            resources.getString(it)
        }
    }

    @JvmStatic
    @BindingAdapter("app:visibleOrGone")
    fun View.setVisible(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }

    private val Player?.drawableRes: Int? get() = when (this) {
        Player.X -> R.drawable.ic_player_x
        Player.O -> R.drawable.ic_player_o
        null -> null
    }

    private val GameResult?.textRes: Int? get() = when (this) {
        is GameResult.Win -> this.winner.winnerTextRes
        GameResult.Draw -> R.string.game_result_draw
        null -> null
    }

    private val Player.winnerTextRes: Int
        get() = if (this == Player.X) R.string.game_result_winner_x else R.string.game_result_winner_o
}