package com.negusoft.tictactoe.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.negusoft.tictactoe.R
import com.negusoft.tictactoe.data.Grid
import com.negusoft.tictactoe.data.Player

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:playerForeground")
    fun View.setPlayerForeground(player: Player?) {
        foreground = player.drawableRes?.let {
            resources.getDrawable(it, null)
        } ?: null
    }

    private val Player?.drawableRes: Int? get() = when (this) {
        Player.X -> R.drawable.ic_player_x
        Player.O -> R.drawable.ic_player_o
        null -> null
    }
}