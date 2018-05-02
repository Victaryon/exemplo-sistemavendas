/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sisvendas;

import static br.com.sisvendas.FabricaEntityManager.createEm;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.internal.SessionImpl;

/**
 * FXML Controller class
 *
 * @author julia
 */
public class FXMLVendasConsultarController implements Initializable {
    EntityManager em= createEm();

    @FXML
    private Button btnPesquisar;
    @FXML
    private TableView<BDVendaitem> tabelaVendaConsultar;
    @FXML
    private TableColumn<BDVendaitem, BigDecimal> tabelaConsultarCodigo;
    @FXML
    private TableColumn<BDVendaitem, String> tabelaConsultarCliente;
    @FXML
    private TableColumn<BDVendaitem, String> tabelaConsultarProduto;
    @FXML
    private TableColumn<BDVendaitem, Date> tabelaConsultarData;
    @FXML
    private TableColumn<BDVendaitem, Float> tabelaConsultarValor;
    
    @FXML
    private TextField txtDataIni;
    @FXML
    private TextField txtDataFim;
    @FXML
    private Button btnImprimirRelatorio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        
    }    

    @FXML
    private void btnPesquisarTabela(ActionEvent event) throws ParseException {
        String dt1 =txtDataIni.getText();
        String dt2= txtDataFim.getText();
        DateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
        carregarTabelaConsulta(dt1,dt2); 
    }
    
    @FXML
    private void btnRelatorioImprimir(ActionEvent event) throws JRException {
        BDVendas venda = clicarVendaTabela();
        BigDecimal codVenda = venda.getCodigoVenda();
        Connection connection = em.unwrap(SessionImpl.class).connection(); //necessario usar o método connection 
        Map parametros = new HashMap();
        parametros.put("codigoVendaJava",codVenda); //passa o código da venda do java  para jasperStudio 
        URL url= getClass().getResource("/report/vendaRelatorioCliente.jasper");
        JasperReport jasperReport= (JasperReport) JRLoader.loadObject(url);
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,parametros,connection);
        JasperViewer jasperViwer= new JasperViewer(jasperPrint, false); //impede q o programa feche
        jasperViwer.setVisible(true); //deixa a janela aberta
        
    }
    
    public void carregarTabelaConsulta(String data1, String data2){
        tabelaConsultarCodigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BDVendaitem, BigDecimal>, ObservableValue<BigDecimal>>() {
            public ObservableValue<BigDecimal> call(TableColumn.CellDataFeatures<BDVendaitem, BigDecimal> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getCodigoVenda().getCodigoVenda()); 
            }
        });
        tabelaConsultarCliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BDVendaitem, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BDVendaitem, String> param) { 
                return new ReadOnlyObjectWrapper<>(param.getValue().getCodigoVenda().getCodigoCliente().getNome()); 
            }
        });
        tabelaConsultarProduto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BDVendaitem, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BDVendaitem, String> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getCodigoProduto().getNome()); 
            }
        });
        tabelaConsultarData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BDVendaitem, Date>, ObservableValue<Date>>() {
            public ObservableValue<Date> call(TableColumn.CellDataFeatures<BDVendaitem, Date> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getCodigoVenda().getDataVenda());
            }
        });
        tabelaConsultarValor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BDVendaitem, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<BDVendaitem, Float> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getCodigoVenda().getValorTotal()); 
            }
        });
        
        TypedQuery<BDVendaitem> query= (TypedQuery<BDVendaitem>) em.createQuery("SELECT b FROM BDVendaitem b WHERE TRUNC( b.codigoVenda.dataVenda ) between '"+data1+"' and '"+data2+"'");
        List<BDVendaitem> listaVendas = query.getResultList();
        ObservableList<BDVendaitem> oblVendas= FXCollections.observableArrayList(listaVendas);
        tabelaVendaConsultar.setItems(oblVendas);
    }

    @FXML
    private void tfDataInicio(KeyEvent event) {
        TextFieldFormatter tff= new TextFieldFormatter(); //classe que formata a entrada/formatação de dados do usuario
        tff.setMask("##/##/####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtDataIni);
        tff.formatter();
    }

    @FXML
    private void tfDataFim(KeyEvent event) {
        TextFieldFormatter tff= new TextFieldFormatter(); //classe baixada do youtube, mto importante
        tff.setMask("##/##/####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtDataFim);
        tff.formatter(); 
    }
    
    public BDVendas clicarVendaTabela(){
        BDVendas vendaSelecionada = tabelaVendaConsultar.getSelectionModel().getSelectedItem().getCodigoVenda();
        return vendaSelecionada;
    }
    
}
