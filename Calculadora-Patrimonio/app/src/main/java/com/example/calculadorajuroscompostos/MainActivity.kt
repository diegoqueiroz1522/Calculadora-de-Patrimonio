package com.example.calculadorajuroscompostos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_main)

        val btnCalcularPatrimonio: Button = findViewById(R.id.btnCalcularPatrimonio)
        val etTaxaAoAno: EditText = findViewById(R.id.etTaxaAoAno)
        val etInflacaoAoAno: EditText = findViewById(R.id.etInflacaoAoAno)
        val etAportePorMes: EditText = findViewById(R.id.etAportePorMes)
        val etTempoEmMeses: EditText = findViewById(R.id.etTempoEmMeses)

        btnCalcularPatrimonio.setOnClickListener{
            if(etTaxaAoAno.text.isEmpty() || etInflacaoAoAno.text.isEmpty() || etAportePorMes.text.isEmpty() || etTempoEmMeses.text.isEmpty()){
                Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_LONG).show()
            }
            else{
                val intent = Intent(this, PatrimonioCalculado::class.java).apply {
                    putExtra("taxa", etTaxaAoAno.text.toString())
                    putExtra("inflacao", etInflacaoAoAno.text.toString())
                    putExtra("aporte", etAportePorMes.text.toString())
                    putExtra("tempo", etTempoEmMeses.text.toString())
                }
                startActivity(intent)
            }
        }
    }
}

