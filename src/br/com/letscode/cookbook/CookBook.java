package br.com.letscode.cookbook;

import br.com.letscode.cookbook.controller.Catalogo;
import br.com.letscode.cookbook.view.CatalogoView;

public class CookBook {
    public static void main(String[] args) {
        System.out.println("Testando.....");
        Catalogo catalogo = new Catalogo();
        new CatalogoView(catalogo);
        System.out.println("Funcionou!!!!");
    }
}
