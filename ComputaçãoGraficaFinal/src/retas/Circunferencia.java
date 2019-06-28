package retas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import panels.PanelPlanoCartesiano;

/**
 * Algoritmos para desenhar circunferÃªncias
 */
public class Circunferencia {

    private static Circunferencia instance;

    private final PanelPlanoCartesiano planoCartesiano;
    private final Graphics g;

    public static int x, y, d_old, x_dif, y_dif;
    private List<Ponto> listaPontos;

    private Circunferencia() {
        listaPontos = new ArrayList<>();
        planoCartesiano = PanelPlanoCartesiano.getInstance();
        g = planoCartesiano.getGraphics();
    }

    public static synchronized Circunferencia getInstance() {
        if (instance == null) {
            instance = new Circunferencia();
        }
        return instance;
    }

    /**
     * Algoritmo da função explicita.
     *
     * @param raio
     * @param cor
     */
    public void funcaoExplicita(int raio, Color cor) {
        planoCartesiano.redesenha();
        
        for (int i = -raio; i < raio; i++) {
            g.setColor(cor);
            g.fillRect(i + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - ((int) Math.sqrt(raio * raio - i * i)), 1, 1);
            g.fillRect(i + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - (-1 * (int) Math.sqrt(raio * raio - i * i)), 1, 1);
        }
    }

    /**
     * Algoritmo da função trigonométrica
     *
     * @param raio
     * @param cor
     */
    public void funcaoTrigonometria(int raio, Color cor) {
        planoCartesiano.redesenha();
        for (int i = -raio; i <= raio; i++) {
            g.setColor(cor);
            g.fillRect(((int) (raio * (double) Math.cos(Math.toRadians(i)))) + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - ((int) (raio * (double) Math.sin(Math.toRadians(i)))), 1, 1);
            drawPontos(((int) (raio * (double) Math.cos(Math.toRadians(i)))), -((int) (raio * (double) Math.sin(Math.toRadians(i)))), cor);
        }
    }

    /**
     * Algoritmo do ponto médio
     *
     * @param raio
     * @param cor
     */
    public void funcaoPontoMedio(int raio, Color cor) {
        planoCartesiano.redesenha();
        
        x = 0;
        y = raio;
        d_old = 1 - raio;
        
        g.setColor(cor);
        g.fillRect(x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - y, 1, 1);
        drawPontos(x, y, cor);
        while (y > x) {
            if (d_old < 0) {
                d_old += 2 * x + 3;
            } else {
                d_old += 2 * x - 2 * y + 5;
                y--;
            }
            x++;
            drawPontos(x, y, cor);
        }

    }

    /**
     * Algoritmo da função elipse
     *
     * @param a
     * @param b
     * @param cor
     * @return
     */
    public List<Ponto> funcaoElipse(int a, int b, Color cor) {
        planoCartesiano.redesenha();
        listaPontos.clear();

        x = 0;
        y = 0;
        double d1;
        double d2;

        /* Valores iniciais */
        x = 0;
        y = b;
        d1 = b * b - a * a * b + a * a / 4.0;

        drawElipse(x, y, cor);

        while (a * a * (y - 0.5) > b * b * (x + 1)) {

            if (d1 < 0) {
                d1 = d1 + b * b * (2 * x + 3);
                x++;
            } else {
                d1 = d1 + b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                x++;
                y--;
            }
            drawElipse(x, y, cor);
        }

        d2 = b * b * (x + 0.5) * (x + 0.5) + a * a * (y - 1) * (y - 1) - a * a * b * b;
        while (y > 0) {

            if (d2 < 0) {
                d2 = d2 + b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
                y--;
            } else {
                d2 = d2 + a * a * (-2 * y + 3);
                y--;
            }
            drawElipse(x, y, cor);
        }

        return listaPontos;
    }

    /**
     * Desenha nos oitantes
     *
     * @param x
     * @param y
     * @param cor
     */
    private void drawPontos(int x, int y, Color cor) {
        g.setColor(cor);

        g.fillRect(x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - y, 1, 1);
        g.fillRect(x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() + y, 1, 1);
        g.fillRect(-x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - y, 1, 1);
        g.fillRect(-x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() + y, 1, 1);

        g.fillRect(y + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - x, 1, 1);
        g.fillRect(y + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() + x, 1, 1);
        g.fillRect(-y + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - x, 1, 1);
        g.fillRect(-y + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() + x, 1, 1);
    }

    /**
     * Imprime os pontos da elipse
     *
     * @param x
     * @param y
     * @param cor
     * @param panel
     */
    private void drawElipse(int x, int y, Color cor) {
        g.setColor(cor);

        g.fillRect(x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - y, 1, 1);
        g.fillRect(x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() + y, 1, 1);
        g.fillRect(-x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() - y, 1, 1);
        g.fillRect(-x + planoCartesiano.getValorCentroX(), planoCartesiano.getValorCentroY() + y, 1, 1);

        if (listaPontos == null) {
            listaPontos = new ArrayList<>();
        }

        listaPontos.add(new Ponto((double) (x + planoCartesiano.getValorCentroX()), (double) (planoCartesiano.getValorCentroY() - y)));
        listaPontos.add(new Ponto((double) (x + planoCartesiano.getValorCentroX()), (double) (planoCartesiano.getValorCentroY() + y)));
        listaPontos.add(new Ponto((double) (-x + planoCartesiano.getValorCentroX()), (double) (planoCartesiano.getValorCentroY() - y)));
        listaPontos.add(new Ponto((double) (-x + planoCartesiano.getValorCentroX()), (double) (planoCartesiano.getValorCentroY() + y)));
    }
}
