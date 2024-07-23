package io.catto.financely.ui

import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.catto.financely.MainViewModel
import io.catto.financely.data.models.Record
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val records = viewModel.getAllRecords().observeAsState()

    val totalIncome = viewModel.getSumOfRecordsOfType("Income").observeAsState()
    val totalExpense = viewModel.getSumOfRecordsOfType("Expense").observeAsState()
    val totalBalance = totalIncome.value?.minus(totalExpense.value ?: 0.00)

    Column {
        if (records.value.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¯\\_(ツ)_/¯",
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.displayLarge.fontSize,
                    fontStyle = MaterialTheme.typography.displayLarge.fontStyle,
                    fontWeight = MaterialTheme.typography.displayLarge.fontWeight,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No records yet",
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    fontStyle = MaterialTheme.typography.labelMedium.fontStyle,
                    fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        } else {
            val dialogState = remember { mutableStateOf(false) }

            LazyRow(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "₱${String.format("%.2f", totalBalance?: 0.00)}",
                                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
                            )
                            Text(
                                text = "Total Balance",
                                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                                fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
                                fontWeight = MaterialTheme.typography.labelSmall.fontWeight
                            )
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "₱${String.format("%.2f", totalExpense.value?: 0.00)}",
                                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
                            )
                            Text(
                                text = "Total Expense",
                                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                                fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
                                fontWeight = MaterialTheme.typography.labelSmall.fontWeight
                            )
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "₱${String.format("%.2f", totalIncome.value?: 0.00)}",
                                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
                            )
                            Text(
                                text = "Total Income",
                                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                                fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
                                fontWeight = MaterialTheme.typography.labelSmall.fontWeight
                            )
                        }
                    }
                }
            }
            Divider()
            LazyColumn {
                items(records.value!!.size, { index -> records.value!![index].id }) { index ->
                    val dismissState = rememberDismissState()

                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        dialogState.value = true
                        val record = records.value!![index]

                        ConfirmDialog(dialogState, dismissState, record, viewModel, coroutineScope)
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val swipeBackground by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> Color.Transparent
                                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.error
                                    DismissValue.DismissedToEnd -> MaterialTheme.colorScheme.primary
                                }
                            )
                            val swipeIconColor by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> Color.Transparent
                                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.onError
                                    DismissValue.DismissedToEnd -> MaterialTheme.colorScheme.onPrimary
                                }
                            )
                            val alignment = when (dismissState.targetValue) {
                                DismissValue.Default -> Alignment.CenterStart
                                DismissValue.DismissedToStart -> Alignment.CenterEnd
                                DismissValue.DismissedToEnd -> Alignment.CenterStart
                            }
                            val icon = when (dismissState.targetValue) {
                                DismissValue.Default -> null
                                DismissValue.DismissedToStart -> Icons.Filled.Delete
                                DismissValue.DismissedToEnd -> Icons.Filled.Edit
                            }

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(swipeBackground)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                                if (icon != null) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = "Action",
                                        tint = swipeIconColor
                                    )
                                }
                            }
                        },
                        dismissContent = {
                            ListItem(
                                headlineContent = { Text(records.value!![index].category) },
                                overlineContent = { Text("₱${records.value!![index].amount} (${records.value!![index].paymentMethod})") },
                                supportingContent = { Text(records.value!![index].description) },
                                leadingContent = {
                                    if (records.value!![index].type == "Income") {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowDownward,
                                            contentDescription = "Income",
                                            tint = Color.Green
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowUpward,
                                            contentDescription = "Expense",
                                            tint = Color.Red
                                        )
                                    }
                                },
                                trailingContent = { Text(humanReadableDate(records.value!![index].date)) },
                            )
                            Divider()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfirmDialog(
    dialogState: MutableState<Boolean>,
    dismissState: DismissState,
    record: Record,
    viewModel: MainViewModel,
    coroutineScope: CoroutineScope
) {
    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = {
                coroutineScope.launch {
                    dialogState.value = false
                    dismissState.reset()
                }
            },

            title = {
                Text("Delete Record")
            },
            text = {
                Text("Are you sure you want to delete this record?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteRecord(record)
                        coroutineScope.launch {
                            dialogState.value = false
                            dismissState.reset()
                        }
                    }
                ) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            dialogState.value = false
                            dismissState.reset()
                        }
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun humanReadableDate(date: Date): String {
    val now = LocalDateTime.now()
    val recordDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

    return if (recordDate.year == now.year && recordDate.month == now.month && recordDate.dayOfMonth == now.dayOfMonth) {
        val hoursAgo = now.hour - recordDate.hour
        if (hoursAgo == 0) {
            val minutesAgo = now.minute - recordDate.minute
            if (minutesAgo == 0) {
                val secondsAgo = now.second - recordDate.second
                if (secondsAgo == 0) {
                    "Just now"
                } else {
                    "$secondsAgo seconds ago"
                }
            } else {
                "$minutesAgo minutes ago"
            }
        } else {
            "$hoursAgo hours ago"
        }
    } else if (recordDate.year == now.year && recordDate.month == now.month && recordDate.dayOfMonth == now.dayOfMonth - 1) {
        "Yesterday"
    } else if (recordDate.year == now.year && recordDate.month == now.month && recordDate.dayOfMonth > now.dayOfMonth - 7) {
        val dayOfWeek = recordDate.dayOfWeek.toString().toLowerCase().capitalize()
        dayOfWeek
    } else if (recordDate.year == now.year) {
        val month = recordDate.month.toString().toLowerCase().capitalize()
        val dayOfMonth = recordDate.dayOfMonth
        "$month $dayOfMonth"
    } else {
        val month = recordDate.month.toString().toLowerCase().capitalize()
        val dayOfMonth = recordDate.dayOfMonth
        val year = recordDate.year
        "$month $dayOfMonth, $year"
    }
}