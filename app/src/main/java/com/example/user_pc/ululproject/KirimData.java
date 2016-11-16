package com.example.user_pc.ululproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class KirimData extends AppCompatActivity {

    Button kirim;
    EditText nama,keterangan;
    String id;
    DatePickerDialog datePickerDialog;

    public static final String KEY_ID = "aa";
    public static final String KEY_PEMERIKSA = "bb";

    public static final String KEY_KETERANGAN = "cc";



    ProgressDialog PD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_data);
        kirim =(Button) findViewById(R.id.button);
        nama = (EditText) findViewById(R.id.editTextNama);
        keterangan = (EditText)findViewById(R.id.editText4);


        PD = new ProgressDialog(this);
        PD.setMessage("Menyimpan data ke Server...");
        PD.setCancelable(false);

        Intent i = getIntent();
        id = i.getStringExtra("data");
       //  id = "160";







      //  tanggal = date.getText().toString();


        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //
                  save();

//                Toast.makeText(KirimData.this, "ID : "+id + " "+"Nama: "+nama.getText().toString()+
//                        "Keterangan: "+ keterangan.getText().toString()
//                        , Toast.LENGTH_SHORT).show();
            }
        });

    }







    private void save() {
       PD.show();
        final String aa = String.valueOf(id.toString());
        final String bb = nama.getText().toString().trim();
        final String cc = keterangan.getText().toString().trim();

//        System.out.println(aa+" "+bb+" "+cc);

       // Toast.makeText(KirimData.this, "Data : "+aa +" "+bb+ " "+cc, Toast.LENGTH_SHORT).show();


        StringRequest sR = new StringRequest(Request.Method.POST, "http://103.43.45.237/ulum/insert_pemeriksa.php?",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                       // System.out.println("bima"+response.toString());
                        PD.dismiss();
                        if(response.equals("Sukses")){

                            Toast.makeText(KirimData.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(KirimData.this, MainActivity.class);
                            startActivity(i);
                            finish();

                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                        PD.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               params.put(KEY_ID, aa);
                params.put(KEY_PEMERIKSA, bb);
                 params.put(KEY_KETERANGAN, cc);

                return params;
            }

        };
        //  Toast.makeText(getApplicationContext(), "Menambahkan makanan = " + makanan, Toast.LENGTH_LONG).show();
        int socketTimeout = 10000;//10 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        sR.setRetryPolicy(policy);
        requestQueue.add(sR);
    }


}
