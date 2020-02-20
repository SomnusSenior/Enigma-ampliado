package administrarpass;

import java.util.Scanner;

public class EjecutarEnigma {

    public static int modo;
    public static int cifrado;
    static Scanner scan = new Scanner(System.in);
    public static char cI, cC, cD;

    public static void RunnerEnigma() {
        if (cifrado == 0 || cifrado == 1) {
            RotoresPregunta();
        } else if (cifrado == 2) {
            ModoPregunta();
        } else {
            System.out.println("Error RunnerEnigma()");
        }
    }

    public static void ModoPregunta() {
        System.out.print("Modo: ");
        int n = scan.nextInt();
        if (n == 0 || n == 1) {
            modo = n;
            RotoresPregunta();
        } else {
            System.out.println("Opción incorrecta.");
        }
    }

    public static void RotoresPregunta() {
        Rotor r1 = null, r2 = null, r3 = null;
        System.out.print("Definir rotores?: ");
        switch (scan.nextInt()) {
            case 0:
                if (cifrado == 0) {
                    r1 = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q'); //tipo I Q
                    r2 = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E'); //tipo II E
                    r3 = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V'); //tipo III V
                } /*else if (cifrado == 1) {
                    r1;
                    r2;
                    r3;
                }*/ else if (cifrado == 2) {
                    r1 = new Rotor("#1q2OXy%x\"*(7GK+QPc$C4M5s!fEHrTiw8udRnFj&Y9)3aep0U6DBvAkJ'lIShgotmzZWVNLb", 'h'); //NUEVO tipo I h
                    r2 = new Rotor("R9(24Dpd%yKV0F!l#Jmz&gA)H87UQ3ErBSPv1TOc65'*n$LCwGsYa+WktMuf\"IeqhjXboiZNx", 'I'); //NUEVO tipo II I 
                    r3 = new Rotor("0xoOdh2G91DyXn+3kT8BP$(4\"V*#r6')R5%lbpt!ajigvCUYIsZN7zJWemwfcAFHuSEqKL&QM", 'i'); //NUEVO tipo III i
                }
                Runner(r1, r2, r3);
                break;
            case 1:
                System.out.print("Rotor 1 y giro: ");
                r1 = new Rotor(scan.next(), scan.next().charAt(0));
                System.out.print("Rotor 2 y giro: ");
                r2 = new Rotor(scan.next(), scan.next().charAt(0));
                System.out.print("Rotor 3 y giro: ");
                r3 = new Rotor(scan.next(), scan.next().charAt(0));

                Runner(r1, r2, r3);
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
    }

    public static void ClavijasPregunta(Enigma enigma) {
        System.out.print("Clavijas por default?: ");
        char c1, c2;
        switch (scan.nextInt()) {
            case 0:
                boolean repetir = false;
                do {
                    System.out.print("Clavija 1: ");
                    c1 = scan.next().charAt(0);
                    System.out.print("Clavija 2: ");
                    c2 = scan.next().charAt(0);
                    enigma.ponerClavija(c1, c2); // Inicializa las clavijas

                    System.out.print("Poner más clavijas?: ");
                    if (scan.nextInt() == 0) {
                        repetir = false;
                    } else {
                        repetir = true;
                    }
                } while (repetir);
                ClavesPregunta(enigma);
                break;
            case 1:
                enigma.ponerClavija('A', 'A');
                ClavesPregunta(enigma);
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
    }

    public static void ClavesPregunta(Enigma enigma) {
        System.out.print("Claves por default?: ");
        //char cI = 0, cC = 0, cD = 0;
        switch (scan.nextInt()) {
            case 0:
                System.out.print("Clave I: ");
                cI = scan.next().charAt(0);
                System.out.print("Clave C: ");
                cC = scan.next().charAt(0);
                System.out.print("Clave D: ");
                cD = scan.next().charAt(0);         // String index out of range: 73 ??????
                enigma.setRotorsIni(cI, cC, cD); // Inicializa las claves                
                break;
            case 1:
                cI = 'A';
                cC = 'B';
                cD = 'C';
                enigma.setRotorsIni(cI, cC, cD);
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
        //prueba.clavesRotores(cI, cC, cD);
        if (cifrado == 0) {
            base(enigma);
        } else {
            ampliado(enigma);
        }
    }

    public static void base(Enigma enigma) {
        modo = 0;

        System.out.print("A cifrar: ");
        String resultado = "", s = scan.next();

        resultado = procesar(enigma, s); // Obtiene el resultado de cifrar, la traducción
        System.out.println("Cifrado: " + resultado);

        //prueba.resta();
    }

    public static void ampliado(Enigma enigma) {
        System.out.println("A cifrar: ");
        String resultado = "", s = scan.next();

        resultado = procesarAmpliado(enigma, s); // Obtiene el resultado de cifrar, la traducción AMPLIADA
        System.out.println("Cifrado: " + resultado);
    }

    public static void Runner(Rotor r1, Rotor r2, Rotor r3) {
        Enigma enigma = new Enigma(r1, r2, r3); // Crea la máquina enigma
        ClavijasPregunta(enigma);
    }

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
            resultado += enigma.cifradoBase(c); //.cifrado(c);
        }
        return resultado;
    }

    public static String procesarAmpliado(Enigma enigma, String s) {
        String resultado = "";
        char c = '0';
        for (int i = 0; i < s.length(); i++) { // analiza la cadena de caracteres introducida
            c = s.charAt(i);
            if (s.isEmpty()) { // comprueba si no ha sido introducido un caracter
                System.exit(0);
            }
            resultado += enigma.cifrado(c);
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
        if ('Z' - c < 0) {
            return c = (char) (c + 'A' - 'a');
        } else {
            return c;
        }
    }
}
