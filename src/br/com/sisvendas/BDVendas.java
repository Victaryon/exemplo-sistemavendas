package br.com.sisvendas; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "VENDAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BDVendas.findAll", query = "SELECT b FROM BDVendas b")
    , @NamedQuery(name = "BDVendas.findByCodigoVenda", query = "SELECT b FROM BDVendas b WHERE b.codigoVenda = :codigoVenda")
    , @NamedQuery(name = "BDVendas.findByDataVenda", query = "SELECT b FROM BDVendas b WHERE b.dataVenda = :dataVenda")
    , @NamedQuery(name = "BDVendas.findByValorTotal", query = "SELECT b FROM BDVendas b WHERE b.valorTotal = :valorTotal")})
public class BDVendas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name="seq_vendas", sequenceName="seq_vendas", allocationSize=1) //Adiciona a sequencia do BD para criar ->
    @GeneratedValue(generator="seq_vendas", strategy=GenerationType.AUTO)// o incremento das primary keys
    @Basic(optional = false)
    @Column(name = "CODIGO_VENDA")
    private BigDecimal codigoVenda;
    @Column(name = "DATA_VENDA")
    @Temporal(TemporalType.DATE)
    private Date dataVenda;
    @Column(name = "VALOR_TOTAL")
    private Float valorTotal;
    @JoinColumn(name = "CODIGO_CLIENTE", referencedColumnName = "CODIGO_CLIENTE")
    @ManyToOne
    private BDClientes codigoCliente;

    public BDVendas() {
    }

    public BDVendas(BigDecimal codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public BDVendas(Date dataVenda, BDClientes codigoCliente) {
        this.dataVenda = dataVenda;
        this.codigoCliente = codigoCliente;
    }
    
    

    public BigDecimal getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(BigDecimal codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BDClientes getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(BDClientes codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoVenda != null ? codigoVenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BDVendas)) {
            return false;
        }
        BDVendas other = (BDVendas) object;
        if ((this.codigoVenda == null && other.codigoVenda != null) || (this.codigoVenda != null && !this.codigoVenda.equals(other.codigoVenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigoVenda.toString();
    }
 
}