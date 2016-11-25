package me.diogoaoliveira.perguntasrespostas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button perguntas = (Button)findViewById(R.id.cadastroPerguntas);
        Button questionario = (Button)findViewById(R.id.reponderQuestionario);

        perguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrarCadastroPerguntas();
            }
        });

        questionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responderQuestionario();
            }
        });
    }

    public void entrarCadastroPerguntas() {
        Intent intent = new Intent(this, PerguntasActivity.class);
        startActivity(intent);
    }

    public void responderQuestionario() {
        Intent intent = new Intent(this, QuestionarioActivity.class);
        startActivity(intent);
    }

}
