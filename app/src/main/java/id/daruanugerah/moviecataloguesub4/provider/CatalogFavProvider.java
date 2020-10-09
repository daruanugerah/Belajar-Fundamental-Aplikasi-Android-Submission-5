package id.daruanugerah.moviecataloguesub4.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import id.daruanugerah.moviecataloguesub4.database.CatalogDAO;
import id.daruanugerah.moviecataloguesub4.database.CatalogDB;

public class CatalogFavProvider extends ContentProvider {
    private CatalogDB catalogDB;
    private CatalogDAO catalogDAO;
    private static final String DB_NAME = "db_catalog";
    private static final String DB_TABLE = "catalog";
    private static final String AUTHORITY = "id.daruanugerah.moviecataloguesub4.provider";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CODE_FAV_DIR = 1;
    private static final int CODE_FAV_ITEM = 2;

    static {
        uriMatcher.addURI(AUTHORITY, DB_TABLE, CODE_FAV_DIR);
        uriMatcher.addURI(AUTHORITY, DB_TABLE + "/#", CODE_FAV_ITEM);
    }


    @Override
    public boolean onCreate() {
        catalogDB = Room.databaseBuilder(getContext(), CatalogDB.class, DB_NAME).build();
        catalogDAO = catalogDB.getCatalogDAO();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = uriMatcher.match(uri);
        if (code == CODE_FAV_DIR || code == CODE_FAV_ITEM) {

            final Context context = getContext();
            if (context == null)
                return null;

            final Cursor cursor;
            if (code == CODE_FAV_DIR)
                cursor = catalogDAO.selectAll();
            else
                cursor = catalogDAO.selectById(ContentUris.parseId(uri));
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;

        } else {
            throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        //return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
