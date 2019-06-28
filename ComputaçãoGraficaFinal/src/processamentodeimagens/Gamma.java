package processamentodeimagens;

import java.awt.image.BufferedImage;

import processamentodeimagens.Normalizacao;

public class Gamma {

    private int[][] imagem;
    private int width;
    private int height;
    private float gamma;

    public Gamma(int[][] imagem, int width, int height, float gamma) {
        this.imagem = imagem;
        this.width = width;
        this.height = height;
        this.gamma = gamma;
    }

    public int[][] getImagem() {
        return imagem;
    }

    public void setImagem(int[][] imagem) {
        this.imagem = imagem;
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

    public float getGamma() {
        return gamma;
    }

    public void setGamma(float gamma) {
        this.gamma = gamma;
    }

    public BufferedImage run() {
        int matrizImagem[][] = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrizImagem[i][j] = Normalizacao.normalizaPixel((int) (2 * (Math.pow(imagem[i][j], gamma))));
            }
        }

        return Normalizacao.matrizToBufferedImage(matrizImagem);
    }
}
