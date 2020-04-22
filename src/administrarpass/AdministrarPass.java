package administrarpass;

import static administrarpass.EjecutarEnigma.cifrado;
import java.util.Scanner;

/**
 *
 * @author 49904022
 */
public class AdministrarPass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Selecciona una opción:");
        System.out.println("0. Enigma base");
        System.out.println("1. Enigma ampliado");
        System.out.println("2. Enigma 'ampliado' Plus");
//        System.out.println("3. Aleatorio (pasado al menu)");
//        System.out.println("4. Aleatorio rotores ampliados (sin funcionamiento)");
//        System.out.println("5. Aleatorio rotores 'ampliados' Plus (pasado al menu)");
//        System.out.println("6. Aleatorio rotor reflector (pasado al menu)");

        cifrado = scan.nextInt();
        switch (cifrado) {
            case 0:
            case 1:
            case 2:
                //RunnerEnigma();
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
    }
}
