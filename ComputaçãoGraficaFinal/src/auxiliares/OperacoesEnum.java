package auxiliares;

/**
 *
 */
public enum OperacoesEnum {
    ADICAO("Adição"), SUBSTRACAO("Substração"), MULTIPLICACAO("Multiplicação"), 
    DIVISAO("DivisÃ£o"), OR("OR"), XOR("XOR"), AND("AND");
    
    private final String titulo;
    
    OperacoesEnum(String titulo){
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }
    
}
