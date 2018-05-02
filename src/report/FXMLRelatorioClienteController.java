/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import static br.com.sisvendas.FabricaEntityManager.createEm;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.persistence.EntityManager;
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
 * @author Julano Amorim 
 */
public class FXMLRelatorioClienteController implements Initializable {
    EntityManager em= createEm();  

    @FXML
    private Button btnRelatorioCliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void relatorioCliente(ActionEvent event) throws JRException {
        em.getTransaction().begin();
        BigDecimal b= new BigDecimal("69");
        Connection connection = em.unwrap(SessionImpl.class).connection(); //necessario usar o método connection 
        Map parametros = new HashMap();
        parametros.put("codigoVendaJava",b); //passa o código da venda para q possa ser impresso
        URL url= getClass().getResource("/report/clienteTeste.jasper");
        JasperReport jasperReport= (JasperReport) JRLoader.loadObject(url);
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
        
        JasperViewer jasperViwer= new JasperViewer(jasperPrint, false);
        jasperViwer.setVisible(true);
        em.getTransaction().commit(); 
    }
    
}
