package rafael.fuctura.br.crud;

import rafael.fuctura.br.model.Aluno;
import jakarta.persistence.*;
import java.util.List;

public class AlunoCRUD {
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public AlunoCRUD() {
        this.emf = Persistence.createEntityManagerFactory("projeto-alunos-pu");
        this.em = emf.createEntityManager();
    }
    
    // CREATE
    public void criarAluno(Aluno aluno) {
        try {
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();
            System.out.println("ALUNO CRIADO: " + aluno.getNome());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao criar aluno: " + e.getMessage());
        }
    }
    
    // READ - BUSCAR POR ID
    public Aluno buscarAlunoPorId(Long id) {
        Aluno aluno = em.find(Aluno.class, id);
        if (aluno == null) {
            System.out.println("Aluno não encontrado: " + id);
        }
        return aluno;
    }
    
    // READ - LISTAR TODOS
    public List<Aluno> listarTodosAlunos() {
        return em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
    }
    
    // READ - BUSCAR POR NOME
    public List<Aluno> buscarAlunoPorNome(String nome) {
        return em.createQuery("SELECT a FROM Aluno a WHERE a.nome LIKE :nome", Aluno.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }
    
    // UPDATE
    public void atualizarAluno(Aluno aluno) {
        try {
            em.getTransaction().begin();
            em.merge(aluno);
            em.getTransaction().commit();
            System.out.println("ALUNO ATUALIZADO: " + aluno.getNome());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage());
        }
    }
    
    // DELETE
    public void deletarAluno(Long id) {
        try {
            em.getTransaction().begin();
            Aluno aluno = em.find(Aluno.class, id);
            if (aluno != null) {
                em.remove(aluno);
                System.out.println("ALUNO DELETADO: " + aluno.getNome());
            } else {
                System.out.println("Aluno não encontrado para deletar: " + id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar aluno: " + e.getMessage());
        }
    }
    
    // JOIN - ALUNOS E SUAS MATRICULAS
    public List<Aluno> listarAlunosComMatriculas() {
        return em.createQuery(
            "SELECT DISTINCT a FROM Aluno a LEFT JOIN FETCH a.matriculas", Aluno.class)
            .getResultList();
    }
    
    public void fechar() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}