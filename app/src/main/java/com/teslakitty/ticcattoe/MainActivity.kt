package com.teslakitty.ticcattoe

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Tic Tac Toe board (3x3 grid)
    private val board = Array(3) { CharArray(3) { ' ' } }
    private var currentPlayer = 'X'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get buttons for the board
        val buttons = arrayOf(
            arrayOf(findViewById<Button>(R.id.button00), findViewById(R.id.button01), findViewById(R.id.button02)),
            arrayOf(findViewById(R.id.button10), findViewById(R.id.button11), findViewById(R.id.button12)),
            arrayOf(findViewById(R.id.button20), findViewById(R.id.button21), findViewById(R.id.button22))
        )

        // Restart button
        val restartButton = findViewById<Button>(R.id.restartButton)
        restartButton.setOnClickListener {
            resetGame(buttons)
        }

        // Set up click listeners for all buttons in the grid
        buttons.forEachIndexed { row, columns ->
            columns.forEachIndexed { col, button ->
                button.setOnClickListener {
                    if (board[row][col] == ' ' && !isGameOver()) {
                        // Update board and button text
                        board[row][col] = currentPlayer
                        button.text = currentPlayer.toString()

                        // Check game status
                        if (checkWin()) {
                            Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_SHORT).show()
                            disableBoard(buttons)
                        } else if (isDraw()) {
                            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
                        } else {
                            // Switch player
                            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
                        }
                    }
                }
            }
        }
    }

    // Check if there is a win
    private fun checkWin(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
            ) {
                return true
            }
        }
        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
        ) {
            return true
        }
        return false
    }

    // Check if the game is a draw
    private fun isDraw(): Boolean {
        return board.all { row -> row.all { it != ' ' } }
    }

    // Check if the game is over
    private fun isGameOver(): Boolean {
        return checkWin() || isDraw()
    }

    // Reset the game
    private fun resetGame(buttons: Array<Array<Button>>) {
        // Clear the board array
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = ' '
            }
        }
        // Reset all buttons
        buttons.forEach { row ->
            row.forEach { button ->
                button.text = ""
                button.isEnabled = true
            }
        }
        // Reset current player
        currentPlayer = 'X'
        Toast.makeText(this, "Game reset! Player X starts.", Toast.LENGTH_SHORT).show()
    }

    // Disable the board when the game is over
    private fun disableBoard(buttons: Array<Array<Button>>) {
        buttons.forEach { row ->
            row.forEach { button ->
                button.isEnabled = false
            }
        }
    }
}
