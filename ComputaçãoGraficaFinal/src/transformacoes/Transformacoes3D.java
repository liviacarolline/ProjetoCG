package transformacoes;

import panels.PanelMenu3D;

import java.util.Stack;

import auxiliares.Eixo;

/**
 * Métodos para transformações 2D
 */
public class Transformacoes3D {

    private static Transformacoes3D instance;
    private double[][] matrizM;

    public Transformacoes3D() {
    }

    public static synchronized Transformacoes3D getInstance() {
        if (instance == null) {
            instance = new Transformacoes3D();
        }
        return instance;
    }

    /**
     * Aplica translação no objeto passado como parametro e de acordo com os
     * fatores de translação.
     *
     * @param matrizObjeto
     * @param tx
     * @param ty
     * @param tz
     * @return double[][] - Matriz resultado.
     */
    public double[][] translacao(double[][] matrizObjeto, double tx, double ty, double tz) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];

        try {
            matrizResult = Matriz.multiplicaMatrizes(geraMatrizTranslacao(tx, ty, tz), matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu3D.matrizObjeto3D = matrizResult;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro na translação!");
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
     * @param sz
     * @return double[][] - Matriz resultado.
     */
    public double[][] escala(double[][] matrizObjeto, double sx, double sy, double sz) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];

        // Fatores de translação.
        double tx = matrizObjeto[0][0], ty = matrizObjeto[1][0], tz = matrizObjeto[2][0];

        try {
            // (Matriz translação posição inicial) X (Matriz Escala)
            matrizM = Matriz.multiplicaMatrizes(geraMatrizTranslacao(tx, ty, tz), geraMatrizEscala(sx, sy, sz));

            // (Matriz M) X (Matriz translação para origem)
            matrizM = Matriz.multiplicaMatrizes(matrizM, geraMatrizTranslacao(-tx, -ty, -tz));

            // (Matriz M) X (Matriz objeto)
            matrizResult = Matriz.multiplicaMatrizes(matrizM, matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu3D.matrizObjeto3D = matrizResult;
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
     * @param eixo
     * @return double[][] - Matriz resultado.
     */
    public double[][] rotacao(double[][] matrizObjeto, double angulo, String eixo) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];

        // Fatores de translação.
        double tx = matrizObjeto[0][0], ty = matrizObjeto[1][0], tz = matrizObjeto[2][0];

        try {
            // (Matriz translação posição inicial) X (Matriz Rotação)
            matrizM = Matriz.multiplicaMatrizes(geraMatrizTranslacao(tx, ty, tz), geraMatrizRotacao(angulo, eixo));

            // (Matriz M) X (Matriz translação para origem)
            matrizM = Matriz.multiplicaMatrizes(matrizM, geraMatrizTranslacao(-tx, -ty, -tz));

            // (Matriz M) X (Matriz objeto)
            matrizResult = Matriz.multiplicaMatrizes(matrizM, matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu3D.matrizObjeto3D = matrizResult;
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
        eixo = eixo.toLowerCase();

        try {
            matrizResult = Matriz.multiplicaMatrizes(geraMatrizReflexao(eixo), matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu3D.matrizObjeto3D = matrizResult;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return matrizResult;
    }

    /**
     * Aplica cisalhamento no objeto passado como parametro, de acordo com os
     * fatores a e b.
     *
     * @param matrizObjeto
     * @param a
     * @param b
     * @param eixo
     * @return double[][] - Matriz resultado.
     */
    public double[][] cisalhamento(double[][] matrizObjeto, double a, double b, String eixo) {
        double[][] matrizResult = new double[matrizObjeto.length][matrizObjeto[0].length];

        // Fatores de translação.
        double tx = matrizObjeto[0][0], ty = matrizObjeto[1][0], tz = matrizObjeto[2][0];

        try {
            // (Matriz translação posição inicial) X (Matriz Cisalhamento)
            matrizM = Matriz.multiplicaMatrizes(geraMatrizTranslacao(tx, ty, tz), geraMatrizCisalhamento(a, b, eixo));

            // (Matriz M) X (Matriz translação para origem)
            matrizM = Matriz.multiplicaMatrizes(matrizM, geraMatrizTranslacao(-tx, -ty, -tz));

            // (Matriz M) X (Matriz objeto)
            matrizResult = Matriz.multiplicaMatrizes(matrizM, matrizObjeto);

            // Atualiza matriz objeto global.
            PanelMenu3D.matrizObjeto3D = matrizResult;
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
            PanelMenu3D.matrizObjeto3D = matrizResult;
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
     * @param tz
     * @return double[][] - matriz translação.
     */
    public double[][] geraMatrizTranslacao(double tx, double ty, double tz) {
        double[][] matriz = new double[4][4];

        matriz[0][0] = 1;
        matriz[0][1] = 0;
        matriz[0][2] = 0;
        matriz[0][3] = tx;

        matriz[1][0] = 0;
        matriz[1][1] = 1;
        matriz[1][2] = 0;
        matriz[1][3] = ty;

        matriz[2][0] = 0;
        matriz[2][1] = 0;
        matriz[2][2] = 1;
        matriz[2][3] = tz;

        matriz[3][0] = 0;
        matriz[3][1] = 0;
        matriz[3][2] = 0;
        matriz[3][3] = 1;

        return matriz;
    }

    /**
     * Gera matriz de escala.
     *
     * @param sx
     * @param sy
     * @param sz
     * @return double[][] - matriz de escala.
     */
    public double[][] geraMatrizEscala(double sx, double sy, double sz) {
        double[][] matriz = new double[4][4];
        sx = (sx == 0) ? 1 : sx;
        sy = (sy == 0) ? 1 : sy;
        sz = (sz == 0) ? 1 : sy;

        matriz[0][0] = sx;
        matriz[1][0] = 0;
        matriz[2][0] = 0;
        matriz[3][0] = 0;

        matriz[0][1] = 0;
        matriz[1][1] = sy;
        matriz[2][1] = 0;
        matriz[3][1] = 0;

        matriz[0][2] = 0;
        matriz[1][2] = 0;
        matriz[2][2] = sz;
        matriz[3][2] = 0;

        matriz[0][3] = 0;
        matriz[1][3] = 0;
        matriz[2][3] = 0;
        matriz[3][3] = 1;

        return matriz;
    }

    /**
     * Gera matriz de rotação em X, Y ou Z de acordo com o eixo passado como
     * parÃ¢metro.
     *
     * @param angulo
     * @param eixo
     * @return double[][] - matriz rotação.
     */
    public double[][] geraMatrizRotacao(double angulo, String eixo) {
        double[][] matriz = new double[4][4];

        double sin = Math.sin(Math.toRadians(angulo));
        double cos = Math.cos(Math.toRadians(angulo));

        eixo = eixo.toUpperCase();

        // Rotação em X
        if (eixo.equals(Eixo.X.getValue())) {
            matriz[0][0] = 1;
            matriz[1][0] = 0;
            matriz[2][0] = 0;
            matriz[3][0] = 0;

            matriz[0][1] = 0;
            matriz[1][1] = cos;
            matriz[2][1] = sin;
            matriz[3][1] = 0;

            matriz[0][2] = 0;
            matriz[1][2] = -sin;
            matriz[2][2] = cos;
            matriz[3][2] = 0;
        } // Rotação em Y
        else if (eixo.equals(Eixo.Y.getValue())) {
            matriz[0][0] = cos;
            matriz[1][0] = 0;
            matriz[2][0] = -sin;
            matriz[3][0] = 0;

            matriz[0][1] = 0;
            matriz[1][1] = 1;
            matriz[2][1] = 0;
            matriz[3][1] = 0;

            matriz[0][2] = sin;
            matriz[1][2] = 0;
            matriz[2][2] = cos;
            matriz[3][2] = 0;
        } else {
            // Rotação em Z
            matriz[0][0] = cos;
            matriz[1][0] = sin;
            matriz[2][0] = 0;
            matriz[3][0] = 0;

            matriz[0][1] = -sin;
            matriz[1][1] = cos;
            matriz[2][1] = 0;
            matriz[3][1] = 0;

            matriz[0][2] = 0;
            matriz[1][2] = 0;
            matriz[2][2] = 1;
            matriz[3][2] = 0;
        }
        // Coluna 4
        matriz[0][3] = 0;
        matriz[1][3] = 0;
        matriz[2][3] = 0;
        matriz[3][3] = 1;

        Matriz.printMatriz(matriz, "ROTA");
        return matriz;
    }

    /**
     * Gera matriz de reflexão em XY, YZ ou XZ de acordo com o eixo passado como
     * parametro.
     *
     * @param eixo
     * @return double[][] - matriz rotação.
     */
    public double[][] geraMatrizReflexao(String eixo) {
        double[][] matriz = new double[4][4];

        eixo = eixo.toUpperCase();

        /**
         * Rotação em XZ
         *
         * COLUNA 1
         */
        matriz[0][0] = 1;
        matriz[1][0] = 0;
        matriz[2][0] = 0;
        matriz[3][0] = 0;

        // COLUNA 2
        matriz[0][1] = 0;
        matriz[1][1] = -1;
        matriz[2][1] = 0;
        matriz[3][1] = 0;

        // COLUNA 3
        matriz[0][2] = 0;
        matriz[1][2] = 0;
        matriz[2][2] = 1;
        matriz[3][2] = 0;

        // Coluna 4
        matriz[0][3] = 0;
        matriz[1][3] = 0;
        matriz[2][3] = 0;
        matriz[3][3] = 1;

        // ReflexÃ£o em XY
        if (eixo.equals(Eixo.XY.getValue())) {
            matriz[1][1] = 1;
            matriz[2][2] = -1;
        } // RotaÃ§Ã£o em YZ
        else if (eixo.equals(Eixo.YZ.getValue())) {
            matriz[0][0] = -1;
            matriz[1][1] = 1;
        }

        return matriz;
    }

    /**
     * Gera matriz de cisalhamento de acordo com o eixo passado como parametro:
     * X, Y ou Z.
     *
     * @param a
     * @param b
     * @param eixo
     * @return matriz de cisalhamento.
     */
    public double[][] geraMatrizCisalhamento(double a, double b, String eixo) {
        double[][] matriz = new double[4][5];
        eixo = eixo.toUpperCase();

        /**
         * Cisalhamento em X
         *
         * COLUNA 1
         */
        matriz[0][0] = 1;
        matriz[1][0] = a;
        matriz[2][0] = b;
        matriz[3][0] = 0;

        // COLUNA 2
        matriz[0][1] = 0;
        matriz[1][1] = 1;
        matriz[2][1] = 0;
        matriz[3][1] = 0;

        // COLUNA 3
        matriz[0][2] = 0;
        matriz[1][2] = 0;
        matriz[2][2] = 1;
        matriz[3][2] = 0;

        // Coluna 4
        matriz[0][3] = 0;
        matriz[1][3] = 0;
        matriz[2][3] = 0;
        matriz[3][3] = 1;

        // Cisalhamento em Y
        if (eixo.equals(Eixo.Y.getValue())) {
            matriz[1][0] = 0;
            matriz[2][0] = 0;
            matriz[0][1] = a;
            matriz[2][1] = b;
        } // Cisalhamento em Z
        else if (eixo.equals(Eixo.Z.getValue())) {
            matriz[1][0] = 0;
            matriz[2][0] = 0;
            matriz[0][2] = a;
            matriz[1][2] = b;
        }

        return matriz;
    }

}
