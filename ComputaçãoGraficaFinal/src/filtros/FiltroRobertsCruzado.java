package filtros;

import panels.PanelFiltros;
import processamentodeimagens.Normalizacao;

import java.awt.image.BufferedImage;

public class FiltroRobertsCruzado {

    PanelFiltros panelFiltros;
    private int[][] imagemMatriz;
    private int imagem[][];
    private int width;
    private int height;

    public FiltroRobertsCruzado(int imagem[][], int width, int height) {
        this.imagem = imagem;
        this.width = width;
        this.height = height;
    }

    public FiltroRobertsCruzado() {
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

        for (int i = 1; i < getWidth() - 1; i++) {
            for (int j = 1; j < getHeight() - 1; j++) {
                int mask5 = imagem[i][j];
                int mask6 = imagem[i][j + 1];
                int mask8 = imagem[i + 1][j];
                int mask9 = imagem[i + 1][j + 1];
                int gradiente = (mask5 - mask9) + (mask6 - mask8);

                matrizImagem[i][j] = Normalizacao.normalizaPixel(gradiente);
            }
        }

        return Normalizacao.matrizToBufferedImage(matrizImagem);
    }
}
