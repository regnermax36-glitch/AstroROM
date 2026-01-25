package com.neuramusic.presentation.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u000f2\u00020\u0001:\u0007\u000f\u0010\u0011\u0012\u0013\u0014\u0015B/\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u0082\u0001\u0006\u0016\u0017\u0018\u0019\u001a\u001b\u00a8\u0006\u001c"}, d2 = {"Lcom/neuramusic/presentation/navigation/Screen;", "", "route", "", "title", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "selectedIcon", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;Landroidx/compose/ui/graphics/vector/ImageVector;)V", "getIcon", "()Landroidx/compose/ui/graphics/vector/ImageVector;", "getRoute", "()Ljava/lang/String;", "getSelectedIcon", "getTitle", "Companion", "Discover", "Home", "Library", "NowPlaying", "Profile", "Search", "Lcom/neuramusic/presentation/navigation/Screen$Discover;", "Lcom/neuramusic/presentation/navigation/Screen$Home;", "Lcom/neuramusic/presentation/navigation/Screen$Library;", "Lcom/neuramusic/presentation/navigation/Screen$NowPlaying;", "Lcom/neuramusic/presentation/navigation/Screen$Profile;", "Lcom/neuramusic/presentation/navigation/Screen$Search;", "app_debug"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String route = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.Nullable
    private final androidx.compose.ui.graphics.vector.ImageVector icon = null;
    @org.jetbrains.annotations.Nullable
    private final androidx.compose.ui.graphics.vector.ImageVector selectedIcon = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<com.neuramusic.presentation.navigation.Screen> bottomNavItems = null;
    @org.jetbrains.annotations.NotNull
    public static final com.neuramusic.presentation.navigation.Screen.Companion Companion = null;
    
    private Screen(java.lang.String route, java.lang.String title, androidx.compose.ui.graphics.vector.ImageVector icon, androidx.compose.ui.graphics.vector.ImageVector selectedIcon) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRoute() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final androidx.compose.ui.graphics.vector.ImageVector getIcon() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final androidx.compose.ui.graphics.vector.ImageVector getSelectedIcon() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/neuramusic/presentation/navigation/Screen$Companion;", "", "()V", "bottomNavItems", "", "Lcom/neuramusic/presentation/navigation/Screen;", "getBottomNavItems", "()Ljava/util/List;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.neuramusic.presentation.navigation.Screen> getBottomNavItems() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/neuramusic/presentation/navigation/Screen$Discover;", "Lcom/neuramusic/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Discover extends com.neuramusic.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.neuramusic.presentation.navigation.Screen.Discover INSTANCE = null;
        
        private Discover() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/neuramusic/presentation/navigation/Screen$Home;", "Lcom/neuramusic/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Home extends com.neuramusic.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.neuramusic.presentation.navigation.Screen.Home INSTANCE = null;
        
        private Home() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/neuramusic/presentation/navigation/Screen$Library;", "Lcom/neuramusic/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Library extends com.neuramusic.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.neuramusic.presentation.navigation.Screen.Library INSTANCE = null;
        
        private Library() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/neuramusic/presentation/navigation/Screen$NowPlaying;", "Lcom/neuramusic/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class NowPlaying extends com.neuramusic.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.neuramusic.presentation.navigation.Screen.NowPlaying INSTANCE = null;
        
        private NowPlaying() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/neuramusic/presentation/navigation/Screen$Profile;", "Lcom/neuramusic/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Profile extends com.neuramusic.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.neuramusic.presentation.navigation.Screen.Profile INSTANCE = null;
        
        private Profile() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/neuramusic/presentation/navigation/Screen$Search;", "Lcom/neuramusic/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Search extends com.neuramusic.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull
        public static final com.neuramusic.presentation.navigation.Screen.Search INSTANCE = null;
        
        private Search() {
        }
    }
}