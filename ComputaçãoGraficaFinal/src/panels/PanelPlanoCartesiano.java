package panels;

import retas.Ponto;
import retas.Rasterizacao;
import transformacoes.Imagem;
import transformacoes.Matriz;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Representa um plano cartesiano.
 */
public class PanelPlanoCartesiano extends JPanel {

    private static PanelPlanoCartesiano instance;

    private PanelPlanoCartesiano() {
        /**
         * Evento click do mouse. Denha um ponto no plano cartesiano.
         */
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                Graphics g = event.getComponent().getGraphics();
                g.setColor(Color.RED);
                g.fillOval(event.getX() - 3, event.getY() - 3, 6, 6);
            }
        });

        /**
         * Evento arrastar do mouse. Desenha a mÃ£o livre no plano cartesiano.
         */
        super.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Graphics g = e.getComponent().getGraphics();
                g.setColor(Color.RED);
                g.fillOval(e.getX() - 2, e.getY() - 2, 4, 4);
            }
        });
    }

    /**
     * Fornece instância do Plano Cartesiano
     *
     * @return
     */
    public static synchronized PanelPlanoCartesiano getInstance() {
        if (instance == null) {
            instance = new PanelPlanoCartesiano();
        }

        return instance;
    }

    /**
     * Retorna a largura/comprimento do plano cartesiano.
     *
     * @return
     */
    public int getLargura() {
        return super.getWidth();
    }

    /**
     * Retorna a altura do plano cartesiano.
     *
     * @return
     */
    public int getAltura() {
        return super.getHeight();
    }

    /**
     * width/2
     *
     * @return
     */
    public int getValorCentroX() {
        return getLargura() / 2;
    }

    /**
     * height/2
     *
     * @return
     */
    public int getValorCentroY() {
        return getAltura() / 2;
    }

    /**
     * Desenha o plano
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.white);
        g.setColor(Color.LIGHT_GRAY);

        // Reta x
        g.drawLine(0, getValorCentroY(), getLargura(), getValorCentroY()); // x1, y1, x2, y2 

        // Reta y
        g.drawLine(getValorCentroX(), 0, getValorCentroX(), getAltura()); // x1, y1, x2, y2 
    }

    /**
     * Redesenha o plano cartesiano. útil quando se quer apagar os objetos
     * desenhado nele e desenhar novos.
     */
    public void redesenha() {
        if (instance != null) {
            instance.getGraphics().clearRect(0, 0, this.getLargura(), this.getAltura());
            this.paint(this.getGraphics()); // Desenha o plano cartesiano novamente
        }
    }

    /**
     * Desenha o eixo Z no plano cartesiano.
     */
    public void desenhaEixoZ() {
        // Desenha a reta do eixo Z
        Rasterizacao.getInstance().pontoMedio(new Ponto(0, 0), new Ponto(-getAltura(), -getAltura()), Color.LIGHT_GRAY, null);
        Rasterizacao.getInstance().pontoMedio(new Ponto(0, 0), new Ponto(getAltura(), getAltura()), Color.LIGHT_GRAY, null);
    }

    public void redesenha3D() {
        redesenha();
        desenhaEixoZ();
    }

    /**
     * Desenha o pixel no plano cartesiano de acordo com os parametros. OBS. Os
     * pontos não são normalizados.
     *
     * @param x - Coordenada x
     * @param y - Coordenada y
     * @param color - Cor do pixel
     */
    public void drawPixel(int x, int y, Color color) {
        // Pega instância do graphics para desenhar no plano cartesiano
        Graphics g = super.getGraphics();
        g.setColor(color);
        g.fillRect(x, y, 1, 1);

    }

    /**
     * Desenha o pixel no plano cartesiano de acordo com os parametros. OBS. Os
     * pontos são normalizados!
     *
     * @param x - Coordenada x
     * @param y - Coordenada y
     * @param color - Cor do pixel
     */
    public void drawPixel(double x, double y, Color color) {
        // Normalizando os pontos
        x = (x + PanelPlanoCartesiano.getInstance().getValorCentroX());
        y = (PanelPlanoCartesiano.getInstance().getValorCentroY() - y);

        drawPixel(Math.round((float) x), Math.round((float) y), color);
    }

    /**
     * Desenha objeto 2D no plano cartesiano.
     *
     * @param color
     * @param matrizObjeto
     */
    public void drawObjeto2D(double[][] matrizObjeto, Color color) {
        redesenha();

        // Pega instância do graphics para desenhar no plano cartesiano
        Graphics g = this.getGraphics();
        g.setColor(color);

        g.drawLine(this.getValorCentroX() + (int) matrizObjeto[0][0], this.getValorCentroY() - (int) matrizObjeto[1][0], this.getValorCentroX() + (int) matrizObjeto[0][1], this.getValorCentroY() - (int) matrizObjeto[1][1]);
        g.drawLine(this.getValorCentroX() + (int) matrizObjeto[0][1], this.getValorCentroY() - (int) matrizObjeto[1][1], this.getValorCentroX() + (int) matrizObjeto[0][2], this.getValorCentroY() - (int) matrizObjeto[1][2]);
        g.drawLine(this.getValorCentroX() + (int) matrizObjeto[0][2], this.getValorCentroY() - (int) matrizObjeto[1][2], this.getValorCentroX() + (int) matrizObjeto[0][3], this.getValorCentroY() - (int) matrizObjeto[1][3]);
        g.drawLine(this.getValorCentroX() + (int) matrizObjeto[0][3], this.getValorCentroY() - (int) matrizObjeto[1][3], this.getValorCentroX() + (int) matrizObjeto[0][0], this.getValorCentroY() - (int) matrizObjeto[1][0]);
    }

    /**
     * Desenha objeto 3D no plano cartesiano.
     *
     * @param color
     * @param matrizObjeto3D
     */
    public void drawObjeto3D(double[][] matrizObjeto3D, Color color) {
        redesenha3D();
        Rasterizacao rast = Rasterizacao.getInstance();

        double fatorCentroCubo = 25;//matrizObjeto3D[0][4]/2; // (profundidade / 2)/2
        Ponto A = new Ponto(matrizObjeto3D[0][0] - fatorCentroCubo, matrizObjeto3D[1][0] - fatorCentroCubo, matrizObjeto3D[2][0] - fatorCentroCubo);
        Ponto B = new Ponto(matrizObjeto3D[0][1] - fatorCentroCubo, matrizObjeto3D[1][1] - fatorCentroCubo, matrizObjeto3D[2][1] - fatorCentroCubo);
        Ponto C = new Ponto(matrizObjeto3D[0][2] - fatorCentroCubo, matrizObjeto3D[1][2] - fatorCentroCubo, matrizObjeto3D[2][2] - fatorCentroCubo);
        Ponto D = new Ponto(matrizObjeto3D[0][3] - fatorCentroCubo, matrizObjeto3D[1][3] - fatorCentroCubo, matrizObjeto3D[2][3] - fatorCentroCubo);
        Ponto E = new Ponto(matrizObjeto3D[0][4] - fatorCentroCubo, matrizObjeto3D[1][4] - fatorCentroCubo, matrizObjeto3D[2][4] - fatorCentroCubo);
        Ponto F = new Ponto(matrizObjeto3D[0][5] - fatorCentroCubo, matrizObjeto3D[1][5] - fatorCentroCubo, matrizObjeto3D[2][5] - fatorCentroCubo);
        Ponto G = new Ponto(matrizObjeto3D[0][6] - fatorCentroCubo, matrizObjeto3D[1][6] - fatorCentroCubo, matrizObjeto3D[2][6] - fatorCentroCubo);
        Ponto H = new Ponto(matrizObjeto3D[0][7] - fatorCentroCubo, matrizObjeto3D[1][7] - fatorCentroCubo, matrizObjeto3D[2][7] - fatorCentroCubo);

        /**
         * Desenha usando o algoritmo de rasterização do Ponto Médio
         */
        rast.pontoMedio(A, B, color, null);
        rast.pontoMedio(B, C, color, null);
        rast.pontoMedio(C, D, color, null);
        rast.pontoMedio(D, A, color, null);
        rast.pontoMedio(E, F, color, null);
        rast.pontoMedio(F, G, color, null);
        rast.pontoMedio(G, H, color, null);
        rast.pontoMedio(H, E, color, null);
        rast.pontoMedio(A, E, color, null);
        rast.pontoMedio(B, F, color, null);
        rast.pontoMedio(C, G, color, null);
        rast.pontoMedio(D, H, color, null);

        /**
         * Desenhar com o drawLine
         */
    }

    /**
     * Desenha imagem no plano cartesiano.
     *
     * @param img Imagem
     */
    public void drawImage(Imagem img) {
        BufferedImage bufferedImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < img.getWidth(); row++) {
            for (int col = 0; col < img.getHeight(); col++) {
                // Prepara a imagem para ser desenhada no jpanel
                bufferedImg.setRGB(row, col, getCorPixel(img.getMatrizPixel()[row][col]));
            }
        }

        this.drawImage(bufferedImg);
    }

    public void drawImageROBSON(int[][] matrizImage, double[][] matrizPosicao) {
        redesenha();
        
        BufferedImage bufferedImg = new BufferedImage(matrizImage.length, matrizImage.length, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < bufferedImg.getWidth(); row++) {
            for (int col = 0; col < bufferedImg.getHeight(); col++) {
                // Prepara a imagem para ser desenhada no jpanel
                bufferedImg.setRGB(col, row, getCorPixel((int) matrizImage[row][col]));
            }
        }

        this.drawImage(bufferedImg);
    }

    /**
     * Desenha imagem no plano cartesiano.
     *
     * @param bufferedImg BufferedImage
     */
    public void drawImage(BufferedImage bufferedImg) {
        redesenha();
        this.getGraphics().drawImage(bufferedImg, getValorCentroX(), getValorCentroY() - bufferedImg.getHeight(), null);
    }

    /**
     * Recebe a imagem a ser processada e o AffineTransform contendo as
     * transformações na imagem e desenha no plano cartesiano.
     *
     * @param img
     * @param affineTransform
     */
    public void drawImage(Imagem img, AffineTransform affineTransform) {
        BufferedImage bufferedImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Ajsuta a imagem
        for (int row = 0; row < img.getBufferedImage().getWidth(); row++) {
            for (int col = 0; col < img.getBufferedImage().getHeight(); col++) {
                // Prepara a imagem para ser desenhada no jpanel
                if (row < 256 && col < 256) {
                    bufferedImg.setRGB(row, col, getCorPixel(img.getMatrizPixel()[row][col]));
                }
            }
        }
        img.setBufferedImage(bufferedImg);

        redesenha(); // redesenha plano cartesiano

        /**
         * Printa a imagem no plano cartesiano
         */
        Graphics2D g2d = (Graphics2D) this.getGraphics();
        g2d.setTransform(affineTransform);
        g2d.drawImage(img.getBufferedImage(), getValorCentroX(), getValorCentroY() - img.getBufferedImage().getHeight(), null);
    }

    /**
     * Retorna o valor em RGB de acordo com o valor do pixel
     *
     * @param corRGB
     * @return
     */
    public int getCorPixel(int corRGB) {
        return new Color(corRGB, corRGB, corRGB).getRGB();
    }

    public void desenhaViewPort(List<Ponto> listaPontos) {
        this.redesenha();
        Rasterizacao rast = Rasterizacao.getInstance();
        /**
         * a-b b-c c-d
         */
        rast.pontoMedio(listaPontos.get(0), listaPontos.get(1), Color.BLACK, null);
        rast.pontoMedio(listaPontos.get(1), listaPontos.get(2), Color.BLACK, null);
        rast.pontoMedio(listaPontos.get(2), listaPontos.get(3), Color.BLACK, null);
        rast.pontoMedio(listaPontos.get(3), listaPontos.get(0), Color.BLACK, null);

    }
}
