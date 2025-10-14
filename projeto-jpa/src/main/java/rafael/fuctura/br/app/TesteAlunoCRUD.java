package rafael.fuctura.br.app;

import rafael.fuctura.br.crud.*;
import rafael.fuctura.br.model.*;
import java.util.List;
import java.util.Comparator;

public class TesteAlunoCRUD {
    public static void main(String[] args) {
        System.out.println("INICIANDO CRUD COMPLETO\n");
        
        // PRIMEIRO LIMPA TUDO
        VerificadorBanco.limparBancoCompleto();
        
        AlunoCRUD alunoCRUD = new AlunoCRUD();
        TurmaCRUD turmaCRUD = new TurmaCRUD();
        MatriculaCRUD matriculaCRUD = new MatriculaCRUD();
        
        try {
            // CREATE ALUNOS 
            System.out.println("CRIANDO ALUNOS:");
            criarAlunosFlexivel(alunoCRUD);
            
            // CREATE TURMAS   
            System.out.println("\nCRIANDO TURMAS:");
            criarTurmasFlexivel(turmaCRUD);
            
            // CREATE MATRÍCULAS 
            System.out.println("\nFAZENDO MATRÍCULAS AUTOMÁTICAS:");
            fazerMatriculasAutomaticas(alunoCRUD, turmaCRUD, matriculaCRUD);
            
            // READ - LISTAR 
            System.out.println("\nALUNOS CADASTRADOS (ORDENADOS POR ID):");
            List<Aluno> alunosOrdenados = alunoCRUD.listarTodosAlunos();
            alunosOrdenados.sort(Comparator.comparing(Aluno::getId)); // 
            alunosOrdenados.forEach(System.out::println);
            
            System.out.println("\nTURMAS CADASTRADAS:");
            turmaCRUD.listarTodasTurmas().forEach(System.out::println);
            
            System.out.println("\nMATRÍCULAS:");
            matriculaCRUD.listarTodasMatriculas().forEach(System.out::println);
            
            // JOIN - ALUNOS COM MATRÍCULAS
            System.out.println("\nALUNOS COM MATRÍCULAS:");
            List<Aluno> alunosComMatriculas = alunoCRUD.listarAlunosComMatriculas();
            
            
            alunosComMatriculas.sort(Comparator.comparing(Aluno::getId));
            for (Aluno a : alunosComMatriculas) {
                System.out.println(a.getNome() + " - " + a.getMatriculas().size() + " matrícula(s)");
            }
            
            // VER RESULTADO FINAL (ORDENADO)
            System.out.println("\nALUNOS FINAIS (ORDENADOS POR ID):");
            List<Aluno> alunosFinais = alunoCRUD.listarTodosAlunos();
            alunosFinais.sort(Comparator.comparing(Aluno::getId)); 
            alunosFinais.forEach(System.out::println);
            
            System.out.println("\nCRUD CONCLUÍDO COM SUCESSO!");
            
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            alunoCRUD.fechar();
            turmaCRUD.fechar();
            matriculaCRUD.fechar();
            System.out.println("Conexões fechadas.");
        }
    }
    
    // MÉTODO FLEXÍVEL PARA CRIAR ALUNOS
    private static void criarAlunosFlexivel(AlunoCRUD alunoCRUD) {
        String[][] alunos = {
            {"João Silva", "joaosilva@email.com"},
            {"Maria Santos", "mariasantos@email.com"},
            {"Pedro Costa", "pedrocosta@email.com"},
            {"Ana Oliveira", "anaoliveira@email.com"},
            {"Rafael Lima", "rafalima@email.com"},
            {"Bernardo Saburido", "bernardosaburido@email.com"},
            {"Lucas Albuquerque", "lucasalbq@email.com"}
           
        };
        
        for (String[] aluno : alunos) {
            try {
                alunoCRUD.criarAluno(new Aluno(aluno[0], aluno[1]));
                System.out.println(aluno[0] + " criado!");
            } catch (Exception e) {
                System.out.println(aluno[0] + " já existe: " + e.getMessage());
            }
        }
    }
    
    // MÉTODO PARA CRIAR TURMAS
    private static void criarTurmasFlexivel(TurmaCRUD turmaCRUD) {
        String[][] turmas = {
            {"Matemática", "Manhã"},
            {"Português", "Tarde"},
            {"Programação", "Tarde"},
            {"História", "Tarde"},
            {"Geografia", "Manhã"},
            {"Literatura", "Manhã"}
            
        };
        
        for (String[] turma : turmas) {
            try {
                turmaCRUD.criarTurma(new Turma(turma[0], turma[1]));
                System.out.println(turma[0] + " criada!");
            } catch (Exception e) {
                System.out.println(turma[0] + " já existe: " + e.getMessage());
            }
        }
    }
    
    // MÉTODO AUTOMÁTICO PARA MATRÍCULAS
    private static void fazerMatriculasAutomaticas(AlunoCRUD alunoCRUD, TurmaCRUD turmaCRUD, MatriculaCRUD matriculaCRUD) {
        List<Aluno> alunos = alunoCRUD.listarTodosAlunos();
        List<Turma> turmas = turmaCRUD.listarTodasTurmas();
        
        System.out.println("Alunos disponíveis: " + alunos.size());
        System.out.println("Turmas disponíveis: " + turmas.size());
        
        //MATRICULAR CADA ALUNO NUMA TURMA DIFERENTE
        for (int i = 0; i < alunos.size() && i < turmas.size(); i++) {
            try {
                matriculaCRUD.matricularAlunoEmTurma(alunos.get(i).getId(), turmas.get(i).getId());
                System.out.println(alunos.get(i).getNome() + " matriculado em " + turmas.get(i).getNome());
            } catch (Exception e) {
                System.out.println("Erro ao matricular " + alunos.get(i).getNome() + ": " + e.getMessage());
            }
        }
    }
}