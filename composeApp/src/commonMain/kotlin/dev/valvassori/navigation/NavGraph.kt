package dev.valvassori.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.valvassori.domain.ItemResponse
import dev.valvassori.home.HomeScreen
import dev.valvassori.merchant.MenuItemScreen
import dev.valvassori.merchant.MerchantDetailsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MerchantDetails : Screen("merchant/{merchantId}") {
        fun createRoute(merchantId: String) = "merchant/$merchantId"
    }
    object MenuItemDetail : Screen("merchant/{merchantId}/menu-item/{itemId}") {
        fun createRoute(merchantId: String, itemId: String) = "merchant/$merchantId/menu-item/$itemId"
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
            MerchantDetailsScreen(
                merchantId = merchantId,
                onBackClick = { navController.popBackStack() },
                onMenuItemClick = { item ->
                    navController.navigate(Screen.MenuItemDetail.createRoute(merchantId, item.id))
                }
            )
        }
        
        composable(Screen.MenuItemDetail.route) { backStackEntry ->
            val merchantId = backStackEntry.arguments?.getString("merchantId") ?: return@composable
            val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
            
            MenuItemScreen(
                merchantId = merchantId,
                itemId = itemId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 