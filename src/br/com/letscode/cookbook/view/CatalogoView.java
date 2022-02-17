package br.com.letscode.cookbook.view;

import br.com.letscode.cookbook.controller.Catalogo;
import br.com.letscode.cookbook.domain.Receita;

import java.util.Locale;

public class CatalogoView {
    private Catalogo controller;
    private Receita ative;
    private int currentIndex;

    public CatalogoView(Catalogo controller) {
        this.controller = controller;
        if (controller.getTotal() > 0) {
            currentIndex = 1;
            ative = controller.getReceita(currentIndex);
        } else {
            currentIndex = 0;
            ative = null;
        }
    }

    private boolean showMenu() {
        String[] options = new String[7];
        StringBuilder sb = new StringBuilder("#".repeat(100));

        sb.append("%n").append("  + : Adicionar  %n");
        options[0] = "+";

        if (ative != null) {
            sb.append("  E : Editar  %n");
            options[1] = "E";
            sb.append("  - : Remover  %n");
            options[2] = "-";
        }

        if (controller.getTotal() > 1) {
            sb.append("  P : Próxima  %n");
            options[3] = "P";
            sb.append("  A : Anterior  %n");
            options[4] = "A";
            sb.append("  L : Localizar  %n");
            options[5] = "L";
        }

        sb.append("#").append(" #".repeat(48)).append("#%n");
        sb.append("  X : Sair  %n");
        options[6] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "+":
                add();
                break;
            case "-":
                del();
                break;
            case "P":
                next();
                break;
            case "A":
                previous();
                break;
            case "L":
                find();
                break;
            case "X":
                System.out.println("Obrigado!!");
                return false;
            default:
                System.out.println("Opção inválida!!!");
        }
        return true;
    }

    private void find() {
        //Capturar o nome da receita.
        String name = ConsoleUtils.getUserInput("Qual o nome da receita que deseja localizar?");
        //Procura no Catalogo a receita com o mesmo nome.
        ative = controller.getReceita(name);
        currentIndex = 0;
    }

    private void next() {
        //Se estiver com uma receita ativa, ativa a próxima receita.
        //Se NÃO estiver com uma receita ativa, ativa a primeira receita.
        if (ative != null) currentIndex++;
        try {
            ative = controller.getReceita(currentIndex);
        } catch (IllegalArgumentException e) {
            ative = null;
        }
        if (ative == null) {
            currentIndex = 1;
            ative = controller.getReceita(currentIndex);
        }
    }

    private void previous() {
        //Se estiver com uma receita ativa, ativa a anterior.
        //Se NÃO estiver com uma receita ativa, ativa a última receita.
        if (ative != null) currentIndex--;
        try {
            ative = controller.getReceita(currentIndex);
        } catch (IllegalArgumentException e) {
            ative = null;
        }
        if (ative == null) {
            currentIndex = controller.getTotal();
            ative = controller.getReceita(currentIndex);
        }
    }

    private void del() {
        //Se NÃO estiver com uma receita ativa, mostra mensagem.
        //Se estiver com uma receita ativa, confirma a operação.
        String opcao = ConsoleUtils.getUserOption("Você deseja realmente APAGAR a receita " + ative.getNome() + "?\nS - Sim   N - Não", "S", "N");
        //Se confirmar, solicita ao Catalogo apagar a receita.
        if (opcao.equalsIgnoreCase("S")) {
            controller.del(ative.getNome());
            ative = null;
            if (controller.getTotal() > 0) {
                currentIndex = 0;
                next();
            }
        }
    }

    private void edit() {
        //Se NÃO estiver com uma receita ativa, mostra mensagem.
        //Se estiver com uma receita ativa, abra a tela de edição.
//        view();
    }

    private void add() {
        //Capturar o nome da receita.
        //Procura no Catalogo a receita com o mesmo nome.
        //Se encontrar, mostra mensagem.
        //Se NÃO encontrar continua.
        //Capturar dados da nova receita.
        //Cria uma nova receita
        //Passa a receita para o Catalogo adicionar.
        //Torna a nova receita a ativa.
    }

    public void view() {
        String tela = "";
        do {
            if (ative == null) {
                //Se NÃO estiver com uma receita ativa, mostra mensagem.
                tela = "Nenhuma receita encontrada!";
            } else {
                //Se estiver com uma receita ativa, continua.
                //Monta o layout da tela com os dados da receita.
                tela = ative.toString();
            }
            //Exibe o layout montado.
            System.out.println(tela);
            //Exibe o menu de opções.
        } while (showMenu());
    }
}
