package administrarpass;

public class Clavijas {

    private char a;
    private char b;

    /**
     * Crea la pareja de clavijas
     *
     * @param a primera clavija
     * @param b segunda clavija
     */
    public Clavijas(char a, char b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Obtiene la "primera" clavija
     *
     * @return la primera clavija
     */
    public char getA() {
        return this.a;
    }

    /**
     * Obtiene la "segunda" clavija
     *
     * @return la segunda clavija
     */
    public char getB() {
        return this.b;
    }
}
