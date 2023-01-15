package com.example.wychkomunikator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class wybor extends AppCompatActivity {
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor);
        Intent i = getIntent();
        String [] dane = i.getStringArrayExtra("string");

        String Id= dane[0];
        String Typ = dane[1];
        //ID,typ
        TextView status = (TextView) findViewById(R.id.textView3);
        TextView id = (TextView) findViewById(R.id.txt_id);
        TextView imie = (TextView) findViewById(R.id.txt_imie);
        TextView nazwisko = (TextView) findViewById(R.id.txt_nazwisko);
        TextView typ = (TextView) findViewById(R.id.txt_typ);

        Button wybierz = (Button) findViewById(R.id.bt_wybierz);
        Spinner odbiorca = (Spinner) findViewById(R.id.sp_rodzic);


        TextView test = (TextView) findViewById(R.id.txt_test);
        test.setText(dane[0]);
        typ.setText(Typ);

        client = new OkHttpClient();
        //wstawienie danych użytkownika
        String url ;

            url = "http://10.0.2.2/PobierzRodzic.php?dane1="+Id;
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    id.setText(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();
                        wybor.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //id.setText(myResponse); /*try { JSONObject jsonObject = new JSONObject(myResponse); tekst.setText(jsonObject.getString("a")); }catch (Exception err){ }*/
                                try { //JSONObject jsonObject = new JSONObject(myResponse);
                                    if(Typ=="rodzic") {
                                        test.setText(myResponse);
                                    }
                                    /*
                                    JSONArray jsonarray = new JSONArray(myResponse);
                                    JSONObject jsonObject = new JSONObject(jsonarray.getJSONObject(0)+"");
                                    //{ID,Typ};
                                    id.setText((jsonObject.getString("idrodzic")));
                                    imie.setText((jsonObject.getString("imie")));
                                    nazwisko.setText((jsonObject.getString("nazwisko")));
                                    //tekst.setText(jsonarray.getString(1));
                                    //tekst.setText(myResponse);
                                    */

                                }
                                catch (Exception err){
                                    id.setText(err.getMessage());
                                }
                            }
                        });
                    }
                }
            });

            url = "http://10.0.2.2/PobierzNauczyciel.php?dane1="+Id;
            Request request2 = new Request.Builder().url(url).build();
            client.newCall(request2).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    id.setText(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();
                        wybor.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //id.setText(myResponse); /*try { JSONObject jsonObject = new JSONObject(myResponse); tekst.setText(jsonObject.getString("a")); }catch (Exception err){ }*/
                                try { //JSONObject jsonObject = new JSONObject(myResponse);
                                    JSONArray jsonarray = new JSONArray(myResponse);
                                    JSONObject jsonObject = new JSONObject(jsonarray.getJSONObject(0)+"");
                                    //{ID,Typ};
                                    test.setText(myResponse);
                                    if(Typ=="nauczyciel") {
                                        id.setText((jsonObject.getString("idnauczyciel")));
                                    }
                                    //imie.setText((jsonObject.getString("imie")));
                                    //nazwisko.setText((jsonObject.getString("nazwisko")));
                                    //tekst.setText(jsonarray.getString(1));
                                    //tekst.setText(myResponse);
                                }
                                catch (Exception err){
                                    id.setText(err.getMessage());
                                }
                            }
                        });
                    }
                }
            });

        //pobranie danych do spinnera

    }
}