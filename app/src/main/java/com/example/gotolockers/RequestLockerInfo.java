package com.example.gotolockers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestLockerInfo extends AsyncTask<String, Void, Void> {
    public RequestLockerInfo() {
        // set context variables
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();

            jsonParam.put("language", "Python");
            jsonParam.put("framework", "Flask");
            jsonParam.put("website", "Goto");
            JSONObject innerJson = new JSONObject();
            innerJson.put("python", "3.4");
            innerJson.put("flask", "1.0");
            jsonParam.put("version_info", innerJson);
            String[] innerArray = new String[] {"query", "form", "json"};
            jsonParam.put("examples", innerArray);
            jsonParam.put("boolean_test", true);

            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            StringBuilder sb = new StringBuilder();
            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            }

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());
            Log.i("RESPONSE", sb.toString());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
