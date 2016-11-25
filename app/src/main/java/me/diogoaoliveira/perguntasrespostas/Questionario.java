package me.diogoaoliveira.perguntasrespostas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diogoaoliveira on 25/11/16.
 */

public class Questionario {
    private int idPergunta;
    public List<Integer> respostas = new ArrayList<>();

    public void setIdPergunta(int id) {
        this.idPergunta = id;
    }

    public int getIdPergunta() {
        return this.idPergunta;
    }
}
