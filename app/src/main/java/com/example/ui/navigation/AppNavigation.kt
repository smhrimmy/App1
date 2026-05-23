package com.example.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.ui.MainViewModel
import com.example.ui.dashboard.DashboardScreen
import com.example.ui.leads.LeadsScreen
import com.example.ui.leads.LeadDetailScreen
import com.example.ui.properties.PropertiesScreen
import com.example.ui.more.MoreScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            val bottomNavRoutes = listOf(Screen.Dashboard.route, Screen.Leads.route, Screen.Properties.route, Screen.More.route)
            if (currentRoute in bottomNavRoutes) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                        label = { Text("Dashboard") },
                        selected = currentRoute == Screen.Dashboard.route,
                        onClick = {
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.People, contentDescription = "Leads") },
                        label = { Text("Leads") },
                        selected = currentRoute == Screen.Leads.route,
                        onClick = {
                            navController.navigate(Screen.Leads.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Properties") },
                        label = { Text("Inventory") },
                        selected = currentRoute == Screen.Properties.route,
                        onClick = {
                            navController.navigate(Screen.Properties.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.MoreHoriz, contentDescription = "More") },
                        label = { Text("More") },
                        selected = currentRoute == Screen.More.route,
                        onClick = {
                            navController.navigate(Screen.More.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen(viewModel, navController) }
            composable(Screen.Leads.route) { LeadsScreen(viewModel, navController) }
            composable(Screen.LeadDetail.route) { backStackEntry ->
                val leadId = backStackEntry.arguments?.getString("leadId")?.toIntOrNull()
                leadId?.let { LeadDetailScreen(it, viewModel, navController) }
            }
            composable(Screen.Properties.route) { PropertiesScreen(viewModel, navController) }
            composable(Screen.More.route) { MoreScreen(viewModel, navController) }
        }
    }
}
