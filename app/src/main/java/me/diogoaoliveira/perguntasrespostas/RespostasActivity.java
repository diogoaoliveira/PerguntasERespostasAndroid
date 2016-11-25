package me.diogoaoliveira.perguntasrespostas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class RespostasActivity extends AppCompatActivity {

    Database mDB;
    EditText mEditTextResposta;
    RadioGroup radioRespostas;
    RadioButton radioResposta;
    ListView mRespostasListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respostas);
        final String idPergunta;
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
        mDB = new Database(this);

        Button adicionarResposta = (Button)findViewById(R.id.adicionarResposta);
        adicionarResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarResposta(idPergunta);
            }
        });

        mRespostasListView = (ListView)findViewById(R.id.listViewRespostas);
        mRespostasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(PerguntasActivity.this, mDB.getDefinition(id), Toast.LENGTH_SHORT).show();
                Toast.makeText(RespostasActivity.this, "Id = " + id, Toast.LENGTH_SHORT).show();
            }
        });
        atualizaListaRespostas(idPergunta);
    }

    private void salvarResposta(String idPergunta) {
        mEditTextResposta = (EditText)findViewById(R.id.edit_resposta);
        radioRespostas = (RadioGroup)findViewById(R.id.radio_group);
        int selectedId = radioRespostas.getCheckedRadioButtonId();
        radioResposta = (RadioButton)findViewById(selectedId);

        if(mEditTextResposta.getText().toString() == "" ) {
            Toast.makeText(this, "Campo está em branco!", Toast.LENGTH_SHORT).show();
        } else {
            int correta;
            String valorResposta = radioResposta.getText().toString();
            if(valorResposta.equals("Sim")) {
                correta = 1;
            } else {
                correta = 0;
            }
            mDB.salvarResposta(mEditTextResposta.getText().toString(), correta, Integer.parseInt(idPergunta));
            mEditTextResposta.setText("");
            Toast.makeText(this, "Resposta inserida com sucesso!", Toast.LENGTH_SHORT).show();
            atualizaListaRespostas(idPergunta);
        }
    }

    private void atualizaListaRespostas(String idPergunta) {
        SimpleCursorAdapter simpleCursorAdapter = new
                SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                mDB.getListaRespostas(idPergunta),
                new String[]{"dsResposta"},
                new int[]{android.R.id.text1},
                0);
        mRespostasListView.setAdapter(simpleCursorAdapter);
    }
}
