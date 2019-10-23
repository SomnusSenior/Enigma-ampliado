/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarpass;

import static administrarpass.EjecutarEnigma.RunnerEnigma;
import static administrarpass.EjecutarEnigma.cifrado;
import static administrarpass.GeneradorContrasenas.RunnerAleatorios;
import static administrarpass.GeneradorContrasenas.rotoresAleatorios;
import static administrarpass.GeneradorContrasenas.rotoresReflector;
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
        System.out.println("2. Aleatorio");
        System.out.println("3. Aleatorio rotores ampliados");
        System.out.println("4. Aleatorio rotor reflector");

        cifrado = scan.nextInt();
        switch (cifrado) {
            case 0:
                RunnerEnigma();
                break;
            case 1:
                RunnerEnigma();
                break;
            case 2:
                RunnerAleatorios();
                break;
            case 3:
                rotoresAleatorios();
                break;
            case 4:
                rotoresReflector();
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }

    }
}
