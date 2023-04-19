package org.example.papagaio;

import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;

//so precisa do implements Serializable se for salvar em arquivo, o que não é necessário
public class Papagaio implements Serializable{


    private final String PAPAGAIO_ARQUIVO = "Papagaiagens.ppg";
    private  ArrayList<Usuario> usuarios = new ArrayList<>();


    public boolean criarUsuario(String nome){

        if(getUsuario(nome) != null){
            System.out.println("Usuario ja existe, tente outro nome");
            return false;
        }

        if(nome.contains(" ")) {
            System.out.println("Nome de usuário não pode possuir espaços");
            return false;
        }

        usuarios.add(new Usuario(nome));
        System.out.println("Usuário adicionado com sucesso");
        return true;

    }

    public Usuario getUsuario(String nome){
        for(Usuario usuario : usuarios){
            if(usuario.getNome().equals(nome)){
                return usuario;
            }
        }

        System.out.println("Não foi encontrado um usuario com este nome");
        return null;
    }

    public void listarUsuarios(){
        for(Usuario usuario : usuarios){
            System.out.println(usuario.getNome());
        }
    }

    public void listarSeguindo(String nome){
        try {
            getUsuario(nome).verSeguidores();
        } catch(NullPointerException e){
            System.out.println("usuario não encontrado");
        }
    }

    public void postar(String nome, String textoParaPostar){
        getUsuario(nome).postar(textoParaPostar);
    }

    public void verMural(String nome){
        getUsuario(nome).lerMural();
    }

    public void seguir(String nomeUsuarioQueVaiSeguir, String nomeUsuarioQueVaiSerSeguido){

        if(getUsuario(nomeUsuarioQueVaiSeguir) == null) {
            System.out.println("usuario " + nomeUsuarioQueVaiSeguir + " não foi encontrado");
            return;
        }

        if(getUsuario(nomeUsuarioQueVaiSerSeguido) == null){
            System.out.println("usuario "+ nomeUsuarioQueVaiSerSeguido + " não foi encontrado");
            return;
        }

        getUsuario(nomeUsuarioQueVaiSeguir).seguir(getUsuario(nomeUsuarioQueVaiSerSeguido));
    }

    //não é necessário mas eu quero salvar em arquivos então pode so ignorar isso
    public Papagaio carregaPapagaios(){
        try {
            FileInputStream arquivoParaCarregar = new FileInputStream(new File(PAPAGAIO_ARQUIVO));
            ObjectInputStream objetoParaCarregar = new ObjectInputStream(arquivoParaCarregar);
            return (Papagaio) objetoParaCarregar.readObject();
        } catch (IOException | ClassNotFoundException e) {
            salvar();
            throw new RuntimeException(e);
        }
    }

    public void salvar(){
        try {
            FileOutputStream arquivoParaSalvar = new FileOutputStream(new File(PAPAGAIO_ARQUIVO));
            ObjectOutputStream objetoParaSalvar = new ObjectOutputStream(arquivoParaSalvar);
            objetoParaSalvar.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
