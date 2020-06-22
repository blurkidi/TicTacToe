package com.negusoft.tictactoe

import com.negusoft.tictactoe.data.Grid
import com.negusoft.tictactoe.data.Line
import com.negusoft.tictactoe.data.Player
import com.negusoft.tictactoe.data.Position

class LineDetector {

    /**
     * Detect three squares with the sample player.
     * It returns a list since there might be several lines.
     */
    fun detect(player: Player, grid: Grid): List<Line> {
        return Lines.allLines.mapNotNull { line ->
            checkLine(player, grid, line)
        }
    }

    // Check if each position of the line corresponds to the player
    private fun checkLine(player: Player, grid: Grid, line: Line): Line? {
        return if (grid[line.first.row, line.first.column] == player &&
            grid[line.second.row, line.second.column] == player &&
            grid[line.second.row, line.second.column] == player) {
            line
        } else {
            null
        }
    }

    // All possible lines in a grid
    object Lines {
        val allLines = listOf<Line>(
            Horizontal.top,
            Horizontal.center,
            Horizontal.bottom,
            Vertical.left,
            Vertical.center,
            Vertical.right,
            Diagonal.topLeft,
            Diagonal.bottomLeft
        )

        object Horizontal {
            val top = create(Position.Row.TOP)
            val center = create(Position.Row.CENTER)
            val bottom = create(Position.Row.BOTTOM)

            private fun create(row: Position.Row) = Line(
                Position(Position.Column.LEFT, row),
                Position(Position.Column.CENTER, row),
                Position(Position.Column.RIGHT, row)
            )
        }

        object Vertical {
            val left = create(Position.Column.LEFT)
            val center = create(Position.Column.CENTER)
            val right = create(Position.Column.RIGHT)

            private fun create(column: Position.Column) = Line(
                Position(column, Position.Row.TOP),
                Position(column, Position.Row.CENTER),
                Position(column, Position.Row.BOTTOM)
            )
        }

        object Diagonal {
            val topLeft = create(Position.Column.LEFT, Position.Column.RIGHT)
            val bottomLeft = create(Position.Column.RIGHT, Position.Column.LEFT)

            private fun create(topColumn: Position.Column, bottomColumn: Position.Column) = Line(
                Position(topColumn, Position.Row.TOP),
                Position(Position.Column.CENTER, Position.Row.CENTER),
                Position(bottomColumn, Position.Row.BOTTOM)
            )
        }
    }

}