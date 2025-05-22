package group.one.sos.presentation.screens.settings


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import group.one.sos.R
import group.one.sos.presentation.components.BottomNavBar
import group.one.sos.presentation.navigation.NavigationRoute


@Composable
fun SettingsScreen(modifier: Modifier = Modifier, navController: NavController) {
    var selectedTheme by remember { mutableStateOf("System Default") }
    var updateFrequency by remember { mutableStateOf("10 seconds") }

    val themeOptions = listOf("Light", "Dark", "System Default")
    val frequencyOptions = listOf("5 seconds", "10 seconds", "30 seconds", "1 minute")

    Scaffold(
        topBar = { SettingsScreenTopBar() },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = NavigationRoute.Settings.route
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Settings", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            // Appearance Setting
            Text("Appearance", style = MaterialTheme.typography.labelLarge)
            DropdownSetting(
                selectedOption = selectedTheme,
                options = themeOptions,
                onOptionSelected = { selectedTheme = it }
            )

            Spacer(Modifier.height(16.dp))

            // Location Update Frequency
            Text("Location Update Frequency", style = MaterialTheme.typography.labelLarge)
            DropdownSetting(
                selectedOption = updateFrequency,
                options = frequencyOptions,
                onOptionSelected = { updateFrequency = it }
            )

            Spacer(Modifier.height(24.dp))

            SearchRadiusSetting()
            Spacer(modifier = Modifier.height(24.dp))
            DefaultResponderSetting()
            Spacer(modifier = Modifier.height(32.dp))
            ProjectInfoSection()
        }
    }
}
//@Composable
//fun SettingsScreen(modifier: Modifier = Modifier, navController: NavController) {
//    Scaffold(
//        topBar = { SettingsScreenTopBar() },
//        bottomBar = {
//            BottomNavBar(
//                navController = navController,
//                currentRoute = NavigationRoute.Settings.route
//            )
//        }
//    ) { innerPadding ->
//        Column(modifier = modifier.padding(innerPadding).padding(12.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
//            SearchRadiusSetting()
//            Spacer(modifier =  Modifier.height(24.dp))
//            DefaultResponderSetting()
//            Spacer(modifier = Modifier.height(32.dp))
//            ProjectInfoSection()
//        }
//    }
//}

@Composable
private fun SettingsScreenTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.settings_header),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun SearchRadiusSetting() {
    var radius by remember { mutableStateOf(5) }

    Column {
        Text(
            text = "Search Radius (km)",
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Button(onClick = { if (radius > 1) radius-- }) {
                Text("-")
            }
            Text(
                text = "$radius km",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = { radius++ }) {
                Text("+")
            }
        }
    }
}

@Composable
private fun DefaultResponderSetting() {
    val options = listOf("Police", "Medical", "Fire", "None")
    var selected by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Default Emergency Response",
            style = MaterialTheme.typography.titleMedium
        )
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selected)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            selected = it
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProjectInfoSection() {
    Column {
        Text(
            text = "About This App",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "This app provides emergency assistance access. Built by a dedicated student team for community safety.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Team Members:",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text("• Elochukwu Okafor, Mobile Lead", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Ogunleye Emmanuel, Backend Lead", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Nwolisa Vincent, Mobile Engineer", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Serif Sani, Backend Engineer", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Ilelaboye Joshua, Mobile Engineer", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Emmanuel Felix, Backend Engineer", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "• Olajide Oluwatomisin, Data Collection Team",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Enikanologbon Emmanuel, Slides Team", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Adigwe Favour, UI/UX", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Bakare Afolabi, Slides Team", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Bada Waheed Babajide, Slides Team", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Babatunde Emmanuel, Slides Team", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun NotificationToggleSetting() {
    var enabled by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Enable Notifications", style = MaterialTheme.typography.bodyLarge)
        Switch(
            checked = enabled, onCheckedChange = { enabled = it }, thumbContent = if (enabled) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        })
    }
}

@Composable
fun DropdownSetting(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedOption)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
