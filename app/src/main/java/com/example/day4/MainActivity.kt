package com.example.day4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.day4.ui.theme.Day4Theme

enum class ConversionType(val label: String) {
    MetersToCentimeters("m to cm"),
    CentimetersToMeters("cm to m"),
    KilogramsToGrams("kg to g"),
    GramsToKilograms("g to kg")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Day4Theme {
                UnitConverterApp()
            }
        }
    }
}

@Composable
fun UnitConverterApp() {
    var inputText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var conversionType by remember { mutableStateOf(ConversionType.MetersToCentimeters) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ConversionType.entries.forEach { type ->
                Button(
                    onClick = { conversionType = type },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f), // Equal weight for all buttons
                    enabled = conversionType != type,
                    shape = MaterialTheme.shapes.small // Rectangular shape
                ) {
                    Text(type.label)
                }
            }
        }

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter value") },
            trailingIcon = { Icon(imageVector = Icons.Default.MonitorWeight, contentDescription = null) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = {
                resultText = convert(inputText, conversionType)
            }),

        )

        OutlinedTextField(
            value = resultText,
            onValueChange = {},
            label = { Text("Result") },
            enabled = false,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = {
                resultText = convert(inputText, conversionType)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Convert")
        }
    }
}
private fun convert(input: String, conversionType: ConversionType): String {
    return when (conversionType) {
        ConversionType.MetersToCentimeters -> convertMetersToCentimeters(input) ?: "Invalid input"
        ConversionType.CentimetersToMeters -> convertCentimetersToMeters(input) ?: "Invalid input"
        ConversionType.KilogramsToGrams -> convertKilogramsToGrams(input) ?: "Invalid input"
        ConversionType.GramsToKilograms -> convertGramsToKilograms(input) ?: "Invalid input"
    }
}

private fun convertMetersToCentimeters(meters: String): String? {
    return try {
        val metersValue = meters.toDouble()
        val centimeters = metersValue * 100
        "%.2f".format(centimeters)
    } catch (e: NumberFormatException) {
        null
    }
}

private fun convertCentimetersToMeters(centimeters: String): String? {
    return try {
        val centimetersValue = centimeters.toDouble()
        val meters = centimetersValue / 100
        "%.2f".format(meters)
    } catch (e: NumberFormatException) {
        null
    }
}

private fun convertKilogramsToGrams(kilograms: String): String? {
    return try {
        val kilogramsValue = kilograms.toDouble()
        val grams = kilogramsValue * 1000
        "%.2f".format(grams)
    } catch (e: NumberFormatException) {
        null
    }
}

private fun convertGramsToKilograms(grams: String): String? {
    return try {
        val gramsValue = grams.toDouble()
        val kilograms = gramsValue / 1000
        "%.2f".format(kilograms)
    } catch (e: NumberFormatException) {
        null
    }
}