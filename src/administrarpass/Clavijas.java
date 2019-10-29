package administrarpass;

public class Clavijas {

    private char x;
    private char y;

    /**
     * Crea la pareja de clavijas
     *
     * @param a primera clavija
     * @param b segunda clavija
     */
    public Clavijas(char a, char b) {
        this.x = a;
        this.y = b;
    }

    /**
     * Obtiene la "primera" clavija
     *
     * @return la primera clavija
     */
    public char getX() {
        return this.x;
    }

    /**
     * Obtiene la "segunda" clavija
     *
     * @return la segunda clavija
     */
    public char getY() {
        return this.y;
    }
}
