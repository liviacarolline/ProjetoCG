package sistemacoordenadas;

public class FuncoesDeNormalizacao {

    private static float ndcx;
    private static float ndcy;
    private static float dcx;
    private static float dcy;

    public static float calcularNDCX(float ndh, float dcx) {
        ndcx = dcx / (ndh - 1);
        return ndcx;
    }

    public static float calcularNDCY(float ndv, float dcy) {
        ndcy = (dcy / (ndv - 1));
        return ndcy;
    }

    public static int calcularDCX(float ndh, float ndcx) {
        dcx = ((ndh - 1) * ndcx);
        return Math.round(dcx);
    }

    public static int calcularDCY(float ndv, float ndcy) {
        dcy = ((ndv - 1) * ndcy);
        return Math.round(dcy);
    }

}
