package com.example.tiptime

abstract class TipTime(var costofService: Double) {
    abstract fun totalPrice(): Double
    abstract fun gratuity(): Double


}