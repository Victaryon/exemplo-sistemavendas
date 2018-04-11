package br.com.sisvendas; 

import antlr.StringUtils;
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
@Table(name = "FORNECEDORES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BDFornecedores.findAll", query = "SELECT b FROM BDFornecedores b")
    , @NamedQuery(name = "BDFornecedores.findByCodigoFornecedor", query = "SELECT b FROM BDFornecedores b WHERE b.codigoFornecedor = :codigoFornecedor")
    , @NamedQuery(name = "BDFornecedores.findByNome", query = "SELECT b FROM BDFornecedores b WHERE b.nome = :nome")
    , @NamedQuery(name = "BDFornecedores.findByEndereco", query = "SELECT b FROM BDFornecedores b WHERE b.endereco = :endereco")
    , @NamedQuery(name = "BDFornecedores.findByBairro", query = "SELECT b FROM BDFornecedores b WHERE b.bairro = :bairro")
    , @NamedQuery(name = "BDFornecedores.findByCidade", query = "SELECT b FROM BDFornecedores b WHERE b.cidade = :cidade")
    , @NamedQuery(name = "BDFornecedores.findByUf", query = "SELECT b FROM BDFornecedores b WHERE b.uf = :uf")
    , @NamedQuery(name = "BDFornecedores.findByCep", query = "SELECT b FROM BDFornecedores b WHERE b.cep = :cep")
    , @NamedQuery(name = "BDFornecedores.findByTelefone", query = "SELECT b FROM BDFornecedores b WHERE b.telefone = :telefone")})
public class BDFornecedores implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name="seq_fornecedores", sequenceName="seq_fornecedores") //Adiciona a sequencia do BD para criar ->
    @GeneratedValue(generator="seq_fornecedores", strategy=GenerationType.AUTO)// o incremento das primary keys
    @Basic(optional = false)
    @Column(name = "CODIGO_FORNECEDOR")
    private BigDecimal codigoFornecedor; //Não recomendavel usar chave primaria como tipo primitivo
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
    @OneToMany(mappedBy = "codigoFornecedor")
    private Collection<BDProdutos> bDProdutosCollection;

    public BDFornecedores() {
    }

    public BDFornecedores(BigDecimal codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
    }
    
    public BDFornecedores(String nome, String endereco, String cep, String uf, String telefone, String cidade,String bairro){
        this.nome=nome;
        this.endereco=endereco;
        this.cep=cep;
        this.uf=uf;
        this.telefone=telefone;
        this.cidade=cidade;
        this.bairro=bairro;
    }

    public BigDecimal getCodigoFornecedor() { 
        return codigoFornecedor;
    }

    public void setCodigoFornecedor(BigDecimal codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
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
    public Collection<BDProdutos> getBDProdutosCollection() {
        return bDProdutosCollection;
    }

    public void setBDProdutosCollection(Collection<BDProdutos> bDProdutosCollection) {
        this.bDProdutosCollection = bDProdutosCollection;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoFornecedor != null ? codigoFornecedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BDClientes)) {
            return false;
        }
        BDFornecedores other = (BDFornecedores) object;
        if ((this.codigoFornecedor == null && other.codigoFornecedor != null) || (this.codigoFornecedor != null && !this.codigoFornecedor.equals(other.codigoFornecedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return nome.format("%-160s", nome); -> caso tiver problema de formatação da area do texto
        return nome;
    }
 
}