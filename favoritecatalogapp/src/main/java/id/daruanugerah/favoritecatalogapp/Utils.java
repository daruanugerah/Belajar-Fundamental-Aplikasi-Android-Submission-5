package id.daruanugerah.favoritecatalogapp;

import android.net.Uri;

public class Utils {
    public static final String TABLE_NAME = "catalog";
    public static final String AUTHORITY = "id.daruanugerah.moviecataloguesub4.provider";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_IMAGE = "image";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

}
