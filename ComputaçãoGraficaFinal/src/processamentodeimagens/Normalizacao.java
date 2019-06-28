package processamentodeimagens;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Normalizacao {

    private Normalizacao() {
    }

    /**
     * Normaliza uma imagem
     *
     * @param matriz
     * @return
     */
    public static BufferedImage normalizaImage(int[][] matriz) {
        int[][] matrizImg = normalizaMatrizImage(matriz);
        BufferedImage imagem = new BufferedImage(matriz[0].length, matriz.length, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < matrizImg.length; i++) {
            for (int j = 0; j < matrizImg[0].length; j++) {
                // Equação para normalizar pixels
                imagem.setRGB(j, i, getCorPixel(matrizImg[i][j]));
            }
        }

        return imagem;
    }

    public static int normalizaPixel(int pixel) {
        if (pixel > 255) {
            return 255;
        } else if (pixel < 0) {
            return 0;
        }
        return pixel;
    }

    /**
     * Normaliza os pixels contindo na matriz que representa uma imagem
     *
     * @param matriz
     * @return
     */
    public static int[][] normalizaMatrizImage(int[][] matriz) {
        int matrizNomalizada[][] = new int[matriz[0].length][matriz.length];

        int fMax = getMax(matriz);
        int fMin = getMin(matriz);

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                // Equação para normalizar pixels
                matrizNomalizada[i][j] = Math.round(255 * (matriz[i][j] - fMin) / (fMax - fMin));
            }
        }

        return matrizNomalizada;
    }

    public static int getCorPixel(int corRGB) {
        return new Color(corRGB, corRGB, corRGB).getRGB();
    }

    public static BufferedImage matrizToBufferedImage(int[][] matrizImg) {
        BufferedImage imagem = new BufferedImage(matrizImg[0].length, matrizImg.length, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < matrizImg.length; i++) {
            for (int j = 0; j < matrizImg[0].length; j++) {
                // Equação para normalizar pixels
                imagem.setRGB(j, i, getCorPixel(matrizImg[i][j]));
            }
        }

        return imagem;
    }

    /**
     * Retorna o maior inteiro da matriz
     *
     * @param matriz
     * @return O maior valor
     */
    public static int getMax(int[][] matriz) {
        return getValue(matriz, false);
    }

    /**
     * Retorna o menor inteiro da matriz
     *
     * @param matriz
     * @return O menor valor
     */
    public static int getMin(int[][] matriz) {
        return getValue(matriz, true);
    }

    /**
     * Retorna o maior ou menor inteiro da matriz
     *
     * @param matriz
     * @param isMin
     * @return O maior valor
     */
    private static int getValue(int[][] matriz, boolean isMin) {
        int value = matriz[0][0];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (isMin) {
                    if (matriz[i][j] < value) {
                        value = matriz[i][j];
                    }
                } else if (matriz[i][j] > value) {
                    value = matriz[i][j];
                }
            }
        }

        return value;
    }
}
