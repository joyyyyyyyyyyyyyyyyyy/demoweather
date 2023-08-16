package sg.edu.rp.c346.id22022096.demoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView lvweather;
    //ArrayAdapter<Weather> aaWeather;

    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvweather = findViewById(R.id.lvweather);
        //create variable client as new obj
        client = new AsyncHttpClient();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //creates a new empty arraylist of weather objects using variable name alWeather
        ArrayList<Weather> alWeather = new ArrayList<Weather>();
        //aaWeather=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, alWeather);

        client.get("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast", new JsonHttpResponseHandler() {

            String area;
            String forecast;

            //@Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    JSONArray jsonArrForecasts = firstObj.getJSONArray("forecasts");
                    for(int i = 0; i < jsonArrForecasts.length(); i++) {
                        JSONObject jsonObjForecast = jsonArrForecasts.getJSONObject(i);
                        area = jsonObjForecast.getString("area");
                        forecast = jsonObjForecast.getString("forecast");
                        Weather weather = new Weather(area, forecast);
                        alWeather.add(weather);
                    }
                }
                catch(JSONException e){

                }

                //POINT X – Code to display List View
                ArrayAdapter aaWeather=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, alWeather);
                lvweather.setAdapter(aaWeather);

            }//end onSuccess
        });
    }//end onResume

}