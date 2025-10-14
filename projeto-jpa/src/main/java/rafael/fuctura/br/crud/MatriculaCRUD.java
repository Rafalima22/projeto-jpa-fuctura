package rafael.fuctura.br.crud;

import rafael.fuctura.br.model.Matricula;
import rafael.fuctura.br.model.Aluno;
import rafael.fuctura.br.model.Turma;
import jakarta.persistence.*;
import java.util.List;

public class MatriculaCRUD {
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public MatriculaCRUD() {
        this.emf = Persistence.createEntityManagerFactory("projeto-alunos-pu");
        this.em = emf.createEntityManager();
    }
    
    // CREATE - MATRICULAR ALUNO EM UMA TURMA
    public void matricularAlunoEmTurma(Long alunoId, Long turmaId) {
        try {
            em.getTransaction().begin();
            
            Aluno aluno = em.find(Aluno.class, alunoId);
            Turma turma = em.find(Turma.class, turmaId);
            
            if (aluno == null || turma == null) {
                throw new RuntimeException("Aluno ou turma não encontrados");
            }
            
            Matricula matricula = new Matricula(aluno, turma);
            em.persist(matricula);
            
            em.getTransaction().commit();
            System.out.println("MATRÍCULA REALIZADA: " + aluno.getNome() + " em " + turma.getNome());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao matricular: " + e.getMessage());
        }
    }
    
    // READ - LISTAR TODAS AS MATRICULAS
    public List<Matricula> listarTodasMatriculas() {
        return em.createQuery("SELECT m FROM Matricula m", Matricula.class).getResultList();
    }
    
    // READ - MATRICULA DE UM ALUNO
    public List<Matricula> listarMatriculasDoAluno(Long alunoId) {
        return em.createQuery("SELECT m FROM Matricula m WHERE m.aluno.id = :alunoId", Matricula.class)
                .setParameter("alunoId", alunoId)
                .getResultList();
    }
    
    // DELETE - PARA DESMATRICULAR UM ALUNO
    public void desmatricularAluno(Long matriculaId) {
        try {
            em.getTransaction().begin();
            Matricula matricula = em.find(Matricula.class, matriculaId);
            if (matricula != null) {
                em.remove(matricula);
                System.out.println("MATRÍCULA CANCELADA: " + matricula.getId());
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao desmatricular: " + e.getMessage());
        }
    }
    
    // JOIN - DETALHES DA MATRICULA
    public List<Matricula> listarMatriculasComDetalhes() {
        return em.createQuery(
            "SELECT m FROM Matricula m " +
            "JOIN FETCH m.aluno " +
            "JOIN FETCH m.turma", Matricula.class)
            .getResultList();
    }
    
    public void fechar() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}