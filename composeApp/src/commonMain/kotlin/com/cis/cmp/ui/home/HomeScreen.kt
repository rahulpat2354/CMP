package com.cis.cmp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.business
import cmp.composeapp.generated.resources.img_horizontal_bar_one_gold
import cmp.composeapp.generated.resources.img_tab_bg
import cmp.composeapp.generated.resources.marketplace
import com.cis.cmp.core.theme.Blue53
import com.cis.cmp.core.theme.GrayEA
import com.cis.cmp.core.theme.PrimaryBlue
import com.cis.cmp.core.theme.White
import com.cis.cmp.core.theme.WhiteF3
import com.cis.cmp.data.models.LocalUserData
import com.cis.cmp.data.models.MainDashboardResp
import com.cis.cmp.ui.home.business.BusinessScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    navController: NavController,
    viewmodel: HomeViewModel = koinInject()
) {
    val userData = viewmodel.userDataState.value
    var selectedItem by remember { mutableIntStateOf(0) }

    val homeUiState = viewmodel.homeUiState.collectAsState()

    val navItems = listOf(
        MainBottomNavItem.Home,
        MainBottomNavItem.Timeline,
        MainBottomNavItem.Reports,
        MainBottomNavItem.Notification,
        MainBottomNavItem.More
    )

    Scaffold(
        bottomBar = {
            CurvedBottomBar(
                items = navItems,
                selectedIndex = selectedItem,
                onItemSelected = { index ->
                    when (navItems[index]) {
                        MainBottomNavItem.Home -> {
                            // Navigate to Home
                        }

                        MainBottomNavItem.Timeline -> {

                        }

                        MainBottomNavItem.Reports -> {

                        }

                        MainBottomNavItem.Notification -> {

                        }

                        MainBottomNavItem.More -> {

                        }
                    }
                    selectedItem = index
                }
            )
        },
        content = { paddingValues ->
            HomeScreenContent(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                userData = userData,
                dashboardOverviewData = homeUiState.value.dashboardOverviewData
            )
        })
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    navController: NavController,
    userData: LocalUserData?,
    dashboardOverviewData: MainDashboardResp.MainDashData?
) {
    val tabs = listOf(
        stringResource(Res.string.business),
        stringResource(Res.string.marketplace)
    )
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }

    Column(modifier) {
        HomeTopTabs(tabs, pagerState)
        HomePager(
            navController = navController,
            pagerState,
            modifier = Modifier.fillMaxSize().weight(1f),
            userData,
            dashboardOverviewData
        )
    }
}


@Composable
fun HomeTopTabs(tabs: List<String>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    GoldBarWithTabs(
        tabs = tabs,
        onSelect = { scope.launch { pagerState.animateScrollToPage(it) } },
        pagerState = pagerState
    )
}

@Composable
fun HomePager(
    navController: NavController,
    pagerState: PagerState,
    modifier: Modifier,
    userData: LocalUserData?,
    dashboardOverviewData: MainDashboardResp.MainDashData?
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        when (page) {
            0 -> {
                BusinessScreen(navController = navController, userData = userData, dashboardOverviewData)
            }

            1 -> { /* MarketplaceScreen() */

            }
        }
    }
}


@Composable
fun GoldBarWithTabs(
    tabs: List<String>,
    onSelect: (Int) -> Unit,
    pagerState: PagerState,
) {
    Row(
        modifier = Modifier.fillMaxWidth().background(
            Brush.verticalGradient(
                colors = listOf(Color(0xFF0E5A91), Color(0xFF08528E))
            )
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.img_horizontal_bar_one_gold),
            contentDescription = null,
            modifier = Modifier.weight(0.8f)
        )

        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            indicator = {},
            divider = {},
            modifier = Modifier.weight(2f)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    selectedContentColor = Color.White,
                    unselectedContentColor = GrayEA,
                    onClick = { onSelect(index) }
                ) {
                    TabItem(
                        text = title,
                        selected = pagerState.currentPage == index,
                    )
                }
            }
        }
    }
}


@Composable
fun TabItem(
    text: String,
    selected: Boolean,
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        if (selected) {
            Image(
                painter = painterResource(Res.drawable.img_tab_bg),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = text,
            color = if (selected) WhiteF3 else GrayEA,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(
                    if (selected)
                        Brush.verticalGradient(listOf(Color(0xFF0A0F6C), Color(0xCC007BFF)))
                    else Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent))
                )
                .fillMaxWidth(0.75f)
                .padding(vertical = 6.dp)
        )
    }
}


@Composable
fun CurvedBottomBar(
    items: List<MainBottomNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Surface(
        color = White,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val selected = index == selectedIndex
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onItemSelected(index) }
                        .padding(vertical = 8.dp)
                ) {
                    if (item.icon != null) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (selected) PrimaryBlue else Blue53,
                            modifier = Modifier.size(24.dp)
                        )
                    } else if (item.iconRes != null) {
                        Icon(
                            painter = painterResource(resource = item.iconRes),
                            contentDescription = item.label,
                            tint = if (selected) PrimaryBlue else Blue53,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = if (selected) PrimaryBlue else Blue53
                    )
                }
            }
        }
    }
}
