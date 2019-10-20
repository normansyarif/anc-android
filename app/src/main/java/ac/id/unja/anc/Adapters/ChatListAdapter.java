package ac.id.unja.anc.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ac.id.unja.anc.ChatActivity;
import ac.id.unja.anc.MainActivity;
import ac.id.unja.anc.Models.ChatList;
import ac.id.unja.anc.R;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder>{
    private List<ChatList> chats;
    private Context context;


    public ChatListAdapter(List<ChatList> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        ChatList model = chats.get(position);

        holder.name.setText(model.getName());
        holder.msg.setText(model.getMsg());

        if(model.getCount() == 0) holder.count.setBackgroundColor(Color.TRANSPARENT);
        else holder.count.setText(model.getCount().toString());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView name, msg, count;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            msg = itemView.findViewById(R.id.msg);
            count = itemView.findViewById(R.id.count);
            layout = itemView.findViewById(R.id.layoutChat);

            layout.setOnHoverListener(new View.OnHoverListener() {
                @Override
                public boolean onHover(View view, MotionEvent motionEvent) {
                    layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("nama", name.getText());
                    context.startActivity(intent);
                }
            });
        }

    }

}