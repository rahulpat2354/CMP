package com.cis.cmp.ui.home.business

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.cash_distributed
import cmp.composeapp.generated.resources.cash_returned
import cmp.composeapp.generated.resources.cash_to_collect
import cmp.composeapp.generated.resources.expenses
import cmp.composeapp.generated.resources.financial_overview
import cmp.composeapp.generated.resources.ic_more_blue
import cmp.composeapp.generated.resources.icon_cash_in_hand
import cmp.composeapp.generated.resources.icon_expenses
import cmp.composeapp.generated.resources.icon_open_cash
import cmp.composeapp.generated.resources.icon_purchase
import cmp.composeapp.generated.resources.icon_sales
import cmp.composeapp.generated.resources.purchases
import cmp.composeapp.generated.resources.sales
import coil3.compose.AsyncImage
import com.cis.cmp.core.common.TextLarge
import com.cis.cmp.core.common.TextMedium
import com.cis.cmp.core.common.TextSmall
import com.cis.cmp.core.theme.Black38
import com.cis.cmp.core.theme.Blue53
import com.cis.cmp.core.theme.Gray7D
import com.cis.cmp.core.theme.White
import com.cis.cmp.core.theme.White50
import com.cis.cmp.data.models.LocalUserData
import com.cis.cmp.data.models.MainDashboardResp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BusinessScreen(
    navController: NavController,
    userData: LocalUserData?,
    dashboardOverviewData: MainDashboardResp.MainDashData?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        UserDataUi(userData)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                FinancialOverviewCard(dashboardOverviewData)
            }
        }
    }
}


@Composable
fun UserDataUi(userData: LocalUserData?) {
    val userLetterName = "${userData?.firstName?.first()} ${userData?.lastName?.first()}"
    Row(
        modifier = Modifier.fillMaxWidth().background(White).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        userData?.let {
            TextMedium(
                text = userLetterName,
                size = 18,
                color = White,
                modifier = Modifier.padding(end = 10.dp)
                    .background(Black38, shape = RoundedCornerShape(8.dp)).padding(4.dp),
            )
            TextLarge(
                text = userData.firstName,
                size = 18,
                color = Blue53,
                modifier = Modifier.weight(1f)
            )
            AsyncImage(
                model = userData.image,
                modifier = Modifier.size(30.dp).padding(end = 10.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun FinancialOverviewCard(dashboardData: MainDashboardResp.MainDashData?) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = White50)
    ) {
        TextMedium(
            text = stringResource(Res.string.financial_overview),
            modifier = Modifier.padding(16.dp),
            color = Blue53,
            size = 16
        )
        FourItemGrid(dashboardData)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun FourItemGrid(data: MainDashboardResp.MainDashData?) {
    val items = listOf(
        Pair(
            Res.drawable.icon_open_cash,
            data?.overview?.cashDistributed to stringResource(Res.string.cash_distributed)
        ),
        Pair(
            Res.drawable.icon_open_cash,
            data?.overview?.cashReturned to stringResource(Res.string.cash_returned)
        ),
        Pair(
            Res.drawable.icon_cash_in_hand,
            data?.overview?.cashDistributed to stringResource(Res.string.cash_to_collect)
        ),
        Pair(
            Res.drawable.icon_purchase,
            data?.overview?.purchases to stringResource(Res.string.purchases)
        ),
        Pair(
            Res.drawable.icon_sales,
            data?.overview?.sales to stringResource(Res.string.sales)
        ),
        Pair(
            Res.drawable.icon_expenses,
            data?.overview?.expenses to stringResource(Res.string.expenses)
        ),
    )


    val gradients = listOf(
        Brush.linearGradient(
            colorStops = arrayOf(
                0.0f to Color(0xFFFFFFFF), // white
                1.0f to Color(0xFFE6F2FF)
                // dark blue
            ), start = Offset(0f, 0f),     // top
            end = Offset(0f, Float.POSITIVE_INFINITY) // bottom
        ),
        Brush.linearGradient(
            colorStops = arrayOf(
                0.0f to Color(0xFFFFFFFF), // white
                1.0f to Color(0xFFE6FAEE)
                // dark blue
            ), start = Offset(0f, 0f),     // top
            end = Offset(0f, Float.POSITIVE_INFINITY) // bottom
        ),
        Brush.linearGradient(
            colorStops = arrayOf(
                0.0f to Color(0xFFFFFFFF), // white
                1.0f to Color(0xFFFFECEC)
                // dark blue
            ), start = Offset(0f, 0f),     // top
            end = Offset(0f, Float.POSITIVE_INFINITY) // bottom
        ),
        Brush.linearGradient(
            colorStops = arrayOf(
                0.0f to Color(0xFFFFFFFF), // white
                1.0f to Color(0xFFFFEAF2)
                // dark blue
            ), start = Offset(0f, 0f),     // top
            end = Offset(0f, Float.POSITIVE_INFINITY) // bottom
        ),
    )

    val borderColors =
        listOf(Color(0xFFE6F2FF), Color(0xFFE6FAEE), Color(0xFFFFECEC), Color(0xFFFFEAF2))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.chunked(2).forEachIndexed { rowIndex, rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEachIndexed { colIndex, (imageRes, texts) ->
                    val globalIndex = rowIndex * 2 + colIndex   // FIXED: global index
                    val (title, subtitle) = texts
                    val gradientColors = gradients[globalIndex % gradients.size]
                    val borderColor = borderColors[globalIndex % borderColors.size]

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    gradientColors, shape = RoundedCornerShape(16.dp)
                                )
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row {
                                    Image(
                                        painter = painterResource(resource = imageRes),
                                        contentDescription = title.toString(),
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Image(
                                        painter = painterResource(resource = Res.drawable.ic_more_blue),
                                        contentDescription = title.toString(),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                TextLarge(text = title.toString(), color = Blue53, size = 14)
                                TextSmall(text = subtitle, color = Gray7D, size = 12)
                            }
                        }
                    }
                }

                // Fill space if row has only one item
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}