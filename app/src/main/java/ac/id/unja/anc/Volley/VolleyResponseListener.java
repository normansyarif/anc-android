package ac.id.unja.anc.Volley;

import com.android.volley.VolleyError;

public interface VolleyResponseListener {
        void onResponse(String response);
        void onErrorResponse(VolleyError error);
}
