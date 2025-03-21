package id.jsaproject.flognest.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object About : Screen("about")
    data object ContentDetail: Screen("home/{contentId}/{contentTitle}") {
        fun createRoute(contentId: String, contentTitle: String) = "home/$contentId/$contentTitle"
    }
    data object TMDBWebViewScreen : Screen("tmdbWebView")
}