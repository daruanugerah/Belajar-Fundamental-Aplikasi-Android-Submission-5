package id.daruanugerah.moviecataloguesub4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.daruanugerah.moviecataloguesub4.BuildConfig;
import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.model.Catalog;

public class FavDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CATALOG_FAV = "extra_catalog_fav";

    TextView tvTitle, tvReleaseDate, tvOriLanguage, tvRate, tvOverview;
    ImageView imgPoster, imgFav;
    RatingBar rbCatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvReleaseDate = findViewById(R.id.tv_released_date);
        tvOriLanguage = findViewById(R.id.tv_ori_language);
        tvRate = findViewById(R.id.tv_rate);
        tvOverview = findViewById(R.id.tv_overview);
        imgPoster = findViewById(R.id.img);
        imgFav = findViewById(R.id.icon_fav);
        rbCatalog = findViewById(R.id.rb_catalog);

        final Catalog catalog = getIntent().getParcelableExtra(EXTRA_CATALOG_FAV);

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

    }
}
