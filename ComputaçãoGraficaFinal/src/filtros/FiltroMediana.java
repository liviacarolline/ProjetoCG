package filtros;

import panels.PanelFiltros;
import processamentodeimagens.Normalizacao;

import java.awt.image.BufferedImage;

public class FiltroMediana {

    PanelFiltros panelFiltros;
    private int[][] imagemMatriz;
    private int imagem[][];
    private int width;
    private int height;

    public FiltroMediana(int imagem[][], int width, int height) {
        this.imagem = imagem;
        this.width = width;
        this.height = height;
    }

    public FiltroMediana() {
    }

    private static void quicksort(int[] vetor, int ini, int fim) {
        int j;
        if (ini < fim) {
            j = separaVetor(vetor, ini, fim);
            quicksort(vetor, ini, j - 1);
            quicksort(vetor, j + 1, fim);
        }// if
    }

    //parte do quick sort
    private static int separaVetor(int[] vetor, int ini, int fim) {
        int aux, c = vetor[ini], i = ini + 1, j = fim;
        while (i <= j) {
            if (vetor[i] <= c) {
                ++i;
            } else if (c < vetor[j]) {
                --j;
            } else {
                aux = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = aux;
                ++i;
                --j;
            }// if
        }// while

        aux = vetor[ini];
        vetor[ini] = vetor[j];
        vetor[j] = aux;
        return j;
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

                // Array para armazenar os valores dos pixels da vizinhaça
                int[] array = new int[9];

                array[0] += imagem[i][j];
                if ((i - 1) >= 0) {
                    array[1] += imagem[i - 1][j];
                }
                if ((i + 1) < getWidth()) {
                    array[2] += imagem[i + 1][j];
                }
                if ((j - 1) >= 0) {
                    array[3] += imagem[i][j - 1];
                }
                if ((j + 1) < getHeight()) {
                    array[4] += imagem[i][j + 1];
                }
                if (((i - 1) >= 0) && ((j - 1) >= 0)) {
                    array[5] += imagem[i - 1][j - 1];
                }
                if (((i + 1) < getHeight() && ((j - 1) >= 0))) {
                    array[6] += imagem[i + 1][j - 1];
                }
                if (((i - 1) >= 0) && ((j + 1) < getHeight())) {
                    array[7] += imagem[i - 1][j + 1];
                }
                if (((i + 1) < getHeight()) && ((j + 1) < getHeight())) {
                    array[8] += imagem[i + 1][j + 1];
                }

                //ordenando o array
                quicksort(array, 0, 8);

                //pegando o pixel da posicao mediana no array
                matrizImagem[i][j] = array[4];
            }
        }

        return Normalizacao.normalizaImage(matrizImagem);
    }
}
