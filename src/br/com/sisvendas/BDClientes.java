package br.com.sisvendas; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CLIENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BDClientes.findAll", query = "SELECT b FROM BDClientes b")
    , @NamedQuery(name = "BDClientes.findByCodigoCliente", query = "SELECT b FROM BDClientes b WHERE b.codigoCliente = :codigoCliente")
    , @NamedQuery(name = "BDClientes.findByNome", query = "SELECT b FROM BDClientes b WHERE b.nome = :nome")
    , @NamedQuery(name = "BDClientes.findByEndereco", query = "SELECT b FROM BDClientes b WHERE b.endereco = :endereco")
    , @NamedQuery(name = "BDClientes.findByBairro", query = "SELECT b FROM BDClientes b WHERE b.bairro = :bairro")
    , @NamedQuery(name = "BDClientes.findByCidade", query = "SELECT b FROM BDClientes b WHERE b.cidade = :cidade")
    , @NamedQuery(name = "BDClientes.findByUf", query = "SELECT b FROM BDClientes b WHERE b.uf = :uf")
    , @NamedQuery(name = "BDClientes.findByCep", query = "SELECT b FROM BDClientes b WHERE b.cep = :cep")
    , @NamedQuery(name = "BDClientes.findByTelefone", query = "SELECT b FROM BDClientes b WHERE b.telefone = :telefone")})
public class BDClientes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name="seq_clientes", sequenceName="seq_clientes") //Adiciona a sequencia do BD para criar ->
    @GeneratedValue(generator="seq_clientes", strategy=GenerationType.AUTO)// o incremento das primary keys
    @Basic(optional = false)
    @Column(name = "CODIGO_CLIENTE")
    private BigDecimal codigoCliente; //Não recomendavel usar chave primaria como tipo primitivo
    @Column(name = "NOME")
    private String nome;
    @Column(name = "ENDERECO")
    private String endereco;
    @Column(name = "BAIRRO")
    private String bairro;
    @Column(name = "CIDADE")
    private String cidade;
    @Column(name = "UF")
    private String uf;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "TELEFONE")
    private String telefone;
    @OneToMany(mappedBy = "codigoCliente")
    private Collection<BDClientes> bDClientesCollection;

    public BDClientes() {
    }

    public BDClientes(BigDecimal codigoCliente) {  
        this.codigoCliente = codigoCliente;
    }
    
    public BDClientes(String nome, String endereco, String cep, String uf, String telefone, String cidade,String bairro){
        this.nome=nome;
        this.endereco=endereco;
        this.cep=cep;
        this.uf=uf;
        this.telefone=telefone;
        this.cidade=cidade;
        this.bairro=bairro;
    }

    public BigDecimal getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(BigDecimal codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @XmlTransient
    public Collection<BDClientes> getBDClientesCollection() {
        return bDClientesCollection;
    }

    public void setBDClientesCollection(Collection<BDClientes> bDClientesCollection) {
        this.bDClientesCollection = bDClientesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCliente != null ? codigoCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BDClientes)) {
            return false;
        }
        BDClientes other = (BDClientes) object;
        if ((this.codigoCliente == null && other.codigoCliente != null) || (this.codigoCliente != null && !this.codigoCliente.equals(other.codigoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.sisvendas.BDClientes[ codigoCliente=" + codigoCliente + " ]";
    }
 
}