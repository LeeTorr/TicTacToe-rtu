package com.example.tictactoe.classes

open class Player (val name: String, playerSymbol: String, opponentSymbol: String){
    var playerSymbol : String = playerSymbol
        private set

    var opponentSymbol : String = opponentSymbol
        private set

    var score: Int = 0
        private set

    fun increaseScore(){
        score++
    }

    open fun swapSymbols(){
        val temp = playerSymbol
        playerSymbol = opponentSymbol
        opponentSymbol = temp
    }

}