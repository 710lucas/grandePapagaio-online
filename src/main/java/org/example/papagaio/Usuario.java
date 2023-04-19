package org.example.papagaio;

import java.io.Serializable;
import java.util.ArrayList;

//so precisa do implements Serializable se for salvar em arquivo, o que não é necessário
public class Usuario implements Serializable {

    private ArrayList<Post> postagens = new ArrayList<>();

    private ArrayList<Usuario> seguindo = new ArrayList<>();

    private String nome;

    private boolean logado = false;

    public Usuario(String nome){
        super();
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }

    public void seguir(Usuario usuario){
        if(!logado){
            System.out.println("Você precisa estar logado nessa conta para conseguir seguir alguem");
            return;
        }

        if(usuario.equals(this)){
            System.out.println("Você não pode seguir seu proprio usuário");
            return;
        }

        if(seguindo.contains(usuario)){
            System.out.println("Você já está seguindo este usuário");
            return;
        }

        seguindo.add(usuario);
    }
    public void postar(String texto){
        if(!logado) {
            System.out.println("Você precisa estar logado para conseguir postar nessa conta");
            return;
        }

        postagens.add(new Post(texto, this));
        System.out.println("Postagem feita com sucesso");
    }

    public ArrayList<Post> getPostagens(){
        return postagens;
    }

    public void lerPostagens(){
        for(Post postagem : postagens){
            postagem.lerPostagem();
        }
    }

    public void lerPostagensSeguidores(){
        for(Usuario usuario : seguindo){
            usuario.lerPostagens();
        }
    }

    public void lerMural(){
        lerPostagens();
        lerPostagensSeguidores();
    }

    public void verSeguidores(){
        for(Usuario usuario : seguindo){
            System.out.println("Seguindo: "+usuario.getNome());
        }
    }

    public boolean isLogado(){
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public ArrayList<Usuario> getSeguindo(){
        return seguindo;
    }
}
