package com.blazeey.sentimentanalysis.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.blazeey.sentimentanalysis.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.ChasingDots;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private Boolean firstTime = null;
    private EditText searchBar;
    private ImageButton search;
    private Context context;
    private SpinKitView spinKitView;
    private ChasingDots chasingDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        isFirstTime();
        searchBar = findViewById(R.id.search_bar);
        spinKitView = findViewById(R.id.spin_kit);
        chasingDots = new ChasingDots();
        spinKitView.setIndeterminateDrawable(chasingDots);
//        searchBar.setInputType(InputType.TYPE_NULL);

        search = findViewById(R.id.go);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getApplicationContext(),GraphsActivity.class);
//                intent.putExtra("key","mkbhd");
//                startActivity(intent);

                JsonParsing jsonParsing = new JsonParsing(getApplicationContext());
                String query = "{'key':'"+searchBar.getEditableText().toString()+"'}";
                jsonParsing.execute(query);
            }
        });

    }

    private void isFirstTime(){
        if(firstTime==null){
            SharedPreferences sharedPreferences = this.getSharedPreferences("first_time",MODE_PRIVATE);
            firstTime = sharedPreferences.getBoolean("first_time",true);
            if(!firstTime){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("first_time",false);
                editor.apply();
                Intent intent = new Intent(this,FirstActivity.class);
                startActivity(intent);
            }
        }
    }

    public class JsonParsing extends AsyncTask<String, Void, String> {

        public Context context;

        public JsonParsing(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinKitView.setIndeterminateDrawable(chasingDots);
            spinKitView.setVisibility(View.VISIBLE);
            searchBar.setEnabled(false);
            search.setEnabled(false);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            searchBar.setEnabled(true);
            search.setEnabled(true);
            if (!result.contains("error")) {

                Log.v("REQUEST", result);
                Intent intent = new Intent(context, GraphsActivity.class);
                intent.putExtra("result", result);
                intent.putExtra("key",searchBar.getEditableText().toString());
                startActivity(intent);
                spinKitView.setVisibility(View.GONE);
                Toast.makeText(context, "Result Obtained", Toast.LENGTH_SHORT).show();

            }
            else{
                Log.v("REQUEST", result);
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String url = "http://192.168.1.100:5000/retrieve";
            JSONObject result = null;
            try {
                String p = params[0];
                if (p.charAt(0) == '"')
                    return "";
                result = foo(url, parseDataa(params[0]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert result != null;
            return result.toString();
        }

        public JSONObject foo(String url, JSONObject json) {
            JSONObject jsonObjectResp = null;

            try {

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(Integer.MAX_VALUE - 10, TimeUnit.MICROSECONDS)
                        .readTimeout(Integer.MAX_VALUE - 10, TimeUnit.MICROSECONDS)
                        .writeTimeout(Integer.MAX_VALUE - 10, TimeUnit.MICROSECONDS)
                        .build();


                Log.v("JSON", json.toString());
                okhttp3.RequestBody body = RequestBody.create(JSON, json.toString());
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Log.v("JSON", "here");
                okhttp3.Response response = client.newCall(request).execute();

                String networkResp = response.body().string();
                if (!networkResp.isEmpty()) {
                    jsonObjectResp = parseJSONStringToJSONObject(networkResp);
                }
            } catch (Exception ex) {
                String err = String.format("{\"result\":\"false\",\"error\":\"%s\"}", ex.getMessage());
                jsonObjectResp = parseJSONStringToJSONObject(err);
            }

            return jsonObjectResp;
        }

        private JSONObject parseJSONStringToJSONObject(final String strr) {

            JSONObject response = null;
            try {
                response = new JSONObject(strr);
            } catch (Exception ex) {
//              Log.e("Could not parse malformed JSON: \"" + json + "\"");
                try {
                    response = new JSONObject();
                    response.put("result", "failed");
                    response.put("data", strr);
                    response.put("error", ex.getMessage());
                } catch (Exception exx) {
                }
            }
            return response;
        }

        JSONObject parseDataa(String query) throws JSONException {

            JSONObject jsonObject = new JSONObject(query);

            return jsonObject;
        }

    }
}
