package br.com.sisvendas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author julia
 */
public class ClassePrincipal extends Application {
    
    private Stage primaryStage;
    public static BorderPane mainLayout;
    
    /*
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLFormularioPrincipal.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Sistema de Vendas");
        stage.setScene(scene);
        stage.show();
    }
    */
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage= primaryStage;
        this.primaryStage.setTitle("Sistema de Vendas");
        
        showFormularioPrincipal2();
    }
    
    
    public void showFormularioPrincipal2() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClassePrincipal.class.getResource("FXMLFormularioPrincipal2.fxml"));
        mainLayout= loader.load();
        Scene scene= new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void showCadastrarClientes() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClassePrincipal.class.getResource("FXMLCadastroClientes.fxml"));
        BorderPane mainItens= loader.load();
        mainLayout.setCenter(mainItens);
    }
    
    public static void showCadastrarProduto() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClassePrincipal.class.getResource("FXMLCadastroProduto.fxml"));
        BorderPane mainItens= loader.load();
        mainLayout.setCenter(mainItens);
    }
    
    public static void showCadastrarFornecedor() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClassePrincipal.class.getResource("FXMLCadastroFornecedor.fxml"));
        BorderPane mainItens= loader.load();
        mainLayout.setCenter(mainItens);
    }
    
    public static void showVendasRegistrar() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClassePrincipal.class.getResource("FXMLVendasRegistrar.fxml"));
        BorderPane mainItens= loader.load();
        mainLayout.setCenter(mainItens);
    }
    
    public static void showVendasConsultar() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClassePrincipal.class.getResource("FXMLVendasConsultar.fxml"));
        BorderPane mainItens= loader.load();
        mainLayout.setCenter(mainItens);
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
