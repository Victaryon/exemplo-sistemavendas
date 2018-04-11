/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sisvendas;

import static br.com.sisvendas.FabricaEntityManager.createEm;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * FXML Controller class
 *
 * @author julia
 */
public class FXMLCadastroFornecedorController implements Initializable {
    EntityManager em= createEm(); //método da classe FabricaEntityManager
    
    @FXML
    private TextField txtFornecedorPesquisar;
    @FXML
    private TableView<BDFornecedores> tabelaFornecedor;
    @FXML
    private TableColumn<BDFornecedores, String> tabelaFornecedorCodigo;
    @FXML
    private TableColumn<BDFornecedores, String> tabelaFornecedorNome;
    @FXML
    private TableColumn<BDFornecedores, String> tabelaFornecedorEndereco;
    @FXML
    private TableColumn<BDFornecedores, String> tabelaFornecedorTelefone;
    @FXML
    private TextField txtFornecedorNome;
    @FXML
    private TextField txtFornecedorEndereco;
    @FXML
    private TextField txtFornecedorCidade;
    @FXML
    private TextField txtFornecedorUF;
    @FXML
    private TextField txtFornecedorCEP;
    @FXML
    private TextField txtFornecedorTelefone;
    @FXML
    private TextField txtFornecedorBairro;
    @FXML
    private Button btnNovoFornecedor;
    @FXML
    private Button btnFecharFornecedor;
    @FXML
    private Button btnSalvarFornecedor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTabelaFornecedores();
        btnSalvarFornecedor.setDisable(true);
    }    

    @FXML
    private void btnFornecedorPesquisar(ActionEvent event) {
        String nome= txtFornecedorPesquisar.getText();
        pesquisarFornecedores(nome);
        if(nome==null){ //se a pesquisa for vazia retorna a lista completa
            carregarTabelaFornecedores();
        }
    }

    @FXML
    public BDFornecedores clicarFornecedorTabela() {
        BDFornecedores fornecedorSelecionado =tabelaFornecedor.getSelectionModel().getSelectedItem(); //seleciona o objeto clicado na tabela
        return fornecedorSelecionado;
    }

    @FXML
    private void btnFornecedorNovo(ActionEvent event) {
        if(txtFornecedorNome.getText().length()!=0 && txtFornecedorCEP.getText().length()!=0 
                && txtFornecedorTelefone.getText().length()!=0 && txtFornecedorEndereco.getText().length()!=0){ //verifica se os campos estão preenchidos   
            
            String nome= txtFornecedorNome.getText();
            String endereco= txtFornecedorEndereco.getText();
            String cep= txtFornecedorCEP.getText();
            String uf= txtFornecedorUF.getText();
            String telefone= txtFornecedorTelefone.getText();
            String cidade= txtFornecedorCidade.getText();
            String bairro= txtFornecedorBairro.getText();

            BDFornecedores fornecedor= new BDFornecedores(nome,endereco,cep,uf,telefone,cidade,bairro);
            em.getTransaction().begin();
            em.persist(fornecedor);
            em.getTransaction().commit();
        }
        else{
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Informação");
            //alert.setHeaderText("Cabeçalho");
            alert.setContentText("Erro!!! Insira todos os campos");
            alert.show();
        }
        limparTexto();
        carregarTabelaFornecedores();
    }

    @FXML
    private void btnFornecedorFechar(ActionEvent event) {
        //Implementar essa parte
    }

    @FXML
    private void btnFornecedorExcluir(ActionEvent event) {
        BDFornecedores fornecedorExcluir= em.find(BDFornecedores.class,clicarFornecedorTabela().getCodigoFornecedor());
        em.getTransaction().begin();
        em.remove(fornecedorExcluir);
        em.getTransaction().commit();
        carregarTabelaFornecedores();
    }

    @FXML
    private void btnFornecedorAlterar(ActionEvent event) {
        //pega o objeto clicado e imprime seus dados nos campos de digitação
        BDFornecedores fornecedorAlterar= clicarFornecedorTabela();
        txtFornecedorNome.setText(fornecedorAlterar.getNome());
        txtFornecedorEndereco.setText(fornecedorAlterar.getEndereco());
        txtFornecedorCEP.setText(fornecedorAlterar.getCep());
        txtFornecedorUF.setText(fornecedorAlterar.getUf());
        txtFornecedorTelefone.setText(fornecedorAlterar.getTelefone());
        txtFornecedorCidade.setText(fornecedorAlterar.getCidade());
        txtFornecedorBairro.setText(fornecedorAlterar.getBairro());
        
        btnSalvarFornecedor.setDisable(false); //Habilita o botão de salvar
    }

    @FXML
    private void btnFornecedorSalvar(ActionEvent event) {
        //Novos dados inseridos para a alteração
        
        String nome= txtFornecedorNome.getText();
        String endereco= txtFornecedorEndereco.getText();
        String cep= txtFornecedorCEP.getText();
        String uf= txtFornecedorUF.getText();
        String telefone= txtFornecedorTelefone.getText();
        String cidade= txtFornecedorCidade.getText();
        String bairro= txtFornecedorBairro.getText(); 
        
        BDFornecedores fornecedor= clicarFornecedorTabela();// obtem o objete a ser alterado
        //Altera os atributos do objeto
        fornecedor.setNome(nome);
        fornecedor.setEndereco(endereco);
        fornecedor.setCep(cep);
        fornecedor.setUf(uf);
        fornecedor.setTelefone(telefone);
        fornecedor.setCidade(cidade);
        fornecedor.setBairro(bairro);
        
        em.getTransaction().begin();       
        em.merge(fornecedor);
        em.getTransaction().commit();
        btnSalvarFornecedor.setDisable(true); //Desabilita o botão de salvar
        limparTexto();
        carregarTabelaFornecedores();
    }
    
    public void carregarTabelaFornecedores(){
        tabelaFornecedor.getColumns().get(0).setVisible(false); //resolve problema de atualizar a tabela na opção ->
        tabelaFornecedor.getColumns().get(0).setVisible(true);//-> alterar fornecedor
        tabelaFornecedorCodigo.setCellValueFactory(new PropertyValueFactory<BDFornecedores,String>("codigoFornecedor"));
        tabelaFornecedorNome.setCellValueFactory(new PropertyValueFactory<BDFornecedores,String>("nome"));
        tabelaFornecedorEndereco.setCellValueFactory(new PropertyValueFactory<BDFornecedores,String>("endereco"));
        tabelaFornecedorTelefone.setCellValueFactory(new PropertyValueFactory<BDFornecedores,String>("telefone"));
        TypedQuery<BDFornecedores> query= em.createQuery("SELECT b FROM BDFornecedores b",BDFornecedores.class);
        List<BDFornecedores> listFornecedores= query.getResultList();
        ObservableList<BDFornecedores> oblFornecedores= FXCollections.observableArrayList(listFornecedores);
        tabelaFornecedor.setItems(oblFornecedores);
    }
    
    public void pesquisarFornecedores(String nome){
        TypedQuery<BDFornecedores> query= (TypedQuery<BDFornecedores>) em.createQuery("SELECT b FROM BDFornecedores b WHERE b.nome like '"+nome+"%'"); //pesquisa auto-completa o nome
        tabelaFornecedorCodigo.setCellValueFactory(new PropertyValueFactory<BDFornecedores,String>("codigoFornecedor"));
        tabelaFornecedorNome.setCellValueFactory(new PropertyValueFactory<BDFornecedores,String>("nome"));
        tabelaFornecedorEndereco.setCellValueFactory(new PropertyValueFactory<BDFornecedores,String>("endereco"));
        tabelaFornecedorTelefone.setCellValueFactory(new PropertyValueFactory<BDFornecedores,String>("telefone"));
        
        List<BDFornecedores> listFornecedores= query.getResultList();
        ObservableList<BDFornecedores> oblFornecedores= FXCollections.observableArrayList(listFornecedores);
        tabelaFornecedor.setItems(oblFornecedores);
        txtFornecedorPesquisar.clear();
    }
    
    public void limparTexto(){
        txtFornecedorNome.clear();
        txtFornecedorEndereco.clear();
        txtFornecedorCEP.clear();
        txtFornecedorUF.clear();
        txtFornecedorTelefone.clear();
        txtFornecedorCidade.clear();
        txtFornecedorBairro.clear();
    }
    
}
