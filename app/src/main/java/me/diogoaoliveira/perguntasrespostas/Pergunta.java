package me.diogoaoliveira.perguntasrespostas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diogoaoliveira on 25/11/16.
 */

public class Pergunta {
    private int idPergunta;
    private String dsPergunta;
    public List<Resposta> respostas = new ArrayList<>();

    public void setIdPergunta(int id) {
        this.idPergunta = id;
    }

    public int getIdPergunta() {
        return this.idPergunta;
    }

    public void setDsPergunta(String descricao) {
        this.dsPergunta = descricao;
    }

    public String getDsPergunta() {
        return this.dsPergunta;
    }
}
