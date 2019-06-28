package processamentodeimagens;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import panels.PanelFiltros;
import panels.PanelGatoArnold;

public class GatoArnold {

    PanelFiltros panelFiltros;
    private int[][] imagem;
    private int[][] imagemArnold;
    private int[][] imagemCopia;
    private int width;
    private int height;
    private JLabel label;
    private ImageIcon icon;

    public GatoArnold(int imagem[][], int width, int height) {
        this.imagem = imagem;
        this.imagemArnold = imagem;
        this.width = width;
        this.height = height;

        copiaMatriz();
    }

    public GatoArnold() {
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

    public void exibeImage() {
        icon = new ImageIcon(geraBuffer());
        label = new JLabel(icon);

        PanelGatoArnold.panelImgOutput.removeAll();
        PanelGatoArnold.panelImgOutput.add(label);
        PanelGatoArnold.panelImgOutput.validate();
        PanelGatoArnold.panelImgOutput.repaint();

        label.setBounds(0, 0, getWidth(), getHeight());
    }

    public void run() {
        exibeImage();
        processaImagem();
        exibeImage();
    }

    public BufferedImage geraBuffer() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);
        WritableRaster raster = image.getRaster();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                raster.setSample(i, j, 0, imagem[j][i] * 255);
            }
        }
        return image;
    }

    public void processaImagem() {
        int x = 0;
        int y = 0;

        for (int i = 0; i < imagemArnold.length; i++) {
            for (int j = 0; j < imagemArnold[0].length; j++) {
                x = ((i + j) % imagem.length);
                y = ((j + 2 * i) % imagem[0].length);
                imagemArnold[x][y] = imagemCopia[i][j];
            }
        }

        for (int i = 0; i < imagemCopia.length; i++) {
            for (int j = 0; j < imagemCopia[0].length; j++) {
                imagemCopia[i][j] = imagemArnold[i][j];
            }
        }
    }

    private void copiaMatriz() {
        imagemCopia = new int[width][height];

        for (int i = 0; i < imagem.length; i++) {
            for (int j = 0; j < imagem[0].length; j++) {
                imagemCopia[i][j] = imagem[i][j];
            }
        }
    }

    public boolean isParada() {
        for (int i = 0; i < imagem.length; i++) {
            for (int j = 0; j < imagem[i].length; j++) {
                if (imagem[i][j] != imagemArnold[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
