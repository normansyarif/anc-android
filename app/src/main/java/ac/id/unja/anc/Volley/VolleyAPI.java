package ac.id.unja.anc.Volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class VolleyAPI {

    public void getDataVolley(String url, final Context context, final VolleyResponseListener listener){
        RequestQueue queue = (RequestQueue) Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onErrorResponse(error);
                        Log.e("volleyerror", error.toString());
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void postDataVolley(final Map<String, String> data, String url, final Context context, final VolleyResponseListener listener){
        RequestQueue queue = (RequestQueue) Volley.newRequestQueue(context);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onErrorResponse(error);
                        Log.e("volleyerror", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return data;
            }
        };

        queue.add(postRequest);
    }

    public void handleError(String error, Context context){
        Log.e("errorvolley", error);
        Toast.makeText(context, "Maaf terjadi kesalahan. Mohon periksa internet atau coba lagi nanti.", Toast.LENGTH_SHORT).show();
    }

}
