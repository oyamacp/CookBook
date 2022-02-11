package br.com.letscode.cookbook.view;

import br.com.letscode.cookbook.controller.Catalogo;
import br.com.letscode.cookbook.domain.Receita;

import java.util.Objects;

public class CatalogoView {
    private Catalogo controller;
    private Receita ative;
    private int currentIndex;

    public CatalogoView(Catalogo controller) {
        this.controller = controller;
        currentIndex = -1;
        ative = null;
        next();
    }

    public void add() {
        //Capturar o nome da receita.
        //Procura no Catalogo a receita com o mesmo nome.
        //Se encontrar, mostra mensagem.
        //Se NÃO encontrar continua.
        //Capturar dados da nova receita.
        //Cria uma nova receita
        //Passa a receita para o Catalogo adicionar.
        //Torna a nova receita a ativa.
        view();
    }

    public void find() {
        //Capturar o nome da receita.
        //Procura no Catalogo a receita com o mesmo nome.
        view();
    }

    public void view() {
        //Se NÃO estiver com uma receita ativa, mostra mensagem.
        //Se estiver com uma receita ativa, continua.
        //Monta o layout da tela com os dados da receita.
        //Exibe o layout montado.
        //Exibe o menu de opções.
    }

    public void next() {
        //Se estiver com uma receita ativa, ativa a próxima receita.
        //Se NÃO estiver com uma receita ativa, ativa a primeira receita.
        if (ative != null) currentIndex++;
        ative = controller.getReceita(currentIndex);
        if (ative == null) {
            currentIndex = 0;
            ative = controller.getReceita(currentIndex);
        }
        view();
    }

    public void previous() {
        //Se estiver com uma receita ativa, ativa a anterior receita.
        //Se NÃO estiver com uma receita ativa, ativa a última receita.
        if (ative != null) currentIndex--;
        ative = controller.getReceita(currentIndex);
        if (ative == null) {
            currentIndex = controller.getTotal() - 1;
            ative = controller.getReceita(currentIndex);
        }
        view();
    }

    public void del() {
        //Se NÃO estiver com uma receita ativa, mostra mensagem.
        //Se estiver com uma receita ativa, confirma a operação.
        //Se confirmar, solicita ao Catalogo apagar a receita.
        next();
    }

    public void edit() {
        //Se NÃO estiver com uma receita ativa, mostra mensagem.
        //Se estiver com uma receita ativa, abra a tela de edição.
        view();
    }
}
