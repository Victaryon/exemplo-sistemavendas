package br.com.sisvendas;

import static br.com.sisvendas.FabricaEntityManager.*;
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


public class FXMLCadastroClienteController implements Initializable {
    
    EntityManager em= createEm(); //método da classe FabricaEntityManager
   
    @FXML
    private Button btnNovoCliente;
    
    @FXML
    private Button btnSalvarCliente;
    
    @FXML
    private Button btnFecharCliente;
   
    @FXML
    private TextField txtClienteTelefone; 

    @FXML
    private TextField txtClienteEndereco;

    @FXML
    private TextField txtClienteCidade;

    @FXML
    private TextField txtClienteBairro;

    @FXML
    private TextField txtClienteCEP;

    @FXML
    private TextField txtClientePesquisar;

    @FXML
    private TextField txtClienteUF;

    @FXML
    private TextField txtClienteNome;
    
    @FXML
    private TableView<BDClientes> tabelaCliente;
    
    @FXML
    private TableColumn<BDClientes, String> tabelaClienteCodigo;
    
    @FXML
    private TableColumn<BDClientes, String> tabelaClienteNome;
    
    @FXML
    private TableColumn<BDClientes, String> tabelaClienteEndereco;

    @FXML
    private TableColumn<BDClientes, String> tabelaClienteTelefone;
  
    
    @FXML
    void btnClienteFechar(ActionEvent event) {
        //PRECISO IMPLEMENTAR ESSE MÉTODO DE SAIR DO PANE
    }

    @FXML
    void btnClientePesquisar(ActionEvent event) {
        String nome= txtClientePesquisar.getText();
        pesquisarClientes(nome);
        if(nome==null){ //se a pesquisa for vazia retorna a lista completa
            carregarTabelaClientes();
        }
    }

    @FXML
    void btnClienteNovo(ActionEvent event) {
        if(txtClienteNome.getText().length()!=0 && txtClienteCEP.getText().length()!=0 
                && txtClienteTelefone.getText().length()!=0 && txtClienteEndereco.getText().length()!=0){ //verifica se os campos estão preenchidos   
            String nome= txtClienteNome.getText();
            String endereco= txtClienteEndereco.getText();
            String cep= txtClienteCEP.getText();
            String uf= txtClienteUF.getText();
            String telefone= txtClienteTelefone.getText();
            String cidade= txtClienteCidade.getText();
            String bairro= txtClienteBairro.getText();

            BDClientes cliente= new BDClientes(nome,endereco,cep,uf,telefone,cidade,bairro);
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            System.out.println("Codigo Cliente: "+cliente.getCodigoCliente());
            
        }
        else{
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Informação");
            //alert.setHeaderText("Cabeçalho");
            alert.setContentText("Erro!!! Insira todos os campos");
            alert.show();
        }
        
        limparTexto();
        carregarTabelaClientes();
    }

    @FXML
    void btnClienteExcluir(ActionEvent event) {   //EXCLUIR
        BDClientes clienteExcluir= clicarClienteTabela();
        em.getTransaction().begin();
        em.remove(clienteExcluir);
        em.getTransaction().commit();
        carregarTabelaClientes();
        //Atualiza a tabela de clientes
        
    }

    @FXML
    void btnClienteAlterar(ActionEvent event) {
        //pega o objeto clicado e imprime seus dados nos campos de digitação
        BDClientes clienteAlterar= clicarClienteTabela();
        txtClienteNome.setText(clienteAlterar.getNome());
        txtClienteEndereco.setText(clienteAlterar.getEndereco());
        txtClienteCEP.setText(clienteAlterar.getCep());
        txtClienteUF.setText(clienteAlterar.getUf());
        txtClienteTelefone.setText(clienteAlterar.getTelefone());
        txtClienteCidade.setText(clienteAlterar.getCidade());
        txtClienteBairro.setText(clienteAlterar.getBairro());
        
        btnSalvarCliente.setDisable(false); //Habilita o botão de salvar
    }

    @FXML
    public void btnClienteSalvar(ActionEvent event) {
        //Novos dados inseridos para a alteração
        
        String nome= txtClienteNome.getText();
        String endereco= txtClienteEndereco.getText();
        String cep= txtClienteCEP.getText();
        String uf= txtClienteUF.getText();
        String telefone= txtClienteTelefone.getText();
        String cidade= txtClienteCidade.getText();
        String bairro= txtClienteBairro.getText(); 
        
        BDClientes cliente= clicarClienteTabela();// obtem o objete a ser alterado
        //Altera os atributos do objeto
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setCep(cep);
        cliente.setUf(uf);
        cliente.setTelefone(telefone);
        cliente.setCidade(cidade);
        cliente.setBairro(bairro);
        
        em.getTransaction().begin();       
        em.merge(cliente);
        em.getTransaction().commit();
        btnSalvarCliente.setDisable(true); //Desabilita o botão de salvar
        limparTexto();
        carregarTabelaClientes();
        
    }
    
    @FXML
    public BDClientes clicarClienteTabela() {
        BDClientes clienteSelecionado =tabelaCliente.getSelectionModel().getSelectedItem(); //seleciona o objeto clicado na tabela
        return clienteSelecionado;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) { //Funciona como o método construtor
        carregarTabelaClientes();
        btnSalvarCliente.setDisable(true);
    }
    
    public void carregarTabelaClientes(){
        tabelaCliente.getColumns().get(0).setVisible(false); //resolve problema de atualizar a tabela na opção ->
        tabelaCliente.getColumns().get(0).setVisible(true);//-> alterar cliente
        tabelaClienteCodigo.setCellValueFactory(new PropertyValueFactory<BDClientes,String>("codigoCliente"));
        tabelaClienteNome.setCellValueFactory(new PropertyValueFactory<BDClientes,String>("nome"));
        tabelaClienteEndereco.setCellValueFactory(new PropertyValueFactory<BDClientes,String>("endereco"));
        tabelaClienteTelefone.setCellValueFactory(new PropertyValueFactory<BDClientes,String>("telefone"));
        
        TypedQuery<BDClientes> query= em.createQuery("SELECT b FROM BDClientes b",BDClientes.class);
        List<BDClientes> listClientes= query.getResultList();
        ObservableList<BDClientes> oblClientes= FXCollections.observableArrayList(listClientes);
        tabelaCliente.setItems(oblClientes);
    }
    
    public void pesquisarClientes(String nome1){
        String nome= nome1;
        TypedQuery<BDClientes> query= (TypedQuery<BDClientes>) em.createQuery("SELECT b FROM BDClientes b WHERE b.nome like '"+nome+"%'"); //pesquisa auto-completa o nome
        tabelaClienteCodigo.setCellValueFactory(new PropertyValueFactory<BDClientes,String>("codigoCliente"));
        tabelaClienteNome.setCellValueFactory(new PropertyValueFactory<BDClientes,String>("nome"));
        tabelaClienteEndereco.setCellValueFactory(new PropertyValueFactory<BDClientes,String>("endereco"));
        tabelaClienteTelefone.setCellValueFactory(new PropertyValueFactory<BDClientes,String>("telefone"));
        
        List<BDClientes> listClientes= query.getResultList();
        ObservableList<BDClientes> oblClientes= FXCollections.observableArrayList(listClientes);
        tabelaCliente.setItems(oblClientes);
        txtClientePesquisar.clear();
    }
    
    public void limparTexto(){
        txtClienteNome.clear();
        txtClienteEndereco.clear();
        txtClienteCEP.clear();
        txtClienteUF.clear();
        txtClienteTelefone.clear();
        txtClienteCidade.clear();
        txtClienteBairro.clear();
    }
}
