package com.negusoft.tictactoe

import com.negusoft.tictactoe.data.Grid
import com.negusoft.tictactoe.data.Player
import com.negusoft.tictactoe.data.Position
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GridTest {

    val sampleGrid = Grid(
        topLeft = Player.X,
        center = Player.O,
        bottomRight = Player.X
    )

    @Test
    fun shouldInitEmpty() {
        val grid = Grid()
        assertNull(grid.topLeft)
        assertNull(grid.top)
        assertNull(grid.topRight)
        assertNull(grid.centerLeft)
        assertNull(grid.center)
        assertNull(grid.centerRight)
        assertNull(grid.bottomLeft)
        assertNull(grid.bottom)
        assertNull(grid.bottomRight)
    }

    @Test
    fun sholudEditValue() {
        assertEquals(sampleGrid.topLeft, Player.X)
        assertEquals(sampleGrid[Position.Row.TOP, Position.Column.LEFT], Player.X)

        val editedGrid = sampleGrid.edit(Player.O, Position.Row.TOP, Position.Column.LEFT)

        // The original is not modified but the new instance is.
        assertEquals(sampleGrid.topLeft, Player.X)
        assertEquals(sampleGrid[Position.Row.TOP, Position.Column.LEFT], Player.X)
        assertEquals(editedGrid.topLeft, Player.O)
        assertEquals(editedGrid[Position.Row.TOP, Position.Column.LEFT], Player.O)
    }
}