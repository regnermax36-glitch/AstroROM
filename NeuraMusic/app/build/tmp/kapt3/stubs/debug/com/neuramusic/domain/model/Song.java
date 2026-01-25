package com.neuramusic.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\bG\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0083\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u001e\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u001e\u00a2\u0006\u0002\u0010!J\t\u0010G\u001a\u00020\u0003H\u00c6\u0003J\t\u0010H\u001a\u00020\u0003H\u00c6\u0003J\t\u0010I\u001a\u00020\u0003H\u00c6\u0003J\t\u0010J\u001a\u00020\u0010H\u00c6\u0003J\t\u0010K\u001a\u00020\u0010H\u00c6\u0003J\u000b\u0010L\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010M\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u0010\u0010N\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u000b\u0010O\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010P\u001a\u00020\u0017H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0010H\u00c6\u0003J\t\u0010R\u001a\u00020\u0005H\u00c6\u0003J\t\u0010S\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010T\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u000b\u0010U\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010V\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010W\u001a\u0004\u0018\u00010\u001eH\u00c6\u0003\u00a2\u0006\u0002\u0010.J\u0010\u0010X\u001a\u0004\u0018\u00010\u001eH\u00c6\u0003\u00a2\u0006\u0002\u0010.J\u0010\u0010Y\u001a\u0004\u0018\u00010\u001eH\u00c6\u0003\u00a2\u0006\u0002\u0010.J\t\u0010Z\u001a\u00020\u0005H\u00c6\u0003J\t\u0010[\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\\\u001a\u00020\u0003H\u00c6\u0003J\t\u0010]\u001a\u00020\u0005H\u00c6\u0003J\t\u0010^\u001a\u00020\u0003H\u00c6\u0003J\t\u0010_\u001a\u00020\u0003H\u00c6\u0003J\t\u0010`\u001a\u00020\u0003H\u00c6\u0003J\u00a6\u0002\u0010a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00102\b\b\u0002\u0010\u0019\u001a\u00020\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u001eH\u00c6\u0001\u00a2\u0006\u0002\u0010bJ\t\u0010c\u001a\u00020\u0010H\u00d6\u0001J\u0013\u0010d\u001a\u00020\u00172\b\u0010e\u001a\u0004\u0018\u00010fH\u00d6\u0003J\t\u0010g\u001a\u00020\u0010H\u00d6\u0001J\t\u0010h\u001a\u00020\u0005H\u00d6\u0001J\u0019\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010#R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010#R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010&R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\b)\u0010*R\u0015\u0010\u001a\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\b,\u0010*R\u0015\u0010\u001f\u001a\u0004\u0018\u00010\u001e\u00a2\u0006\n\n\u0002\u0010/\u001a\u0004\b-\u0010.R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010&R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010&R\u0011\u00102\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b3\u0010#R\u0011\u00104\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b5\u0010#R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010&R\u0015\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u00a2\u0006\n\n\u0002\u0010/\u001a\u0004\b7\u0010.R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010#R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010&R\u0011\u0010\u0016\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010:R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010#R\u0011\u0010\u0019\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010&R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010#R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010#R\u0011\u0010\u0018\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\bA\u0010*R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010&R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010#R\u0011\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010@R\u0015\u0010 \u001a\u0004\u0018\u00010\u001e\u00a2\u0006\n\n\u0002\u0010/\u001a\u0004\bE\u0010.R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010@\u00a8\u0006n"}, d2 = {"Lcom/neuramusic/domain/model/Song;", "Landroid/os/Parcelable;", "id", "", "title", "", "artist", "album", "duration", "path", "albumId", "artistId", "size", "dateAdded", "dateModified", "year", "", "track", "genre", "bitrate", "sampleRate", "albumArt", "isFavorite", "", "playCount", "lastPlayed", "bpm", "key", "mood", "energy", "", "danceability", "valence", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;JJJJJIILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;ZIJLjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;)V", "getAlbum", "()Ljava/lang/String;", "getAlbumArt", "getAlbumId", "()J", "getArtist", "getArtistId", "getBitrate", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBpm", "getDanceability", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getDateAdded", "getDateModified", "displayDuration", "getDisplayDuration", "displaySize", "getDisplaySize", "getDuration", "getEnergy", "getGenre", "getId", "()Z", "getKey", "getLastPlayed", "getMood", "getPath", "getPlayCount", "()I", "getSampleRate", "getSize", "getTitle", "getTrack", "getValence", "getYear", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;JJJJJIILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;ZIJLjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;)Lcom/neuramusic/domain/model/Song;", "describeContents", "equals", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_debug"})
@kotlinx.parcelize.Parcelize
public final class Song implements android.os.Parcelable {
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String artist = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String album = null;
    private final long duration = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String path = null;
    private final long albumId = 0L;
    private final long artistId = 0L;
    private final long size = 0L;
    private final long dateAdded = 0L;
    private final long dateModified = 0L;
    private final int year = 0;
    private final int track = 0;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String genre = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer bitrate = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer sampleRate = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String albumArt = null;
    private final boolean isFavorite = false;
    private final int playCount = 0;
    private final long lastPlayed = 0L;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer bpm = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String key = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String mood = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Float energy = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Float danceability = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Float valence = null;
    
    public Song(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String artist, @org.jetbrains.annotations.NotNull
    java.lang.String album, long duration, @org.jetbrains.annotations.NotNull
    java.lang.String path, long albumId, long artistId, long size, long dateAdded, long dateModified, int year, int track, @org.jetbrains.annotations.Nullable
    java.lang.String genre, @org.jetbrains.annotations.Nullable
    java.lang.Integer bitrate, @org.jetbrains.annotations.Nullable
    java.lang.Integer sampleRate, @org.jetbrains.annotations.Nullable
    java.lang.String albumArt, boolean isFavorite, int playCount, long lastPlayed, @org.jetbrains.annotations.Nullable
    java.lang.Integer bpm, @org.jetbrains.annotations.Nullable
    java.lang.String key, @org.jetbrains.annotations.Nullable
    java.lang.String mood, @org.jetbrains.annotations.Nullable
    java.lang.Float energy, @org.jetbrains.annotations.Nullable
    java.lang.Float danceability, @org.jetbrains.annotations.Nullable
    java.lang.Float valence) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getArtist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAlbum() {
        return null;
    }
    
    public final long getDuration() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPath() {
        return null;
    }
    
    public final long getAlbumId() {
        return 0L;
    }
    
    public final long getArtistId() {
        return 0L;
    }
    
    public final long getSize() {
        return 0L;
    }
    
    public final long getDateAdded() {
        return 0L;
    }
    
    public final long getDateModified() {
        return 0L;
    }
    
    public final int getYear() {
        return 0;
    }
    
    public final int getTrack() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getGenre() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getBitrate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getSampleRate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAlbumArt() {
        return null;
    }
    
    public final boolean isFavorite() {
        return false;
    }
    
    public final int getPlayCount() {
        return 0;
    }
    
    public final long getLastPlayed() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getBpm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getKey() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getMood() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float getEnergy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float getDanceability() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float getValence() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDisplayDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDisplaySize() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component10() {
        return 0L;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final int component13() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component17() {
        return null;
    }
    
    public final boolean component18() {
        return false;
    }
    
    public final int component19() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    public final long component20() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component21() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component22() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component23() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float component24() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float component25() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float component26() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    public final long component5() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final long component8() {
        return 0L;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.neuramusic.domain.model.Song copy(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String artist, @org.jetbrains.annotations.NotNull
    java.lang.String album, long duration, @org.jetbrains.annotations.NotNull
    java.lang.String path, long albumId, long artistId, long size, long dateAdded, long dateModified, int year, int track, @org.jetbrains.annotations.Nullable
    java.lang.String genre, @org.jetbrains.annotations.Nullable
    java.lang.Integer bitrate, @org.jetbrains.annotations.Nullable
    java.lang.Integer sampleRate, @org.jetbrains.annotations.Nullable
    java.lang.String albumArt, boolean isFavorite, int playCount, long lastPlayed, @org.jetbrains.annotations.Nullable
    java.lang.Integer bpm, @org.jetbrains.annotations.Nullable
    java.lang.String key, @org.jetbrains.annotations.Nullable
    java.lang.String mood, @org.jetbrains.annotations.Nullable
    java.lang.Float energy, @org.jetbrains.annotations.Nullable
    java.lang.Float danceability, @org.jetbrains.annotations.Nullable
    java.lang.Float valence) {
        return null;
    }
    
    @java.lang.Override
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override
    public void writeToParcel(@org.jetbrains.annotations.NotNull
    android.os.Parcel parcel, int flags) {
    }
}