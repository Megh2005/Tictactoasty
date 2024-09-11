package com.example.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var tvStatus: TextView
    private lateinit var buttonReset: Button
    private var board = Array(3) { arrayOfNulls<String>(3) } // 3x3 board
    private var currentPlayer = "X"
    private var gameActive = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        gridLayout = findViewById(R.id.gridLayout)
        tvStatus = findViewById(R.id.tvStatus)
        buttonReset = findViewById(R.id.buttonReset)

        // Set up button listeners for the grid
        val buttons = listOf(
            findViewById<Button>(R.id.button1), findViewById<Button>(R.id.button2), findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4), findViewById<Button>(R.id.button5), findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7), findViewById<Button>(R.id.button8), findViewById<Button>(R.id.button9)
        )

        for ((index, button) in buttons.withIndex()) {
            button.setOnClickListener {
                onButtonClick(button, index / 3, index % 3)
            }
        }

        // Reset the game
        buttonReset.setOnClickListener {
            resetGame()
        }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (gameActive && button.text.isEmpty()) {
            // Mark the board and button with the current player's symbol
            button.text = currentPlayer
            board[row][col] = currentPlayer

            // Check if there's a winner
            if (checkWinner()) {
                tvStatus.text = "Player $currentPlayer wins!"
                gameActive = false
            } else if (isBoardFull()) {
                tvStatus.text = "It's a Draw!"
                gameActive = false
            } else {
                // Switch to the next player
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                tvStatus.text = "Player $currentPlayer's Turn"
            }
        }
    }

    private fun checkWinner(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer)
                return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
                return true
        }

        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer)
            return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
            return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == null) return false
            }
        }
        return true
    }

    private fun resetGame() {
        // Clear the board
        board = Array(3) { arrayOfNulls<String>(3) }
        currentPlayer = "X"
        gameActive = true
        tvStatus.text = "Player X's Turn"

        // Clear the buttons
        val buttons = listOf(
            findViewById<Button>(R.id.button1), findViewById<Button>(R.id.button2), findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4), findViewById<Button>(R.id.button5), findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7), findViewById<Button>(R.id.button8), findViewById<Button>(R.id.button9)
        )

        for (button in buttons) {
            button.text = ""
        }
    }
}