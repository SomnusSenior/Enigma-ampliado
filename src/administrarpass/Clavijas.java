package administrarpass;

public class Clavijas {

    private char primera;
    private char segunda;

    /**
     * Crea la pareja de clavijas
     *
     * @param a primera clavija
     * @param b segunda clavija
     */
    public Clavijas(char a, char b) {
        this.primera = a;
        this.segunda = b;
    }

    /**
     * Obtiene la "primera" clavija
     *
     * @return la primera clavija
     */
    public char getPrimera() {
        return this.primera;
    }

    /**
     * Obtiene la "segunda" clavija
     *
     * @return la segunda clavija
     */
    public char getSegunda() {
        return this.segunda;
    }
}
