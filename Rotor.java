package administrarpass;

import static administrarpass.EjecutarEnigma.cifrado;


public class Rotor {

    private String contenido; // alfabeto del rotor
    private String contEscritura = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // alfabeto de escritura
    private String contEscrituraAmpliado = "!\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // alfabeto de escritura AMPLIADO
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
        if (cifrado == 0) {
            this.contEscritura = mensaje;
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
        String aux = "";
        String mensaje = "";
        int nuevaPos = this.tam + offset; // calcula la nueva posición del rotor
        for (int i = 0; i < this.tam; i++) {
            if (i + offset < this.tam) { // giro del rotor
                aux += this.contenido.charAt(i + offset);
                if (cifrado == 0) {
                    mensaje += this.contEscritura.charAt(i + offset);
                } else {
                    mensaje += this.contEscrituraAmpliado.charAt(i + offset);
                }
            } else { // giro del rotor al dar una vuelta completa
                aux += this.contenido.charAt(i + offset - this.tam);
                if (cifrado == 0) {
                    mensaje += this.contEscritura.charAt(i + offset - this.tam);
                } else {
                    mensaje += this.contEscrituraAmpliado.charAt(i + offset - this.tam);
                }
            }
        }
        return new Rotor(aux, nuevaPos, mensaje, this.puntoGiro);
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
     * Cifra la letra a la "ida", antes del reflector; también realiza el
     * cifrado del reflector
     *
     * @param i índice del caracter a cifrar
     * @return índice de la letra cifrada
     */
    public int cifrarIda(int i) {
        char c = this.contenido.charAt(i); // obtiene el caracter con ese índice en el alfabeto del rotor
        int iCifrada;
        if (cifrado == 0) {
            iCifrada = this.contEscritura.indexOf(c); // obtiene el índice del caracter pasado con respecto al alfabeto de escritura
        } else {
            iCifrada = this.contEscrituraAmpliado.indexOf(c); // obtiene el índice del caracter pasado con respecto al alfabeto de escritura
        }
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
        if (cifrado == 0) {
            c = this.contEscritura.charAt(i); // obtiene el caracter con ese índice con respecto al alfabeto de escritura}else{
        } else {
            c = this.contEscrituraAmpliado.charAt(i); // obtiene el caracter con ese índice con respecto al alfabeto de escritura
        }
        int iCifrada = this.contenido.indexOf(c); // obtiene el índice del caracter pasado al alfabeto del rotor
        return iCifrada;
    }

    /**
     * Obtiene el punto de cambio / giro de los rotores
     *
     * @return punto de cambio giro
     */
    public char obtenerPuntoGiro() {
        return this.puntoGiro;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
