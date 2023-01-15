package com.example.wychkomunikator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.*;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText login = (EditText) findViewById(R.id.txt_login);
        EditText haslo = (EditText) findViewById(R.id.txt_haslo);
        Button loguj = (Button) findViewById(R.id.bt_loguj);
        EditText id = (EditText) findViewById(R.id.txt_login2);

        loguj.setOnClickListener(new View.OnClickListener() {
            EditText id = (EditText) findViewById(R.id.txt_login2);
            public String ID ="";
            public String Typ ="";
            @Override
            public void onClick(View view) {


                String Login = login.getText().toString();
                String Haslo = haslo.getText().toString();


                client = new OkHttpClient();
                //String url = "http://10.0.2.2/loguj.php?dane1="+Login+"&dane2="+Haslo; //testowe

                String url = "http://10.0.2.2/loguj.php?dane1="+Login+"&dane2="+Haslo;
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
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //id.setText(myResponse); /*try { JSONObject jsonObject = new JSONObject(myResponse); tekst.setText(jsonObject.getString("a")); }catch (Exception err){ }*/
                                    try { //JSONObject jsonObject = new JSONObject(myResponse);
                                        JSONArray jsonarray = new JSONArray(myResponse);
                                        JSONObject jsonObject = new JSONObject(jsonarray.getJSONObject(0)+"");
                                        //{ID,Typ};
                                        ID = (jsonObject.getString("IDuzytkownik"));
                                        Typ = (jsonObject.getString("typ"));
                                        //tekst.setText(jsonarray.getString(1));
                                        //tekst.setText(myResponse);
                                        String [] uzytkownik_dane = {jsonObject.getString("IDuzytkownik"),jsonObject.getString("typ")};
                                        Intent i = new Intent(MainActivity.this, wybor.class);
                                        i.putExtra("string",uzytkownik_dane);
                                        startActivity(i);
                                    }
                                    catch (Exception err){
                                        id.setText(err.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}