package filtros;

import panels.PanelFiltros;
import processamentodeimagens.Normalizacao;

import java.awt.image.BufferedImage;

public class FiltroPrewitt {

    PanelFiltros panelFiltros;
    private int[][] imagemMatriz;
    private int imagem[][];
    private int width;
    private int height;

    public FiltroPrewitt(int imagem[][], int width, int height) {
        this.imagem = imagem;
        this.width = width;
        this.height = height;
    }

    public FiltroPrewitt() {
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
                int mask1 = imagem[i - 1][j - 1];
                int mask2 = imagem[i - 1][j];
                int mask3 = imagem[i - 1][j + 1];
                int mask4 = imagem[i][j - 1];
                int mask6 = imagem[i][j + 1];
                int mask7 = imagem[i + 1][j - 1];
                int mask8 = imagem[i + 1][j];
                int mask9 = imagem[i + 1][j + 1];
                int prewitt = Math.abs((mask7 + mask8 + mask9) - (mask1 + mask2 + mask3))
                        + Math.abs((mask3 + mask6 + mask9) - (mask1 + mask4 + mask7));

                matrizImagem[i][j] = Normalizacao.normalizaPixel(prewitt);
            }
        }
        return Normalizacao.matrizToBufferedImage(matrizImagem);
    }
}
