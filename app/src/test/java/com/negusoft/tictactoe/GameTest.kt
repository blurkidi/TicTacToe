package com.negusoft.tictactoe

import com.negusoft.tictactoe.data.Grid
import com.negusoft.tictactoe.data.Player
import com.negusoft.tictactoe.data.Position
import com.negusoft.tictactoe.utils.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameTest {

    @Test
    fun shouldInitGame() {
        val game = Game()
        game.state.assertOngoing(Player.X)
        assertNull(game.grid.topLeft)
        assertNull(game.grid.top)
        assertNull(game.grid.topRight)
        assertNull(game.grid.centerLeft)
        assertNull(game.grid.center)
        assertNull(game.grid.centerRight)
        assertNull(game.grid.bottomLeft)
        assertNull(game.grid.bottom)
        assertNull(game.grid.bottomRight)
    }

    @Test
    fun shouldAlternateMoves() {
        val game = Game()

        val firstResult = game.makeMove(Position.Row.TOP, Position.Column.LEFT)
        firstResult.assertSuccess { it.state.assertOngoing(Player.O) }
        game.state.assertOngoing(Player.O)
        assertEquals(game.grid.topLeft, Player.X)

        val secondResult = game.makeMove(Position.Row.TOP, Position.Column.CENTER)
        secondResult.assertSuccess { it.state.assertOngoing(Player.X) }
        game.state.assertOngoing(Player.X)
        assertEquals(game.grid.top, Player.O)
    }

    @Test
    fun shouldNotOverrideSquare() {
        val game = Game()

        game.makeMove(Position.Row.TOP, Position.Column.LEFT)
        val result = game.makeMove(Position.Row.TOP, Position.Column.LEFT)
        game.state.assertOngoing(Player.O)
        assertEquals(game.grid.topLeft, Player.X)
        result.assertError { assertEquals(it.reason, MoveResult.Reason.SQUARE_NOT_EMPTY) }
    }

    @Test
    fun shouldWinX() {
        val game = Game()

        game.makeMove(Position.Row.TOP, Position.Column.LEFT)
        game.makeMove(Position.Row.TOP, Position.Column.CENTER)
        game.makeMove(Position.Row.CENTER, Position.Column.LEFT)
        game.makeMove(Position.Row.TOP, Position.Column.RIGHT)
        game.makeMove(Position.Row.BOTTOM, Position.Column.LEFT)

        game.state.assertFinished { it.result.assertWinner(Player.X) }
    }

    @Test
    fun shouldWinO() {
        val game = Game()

        game.makeMove(Position.Row.TOP, Position.Column.CENTER)
        game.makeMove(Position.Row.TOP, Position.Column.LEFT)
        game.makeMove(Position.Row.TOP, Position.Column.RIGHT)
        game.makeMove(Position.Row.CENTER, Position.Column.LEFT)
        game.makeMove(Position.Row.CENTER, Position.Column.RIGHT)
        game.makeMove(Position.Row.BOTTOM, Position.Column.LEFT)

        game.state.assertFinished { it.result.assertWinner(Player.O) }
    }

    @Test
    fun shouldDraw() {
        val game = Game()

        game.makeMove(Position.Row.CENTER, Position.Column.CENTER)//x
        game.makeMove(Position.Row.TOP, Position.Column.LEFT)//o
        game.makeMove(Position.Row.BOTTOM, Position.Column.LEFT)//x
        game.makeMove(Position.Row.TOP, Position.Column.RIGHT)//o
        game.makeMove(Position.Row.TOP, Position.Column.CENTER)//x
        game.makeMove(Position.Row.BOTTOM, Position.Column.CENTER)//o
        game.makeMove(Position.Row.CENTER, Position.Column.LEFT)//x
        game.makeMove(Position.Row.CENTER, Position.Column.RIGHT)//o
        game.makeMove(Position.Row.BOTTOM, Position.Column.RIGHT)//x

        game.state.assertFinished { assert(it.result == GameResult.Draw) }
    }

    @Test
    fun shouldNotDrawWhenLastMoveWins() {
        val game = Game()

        game.makeMove(Position.Row.TOP, Position.Column.LEFT)//x
        game.makeMove(Position.Row.TOP, Position.Column.CENTER)//o
        game.makeMove(Position.Row.TOP, Position.Column.RIGHT)//x
        game.makeMove(Position.Row.CENTER, Position.Column.LEFT)//o
        game.makeMove(Position.Row.CENTER, Position.Column.CENTER)//x
        game.makeMove(Position.Row.CENTER, Position.Column.RIGHT)//o
        game.makeMove(Position.Row.BOTTOM, Position.Column.CENTER)//x
        game.makeMove(Position.Row.BOTTOM, Position.Column.RIGHT)//o
        game.makeMove(Position.Row.BOTTOM, Position.Column.LEFT)//x

        game.state.assertFinished { it.result.assertWinner(Player.X) }
    }

    @Test
    fun shouldDetectRows() {
        val topGame = Game().playInRow(Position.Row.TOP, Position.Row.CENTER)
        topGame.state.assertFinished { it.result.assertWinner(Player.X) }

        val centerGame = Game().playInRow(Position.Row.CENTER, Position.Row.BOTTOM)
        centerGame.state.assertFinished { it.result.assertWinner(Player.X) }

        val bottomGame = Game().playInRow(Position.Row.BOTTOM, Position.Row.TOP)
        bottomGame.state.assertFinished { it.result.assertWinner(Player.X) }
    }

    // Make 3 moves with X and 2 with O on the speficied rows
    private fun Game.playInRow(rowX: Position.Row, rowO: Position.Row): Game = apply {
        makeMove(rowX, Position.Column.LEFT)
        makeMove(rowO, Position.Column.LEFT)
        makeMove(rowX, Position.Column.CENTER)
        makeMove(rowO, Position.Column.CENTER)
        makeMove(rowX, Position.Column.RIGHT)
    }

    @Test
    fun shouldDetectColumns() {
        val leftGame = Game().playInCoumnt(Position.Column.LEFT, Position.Column.CENTER)
        leftGame.state.assertFinished { it.result.assertWinner(Player.X) }

        val centerGame = Game().playInCoumnt(Position.Column.CENTER, Position.Column.RIGHT)
        centerGame.state.assertFinished { it.result.assertWinner(Player.X) }

        val rightGame = Game().playInCoumnt(Position.Column.RIGHT, Position.Column.LEFT)
        rightGame.state.assertFinished { it.result.assertWinner(Player.X) }
    }

    // Make 3 moves with X and 2 with O on the speficied rows
    private fun Game.playInCoumnt(columnX: Position.Column, columnO: Position.Column): Game = apply {
        makeMove(Position.Row.TOP, columnX)
        makeMove(Position.Row.TOP, columnO)
        makeMove(Position.Row.CENTER, columnX)
        makeMove(Position.Row.CENTER, columnO)
        makeMove(Position.Row.BOTTOM, columnX)
    }

    @Test
    fun shouldDetectDiagonals() {
        Game().let { game ->
            game.makeMove(Position.Row.TOP, Position.Column.LEFT)
            game.makeMove(Position.Row.TOP, Position.Column.CENTER)
            game.makeMove(Position.Row.CENTER, Position.Column.CENTER)
            game.makeMove(Position.Row.TOP, Position.Column.RIGHT)
            game.makeMove(Position.Row.BOTTOM, Position.Column.RIGHT)

            game.state.assertFinished { it.result.assertWinner(Player.X) }
        }

        Game().let { game ->
            game.makeMove(Position.Row.TOP, Position.Column.RIGHT)
            game.makeMove(Position.Row.TOP, Position.Column.CENTER)
            game.makeMove(Position.Row.CENTER, Position.Column.CENTER)
            game.makeMove(Position.Row.TOP, Position.Column.LEFT)
            game.makeMove(Position.Row.BOTTOM, Position.Column.LEFT)

            game.state.assertFinished { it.result.assertWinner(Player.X) }
        }
    }

    @Test
    fun shouldNotMoveAfterGameFinishes() {
        val finishedGame = Game().playInCoumnt(Position.Column.LEFT, Position.Column.CENTER)

        val result = finishedGame.makeMove(Position.Row.TOP, Position.Column.LEFT)
        finishedGame.state.assertFinished { it.result.assertWinner(Player.X) }
        result.assertError { assert(it.reason == MoveResult.Reason.GAME_ALREADY_FINISHED) }
    }

}