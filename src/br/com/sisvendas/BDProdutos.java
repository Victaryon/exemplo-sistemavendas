package br.com.sisvendas; 

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PRODUTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BDProdutos.findAll", query = "SELECT b FROM BDProdutos b")
    , @NamedQuery(name = "BDProdutos.findByCodigoProduto", query = "SELECT b FROM BDProdutos b WHERE b.codigoProduto = :codigoProduto")
    , @NamedQuery(name = "BDProdutos.findByNome", query = "SELECT b FROM BDProdutos b WHERE b.nome = :nome")
    , @NamedQuery(name = "BDProdutos.findByValor", query = "SELECT b FROM BDProdutos b WHERE b.valor = :valor")
    , @NamedQuery(name = "BDProdutos.findByEstoque", query = "SELECT b FROM BDProdutos b WHERE b.estoque = :estoque")})
public class BDProdutos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO_PRODUTO")
    private int codigoProduto;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "VALOR")
    private float valor;
    @Column(name = "ESTOQUE")
    private int estoque;
    @JoinColumn(name = "CODIGO_FORNECEDOR", referencedColumnName = "CODIGO_FORNECEDOR")
    @ManyToOne
    private BDFornecedores codigoFornecedor;

    public BDProdutos() {
    }

    public BDProdutos(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    
    public BDProdutos(String nome, float valor, int estoque, BDFornecedores codigoFornecedor) {
        //this.codigoProduto = codigoProduto;
        this.nome= nome;
        this.valor= valor;
        this.estoque= estoque;
        this.codigoFornecedor=codigoFornecedor;
    }


    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public BDFornecedores getCodigoFornecedor() {
        return codigoFornecedor;
    }

    public void setCodigoFornecedor(BDFornecedores codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BDProdutos)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sisvendas.BDProdutos[ codigoProduto=" + codigoProduto + " ]";
    }
 
}