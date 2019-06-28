package filtros;

import panels.PanelFiltros;
import processamentodeimagens.Normalizacao;

import java.awt.image.BufferedImage;

public class FiltroRoberts {

    PanelFiltros panelFiltros;
    private int[][] imagemMatriz;
    private int imagem[][];
    private int width;
    private int height;

    public FiltroRoberts(int imagem[][], int width, int height) {
        this.imagem = imagem;
        this.width = width;
        this.height = height;
    }

    public FiltroRoberts() {
    }

    public int[][] getImagemMatriz() {
        return imagemMatriz;
    }

    public void setImagemMatriz(int[][] imagemMatriz) {
        this.imagemMatriz = imagemMatriz;
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
        int matrizImagem[][] = new int[getWidth()][getHeight()];

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                // Aproximações em X e Y
                int aproximacaoX = 0;
                int aproximacaoY = 0;

                if ((j + 1) < getHeight()) {
                    aproximacaoY = imagem[i][j] - imagem[i][j + 1];
                } else {
                    aproximacaoY = imagem[i][j];
                }

                if ((i + 1) < getHeight()) {
                    aproximacaoX = imagem[i][j] - imagem[i + 1][j];
                } else {
                    aproximacaoX = imagem[i][j];
                }

                // Adiciona o novo valor na matriz
                matrizImagem[i][j] = Math.abs(aproximacaoX) + Math.abs(aproximacaoY);
                matrizImagem[i][j] = Normalizacao.normalizaPixel(matrizImagem[i][j]);
            }
        }

        return Normalizacao.matrizToBufferedImage(matrizImagem);
    }
}
