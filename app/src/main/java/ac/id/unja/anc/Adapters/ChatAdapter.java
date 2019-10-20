package ac.id.unja.anc.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ac.id.unja.anc.R;

public class ChatAdapter {
    Context context;
    Activity activity;

    public ChatAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void addUserChat(String img, Bitmap bitmap){
        TextView textViewMsg = activity.findViewById(R.id.msg);
        String msg = textViewMsg.getText().toString();
        generateLayout(Gravity.RIGHT, R.drawable.textviewround_green, msg, img, bitmap);
        textViewMsg.setText("");
    }

    public void addDoctorChat(String msg, String img, Bitmap bitmap){
        generateLayout(Gravity.LEFT, R.drawable.textviewround, msg, img, bitmap);
    }

    public void generateLayout(int gravity, int background, String msg, String img, Bitmap bitmap){

        // Init Layout Parent

        LinearLayout root = activity.findViewById(R.id.parentChat);
        LinearLayout newChat = new LinearLayout(context);
        newChat.setBackgroundResource(background);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = gravity;
        lp.width = convertToDP(250);
        lp.bottomMargin = convertToDP(10);

        newChat.setLayoutParams(lp);
        newChat.setOrientation(LinearLayout.VERTICAL);

        // Add ImgView

        if(!TextUtils.isEmpty(img)) {
            ImageView imgView = new ImageView(context);
            imgView.setMaxHeight(convertToDP(10));
            imgView.setImageBitmap(bitmap);
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            newChat.addView(imgView);
        }

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
        textViewTime.setText(getTime());
        textViewTime.setGravity(gravity);
        textViewTime.setTextColor(Color.GRAY);
        textViewTime.setTextSize(12);
        newContainer.addView(textViewTime);

        root.addView(newChat);
    }

    public int convertToDP(int val){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, context.getResources().getDisplayMetrics());
    }

    public static String getTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }
}
