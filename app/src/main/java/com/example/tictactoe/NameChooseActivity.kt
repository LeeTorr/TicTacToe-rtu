package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class NameChooseActivity : AppCompatActivity() {
    private var isPvP : Boolean = true

    companion object {
        const val player_name_one= "com.example.tictactoe.name_one"
        const val player_name_two= "com.example.tictactoe.name_two"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_choose)

        checkPvP()
    }

    private fun checkPvP() {
        isPvP = intent.getBooleanExtra(MainActivity.ex_bool, true)
        if (!isPvP){
            changeLayoutForPvE()
        }
    }

    private fun changeLayoutForPvE() {
        val playerTwoText : TextView = findViewById(R.id.tv_player2)
        val playerTwoEditText : EditText = findViewById(R.id.et_player2)

        playerTwoText.visibility = View.INVISIBLE
        playerTwoEditText.visibility = View.INVISIBLE

        val activityLayout : ConstraintLayout = findViewById(R.id.parentLayout)
        val set = ConstraintSet()

        set.clone(activityLayout)
        set.connect(R.id.btn_start_game, ConstraintSet.TOP,
            R.id.tv_player1, ConstraintSet.BOTTOM)
        set.applyTo(activityLayout)
    }

    fun startGame(view: View) {
        val playerOneEditText : EditText = findViewById(R.id.et_player1)
        val playerTwoEditText : EditText = findViewById(R.id.et_player2)

        val playerOneName = playerOneEditText.text.toString()
        val playerTwoName = playerTwoEditText.text.toString()

        if (playerOneName == "" || playerTwoName == ""){
            Toast.makeText(this, "Enter player name.", Toast.LENGTH_SHORT).show()
        }else{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(MainActivity.ex_bool, isPvP)
            intent.putExtra(player_name_one, playerOneName)
            intent.putExtra(player_name_two, playerTwoName)
            startActivity(intent)
        }
    }
}