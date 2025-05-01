package dev.valvassori.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.valvassori.cart.CartScreen
import dev.valvassori.home.HomeScreen
import dev.valvassori.merchant.MenuItemScreen
import dev.valvassori.merchant.MerchantDetailsScreen
import dev.valvassori.presentation.cart.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    // Create a single CartViewModel instance to share across screens
    val cartViewModel: CartViewModel = koinViewModel()

    // Define a navigation function for cart
    val navigateToCart: () -> Unit = {
        navController.navigate(Cart)
    }

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onMerchantClick = { merchantId ->
                    navController.navigate(MerchantDetails(merchantId))
                },
                onNavigateToCart = navigateToCart,
                cartViewModel = cartViewModel
            )
        }

        composable<MerchantDetails> { backStackEntry ->
            val route: MerchantDetails = backStackEntry.toRoute()
            MerchantDetailsScreen(
                merchantId = route.merchantId,
                onBackClick = { navController.popBackStack() },
                onMenuItemClick = { item ->
                    navController.navigate(MenuItemDetail(route.merchantId, item.id))
                },
                onNavigateToCart = navigateToCart,
                viewModel = koinViewModel(),
                cartViewModel = cartViewModel
            )
        }

        composable<MenuItemDetail> { backStackEntry ->
            val route: MenuItemDetail = backStackEntry.toRoute()
            
            MenuItemScreen(
                merchantId = route.merchantId,
                itemId = route.itemId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCart = navigateToCart,
                viewModel = koinViewModel(),
                cartViewModel = cartViewModel
            )
        }

        composable<Cart> {
            CartScreen(
                onNavigateBack = { navController.popBackStack() },
                onCheckout = {
                    // TODO: Navigate to checkout screen when implemented
                    navController.popBackStack(Home, false)
                },
                viewModel = cartViewModel
            )
        }
    }
}
