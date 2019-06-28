package auxiliares;

/**
 * Mapeia os tipos de filtros
 *
 */
public enum FiltrosEnum {
    MEDIA("Média"), MEDIANA("MEDIANA"), PASSA_ALTA_BASICO("Passa alta básico"), 
    OPERADOR_ROBERTS("Operador de Roberts"), OPERADOR_ROBERTS_CRUZADO("Operador de Roberts Cruzado"),OPERADOR_PEWITT("Operador de Prewitt"), 
    ALTO_REFORCO("Alto Reforço (Hight-Boost)"), OPERADOR_SOBEL("Operador de Sobel");

    private final String titulo;

    FiltrosEnum(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }
}
