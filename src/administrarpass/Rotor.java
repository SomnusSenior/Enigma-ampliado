package administrarpass;

public class Rotor {

    private String contenido; // alfabeto del rotor
    private String contEscritura = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // alfabeto de escritura
    private String contEscrituraAmpliadoPlus = "!\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // alfabeto de escritura AMPLIADO Plus
    private String contEscrituraAmpliado = " !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // alfabeto de escritura AMPLIADO
    private int posicion; // posición del rotor
    private int tam; // tamaño del rotor
    private char puntoGiro; // punto de giro del rotor

    /**
     * Creacion del rotor
     *
     * @param contenido Contenido
     * @param puntoGiro Punto de giro
     */
    public Rotor(String contenido, char puntoGiro) {
        this.contenido = contenido;
        this.posicion = 0;
        this.tam = contenido.length();
        this.puntoGiro = puntoGiro;
    }

    /**
     * Creacion del rotor
     *
     * @param contenido Contenido
     * @param pos Posicion
     * @param mensaje Mensaje a traducir
     * @param puntoGiro Punto de giro
     */
    public Rotor(String contenido, int pos, String mensaje, char puntoGiro) {
        this.contenido = contenido;
        this.tam = contenido.length();
        this.posicion = pos % this.tam;
        this.contEscritura = mensaje;
        this.puntoGiro = puntoGiro;
    }

    /**
     * Creacion del rotor
     *
     * @param contenido Contenido
     * @param pos Posicion
     * @param mensaje Mensaje a traducir
     * @param puntoGiro Punto de giro
     * @param plus Indica si el rotor ampliado es plus
     */
    public Rotor(String contenido, int pos, String mensaje, char puntoGiro, boolean plus) {
        this.contenido = contenido;
        this.tam = contenido.length();
        this.posicion = pos % this.tam;
        if (plus) {
            this.contEscrituraAmpliadoPlus = mensaje;
        } else {
            this.contEscrituraAmpliado = mensaje;
        }
        this.puntoGiro = puntoGiro;
    }

    /**
     * Gira el rotor
     *
     * @param offset numero de posiciones a desplazar
     * @return un nuevo rotor con el contenido "girado"
     */
    public Rotor giro(int offset) {
        StringBuilder aux = new StringBuilder();
        StringBuilder mensaje = new StringBuilder();
        int nuevaPos = this.tam + offset; // calcula la nueva posición del rotor
        for (int i = 0; i < this.tam; i++) {
            aux.append((i + offset < this.tam) ? this.contenido.charAt(i + offset) : this.contenido.charAt(i + offset - this.tam)); // giro del rotor : giro del rotor al dar una vuelta completa
            mensaje.append((i + offset < this.tam) ? this.contEscritura.charAt(i + offset) : this.contEscritura.charAt(i + offset - this.tam));
        }
        return new Rotor(aux.toString(), nuevaPos, mensaje.toString(), this.puntoGiro);
    }

    /**
     * Gira el rotor ampliado
     *
     * @param offset numero de posiciones a desplazar
     * @param plus Indica si es ampliado plus
     * @return un nuevo rotor con el contenido "girado"
     */
    public Rotor giroAmpliado(int offset, boolean plus) {
        StringBuilder aux = new StringBuilder();
        StringBuilder mensaje = new StringBuilder();
        int nuevaPos = this.tam + offset; // calcula la nueva posición del rotor
        char c; // si el índice se sale o no del array
        for (int i = 0; i < this.tam; i++) {
            // giro del rotor : giro del rotor al dar una vuelta completa
            aux.append((i + offset < this.tam) ? this.contenido.charAt(i + offset) : this.contenido.charAt(i + offset - this.tam));
            if (i + offset < this.tam) {
                c = plus ? this.contEscrituraAmpliadoPlus.charAt(i + offset) : this.contEscrituraAmpliado.charAt(i + offset);
            } else {
                c = plus ? this.contEscrituraAmpliadoPlus.charAt(i + offset - this.tam) : this.contEscrituraAmpliado.charAt(i + offset - this.tam);
            }
            mensaje.append(c);
        }
        return new Rotor(aux.toString(), nuevaPos, mensaje.toString(), this.puntoGiro);
    }

    /**
     * Cifra la letra a la "ida", antes del reflector; también realiza el
     * cifrado del reflector
     *
     * @param i índice del caracter a cifrar
     * @return índice de la letra cifrada
     */
    public int cifrarIda(int i) {
        int iCifrada;
        Enigma.pintar[Enigma.indiceP] = this.contEscritura.charAt(i);
        Enigma.indiceP++;
        char c = this.contenido.charAt(i); // obtiene el caracter con ese índice en el alfabeto del rotor
        Enigma.pintar[Enigma.indiceP] = c;
        Enigma.indiceP++;
        iCifrada = this.contEscritura.indexOf(c); // obtiene el índice del caracter pasado con respecto al alfabeto de escritura
        return iCifrada;
    }

    /**
     * Cifra la letra a la "vuelta", después del reflector
     *
     * @param i índice de la letra a cifrar
     * @return índice del caracter cifrada
     */
    public int cifrarVuelta(int i) {
        char c;
        c = this.contEscritura.charAt(i); // obtiene el caracter con ese índice en el alfabeto de escritura
        Enigma.pintar[Enigma.indiceP] = c;
        Enigma.indiceP++;
        int iCifrada = this.contenido.indexOf(c); // obtiene el índice del caracter pasado al alfabeto del rotor
        Enigma.pintar[Enigma.indiceP] = this.contEscritura.charAt(iCifrada);
        Enigma.indiceP++;
        return iCifrada;
    }

    /**
     * Cifra a la "ida"
     *
     * @param i
     * @param plus
     * @return nuevo índice
     */
    public int cifrarIdaAmpliado(int i, boolean plus) {
        char c = this.contenido.charAt(i); // obtiene el caracter con ese índice en el alfabeto del rotor
        return plus ? this.contEscrituraAmpliadoPlus.indexOf(c) : this.contEscrituraAmpliado.indexOf(c); // obtiene el índice del caracter pasado con respecto al alfabeto de escritura
    }

    /**
     * Cifra a la "vuelta"
     *
     * @param i
     * @param plus
     * @return nuevo índice
     */
    public int cifrarVueltaAmpliado(int i, boolean plus) {
        char c;
        c = plus ? this.contEscrituraAmpliadoPlus.charAt(i) : this.contEscrituraAmpliado.charAt(i); // obtiene el caracter con ese índice en el alfabeto de escritura
        return this.contenido.indexOf(c); // obtiene el índice del caracter pasado al alfabeto del rotor
    }

    /**
     * Obtiene la posicion actual del rotor
     *
     * @return posicion actual
     */
    public int obtenerPosicion() {
        return this.posicion;
    }

    /**
     * Obtiene el alfabeto del rotor
     *
     * @return contenido del rotor
     */
    public String obtenerContenido() {
        return this.contenido;
    }

    /**
     * Obtiene el alfabeto ordenado, antes de empezar a cifrar
     *
     * @return alfabeto de escritura
     */
    public String obtenerContEscritura() {
        return this.contEscritura;
    }

    /**
     * Obtiene el alfabeto ordenado AMPLIADO, antes de empezar a cifrar
     *
     * @return alfabeto de escritura
     */
    public String obtenerContEscrituraAmpliado() {
        return this.contEscrituraAmpliado;
    }

    /**
     * Obtiene el alfabeto ordenado AMPLIADO, antes de empezar a cifrar
     *
     * @return alfabeto de escritura
     */
    public String obtenerContEscrituraAmpliadoPlus() {
        return this.contEscrituraAmpliadoPlus;
    }

    /**
     * Obtiene el punto de cambio / giro de los rotores
     *
     * @return punto de cambio giro
     */
    public char obtenerPuntoGiro() {
        return this.puntoGiro;
    }
}
