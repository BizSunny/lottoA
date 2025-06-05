package biz.sunny.path.lottoa.view

sealed class Screen(val route: String) {
    object Permission: Screen("permission_screen")
    object Splash: Screen("splash_screen")
    object Main: Screen("main_screen")
}