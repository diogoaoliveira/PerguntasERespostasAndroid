package me.diogoaoliveira.perguntasrespostas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuestionarioActivity extends AppCompatActivity {

    Database mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDB = new Database(this);
        ScrollView sv = new ScrollView(this);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        TextView nome = new TextView(this);
        nome.setText("Insira seu nome:");
        ll.addView(nome);

        final EditText campoNome = new EditText(this);
        campoNome.setHint("nome");
        ll.addView(campoNome);

        final List<Pergunta> perguntas = mDB.getTodasPerguntas();

        for(int i = 0; i < perguntas.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(perguntas.get(i).getDsPergunta());
            ll.addView(tv);

            for(int j = 0; j < perguntas.get(i).respostas.size(); j++) {

                CheckBox cb = new CheckBox(this);
                cb.setId(perguntas.get(i).respostas.get(j).getIdResposta());
                cb.setText(perguntas.get(i).respostas.get(j).getDsResposta());
                ll.addView(cb);
                perguntas.get(i).respondidos.add(cb);
            }
        }

        Button btn  = new Button(this);
        btn.setText("Finalizar");
        ll.addView(btn);
        setContentView(sv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultado(perguntas, campoNome.getText().toString());
            }
        });

    }

    public void calcularResultado(List<Pergunta> perguntasRespondidas, String nome) {
        List<Questionario> questoes = new ArrayList<>();

        for(int i = 0; i < perguntasRespondidas.size() ; i++) {
            Questionario questao = new Questionario();
            questao.setIdPergunta(perguntasRespondidas.get(i).getIdPergunta());
            for(CheckBox check : perguntasRespondidas.get(i).respondidos) {
                if(check.isChecked()) {
                    questao.respostas.add(check.getId());
                }
            }
            questoes.add(questao);
        }

        int counter = 0;
        boolean jaContabilizou = false;
        int numeroQuestoes = perguntasRespondidas.size();

        for(int j = 0; j < questoes.size(); j++) {
            for(int k = 0; k < questoes.get(j).respostas.size(); k++) {
                Integer resposta = questoes.get(j).respostas.get(k);
                if(mDB.verificarResposta(resposta) == true && jaContabilizou == false){
                    jaContabilizou = true;
                    counter++;
                }
            }
            jaContabilizou = false;
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(nome + " acertou " + counter + "/" + numeroQuestoes);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
