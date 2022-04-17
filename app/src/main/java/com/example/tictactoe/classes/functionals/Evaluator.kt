package com.example.tictactoe.classes.functionals

import android.widget.Button

fun interface Evaluator<T> {
    fun evaluate(buttons: Array<Array<Button>>) : T
}