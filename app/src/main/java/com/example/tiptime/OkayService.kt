package com.example.tiptime

class OkayService(costofService: Double, private val round_state: Boolean) :
    TipTime(costofService) {
    private val okay = .15
    override fun gratuity(): Double {
        return when (round_state) {
            true -> kotlin.math.ceil(costofService * okay)
            false -> costofService * okay
        }

    }

    override fun totalPrice(): Double {
        return when (round_state) {
            true -> kotlin.math.ceil(costofService + (costofService * okay))
            false -> costofService + (costofService * okay)
        }
    }
}