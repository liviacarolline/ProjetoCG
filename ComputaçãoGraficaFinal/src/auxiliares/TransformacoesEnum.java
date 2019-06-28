package auxiliares;

/**
 * Mapeia os tipos de tranforma��es 2D/3D em objetos
 *
 */
public enum TransformacoesEnum {
    TRANSLACAO("Transla��o"), ESCALA("Escala"), ROTACAO("Rota��o"), REFLEXAO("Reflex�o"), CISALHAMENTO("Cisalhamento"), COMPOSTA("Composta");

    private final String titulo;

    TransformacoesEnum(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }
}
