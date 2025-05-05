package group.one.sos.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            // TODO: add contents here
            Text(text = "Home, Vinz and Josh y'all can continue from here")
        }
    }
}