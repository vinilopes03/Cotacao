package com.st.smarttrash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by vinic on 04/07/2017.
 */

public class coinFragment extends android.support.v4.app.Fragment {
    private static final coinFragment instance = new coinFragment();

    private ArrayAdapter<String> coinAdapter;

    public coinFragment(){
    }

    public static coinFragment getInstance(){
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] data = {
        };

        List<String> trashData = new ArrayList<String>(Arrays.asList(data));
        coinAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        com.st.smarttrash.R.layout.list_item_coin, // The name of the layout ID.
                        com.st.smarttrash.R.id.list_item_trash_textview, // The ID of the textview to populate.
                        trashData);

        View rootView = inflater.inflate(com.st.smarttrash.R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(com.st.smarttrash.R.id.list_view_trash);
        listView.setAdapter(coinAdapter);



        return rootView;
    }
    public void updateStatus(){
        TrashTask trashTask = new TrashTask(getActivity());
        trashTask.execute();
    }

    @Override
    public void onStart(){
        super.onStart();
        updateStatus();
    }


    private class TrashTask extends AsyncTask<String , Void,  String[]>  {

        private final String LOG_TAG = TrashTask.class.getSimpleName();

        Context ctx;
        String key;
        SharedPreferences prefs;

        TrashTask(Context ctx){
            this.ctx = ctx;
            prefs = ctx.getSharedPreferences("chave",Context.MODE_PRIVATE);
        }

        private String[] getDataFromJson(String dataJsonStr)
                throws JSONException {


            final String BPI = "bpi";


            String [] last = new String [3];

            JSONObject jsonData = new JSONObject(dataJsonStr);
            JSONObject search = jsonData.getJSONObject(BPI);

            JSONObject usd = search.getJSONObject("USD");
            String rate = usd.getString("rate_float");
            String code = usd.getString("code");
            String desc = usd.getString("description");

            last[0] = "Moeda: "+desc+"\n\n"+"Codigo: "+code+"\n\n"+"US$ "+rate;


            JSONObject gbp = search.getJSONObject("GBP");
            String rate2 = gbp.getString("rate_float");
            String code2 = gbp.getString("code");
            String desc2 = gbp.getString("description");

            last[1] = "Moeda: "+desc2+"\n\n"+"Codigo: "+code2+"\n\n"+"£ "+rate2;

            JSONObject eur = search.getJSONObject("EUR");
            String rate3 = eur.getString("rate_float");
            String code3 = eur.getString("code");
            String desc3 = eur.getString("description");

            last[2] = "Moeda: "+desc3+"\n\n"+"Codigo: "+code3+"\n\n"+"€ "+rate3;

            return last;


        }

        @Override
        public void onPreExecute() {
            key = prefs.getString("chave", "chave");
        }

        protected String[] doInBackground(String...params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String coinJsonStr;

            try {



                // Possible parameters are avaiable at OWM's forecast API page, at
                URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                coinJsonStr = buffer.toString();
                try{
                return getDataFromJson(coinJsonStr);
                } catch (JSONException e){
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the speakthing data, there's no point in attemping
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result[]) {
            coinAdapter.clear();
            if(result != null){
                for(String coinStr: result){
                    coinAdapter.add(coinStr);
                }
            }
        }

    }


}
