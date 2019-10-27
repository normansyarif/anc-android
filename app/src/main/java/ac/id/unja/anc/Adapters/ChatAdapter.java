package ac.id.unja.anc.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;

import ac.id.unja.anc.R;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;

public class ChatAdapter {

    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    Context context;
    Activity activity;

    public ChatAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void addUserChatDB(String id, String msg, String time){
        generateLayout(Gravity.RIGHT, R.drawable.textviewround_green, msg, time, id, null, true);
    }

    public void addDoctorChatDB(String id, String msg, String time){
        generateLayout(Gravity.LEFT, R.drawable.textviewround, msg, time, id, null, true);
    }

    public void addUserChat(String img, String msg, String time, Bitmap bitmap){
        generateLayout(Gravity.RIGHT, R.drawable.textviewround_green, msg, time, img, bitmap, false);
    }

    public void addDoctorChat(String img, String msg, String time, Bitmap bitmap){
        generateLayout(Gravity.LEFT, R.drawable.textviewround, msg, time, img, bitmap, false);
    }

    public void generateLayout(int gravity, int background, String msg, String time, String img, Bitmap bitmap, boolean db){

        // Init Layout Parent

        //LinearLayout root = activity.findViewById(R.id.parentChat);
        LinearLayout newChat = new LinearLayout(context);
        newChat.setBackgroundResource(background);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = gravity;
        lp.width = convertToDP(250);
        lp.bottomMargin = convertToDP(10);

        newChat.setLayoutParams(lp);
        newChat.setOrientation(LinearLayout.VERTICAL);

        // Add ImgView

        if(bitmap != null) {
            ImageView imgView = new ImageView(context);
            imgView.setImageBitmap(bitmap);
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            newChat.addView(imgView);
            imgView.getLayoutParams().height = 500;
        }

//        if(db){
//            ImageView imgView = new ImageView(context);
//            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            newChat.addView(imgView);
//            imgView.getLayoutParams().height = 500;
//
//            Glide.with(context).load(routes.imgChat + img)
//                    .thumbnail(Glide.with(context).load(R.drawable.ic_broken_image))
//                    .into(imgView);
//        }

        // Add Layout Chat

        LinearLayout newContainer = new LinearLayout(context);
        newContainer.setOrientation(LinearLayout.VERTICAL);
        newContainer.setPadding(convertToDP(7), convertToDP(7), convertToDP(7), convertToDP(7));
        newChat.addView(newContainer);

        // Add TextView Message

        TextView textViewMsg = new TextView(context);
        textViewMsg.setText(msg);
        textViewMsg.setPadding(0, 0, 0, convertToDP(7));
        textViewMsg.setTextColor(Color.BLACK);
        textViewMsg.setTextSize(15);
        textViewMsg.setTextIsSelectable(true);
        newContainer.addView(textViewMsg);

        // Add TextView Time

        TextView textViewTime = new TextView(context);
        textViewTime.setText(time);
        textViewTime.setGravity(gravity);
        textViewTime.setTextColor(Color.GRAY);
        textViewTime.setTextSize(12);
        newContainer.addView(textViewTime);

        //root.addView(newChat);
    }

    public int convertToDP(int val){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, context.getResources().getDisplayMetrics());
    }

    public static String getTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }


}
