package administrarpass;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author 49904022
 */
public class GeneradorContrasenas {

    public static int N = 20;   // Longitud de contraseña
    public static int rotor = 73; // Longitud NUEVOS rotores
    static Scanner scan = new Scanner(System.in);
    public static int symbolic = 0;

    public static void RunnerAleatorios() {
        longitudPregunta();
    }

    public static void longitudPregunta() {
        System.out.print("Definir longitud?: ");
        switch (scan.nextInt()) {
            case 0:
                N = 20;
                symbolPregunta();
                break;
            case 1:
                System.out.print("Longitud contraseña: ");
                N = scan.nextInt();
                symbolPregunta();
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
    }

    public static void symbolPregunta() {
        System.out.print("Con símbolos?: ");
        switch (scan.nextInt()) {
            case 0:
                symbolic = 3;
                aleatorio();
                break;
            case 1:
                symbolic = 4;
                aleatorio();
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
    }

    public static void aleatorio() {
        String pass = "";
        char chaM, cham, num, sym;
        int alea;

        for (int i = 0; i < N; i++) {
            chaM = randomCharM();   // Define MAYÚSCULAS
            cham = randomCharm();   // Define minúsculas
            num = randomNumber();   // Define números
            sym = randomSymbol();   // Define símbolos
            alea = alea(symbolic);  // Define siguiente caracter de la contraseña

            switch (alea) {
                case 0:
                    pass += sym;
                    break;
                case 1:
                    pass += chaM;
                    break;
                case 2:
                    pass += cham;
                    break;
                case 3:
                    pass += num;
                    break;
            }
        }
        System.out.println("Pass: " + pass);
    }

    public static void rotoresAleatorios() {
        String r = "";
        char chaM, cham, num, sym;
        int alea;
        String s = ".";
        boolean repetido;

        for (int i = 0; i < rotor; i++) {
            do {
                chaM = randomCharM();   // Define MAYÚSCULAS
                cham = randomCharm();   // Define minúsculas
                num = randomNumber();   // Define números
                sym = randomSymbol();   // Define símbolos
                alea = alea(4);          // Define siguiente caracter de la contraseña

                switch (alea) {
                    case 0:
                        s = Character.toString(sym);
                        break;
                    case 1:
                        s = Character.toString(chaM);
                        break;
                    case 2:
                        s = Character.toString(cham);
                        break;
                    case 3:
                        s = Character.toString(num);
                        break;
                }

                if (!r.contains(s)) {
                    r += s;
                    repetido = false;
                } else {
                    repetido = true;
                }
            } while (repetido);
        }
        System.out.println("rotor: " + r);
    }

    public static void rotoresReflector() {
        ArrayList<Character> r = new ArrayList<>();
        //String r = "..........................................................................";
        String alfabeto = " !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", resultado = "";
        ArrayList<Character> alfabetoArray = new ArrayList<>();
        for (int i = 0; i < alfabeto.length(); i++) {
            alfabetoArray.add(alfabeto.charAt(i));
            r.add('.');
        }
        char chaM, cham, num, sym, c1 = '.', c2 = '.';
        int alea;
        String s = ".";
        boolean repetido = false;

        for (int i = 0; i < rotor; i++) {
            do {
                int iA = (int) random() * alfabetoArray.size();
                int iB = (int) random() * alfabetoArray.size();

                // añadir if iA < iB, el menor indice se extrae primero y se resta 1 al segundo; Y REVISAR CODIGO COMPLETO PARA LA NUEVA CARACTERISTICA
                if (iA != iB) {
                    repetido = false;
                    //if (iA < iB) {
                    c1 = alfabetoArray.get(iA);
                    c2 = alfabetoArray.get(iB);
                    /*}else if(iB < iA){
                        c1 = alfabetoArray.get(iB);
                        c2 = alfabetoArray.get(iA);
                    }*/
                    alfabetoArray.remove(c1);
                    alfabetoArray.remove(c2);
                } else {
                    repetido = true;
                }
                r.set(iB, c1);
                r.set(iA, c2);
                System.out.println("r?: " + r);
            } while (repetido);
        }
        System.out.print("reflector: ");
        for (char c : r) {
            resultado = resultado + c;
        }
        System.out.println("resultado: " + resultado);
        System.out.println("");
        System.out.print("teclado: ");
        for (int i = 0; i < alfabeto.length(); i++) {
            System.out.print("|" + alfabeto.charAt(i));
        }
        System.out.println("en length " + alfabeto.length());
    }

    public static boolean checkArray(String[] r, String s) {
        for (int i = 0; i < r.length; i++) {
            if (r[i].equals(s)) {
                return false;
            }
        }
        return true;
    }

    public static int alea(int n) {
        return (int) (random() * n);
    }

    public static char randomNumber() {
        int Nr = (int) (random() * 10) + 48;
        return (char) Nr;
    }

    public static char randomCharM() {
        int Cr = (int) (random() * 26) + 65;
        return (char) Cr;
    }

    public static char randomCharm() {
        int cr = (int) (random() * 26) + 97;
        return (char) cr;
    }

    public static char randomSymbol() {
        int sr = (int) (random() * 11) + 33;
        return (char) sr;
    }

    public static char randomSymbol2() {
        int sr = (int) (random() * 12) + 32;
        return (char) sr;
    }
}
