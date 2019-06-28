package processamentodeimagens;

import java.awt.Color;
import java.awt.image.BufferedImage;

import panels.PanelFiltros;

public class HistogramaGrafico {

    PanelFiltros panelFiltros;
    private int[][] imagem;
    private int width;
    private int height;

    public HistogramaGrafico(int imagem[][], int width, int height) {
        this.imagem = imagem;
        this.width = width;
        this.height = height;
    }

    public BufferedImage run() {
        BufferedImage imagemResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[] freq = new int[256];

        for (int i = 0; i < freq.length; i++) {
            freq[i] = 0;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                freq[imagem[i][j]] += 1;
            }

        }

        // Coloca todos os pixels do buffered image na cor branca
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imagemResult.setRGB(i, j, Color.WHITE.getRGB());
            }
        }

        // Procura o valor RGB com maior frequencia entre 0 e 255
        int maior = 0;
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > maior) {
                maior = freq[i];
            }
        }

        // Plota as frequencias dos valores RGB na vertical
        for (int i = 0; i < height - 1; i++) {
            int funcao = (100 * freq[i]) / maior;
            for (int j = 0; j < funcao; j++) {
                imagemResult.setRGB(i, height - 1 - j, Color.DARK_GRAY.getRGB());
            }
        }

        return imagemResult;
    }
}
