package dev.valvassori.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.valvassori.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MerchantDetails : Screen("merchant/{merchantId}") {
        fun createRoute(merchantId: String) = "merchant/$merchantId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onMerchantClick = { merchantId ->
                    navController.navigate(Screen.MerchantDetails.createRoute(merchantId))
                }
            )
        }
        
        composable(Screen.MerchantDetails.route) { backStackEntry ->
            val merchantId = backStackEntry.arguments?.getString("merchantId") ?: return@composable
            // TODO: Implement MerchantDetailsScreen
            // MerchantDetailsScreen(
            //     merchantId = merchantId,
            //     onBackClick = { navController.popBackStack() }
            // )
        }
    }
} 