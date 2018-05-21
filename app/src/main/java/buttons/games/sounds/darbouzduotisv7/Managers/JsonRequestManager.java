package buttons.games.sounds.darbouzduotisv7.Managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import buttons.games.sounds.darbouzduotisv7.R;
import buttons.games.sounds.darbouzduotisv7.VolleyResponseListener;

public class JsonRequestManager {

    @SuppressLint("StaticFieldLeak")
    public static JsonRequestManager instance = null;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static RequestQueue requestQueue;

    public JsonRequestManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        this.context = context;
    }

    public static synchronized JsonRequestManager getInstance(Context context)
    {
        if (null == instance)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            instance = new JsonRequestManager(context);
        return instance;
    }

    public static synchronized JsonRequestManager getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(JsonRequestManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public static void jsonRequest(String WORD, String PARAM, final VolleyResponseListener listener){
        requestQueue = Volley.newRequestQueue(context);
        String uri = Uri.parse(context.getString(R.string.API_LINK)
                + WORD
                + "/"
                + PARAM)
                .buildUpon()
                .build().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("X-Mashape-Key", context.getString(R.string.API_KEY));
                params.put("Accept", "text/plain");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
