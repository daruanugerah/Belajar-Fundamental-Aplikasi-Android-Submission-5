package id.daruanugerah.moviecataloguesub4.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import id.daruanugerah.moviecataloguesub4.BuildConfig;
import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.activity.FavDetailActivity;
import id.daruanugerah.moviecataloguesub4.database.CatalogDAO;
import id.daruanugerah.moviecataloguesub4.database.CatalogDB;
import id.daruanugerah.moviecataloguesub4.model.Catalog;

import static id.daruanugerah.moviecataloguesub4.activity.FavDetailActivity.EXTRA_CATALOG_FAV;

public class CatalogFavAdapter extends RecyclerView.Adapter<CatalogFavAdapter.CatalogFavViewHolder> {
    private Context context;
    private ArrayList<Catalog> cfData = new ArrayList<>();

    public CatalogFavAdapter(Context context) {
        this.context = context;
    }

    public void setCfData(ArrayList<Catalog> cfData) {
        this.cfData = cfData;
        notifyDataSetChanged();

        //Log.d("cfdata", String.valueOf(cfData));
    }

    @NonNull
    @Override
    public CatalogFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cfView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_fav, parent, false);
        return new CatalogFavViewHolder(cfView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogFavViewHolder holder, int position) {
        holder.bind(cfData.get(position));

    }

    @Override
    public int getItemCount() {
        return cfData.size();
    }

    public class CatalogFavViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFav, imgDeleteFav;
        private TextView tvTitle, tvOverview;

        public CatalogFavViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFav = itemView.findViewById(R.id.img_fav);
            imgDeleteFav = itemView.findViewById(R.id.img_delete_fav);
            tvTitle = itemView.findViewById(R.id.tv_title_fav);
            tvOverview = itemView.findViewById(R.id.tv_overview_fav);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(context, FavDetailActivity.class);
                    detailIntent.putExtra(EXTRA_CATALOG_FAV, cfData.get(getAdapterPosition()));
                    context.startActivity(detailIntent);

                }
            });
        }

        public void bind(final Catalog catalog) {
            Glide.with(context)
                    .load(BuildConfig.IMG_URL + catalog.getPosterPath())
                    .into(imgFav);
            tvTitle.setText(catalog.getTitle());
            tvOverview.setText(catalog.getOverview());

            //function for icon delete onCLick
            imgDeleteFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle(R.string.confirm_title);
                    builder.setMessage(R.string.confirm_message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.positive_choice, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            CatalogDAO catalogDAO = Room.databaseBuilder(itemView.getContext(), CatalogDB.class, "db_catalog")
                                    .allowMainThreadQueries()
                                    .build()
                                    .getCatalogDAO();
                            catalogDAO.deleteByUid(cfData.get(getAdapterPosition()).getUid());
                            cfData.remove(catalog);
                            notifyDataSetChanged();
                            Toast.makeText(context, R.string.alert_delete_fav, Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.setNegativeButton(R.string.negative_choice, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);

                        }
                    });
                    builder.show();
                }
            });
        }

    }
}
