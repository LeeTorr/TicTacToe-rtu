package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    companion object{
        const val ex_bool = "com.example.tictactoe.BOOLEAN"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startPvC(view: View){
        val intent = Intent(this, NameChooseActivity::class.java)
        intent.putExtra(Companion.ex_bool, false)
        startActivity(intent)
    }
    fun startPvP(view: View){
        val intent = Intent(this, NameChooseActivity::class.java)
        intent.putExtra(Companion.ex_bool, true)
        startActivity(intent)
    }
}
