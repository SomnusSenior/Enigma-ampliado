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
        //System.out.println("l: " + alfabeto.length());
        for (int i = 0; i < alfabeto.length(); i++) {
            alfabetoArray.add(alfabeto.charAt(i));
            r.add('.');
        }
        char chaM, cham, num, sym, c1 = '.', c2 = '.';
        int alea = 0, count = 0;
        String s = ".";
        boolean repetido = false;

        for (int i = 0; i < alfabetoArray.size() / 2; i++) {
            do {
                //System.out.println(".......... INICIO DO WHILE() .........");
                int iA = alea(alfabetoArray.size()); //(int) random() * alfabetoArray.size();
                int iB = alea(alfabetoArray.size()); //(int) random() * alfabetoArray.size();
                //System.out.println("size?: " + alfabetoArray.size());
                //System.out.println("alfabeto?: " + alfabetoArray.toString());
                //System.out.println("iA: " + iA + " iB: " + iB);
                /*count = 0;
                for (int j = 0; j < alfabeto.length(); j++) {
                    if(r.get(j) == '.'){
                        count++;
                    }
                }*/
                //System.out.println("r '.'?: " + count);
                // añadir if iA < iB, el menor indice se extrae primero y se resta 1 al segundo; Y REVISAR CODIGO COMPLETO PARA LA NUEVA CARACTERISTICA
                /*if (iB < iA) {
                    //System.out.println("<<<<<<<<<<< Entro comparación");
                    int aux = iA;
                    iA = iB;
                    iB = aux;
                }*/

                if (iA != iB) {
                    //System.out.println("----------------- Entro if");
                    repetido = false;
                    c1 = alfabetoArray.get(iA);
                    c2 = alfabetoArray.get(iB);

                    //r.set(alfabeto.indexOf(c2), c1);
                    //r.set(alfabeto.indexOf(c1), c2);
                    if (!r.contains(c1) && !r.contains(c2) && r.get(iA) == '.' && r.get(iB) == '.') {
                        //System.out.println("C1 y C2 NO están en r.");
                        r.set(iB, c1);
                        r.set(iA, c2);

                        //System.out.println("c1: " + c1 + " c2: " + c2);
                        //System.out.println("alfabeto antes?: " + alfabetoArray.toString());
                        //alfabetoArray.remove(iA);
                        alfabetoArray.set(iA,'.');
                        //System.out.println("alfabeto antes remove iB?: " + alfabetoArray.toString());
                        //alfabetoArray.remove(iB - 1);
                        alfabetoArray.set(iB,'.');
                        //System.out.println("alfabeto después?: " + alfabetoArray.toString());
                        //System.out.println("r?: " + r);
                    } else {
                        //System.out.println("C1 y C2 ESTÁN en r.");
                        repetido = true;
                    }
                } else {
                    //System.out.println(">>>>>>>>>>>>> Entro else");
                    repetido = true;
                }
            } while (repetido && !alfabetoArray.isEmpty());
        }
        for (char c : r) {
            resultado = resultado + c;
        }
        System.out.println("reflector: ");
        System.out.println(resultado);
        //System.out.print("teclado: ");
        /*for (int i = 0; i < alfabeto.length(); i++) {
            System.out.print("|" + alfabeto.charAt(i));
        }*/
        //System.out.println("");
        //System.out.println("en length " + alfabeto.length());
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
