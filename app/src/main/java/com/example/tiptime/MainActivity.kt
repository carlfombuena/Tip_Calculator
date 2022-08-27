package com.example.tiptime
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.tiptime.databinding.ActivityMainBinding
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var button:Button
    private var tip_state:String?=null
    private var round_state:Boolean=true
    private val amazing_service="amazing_service"
    private val good_service="good_service"
    private val ok_service="ok_service"
    private var tipAmount:TextView?=null
    private var totalSale:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button=binding.calculateButton

        if (savedInstanceState != null) {

        }
        val roundoffsw=binding.roundUpSwitch
        roundoffsw?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                round_state = true
                println("button is true")

            } else {
                round_state = false
                println("button is false")
            }
        }



        button.setOnClickListener {
            setViewElement()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }
    fun onRadioButtonClicked(view: View) {

        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.option_twenty_percent ->
                    if (checked) {
                        tip_state=amazing_service
                        Toast.makeText(this,"20 percent",Toast.LENGTH_SHORT).show()


                    }
                R.id.option_eighteen_percent ->
                    if (checked) {
                        // Ninjas rule
                        Toast.makeText(this,"18 percent",Toast.LENGTH_SHORT).show()
                        tip_state=good_service

                    }
                R.id.option_fifteen_percent ->
                    if (checked) {
                        // Ninjas rule
                        Toast.makeText(this,"15 percent",Toast.LENGTH_SHORT).show()
                        tip_state=ok_service

                    }
            }
        }
    }

    fun setViewElement(){
        tipAmount=binding.tipResult
        totalSale=binding.totaltipResult
        val inputText=binding.costOfService.text.toString()

        val input= inputText.toDoubleOrNull()
        val costofService=input
        if (costofService==null){
            tipAmount?.setText("Tip Amount: ")
            totalSale?.setText("Total Sale: ")
            return
        }
        when(tip_state){

            amazing_service->{


                val amazingService=amazingService(costofService).gratuity()
                val amazingServiceTotal=amazingService(costofService).totalPrice()



                if (round_state==true){

                    tipAmount?.setText("Tip Amount: $${amazingService.roundToInt()}")
                    totalSale?.setText("Total Sale: $${amazingServiceTotal.roundToInt()}")
                }else{
                    tipAmount?.setText("Tip Amount: $$amazingService")
                    totalSale?.setText("Total Sale: $$amazingServiceTotal")

                }


            }
            good_service->{


                val goodService=goodService(costofService).gratuity()
                val goodServiceTotal=goodService(costofService).totalPrice()


                if (round_state==true){

                    tipAmount?.setText("Tip Amount: $${goodService.roundToInt()}")
                    totalSale?.setText("Total Sale: $${goodServiceTotal.roundToInt()}")
                }else{
                    tipAmount?.setText("Tip Amount: $$goodService")
                    totalSale?.setText("Total Sale: $$goodServiceTotal")

                }
            }
            ok_service->{


                val okService=okayService(costofService).gratuity()
                val okServiceTotal=okayService(costofService).totalPrice()

                tipAmount?.setText("Tip Amount: $$okService")
                totalSale?.setText("Total Sale: $$okServiceTotal")
                if (round_state==true){

                    tipAmount?.setText("Tip Amount: $${okService.roundToInt()}")
                    totalSale?.setText("Total Sale: $${okServiceTotal.roundToInt()}")
                }else{
                    tipAmount?.setText("Tip Amount: $$okService")
                    totalSale?.setText("Total Sale: $$okServiceTotal")

                }
            }
        }

    }

}
abstract class TipTime( var costofService: Double){

    abstract fun totalPrice(): Double
    abstract fun gratuity(): Double


}
class amazingService(costofService: Double) : TipTime(costofService){
    val amazing= .20

    override fun gratuity(): Double{
        return costofService * amazing
    }
    override fun totalPrice():Double{
        return costofService+(costofService*amazing)
    }
}
open class goodService(costofService: Double) : TipTime(costofService){

    val good= .18
    override fun gratuity(): Double{
        return costofService * good
    }
    override fun totalPrice():Double{
        return costofService+(costofService*good)
    }
}
open class okayService(costofService: Double) : TipTime(costofService){
    val okay= .15
    override fun gratuity(): Double{
        return costofService * okay }
    override fun totalPrice():Double{
        return costofService+(costofService*okay)
    }

}