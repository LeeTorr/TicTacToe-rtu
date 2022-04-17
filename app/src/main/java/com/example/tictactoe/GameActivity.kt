package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.classes.AI
import com.example.tictactoe.classes.Player
import com.example.tictactoe.classes.functionals.GameEvaluator


class GameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var x: String
    private lateinit var o: String

    private var isPvP : Boolean = true
    private var gameEnded : Boolean = false
    private var roundCount : Int = 0
    private lateinit var currentTurnSymbol : String

    private lateinit var playerOne: Player
    private lateinit var playerTwo: Player

    private lateinit var tvPlayerOneScore: TextView
    private lateinit var tvPlayerTwoScore: TextView
    private lateinit var tvPlayersTurn: TextView

    private var buttonsArray = arrayOf<Array<Button>>()
    private val gameEndEvaluator = GameEvaluator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initializeGame()
    }

    private fun initializeGame() {
        val nameOne : String? = intent.getStringExtra(NameChooseActivity.player_name_one)
        val nameTwo : String? = intent.getStringExtra(NameChooseActivity.player_name_two)
        isPvP = intent.getBooleanExtra(MainActivity.ex_bool, true)

        x = getString(R.string.X_Value)
        o = getString(R.string.O_Value)

        playerOne = Player(nameOne!!, x, o)
        playerTwo = if (isPvP) Player(nameTwo!!, o, x) else AI("AI", o, x)

        tvPlayerOneScore = findViewById(R.id.tv_player1_score)
        tvPlayerTwoScore = findViewById(R.id.tv_player2_score)
        tvPlayersTurn = findViewById(R.id.tv_turn)

        (findViewById<TextView>(R.id.tv_player1_name)).text = "${playerOne.name}:"
        (findViewById<TextView>(R.id.tv_player2_name)).text = "${playerTwo.name}:"
        tvPlayersTurn.text = getString(R.string.turn_of) + " $nameOne"

        initializeButtons()
        currentTurnSymbol = x
    }

    private fun initializeButtons() {
        for (indexOne in 0..2){
            var array = arrayOf<Button>()
            for (indexTwo in 0..2){
                val buttonID = "button_$indexOne$indexTwo"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                array += findViewById<Button>(resID)
                array[indexTwo].setOnClickListener(this)
            }
            buttonsArray += array
        }
    }

    override fun onClick(view: View?) {
        if ((view as Button).text.toString() != "" || gameEnded){
            return
        }

        if (currentTurnSymbol == x) {
            view.text = x
        } else {
            view.text = o
        }

        roundCount++

        if (gameEndEvaluator.evaluate(buttonsArray)) {
            if (currentTurnSymbol == playerOne.playerSymbol) {
                playerWins(playerOne)
                tvPlayerOneScore.text = playerOne.score.toString()
            } else {
                playerWins(playerTwo)
                tvPlayerTwoScore.text = playerTwo.score.toString()
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            changeTurn()

            if (!isPvP && currentTurnSymbol == (playerTwo as AI).playerSymbol){
                moveOfAI()
            }
        }
    }

    private fun moveOfAI() {
        (playerTwo as AI).makeBestMove(buttonsArray)
    }

    private fun changeTurn() {
        when(currentTurnSymbol){
            x -> currentTurnSymbol = o
            o -> currentTurnSymbol = x
        }

        if (currentTurnSymbol == playerOne.playerSymbol){
            tvPlayersTurn.text = getString(R.string.turn_of) + " " + playerOne.name
        }else{
            tvPlayersTurn.text = getString(R.string.turn_of) + " " + playerTwo.name
        }
    }

    private fun playerWins(player: Player?) {
        player?.increaseScore()
        Toast.makeText(this, player?.name + " wins!", Toast.LENGTH_SHORT).show()
        gameEnded = true
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
    }

    private fun resetField() {
        buttonsArray.forEach {
            it.forEach { button ->
                button.text = ""
            }
        }
        roundCount = 0
        gameEnded = false

        playerOne.swapSymbols()
        if (!isPvP){
            val AI = playerTwo as AI
            AI.swapSymbols()

            currentTurnSymbol = o
            changeTurn()
            if (AI.playerSymbol == x){
                moveOfAI()
            }
        }else{
            playerTwo.swapSymbols()
            currentTurnSymbol = o
            changeTurn()
        }
    }

    fun endGame(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    fun resetGame(view: View) {
        resetField()
    }

}