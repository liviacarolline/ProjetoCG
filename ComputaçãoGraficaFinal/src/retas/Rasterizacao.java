package retas;

import java.awt.Color;
import javax.swing.JTextArea;

import panels.PanelPlanoCartesiano;

/**
 * Classe que impementa os algoritmos de rasterização da reta.
 *
 */
public class Rasterizacao {

    private static Rasterizacao instance;

    private final PanelPlanoCartesiano planoCartesiano;

    public Rasterizacao() {
        planoCartesiano = PanelPlanoCartesiano.getInstance();
    }

    public static synchronized Rasterizacao getInstance() {
        if (instance == null) {
            instance = new Rasterizacao();
        }
        return instance;
    }

    /**
     * Algoritmo DDA (Digital Differential Analyser)
     *
     * @param pInicial Ponto inicial
     * @param pFinal Ponto Final
     * @param cor Cor da reta
     * @param textAreaSolution jTextArea para exibir solução
     */
    public void dda(Ponto pInicial, Ponto pFinal, Color cor, JTextArea textAreaSolution) {
        double x1 = pInicial.getX();
        double x2 = pFinal.getX();
        double y1 = pInicial.getY();
        double y2 = pFinal.getY();
        double dx = (x2 - x1);
        double dy = (y2 - y1);
        int count = 0;

        if (Math.abs(y2 - y1) <= Math.abs(x2 - x1)) {
            if ((x1 == x2) && (y1 == y2)) {
                planoCartesiano.drawPixel(x1, y1, cor);
//                setSolution(textAreaSolution, x1, y1, ++count, null);
            } else {
                if (x2 < x1) {
                    double tmp = x2;
                    x2 = x1;
                    x1 = tmp;

                    tmp = y2;
                    y2 = y1;
                    y1 = tmp;
                }

                double k = dy / dx;
                int cele_y;
                double y = y1;

                for (int x = (int) x1; x <= x2; x++) {
                    cele_y = (int) Math.round(y);
                    planoCartesiano.drawPixel(x, cele_y, cor);
//                    setSolution(textAreaSolution, x, cele_y, ++count, null);
                    y += k;
                }
            }
        } else if (y2 < y1) {
            double tmp = x2;
            x2 = x1;
            x1 = tmp;

            tmp = y2;
            y2  = y1;
            y1 = tmp;
        }

        double k = dx / dy;
        double x = x1;
        for (int y = (int) y1; y <= y2; y++) {
            planoCartesiano.drawPixel(x, y, cor);
//            setSolution(textAreaSolution, x, y, ++count, null);
            x += k;
        }
    }

    /**
     * Algoritmo do ponto médio 
     *
     * @param pInicial Ponto inicial
     * @param pFinal Ponto Final
     * @param cor Cor da reta
     * @param textAreaSolution jTextArea para exibir solução
     */
    public void pontoMedio(Ponto pInicial, Ponto pFinal, Color cor, JTextArea textAreaSolution) {
        int x1 = (int) (pInicial.getX() + pInicial.getZ());
        int x2 = (int) (pFinal.getX() + pInicial.getZ());
        int y1 = (int) (pInicial.getY() + pFinal.getZ());
        int y2 = (int) (pFinal.getY() + pFinal.getZ());

        if ((x1 == x2) && (y1 == y2)) {
            planoCartesiano.drawPixel(x1, y1, cor);
        } else {
            double dx = Math.abs(x2 - x1);
            double dy = Math.abs(y2 - y1);
            double rozdil = dx - dy;
            int posun_x, posun_y;

            // Determinando Incremento
            if (x1 < x2) {
                posun_x = 1;
            } else {
                posun_x = -1;
            }
            if (y1 < y2) {
                posun_y = 1;
            } else {
                posun_y = -1;
            }

            int count = 0;
            planoCartesiano.drawPixel((double) x1, (double) y1, cor); // Pinta o primeiro ponto
//            setSolution(textAreaSolution, x1, y1, ++count, null);

            //Desenha a reta, fazendo o somatório em x e y.
            while ((x1 != x2) || (y1 != y2)) {
                double p = 2 * rozdil;

                if (p > -dy) {
                    rozdil = rozdil - dy;
                    x1 = x1 + posun_x;
                }
                if (p < dx) {
                    rozdil = rozdil + dx;
                    y1 = y1 + posun_y;
                }

                planoCartesiano.drawPixel((double) x1, (double) y1, cor);
//                setSolution(textAreaSolution, x1, y1, ++count, null);
            }
        }
    }

//    /**
//     * Popula o jTextArea com a solução do problema.
//     *
//     * @param textAreaSolution
//     * @param x
//     * @param y
//     * @param count
//     * @param d
//     */
//    private static void setSolution(JTextArea textAreaSolution, double x, double y, int count, String d) {
//        if (textAreaSolution != null) {
//            StringBuilder solution = new StringBuilder();
//
//            solution.append(textAreaSolution.getText());
//            solution.append(String.format("%02d", count));
//            solution.append("-> ");
//            if (d != null) {
//                solution.append("d = ").append(Math.round(Double.parseDouble(d))).append(", ");
//            }
//            solution.append("P(").append(Math.round(x)).append(", ").append(Math.round(y));
//            solution.append(")\n");
//            textAreaSolution.setText(solution.toString());
//        }
//    }
}
