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
    public static int rotor = 73; // Longitud NUEVOS rotores Plus
    static Scanner scan = new Scanner(System.in);
    public static int symbolic = 0;

    public static void RunnerAleatorios() {
        longitudPregunta();
    }

    private static void longitudPregunta() {
        System.out.print("Definir longitud?: ");
        switch (scan.nextInt()) {
            case 0:
                break;
            case 1:
                System.out.print("Longitud contraseña: ");
                N = scan.nextInt();
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
        symbolPregunta();
    }

    private static void symbolPregunta() {
        System.out.print("Con símbolos?: ");
        switch (scan.nextInt()) {
            case 0:
                symbolic = 3;
                break;
            case 1:
                symbolic = 4;
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
        aleatorio();
    }

    private static void aleatorio() {
        String pass = "";
        int alea;
        for (int i = 0; i < N; i++) {
            alea = alea(symbolic);  // Define siguiente caracter de la contraseña
            switch (alea) {
                case 0:
                    pass += randomNumber();   // Define números
                    break;
                case 1:
                    pass += randomCharM();   // Define MAYÚSCULAS
                    break;
                case 2:
                    pass += randomCharm();   // Define minúsculas
                    break;
                case 3:
                    pass += randomSymbol();   // Define símbolos
                    break;
            }
        }
        System.out.println("Pass: " + pass);
    }

    public static void rotoresAleatorios() {
        String r = "";
        int alea;
        String s = ".";
        boolean repetido;
        for (int i = 0; i < rotor; i++) {
            do {
                alea = alea(4);          // Define siguiente caracter de la contraseña
                switch (alea) {
                    case 0:
                        s = Character.toString(randomSymbol());   // Define símbolos
                        break;
                    case 1:
                        s = Character.toString(randomCharM());   // Define MAYÚSCULAS
                        break;
                    case 2:
                        s = Character.toString(randomCharm());   // Define minúsculas
                        break;
                    case 3:
                        s = Character.toString(randomNumber());   // Define números
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
        System.out.println("Rotor: " + r);
    }

    public static void rotoresReflector() {
        char c1 = '.', c2 = '.';
        boolean repetido = false;
        ArrayList<Character> r = new ArrayList<>();
        String alfabeto = " !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", resultado = "";
        ArrayList<Character> alfabetoArray = new ArrayList<>();
        for (int i = 0; i < alfabeto.length(); i++) {
            alfabetoArray.add(alfabeto.charAt(i));
            r.add('.');
        }
        for (int i = 0; i < alfabetoArray.size() / 2; i++) {
            do {
                int iA = alea(alfabetoArray.size());
                int iB = alea(alfabetoArray.size());
                if (iA != iB) {
                    repetido = false;
                    c1 = alfabetoArray.get(iA);
                    c2 = alfabetoArray.get(iB);
                    if (!r.contains(c1) && !r.contains(c2) && r.get(iA) == '.' && r.get(iB) == '.') {
                        r.set(iB, c1);
                        r.set(iA, c2);
                        alfabetoArray.set(iA, '.');
                        alfabetoArray.set(iB, '.');
                    } else {
                        repetido = true;
                    }
                } else {
                    repetido = true;
                }
            } while (repetido && !alfabetoArray.isEmpty());
        }
        for (char c : r) {
            resultado = resultado + c;
        }
        System.out.println("Reflector: ");
        System.out.println(resultado);
    }

    private static int alea(int n) {
        return (int) (random() * n);
    }

    private static char randomNumber() {
        int Nr = (int) (random() * 10) + 48;
        return (char) Nr;
    }

    private static char randomCharM() {
        int Cr = (int) (random() * 26) + 65;
        return (char) Cr;
    }

    private static char randomCharm() {
        int cr = (int) (random() * 26) + 97;
        return (char) cr;
    }

    private static char randomSymbol() {
        int sr = (int) (random() * 11) + 33;
        return (char) sr;
    }

    public static char randomSymbol2() {
        int sr = (int) (random() * 12) + 32;
        return (char) sr;
    }
}
