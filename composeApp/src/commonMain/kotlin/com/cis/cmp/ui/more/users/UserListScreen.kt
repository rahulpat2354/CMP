package com.cis.cmp.ui.more.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.ic_flag_usa
import cmp.composeapp.generated.resources.ic_menu_vertical_dot
import cmp.composeapp.generated.resources.ic_users
import cmp.composeapp.generated.resources.img_gold
import cmp.composeapp.generated.resources.img_screen_bg
import cmp.composeapp.generated.resources.users
import com.cis.cmp.core.common.BackTopAppBar
import com.cis.cmp.core.common.TextMedium
import com.cis.cmp.core.common.TextSmall
import com.cis.cmp.core.theme.Black2A
import com.cis.cmp.core.theme.Blue53
import com.cis.cmp.core.theme.Blue72
import com.cis.cmp.core.theme.Fonts
import com.cis.cmp.core.theme.Green53
import com.cis.cmp.core.theme.Orange
import com.cis.cmp.core.theme.PrimaryBlue
import com.cis.cmp.core.theme.White
import com.cis.cmp.core.theme.White50
import com.cis.cmp.core.theme.White70
import com.cis.cmp.core.theme.YellowB7
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val countryCode: String,
)

fun sampleUserData(): List<User> {
    return listOf(
        User(
            id = 1,
            name = "Peter parker",
            email = "peter@gmail.com",
            role = "Buyer",
            countryCode = "USA"
        ), User(
            id = 2,
            name = "Williams David",
            email = "williams@gmail.com",
            role = "Buyer",
            countryCode = "USA"
        ), User(
            id = 3,
            name = "Victoria Williams",
            email = "victoria@gmail.com",
            role = "Buyer",
            countryCode = "USA"
        )
    )
}

@Composable
fun UserListScreen(navController: NavController) {

    val tabs = listOf("All", "Supervisor", "Buyer")
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackTopAppBar(stringResource(Res.string.users)) { navController.popBackStack() }

            Spacer(Modifier.height(16.dp))

            UserTabs(tabs, pagerState, onTabSelect = {
                scope.launch { pagerState.animateScrollToPage(it) }
            })

            Spacer(Modifier.height(16.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(sampleUserData(), key = { it.id }) { user ->
                        UserItemUi(
                            user
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserTabs(
    tabs: List<String>, pagerState: PagerState, onTabSelect: (Int) -> Unit,
) {
    SecondaryTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        containerColor = Color.Transparent,
        contentColor = Blue53,
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
        divider = {
            HorizontalDivider(
                thickness = 1.dp,
                color = White50
            )
        },
        tabs = {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    selectedContentColor = Blue53,
                    unselectedContentColor = White,
                    onClick = {
                        onTabSelect(index)
                    }
                ) {
                    Text(
                        text = title,
                        color = if (pagerState.currentPage == index) Blue53 else White,
                        fontSize = 14.sp,
                        fontFamily = Fonts.Poppins,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                }
            }
        })
}

@Composable
fun UserItemUi(user: User) {
    Row(
        modifier = Modifier.fillMaxWidth().background(White50, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(end = 8.dp).width(60.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(Res.drawable.img_gold),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_flag_usa),
                    modifier = Modifier.width(14.dp),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                TextSmall(text = user.countryCode, size = 12, color = Blue53)
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextMedium(
                    text = user.name,
                    size = 14,
                    color = Black2A,
                    modifier = Modifier.padding(end = 4.dp).weight(1f)
                )

                TextSmall(
                    text = "Active",
                    size = 13,
                    color = Green53,
                    modifier = Modifier.padding(end = 10.dp)
                )

                Image(
                    painter = painterResource(Res.drawable.ic_menu_vertical_dot),
                    contentDescription = null,
                )
            }

            ThresholdInfoText(
                modifier = Modifier.padding(bottom = 4.dp), threshold = "$3000", advanced = "$6000"
            )

            PurchaseInfoText(
                modifier = Modifier.padding(bottom = 4.dp),
                pur = "$1000",
                exp = "$2000",
                cih = "$3000"
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Blue72)) {
                        append("Allocated Place: ")
                    }
                    withStyle(style = SpanStyle(color = Blue53)) {
                        append("Eastern Cape, South Africa")
                    }
                }, style = TextStyle(
                    fontSize = 12.sp, fontFamily = Fonts.Poppins, fontWeight = FontWeight.Normal
                ), modifier = Modifier.padding(bottom = 4.dp)
            )

            ReportTo(
                modifier = Modifier,
                reportToName = "Victoria Williams",
                reportToImage = "",
                status = "Report Pending"
            )
        }
    }
}

@Composable
fun ThresholdInfoText(modifier: Modifier = Modifier, threshold: String, advanced: String) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Blue72)) {
                append("Threshold: ")
            }
            withStyle(style = SpanStyle(color = Blue53)) {
                append(threshold)
            }

            withStyle(style = SpanStyle(color = Blue72)) {
                append(" | Advanced: ")
            }
            withStyle(style = SpanStyle(color = Blue53)) {
                append(advanced)
            }
        },
        style = TextStyle(
            fontSize = 12.sp, fontFamily = Fonts.Poppins, fontWeight = FontWeight.Normal
        ),
    )
}

@Composable
fun PurchaseInfoText(modifier: Modifier = Modifier, pur: String, exp: String, cih: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Blue72)) { append("PUR: ") }
            withStyle(style = SpanStyle(color = Blue53)) { append(pur) }
            withStyle(style = SpanStyle(color = Blue72)) {
                append(" EXP: ")
            }
            withStyle(style = SpanStyle(color = Blue53)) {
                append(exp)
            }

            withStyle(style = SpanStyle(color = Blue72)) {
                append(" CIH: ")
            }
            withStyle(style = SpanStyle(color = Blue53)) {
                append(cih)
            }
        },
        style = TextStyle(
            fontSize = 12.sp, fontFamily = Fonts.Poppins, fontWeight = FontWeight.Normal
        ),
        modifier = modifier.fillMaxWidth()
            .background(color = YellowB7, shape = RoundedCornerShape(4.dp)).padding(4.dp)
    )
}

@Composable
fun ReportTo(
    modifier: Modifier = Modifier, reportToImage: String, reportToName: String, status: String
) {
    Row(modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.padding(end = 8.dp).weight(1f)) {
            TextSmall(
                text = "Report to:",
                size = 12,
                color = Blue72,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.ic_users),
                    modifier = Modifier.padding(end = 4.dp).size(24.dp)
                        .background(color = White70, shape = CircleShape).padding(4.dp),
                    contentDescription = null
                )

                TextSmall(text = reportToName, size = 12, color = Blue53)
            }
        }

        TextMedium(
            text = status,
            size = 12,
            color = Orange,
            modifier = Modifier.background(color = White, shape = RoundedCornerShape(100.dp))
                .border(width = 1.dp, color = Orange, shape = RoundedCornerShape(100.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}