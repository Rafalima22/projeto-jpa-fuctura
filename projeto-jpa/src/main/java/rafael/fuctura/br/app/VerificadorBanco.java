package rafael.fuctura.br.app;

import jakarta.persistence.*;

public class VerificadorBanco {
    
    public static void limparBancoCompleto() {
        System.out.println("LIMPANDO BANCO DE DADOS...");
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("projeto-alunos-pu");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            
            // LIMPA NA ORDEM CORRETA (FOREIGN KEYS)
            em.createQuery("DELETE FROM Matricula").executeUpdate();
            em.createQuery("DELETE FROM Aluno").executeUpdate();
            em.createQuery("DELETE FROM Turma").executeUpdate();
            
            // ADICIONAR ESTAS LINHAS PARA RESETAR AS SEQUÊNCIAS
            em.createNativeQuery("ALTER SEQUENCE alunos_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE turmas_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE matriculas_id_seq RESTART WITH 1").executeUpdate();
            
            em.getTransaction().commit();
            System.out.println("BANCO COMPLETAMENTE LIMPO E SEQUÊNCIAS RESETADAS!");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}