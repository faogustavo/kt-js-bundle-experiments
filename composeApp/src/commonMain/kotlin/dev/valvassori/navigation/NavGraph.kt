package dev.valvassori.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.valvassori.cart.CartScreen
import dev.valvassori.home.HomeScreen
import dev.valvassori.merchant.MenuItemScreen
import dev.valvassori.merchant.MerchantDetailsScreen
import dev.valvassori.presentation.cart.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MerchantDetails : Screen("merchant/{merchantId}") {
        fun createRoute(merchantId: String) = "merchant/$merchantId"
    }

    object MenuItemDetail : Screen("merchant/{merchantId}/menu-item/{itemId}") {
        fun createRoute(merchantId: String, itemId: String) = "merchant/$merchantId/menu-item/$itemId"
    }

    object Cart : Screen("cart")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    // Create a single CartViewModel instance to share across screens
    val cartViewModel: CartViewModel = koinViewModel()

    // Define a navigation function for cart
    val navigateToCart: () -> Unit = {
        navController.navigate(Screen.Cart.route)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onMerchantClick = { merchantId ->
                    navController.navigate(Screen.MerchantDetails.createRoute(merchantId))
                },
                onNavigateToCart = navigateToCart,
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.MerchantDetails.route) { backStackEntry ->
            val merchantId = backStackEntry.arguments?.getString("merchantId") ?: return@composable
            MerchantDetailsScreen(
                merchantId = merchantId,
                onBackClick = { navController.popBackStack() },
                onMenuItemClick = { item ->
                    navController.navigate(Screen.MenuItemDetail.createRoute(merchantId, item.id))
                },
                onNavigateToCart = navigateToCart,
                viewModel = koinViewModel(),
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.MenuItemDetail.route) { backStackEntry ->
            val merchantId = backStackEntry.arguments?.getString("merchantId") ?: return@composable
            val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable

            MenuItemScreen(
                merchantId = merchantId,
                itemId = itemId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCart = navigateToCart,
                viewModel = koinViewModel(),
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onNavigateBack = { navController.popBackStack() },
                onCheckout = {
                    // TODO: Navigate to checkout screen when implemented
                    navController.popBackStack(Screen.Home.route, false)
                },
                viewModel = cartViewModel
            )
        }
    }
}
