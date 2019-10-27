package ac.id.unja.anc.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import ac.id.unja.anc.Models.Chat;
import ac.id.unja.anc.R;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;

public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.MyViewHolder> {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    private List<Chat> chatList;
    private Context context;


    public ChatItemAdapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Chat model = chatList.get(position);
        holder.id.setText(model.getId());
        holder.msg.setText(model.getMsg());
        holder.time.setText(model.getTime());
        holder.status.setText(model.getStatus());

        Context context = holder.imageView.getContext();
        if(!model.getImg().equals("0")){
            Glide.with(context).load(routes.imgChat + model.getId())
                    .thumbnail(Glide.with(context).load(R.drawable.ic_broken_image))
                    .into(holder.imageView);
        } else holder.imageView.setVisibility(View.GONE);

        if(model.getSender_id().equals("1")){
            holder.layout.setBackgroundResource(R.drawable.textviewround);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.LEFT;
            lp.width = convertToDP(250);
            lp.topMargin = convertToDP(10);

            holder.layout.setLayoutParams(lp);
        }
    }

    public int convertToDP(int val){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView id, msg, time, status;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.container);
            id = itemView.findViewById(R.id.id);
            msg = itemView.findViewById(R.id.msg);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}

