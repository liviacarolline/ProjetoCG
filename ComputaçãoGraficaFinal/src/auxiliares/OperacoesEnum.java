package auxiliares;

/**
 *
 */
public enum OperacoesEnum {
    ADICAO("Adi��o"), SUBSTRACAO("Substra��o"), MULTIPLICACAO("Multiplica��o"), 
    DIVISAO("Divisão"), OR("OR"), XOR("XOR"), AND("AND");
    
    private final String titulo;
    
    OperacoesEnum(String titulo){
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }
    
}
