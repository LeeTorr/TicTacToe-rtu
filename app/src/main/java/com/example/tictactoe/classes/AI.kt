package com.example.tictactoe.classes

import android.widget.Button
import com.example.tictactoe.classes.functionals.AIEvaluator

class AI(name: String, playerSymbol: String, opponentSymbol: String) : Player(name, playerSymbol, opponentSymbol) {

    private var evaluator = AIEvaluator(playerSymbol)
    private val min = Int.MIN_VALUE
    private val max = Int.MAX_VALUE

    fun makeBestMove(buttons: Array<Array<Button>>) {
        var bestValue : Int = min
        var bestButton : Button? = null

        buttons.forEach {
            it.forEach { button ->
                if (button.text.equals("")){
                    button.text = playerSymbol
                    val moveValue = minimax(buttons, 0, false, min, max)
                    button.text = ""
                    if (moveValue > bestValue){
                        bestValue = moveValue
                        bestButton = button
                    }
                }
            }
        }

        bestButton!!.performClick()
    }

    private fun minimax(field: Array<Array<Button>>, depth: Int, isMax: Boolean, alpha: Int, beta: Int): Int{
        val score = evaluator.evaluate(field)
        var alpha = alpha
        var beta = beta
        when(score){
            10 -> return score - depth
            -10 -> return score + depth
        }

        if (!isMoveLeft(field)){
            return 0
        }

        var bestValue : Int
        if (isMax){
            bestValue = min
            field.forEach {
                it.forEach { button ->
                    if (button.text.equals("")){
                        button.text = playerSymbol
                        bestValue = maxOf(bestValue, minimax(field, depth + 1, !isMax, alpha, beta))
                        alpha = maxOf(alpha, bestValue)
                        button.text = ""
                        if (alpha >= beta){
                            return bestValue
                        }
                    }
                }
            }
        }else{
            bestValue = max
            field.forEach {
                it.forEach { button ->
                    if (button.text.equals("")){
                        button.text = opponentSymbol
                        bestValue = minOf(bestValue, minimax(field, depth + 1, !isMax, alpha, beta))
                        button.text = ""
                        beta = minOf(beta, bestValue)
                        if (beta <= alpha){
                            return bestValue
                        }
                    }
                }
            }
        }
        return bestValue
    }

    private fun isMoveLeft(field: Array<Array<Button>>) : Boolean{
        field.forEach {
            it.forEach { button ->
                if (button.text.equals("")){
                    return true
                }
            }
        }
        return false
    }

    override fun swapSymbols(){
        super.swapSymbols()
        evaluator = AIEvaluator(playerSymbol)
    }
}