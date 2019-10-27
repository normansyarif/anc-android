package ac.id.unja.anc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ProgressBar;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#ffeead")),
                    new ColorDrawable(Color.parseColor("#93cfb3")),
                    new ColorDrawable(Color.parseColor("#fd7a7a")),
                    new ColorDrawable(Color.parseColor("#faca5f")),
                    new ColorDrawable(Color.parseColor("#1ba798")),
                    new ColorDrawable(Color.parseColor("#6aa9ae")),
                    new ColorDrawable(Color.parseColor("#ffbf27")),
                    new ColorDrawable(Color.parseColor("#d93947"))
            };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static String FormatTime(String s) {
        String newString = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(s);
            newString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newString;
    }

    public static String DateToTimeFormat(String oldstringDate){
        PrettyTime p = new PrettyTime(new Locale(getCountry()));
        String isTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                    Locale.ENGLISH);
            Date date = sdf.parse(oldstringDate);
            isTime = p.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTime;
    }

    public static String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getLanguage());
        return country.toLowerCase();
    }

    public static String splitDesc(String string, int noOfWords){
        String[] words = string.split(" ");
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < noOfWords; i++) {
            newString.append(" ").append(words[i]);
        }

        return newString.toString().trim();
    }

    public static long dateToWeek(String sDate) {
        long diff = getDateDiff(sDate);
        return diff/7;
    }

    public static String weekToDate(int week) {
        int days = week * 7;
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, 0-days);
        now = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(now);
    }

    private static long getDateDiff(String sDate) {
        Date date= null;
        Date now = new Date();

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long duration  = now.getTime() - date.getTime();

        return TimeUnit.MILLISECONDS.toDays(duration);
    }

    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
