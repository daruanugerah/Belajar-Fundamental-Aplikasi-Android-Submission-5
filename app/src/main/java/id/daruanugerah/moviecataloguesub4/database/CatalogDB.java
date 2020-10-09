package id.daruanugerah.moviecataloguesub4.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.daruanugerah.moviecataloguesub4.model.Catalog;

@Database(entities = {Catalog.class}, version = 1)
public abstract class CatalogDB extends RoomDatabase {
    public abstract CatalogDAO getCatalogDAO();
}
