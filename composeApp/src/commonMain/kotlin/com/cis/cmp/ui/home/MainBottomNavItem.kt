package com.cis.cmp.ui.home

import androidx.compose.ui.graphics.vector.ImageVector
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.ic_more_blue
import cmp.composeapp.generated.resources.icon_home
import cmp.composeapp.generated.resources.icon_notification
import cmp.composeapp.generated.resources.icon_report
import cmp.composeapp.generated.resources.icon_time_line
import org.jetbrains.compose.resources.DrawableResource

sealed class MainBottomNavItem(
    val route: String, val label: String, val icon: ImageVector? = null,
    val iconRes: DrawableResource? = null
) {
    object Home : MainBottomNavItem("home", "Home", iconRes = Res.drawable.icon_home)
    object Timeline : MainBottomNavItem("timeline", "Timeline", iconRes = Res.drawable.icon_time_line)
    object Reports : MainBottomNavItem("reports", "Reports", iconRes = Res.drawable.icon_report)
    object Notification :
        MainBottomNavItem("notification", "Notification", iconRes = Res.drawable.icon_notification)
    object More: MainBottomNavItem("more", "More", iconRes = Res.drawable.ic_more_blue)
}