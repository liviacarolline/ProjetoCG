package transformacoes;

import panels.PanelFiltros;
import panels.PanelMenuImagem;
import processamentodeimagens.Normalizacao;
import retas.Ponto;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa uma imagem do tipo .PGM
 *
 */
public class Imagem {

    private int[][] matrizPixel;
    private BufferedImage bufferedImage;
    private int width;
    private int height;
    private Ponto ponto;

    public Imagem() {

    }

    public Imagem(int[][] matrizPixel, BufferedImage bufferedImage, int width, int height) {
        this.matrizPixel = matrizPixel;
        this.bufferedImage = bufferedImage;
        this.width = width;
        this.height = height;
        this.ponto = new Ponto(0, 0, 0);
    }

    /**
     * Add um pixel na matriz de pixel.
     *
     * @param lin Linha
     * @param col Coluna
     * @param pixel Valor do pixel
     * @return True se sucesso, False caso contrário.
     * @throws ArrayIndexOutOfBoundsException
     */
    public boolean addPixel(int lin, int col, int pixel) throws ArrayIndexOutOfBoundsException {
        if (this.width <= 0 || this.height <= 0) {
            return false;
        }

        if (this.matrizPixel == null) {
            this.matrizPixel = new int[this.width][this.height];
        }

        try {
            this.matrizPixel[lin][col] = pixel;
        } catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException(e.getMessage());
        }

        return true;
    }
    
    /**
     * Cria uma nova imagem de acordo com os dados do arquivo .PGM
     * @param file Arquivo com os dados da imagem.
     * @return Objeto Imagem
     */
    public static Imagem criaImagem(File file) {
        Imagem img = new Imagem();
        BufferedImage bufferedImg;

        try {
            FileInputStream fileInput = new FileInputStream(file);
            Scanner scan = new Scanner(fileInput);

            // Descarta a primeira linha
            scan.nextLine();

            img.setWidth(scan.nextInt()); // Largura da imagem
            img.setHeight(scan.nextInt()); // Altura da imagem
            scan.nextInt(); // valor maximo. Infor desconsiderada
            bufferedImg = new BufferedImage(img.getWidth(), img.getHeight(),  BufferedImage.TYPE_INT_RGB);
            
            /**
             * Monta a matriz imagem com os pixels da imagem selecionada
             */
            for (int row = 0; row < img.getWidth(); row++) {
                for (int col = 0; col < img.getHeight(); col++) {
                    // Popula a matriz com os pixels da imagem
                    int valuePixel = scan.nextInt();
                    img.addPixel(col, row, valuePixel);
                    bufferedImg.setRGB(row, col, new Color(valuePixel, valuePixel, valuePixel).getRGB());
                }
            }
            img.setBufferedImage(bufferedImg);

            fileInput.close();
            scan.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PanelFiltros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PanelMenuImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return img;
    }

    public int[][] getMatrizPixel() {
        return matrizPixel;
    }

    public void setMatrizPixel(int[][] matrizPixel) {
        this.matrizPixel = matrizPixel;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
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

    public Ponto getPonto() {
        return ponto;
    }

    public void setPonto(Ponto ponto) {
        this.ponto = ponto;
    }
    
    public void setPonto2D(double x, double y) {
        this.ponto = new Ponto(x, y);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Arrays.deepHashCode(this.matrizPixel);
        hash = 59 * hash + this.width;
        hash = 59 * hash + this.height;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Imagem)) {
            return false;
        }

        final Imagem other = (Imagem) obj;

        if (this.width != other.width || this.height != other.height) {
            return false;
        }

        return Arrays.deepEquals(this.matrizPixel, other.matrizPixel);
    }

    @Override
    public String toString() {
        return "Imagem{" + "matrizPixel=" + Arrays.toString(matrizPixel) + ", width=" + width + ", height=" + height + '}';
    }

}
