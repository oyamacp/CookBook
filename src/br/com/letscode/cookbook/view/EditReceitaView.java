package br.com.letscode.cookbook.view;

import br.com.letscode.cookbook.domain.Ingrediente;
import br.com.letscode.cookbook.domain.Receita;
import br.com.letscode.cookbook.domain.Rendimento;
import br.com.letscode.cookbook.enums.Categoria;
import br.com.letscode.cookbook.enums.TipoMedida;
import br.com.letscode.cookbook.enums.TipoRendimento;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditReceitaView {
    private Receita receita;

    public EditReceitaView(Receita receita) {
        this.receita = new Receita(receita);
    }

    public Receita edit() {
        do {
            new ReceitaView(receita).fullView(System.out);
        } while (showMenuReceita());

        String opcao = ConsoleUtils.getUserOption("Confirma gravação da receita " + receita.getNome() + "? %n S - Sim   N - Não", "S", "N");
        if ("S".equalsIgnoreCase(opcao)) {
            return receita;
        } else {
            return null;
        }
    }

    private boolean showMenuReceita() {
        String[] options = new String[9];
        StringBuilder sb = new StringBuilder("=".repeat(80));
        sb.append("%n").append("Informe o item a alterar");
        sb.append("%n").append("=".repeat(80));
        sb.append("%n").append("1 Nome   2 Categoria   3 Tempo   4 Rendimento   5 Ingrediente   6 Preparo   X Retornar");
        sb.append("%n").append("=".repeat(80)).append("%n");
        options[0] = "1";
        options[1] = "2";
        options[2] = "3";
        options[3] = "4";
        options[4] = "5";
        options[5] = "6";
        options[6] = "X";
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "1":
                editNome();
                break;
            case "2":
                editCategoria();
                break;
            case "3":
                editTempoPreparo();
                break;
            case "4":
                editRendimento();
                break;
            case "5":
                editIngrediente();
                break;
            case "6":
                editPreparo();
                break;
            case "X":
                return false;
            default:
                System.out.println("Opção inválida");
        }
        return true;
    }

    private void editNome() {
        String nomeReceita = ConsoleUtils.getUserInput("Informe o nome da receita");
        if ((nomeReceita != null) && (!nomeReceita.isBlank())) {
            receita.setNome(nomeReceita);
        }
    }

    private void editCategoria() {
        StringBuilder sb = new StringBuilder("Informe a categoria");
        String[] options = new String[Categoria.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, Categoria.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        Categoria categoria = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                categoria = Categoria.values()[i];
                break;
            }
        }
        receita.setCategoria(categoria);
    }

    private void editTempoPreparo() {
        do {
            String time = ConsoleUtils.getUserInput("Informe o tempo de preparo da receita");
            if (!time.isBlank()) {
                try {
                    double value = Double.parseDouble(time);
                    receita.setTempoPreparo(value);
                    return;
                } catch (NullPointerException | NumberFormatException e) {
                    System.out.println("Tempo de preparo invalido");
                }
            } else {
                break;
            }
        } while (true);
    }

    public void editRendimento() {
        int valorMin;
        int valorMax;
        do {
            String min = ConsoleUtils.getUserInput("Informe o rendimento");
            try {
                if (min != null) {
                    valorMin = Integer.parseInt(min);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Rendimento mínimo invalido");
            }
        } while (true);
        do {
            String max = ConsoleUtils.getUserInput("Qual o rendimento maximo da receita?");
            try {
                if (max != null) {
                    valorMax = Integer.parseInt(max);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Rendimento máximo invalido");
            }
        } while (true);

        TipoRendimento tipoRendimento = editTipoRendimento();
        if (valorMin == valorMax) {
            receita.setRendimento(new Rendimento(valorMin, tipoRendimento));
        } else {
            receita.setRendimento(new Rendimento(valorMin, valorMax, tipoRendimento));
        }
    }

    private TipoRendimento editTipoRendimento() {
        StringBuilder sb = new StringBuilder("Qual o tipo de rendimento da receita?\n");
        String[] options = new String[TipoRendimento.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, TipoRendimento.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        TipoRendimento tipoRendimento = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                tipoRendimento = TipoRendimento.values()[i];
                break;
            }
        }
        return tipoRendimento;
    }

    private void editIngrediente() {
        StringBuilder sb = new StringBuilder("Ingredientes: %n");
        int ingredienteSize = receita.getIngredientes().size();
        if (ingredienteSize > 0) {
            for (Ingrediente ingrediente : receita.getIngredientes()) {
                sb.append(String.format("> %s %s de %s%n", ingrediente.getQuantidade(), ingrediente.getTipo().name(), ingrediente.getNome()));
            }
        } else {
            sb.append("   sem ingredientes %n");
        }
        sb.append("%n").append("=".repeat(99));
        sb.append("%n").append("1 Adiciona   2 Remove   X Retornar");
        sb.append("%n").append("=".repeat(99)).append("%n");
        String[] options = new String[3];
        options[0] = "1";
        options[1] = "2";
        options[2] = "X";
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        if (opcao.equalsIgnoreCase("1")) {
            addIngrediente();
        } else {
            if (opcao.equalsIgnoreCase("2") && ingredienteSize > 0) {
                delIngrediente();
            }
        }
    }

    private void addIngrediente() {
        double quantidade;
        String name = ConsoleUtils.getUserInput("Qual o nome do ingrediente?");
        if (name.isBlank()) {
            return;
        }
        do {
            String qtd = ConsoleUtils.getUserInput("Qual a quantidade do ingrediente?");
            try {
                if (qtd != null) {
                    quantidade = Double.parseDouble(qtd);
                    break;
                }
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Quantidade do ingrediente invalida.");
            }
        } while (true);

        TipoMedida tipoMedida = addTipoMedida();
        receita.getIngredientes().add(new Ingrediente(name, quantidade, tipoMedida));
    }

    private TipoMedida addTipoMedida() {
        StringBuilder sb = new StringBuilder("Qual o tipo de medida do ingrediente?\n");
        String[] options = new String[TipoMedida.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, TipoMedida.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        TipoMedida tipoMedida = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                tipoMedida = TipoMedida.values()[i];
                break;
            }
        }
        return tipoMedida;
    }

    private void delIngrediente() {
        StringBuilder sb = new StringBuilder("Qual o ingrediente que você quer remover?\n");
        String[] options = new String[receita.getIngredientes().size()+1];
        int k = 0;
        for (Ingrediente ingrediente : receita.getIngredientes()) {
            options[k] = String.valueOf(k);
            sb.append(String.format("%d - %s %s de %s%n", k, ingrediente.getQuantidade(), ingrediente.getTipo().name(), ingrediente.getNome()));
            k++;
        }
        options[options.length-1] = "X";
        sb.append("X - Sair");
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        if (!"X".equalsIgnoreCase(opcao)) {
            for (int i = 0; i < options.length - 1; i++) {
                if (opcao.equalsIgnoreCase(options[i])) {
                    receita.getIngredientes().remove(i);
                    break;
                }
            }
        }
    }

    private void editPreparo() {
        StringBuilder sb = new StringBuilder("Preparo: %n");
        int preparoSize = receita.getPreparo().size();
        if (preparoSize > 0) {
            List<String> preparo = receita.getPreparo();
            receita.getPreparo().forEach(s -> {
                System.out.println(s);
            });
            /* receita.getPreparo().forEach(System.out::println);
            for (int i = 0 ; i < preparoSize ; i++) {
                sb.append(String.format("> %s - %s%n", preparo.get(i,1), preparo.get(i,2)));
            } */
        } else {
            sb.append("   sem modo de preparo %n");
        }
        sb.append("%n").append("=".repeat(99));
        sb.append("%n").append("1 Adiciona   2 Remove   X Retornar");
        sb.append("%n").append("=".repeat(99)).append("%n");
        String[] options = new String[3];
        options[0] = "1";
        options[1] = "2";
        options[2] = "X";
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        if (opcao.equalsIgnoreCase("1")) {
            addPreparo();
        } else {
            if (opcao.equalsIgnoreCase("2") && preparoSize > 0) {
                delPreparo();
            }
        }
    }

    private void addPreparo() {
        String preparo = ConsoleUtils.getUserInput("Informe o passo a incluir");
        if (preparo.isBlank()) {
            return;
        }
        if (receita.getPreparo().size() == 0) {
            receita.getPreparo().add(preparo);
        } else {
            StringBuilder sb = new StringBuilder("Em que ponto do preparo você quer adicionar esse passo?\n");
            String[] options = new String[receita.getPreparo().size()+1];
            for (int i = 0; i < options.length-1; i++) {
                options[i] = String.valueOf(i);
                sb.append(String.format("%d - %s%n", i, receita.getPreparo().get(i)));
            }
            options[options.length-1] = String.valueOf(options.length-1);
            sb.append(String.format("%d - %s%n", options.length-1, "+++Fim da lista+++"));
            String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
            for (int i = 0; i < options.length; i++) {
                if (opcao.equalsIgnoreCase(options[i])) {
                    receita.getPreparo().add(i, preparo);
                    break;
                }
            }
        }
    }

    private void delPreparo() {
        StringBuilder sb = new StringBuilder("Qual o passo a remover?\n");
        String[] options = new String[receita.getPreparo().size()+1];
        for (int i = 0; i < options.length-1; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, receita.getPreparo().get(i)));
        }
        options[options.length-1] = "X";
        sb.append("X - Sair");
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        if (!"X".equalsIgnoreCase(opcao)) {
            for (int i = 0; i < options.length - 1; i++) {
                if (opcao.equalsIgnoreCase(options[i])) {
                    receita.getPreparo().remove(i);
                    break;
                }
            }
        }
    }

}
