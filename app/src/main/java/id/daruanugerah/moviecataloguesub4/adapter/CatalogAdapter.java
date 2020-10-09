package id.daruanugerah.moviecataloguesub4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.daruanugerah.moviecataloguesub4.BuildConfig;
import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.activity.DetailActivity;
import id.daruanugerah.moviecataloguesub4.model.Catalog;

import static id.daruanugerah.moviecataloguesub4.activity.DetailActivity.EXTRA_CATALOG;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    private Context context;
    private ArrayList<Catalog> cData = new ArrayList<>();

    public CatalogAdapter(Context context) {
        this.context = context;
    }

    public void setcData(ArrayList<Catalog> cData) {
        this.cData = cData;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_list, parent, false);
        return new CatalogViewHolder(cView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {
        holder.bind(cData.get(position));

    }

    @Override
    public int getItemCount() {
        return cData.size();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {
//        private ImageView img;
//        private TextView tvTitle, tvReleaseDate, tvVoteAverage, tvVoteCount;

        @BindView(R.id.img_list)
        ImageView img;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_released_date)
        TextView tvReleaseDate;
        @BindView(R.id.tv_rate)
        TextView tvVoteAverage;
        @BindView(R.id.tv_count)
        TextView tvVoteCount;

        public CatalogViewHolder(@NonNull View itemView) {
            super(itemView);
//            img = itemView.findViewById(R.id.img_list);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvReleaseDate = itemView.findViewById(R.id.tv_released_date);
//            tvVoteAverage = itemView.findViewById(R.id.tv_rate);
//            tvVoteCount = itemView.findViewById(R.id.tv_count);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(context, DetailActivity.class);
                    detailIntent.putExtra(EXTRA_CATALOG, cData.get(getAdapterPosition()));
                    context.startActivity(detailIntent);

                }
            });
        }

        public void bind(Catalog catalog) {
            String convertRate = Float.toString(catalog.getVoteAverage());
            String convertCount = Integer.toString(catalog.getVoteCount());

            tvTitle.setText(catalog.getTitle());
            tvReleaseDate.setText(catalog.getReleaseDate());
            tvVoteAverage.setText(convertRate);
            tvVoteCount.setText(convertCount);

            Glide.with(context)
                    .load(BuildConfig.IMG_URL + catalog.getPosterPath())
                    .into(img);
        }
    }
}
