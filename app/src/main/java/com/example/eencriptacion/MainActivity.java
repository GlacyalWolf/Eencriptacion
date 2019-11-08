package com.example.eencriptacion;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String Fichero = "encript.xml";
    private TextView original;
    private TextView encriptado;
    private Button encriptar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        original = findViewById(R.id.original);
        encriptado = findViewById(R.id.encriptado);
        encriptar = (Button) findViewById(R.id.button2);

        encriptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String linea= original.getText().toString();
                    RSA rsa = new RSA();
                    rsa.setContext(getBaseContext());
                    rsa.genKeyPair(1024);
                    rsa.saveToDiskPrivateKey("rsa.pri");
                    rsa.saveToDiskPublicKey("rsa.pub");
                    String encode_text = rsa.Encrypt(linea);
                    encriptado.setText(encode_text);

                } catch (Exception e) {

                }
                String linea= original.getText().toString();
                String encripted = encriptado.getText().toString();

                try {
                    XML(linea,encripted);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void XML(String linea,String encripted) throws IOException {
        Date currentTime = Calendar.getInstance().getTime();
        String converttoxml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<content_file>\n"+
                "\t<data>\n"+
                "\t\t<time>"+currentTime + "</time>\n"+
                "\t\t<text>"+linea+"</text>\n"+
                "\t\t<cipher_text>"+encripted+"</cipher_text>\n" +
                "\t\t</data>\n"+
                "</content_file>\n";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(Fichero, MODE_PRIVATE);
            fos.write(converttoxml.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
    }

}
