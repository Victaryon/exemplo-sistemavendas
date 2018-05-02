package br.com.sisvendas;

import static br.com.sisvendas.FabricaEntityManager.getEmf;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.eclipse.persistence.exceptions.ExceptionHandler;

public class ClassePrincipal extends Application {
    
    private Stage primaryStage;
    public static BorderPane mainLayout;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage= primaryStage;
        this.primaryStage.setTitle("Sistema de Vendas");
        
        showFormularioPrincipal2();
    }
   
    public Scene showFormularioPrincipal2() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClassePrincipal.class.getResource("FXMLFormularioPrincipal2.fxml"));
        mainLayout= loader.load();
        Scene scene= new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        return scene;
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
    
    public static void showRelatorioClientes() throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClassePrincipal.class.getResource("/report/FXMLRelatorioCliente.fxml"));
        BorderPane mainItens= loader.load();
        mainLayout.setCenter(mainItens);
    }
    
    public static void main(String[] args) {
        try{
            launch(args);
        }
        finally{
            getEmf().close();//Encerra as conexões antes de fechar o programa
        } 
    }
    
}
