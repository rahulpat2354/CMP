package com.cis.cmp.ui.more.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.dummy_profile_img
import cmp.composeapp.generated.resources.ic_flag_usa
import com.cis.cmp.core.common.BackTopAppBar
import com.cis.cmp.core.common.TextMedium
import com.cis.cmp.core.common.TextSmall
import com.cis.cmp.core.theme.Blue53
import com.cis.cmp.core.theme.Blue72
import com.cis.cmp.core.theme.Blue91
import com.cis.cmp.core.theme.Green53
import com.cis.cmp.core.theme.Orange
import com.cis.cmp.core.theme.PrimaryBlue
import com.cis.cmp.core.theme.White
import com.cis.cmp.core.theme.White20
import com.cis.cmp.core.theme.White50
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

data class UserDetailsState(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val countryCode: String,
    val phoneNumber: String,
    val lastActivity: String,
    val location: String
)

val sampleUserData = UserDetailsState(
    id = 0,
    name = "Myrna M. Negron",
    email = "john.c.breckinridge@altostrat.com",
    role = "Supervisor",
    countryCode = "USA",
    phoneNumber = "+1234567890",
    lastActivity = "10 min ago",
    location = "Eastern Cape, South Africa"
)

@Composable
fun UserDetailScreen(nanController: NavController) {

    val tabs = listOf("Dashboard", "Reports", "Buyer", "Analytics")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val scope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxSize()) {
        BackTopAppBar(title = "User Details") { nanController.popBackStack() }

        Spacer(Modifier.height(12.dp))

        UserInfo(userData = sampleUserData)

        UserDetailTabs(tabs, pagerState) {
            scope.launch { pagerState.animateScrollToPage(it) }
        }

        HorizontalPager(state = pagerState,
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Top
        ) {
            when (pagerState.currentPage) {
                0 -> UserInfo(userData = sampleUserData)
                1 -> UserInfo(userData = sampleUserData)
            }
        }
    }
}

@Composable
fun UserInfo(modifier: Modifier = Modifier, userData: UserDetailsState) {
    Row(
        modifier = Modifier.fillMaxWidth().background(
            color = White20,
            shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)
        ).padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(end = 12.dp).width(90.dp)
                .background(White50, shape = RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painterResource(Res.drawable.dummy_profile_img),
                modifier = Modifier.fillMaxWidth().height(72.dp),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_flag_usa),
                    contentDescription = null,
                    Modifier.padding(end = 8.dp).size(14.dp)
                )

                TextSmall(text = userData.countryCode, size = 12, color = Blue53)
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            TextMedium(
                text = userData.name,
                size = 16,
                color = Blue53,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            TextSmall(
                text = "${userData.role} | ${userData.phoneNumber}",
                size = 13,
                color = Blue53,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            TextSmall(
                text = userData.location,
                size = 12,
                color = Blue53,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextSmall(
                    text = "Active",
                    size = 14,
                    color = Green53,
                    modifier = Modifier.padding(end = 4.dp).weight(1f)
                )

                TextSmall(
                    text = "Last Activity ${userData.lastActivity}",
                    size = 12,
                    color = Blue72,
                )
            }

            TextMedium(
                text = "Report Pending",
                size = 12,
                color = Orange,
                modifier = Modifier.background(color = White, shape = RoundedCornerShape(100.dp))
                    .border(width = 1.dp, color = Orange, shape = RoundedCornerShape(100.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun UserDetailTabs(tabs: List<String>, pagerState: PagerState, onTabSelect: (Int) -> Unit) {

    SecondaryTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.fillMaxWidth(),
        containerColor = Blue91,
        contentColor = White,
        indicator = {
            if (pagerState.currentPage < tabs.size) {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(pagerState.currentPage)
                        .fillMaxWidth(), // This makes it full width
                    height = 2.dp,
                    color = PrimaryBlue
                )
            }
        },
        tabs = {
            tabs.forEachIndexed { index, string ->
                Tab(
                    selected = pagerState.currentPage == index,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    onClick = {
                        onTabSelect(index)
                    }, content = {
                        TextMedium(
                            text = string,
                            size = 13,
                            color = if (pagerState.currentPage == index) Blue53 else White
                        )
                    }
                )
            }
        }
    )
}