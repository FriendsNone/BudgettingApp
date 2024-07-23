package io.catto.financely

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import io.catto.financely.ui.HomeScreen
import io.catto.financely.ui.RecordScreen
import io.catto.financely.ui.ReportScreen
import io.catto.financely.ui.theme.FinancelyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FinancelyTheme {
                var selectedItem by remember { mutableStateOf(0) }

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    stringResource(R.string.app_name),
                                    fontWeight = FontWeight.Bold,
                                )
                            },
                        )
                    }, bottomBar = {
                        NavigationBar {
                            NavigationBarItem(icon = {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "add icon"
                                )
                            },
                                label = { Text("Add") },
                                selected = selectedItem == 2,
                                onClick = { selectedItem = 2 })
                            NavigationBarItem(icon = {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = "home icon"
                                )
                            },
                                label = { Text("Home") },
                                selected = selectedItem == 0,
                                onClick = { selectedItem = 0 })
                            NavigationBarItem(icon = {
                                Icon(
                                    imageVector = Icons.Default.ShowChart,
                                    contentDescription = "reports icon"
                                )
                            },
                                label = { Text("Reports") },
                                selected = selectedItem == 1,
                                onClick = { selectedItem = 1 })
                        }
                    }, content = {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxSize()
                                .navigationBarsPadding()
                                .statusBarsPadding()
                                .padding(top = 64.dp, bottom = 80.dp)
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            when (selectedItem) {
                                0 -> HomeScreen(viewModel)
                                1 -> ReportScreen(viewModel)
                                2 -> RecordScreen(viewModel) { selectedItem = 0 }
                            }
                        }
                    }
                )
            }
        }
    }
}