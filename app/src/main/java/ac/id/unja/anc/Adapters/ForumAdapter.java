package ac.id.unja.anc.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ac.id.unja.anc.ForumItemActivity;
import ac.id.unja.anc.Models.Forum;
import ac.id.unja.anc.R;
import ac.id.unja.anc.Utils;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.MyViewHolder>{
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    private List<Forum> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public ForumAdapter(List<Forum> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_forum, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Forum model = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.ic_person_round);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        String img;
        try{
            img = model.getAvatar();
        } catch (Exception f) {
            img = "";
        }


        Glide.with(context)
                .load(img)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(model.getTitle());
        holder.userName.setText(model.getUserName());
        holder.responseCount.setText(String.format("%d tanggapan", model.getResponseCount()));
        holder.createdAt.setText(Utils.DateFormat(model.getCreatedAt()));

        Context context = holder.imageView.getContext();
        Glide.with(context).load(routes.imgProfile + model.getToken())
                .thumbnail(Glide.with(context).load(R.drawable.ic_person_round))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView id, title, createdAt, userName, responseCount;
        ImageView imageView;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            createdAt = itemView.findViewById(R.id.createdAt);
            userName = itemView.findViewById(R.id.user_name);
            responseCount = itemView.findViewById(R.id.response_count);
            imageView = itemView.findViewById(R.id.img);

            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void addItem(List<Forum> mArticles) {
        for(Forum ar : mArticles) {
            articles.add(ar);
        }

        notifyDataSetChanged();
    }
}
