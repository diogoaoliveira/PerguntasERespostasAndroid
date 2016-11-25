package me.diogoaoliveira.perguntasrespostas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

        List<Pergunta> perguntas = mDB.getTodasPerguntas();

        for(int i = 0; i < perguntas.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(perguntas.get(i).getDsPergunta());
            ll.addView(tv);

            for(int j = 0; j < perguntas.get(i).respostas.size(); j++) {
                CheckBox cb = new CheckBox(this);
                cb.setText(perguntas.get(i).respostas.get(j).getDsResposta());
                ll.addView(cb);
            }
        }

        Button btn  = new Button(this);
        btn.setText("Finalizar");
        ll.addView(btn);

        setContentView(sv);

    }
}
