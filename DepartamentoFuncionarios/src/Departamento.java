import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Departamento {
    private String nome;
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Departamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "- " + this.nome;
    }

    public void AdicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public void RemoverFuncionario(String nome) {
        funcionarios.removeIf(f -> f.getNome().equals(nome));
    }

    public double ObterTotalSalarios() {
        return funcionarios.stream()
               .mapToDouble(Funcionario::getSalario)
               .sum();
    }

    public void PromoverFuncionario(String nome) {
        funcionarios.stream()
            .filter(f -> f.getNome().equals(nome))
            .findFirst()
            .ifPresentOrElse(
                f -> f.setSalario(f.getSalario() + 500),
                () -> System.out.println("Funcionário não localizado!")
            );
    }

    public void OrdenarPorNome() {
        Collections.sort(funcionarios, Comparator.comparing(Funcionario::getNome));
    }

    public void OrdenarPorCPF() {
        Collections.sort(funcionarios, Comparator.comparing(Funcionario::getCpf));
    }

    public void OrdenarPorSalario() {
        Collections.sort(funcionarios, Comparator.comparingDouble(Funcionario::getSalario));
    }

    public void ExibirFuncionariosOrdenados() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado neste departamento.");
            return;
        }
        
        for (Funcionario func : funcionarios) {
            System.out.println(func.getNome() + " - CPF: " + func.getCpf() + " - Salário: R$" + func.getSalario());
        }
    }
}
