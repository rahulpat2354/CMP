package com.cis.cmp.ui.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.app_name
import cmp.composeapp.generated.resources.ic_business_places
import cmp.composeapp.generated.resources.ic_chartpie
import cmp.composeapp.generated.resources.ic_price_settings
import cmp.composeapp.generated.resources.ic_users
import cmp.composeapp.generated.resources.icon_language
import cmp.composeapp.generated.resources.img_bar_one_gold
import cmp.composeapp.generated.resources.img_screen_bg
import cmp.composeapp.generated.resources.more
import com.cis.cmp.core.common.BackTopAppBar
import com.cis.cmp.core.common.TextMedium
import com.cis.cmp.core.common.TextSmall
import com.cis.cmp.core.theme.Blue53
import com.cis.cmp.core.theme.White20
import com.cis.cmp.core.theme.White50
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class MoreItem(
    val id: Int,
    val title: String,
    val icon: DrawableResource
)

@Composable
fun MoreScreen(navController: NavController) {
    val list = listOf<MoreItem>(
        MoreItem(0, "Analytics", Res.drawable.ic_chartpie),
        MoreItem(1, "Inventory", Res.drawable.icon_language),
        MoreItem(2, "Users", Res.drawable.ic_users),
        MoreItem(2, "Business Places", Res.drawable.ic_business_places),
        MoreItem(2, "Price Settings", Res.drawable.ic_price_settings),
        MoreItem(3, "Currency", Res.drawable.ic_price_settings),
        MoreItem(4, "Measurement Unit", Res.drawable.ic_users)
    )

    Box(
        modifier = Modifier.fillMaxSize().paint(
            painter = painterResource(Res.drawable.img_screen_bg),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // ✅ makes it scrollable
                .padding(vertical = 15.dp)
                .imePadding()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.padding(horizontal = 16.dp)) {
                BackTopAppBar(stringResource(Res.string.more)) { navController.popBackStack() }
            }

            Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            White50, RoundedCornerShape(12.dp)
                        )
                        .padding(10.dp),
                ) {
                    list.forEachIndexed { index, moreItem ->
                        MoreItem(moreItem = moreItem)
                    }
                }
        }
    }
}


@Composable
fun MoreItem(moreItem: MoreItem) {
    Row(
        Modifier.padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(moreItem.icon),
            modifier = Modifier.padding(end = 8.dp).size(32.dp),
            contentDescription = null
        )
        TextMedium(text = moreItem.title, size = 14, color = Blue53)
    }
}