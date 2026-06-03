package com.example.conversor_de_medidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
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

                    val navController = rememberNavController()

                    var historico by remember {
                        mutableStateOf("Nenhuma conversão realizada")
                    }

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {

                        composable("home") {

                            TelaConversor(
                                navController = navController,
                                onSalvarHistorico = {
                                    historico = it
                                }
                            )
                        }

                        composable("historico") {

                            TelaHistorico(
                                navController = navController,
                                historico = historico
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TelaConversor(
    navController: NavHostController,
    onSalvarHistorico: (String) -> Unit
) {

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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Conversor de Medidas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

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

                    "Metros para Centímetros" ->
                        Conversor.metrosParaCentimetros(numero).toString()

                    "Centímetros para Metros" ->
                        Conversor.centimetrosParaMetros(numero).toString()

                    "Metros para Quilômetros" ->
                        Conversor.metrosParaQuilometros(numero).toString()

                    "Quilômetros para Metros" ->
                        Conversor.quilometrosParaMetros(numero).toString()

                    else -> ""
                }

            } else {
                "Valor inválido"
            }

            onSalvarHistorico(
                "$valor | $opcao = $resultado"
            )

        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Resultado: $resultado")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.navigate("historico")
        }) {
            Text("Ver Histórico")
        }
    }
}

@Composable
fun TelaHistorico(
    navController: NavHostController,
    historico: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Histórico",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = historico)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Voltar")
        }
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

        Button(onClick = {
            expanded = true
        }) {
            Text(selecionado)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            opcoes.forEach { opcao ->

                DropdownMenuItem(
                    text = {
                        Text(opcao)
                    },

                    onClick = {
                        onSelecionar(opcao)
                        expanded = false
                    }
                )
            }
        }
    }
}