package administrarpass;

import static administrarpass.EjecutarEnigma.getModo;

public class Enigma {

    private Rotor entrada = new Rotor("ABCDEFGHIJKLMNOPQRSTUVWXYZ", '-'); // rotor entrada
    private Rotor entradaAmpliado = new Rotor(" !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", '-'); // rotor entrada AMPLIADO
    private Rotor entradaAmpliadoPlus = new Rotor("!\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", '-'); // rotor entrada AMPLIADO Plus
    private Rotor rotorDerecha; // rotor derecho
    private Rotor rotorCentral; // rotor medio
    private Rotor rotorIzquierda; // rotor izquierdo
    private Rotor reflector = new Rotor("YRUHQSLDPXNGOKMIEBFZCWVJAT", '-'); // reflector B
    private Rotor reflectorAmpliado = new Rotor("vsUyTlYoiKZWtjVNkRIHDBh9c8zqf76S)xQ3rpM5J$\"2+g&*emCwaGXA(14%bu'PFO!0n dL#E", '-'); //reflector AMPLIADO
    private Rotor reflectorAmpliadoPlus = new Rotor("bnD(gcF3SzpT6Q%#lRv70\"+V8J5eOoLxGN2)r$PiwAy1hfm'jX*t!4sCkYMHBq&KudWUE9ZaI", '-'); // reflector AMPLIADO Plus
    private Clavijero plugboard; // tablero de clavijas
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
     * Creación la maquina enigma plus
     *
     * @param rDe rotor derecho
     * @param rCen rotor medio
     * @param rIz rotor izquierdo
     * @param plus indica si los rotores son plus
     */
    public Enigma(Rotor rIz, Rotor rCen, Rotor rDe, boolean plus) {
        this.rotorDerecha = rDe;
        this.rotorCentral = rCen;
        this.rotorIzquierda = rIz;
        this.plugboard = new Clavijero(plus);
    }

    /**
     * Gira los rotores a la posición de sus claves
     *
     * @param claveDer Clave / posición inicial del rotor derecho
     * @param claveCen Clave / posición inicial del rotor medio
     * @param claveIzq Clave / posición inicial del rotor izquierdo
     */
    public void setRotoresIni(char claveIzq, char claveCen, char claveDer) {
        int posDer = claveDer - 'A';
        int posCen = claveCen - 'A';
        int posIzq = claveIzq - 'A';
        this.rotorDerecha = this.rotorDerecha.giro(posDer);
        this.rotorCentral = this.rotorCentral.giro(posCen);
        this.rotorIzquierda = this.rotorIzquierda.giro(posIzq);
    }

    /**
     * Gira los rotores a la posición de sus claves
     *
     * @param claveDer Clave / posición inicial del rotor derecho
     * @param claveCen Clave / posición inicial del rotor medio
     * @param claveIzq Clave / posición inicial del rotor izquierdo
     * @param plus indica si los rotores son plua
     */
    public void setRotoresIniAmpliado(char claveIzq, char claveCen, char claveDer, boolean plus) {
        int posDer = plus ? posCheckPlus(claveDer) : posCheck(claveDer);
        int posCen = plus ? posCheckPlus(claveCen) : posCheck(claveCen);
        int posIzq = plus ? posCheckPlus(claveIzq) : posCheck(claveIzq);
        this.rotorDerecha = this.rotorDerecha.giroAmpliado(posDer, plus);
        this.rotorCentral = this.rotorCentral.giroAmpliado(posCen, plus);
        this.rotorIzquierda = this.rotorIzquierda.giroAmpliado(posIzq, plus);
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
    private void actualizarRotores() {
        char claveActualDer = this.rotorDerecha.obtenerContEscritura().charAt(0); // obtiene la clave actual del rotor derecho
        char claveActualCen = this.rotorCentral.obtenerContEscritura().charAt(0); // obtiene la clave actual del rotor central
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

    /**
     * Actualiza la posicion de los rotores ampliados haciendo que giren 1 vez
     *
     * @param plus indica si es un rotor ampliado plus
     */
    private void actualizarRotoresAmpliado(boolean plus) {
        char claveActualDer = plus ? this.rotorDerecha.obtenerContEscrituraAmpliadoPlus().charAt(0) : this.rotorDerecha.obtenerContEscrituraAmpliado().charAt(0); // obtiene la clave actual del rotor derecho
        char claveActualCen = plus ? this.rotorCentral.obtenerContEscrituraAmpliadoPlus().charAt(0) : this.rotorCentral.obtenerContEscrituraAmpliado().charAt(0); // obtiene la clave actual del rotor central
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
    public char cifrado(char c) {
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
        Menu.setcI(this.rotorIzquierda.obtenerContEscritura().charAt(0));
        Menu.setcC(this.rotorCentral.obtenerContEscritura().charAt(0));
        Menu.setcD(this.rotorDerecha.obtenerContEscritura().charAt(0));
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
        i = plus ? this.entradaAmpliadoPlus.obtenerContenido().indexOf(c) : this.entradaAmpliado.obtenerContenido().indexOf(c); // obtiene el indice del caracter pasado
        i = this.rotorDerecha.cifrarIdaAmpliado(i, plus);
        i = this.rotorCentral.cifrarIdaAmpliado(i, plus);
        i = this.rotorIzquierda.cifrarIdaAmpliado(i, plus);
        i = plus ? aux(i, plus) : this.reflectorAmpliado.cifrarIdaAmpliado(i, plus); // modo = 0 | modo = 1 -> Ampliado Plus
        i = this.rotorIzquierda.cifrarVueltaAmpliado(i, plus);
        i = this.rotorCentral.cifrarVueltaAmpliado(i, plus);
        i = this.rotorDerecha.cifrarVueltaAmpliado(i, plus);
        return plus ? this.entradaAmpliadoPlus.obtenerContenido().charAt(i) : this.entradaAmpliado.obtenerContenido().charAt(i); // obtiene el caracter del indice obtenido
    }

    /**
     * Función auxiliar para el cifrado
     * @param i índice caracter
     * @param plus indica si es plus
     * @return índice character cifrado
     */
    private int aux(int i, boolean plus) {
        return getModo() == 0 ? this.reflectorAmpliadoPlus.cifrarIdaAmpliado(i, plus) : this.reflectorAmpliadoPlus.cifrarVueltaAmpliado(i, plus);
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
     * Pone la conexión de las clavijas en el alfabeto ampliado
     *
     * @param a clavija a
     * @param b clavija b
     * @param plus indica si es plus
     */
    public void ponerClavijaAmpliado(char a, char b, boolean plus) {
        if (this.plugboard.establecerConexion(a, b)) {
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
        String contenido = plus ? this.entradaAmpliadoPlus.obtenerContenido() : this.entradaAmpliado.obtenerContenido();
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
    public void setPlugboard(Clavijero plugboard) {
        this.plugboard = plugboard;
    }

    /**
     * Configura el reflector ampliado
     *
     * @param reflectorAmpliado
     */
    public void setReflectorAmpliado(Rotor reflectorAmpliado) {
        this.reflectorAmpliado = reflectorAmpliado;
    }

    /**
     * Configura el reflector ampliado plus
     *
     * @param reflectorAmpliadoPlus
     */
    public void setReflectorAmpliadoPlus(Rotor reflectorAmpliadoPlus) {
        this.reflectorAmpliadoPlus = reflectorAmpliadoPlus;
    }

}
