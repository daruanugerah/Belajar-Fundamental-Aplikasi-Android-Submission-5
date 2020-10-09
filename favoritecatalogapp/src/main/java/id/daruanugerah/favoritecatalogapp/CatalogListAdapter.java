package id.daruanugerah.favoritecatalogapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CatalogListAdapter extends RecyclerView.Adapter<CatalogListAdapter.CatalogViewHolder> {

    private Cursor cursor;
    private Context context;

    public CatalogListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_list, parent, false);
        return new CatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {
        holder.bind(cursor.moveToPosition(position));

    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public void setData(Cursor ncursor) {
        cursor = ncursor;
        notifyDataSetChanged();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_fav)
        CircleImageView imgFav;
        @BindView(R.id.title_fav)
        TextView titleFav;
        @BindView(R.id.overview_fav)
        TextView overviewFav;

        public CatalogViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(boolean moveToPosition) {
            if (moveToPosition) {
                titleFav.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_TITLE)));
                overviewFav.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_OVERVIEW)));

                Glide.with(context)
                        .load(Utils.IMAGE_BASE_URL + cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_IMAGE)))
                        .into(imgFav);
            }
        }
    }
}
