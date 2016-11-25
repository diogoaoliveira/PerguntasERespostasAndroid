package me.diogoaoliveira.perguntasrespostas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class PerguntasActivity extends AppCompatActivity {

    Database mDB;
    EditText mEditTextDescricaoPergunta;
    ListView mPerguntasListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perguntas);

        mDB = new Database(this);

        Button adicionarPergunta = (Button)findViewById(R.id.adicionarPergunta);
        adicionarPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPergunta();
            }
        });

        mPerguntasListView = (ListView)findViewById(R.id.listView);
        mPerguntasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(PerguntasActivity.this, mDB.getDefinition(id), Toast.LENGTH_SHORT).show();
                Toast.makeText(PerguntasActivity.this, "Abrir inserção de questões!", Toast.LENGTH_SHORT).show();
            }
        });
        atualizaListaPerguntas();
    }

    private void salvarPergunta() {
        mEditTextDescricaoPergunta = (EditText)findViewById(R.id.edit_pergunta);

        if(mEditTextDescricaoPergunta.getText().toString() == "" ) {
            Toast.makeText(this, "Campo está em branco!", Toast.LENGTH_SHORT).show();
        } else {
            mDB.salvarPergunta(mEditTextDescricaoPergunta.getText().toString());
            mEditTextDescricaoPergunta.setText("");
            Toast.makeText(this, "Pergunta inserida com sucesso!", Toast.LENGTH_SHORT).show();
            atualizaListaPerguntas();
        }
    }

    private void atualizaListaPerguntas() {
        SimpleCursorAdapter simpleCursorAdapter = new
                SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                mDB.getListaPerguntas(),
                new String[]{"dsPergunta"},
                new int[]{android.R.id.text1},
                0);
        mPerguntasListView.setAdapter(simpleCursorAdapter);
    }
}
