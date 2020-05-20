package administrarpass;

import java.util.Scanner;

public class EjecutarEnigma {

    public static int modo;
    public static int cifrado;
    static Scanner scan = new Scanner(System.in);
    public static char cI, cC, cD;

    /**
     * Ejecución de la máquina Enigma
     *
     * @param enigma máquina enigma
     * @param s Cadena pasada a traducir, ya sea cifrar o descifrar
     * @return Regresa el resultado de la traducción de la cadena pasada
     */
    public static String procesar(Enigma enigma, String s) {
        String resultado = "";
        char c = '0';
        for (int i = 0; i < s.length(); i++) { // analiza la cadena de caracteres introducida
            c = s.charAt(i);
            if (c >= '0' && c <= '9' || s.isEmpty()) { // comprueba si uno de los caracteres 
                // pasados es un número o si no ha sido introducido una letra
                System.exit(0);
            }
            c = pasarMayus(c); // transforma en mayúscula los caracteres
            resultado += enigma.cifrado(c); //.cifrado(c);
        }
        return resultado;
    }

    public static String procesarAmpliadoPlus(Enigma enigma, String s, boolean plus) {
        String resultado = "";
        char c = '0';
        for (int i = 0; i < s.length(); i++) { // analiza la cadena de caracteres introducida
            c = s.charAt(i);
            if (s.isEmpty()) { // comprueba si no ha sido introducido un caracter
                System.exit(0);
            }
            resultado += enigma.cifradoAmpliadoPlus(c, plus);
        }
        return resultado;
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
}
