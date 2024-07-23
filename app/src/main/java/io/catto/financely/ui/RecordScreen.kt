package io.catto.financely.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.catto.financely.MainViewModel
import io.catto.financely.data.models.Record
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(viewModel: MainViewModel, onDismiss: () -> Unit) {
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    var date by remember { mutableStateOf(LocalDate.now()) }
    var time by remember { mutableStateOf(LocalTime.now()) }

    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }

    val typeOptions = listOf("Expense", "Income")
    var typeExpanded by remember { mutableStateOf(false) }
    var type by remember { mutableStateOf(typeOptions[0]) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Outlined.Category, "Category icon") },
            value = category,
            onValueChange = { category = it },
            label = { Text(text = "Category") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Outlined.Notes, "Description icon") },
            maxLines = 3,
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Outlined.Payments, "Amount icon") },
            value = amount,
            onValueChange = { amount = it },
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Outlined.Payment, "Payment Method icon") },
            value = paymentMethod,
            onValueChange = { paymentMethod = it },
            label = { Text(text = "Payment Method") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = typeExpanded,
            onExpandedChange = { typeExpanded = !typeExpanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = type,
                onValueChange = {},
                label = { Text("Type") },
                leadingIcon = { Icon(Icons.Outlined.SwapVert, "Type icon") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = typeExpanded,
                onDismissRequest = { typeExpanded = false },
            ) {
                typeOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            type = selectionOption
                            typeExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(date),
                    onValueChange = { date = LocalDate.parse(it) },
                    leadingIcon = { Icon(Icons.Outlined.DateRange, "Date icon") },
                    label = { Text(text = "Date") },
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0f)
                        .clickable {
                            dateDialogState.show()
                        }
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    . padding(start = 8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = DateTimeFormatter.ofPattern("HH:mm").format(time),
                    onValueChange = { time = LocalTime.parse(it) },
                    leadingIcon = { Icon(Icons.Outlined.Schedule, "Clock icon") },
                    label = { Text(text = "Time") },
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0f)
                        .clickable {
                            timeDialogState.show()
                        }
                )
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        if (category.isNotBlank() && description.isNotBlank() && amount.isNotBlank() && paymentMethod.isNotBlank()) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.insertRecord(
                        Record(
                            date = Date.from(
                                LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toInstant()
                            ),
                            category = category,
                            description = description,
                            amount = amount.toDouble(),
                            paymentMethod = paymentMethod,
                            type = type,
                        )
                    )
                    onDismiss()
                }
            ) {
                Text(text = "Add record")
            }
        }
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
                dateInactiveBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    elevation = 4.dp
                ),
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
                inactiveBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    elevation = 5.dp
                ),
                activeTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                inactiveTextColor = MaterialTheme.colorScheme.onSurface,
                inactivePeriodBackground = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    elevation = 5.dp
                ),
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