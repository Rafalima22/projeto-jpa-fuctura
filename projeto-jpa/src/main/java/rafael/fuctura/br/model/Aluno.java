package rafael.fuctura.br.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
public class Aluno {
    
    // A ORDEM DAS COLUNAS, CASO SEJA POSSIVEL...
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   
    
    private String nome;                
    
    private String email;               
    
    private LocalDate dataMatricula;  
    
    // RELACIONAMENTO DA MATRICULA
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Matricula> matriculas = new ArrayList<>();
    
    // O HIBERNATE OBRGA A TER O CONST. VAZIO
    public Aluno() {}
    
    // CRIANDO ALUNOS
    public Aluno(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.dataMatricula = LocalDate.now(); // DATA AUTOMATICA 
    }
    
    //GETTERS E SETTERS 
    
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getNome() { 
        return nome; 
    }
    
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public LocalDate getDataMatricula() { 
        return dataMatricula; 
    }
    
    public void setDataMatricula(LocalDate dataMatricula) { 
        this.dataMatricula = dataMatricula; 
    }
    
    public List<Matricula> getMatriculas() { 
        return matriculas; 
    }
    
    public void setMatriculas(List<Matricula> matriculas) { 
        this.matriculas = matriculas; 
    }
    
    // MÉTODO TOSTRING PARA MOSTRAR O ALUNO COM TEXTO
    @Override
    public String toString() {
        return "Aluno [id=" + id + ", nome=" + nome + ", email=" + email + ", dataMatricula=" + dataMatricula + "]";
    }
}