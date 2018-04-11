/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sisvendas;

import static br.com.sisvendas.FabricaEntityManager.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * FXML Controller class
 *
 * @author julia
 */
public class FXMLCadastroProdutoController implements Initializable {
    
    EntityManager em= createEm();
    
    @FXML
    private TextField txtProdutoNome;

    @FXML
    private TextField txtProdutoValor;

    @FXML
    private TextField txtProdutoPesquisa;

    @FXML
    private TextField txtProdutoEstoque;
    
    @FXML
    private ComboBox<String> comboBxtProdutoFornecedor;
    
    @FXML
    private TableView<BDProdutos> tabelaProduto;
    
    @FXML
    private TableColumn<BDProdutos, String> tabelaProdutoNome;
    
    @FXML
    private TableColumn<BDProdutos, BDProdutos> tabelaProdutoFornecedor;
    
    @FXML
    private TableColumn<BDProdutos, String> tabelaProdutoValor;
    
    @FXML
    private TableColumn<BDProdutos, String> tabelaProdutoEstoque;
    
    @FXML
    private Button btnSalvarProduto;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarComboFornecedores();
        carregarTabelaProduto();  
        btnSalvarProduto.setDisable(true);
    } 
    
    @FXML
    void btnProdutoPesquisar(ActionEvent event) {
        String nome= txtProdutoPesquisa.getText();
        pesquisarNomeProduto(nome);
        if(nome==null){
            carregarTabelaProduto();
        }
    }

    @FXML
    void btnProdutoNovo(ActionEvent event) {
        if(txtProdutoNome.getText().length()!=0 && txtProdutoEstoque.getText().length()!=0 
                && txtProdutoValor.getText().length()!=0 && comboBxtProdutoFornecedor.getSelectionModel().isEmpty()==false){
        
            //não deixar o Entity manager aberto sem transação
            String nome= txtProdutoNome.getText();
            int estoque= Integer.parseInt(txtProdutoEstoque.getText());
            float valor= Float.parseFloat(txtProdutoValor.getText());
            String fornecNome= selecionarItemComboBoxProduto();
            BDFornecedores f = null;
            //Obter o objeto Fornecedor correto da lista, atraves da string do nome
            for( BDFornecedores t: carregarComboFornecedores()){
                if(t.getNome().equals(fornecNome)){
                    f=t;
                }

            }
            /******************************************************************************/
            BDFornecedores fornecedor= em.find(BDFornecedores.class, f.getCodigoFornecedor());//para pesquisar o fornecedor no BD preciso do codigo
            BDProdutos produto= new BDProdutos(nome,valor,estoque,fornecedor);
            em.getTransaction().begin();
            em.persist(produto);
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
        carregarTabelaProduto();
    }

    @FXML
    void btnProdutoFechar(ActionEvent event) {
        
    }

    @FXML
    void btnProdutoAlterar(ActionEvent event) {
        BDProdutos produtoAlterar = clicarProdutoTabela();
        txtProdutoNome.setText(produtoAlterar.getNome());
        txtProdutoValor.setText(Float.toString(produtoAlterar.getValor()));
        txtProdutoEstoque.setText(Integer.toString(produtoAlterar.getEstoque()));
        btnSalvarProduto.setDisable(false);
    }
    
    @FXML
    private void btnProdutoSalvar(ActionEvent event) {
        String nome= txtProdutoNome.getText();
        int estoque= Integer.parseInt(txtProdutoEstoque.getText());
        float valor= Float.parseFloat(txtProdutoValor.getText());
        String fornecNome= selecionarItemComboBoxProduto();
        BDFornecedores f = null;
        //Obter o objeto Fornecedor correto da lista, atraves da string do nome
        for( BDFornecedores t: carregarComboFornecedores()){
            if(t.getNome().equals(fornecNome)){
               f=t;
            }
        }
        
        BDProdutos produto = clicarProdutoTabela();
        produto.setNome(nome);
        produto.setEstoque(estoque);
        produto.setValor(valor);
        produto.setCodigoFornecedor(f);
        
        em.getTransaction().begin();       
        em.merge(produto);
        em.getTransaction().commit();
        
        btnSalvarProduto.setDisable(true);
        limparTexto();
        carregarTabelaProduto();
    }
    
    @FXML
    void btnProdutoExcluir(ActionEvent event) {
        BDProdutos fornecedorExcluir= em.find(BDProdutos.class,clicarProdutoTabela().getCodigoProduto());
        em.getTransaction().begin();
        em.remove(fornecedorExcluir);
        em.getTransaction().commit();
        carregarTabelaProduto();
    }

    @FXML
    public String selecionarItemComboBoxProduto() {
        String fornecedor = comboBxtProdutoFornecedor.getSelectionModel().getSelectedItem();
        //System.out.println("Fornecedor: "+fornecedor);   
        return fornecedor;
    }
    
    @FXML
    private BDProdutos clicarProdutoTabela() {
        BDProdutos produtoSelecionado= tabelaProduto.getSelectionModel().getSelectedItem();
        return produtoSelecionado;
    }
    
    public List<BDFornecedores> carregarComboFornecedores(){
        TypedQuery<BDFornecedores> query = em.createQuery("SELECT b FROM BDFornecedores b",BDFornecedores.class);
        List<BDFornecedores> fornecedores= query.getResultList();
        /******************************Gambiarra para não dar ERRO!!!****************************************/
        String listFor= query.getResultList().toString();
        listFor=listFor.replace("[","");
        listFor=listFor.replace("]","");
        List<String> minhaLista= new ArrayList<String>(Arrays.asList(listFor.split(", "))); //transf lista de BDProdutos em String
        /***************************************************************************************************/
        ObservableList<String> oblFornecedores= FXCollections.observableArrayList(minhaLista);
        comboBxtProdutoFornecedor.setItems(oblFornecedores);  
        return fornecedores;
    } 
    
    public void carregarTabelaProduto(){
        tabelaProduto.getColumns().get(0).setVisible(false); //resolve problema de atualizar a tabela na opção -
        tabelaProduto.getColumns().get(0).setVisible(true);//- alterar 
        tabelaProdutoFornecedor.setCellValueFactory(new PropertyValueFactory<BDProdutos,BDProdutos>("codigoFornecedor"));
        tabelaProdutoNome.setCellValueFactory(new PropertyValueFactory<BDProdutos,String>("nome"));
        tabelaProdutoValor.setCellValueFactory(new PropertyValueFactory<BDProdutos,String>("valor"));
        tabelaProdutoEstoque.setCellValueFactory(new PropertyValueFactory<BDProdutos,String>("estoque"));
        
        TypedQuery<BDProdutos> query= em.createQuery("SELECT b FROM BDProdutos b",BDProdutos.class);
        List<BDProdutos> listProdutos= query.getResultList();
        ObservableList<BDProdutos> oblClientes= FXCollections.observableArrayList(listProdutos);
        tabelaProduto.setItems(oblClientes);
    }
    
    public void pesquisarNomeProduto(String nome){
        TypedQuery<BDProdutos> query= (TypedQuery<BDProdutos>) em.createQuery("SELECT b FROM BDProdutos b WHERE b.nome like '"+nome+"%'"); //pesquisa auto-completa o nome
        tabelaProdutoFornecedor.setCellValueFactory(new PropertyValueFactory<BDProdutos,BDProdutos>("codigoFornecedor"));
        tabelaProdutoNome.setCellValueFactory(new PropertyValueFactory<BDProdutos,String>("nome"));
        tabelaProdutoValor.setCellValueFactory(new PropertyValueFactory<BDProdutos,String>("valor"));
        tabelaProdutoEstoque.setCellValueFactory(new PropertyValueFactory<BDProdutos,String>("estoque"));
        
        List<BDProdutos> listProdutos= query.getResultList();
        ObservableList<BDProdutos> oblFornecedores= FXCollections.observableArrayList(listProdutos);
        tabelaProduto.setItems(oblFornecedores);
        txtProdutoPesquisa.clear();
    }

    public void limparTexto(){
        txtProdutoNome.clear();
        txtProdutoEstoque.clear();
        txtProdutoValor.clear();
    }
}
