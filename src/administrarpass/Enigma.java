package administrarpass;

import static administrarpass.EjecutarEnigma.cifrado;
import static administrarpass.EjecutarEnigma.modo;

public class Enigma {

    private Rotor entrada = new Rotor("ABCDEFGHIJKLMNOPQRSTUVWXYZ", '-'); // rotor entrada
    private Rotor entradaAmpliado = new Rotor(" !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", '-'); // rotor entrada AMPLIADO
    private Rotor entradaAmpliadoPlus = new Rotor("!\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", '-'); // rotor entrada AMPLIADO Plus
    private static Rotor rotorDerecha; // rotor derecho
    private static Rotor rotorCentral; // rotor medio
    private static Rotor rotorIzquierda; // rotor izquierdo
    private static Rotor reflector = new Rotor("YRUHQSLDPXNGOKMIEBFZCWVJAT", '-'); // reflector B
    private static Rotor reflectorAmpliado = new Rotor("vsUyTlYoiKZWtjVNkRIHDBh9c8zqf76S)xQ3rpM5J$\"2+g&*emCwaGXA(14%bu'PFO!0n dL#E", '-'); //reflector AMPLIADO
    private static Rotor reflectorAmpliadoPlus = new Rotor("bnD(gcF3SzpT6Q%#lRv70\"+V8J5eOoLxGN2)r$PiwAy1hfm'jX*t!4sCkYMHBq&KudWUE9ZaI", '-'); // reflector AMPLIADO Plus
    private static Clavijero plugboard; // tablero de clavijas
    public static int indiceP = 0;
    public static char[] pintar = new char[16];

    /**
     * Creación la maquina enigma
     *
     * @param rDe rotor derecho
     * @param rCen rotor medio
     * @param rIz rotor izquierdo
     */
    public Enigma(Rotor rIz, Rotor rCen, Rotor rDe) {
        this.rotorDerecha = rDe;
        this.rotorCentral = rCen;
        this.rotorIzquierda = rIz;
        this.plugboard = new Clavijero();
    }

    /**
     * Gira los rotores a la posición de sus claves
     *
     * @param claveDer Clave / posición inicial del rotor derecho
     * @param claveCen Clave / posición inicial del rotor medio
     * @param claveIzq Clave / posición inicial del rotor izquierdo
     */
    public void setRotoresIni(char claveIzq, char claveCen, char claveDer) {
        int posDer, posCen, posIzq;
        posDer = claveDer - 'A';
        posCen = claveCen - 'A';
        posIzq = claveIzq - 'A';
        this.rotorDerecha = this.rotorDerecha.giro(posDer);
        this.rotorCentral = this.rotorCentral.giro(posCen);
        this.rotorIzquierda = this.rotorIzquierda.giro(posIzq);
    }

    public void setRotoresIniAmpliado(char claveIzq, char claveCen, char claveDer, boolean plus) {
        int posDer, posCen, posIzq;
        posDer = plus ? posCheckPlus(claveDer) : posCheck(claveDer);
        posCen = plus ? posCheckPlus(claveCen) : posCheck(claveCen);
        posIzq = plus ? posCheckPlus(claveIzq) : posCheck(claveIzq);
        this.rotorDerecha = this.rotorDerecha.giroAmpliado(posDer, plus);
        this.rotorCentral = this.rotorCentral.giroAmpliado(posCen, plus);
        this.rotorIzquierda = this.rotorIzquierda.giroAmpliado(posIzq, plus);
    }

    /**
     * Comprueba las posiciones de la clave pasada; funcionalidad de los rotores
     * ampliados plus.
     *
     * @param clave
     * @return posición de la clave
     */
    private int posCheck(int clave) {
        int check = clave - '!';
        return check < 11 ? check : (check < 25 ? check - 4 : (check < 58 ? check - 11 : check - 17)); // simbolos : numeros : mayusculas : minusculas
    }

    private int posCheckPlus(int clave) {
        int check = clave - ' ';
        return check < 12 ? check : (check < 26 ? check - 4 : (check < 59 ? check - 11 : check - 17)); // simbolos : numeros : mayusculas : minusculas
    }

    /**
     * Actualiza la posicion de los rotores haciendo que giren 1 vez
     */
    private void actualizarRotores() {
        char claveActualDer, claveActualCen;
        claveActualDer = this.rotorDerecha.obtenerContEscritura().charAt(0); // obtiene la clave actual del rotor derecho
        claveActualCen = this.rotorCentral.obtenerContEscritura().charAt(0); // obtiene la clave actual del rotor central
        if (claveActualDer == this.rotorDerecha.obtenerPuntoGiro()) { // si la clave actual del rotor derecho es el punto de giro del rotor derecho
            if (claveActualCen == this.rotorCentral.obtenerPuntoGiro()) { // si la clave actual del rotor central es el punto de giro del rotor central
                this.rotorIzquierda = this.rotorIzquierda.giro(1); // gira rotor izquierdo
            }
            this.rotorCentral = this.rotorCentral.giro(1); // gira rotor central
        } else {
            if (claveActualCen == this.rotorCentral.obtenerPuntoGiro()) { // si la clave actual del rotor central es el punto de giro del rotor central
                this.rotorIzquierda = this.rotorIzquierda.giro(1); // gira rotor izquierdo
                this.rotorCentral = this.rotorCentral.giro(1); // gira rotor central
            }
        }
        this.rotorDerecha = this.rotorDerecha.giro(1); // gira rotor derecho
    }

    private void actualizarRotoresAmpliado(boolean plus) {
        char claveActualDer, claveActualCen;
        claveActualDer = plus ? this.rotorDerecha.obtenerContEscrituraAmpliadoPlus().charAt(0) : this.rotorDerecha.obtenerContEscrituraAmpliado().charAt(0); // obtiene la clave actual del rotor derecho
        claveActualCen = plus ? this.rotorCentral.obtenerContEscrituraAmpliadoPlus().charAt(0) : this.rotorCentral.obtenerContEscrituraAmpliado().charAt(0); // obtiene la clave actual del rotor central
        if (claveActualDer == this.rotorDerecha.obtenerPuntoGiro()) { // si la clave actual del rotor derecho es el punto de giro del rotor derecho
            if (claveActualCen == this.rotorCentral.obtenerPuntoGiro()) { // si la clave actual del rotor central es el punto de giro del rotor central
                this.rotorIzquierda = this.rotorIzquierda.giroAmpliado(1, plus); // gira rotor izquierdo
            }
            this.rotorCentral = this.rotorCentral.giroAmpliado(1, plus); // gira rotor central
        } else {
            if (claveActualCen == this.rotorCentral.obtenerPuntoGiro()) { // si la clave actual del rotor central es el punto de giro del rotor central
                this.rotorIzquierda = this.rotorIzquierda.giroAmpliado(1, plus); // gira rotor izquierdo
                this.rotorCentral = this.rotorCentral.giroAmpliado(1, plus); // gira rotor central
            }
        }
        this.rotorDerecha = this.rotorDerecha.giroAmpliado(1, plus); // gira rotor derecho
    }

    /**
     * Cifrado para la interfaz interactiva, revisar utilidad para usar esta
     * función o la de cifrado.
     *
     * @param c Caracter del mensaje para cifrar
     * @return Caracter cifrado del mensaje
     */
    public char cifradoBase(char c) {
        char aux;
        int i;
        indiceP = 0;
        pintar[indiceP] = c;
        indiceP++;
        actualizarRotores();
        i = this.entrada.obtenerContenido().indexOf(c); // obtiene el indice del caracter pasado
        i = this.rotorDerecha.cifrarIda(i);
        i = this.rotorCentral.cifrarIda(i);
        i = this.rotorIzquierda.cifrarIda(i);
        i = this.reflector.cifrarIda(i);            //Reflector
        i = this.rotorIzquierda.cifrarVuelta(i);
        i = this.rotorCentral.cifrarVuelta(i);
        i = this.rotorDerecha.cifrarVuelta(i);
        aux = this.entrada.obtenerContenido().charAt(i); // obtiene el caracter del indice obtenido
        pintar[indiceP] = aux;
        indiceP++;
        menu.setcI((char) this.rotorIzquierda.obtenerContEscritura().charAt(0));
        menu.setcC((char) this.rotorCentral.obtenerContEscritura().charAt(0));
        menu.setcD((char) this.rotorDerecha.obtenerContEscritura().charAt(0));
        return aux;
    }

    /**
     * Cifra el caracter pasado, ampliado y ampliado plus
     *
     * @param c Caracter del mensaje para cifrar
     * @return Caracter cifrado del mensaje
     */
    public char cifradoAmpliadoPlus(char c, boolean plus) {
        char aux;
        actualizarRotoresAmpliado(plus);
        int i;
        i = plus ? this.entradaAmpliadoPlus.obtenerContenido().indexOf(c) : this.entradaAmpliado.obtenerContenido().indexOf(c); // obtiene el indice del caracter pasado
        i = this.rotorDerecha.cifrarIdaAmpliado(i, plus);
        i = this.rotorCentral.cifrarIdaAmpliado(i, plus);
        i = this.rotorIzquierda.cifrarIdaAmpliado(i, plus);
        i = plus ? (modo == 0 ? this.reflectorAmpliadoPlus.cifrarIdaAmpliado(i, plus) : this.reflectorAmpliadoPlus.cifrarVueltaAmpliado(i, plus))
                : this.reflectorAmpliado.cifrarIdaAmpliado(i, plus); // modo = 0 -> Ampliado | modo = 1 -> Ampliado Plus
        i = this.rotorIzquierda.cifrarVueltaAmpliado(i, plus);
        i = this.rotorCentral.cifrarVueltaAmpliado(i, plus);
        i = this.rotorDerecha.cifrarVueltaAmpliado(i, plus);
        aux = plus ? this.entradaAmpliadoPlus.obtenerContenido().charAt(i) : this.entradaAmpliado.obtenerContenido().charAt(i); // obtiene el caracter del indice obtenido
        return aux;
    }

    /**
     * Pone la conexión de las clavijas en el alfabeto
     *
     * @param a clavija a
     * @param b clavija b
     */
    public void ponerClavija(char a, char b) {
        if (this.plugboard.establecerConexion(a, b)) {
            moverClavijas(a, b);
        }
    }

    /**
     * Elimina la conexión de las clavijas del alfabeto
     *
     * @param a clavija a
     * @param b clavija b
     */
    /*public void eliminarClavija(char a, char b) {
        if (this.plugboard.eliminarConexion(a, b)) {
            moverClavijas(a, b);
        }
    }*/
    /**
     * Mueve las clavijas en el alfabeto
     *
     * @param a clavija a
     * @param b clavija b
     */
    public void moverClavijas(char a, char b) {
        String aux = "", contenido;
        char susIzq, susDer, c;
        contenido = cifrado == 0 ? this.entrada.obtenerContenido() : this.entradaAmpliadoPlus.obtenerContenido();
        // ordena las clavijas
        susIzq = a < b ? a : b;
        susDer = a < b ? b : a;
        for (int i = 0; i < contenido.length(); i++) {
            c = contenido.charAt(i);
            aux += c == susIzq ? susDer : (c == susDer ? susIzq : c); // reordena el alfabeto de escritura
        }
        if (cifrado == 0) {
            this.entrada = new Rotor(aux, this.entrada.obtenerPuntoGiro());
        } else {
            this.entradaAmpliadoPlus = new Rotor(aux, this.entradaAmpliadoPlus.obtenerPuntoGiro());
        }
    }

    /**
     * Obtiene las conexiones de las clavijas.
     *
     * @return conexiones del clavijero
     */
    public Clavijero getPlugboard() {
        return plugboard;
    }

    /**
     * Configura las conexiones de las clavijas
     *
     * @param plugboard
     */
    public static void setPlugboard(Clavijero plugboard) {
        plugboard = plugboard;
    }

    public static void setReflectorAmpliado(Rotor reflectorAmpliado) {
        reflectorAmpliado = reflectorAmpliado;
    }

    public static void setReflectorAmpliadoPlus(Rotor reflectorAmpliadoPlus) {
        reflectorAmpliadoPlus = reflectorAmpliadoPlus;
    }
    
    
}
