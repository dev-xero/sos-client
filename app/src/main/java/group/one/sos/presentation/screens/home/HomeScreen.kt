package group.one.sos.presentation.screens.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import group.one.sos.R
import group.one.sos.presentation.components.BottomNavBar
import group.one.sos.presentation.navigation.NavigationRoute
import group.one.sos.presentation.theme.DarkGreen
import group.one.sos.presentation.theme.LightGreen
import group.one.sos.presentation.theme.LimeGreen
import group.one.sos.presentation.theme.Maroon
import group.one.sos.presentation.theme.OliveGreen
import group.one.sos.presentation.theme.PaleMaroon
import group.one.sos.presentation.theme.Primary
import group.one.sos.presentation.theme.White

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()

    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()
    val error by viewModel.error.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(error!!)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = { HomeScreenTopBar() },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = NavigationRoute.Home.route
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SOSButton(onClick = { viewModel.sendSOSRequest() }, disabled = (uiState == UiState.Loading || uiState == UiState.Fetching))
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(when (uiState) {
                    is UiState.Base -> R.string.tap_sos
                    is UiState.Fetching -> R.string.fetching_responders
                    is UiState.Loading -> R.string.getting_location
                }),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(240.dp)
            )
        }
    }
}

@Composable
private fun HomeScreenTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.emergency_response),
            style = MaterialTheme.typography.titleMedium
        )

        LocationChip()
    }
}

@Composable
private fun LocationChip(modifier: Modifier = Modifier) {
    val isDarkTheme = isSystemInDarkTheme()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(color = if (isDarkTheme) LimeGreen else LightGreen)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (isDarkTheme) OliveGreen.copy(0.5F) else DarkGreen.copy(
                            0.5F
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = if (isDarkTheme) OliveGreen else DarkGreen)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.location_active),
                style = MaterialTheme.typography.labelSmall,
                color = if (isDarkTheme) Maroon else DarkGreen
            )
        }
    }
}

@Composable
private fun SOSButton(modifier: Modifier = Modifier, onClick: () -> Unit, disabled: Boolean) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            scale.animateTo(
                0.95f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
            scale.animateTo(
                1.05f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = modifier
                .size(280.dp)
                .scale(scale.value)
                .clip(RoundedCornerShape(280.dp))
                .background(if (disabled) PaleMaroon.copy(0.5F) else Primary.copy(0.5F)),
        )
        Box(
            modifier = Modifier
                .size(260.dp)
                .clip(RoundedCornerShape(260.dp))
                .background(if (disabled) PaleMaroon else Primary)
                .clickable { if (!disabled) onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SOS",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 64.sp,
                color = White
            )
        }
    }
}