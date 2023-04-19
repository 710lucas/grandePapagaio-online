package org.example.papagaio;

import java.io.Serializable;
import java.util.Calendar;

//so precisa do implements Serializable se for salvar em arquivo, o que não é necessário
public class Post implements Serializable {

    private String texto;
    private Usuario autor;
    private Calendar data;

    public Post(String texto, Usuario autor){
        super();
        this.texto = texto;
        this.autor = autor;
        data = Calendar.getInstance();
    }

    public void lerPostagem(){
        System.out.printf("%s --> %s (%d/%d/%d %d:%d)\n", autor.getNome(), texto, data.get(Calendar.DAY_OF_MONTH), data.get(Calendar.MONTH)+1, data.get(Calendar.YEAR), data.get(Calendar.HOUR_OF_DAY), data.get(Calendar.MINUTE));
    }

    public String getTexto(){
        return texto;
    }

    public Usuario getAutor(){
        return autor;
    }

    public Calendar getData(){
        return data;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }


}
