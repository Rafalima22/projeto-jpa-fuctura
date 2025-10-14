package rafael.fuctura.br.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turmas")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "materia")  
    private String nome;
    
    private String turno;
    
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<Matricula> matriculas = new ArrayList<>();
    
    // CONSTRUTORES
    public Turma() {}
    
    public Turma(String nome, String turno) {
        this.nome = nome;
        this.turno = turno;
    }
    
    // GETTERS E SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    
    public List<Matricula> getMatriculas() { return matriculas; }
    public void setMatriculas(List<Matricula> matriculas) { this.matriculas = matriculas; }
    
    @Override
    public String toString() {
        return "Turma [id=" + id + ", nome=" + nome + ", turno=" + turno + "]";
    }
}