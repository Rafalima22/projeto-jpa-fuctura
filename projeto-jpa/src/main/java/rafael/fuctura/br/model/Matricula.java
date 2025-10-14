package rafael.fuctura.br.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_matricula")  //MAPEADO
    private LocalDateTime dataMatricula;
    
    @Column(name = "ativa")   //MAPEADO
    private Boolean ativa;
    
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    
    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;
    
    // CONSTRUTORES
    public Matricula() {
        this.dataMatricula = LocalDateTime.now();
        this.ativa = true;
    }
    
    public Matricula(Aluno aluno, Turma turma) {
        this();
        this.aluno = aluno;
        this.turma = turma;
    }
    
    // GETTERS E SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDateTime dataMatricula) { this.dataMatricula = dataMatricula; }
    
    public Boolean getAtiva() { return ativa; }
    public void setAtiva(Boolean ativa) { this.ativa = ativa; }
    
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    
    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
    
    @Override
    public String toString() {
        return "Matricula [id=" + id + ", aluno=" + aluno.getNome() + ", turma=" + turma.getNome() + "]";
    }
}