package ir.sharif.simplenote.ui.features.navigation

sealed class Screen(open val route: String) {
    object Splash: Screen("splash")
    object OnBoarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object Register : Screen("register")
    object CreateNote : Screen("create-note")

    object Sync: Screen("sync")

    object EditNote : Screen("edit-note/{noteId}") {
        // These constants are already correctly used in your NavHost setup
        const val BASE_ROUTE = "edit-note/{noteId}" // Used for the composable route definition
        const val NOTE_ID_ARG = "noteId"            // Used for the navArgument key

        /**
         * Helper function to construct the actual navigation route string
         * with a specific noteId.
         * The noteId is of type Long, consistent with NavType.LongType.
         */
        fun createRouteWithId(noteId: Long): String {
            // This builds the route string like "edit-note/123"
            return "edit-note/$noteId"
        }
    }

    object Settings: Screen("settings")
    object ChangePassword: Screen("change-password")

}

object Graph {
    const val ROOT = "ROOT"
    const val AUTH = "AUTH"
    const val MAIN = "MAIN"
    const val SYNC = "SYNC"
}