package administrarpass;

public class EjecutarEnigma {

    private static int modo = 0;

    /**
     * Constructor privado.
     */
    private EjecutarEnigma() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Ejecución de la máquina Enigma
     *
     * @param enigma máquina enigma
     * @param s Cadena pasada a traducir, ya sea cifrar o descifrar
     * @return Regresa el resultado de la traducción de la cadena pasada
     */
    public static String procesar(Enigma enigma, String s) {
        StringBuilder resultado = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) { // analiza la cadena de caracteres introducida
            c = s.charAt(i);
            if (c >= '0' && c <= '9' || s.isEmpty()) { // comprueba si uno de los caracteres 
                // pasados es un número o si no ha sido introducido una letra
                System.exit(0);
            }
            c = pasarMayus(c); // transforma en mayúscula los caracteres
            resultado.append(enigma.cifrado(c));
        }
        return resultado.toString();
    }

    /**
     * Ejecución de la máquina Enigma
     *
     * @param enigma máquina enigma
     * @param s Cadena pasada a traducir, ya sea cifrar o descifrar
     * @param plus Indica si el alfabeto es plus
     * @return Regresa el resultado de la traducción de la cadena pasada
     */
    public static String procesarAmpliadoPlus(Enigma enigma, String s, boolean plus) {
        StringBuilder resultado = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) { // analiza la cadena de caracteres introducida
            c = s.charAt(i);
            if (s.isEmpty()) { // comprueba si no ha sido introducido un caracter
                System.exit(0);
            }
            resultado.append(enigma.cifradoAmpliadoPlus(c, plus));
        }
        return resultado.toString();
    }

    /**
     * Transforma en mayúscula el caracter pasado
     *
     * @param c caracter a transformar
     * @return caracter en mayúscula
     */
    public static char pasarMayus(char c) {
        return ('Z' - c < 0) ? (char) (c + 'A' - 'a') : c;
    }

    /**
     * Configurado el cifrado ida y el cifrado vuelta de enigma ampliada plus
     *
     * @param modo
     */
    public static void setModo(int modo) {
        EjecutarEnigma.modo = modo;
    }

    /**
     * Devuelve el valor de modo
     *
     * @return modo
     */
    public static int getModo() {
        return modo;
    }
}
