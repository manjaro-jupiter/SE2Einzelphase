package com.example.networktest;
/**
 * Created by Ahmad Sharifi
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//......................................send to server Button........................................
        button1 = findViewById(R.id.sendToServerBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText matrikelnummer = findViewById(R.id.Matrikelnummer);

                final String text = matrikelnummer.getText().toString();

                final TextView displayServerMsg = findViewById(R.id.displayMsg);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Socket clientSocket = new Socket("se2-isys.aau.at",53212);

                            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());

                            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                            writer.write(text + "\n");
                            writer.flush();

                            String result = reader.readLine();

                            System.out.println("Server response: " + result);
                            displayServerMsg.setText(result);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
//................................calculation Button.....................................................
        button2 = findViewById(R.id.calculationBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText textBox = findViewById(R.id.Matrikelnummer);

                String inputText = textBox.getText().toString();

                TextView  displayCalcMsg = findViewById(R.id.displayMsg);

                String withoutPrimes = inputText.replaceAll("2|3|5|7", "");

                char[] chars = withoutPrimes.toCharArray();
                Arrays.sort(chars);


                String sorted = new String(chars);

                displayCalcMsg.setText(sorted);
            }
        });
    }
}
