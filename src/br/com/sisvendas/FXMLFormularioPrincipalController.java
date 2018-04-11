package br.com.sisvendas;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import static br.com.sisvendas.FabricaEntityManager.*;


public class FXMLFormularioPrincipalController {
    
    @FXML
    private Menu mnuOpcoes;
    @FXML
    private Menu mnuRelatorios;
    @FXML
    private MenuItem mnuRelatorioClientes;
    @FXML
    private MenuItem mnuRelatorioProdutos;
    @FXML
    private MenuItem mnuRelatorioFornecedores;
    @FXML
    private MenuItem mnuRelatorioVendas;
   
    
    @FXML
    void mnuSobreActionPerformed(ActionEvent event) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Informação");
        //alert.setHeaderText("Cabeçalho");
        alert.setContentText("Sistem de Vendas - Juliano \nTelefone:(77)99819-3781");
        alert.show();
    }

    @FXML
    void mnuSairActionPerformed(ActionEvent event) {
        sair();
    }
    
    public void sair(){
        //Tentativa de rollback ao fechar o progrma -> falhou
        /*try{
            createEm().getTransaction().rollback();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            getEmf().close();
            System.exit(0);
        }*/
        getEmf().close();
        System.exit(0);
    }
    
    ClassePrincipal principal;

    @FXML
    private void mnuCadastrarCliente(ActionEvent event) throws IOException {
        principal.showCadastrarClientes(); 
    }


    @FXML
    private void mnuCadastrarProduto(ActionEvent event) throws IOException {
        principal.showCadastrarProduto();
        
    }
    
    @FXML
    private void mnuCadastrarFornecedor(ActionEvent event) throws IOException {
        principal.showCadastrarFornecedor();
    }

    @FXML
    private void mnuVendasRegistar(ActionEvent event) throws IOException {
        principal.showVendasRegistrar();
    }

    @FXML
    private void mnuVendasConsultar(ActionEvent event) throws IOException {
        principal.showVendasConsultar();
    }
    
    
    
}
