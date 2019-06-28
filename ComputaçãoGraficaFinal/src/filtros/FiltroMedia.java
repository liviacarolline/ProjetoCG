package filtros;

import java.awt.image.BufferedImage;

import processamentodeimagens.Normalizacao;

/**
 * Filtro de Média. Suavização de Imagens no Domínio Espacial.
 *
 */
public class FiltroMedia {

    private int[][] imagem;
    private int width;
    private int height;

    public FiltroMedia(int[][] imagem, int width, int height) {
        this.imagem = imagem;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage run() {
        int CoeficienteDeNormalizacao = 0;
        int matrizImagem[][] = new int[width][height];

        // Soma os valores dos pixels da matriz para determinar o coeficiente de normalizaÃ§Ã£o
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                CoeficienteDeNormalizacao = CoeficienteDeNormalizacao + imagem[i][j];
            }
        }
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {

                //soma dos pixeis da vizinhança
                int soma = 0;

                soma += imagem[i][j];
                if ((i - 1) >= 0) {
                    soma += imagem[i - 1][j] / CoeficienteDeNormalizacao;
                }
                if ((i + 1) < getWidth()) {
                    soma += imagem[i + 1][j] / CoeficienteDeNormalizacao;
                }
                if ((j - 1) >= 0) {
                    soma += imagem[i][j - 1] / CoeficienteDeNormalizacao;
                }
                if ((j + 1) < getHeight()) {
                    soma += imagem[i][j + 1] / CoeficienteDeNormalizacao;
                }
                if (((i - 1) >= 0) && ((j - 1) >= 0)) {
                    soma += imagem[i - 1][j - 1] / CoeficienteDeNormalizacao;
                }
                if (((i + 1) < getWidth()) && ((j - 1) >= 0)) {
                    soma += imagem[i + 1][j - 1] / CoeficienteDeNormalizacao;
                }
                if (((i - 1) >= 0) && ((j + 1) < getHeight())) {
                    soma += imagem[i - 1][j + 1] / CoeficienteDeNormalizacao;
                }
                if (((i + 1) < getWidth()) && ((j + 1) < getHeight())) {
                    soma += imagem[i + 1][j + 1] / CoeficienteDeNormalizacao;
                }

                //adiciona a soma dos valores RGB da vizinhança na posiçao central
                matrizImagem[i][j] = Math.round(soma);
            }
        }
        
        return Normalizacao.normalizaImage(matrizImagem);
    }
}
