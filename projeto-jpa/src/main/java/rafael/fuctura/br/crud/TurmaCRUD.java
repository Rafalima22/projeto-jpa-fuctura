package rafael.fuctura.br.crud;

import rafael.fuctura.br.model.Turma;
import jakarta.persistence.*;
import java.util.List;

public class TurmaCRUD {
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public TurmaCRUD() {
        this.emf = Persistence.createEntityManagerFactory("projeto-alunos-pu");
        this.em = emf.createEntityManager();
    }
    
    // CREATE
    public void criarTurma(Turma turma) {
        try {
            em.getTransaction().begin();
            em.persist(turma);
            em.getTransaction().commit();
            System.out.println("TURMA CRIADA: " + turma.getNome());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao criar turma: " + e.getMessage());
        }
    }
    
    // READ - BUSCAR POR ID
    public Turma buscarTurmaPorId(Long id) {
        return em.find(Turma.class, id);
    }
    
    // READ - LISTAR TUDO
    public List<Turma> listarTodasTurmas() {
        return em.createQuery("SELECT t FROM Turma t", Turma.class).getResultList();
    }
    
    // UPDATE
    public void atualizarTurma(Turma turma) {
        try {
            em.getTransaction().begin();
            em.merge(turma);
            em.getTransaction().commit();
            System.out.println("TURMA ATUALIZADA: " + turma.getNome());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar turma: " + e.getMessage());
        }
    }
    
    // DELETE
    public void deletarTurma(Long id) {
        try {
            em.getTransaction().begin();
            Turma turma = em.find(Turma.class, id);
            if (turma != null) {
                em.remove(turma);
                System.out.println("TURMA DELETADA: " + turma.getNome());
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar turma: " + e.getMessage());
        }
    }
    
    public void fechar() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}