package administrarpass;

import static administrarpass.EjecutarEnigma.getModo;

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
        Enigma.rotorDerecha = rDe;
        Enigma.rotorCentral = rCen;
        Enigma.rotorIzquierda = rIz;
        Enigma.plugboard = new Clavijero();
    }

    /**
     * Creación la maquina enigma plus
     *
     * @param rDe rotor derecho
     * @param rCen rotor medio
     * @param rIz rotor izquierdo
     * @param plus indica si los rotores son plus
     */
    public Enigma(Rotor rIz, Rotor rCen, Rotor rDe, boolean plus) {
        Enigma.rotorDerecha = rDe;
        Enigma.rotorCentral = rCen;
        Enigma.rotorIzquierda = rIz;
        Enigma.plugboard = new Clavijero(plus);
    }

    /**
     * Gira los rotores a la posición de sus claves
     *
     * @param claveDer Clave / posición inicial del rotor derecho
     * @param claveCen Clave / posición inicial del rotor medio
     * @param claveIzq Clave / posición inicial del rotor izquierdo
     */
    public static void setRotoresIni(char claveIzq, char claveCen, char claveDer) {
        int posDer = claveDer - 'A';
        int posCen = claveCen - 'A';
        int posIzq = claveIzq - 'A';
        Enigma.rotorDerecha = Enigma.rotorDerecha.giro(posDer);
        Enigma.rotorCentral = Enigma.rotorCentral.giro(posCen);
        Enigma.rotorIzquierda = Enigma.rotorIzquierda.giro(posIzq);
    }

    /**
     * Gira los rotores a la posición de sus claves
     *
     * @param claveDer Clave / posición inicial del rotor derecho
     * @param claveCen Clave / posición inicial del rotor medio
     * @param claveIzq Clave / posición inicial del rotor izquierdo
     * @param plus indica si los rotores son plua
     */
    public static void setRotoresIniAmpliado(char claveIzq, char claveCen, char claveDer, boolean plus) {
        int posDer = plus ? posCheckPlus(claveDer) : posCheck(claveDer);
        int posCen = plus ? posCheckPlus(claveCen) : posCheck(claveCen);
        int posIzq = plus ? posCheckPlus(claveIzq) : posCheck(claveIzq);
        Enigma.rotorDerecha = Enigma.rotorDerecha.giroAmpliado(posDer, plus);
        Enigma.rotorCentral = Enigma.rotorCentral.giroAmpliado(posCen, plus);
        Enigma.rotorIzquierda = Enigma.rotorIzquierda.giroAmpliado(posIzq, plus);
    }

    /**
     * Comprueba las posiciones de la clave pasada; funcionalidad de los rotores
     * ampliados.
     *
     * @param clave
     * @return posición de la clave
     */
    private static int posCheck(int clave) {
        int check = clave - '!';
        int letras = check < 58 ? check - 11 : check - 17; // mayusculas : minusculas
        int numeros = check < 25 ? check - 4 : letras; // numeros : 
        return check < 11 ? check : numeros; // simbolos : 
    }

    /**
     * Comprueba las posiciones de la clave pasada; funcionalidad de los rotores
     * ampliados plus.
     *
     * @param clave
     * @return posición de la clave
     */
    private static int posCheckPlus(int clave) {
        int check = clave - ' ';
        int letras = check < 59 ? check - 11 : check - 17; // mayusculas : minusculas
        int numeros = check < 26 ? check - 4 : letras; // numeros : 
        return check < 12 ? check : numeros; // simbolos : 
    }

    /**
     * Actualiza la posicion de los rotores haciendo que giren 1 vez
     */
    private static void actualizarRotores() {
        char claveActualDer = Enigma.rotorDerecha.obtenerContEscritura().charAt(0); // obtiene la clave actual del rotor derecho
        char claveActualCen = Enigma.rotorCentral.obtenerContEscritura().charAt(0); // obtiene la clave actual del rotor central
        if (claveActualDer == Enigma.rotorDerecha.obtenerPuntoGiro()) { // si la clave actual del rotor derecho es el punto de giro del rotor derecho
            if (claveActualCen == Enigma.rotorCentral.obtenerPuntoGiro()) { // si la clave actual del rotor central es el punto de giro del rotor central
                Enigma.rotorIzquierda = Enigma.rotorIzquierda.giro(1); // gira rotor izquierdo
            }
            Enigma.rotorCentral = Enigma.rotorCentral.giro(1); // gira rotor central
        } else {
            if (claveActualCen == Enigma.rotorCentral.obtenerPuntoGiro()) { // si la clave actual del rotor central es el punto de giro del rotor central
                Enigma.rotorIzquierda = Enigma.rotorIzquierda.giro(1); // gira rotor izquierdo
                Enigma.rotorCentral = Enigma.rotorCentral.giro(1); // gira rotor central
            }
        }
        Enigma.rotorDerecha = Enigma.rotorDerecha.giro(1); // gira rotor derecho
    }

    /**
     * Actualiza la posicion de los rotores ampliados haciendo que giren 1 vez
     *
     * @param plus indica si es un rotor ampliado plus
     */
    private static void actualizarRotoresAmpliado(boolean plus) {
        char claveActualDer = plus ? Enigma.rotorDerecha.obtenerContEscrituraAmpliadoPlus().charAt(0) : Enigma.rotorDerecha.obtenerContEscrituraAmpliado().charAt(0); // obtiene la clave actual del rotor derecho
        char claveActualCen = plus ? Enigma.rotorCentral.obtenerContEscrituraAmpliadoPlus().charAt(0) : Enigma.rotorCentral.obtenerContEscrituraAmpliado().charAt(0); // obtiene la clave actual del rotor central
        if (claveActualDer == Enigma.rotorDerecha.obtenerPuntoGiro()) { // si la clave actual del rotor derecho es el punto de giro del rotor derecho
            if (claveActualCen == Enigma.rotorCentral.obtenerPuntoGiro()) { // si la clave actual del rotor central es el punto de giro del rotor central
                Enigma.rotorIzquierda = Enigma.rotorIzquierda.giroAmpliado(1, plus); // gira rotor izquierdo
            }
            Enigma.rotorCentral = Enigma.rotorCentral.giroAmpliado(1, plus); // gira rotor central
        } else {
            if (claveActualCen == Enigma.rotorCentral.obtenerPuntoGiro()) { // si la clave actual del rotor central es el punto de giro del rotor central
                Enigma.rotorIzquierda = Enigma.rotorIzquierda.giroAmpliado(1, plus); // gira rotor izquierdo
                Enigma.rotorCentral = Enigma.rotorCentral.giroAmpliado(1, plus); // gira rotor central
            }
        }
        Enigma.rotorDerecha = Enigma.rotorDerecha.giroAmpliado(1, plus); // gira rotor derecho
    }

    /**
     * Cifrado para la interfaz interactiva, revisar utilidad para usar esta
     * función o la de cifrado.
     *
     * @param c Caracter del mensaje para cifrar
     * @return Caracter cifrado del mensaje
     */
    public char cifrado(char c) {
        char aux;
        int i;
        indiceP = 0;
        pintar[indiceP] = c;
        indiceP++;
        actualizarRotores();
        i = this.entrada.obtenerContenido().indexOf(c); // obtiene el indice del caracter pasado
        i = Enigma.rotorDerecha.cifrarIda(i);
        i = Enigma.rotorCentral.cifrarIda(i);
        i = Enigma.rotorIzquierda.cifrarIda(i);
        i = Enigma.reflector.cifrarIda(i);            //Reflector
        i = Enigma.rotorIzquierda.cifrarVuelta(i);
        i = Enigma.rotorCentral.cifrarVuelta(i);
        i = Enigma.rotorDerecha.cifrarVuelta(i);
        aux = this.entrada.obtenerContenido().charAt(i); // obtiene el caracter del indice obtenido
        pintar[indiceP] = aux;
        indiceP++;
        Menu.setcI((char) Enigma.rotorIzquierda.obtenerContEscritura().charAt(0));
        Menu.setcC((char) Enigma.rotorCentral.obtenerContEscritura().charAt(0));
        Menu.setcD((char) Enigma.rotorDerecha.obtenerContEscritura().charAt(0));
        return aux;
    }

    /**
     * Cifra el caracter pasado, ampliado y ampliado plus
     *
     * @param c Caracter del mensaje para cifrar
     * @param plus indica si es plus
     * @return Caracter cifrado del mensaje
     */
    public char cifradoAmpliadoPlus(char c, boolean plus) {
        actualizarRotoresAmpliado(plus);
        int i;
        int aux;
        i = plus ? this.entradaAmpliadoPlus.obtenerContenido().indexOf(c) : this.entradaAmpliado.obtenerContenido().indexOf(c); // obtiene el indice del caracter pasado
        i = Enigma.rotorDerecha.cifrarIdaAmpliado(i, plus);
        i = Enigma.rotorCentral.cifrarIdaAmpliado(i, plus);
        i = Enigma.rotorIzquierda.cifrarIdaAmpliado(i, plus);
        aux = getModo() == 0 ? Enigma.reflectorAmpliadoPlus.cifrarIdaAmpliado(i, plus) : Enigma.reflectorAmpliadoPlus.cifrarVueltaAmpliado(i, plus);
        i = plus ? aux : Enigma.reflectorAmpliado.cifrarIdaAmpliado(i, plus); // modo = 0 | modo = 1 -> Ampliado Plus
        i = Enigma.rotorIzquierda.cifrarVueltaAmpliado(i, plus);
        i = Enigma.rotorCentral.cifrarVueltaAmpliado(i, plus);
        i = Enigma.rotorDerecha.cifrarVueltaAmpliado(i, plus);
        return plus ? this.entradaAmpliadoPlus.obtenerContenido().charAt(i) : this.entradaAmpliado.obtenerContenido().charAt(i); // obtiene el caracter del indice obtenido
    }

    /**
     * Pone la conexión de las clavijas en el alfabeto
     *
     * @param a clavija a
     * @param b clavija b
     */
    public void ponerClavija(char a, char b) {
        if (Enigma.plugboard.establecerConexion(a, b)) {
            moverClavijas(a, b);
        }
    }

    /**
     * Pone la conexión de las clavijas en el alfabeto ampliado
     *
     * @param a clavija a
     * @param b clavija b
     * @param plus indica si es plus
     */
    public void ponerClavijaAmpliado(char a, char b, boolean plus) {
        if (Enigma.plugboard.establecerConexion(a, b)) {
            moverClavijasAmpliado(a, b, plus);
        }
    }

    /**
     * Mueve las clavijas en el alfabeto
     *
     * @param a clavija a
     * @param b clavija b
     */
    public void moverClavijas(char a, char b) {
        StringBuilder aux = new StringBuilder();
        String contenido = this.entrada.obtenerContenido();
        // ordena las clavijas        
        char susIzq = a < b ? a : b;
        char susDer = a < b ? b : a;
        char c;
        char mew;
        for (int i = 0; i < contenido.length(); i++) {
            c = contenido.charAt(i);
            mew = c == susDer ? susIzq : c;
            aux.append(c == susIzq ? susDer : mew); // reordena el alfabeto de escritura
        }
        this.entrada = new Rotor(aux.toString(), this.entrada.obtenerPuntoGiro());
    }

    /**
     * Mueve las clavijas en el alfabeto
     *
     * @param a clavija a
     * @param b clavija b
     * @param plus indica si es plus
     */
    public void moverClavijasAmpliado(char a, char b, boolean plus) {
        StringBuilder aux = new StringBuilder();
        String contenido = plus ? this.entrada.obtenerContenido() : this.entrada.obtenerContenido();
        // ordena las clavijas
        char susIzq = a < b ? a : b;
        char susDer = a < b ? b : a;
        char c;
        char mew;
        for (int i = 0; i < contenido.length(); i++) {
            c = contenido.charAt(i);
            mew = c == susDer ? susIzq : c;
            aux.append(c == susIzq ? susDer : (mew)); // reordena el alfabeto de escritura
        }
        if (plus) {
            this.entradaAmpliadoPlus = new Rotor(aux.toString(), this.entradaAmpliadoPlus.obtenerPuntoGiro());
        } else {
            this.entradaAmpliado = new Rotor(aux.toString(), this.entradaAmpliado.obtenerPuntoGiro());
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
        Enigma.plugboard = plugboard;
    }

    /**
     * Configura el reflector ampliado
     *
     * @param reflectorAmpliado
     */
    public static void setReflectorAmpliado(Rotor reflectorAmpliado) {
        Enigma.reflectorAmpliado = reflectorAmpliado;
    }

    /**
     * Configura el reflector ampliado plus
     *
     * @param reflectorAmpliadoPlus
     */
    public static void setReflectorAmpliadoPlus(Rotor reflectorAmpliadoPlus) {
        Enigma.reflectorAmpliadoPlus = reflectorAmpliadoPlus;
    }

}
