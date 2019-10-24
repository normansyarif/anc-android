package ac.id.unja.anc.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ac.id.unja.anc.ForumItemActivity;
import ac.id.unja.anc.Models.Response;
import ac.id.unja.anc.R;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder>  {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    private ArrayList<Response> dataList;

    public ResponseAdapter(ArrayList<Response> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ResponseAdapter.ResponseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_response, parent, false);
        return new ResponseAdapter.ResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResponseAdapter.ResponseViewHolder holder, int position) {
        holder.user_name.setText(dataList.get(position).getUser_name());
        holder.response.setText(dataList.get(position).getResponse());
        holder.created_at.setText(dataList.get(position).getCreated_at());

        Context context = holder.imageView.getContext();
        Glide.with(context).load(routes.imgProfile + dataList.get(position).getId())
                .thumbnail(Glide.with(context).load(R.drawable.ic_broken_image))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ResponseViewHolder extends RecyclerView.ViewHolder{
        private TextView user_name, response, created_at;
        private ImageView imageView;

        public ResponseViewHolder(final View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.username);
            response = itemView.findViewById(R.id.response);
            created_at = itemView.findViewById(R.id.created_at);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
