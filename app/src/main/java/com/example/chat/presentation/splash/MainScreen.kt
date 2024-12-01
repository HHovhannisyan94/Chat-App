
package com.example.chat.presentation.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chat.common.DataStoreHelper
import com.example.chat.common.navigateTo
import com.example.chat.presentation.common.Routes
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun MainScreen(navController: NavController) {
    val dataStoreHelper = koinInject<DataStoreHelper>()

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 600,
                easing = { OvershootInterpolator(4f).getInterpolation(it) })
        )
        delay(800L)
        navigateTo(
            navController = navController,
            destination = if (dataStoreHelper.getUser()?.isLoggedIn == true) Routes.FriendsList.route else Routes.Login.route,
            clearBackStack = true
        )
    }

}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun ScreenSplashPrev() {
    MainScreen(rememberNavController())
}
