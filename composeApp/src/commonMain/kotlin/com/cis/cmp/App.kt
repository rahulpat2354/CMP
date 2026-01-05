package com.cis.cmp

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.ca
import cmp.composeapp.generated.resources.gb
import cmp.composeapp.generated.resources.jp
import com.cis.cmp.core.theme.MyAppTheme
import com.cis.cmp.di.appModules
import com.cis.cmp.core.navigation.AppNavigation
import kotlinx.datetime.IllegalTimeZoneException
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.koin.compose.KoinApplication
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
@Preview
fun App(countries: List<Country> = countries()) {
    KoinApplication(application = {
        modules(appModules)
    }) {
        MyAppTheme {
            AppNavigation()
        }
    }
}

@OptIn(ExperimentalTime::class)
fun currentTimeAt(location: String, zone: TimeZone): String {
    fun LocalTime.formatted() = "$hour:$minute:$second"
    return try {
        val time = Clock.System.now()
        val localTime = time.toLocalDateTime(zone).time
        "The time in $location is ${localTime.formatted()}"
    } catch (ex: IllegalTimeZoneException) {
        ""
    }
}

data class Country(val name: String, val zone: TimeZone, val image: DrawableResource)

fun countries() = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo"), Res.drawable.jp),
    Country("Spain", TimeZone.of("Europe/Madrid"), Res.drawable.gb),
    Country("France", TimeZone.of("Europe/Paris"), Res.drawable.gb),
    Country("Germany", TimeZone.of("Europe/Berlin"), Res.drawable.ca),
    Country("Egypt", TimeZone.of("Africa/Cairo"), Res.drawable.gb)
)