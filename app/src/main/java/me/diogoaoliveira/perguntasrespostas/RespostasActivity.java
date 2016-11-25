package me.diogoaoliveira.perguntasrespostas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RespostasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respostas);
        String idPergunta;
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if(extras == null) {
                idPergunta = null;
            } else {
                idPergunta = extras.getString("idPergunta");
            }
        } else {
            idPergunta = (String) savedInstanceState.getSerializable("idPergunta");
        }


    }
}
