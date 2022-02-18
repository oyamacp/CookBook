package br.com.letscode.cookbook.view;

import br.com.letscode.cookbook.domain.Receita;

public class EditReceitaView {
    private Receita receita;

    public EditReceitaView(Receita receita) {
        this.receita = new Receita(receita);
    }

    public Receita edit() {
        boolean confirm = false;
        if (confirm) {
            return receita;
        } else {
            return null;
        }
    }
}
