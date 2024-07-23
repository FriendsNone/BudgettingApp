package io.catto.financely.ui

import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.composables.BarGraph
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.catto.financely.MainViewModel
import io.catto.financely.data.models.Record
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(viewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val startDateDialogState = rememberMaterialDialogState()
    val endDateDialogState = rememberMaterialDialogState()

//    val startDate = LocalDateTime.now().minusDays(60).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
//    val endDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    var startDate by remember { mutableStateOf(LocalDate.now().minusDays(30)) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }

    val records = viewModel.getRecordsBetweenDate(
        startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    ).observeAsState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                OutlinedTextField(
                    value = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(startDate),
                    onValueChange = { startDate = LocalDate.parse(it) },
                    leadingIcon = { Icon(Icons.Outlined.DateRange, "Date icon") },
                    label = { Text(text = "Date") },
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0f)
                        .clickable {
                            startDateDialogState.show()
                        }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                OutlinedTextField(
                    value = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(endDate),
                    onValueChange = { endDate = LocalDate.parse(it) },
                    leadingIcon = { Icon(Icons.Outlined.DateRange, "Date icon") },
                    label = { Text(text = "Date") },
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0f)
                        .clickable {
                            endDateDialogState.show()
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
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
            val incomeX = mutableListOf<String>()   // date in mm/dd format
            val incomeY = mutableListOf<Double>()
            var incomeLabel by remember { mutableStateOf("") }

            val expenseX = mutableListOf<String>()  // date in mm/dd format
            val expenseY = mutableListOf<Double>()
            var expenseLabel by remember { mutableStateOf("") }

            records.value!!.forEach {
                val date = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                if (it.type == "Income") {
                    incomeX.add("${date.monthValue}/${date.dayOfMonth}")
                    incomeY.add(it.amount)
                } else {
                    expenseX.add("${date.monthValue}/${date.dayOfMonth}")
                    expenseY.add(it.amount)
                }
            }

            val style = LineGraphStyle(
                colors = LinearGraphColors(
                    lineColor = MaterialTheme.colorScheme.tertiary,
                    pointColor = MaterialTheme.colorScheme.tertiary,
                    clickHighlightColor = MaterialTheme.colorScheme.primary,
                    crossHairColor = MaterialTheme.colorScheme.primary,
                    fillGradient = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.0f)
                        )
                    )
                ),
                visibility = LinearGraphVisibility(
                    isCrossHairVisible = true,
                    isYAxisLabelVisible = true,
                    isXAxisLabelVisible = true,
                    isGridVisible = true,
                    isHeaderVisible = true
                )
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    LineGraph(
                        header = {
                            Text(
                                text = "Expense ${expenseLabel}",
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        xAxisData = expenseX.reversed().map { GraphData.String(it) },
                        yAxisData = expenseY.reversed(),
                        style = style,
                        onPointClicked = { point ->
                            expenseLabel = "for ${point.first} = ₱${point.second}"
                        }
                    )
                }

                Card(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    LineGraph(
                        header = {
                            Text(
                                text = "Income ${incomeLabel}",
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        xAxisData = incomeX.reversed().map { GraphData.String(it) },
                        yAxisData = incomeY.reversed(),
                        style = style,
                        onPointClicked = { point ->
                            incomeLabel = "for ${point.first} = ₱${point.second}"
                        }
                    )
                }
            }

//            LazyColumn {
//                items(records.value!!.size, { index -> records.value!![index].id }) { index ->
//                    ListItem(
//                        headlineContent = { Text(records.value!![index].category) },
//                        overlineContent = { Text("₱${records.value!![index].amount} (${records.value!![index].paymentMethod})") },
//                        supportingContent = { Text(records.value!![index].description) },
//                        leadingContent = {
//                            if (records.value!![index].type == "Income") {
//                                Icon(
//                                    imageVector = Icons.Filled.ArrowDownward,
//                                    contentDescription = "Income",
//                                    tint = Color.Green
//                                )
//                            } else {
//                                Icon(
//                                    imageVector = Icons.Filled.ArrowUpward,
//                                    contentDescription = "Expense",
//                                    tint = Color.Red
//                                )
//                            }
//                        },
//                        trailingContent = { Text(humanReadableDate(records.value!![index].date)) },
//                    )
//                    Divider()
//                }
//            }
        }

        MaterialDialog(
            dialogState = startDateDialogState,
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
                initialDate = startDate,
                title = "Pick a start date",
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
                startDate = it
            }
        }

        MaterialDialog(
            dialogState = endDateDialogState,
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
                initialDate = endDate,
                title = "Pick an end date",
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        elevation = 4.dp
                    ),
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
                endDate = it
            }
        }
    }
}

//private fun humanReadableDate(date: Date): String {
//    val now = LocalDateTime.now()
//    val recordDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
//
//    return if (recordDate.year == now.year && recordDate.month == now.month && recordDate.dayOfMonth == now.dayOfMonth) {
//        val hoursAgo = now.hour - recordDate.hour
//        if (hoursAgo == 0) {
//            val minutesAgo = now.minute - recordDate.minute
//            if (minutesAgo == 0) {
//                val secondsAgo = now.second - recordDate.second
//                if (secondsAgo == 0) {
//                    "Just now"
//                } else {
//                    "$secondsAgo seconds ago"
//                }
//            } else {
//                "$minutesAgo minutes ago"
//            }
//        } else {
//            "$hoursAgo hours ago"
//        }
//    } else if (recordDate.year == now.year && recordDate.month == now.month && recordDate.dayOfMonth == now.dayOfMonth - 1) {
//        "Yesterday"
//    } else if (recordDate.year == now.year && recordDate.month == now.month && recordDate.dayOfMonth > now.dayOfMonth - 7) {
//        val dayOfWeek = recordDate.dayOfWeek.toString().toLowerCase().capitalize()
//        dayOfWeek
//    } else if (recordDate.year == now.year) {
//        val month = recordDate.month.toString().toLowerCase().capitalize()
//        val dayOfMonth = recordDate.dayOfMonth
//        "$month $dayOfMonth"
//    } else {
//        val month = recordDate.month.toString().toLowerCase().capitalize()
//        val dayOfMonth = recordDate.dayOfMonth
//        val year = recordDate.year
//        "$month $dayOfMonth, $year"
//    }
//}