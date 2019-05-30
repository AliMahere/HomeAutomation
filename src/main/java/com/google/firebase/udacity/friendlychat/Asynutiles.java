package com.google.firebase.udacity.friendlychat;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class Asynutiles extends AsyncTask<String, Void, String> {

    String LOG_TAG = "dfjkdjifjdi";
    public boolean LOCAL_CONNECTION = false;
    WeakReference<StringBuilder> current;

    public Asynutiles(StringBuilder current) {

        this.current = new WeakReference<>(current);
    }

    public Asynutiles() {
    }


    @Override
    protected String doInBackground(String... strings) {

        String requestUrl = strings[0];
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return jsonResponse;
    }

    @Override
    protected void onPostExecute(String s) {

        if(current !=null) {
            if(current.get().length() <8) {
                current.get().append(s);
            }
            else {
                current.get().delete(0,current.get().length()-1) ;
            }
        }
    }


    /**
     * Returns new URL object from the given string URL.
     */


    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(40000 /* milliseconds */);
            urlConnection.setConnectTimeout(40000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);


        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the data JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
