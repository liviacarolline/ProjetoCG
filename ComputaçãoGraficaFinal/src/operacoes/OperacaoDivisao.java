package operacoes;

import java.awt.image.BufferedImage;

import processamentodeimagens.Normalizacao;

public class OperacaoDivisao {

    private final int[][] imagem1;
    private final int[][] imagem2;
    private final int width;
    private final int height;

    public OperacaoDivisao(int[][] imagem1, int[][] imagem2, int width, int height) {
        this.imagem1 = imagem1;
        this.imagem2 = imagem2;
        this.width = width;
        this.height = height;
    }

    public BufferedImage run() {
        int matrizImagemDivisao[][] = new int[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (imagem2[i][j] != 0) {
                    matrizImagemDivisao[i][j] = imagem1[i][j] / imagem2[i][j];
                } else {
                    matrizImagemDivisao[i][j] = imagem1[i][j];
                }
            }
        }

        return Normalizacao.normalizaImage(matrizImagemDivisao);
    }
}
