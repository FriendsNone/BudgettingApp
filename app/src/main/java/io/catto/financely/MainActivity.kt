package io.catto.financely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.catto.financely.data.TransactionDatabase
import io.catto.financely.data.TransactionRepository
import io.catto.financely.ui.theme.FinancelyTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(TransactionRepository(TransactionDatabase.getDatabaseInstance(this)))
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinancelyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    var date by remember { mutableStateOf(LocalDate.now()) }
    var time by remember { mutableStateOf(LocalTime.now()) }

    var category by remember { mutableStateOf("Uncategorized") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("0.00") }
    var paymentMethod by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Expense") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.Center
    ) {
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text(text = "Category") },
            modifier = Modifier.padding(16.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description") },
            modifier = Modifier.padding(16.dp),
            maxLines = 5
        )
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(text = "Amount") },
            modifier = Modifier.padding(16.dp),
            prefix = { Text(text = "₱") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = paymentMethod,
            onValueChange = { paymentMethod = it },
            label = { Text(text = "Payment Method") },
            modifier = Modifier.padding(16.dp)
        )
        

//        Button(onClick = {
//            dateDialogState.show()
//        }) {
//            Icon(
//                Icons.Default.DateRange,
//                contentDescription = "Date",
//                modifier = Modifier.size(ButtonDefaults.IconSize)
//            )
//            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//            Text(text = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(date))
//        }
//        Spacer(modifier = Modifier.padding(16.dp))
//        Button(onClick = {
//            timeDialogState.show()
//        }) {
//            Icon(
//                Icons.Default.Schedule,
//                contentDescription = "Time",
//                modifier = Modifier.size(ButtonDefaults.IconSize)
//            )
//            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//            Text(text = DateTimeFormatter.ofPattern("HH:mm").format(time))
//        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(
                text = "OK",
                textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
            )
            negativeButton(
                text = "Cancel",
                textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
            )
        },
        backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 4.dp),
    ) {
        datepicker(
            initialDate = date,
            title = "Pick a date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 4.dp),
                headerTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                calendarHeaderTextColor = MaterialTheme.colorScheme.onSurface,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.primary,
                dateInactiveBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 4.dp),
                dateActiveTextColor = MaterialTheme.colorScheme.onPrimary,
                dateInactiveTextColor = MaterialTheme.colorScheme.onSurface,
            )
        ) {
            date = it
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(
                text = "OK",
                textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
            )
            negativeButton(
                text = "Cancel",
                textStyle = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
            )
        },
        backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 4.dp),
    ) {
        timepicker(
            initialTime = time,
            title = "Pick a time",
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                inactiveBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 5.dp),
                activeTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                inactiveTextColor = MaterialTheme.colorScheme.onSurface,
                inactivePeriodBackground = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 5.dp),
                selectorColor = MaterialTheme.colorScheme.primary,
                selectorTextColor = MaterialTheme.colorScheme.onPrimary,
                headerTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                borderColor = MaterialTheme.colorScheme.outline
            )
        ) {
            time = it
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinancelyTheme {
        Greeting()
    }
}