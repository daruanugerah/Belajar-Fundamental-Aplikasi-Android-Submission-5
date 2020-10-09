package id.daruanugerah.moviecataloguesub4.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.daruanugerah.moviecataloguesub4.BuildConfig;
import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.database.CatalogDAO;
import id.daruanugerah.moviecataloguesub4.database.CatalogDB;
import id.daruanugerah.moviecataloguesub4.model.Catalog;
import id.daruanugerah.moviecataloguesub4.viewmodel.MainViewModel;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_CATALOG = "extra_catalog";

    TextView tvTitle, tvReleaseDate, tvOriLanguage, tvRate, tvOverview;
    ImageView imgPoster, imgFav;
    RatingBar rbCatalog;
    //Catalog catalog;
    CatalogDAO catalogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Item");
        }

        tvTitle = findViewById(R.id.tv_title);
        tvReleaseDate = findViewById(R.id.tv_released_date);
        tvOriLanguage = findViewById(R.id.tv_ori_language);
        tvRate = findViewById(R.id.tv_rate);
        tvOverview = findViewById(R.id.tv_overview);
        imgPoster = findViewById(R.id.img);
        imgFav = findViewById(R.id.icon_fav);
        rbCatalog = findViewById(R.id.rb_catalog);

        final Catalog catalog = getIntent().getParcelableExtra(EXTRA_CATALOG);

        String convertRate = Float.toString(catalog.getVoteAverage());
        String percent = "/10";
        String rateValue = convertRate + percent;

        tvTitle.setText(catalog.getTitle());
        tvReleaseDate.setText(catalog.getReleaseDate());
        tvOriLanguage.setText(catalog.getOriginalLanguage());
        tvRate.setText(rateValue);
        tvOverview.setText(catalog.getOverview());
        rbCatalog.setRating(catalog.getVoteAverage());

        Glide.with(this)
                .load(BuildConfig.IMG_URL + catalog.getPosterPath())
                .into(imgPoster);

        catalogDAO = Room.databaseBuilder(this, CatalogDB.class, "db_catalog")
                .allowMainThreadQueries()
                .build()
                .getCatalogDAO();

        //function for icon favorite onClick
        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case android.R.id.home:
                        DetailActivity.this.finish();
                        break;

                    case R.id.icon_fav:
                        try {
                            if (catalogDAO.getCatalogByTitle(catalog.getTitle()) > 0) {
                                v.setEnabled(false);
                            } else {

                                catalogDAO.insert(catalog);
                                setResult(RESULT_OK);
                                //imgFav.setImageResource(R.drawable.icon_love);
                                //v.setEnabled(false);
                                Toast.makeText(DetailActivity.this, R.string.alert_success_fav, Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLiteConstraintException e) {
                            Toast.makeText(DetailActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                }
            }
        });

    }

}
