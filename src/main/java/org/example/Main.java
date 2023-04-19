package org.example;
import express.*;
import express.http.Cookie;
import express.utils.Status;
import org.example.papagaio.Papagaio;
import org.example.papagaio.Usuario;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    static Papagaio papa = new Papagaio();
    public static void main(String[] args) {
        papa = papa.carregaPapagaios();

        Express app = new Express();

        System.out.println(Paths.get("").toAbsolutePath()+"/src/main/html/index.html");


        app.get("/", (req, res) -> {
            res.send(Paths.get(Paths.get("").toAbsolutePath() +"\\src\\main\\html\\index.html"));
        }).listen();
        app.get("/criar", (req, res) -> {
            res.send(Paths.get(Paths.get("").toAbsolutePath() +"\\src\\main\\html\\criar.html"));
        }).listen();
        app.get("/login", (req, res) -> {
            res.send(Paths.get(Paths.get("").toAbsolutePath() +"\\src\\main\\html\\login.html"));
        }).listen();
        app.get("/postar", (req, res) -> {
            res.send(Paths.get(Paths.get("").toAbsolutePath() +"\\src\\main\\html\\postar.html"));
        }).listen();
        app.get("/mural", (req, res) -> {
            res.send(Paths.get(Paths.get("").toAbsolutePath() +"\\src\\main\\html\\mural.html"));
        }).listen();
        app.get("/ver_mural", (req, res) -> {
            res.send(Paths.get(Paths.get("").toAbsolutePath() +"\\src\\main\\html\\ver_mural.html"));
        }).listen();

        app.post("/registrar", (req, res) ->{
            if(papa.criarUsuario(req.getFormQuery("nome")))
                res.send("Usuario criado com sucesso");
            else
                res.send("Já existe um usuario com este nome");
        }).listen();

        app.post("/logar", (req, res) ->{
            Usuario us =  papa.getUsuario(req.getFormQuery("nome"));
            if( us != null) {
                Cookie cook = new Cookie("usuarioNome", us.getNome());
                us.setLogado(true);
                res.setCookie(cook);
                res.send("Logado");
                res.redirect("/");
            }
            else
                res.send("Não existe um usuario com este nome");
        }).listen();

        app.post("/api/postar", (req, res) ->{
            Usuario us = papa.getUsuario(req.getCookie("usuarioNome").getValue());
            if(us == null) {
                res.redirect("/?msg=Usuario não está logado");
            }
            else if(!us.isLogado()){
                res.redirect("/?msg=Usuario não está logado");
            }
            else{
                us.postar(req.getFormQuery("texto"));
                papa.salvar();
                res.redirect("/?msg=post postado com sucesso");
            }


        }).listen();

        app.post("/api/redirect_mural", (req, res) ->{
            String nome = req.getParam("nome");
            System.out.println(req.getFormQuery("nome"));
            res.redirect("/ver_mural?usuario="+req.getFormQuery("nome"));
            res.sendStatus(Status._301);
        }).listen();

        app.get("/get-qnt-posts", (req, res) ->{
            String nome = req.getQuery("nome");
            System.out.println(req.getQuery("nome"));
            Usuario us = papa.getUsuario(nome);
            if(us == null){
                res.send("0");
            }
            else{
                res.send(String.valueOf(us.getPostagens().size()));
            }
        }).listen();

        app.get("/get-post-nome", (req, res) ->{
            String nome = req.getQuery("nome");
            int nmr = Integer.parseInt(req.getQuery("nmr"));
            System.out.println(req.getQuery("nome"));
            Usuario us = papa.getUsuario(nome);
            if(us == null){
                res.send("0");
            }
            else{
                res.send(us.getPostagens().get(nmr).getAutor().getNome());
            }
        }).listen();
        app.get("/get-post-texto", (req, res) ->{
            String nome = req.getQuery("nome");
            int nmr = Integer.parseInt(req.getQuery("nmr"));
            System.out.println(req.getQuery("nome"));
            Usuario us = papa.getUsuario(nome);
            if(us == null){
                res.send("0");
            }
            else{
                res.send(us.getPostagens().get(nmr).getTexto());
            }
        }).listen();

       app.get("/sair", (req, res) ->{
           Usuario us = papa.getUsuario(req.getCookie("usuarioNome").getValue());
           if(us != null)
               us.setLogado(false);
           System.out.println("Usuario deslogado com sucesso");
       }).listen();



    }
}