package retas;

import exceptions.RecorteException;
import panels.PanelPlanoCartesiano;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RecorteLinha {

    static RecorteLinha instance;
    private final PanelPlanoCartesiano planoCartesiano;
    private final Graphics g;
    private List<Ponto> listaPontos;

    public RecorteLinha() {
        listaPontos = new ArrayList<>();
        planoCartesiano = PanelPlanoCartesiano.getInstance();
        g = planoCartesiano.getGraphics();
    }

    public static synchronized RecorteLinha getInstance() {
        if (instance == null) {
            instance = new RecorteLinha();
        }
        return instance;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static String funcaoSig(double a, double b) {
        if ((a - b) > 0) {
            return "1";
        }
        return "0";
    }

    public String criarBit(double Xmin, double Xmax, double Ymin, double Ymax, Ponto ponto) {
        String bit1, bit2, bit3, bit4;

        bit1 = funcaoSig((int) ponto.getY(), Ymax); //sig(y-ymax)
        bit2 = funcaoSig(Ymin, (int) ponto.getY()); //sig(ymin - y)
        bit3 = funcaoSig((int) ponto.getX(), Xmax); //sig(x-xmax)
        bit4 = funcaoSig(Xmin, (int) ponto.getX()); //sig(xmin-x)

        return bit1 + bit2 + bit3 + bit4;
    }

    public String operacaoLogicaAND(String codigo1, String codigo2) {
        String codigoFinal = "";
        int bitFInal;

//      fazer operacao AND bit/bit (codigo1 AND codigo2)        
        for (int bit = 0; bit < codigo1.length(); bit++) {
            bitFInal = Integer.parseInt(String.valueOf(codigo1.charAt(bit))) & Integer.parseInt(String.valueOf(codigo2.charAt(bit)));
            codigoFinal += bitFInal;
        }
        return codigoFinal;
    }

    public boolean isRetaInJanela(double Xmin, double Xmax, double Ymin, double Ymax, Ponto pontoInicial, Ponto pontoFinal) throws RecorteException {
        String codigo1, codigo2, resultado;
        codigo1 = criarBit(Xmin, Xmax, Ymin, Ymax, pontoInicial);
        codigo2 = criarBit(Xmin, Xmax, Ymin, Ymax, pontoFinal);
        if ("0000".equals(codigo1) && "0000".equals(codigo2)) {
            throw new RecorteException("Trivial \nA reta esta dentro da janela");
        } else {
            resultado = operacaoLogicaAND(codigo1, codigo2);
            if (!"0000".equals(resultado)) {
                throw new RecorteException("Rejeita \nA reta esta fora da janela");
            }
        }
        return true;
    }

    /**
     *
     * @param codigo
     * @param Xmin
     * @param Xmax
     * @param Ymin
     * @param Ymax
     * @param pontoInicial
     * @param pontoFinal
     * @return
     */
    public Ponto aplicaRecortePonto1(String codigo, double Xmin, double Xmax, double Ymin, double Ymax, Ponto pontoInicial, Ponto pontoFinal) {
        int posicao = 0;
        double m = (pontoFinal.getY() - pontoInicial.getY()) / (pontoFinal.getX() - pontoInicial.getX());

        posicao = codigo.indexOf("1");
        System.out.println(posicao);
        switch (posicao) {
            case 0:
                double xNovo1 = pontoInicial.getX() + (Ymax - pontoInicial.getY()) / m;
                pontoInicial.setX(xNovo1);
                pontoInicial.setY(Ymax);
                break;
            case 1:
                double xNovo2 = pontoInicial.getX() + (Ymin - pontoInicial.getY()) / m;
                pontoInicial.setX(xNovo2);
                pontoInicial.setY(Ymin);
                break;
            case 2:
                double yNovo1 = pontoInicial.getY() + m * (Xmax - pontoInicial.getX());
                pontoInicial.setX(Xmax);
                pontoInicial.setY(yNovo1);
                break;

            case 3:
                double yNovo2 = pontoInicial.getY() + m * (Xmin - pontoInicial.getX());
                pontoInicial.setX(Xmin);
                pontoInicial.setY(yNovo2);
                break;
        }
        return pontoInicial;
    }

    public Ponto aplicaRecortePonto2(String codigo, double xMin, double xMax, double yMin, double yMax, Ponto pontoInicial, Ponto pontoFinal) {
        int posicao = 0;
        double m = (pontoFinal.getY() - pontoInicial.getY()) / (pontoFinal.getX() - pontoInicial.getX());

        posicao = codigo.indexOf("1");
//        aplicar o recorte de acordo a posicao do bit 1 
        switch (posicao) {
            case 0:
                double xNovo1 = pontoInicial.getX() + (yMax - pontoInicial.getY()) / m;
                pontoFinal.setX(xNovo1);
                pontoFinal.setY(yMax);
                break;
            case 1:
                double xNovo2 = pontoInicial.getX() + (yMin - pontoInicial.getY()) / m;
                pontoFinal.setX(xNovo2);
                pontoFinal.setY(yMin);
                break;
            case 2:
                double yNovo1 = pontoInicial.getY() + m * (xMax - pontoInicial.getX());
                pontoFinal.setX(xMax);
                pontoFinal.setY(yNovo1);
                break;

            case 3:
                double yNovo2 = pontoInicial.getY() + m * (xMin - pontoInicial.getX());
                pontoFinal.setX(xMin);
                pontoFinal.setY(yNovo2);
                break;
        }
        return pontoFinal;
    }

    public List<Ponto> recorteFinal(double xMin, double xMax, double yMin, double yMax, List<Ponto> listaPontosOriginal) {
        List<Ponto> novaListaPontos = new ArrayList<>();
        String codigo1, codigo2;

        Ponto pontoA;
        Ponto pontoB;
        System.out.println("pontoOrigin1 " + listaPontosOriginal.get(0));
        System.out.println("pontoOrigin2 " + listaPontosOriginal.get(1));

        codigo1 = criarBit(xMin, xMax, yMin, yMax, listaPontosOriginal.get(0));
        codigo2 = criarBit(xMin, xMax, yMin, yMax, listaPontosOriginal.get(1));

        while (!codigo1.equals("0000") || !codigo2.equals("0000")) {
            if (!codigo1.equals("0000") && codigo2.equals("0000")) {
                pontoA = aplicaRecortePonto1(codigo1, xMin, xMax, yMin, yMax, listaPontosOriginal.get(0), listaPontosOriginal.get(1));

                codigo1 = criarBit(xMin, xMax, yMin, yMax, pontoA);
                System.out.println("Novo ponto" + pontoA);
                codigo2 = criarBit(xMin, xMax, yMin, yMax, listaPontosOriginal.get(1));
                novaListaPontos.add(pontoA);
                novaListaPontos.add(listaPontosOriginal.get(1));
                System.out.println("1: " + codigo1);
            } else if (codigo1.equals("0000") && !codigo2.equals("0000")) {
                pontoB = aplicaRecortePonto2(codigo2, xMin, xMax, yMin, yMax, listaPontosOriginal.get(0), listaPontosOriginal.get(1));
                codigo2 = criarBit(xMin, xMax, yMin, yMax, pontoB);
                novaListaPontos.add(listaPontosOriginal.get(0));
                novaListaPontos.add(pontoB);
                System.out.println("2");
            } else {
                pontoA = aplicaRecortePonto1(codigo1, xMin, xMax, yMin, yMax, listaPontosOriginal.get(0), listaPontosOriginal.get(1));
                pontoB = aplicaRecortePonto2(codigo2, xMin, xMax, yMin, yMax, listaPontosOriginal.get(0), listaPontosOriginal.get(1));
                codigo1 = criarBit(xMin, xMax, yMin, yMax, pontoA);
                codigo2 = criarBit(xMin, xMax, yMin, yMax, pontoB);
                novaListaPontos.add(pontoA);
                novaListaPontos.add(pontoB);
                System.out.println("3");
            }

        }

//        
        System.out.println("pontoFinal1 " + novaListaPontos.get(0));
        System.out.println("pontoFinal2 " + novaListaPontos.get(1));
        return novaListaPontos;
    }

}
