package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tipTimeState = "TIPTIME_STATE"
    private var tipState: String? = "idle"
    private var roundState: Boolean = true
    private val amazingService = "amazing_service"
    private val goodService = "good_service"
    private val okService = "ok_service"
    private var calculateBtn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (savedInstanceState != null) {
            tipState = savedInstanceState.getString(tipTimeState, "idle")

        }
//This switch changes the boolean state for rounding off the tip amount and total sale
        //if it's true tip amount will be rounding up or else not
        binding.roundUpSwitch.setOnCheckedChangeListener { _, isChecked ->
            roundState = isChecked
        }


//This button trigger the function for calculating tip.
        binding.calculateButton.setOnClickListener {
            calculateService()
            calculateBtn = true
        }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(tipTimeState, tipState)
        super.onSaveInstanceState(outState)

    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    //This function detects the Radio button when its clicked and triggered a conditional statement
    //on which specific radio button is clicked currently there is three radio button
    //There is three condition that change the state of Tipping one for 20 percent tip,18 percent tip and 15 percent tip
    fun onRadioButtonClicked(view: View) {

        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.option_twenty_percent ->
                    if (checked) {
                        tipState = amazingService
                        Toast.makeText(this, "20 percent", Toast.LENGTH_SHORT).show()


                    }
                R.id.option_eighteen_percent ->
                    if (checked) {
                        // Ninjas rule
                        Toast.makeText(this, "18 percent", Toast.LENGTH_SHORT).show()
                        tipState = goodService

                    }
                R.id.option_fifteen_percent ->
                    if (checked) {
                        // Ninjas rule
                        Toast.makeText(this, "15 percent", Toast.LENGTH_SHORT).show()
                        tipState = okService

                    }
            }
        }
    }

    //This function calls the class for calculating tip, find out which tip state base on radio button output
    //And calculate the tip to output the right value base on state.
    private fun calculateService() {
        //This is declared value of textview and editText, EditText is for tracking the user input, Textview for showing result.
        val tipAmount = binding.tipResult
        val totalSale = binding.totaltipResult
        val costofService = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        //This if condition bellow fix the app crash when user accidentally input nothing and click calculate.
        if (costofService == null) {
            tipAmount.text = ""
            totalSale.text = ""
            return
        }
        //This condition bellow track the state of radio button when its click and Instantiate the calculation from
        //the open classes below
        when (tipState) {

            amazingService -> {
                val amazingService = AmazingService(costofService, roundState).gratuity()
                val amazingServiceTotal = AmazingService(costofService, roundState).totalPrice()
                val tip = NumberFormat.getCurrencyInstance().format(amazingService)
                val total = NumberFormat.getCurrencyInstance().format(amazingServiceTotal)
                tipAmount.text = getString(R.string.tip_amount, tip)
                totalSale.text = getString(R.string.totalTip_amount, total)
            }
            goodService -> {
                val goodService = GoodService(costofService, roundState).gratuity()
                val goodServiceTotal = GoodService(costofService, roundState).totalPrice()
                val tip = NumberFormat.getCurrencyInstance().format(goodService)
                val total = NumberFormat.getCurrencyInstance().format(goodServiceTotal)
                tipAmount.text = getString(R.string.tip_amount, tip)
                totalSale.text = getString(R.string.totalTip_amount, total)
            }
            okService -> {
                val okService = OkayService(costofService, roundState).gratuity()
                val okServiceTotal = OkayService(costofService, roundState).totalPrice()
                val tip = NumberFormat.getCurrencyInstance().format(okService)
                val total = NumberFormat.getCurrencyInstance().format(okServiceTotal)
                tipAmount.text = getString(R.string.tip_amount, tip)
                totalSale.text = getString(R.string.totalTip_amount, total)
            }
        }

    }

}

//I created one parent class with three subclasses for solving gratuity and total sales.
//Boolean is use for tracking roundup state if its true it will return a rounded number or result if false not.
abstract class TipTime(var costofService: Double) {
    abstract fun totalPrice(): Double
    abstract fun gratuity(): Double


}

open class AmazingService(costofService: Double, private val round_state: Boolean) :
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

open class GoodService(costofService: Double, private val round_state: Boolean) :
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

open class OkayService(costofService: Double, private val round_state: Boolean) :
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