package administrarpass;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author 49904022
 */
public class GeneradorContrasenas {

    private static int n = 20;   // Longitud de contraseña
    private static int rotor = 73; // Longitud NUEVOS rotores Plus
    private static int rotorPlus = 74; // Longitud NUEVOS rotores Plus
    private static int symbolic = 0;
    private static Random random = new Random();

    /**
     * Constructor privado
     */
    private GeneradorContrasenas() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Genera una contraseña de forma aleatoria de longitud N y con o sin
     * símbolos según "symbolic"
     *
     * @return Contraseña aleatoria
     */
    public static String aleatorio() {
        StringBuilder pass = new StringBuilder();
        int alea;
        for (int i = 0; i < n; i++) {   // Crea pass
            alea = alea(symbolic);  // Define siguiente caracter de la contraseña
            switch (alea) {
                case 0:
                    pass.append(Character.toString(randomNumber()));   // Define números
                    break;
                case 1:
                    pass.append(Character.toString(randomCharMayus()));   // Define MAYÚSCULAS
                    break;
                case 2:
                    pass.append(Character.toString(randomCharMinus()));   // Define minúsculas
                    break;
                case 3:
                    pass.append(Character.toString(randomSymbol()));   // Define símbolos
                    break;
                default:
                    break;
            }
        }
        return pass.toString();
    }

    /**
     * Genera un rotor ampliado de forma aleatoria
     *
     * @param plus indica si el rotor es plus
     * @return rotor ampliado
     */
    public static String rotoresAleatoriosAmp(boolean plus) {
        StringBuilder r = new StringBuilder();
        String s = ".";
        int alea;
        int ro = plus ? rotor : rotorPlus; // 73 : 74
        boolean repetido;
        for (int i = 0; i < ro; i++) {  // Crea Rotor ampliado o Rotor/Reflector ampliado Plus
            do {
                alea = alea(4);          // Define siguiente caracter de la contraseña
                switch (alea) {
                    case 0:
                        s = plus ? Character.toString(randomSymbol()) : Character.toString(randomSymbol2());   // Define símbolos
                        break;
                    case 1:
                        s = Character.toString(randomCharMayus());   // Define MAYÚSCULAS
                        break;
                    case 2:
                        s = Character.toString(randomCharMinus());   // Define minúsculas
                        break;
                    case 3:
                        s = Character.toString(randomNumber());   // Define números
                        break;
                    default:
                        break;
                }
                if (!r.toString().contains(s)) {   // Comprueba si ya existe el caracter
                    r.append(s);
                    repetido = false;
                } else {
                    repetido = true;
                }
            } while (repetido);
        }
        return r.toString();
    }

    /**
     * Genera un reflector ampliado de forma aleatoria
     *
     * @return reflector ampliado
     */
    public static String rotorReflectorAmp() {
        char c1;
        char c2;
        boolean repetido = false;
        ArrayList<Character> r = new ArrayList<>();
        ArrayList<Character> alfabetoArray = new ArrayList<>();
        String alfabeto = " !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
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
        return transformToString(r);
    }

    /**
     * Transforma una lista de arrays de caracteres en un string
     *
     * @param lista
     * @return string de caracteres
     */
    private static String transformToString(ArrayList<Character> lista) {
        StringBuilder cadena = new StringBuilder();
        for (char c : lista) {  // Transforma el reflector en una cadena
            cadena.append(c);
        }
        return cadena.toString();
    }

    /**
     * Genera una opción de forma aleatoria
     *
     * @param n
     * @return opción
     */
    private static int alea(int n) {
        return random.nextInt(n);
    }

    /**
     * Genera un número de forma aleatoria
     *
     * @return número
     */
    private static char randomNumber() {
        return (char) (random.nextInt(10) + 48);
    }

    /**
     * Genera un caracter en mayúscula de forma aleatoria
     *
     * @return caracter en mayúscula
     */
    private static char randomCharMayus() {
        return (char) (random.nextInt(26) + 65);
    }

    /**
     * Genera un caracter en minúscula de forma aleatoria
     *
     * @return caracter en minúscula
     */
    private static char randomCharMinus() {
        return (char) (random.nextInt(26) + 97);
    }

    /**
     * Genera un símbolo de forma aleatoria incluyendo el "espacio" (bar space)
     *
     * @return símbolo
     */
    private static char randomSymbol() {
        return (char) (random.nextInt(11) + 33);
    }

    /**
     * Genera un símbolo de forma aleatoria
     *
     * @return símbolo
     */
    private static char randomSymbol2() {
        return (char) (random.nextInt(12) + 32);
    }

    /**
     * Configura la longitud de la cadena a generar
     *
     * @param n
     */
    public static void setn(int n) {
        GeneradorContrasenas.n = n;
    }

    /**
     * Configura el uso de símbolos para la cadena a generar
     *
     * @param symbolic
     */
    public static void setSymbolic(int symbolic) {
        GeneradorContrasenas.symbolic = symbolic;
    }
}
