package id.daruanugerah.moviecataloguesub4.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import id.daruanugerah.moviecataloguesub4.model.Catalog;

@Dao
public interface CatalogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (Catalog catalog);

    @Query("DELETE FROM catalog WHERE uid = :uid")
    void deleteByUid(int uid);

    @Query("SELECT COUNT(uid) FROM catalog WHERE title = :title")
    int getCatalogByTitle (String title);

    @Query("SELECT * FROM catalog WHERE catalogType = :catalogType")
    List<Catalog> getTypeByCatalogType (int catalogType);

    @Query("SELECT * FROM catalog")
    Cursor selectAll();

    @Query("SELECT * FROM catalog WHERE uid = :uid")
    Cursor selectById(long uid);

    @Query("SELECT * FROM catalog")
    List<Catalog> getAllFavCatalogs();

}
