package com.example.calculadorajuroscompostos

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat
import java.util.*
import kotlin.math.pow

class PatrimonioCalculado : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_patrimonio_calculado)

        val btnVoltar: Button = findViewById(R.id.btnVoltar)

        try {
            val taxa = intent.getStringExtra("taxa")?.toDouble()
            val inflacao = intent.getStringExtra("inflacao")?.toDouble()
            val aporte = intent.getStringExtra("aporte")?.toDouble()
            val tempo = intent.getStringExtra("tempo")?.toInt()
            val tvPatrimonio: TextView = findViewById(R.id.tvPatrimonio)
            val tvPoderDeCompra: TextView = findViewById(R.id.tvPoderDeCompra)
            var resultadoPatrimonio: Resultados = Resultados()
            var resultadoPoderDeCompra: Resultados = Resultados()

            resultadoPatrimonio = calculaPatrimonio(taxa, aporte, tempo)
            resultadoPoderDeCompra = calculaPoderDeCompra(taxa, aporte, tempo, inflacao)

            if(resultadoPatrimonio.patrimonio == "NaN" && resultadoPoderDeCompra.poderDeCompra == "NaN"){
                tvPatrimonio.text = "R$0"
                tvPoderDeCompra.text = "R$0"
            }
            else{
                tvPatrimonio.text = "R$${resultadoPatrimonio.patrimonio}"
                tvPoderDeCompra.text = "R$${resultadoPoderDeCompra.poderDeCompra}"
            }

        } catch (e: Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }

        btnVoltar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun calculaPoderDeCompra(taxa: Double?, aporte: Double?, tempo: Int?, inflacao: Double?): Resultados{
        val resultado: Resultados = Resultados()
        val taxaSobre100 = (taxa!!/100)/12
        val numerador = ((1+taxaSobre100).pow(tempo!!))-1
        val primeiraParteEquacao = (aporte!! * (numerador / taxaSobre100))
        val segundaParteEquacao = (1+taxaSobre100)
        val patrimonio = primeiraParteEquacao*segundaParteEquacao
        val denominador = (1+inflacao!!/100).pow(tempo /12)
        resultado.poderDeCompra = (patrimonio/denominador).format()
        return resultado
    }

    private fun calculaPatrimonio(taxa: Double?, aporte: Double?, tempo: Int?): Resultados {
        val resultado: Resultados = Resultados()
        val taxaSobre100 = (taxa!!/100)/12
        val numerador = ((1+taxaSobre100).pow(tempo!!))-1
        val primeiraParteEquacao = (aporte!! * (numerador / taxaSobre100))
        val segundaParteEquacao = (1+taxaSobre100)
        resultado.patrimonio = (primeiraParteEquacao*segundaParteEquacao).format()
        return resultado
    }

    private fun Double.format(): String {
        val df = DecimalFormat("#,###.##")
        return df.format(this)
    }
    data class Resultados(
        var patrimonio: String = "",
        var poderDeCompra: String = ""
    )

}