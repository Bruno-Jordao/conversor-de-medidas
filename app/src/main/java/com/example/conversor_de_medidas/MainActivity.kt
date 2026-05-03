package com.example.conversor_de_medidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.conversor_de_medidas.ui.theme.ConversordemedidasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ConversordemedidasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelaConversor()
                    }
            }
        }
    }
}

@Composable
fun TelaConversor() {

    var valor by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    var opcao by remember { mutableStateOf("Metros para Centímetros") }

    val opcoes = listOf(
        "Metros para Centímetros",
        "Centímetros para Metros",
        "Metros para Quilômetros",
        "Quilômetros para Metros"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Digite o valor") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenuBox(opcoes, opcao) {
            opcao = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val numero = valor.toDoubleOrNull()

            resultado = if (numero != null) {
                when (opcao) {
                    "Metros para Centímetros" -> Conversor.metrosParaCentimetros(numero).toString()
                    "Centímetros para Metros" -> Conversor.centimetrosParaMetros(numero).toString()
                    "Metros para Quilômetros" -> Conversor.metrosParaQuilometros(numero).toString()
                    "Quilômetros para Metros" -> Conversor.quilometrosParaMetros(numero).toString()
                    else -> ""
                }
            } else {
                "Valor inválido"
            }

        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Resultado: $resultado")
    }
}

@Composable
fun DropdownMenuBox(
    opcoes: List<String>,
    selecionado: String,
    onSelecionar: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(selecionado)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            opcoes.forEach { opcao ->
                DropdownMenuItem(
                    text = { Text(opcao) },
                    onClick = {
                        onSelecionar(opcao)
                        expanded = false
                    }
                )
            }
        }
    }
}