package com.example.user_pc.ululproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView  percobaan;
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.buttonScan);

        percobaan = (TextView) findViewById(R.id.textView4);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent captureIntent = new Intent(MainActivity.this, CaptureActivity.class);


                CaptureActivityIntents.setPromptMessage(captureIntent, "Membaca QR Code...");


                startActivityForResult(captureIntent, 0);
            }
        });

    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK & data != null) {
                String value = data.getStringExtra("SCAN_RESULT");


                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Data QR Code");
                alertDialog.setMessage(value);
                // alertDialog.setIcon(R.drawable.);

                String string = value;
                String[] parts = string.split(" ");
                 id = parts[1];


                percobaan.setText(id);


                alertDialog.setPositiveButton("Update Data", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                           Intent i = new Intent(MainActivity.this, KirimData.class);
                        i.putExtra("data",id);
                        Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();



                    }
                });



                // Showing Alert Message
                alertDialog.show();


              //  result.setText(value);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                percobaan.setText("Scanning Gagal, mohon coba lagi.");
            }
        } else {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
