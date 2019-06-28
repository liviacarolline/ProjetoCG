package processamentodeimagens;

import java.awt.Color;
import java.awt.image.BufferedImage;

import panels.PanelFiltros;

public class EqualizarImagem {

    PanelFiltros panelFiltros;
    private int[][] imagem;
    private int width;
    private int height;

    public EqualizarImagem(int imagem[][], int width, int height) {
        this.imagem = imagem;
        this.width = width;
        this.height = height;
    }

    public int[][] run() {
        float[][] matrizDeEqualizacao = new float[255][6];
        int[][] matrizResult = new int[width][height];

        // inicializa o nível de cinza e a frequência dos níveis de cinza
        for (int i = 0; i < matrizDeEqualizacao.length; i++) {
            matrizDeEqualizacao[i][0] = 1000;
            matrizDeEqualizacao[i][1] = 0;
        }

        // Insere os níveis de cinza, incrementando as suas frequências de repetições e calculando rK (valor do pixel / 255)
        for (int i = 0; i < matrizResult.length; i++) {
            for (int j = 0; j < matrizResult.length; j++) {
                matrizDeEqualizacao[imagem[i][j]][0] = imagem[i][j];
                matrizDeEqualizacao[imagem[i][j]][1] += 1;
                matrizDeEqualizacao[imagem[i][j]][2] = imagem[i][j] / 255;
            }
        }

        matrizDeEqualizacao = ordenarMatriz(matrizDeEqualizacao);

        //calculando Pr(rk)
        int contador = 0;
        while (matrizDeEqualizacao[contador][0] != 1000) {

            //frequencia do pixel dividido pela quantidade de pixels
            matrizDeEqualizacao[contador][3] = matrizDeEqualizacao[contador][1] / (255 * 255);
            contador = contador + 1;
        }

        //calculando Sk - Soma acumulada de Pr(rk)
        contador = 0;
        while (matrizDeEqualizacao[contador][0] != 1000) {

            if (contador == 0) {
                matrizDeEqualizacao[contador][4] = matrizDeEqualizacao[contador][3];
            } else {
                matrizDeEqualizacao[contador][4] = matrizDeEqualizacao[contador][3] + matrizDeEqualizacao[contador - 1][4];
            }
            contador = contador + 1;
        }

        //calculando Round(255 * Sk)
        contador = 0;
        while (matrizDeEqualizacao[contador][0] != 1000) {
            matrizDeEqualizacao[contador][5] = (int) Math.round(255 * matrizDeEqualizacao[contador][4]);
            if (matrizDeEqualizacao[contador][5] > 255) {
                matrizDeEqualizacao[contador][5] = 255;
            }
            contador = contador + 1;
        }
        for (int i = 0; i < imagem.length; i++) {
            for (int j = 0; j < imagem[0].length; j++) {

                int valorDoPixel = imagem[i][j];
                int contador2 = 0;

                while (matrizDeEqualizacao[contador2][0] != valorDoPixel) {
                    contador2 = contador2 + 1;
                }

                matrizResult[i][j] = (int) matrizDeEqualizacao[contador2][5];
            }

        }

        return matrizResult;
    }

    public static float[][] ordenarMatriz(float matrizDeEqualizacao[][]) {
        boolean houveTroca = true;
        
        while (houveTroca == true) {
            houveTroca = false;

            for (int i = 0; i < matrizDeEqualizacao.length - 1; i++) {
                if (matrizDeEqualizacao[i][0] > matrizDeEqualizacao[i + 1][0]) {
                    float variavelAuxiliar0, variavelAuxiliar1, variavelAuxiliar2, variavelAuxiliar3;

                    variavelAuxiliar0 = matrizDeEqualizacao[i][0];
                    variavelAuxiliar1 = matrizDeEqualizacao[i][1];
                    variavelAuxiliar2 = matrizDeEqualizacao[i][2];
                    variavelAuxiliar3 = matrizDeEqualizacao[i][3];

                    matrizDeEqualizacao[i][0] = matrizDeEqualizacao[i + 1][0];
                    matrizDeEqualizacao[i][1] = matrizDeEqualizacao[i + 1][1];
                    matrizDeEqualizacao[i][2] = matrizDeEqualizacao[i + 1][2];
                    matrizDeEqualizacao[i][3] = matrizDeEqualizacao[i + 1][3];

                    matrizDeEqualizacao[i + 1][0] = variavelAuxiliar0;
                    matrizDeEqualizacao[i + 1][1] = variavelAuxiliar1;
                    matrizDeEqualizacao[i + 1][2] = variavelAuxiliar2;
                    matrizDeEqualizacao[i + 1][3] = variavelAuxiliar3;

                    houveTroca = true;
                }
            }

        }

        return matrizDeEqualizacao;
    }
}
