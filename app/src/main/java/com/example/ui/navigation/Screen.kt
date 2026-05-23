package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Leads : Screen("leads")
    object LeadDetail : Screen("lead_detail/{leadId}") {
        fun createRoute(leadId: Int) = "lead_detail/$leadId"
    }
    object Properties : Screen("properties")
    object More : Screen("more")
}
