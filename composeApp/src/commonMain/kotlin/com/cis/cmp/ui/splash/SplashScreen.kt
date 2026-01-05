package com.cis.cmp.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.img_bar_one_gold
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    viewmodel: SplashViewModel = koinInject(),
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val splashState by viewmodel.splashState.collectAsState()

    LaunchedEffect(splashState) {
        if (splashState is SplashUIState.NavigateToHome) {
            onNavigateToHome()
        } else if (splashState is SplashUIState.NavigateToLogin) {
            onNavigateToLogin()
        }
    }
    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.img_bar_one_gold),
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )
    }
}