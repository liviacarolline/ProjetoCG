package transformacoes;

import panels.PanelMenu2D;

import java.util.Stack;
import javax.swing.JPanel;

import auxiliares.Eixo;

/**
 * Métodos para transformações 2D.
 *
 */
public class Transformacoes2D {

    private static Transformacoes2D instance;

    private double[][] matrizM;

    private Transformacoes2D() {
    }

    public static synchronized Transformacoes2D getInstance() {
        if (instance == null) {
            instance = new Transformacoes2D();
        }
        return instance;
    }

    /**
     * Aplica translação no objeto passado como parametro e de acordo com os
     * fatores de translaçãoo.
     *
     * @param matrizObjeto
     * @param tx
     * @param ty
     * @return double[][] - Matriz resultado.
     */
    public double[][] translacao(double[][] matrizObjeto, double tx, double ty) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];

        try {
            matrizResult = Matriz.multiplicaMatrizes(geraMatrizTranslacao(tx, ty), matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu2D.matrizObjeto = matrizResult;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro na translaçãoo!");
        }

        return matrizResult;
    }

    /**
     * Aplica escala no objeto passado como parametro, de acordo com os fatores
     * de escala.
     *
     * @param matrizObjeto
     * @param sx
     * @param sy
     * @return double[][] - Matriz resultado.
     */
    public double[][] escala(double[][] matrizObjeto, double sx, double sy) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];

        // Fatores de translação.
        double tx = matrizObjeto[0][0], ty = matrizObjeto[1][0];

        try {
            // (Matriz translação posição inicial) X (Matriz Escala)
            matrizM = Matriz.multiplicaMatrizes(geraMatrizTranslacao(tx, ty), geraMatrizEscala(sx, sy));

            // (Matriz M) X (Matriz translação para origem)
            matrizM = Matriz.multiplicaMatrizes(matrizM, geraMatrizTranslacao(-tx, -ty));

            // (Matriz M) X (Matriz objeto)
            matrizResult = Matriz.multiplicaMatrizes(matrizM, matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu2D.matrizObjeto = matrizResult;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro na escala!");
        }

        return matrizResult;
    }

    /**
     * Aplica rotacao no objeto passado como parametro, de acordo com angulo.
     *
     * @param matrizObjeto
     * @param angulo
     * @return double[][] - Matriz resultado.
     */
    public double[][] rotacao(double[][] matrizObjeto, double angulo) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];

        // Fatores de translação.
        double tx = matrizObjeto[0][0], ty = matrizObjeto[1][0];

        try {
            // (Matriz translação posição inicial) X (Matriz Rotação)
            matrizM = Matriz.multiplicaMatrizes(geraMatrizTranslacao(tx, ty), geraMatrizRotacao(angulo));

            // (Matriz M) X (Matriz translação para origem)
            matrizM = Matriz.multiplicaMatrizes(matrizM, geraMatrizTranslacao(-tx, -ty));

            // (Matriz M) X (Matriz objeto)
            matrizResult = Matriz.multiplicaMatrizes(matrizM, matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu2D.matrizObjeto = matrizResult;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro na rotação!");
        }

        return matrizResult;
    }

    /**
     * Aplica reflexão no objeto passado como parametro, de acordo com o eixo
     * escolhido
     *
     * @param matrizObjeto
     * @param eixo
     * @return double[][] - Matriz resultado.
     */
    public double[][] reflexao(double[][] matrizObjeto, String eixo) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];
        eixo = eixo.toUpperCase();

        try {
            matrizResult = Matriz.multiplicaMatrizes(geraMatrizReflexao(eixo), matrizObjeto);
            // Atualiza matriz objeto global.
            PanelMenu2D.matrizObjeto = matrizResult;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro na reflexÃ£o em: " + eixo + "!");
        }

        return matrizResult;
    }

    /**
     * Aplica cisalhamento no objeto passado como parametro, de acordo com os
     * fatores a e b.
     *
     * @param matrizObjeto
     * @param cx
     * @param cy
     * @return double[][] - Matriz resultado.
     */
    public double[][] cisalhamento(double[][] matrizObjeto, double cx, double cy) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];

        // Fatores de translação.
        double tx = matrizObjeto[0][0], ty = matrizObjeto[1][0];

        try {
            // (Matriz translação posição inicial) X (Matriz Cisalhamento)
            matrizM = Matriz.multiplicaMatrizes(geraMatrizTranslacao(tx, ty), geraMatrizCisalhamento(cx, cy));

            // (Matriz M) X (Matriz translação para origem)
            matrizM = Matriz.multiplicaMatrizes(matrizM, geraMatrizTranslacao(-tx, -ty));

            // (Matriz M) X (Matriz objeto)
            matrizResult = Matriz.multiplicaMatrizes(matrizM, matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu2D.matrizObjeto = matrizResult;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro no cisalhamento!");
        }

        return matrizResult;
    }

    /**
     * Aplica as operações compostas.
     *
     * @param listaDeTransformacoes
     * @param matrizObjeto
     * @return double[][] - Matriz resultado.
     */
    public double[][] composta(Stack<double[][]> listaDeTransformacoes, double[][] matrizObjeto) {
        Stack<double[][]> listaDeTransformacoesCopy = (Stack<double[][]>) listaDeTransformacoes.clone();

        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];
        matrizM = listaDeTransformacoesCopy.pop();

        try {
            while (!listaDeTransformacoesCopy.isEmpty()) {
                matrizM = Matriz.multiplicaMatrizes(matrizM, listaDeTransformacoesCopy.pop());
            }

            matrizResult = Matriz.multiplicaMatrizes(matrizM, matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu2D.matrizObjeto = matrizResult;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro nas transformações compostas!");
        }

        return matrizResult;
    }

    /**
     * Gera matriz de translação.
     *
     * @param tx
     * @param ty
     * @return double[][] - matriz translação.
     */
    public double[][] geraMatrizTranslacao(double tx, double ty) {
        double[][] matriz = new double[3][3];

        matriz[0][0] = 1;
        matriz[0][1] = 0;
        matriz[0][2] = tx;

        matriz[1][0] = 0;
        matriz[1][1] = 1;
        matriz[1][2] = ty;

        matriz[2][0] = 0;
        matriz[2][1] = 0;
        matriz[2][2] = 1;

        return matriz;
    }

    /**
     * Gera matriz de escala.
     *
     * @param sx
     * @param sy
     * @return double[][] - matriz de escala.
     */
    public double[][] geraMatrizEscala(double sx, double sy) {
        double[][] matriz = new double[3][3];
        sx = (sx == 0) ? 1 : sx;
        sy = (sy == 0) ? 1 : sy;

        matriz[0][0] = sx;
        matriz[1][0] = 0;
        matriz[2][0] = 0;

        matriz[0][1] = 0;
        matriz[1][1] = sy;
        matriz[2][1] = 0;

        matriz[0][2] = 0;
        matriz[1][2] = 0;
        matriz[2][2] = 1;

        return matriz;
    }

    /**
     * Gera matriz de rotação.
     *
     * @param angulo
     * @return double[][] - matriz rotação.
     */
    public double[][] geraMatrizRotacao(double angulo) {
        double[][] matriz = new double[3][3];

        double sen = Math.sin(Math.toRadians(angulo));
        double cos = Math.cos(Math.toRadians(angulo));

        // Coluna 0
        matriz[0][0] = cos;
        matriz[1][0] = sen;
        matriz[2][0] = 0;

        // Coluna 1
        matriz[0][1] = -sen;
        matriz[1][1] = cos;
        matriz[2][1] = 0;

        // Coluna 2
        matriz[0][2] = 0;
        matriz[1][2] = 0;
        matriz[2][2] = 1;

        return matriz;
    }

    /**
     * Gera matriz de reflexão em torno do eixo passado como parametro X, Y ou
     * XY.
     *
     * @return double[][] - matriz reflexão.
     */
    public double[][] geraMatrizReflexao(String eixo) {
        double[][] matriz = new double[3][3];

        eixo = eixo.toUpperCase();

        // Rotação em XY
        // Coluna 0
        matriz[0][0] = -1;
        matriz[1][0] = 0;
        matriz[2][0] = 0;

        // Coluna 1
        matriz[0][1] = 0;
        matriz[1][1] = -1;
        matriz[2][1] = 0;

        // Coluna 2
        matriz[0][2] = 0;
        matriz[1][2] = 0;
        matriz[2][2] = 1;

        // Rotação em X
        if (eixo.equals(Eixo.X.getValue())) {
            matriz[0][0] = 1;
        } else if (eixo.equals(Eixo.Y.getValue())) {
            matriz[1][1] = 1;
        }

        return matriz;
    }

    /**
     * Gera matriz de cisalhamento.
     *
     * @param cx
     * @param cy
     * @return matriz de cisalhamento.
     */
    public double[][] geraMatrizCisalhamento(double cx, double cy) {
        double[][] matriz = new double[3][3];

        // Coluna 0
        matriz[0][0] = 1;
        matriz[1][0] = cy;
        matriz[2][0] = 0;

        // Coluna 1
        matriz[0][1] = cx;
        matriz[1][1] = 1;
        matriz[2][1] = 0;

        // Coluna 2
        matriz[0][2] = 0;
        matriz[1][2] = 0;
        matriz[2][2] = 1;

        return matriz;
    }
}
