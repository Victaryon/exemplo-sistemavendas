package br.com.sisvendas; 

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "VENDAITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BDVendaitem.findAll", query = "SELECT b FROM BDVendaitem b")
    , @NamedQuery(name = "BDVendaitem.findByQuantidade", query = "SELECT b FROM BDVendaitem b WHERE b.quantidade = :quantidade")
    , @NamedQuery(name = "BDVendaitem.findByContador", query = "SELECT b FROM BDVendaitem b WHERE b.contador = :contador")})
public class BDVendaitem implements Serializable {
    
    @OneToMany(mappedBy = "contador")
    private static final long serialVersionUID = 1L;
    @Column(name = "QUANTIDADE")
    private int quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name="seq_vendaitem", sequenceName="seq_vendaitem") //Adiciona a sequencia do BD para criar ->
    @GeneratedValue(generator="seq_vendaitem", strategy=GenerationType.AUTO)// o incremento das primary keys
    @Basic(optional = false)
    @Column(name = "CONTADOR")
    private BigDecimal contador;
    @JoinColumn(name = "CODIGO_PRODUTO", referencedColumnName = "CODIGO_PRODUTO")
    @ManyToOne
    private BDProdutos codigoProduto;
    @JoinColumn(name = "CODIGO_VENDA", referencedColumnName = "CODIGO_VENDA")
    @ManyToOne
    private BDVendas codigoVenda;

    public BDVendaitem() {
    }

    public BDVendaitem(BigDecimal contador) {
        this.contador = contador;
    }

    public BDVendaitem(int quantidade, BDProdutos codigoProduto, BDVendas codigoVenda) {
        this.quantidade = quantidade;
        this.codigoProduto = codigoProduto;
        this.codigoVenda = codigoVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getContador() {
        return contador;
    }

    public void setContador(BigDecimal contador) {
        this.contador = contador;
    }

    public BDProdutos getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(BDProdutos codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public BDVendas getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(BDVendas codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contador != null ? contador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BDVendaitem)) {
            return false;
        }
        BDVendaitem other = (BDVendaitem) object;
        if ((this.contador == null && other.contador != null) || (this.contador != null && !this.contador.equals(other.contador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(quantidade);
    }
 
}