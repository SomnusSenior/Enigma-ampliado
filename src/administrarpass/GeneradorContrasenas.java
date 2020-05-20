package administrarpass;

import static java.lang.Math.random;
import java.util.ArrayList;

/**
 *
 * @author 49904022
 */
public class GeneradorContrasenas {

    private static int N = 20;   // Longitud de contraseña
    private static int rotor = 73; // Longitud NUEVOS rotores Plus
    private static int rotorPlus = 74; // Longitud NUEVOS rotores Plus
    private static int symbolic = 0;

    /**
     * Genera una contraseña de forma aleatoria de longitud N y con o sin
     * símbolos según "symbolic"
     *
     * @return Contraseña aleatoria
     */
    public static String aleatorio() {
        String pass = "";
        int alea;
        for (int i = 0; i < N; i++) {   // Crea pass
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
        return pass;
    }

    public static String rotoresAleatoriosAmp(boolean plus) {
        String r = "", s = ".";
        int alea;
        boolean repetido;
        for (int i = 0; i < (plus ? rotor : rotorPlus); i++) {  // Crea Rotor ampliado o Rotor/Reflector ampliado Plus
            do {
                alea = alea(4);          // Define siguiente caracter de la contraseña
                switch (alea) {
                    case 0:
                        s = plus ? Character.toString(randomSymbol()) : Character.toString(randomSymbol2());   // Define símbolos
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
                if (!r.contains(s)) {   // Comprueba si ya existe el caracter
                    r += s;
                    repetido = false;
                } else {
                    repetido = true;
                }
            } while (repetido);
        }
        return r;
    }

    public static String rotorReflectorAmp() {
        char c1 = '.', c2 = '.';
        boolean repetido = false;
        ArrayList<Character> r = new ArrayList<>(), alfabetoArray = new ArrayList<>();
        String alfabeto = " !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", resultado = "";
        for (int i = 0; i < alfabeto.length(); i++) {   // Crea arraylist de 74 '.'
            alfabetoArray.add(alfabeto.charAt(i));
            r.add('.');
        }
        for (int i = 0; i < alfabetoArray.size() / 2; i++) {    // Crea reflector con permutaciones a pares
            do {
                int iA = alea(alfabetoArray.size());
                int iB = alea(alfabetoArray.size());
                if (iA != iB) {
                    repetido = false;
                    c1 = alfabetoArray.get(iA);
                    c2 = alfabetoArray.get(iB);
                    if (!r.contains(c1) && !r.contains(c2) && r.get(iA) == '.' && r.get(iB) == '.') {   // Comprueba caracter no esté ya seleccionado
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
        for (char c : r) {  // Transforma el reflector en una cadena
            resultado = resultado + c;
        }
        return resultado;
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

    private static char randomSymbol2() {
        int sr = (int) (random() * 12) + 32;
        return (char) sr;
    }

    public static int getN() {
        return N;
    }

    public static void setN(int N) {
        GeneradorContrasenas.N = N;
    }

    public static int getSymbolic() {
        return symbolic;
    }

    public static void setSymbolic(int symbolic) {
        GeneradorContrasenas.symbolic = symbolic;
    }
}
