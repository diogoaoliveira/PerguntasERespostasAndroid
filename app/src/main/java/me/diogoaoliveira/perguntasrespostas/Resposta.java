package me.diogoaoliveira.perguntasrespostas;

/**
 * Created by diogoaoliveira on 25/11/16.
 */

public class Resposta {
    private int idResposta;
    private String dsResposta;
    private int flCorreta;

    public void setIdResposta(int id) {
        this.idResposta = id;
    }

    public int getIdResposta() {
        return this.idResposta;
    }

    public void setDsResposta(String descricao) {
        this.dsResposta = descricao;
    }

    public String getDsResposta() {
        return this.dsResposta;
    }

    public void setFlCorreta(int flag) {
        this.flCorreta = flag;
    }

    public int getFlCorreta() {
        return this.flCorreta;
    }
}
