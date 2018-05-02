package br.com.sisvendas;

import static br.com.sisvendas.FabricaEntityManager.createEm;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * FXML Controller class
 *
 * @author julia
 */
public class FXMLVendasRegistrarController implements Initializable {
    EntityManager em= createEm();
    
    @FXML
    private TableView<BDVendaitem> tabelaVenda;
    
    @FXML
    private TableColumn<BDVendaitem ,BDProdutos> tabelaVendaProduto;
    
    @FXML
    private TableColumn<BDVendaitem, Integer> tabelaVendaQuantidade;
    
    @FXML
    private TableColumn<BDVendaitem ,Float> tabelaVendaValor;
    
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
    private Button btnVendaCancelarItem;
    
    @FXML
    private Button btnVendaCancelar;
    
    @FXML
    private TextField txtVendaDesconto;
    
    @FXML
    private Button btnVendaIncluir;
    
    BDVendas venda;
    boolean finalizar= true;
    float totalVenda=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarClienteBox();
        carregarProdutoBox();    
        btnVendaFinalizar.setDisable(true);  
        btnVendaCancelar.setDisable(true);
        btnVendaCancelarItem.setDisable(true);
    } 

    @FXML
    private BDVendaitem clicarProdutoTabela() {
        BDVendaitem item = tabelaVenda.getSelectionModel().getSelectedItem();
        return item;
    }
    
    @FXML
    private void btnFinalizarVenda(ActionEvent event) {
        venda.setValorTotal(totalVenda); //armazena o valor total da venda
        em.getTransaction().begin();
        em.merge(venda);
        em.getTransaction().commit();
        finalizar= true;
        btnVendaFinalizar.setDisable(true); 
        comboBxVendaCliente.setDisable(false);
        tabelaVenda.getItems().clear(); //limpa os dados da tabela
        totalVenda=0;
        txtVendaTotal.setText(Float.toString(totalVenda));
    }
    
    @FXML
    private void btnCancelarVenda(ActionEvent event) {
        List<BDVendaitem> itemExcluir = tabelaVenda.getSelectionModel().getTableView().getItems();
        for(int i=0;i<itemExcluir.size();i++){
            em.getTransaction().begin();
            em.remove(itemExcluir.get(i));
            em.getTransaction().commit();
        }
        em.getTransaction().begin();
        em.remove(venda);
        em.getTransaction().commit();
        
        totalVenda=0;
        carregarTabelaVenda();
        comboBxVendaCliente.setDisable(false);//permite escolher um cliente na lista
        finalizar=true; // permite a criação de um novo objeto venda
        System.out.println("Venda: "+venda.getCodigoVenda());
    }

    @FXML
    private void btnCancelarVendaItem(ActionEvent event) {
        BDVendaitem itemExcluir = em.find(BDVendaitem.class, clicarProdutoTabela().getContador());
        totalVenda= totalVenda - (itemExcluir.getCodigoProduto().getValor())*itemExcluir.getQuantidade();//subtrai o valor do item na venda total
        txtVendaTotal.setText(Float.toString(totalVenda));
        em.getTransaction().begin();
        em.remove(itemExcluir);
        em.getTransaction().commit();
        carregarTabelaVenda();   
    }
    
    @FXML
    private void btnIncluirVenda() throws ParseException {
        int quantidade= Integer.parseInt(txtVendaQuant.getText());
        String data1= "10/01/2017";
        SimpleDateFormat dt= new SimpleDateFormat("dd/MM/yyyy");
        Date data =dt.parse(data1);
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
        BDVendaitem item= new BDVendaitem(quantidade, produto, venda);
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        txtVendaQuant.clear();
        finalizar=false;
        carregarTabelaVenda();
        totalVenda=totalVenda+(item.getCodigoProduto().getValor())*quantidade;
        txtVendaTotal.setText(Float.toString(totalVenda));
        //ativação de desativação dos botões
        btnVendaCancelar.setDisable(false);
        btnVendaCancelarItem.setDisable(false);
        btnVendaFinalizar.setDisable(false);
        comboBxVendaCliente.setDisable(true);
        System.out.println("Venda: "+venda.getCodigoVenda());
        
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
        tabelaVendaProduto.setCellValueFactory(new PropertyValueFactory<BDVendaitem ,BDProdutos>("codigoProduto"));
        tabelaVendaQuantidade.setCellValueFactory(new PropertyValueFactory<BDVendaitem,Integer>("quantidade"));
        
        //Implementar atributo da classe BDProdutos que é acessada pela classe BDVendaItem -> muito útil
        tabelaVendaValor.setCellValueFactory(new Callback<CellDataFeatures<BDVendaitem, Float>, ObservableValue<Float>>() {
            @Override
            public ObservableValue<Float> call(CellDataFeatures<BDVendaitem, Float> param) { 
                return new ReadOnlyObjectWrapper<>(param.getValue().getCodigoProduto().getValor()); 
            }
        });
        /**************************************************************************************************************/
        TypedQuery<BDVendaitem> query= (TypedQuery<BDVendaitem>) em.createQuery("SELECT b FROM BDVendaitem b WHERE b.codigoVenda.codigoVenda = :codigo");
        query.setParameter("codigo", venda.getCodigoVenda());
        List<BDVendaitem> listProdutos =query.getResultList();
        ObservableList<BDVendaitem> oblProdutos= FXCollections.observableArrayList(listProdutos);
        tabelaVenda.setItems(oblProdutos);
    }
}
