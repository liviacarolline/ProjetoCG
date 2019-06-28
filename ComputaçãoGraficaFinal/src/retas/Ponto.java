package retas;

/**
 * Representa um ponto
 */
public class Ponto {

    private double x;
    private double y;
    private double z;

    public Ponto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Ponto(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void swap() {
        double temp = x;
        setX(y);
        setY(temp);
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Ponto{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
