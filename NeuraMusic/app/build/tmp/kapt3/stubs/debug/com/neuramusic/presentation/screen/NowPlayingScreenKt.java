package com.neuramusic.presentation.screen;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\u001ax\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u001a(\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u001a*\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u001aL\u0010\u0019\u001a\u00020\u00012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u001a(\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u001a,\u0010\"\u001a\u00020\u00012\b\u0010#\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u00a8\u0006%"}, d2 = {"NeuralControlButtons", "", "isPlaying", "", "onPreviousClick", "Lkotlin/Function0;", "onPlayPauseClick", "onNextClick", "onShuffleClick", "onRepeatClick", "isShuffleEnabled", "repeatMode", "", "pulseScale", "", "modifier", "Landroidx/compose/ui/Modifier;", "NeuralLyricsPanel", "lyrics", "", "onDismiss", "NeuralSongInfo", "title", "artist", "album", "NeuralTopBar", "onNavigateBack", "onToggleLyrics", "onToggleVisualizer", "showLyrics", "showVisualizer", "NowPlayingScreen", "musicViewModel", "Lcom/neuramusic/presentation/viewmodel/AIEnhancedMusicViewModel;", "QuantumAlbumArt", "albumArt", "rotation", "app_debug"})
public final class NowPlayingScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void NowPlayingScreen(@org.jetbrains.annotations.NotNull
    com.neuramusic.presentation.viewmodel.AIEnhancedMusicViewModel musicViewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void NeuralTopBar(kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleLyrics, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleVisualizer, boolean showLyrics, boolean showVisualizer, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void QuantumAlbumArt(java.lang.String albumArt, boolean isPlaying, float rotation, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void NeuralSongInfo(java.lang.String title, java.lang.String artist, java.lang.String album, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void NeuralControlButtons(boolean isPlaying, kotlin.jvm.functions.Function0<kotlin.Unit> onPreviousClick, kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPauseClick, kotlin.jvm.functions.Function0<kotlin.Unit> onNextClick, kotlin.jvm.functions.Function0<kotlin.Unit> onShuffleClick, kotlin.jvm.functions.Function0<kotlin.Unit> onRepeatClick, boolean isShuffleEnabled, int repeatMode, float pulseScale, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void NeuralLyricsPanel(java.lang.String lyrics, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, androidx.compose.ui.Modifier modifier) {
    }
}