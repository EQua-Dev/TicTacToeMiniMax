package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

//    we define a 2D array of ImageView data type and assign it to the constant 'boardCells'
    private val boardCells = Array(3){ arrayOfNulls<ImageView>(3)}

    var board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        we then call the loadBoard in the onCreate
        loadBoard()

//        we then set a function on the reset button
        button_restart.setOnClickListener {
//            we create a new board with empty values (more like the old board is deleted)
            board = Board()
            text_view_result.text = ""
//            we call the mapBoardToUI in order to empty our cells
            mapBoardToUI()
        }
    }

//    we then create a function to match the skeleton board to the UI board
    private fun mapBoardToUI(){
//    then we run a double loop through all the cells in the Board class
    for (i in board.board.indices){
        for (j in board.board.indices){
//            we then write statements for thr following 3 possibilities
            when(board.board[i][j]){
//                it is the player's move
                Board.PLAYER ->{
//                    we set the image to be displayed as the circle
                    boardCells[i][j]?.setImageResource(R.drawable.circle)
//                    we then disable that cell in order not to be clickable
                    boardCells[i][j]?.isEnabled = false
                }
//                it is the computer's move
                Board.COMPUTER ->{
//                    we set the image to be displayed as the cross
                    boardCells[i][j]?.setImageResource(R.drawable.cross)
//                    we then disable that cell in order not to be clickable
                    boardCells[i][j]?.isEnabled = false

            }
//                it is none of their moves
                else ->{
//                    we set no image to be displayed
                    boardCells[i][j]?.setImageResource(0)
//                    we then enable that cell in order to be clickable
                    boardCells[i][j]?.isEnabled = true

            }

            }
        }
    }
    }


    private fun loadBoard(){
//        we will run a double loop through all the image views of the 2d array to initialize them
//        for every item in the array position of the 2 arrays
        for (i in boardCells.indices)
            for (j in boardCells.indices){
//                we then initialize the imageView to the boardCells
                boardCells[i][j] = ImageView(this)
//                we then set the layout format which the ImageView will assume
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
//                    we specify which of the array items is the row and column i.e the grid image indexes of the image View
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)

//                    we then set the properties for the imageView
                    width = 250
                    height = 230
                    bottomMargin = 5
                    topMargin = 5

                }

//                we then set the background color of the imageViews
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                we then set the custom click listener to the imageViews
                boardCells[i][j]?.setOnClickListener(CellClickListener(i,j))
//                we then add the ImageView to the grid layout
                layout_board.addView(boardCells[i][j])
            }
    }


//        we will create a custom click listener in order to obtain the index of the cell that is clicked
    inner class CellClickListener(
    private val i: Int,
    private val j: Int) : View.OnClickListener{

//    we then override the onClick function
    override fun onClick(p0: View?) {
//    if the game is not over we apply on Click to the imageViews
    if (!board.isGameOver){
//    then we create a cell var and assign to it the rows amd columns
        val  cell = Cell(i,j)
//    we then invoke the placeMove function on the cell of the Player's move
        board.placeMove(cell, Board.PLAYER)

//        todo implement this move for hard difficulty
//        when the player has moved, we then invoke the minimax algorithm to handle the computer's move
        board.minimax(0, Board.COMPUTER)
//    we then invoke the placeMove function on the cell of the Computer's move
        board.computersMove?.let {
            board.placeMove(it, Board.COMPUTER)
        }



//    we then check if there are available cells
//        if (board.availableCells.isNotEmpty()){

//        todo implement this method below for when you add difficulties
//        we set a val 'cCell' to represent the computer's move  and chose a random cell index from the available cells
//            val cCell = board.availableCells[Random.nextInt(0,board.availableCells.size)]
//        we then invoke the placeMove function on the cell of the Computer's move
//            board.placeMove(cCell, Board.COMPUTER)
//        }
//    we then invoke the mapBoardToUI function to the custom onClick
        mapBoardToUI()
    }

    when{
        board.hasComputerWon() -> text_view_result.text = "COMPUTER WON!!"
        board.hasPlayerWon() -> text_view_result.text = "PLAYER WON!!"
        board.isGameOver -> text_view_result.text = "Game Tied!!"
//        TODO update the scores based on who wins
//        todo make the player who loses to start the next game
//        todo implement various difficulty levels
    }
    }
}

}