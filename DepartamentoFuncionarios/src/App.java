import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao = -1;
        List<Departamento> departamentos = new ArrayList<>();

        while (opcao != 0) {
            System.out.println("\n*****MENU*****");
            System.out.println("1 - Cadastrar departamento");
            System.out.println("2 - Listar departamentos");
            System.out.println("3 - Editar departamento");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    CadastrarDepartamento(input, departamentos);
                    break;
                case 2:
                    ListarDepartamentos(departamentos);
                    break;
                case 3:
                    EditarDepartamento(input, departamentos);
                    break;
                case 0:
                    System.out.println("Encerrando aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
        input.close();
    }

    private static void EditarDepartamento(Scanner input, List<Departamento> departamentos) {
        System.out.print("Informe o departamento: ");
        String nome = input.nextLine();

        Departamento dep = departamentos.stream()
            .filter(d -> d.getNome().equals(nome))
            .findFirst()
            .orElse(null);

        if (dep == null) {
            System.out.println("Departamento não localizado!");
        } else {
            int opcao = -1;
            while (opcao != 0) {
                System.out.println("\n*****MENU DO DEPARTAMENTO*****");
                System.out.println("1 - Cadastrar funcionário");
                System.out.println("2 - Remover funcionário");
                System.out.println("3 - Exibir total salarial");
                System.out.println("4 - Promover funcionário");
                System.out.println("5 - Exibir funcionários ordenados");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                opcao = input.nextInt();
                input.nextLine();

                switch (opcao) {
                    case 1:
                        CadastrarFuncionario(input, dep);
                        break;
                    case 2:
                        RemoverFuncionario(input, dep);
                        break;
                    case 3:
                        ExibirTotalSalarial(dep);
                        break;
                    case 4:
                        PromoverFuncionario(input, dep);
                        break;
                    case 5:
                        exibirMenuOrdenacao(input, dep);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        }
    }

    private static void exibirMenuOrdenacao(Scanner input, Departamento dep) {
        System.out.println("\nOrdenar funcionários por:");
        System.out.println("1 - Nome");
        System.out.println("2 - CPF");
        System.out.println("3 - Salário");
        System.out.print("Escolha uma opção: ");
        
        int opcao = input.nextInt();
        input.nextLine();
        
        switch (opcao) {
            case 1:
                dep.OrdenarPorNome();
                System.out.println("\nFuncionários ordenados por nome:");
                dep.ExibirFuncionariosOrdenados();
                break;
            case 2:
                dep.OrdenarPorCPF();
                System.out.println("\nFuncionários ordenados por CPF:");
                dep.ExibirFuncionariosOrdenados();
                break;
            case 3:
                dep.OrdenarPorSalario();
                System.out.println("\nFuncionários ordenados por salário:");
                dep.ExibirFuncionariosOrdenados();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private static void PromoverFuncionario(Scanner input, Departamento dep) {
        System.out.print("Informe o nome do funcionário: ");
        String nome = input.nextLine();
        dep.PromoverFuncionario(nome);
    }

    private static void ExibirTotalSalarial(Departamento dep) {
        double salarioTotal = dep.ObterTotalSalarios();
        System.out.printf("O total salarial do departamento é de: R$%.2f\n", salarioTotal);
    }

    private static void CadastrarFuncionario(Scanner input, Departamento departamento) {
        System.out.print("Informe o nome do funcionário: ");
        String nome = input.nextLine();
        System.out.print("Informe o CPF do funcionário: ");
        String cpf = input.nextLine();
        System.out.print("Informe o salário do funcionário: ");
        double salario = input.nextDouble();
        input.nextLine();

        Funcionario func = new Funcionario(nome, cpf, salario);
        departamento.AdicionarFuncionario(func);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void RemoverFuncionario(Scanner input, Departamento departamento) {
        System.out.print("Informe o nome do funcionário: ");
        String nome = input.nextLine();
        departamento.RemoverFuncionario(nome);
        System.out.println("Funcionário removido com sucesso!");
    }

    private static void ListarDepartamentos(List<Departamento> departamentos) {
        System.out.println("\nDepartamentos cadastrados:");
        if (departamentos.isEmpty()) {
            System.out.println("Nenhum departamento cadastrado.");
        } else {
            departamentos.forEach(System.out::println);
        }
    }

    private static void CadastrarDepartamento(Scanner input, List<Departamento> departamentos) {
        System.out.print("Informe o nome do departamento: ");
        String nome = input.nextLine();
        departamentos.add(new Departamento(nome));
        System.out.println("Departamento cadastrado com sucesso!");
    }
}

class Departamento {
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
        
        funcionarios.forEach(f -> 
            System.out.println(f.getNome() + " - CPF: " + f.getCpf() + " - Salário: R$" + f.getSalario())
        );
    }
}

class Funcionario {
    private String nome;
    private String cpf;
    private double salario;

    public Funcionario(String nome, String cpf, double salario) {
        this.nome = nome;
        this.cpf = cpf;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}