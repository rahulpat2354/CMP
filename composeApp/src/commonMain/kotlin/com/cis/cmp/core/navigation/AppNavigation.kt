package com.cis.cmp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cis.cmp.ui.splash.SplashScreen
import com.cis.cmp.ui.authentication.LoginScreen
import com.cis.cmp.ui.home.HomeScreen
import com.cis.cmp.ui.more.MoreScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onNavigateToHome = {
                navController.navigate(Routes.HOME){
                    popUpTo(Routes.SPLASH){
                        inclusive = true
                    }
                }
            }, onNavigateToLogin = {
                navController.navigate(Routes.LOGIN){
                    popUpTo(Routes.SPLASH) {
                        inclusive = true
                    }
                }
            })
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.MORE) {
            MoreScreen(navController)
        }
    }
}