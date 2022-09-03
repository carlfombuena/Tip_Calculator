package com.example.tiptime

class AmazingService(costofService: Double, private val round_state: Boolean) :
    TipTime(costofService) {
    private val amazing = .20

    override fun gratuity(): Double {
        return when (round_state) {
            true -> kotlin.math.ceil(costofService * amazing)
            false -> costofService * amazing
        }

    }

    override fun totalPrice(): Double {
        return when (round_state) {
            true -> kotlin.math.ceil(costofService + (costofService * amazing))
            false -> costofService + (costofService * amazing)
        }
    }
}
