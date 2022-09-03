package com.example.tiptime

class GoodService(costofService: Double, private val round_state: Boolean) :
    TipTime(costofService) {

    private val good = .18
    override fun gratuity(): Double {
        return when (round_state) {
            true -> kotlin.math.ceil(costofService * good)
            false -> costofService * good
        }

    }

    override fun totalPrice(): Double {
        return when (round_state) {
            true -> kotlin.math.ceil(costofService + (costofService * good))
            false -> costofService + (costofService * good)
        }
    }
}