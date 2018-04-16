/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sisvendas;

import static br.com.sisvendas.FabricaEntityManager.createEm;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import oracle.jrockit.jfr.events.Bits;

/**
 * FXML Controller class
 *
 * @author julia
 */
public class FXMLVendasRegistrarController implements Initializable {
    EntityManager em= createEm();
    
    @FXML
    private TableView<BDProdutos> tabelaVenda;
    
    @FXML
    private TableColumn<BDProdutos, String> tabelaVendaProduto;
    
    @FXML
    private TableColumn<BDVendaitem, Integer> tabelaVendaQuantidade;
    
    @FXML
    private TableColumn<BDProdutos, Float> tabelaVendaValor;
    
    @FXML
    private TextField txtVendaTotal;
    
    @FXML
    private Button btnVendaFinalizar;
    
    @FXML
    private ComboBox<String> comboBxVendaCliente;
    
    @FXML
    private ComboBox<String> comboBxVendaProduto;
    
    @FXML
    private TextField txtVendaQuant;
    
    @FXML
    private Button btnVendaCancelar;
    
    @FXML
    private TextField txtVendaDesconto;
    
    @FXML
    private Button btnVendaIncluir;
    
    BDVendas venda;
    boolean finalizar= true;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarClienteBox();
        carregarProdutoBox();
        
        btnVendaFinalizar.setDisable(true);
        
    } 

    @FXML
    private void clicarProdutoTabela(MouseEvent event) {
    }
    
    @FXML
    private void btnFinalizarVenda(ActionEvent event) {
        finalizar= true;
        btnVendaFinalizar.setDisable(true);    
    }

    @FXML
    private void btnCancelarVenda(ActionEvent event) {
    }
    
    @FXML
    private void btnIncluirVenda(ActionEvent event) throws ParseException {
        int quantidade= Integer.parseInt(txtVendaQuant.getText());
        String data1= "10/01/2017";
        SimpleDateFormat dt= new SimpleDateFormat("dd/mm/yyyy");
        Date data= dt.parse(data1);
        BDProdutos produto=acharProduto();
        BDClientes cliente= null;
        String clienteNome= comboBxVendaCliente.getSelectionModel().getSelectedItem(); //o do produto está no metodo acharProduto()
          
        for( BDClientes t: carregarClienteBox()){
                if(t.getNome().equals(clienteNome)){
                    cliente=t;
                }
        }        
        if(finalizar==true){
            venda= new BDVendas(data,cliente);
            em.getTransaction().begin();
            em.persist(venda);
            em.getTransaction().commit();
        }
        System.out.println(venda.getCodigoVenda());
        BDVendaitem item= new BDVendaitem(quantidade, produto, venda);
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        txtVendaQuant.clear();
        btnVendaFinalizar.setDisable(false);
        finalizar=false;
        System.out.println("finalizar: "+finalizar);
        carregarTabelaVenda();
    }
    
    public BDProdutos acharProduto(){ //acha o objeto produto pela String do seu nome
        String produtoNome= comboBxVendaProduto.getSelectionModel().getSelectedItem();
        BDProdutos produto= null;
        for( int i=0;i<carregarProdutoBox().size();i++){
                if(carregarProdutoBox().get(i).getNome().endsWith(produtoNome)==true){
                    produto=carregarProdutoBox().get(i);
                }
        }
        return produto;
    }
    
    public List<BDProdutos> carregarProdutoBox(){
        TypedQuery<BDProdutos> query = em.createQuery("SELECT b FROM BDProdutos b",BDProdutos.class);
        List<BDProdutos> produtos= query.getResultList();
        /******************************Gambiarra para não dar ERRO!!!****************************************/
        String listFor= query.getResultList().toString();
        listFor=listFor.replace("[","");
        listFor=listFor.replace("]","");
        List<String> minhaLista= new ArrayList<String>(Arrays.asList(listFor.split(", "))); //transf lista de BDProdutos em String
        /***************************************************************************************************/
        ObservableList<String> oblProduto= FXCollections.observableArrayList(minhaLista);
        comboBxVendaProduto.setItems(oblProduto);  
        return produtos;
    }
    
    public List<BDClientes> carregarClienteBox(){
        TypedQuery<BDClientes> query = em.createQuery("SELECT b FROM BDClientes b",BDClientes.class);
        List<BDClientes> fornecedores= query.getResultList();
        /******************************Gambiarra para não dar ERRO!!!****************************************/
        String listFor= query.getResultList().toString();
        listFor=listFor.replace("[","");
        listFor=listFor.replace("]","");
        List<String> minhaLista= new ArrayList<String>(Arrays.asList(listFor.split(", "))); //transf lista de BDProdutos em String
        /***************************************************************************************************/
        ObservableList<String> oblCliente= FXCollections.observableArrayList(minhaLista);
        comboBxVendaCliente.setItems(oblCliente);  
        return fornecedores;
    }
    
    public void carregarTabelaVenda(){
        tabelaVenda.getColumns().get(0).setVisible(false); //resolve problema de atualizar a tabela na opção -
        tabelaVenda.getColumns().get(0).setVisible(true);//- alterar 
        tabelaVendaProduto.setCellValueFactory(new PropertyValueFactory<BDProdutos,String>("nome"));
        tabelaVendaQuantidade.setCellValueFactory(new PropertyValueFactory<BDVendaitem,Integer>("quantidade"));
        tabelaVendaValor.setCellValueFactory(new PropertyValueFactory<BDProdutos,Float>("valor"));
        
        BDProdutos listProdutos= (BDProdutos) em.find(BDProdutos.class, acharProduto().getCodigoProduto());
        ObservableList<BDProdutos> oblProdutos= FXCollections.observableArrayList(listProdutos);
        tabelaVenda.setItems(oblProdutos);
        BDVendaitem item= null;
        List<BDVendaitem> listVendaItem= (List<BDVendaitem>) listProdutos.getVendaitemCollection();
        for(int i=0;i<listVendaItem.size();i++){
            if(listVendaItem.get(i).getCodigoVenda() == venda){
                item= listVendaItem.get(i);
            }  
        }
        ObservableList<BDVendaitem> oblQuantidade= FXCollections.observableArrayList(item);
        System.out.println(item.getQuantidade());
        tabelaVendaQuantidade.setCellValueFactory((Callback<TableColumn.CellDataFeatures<BDVendaitem, Integer>, ObservableValue<Integer>>) oblQuantidade);
    }

}
