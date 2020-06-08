/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarpass;

import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 49904022
 */
public class EjecutarEnigmaTest {

    /**
     * Test of traducción procesar method, of class EjecutarEnigma.
     */
    @Test
    public void testTraducir() {
        // traducir 
        Rotor rI = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q'); //tipo I Q
        Rotor rII = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E'); //tipo II E
        Rotor rIII = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V'); //tipo III V
        Rotor rIV = new Rotor("ESOVPZJAYQUIRHXLNFTGKDCMWB", 'J'); //tipo IV J
        Rotor rV = new Rotor("VZBRGITYUPSDNHLXAWMJQOFECK", 'Z'); //tipo V Z
        Rotor rIz;
        Rotor rCen;
        Rotor rDe;
        char cI;
        char cC;
        char cD;
        ArrayList<Rotor> list = new ArrayList<>();
        ArrayList<String> mensajes = new ArrayList<>();
        list.add(rI);
        list.add(rII);
        list.add(rIII);
        list.add(rIV);
        list.add(rV);
        Random random = new Random();
        Enigma enigma;
        int max = 1000;
        for (int i = 0; i < max; i++) {
            mensajes.add(generador(random, max));
        }
        String mensaje;
        String expResult;
        String result;
        for (int i = 0; i < mensajes.size(); i++) {
            mensaje = mensajes.get(i);
            rIz = list.get(random.nextInt(list.size()));
            rCen = list.get(random.nextInt(list.size()));
            rDe = list.get(random.nextInt(list.size()));
            cI = (char) (random.nextInt(26) + 65);
            cC = (char) (random.nextInt(26) + 65);
            cD = (char) (random.nextInt(26) + 65);

            enigma = new Enigma(rIz, rCen, rDe);
            enigma.setRotoresIni(cI, cC, cD);
            expResult = EjecutarEnigma.procesar(enigma, mensaje);

            enigma = new Enigma(rIz, rCen, rDe);
            enigma.setRotoresIni(cI, cC, cD);
            result = EjecutarEnigma.procesar(enigma, expResult);

            assertEquals(mensaje, result);
        }
    }

    private String generador(Random random, int max) {
        StringBuilder s = new StringBuilder();
        int num = random.nextInt(max);
        for (int j = 0; j < num; j++) {
            s.append(Character.toString(((char) (random.nextInt(26) + 65))));
        }
        return s.toString();
    }

    /**
     * Test of traducción procesar method, of class EjecutarEnigma.
     */
    @Test
    public void testTraducirAmp() {
        // traducir amp no plus
        boolean plus = false;
        Rotor rIzqAmp = new Rotor("4\"HIr(c36!5fs+kB28l#$mjO%tGAo)*1Md9Dy7'EhWJgavVS0exRN ZuF&PnqYipCUzLwTbKQX", 'X'); //NUEVO tipo Izq X
        Rotor rCenAmp = new Rotor("YZh!+76W&Ig%fiPsnUuSQC21v49q3B'5j)(TA8kGdmX$ O#0KbRJF*HNtEwc\"zVxaoerMplDyL", '\''); //NUEVO tipo Cen '
        Rotor rDerAmp = new Rotor("X14&w92 5!\"khP#Ij6*BdvLQGUE%p$ql7Fa3KuDn(Z8MbmTo'iHSWA0YCRytJ+OfzVsgN)cxre", 'J'); //NUEVO tipo Der J
        char cI;
        char cC;
        char cD;
        ArrayList<String> mensajes = new ArrayList<>();
        Random random = new Random();
        Enigma enigma;
        int max = 1000;
        for (int i = 0; i < max; i++) {
            mensajes.add(generadorAmpPlus(random, max, plus));
        }
        String mensaje;
        String expResult;
        String result;
        for (int i = 0; i < mensajes.size(); i++) {
            mensaje = mensajes.get(i);
            cI = (char) (random.nextInt(26) + 65);
            cC = (char) (random.nextInt(26) + 65);
            cD = (char) (random.nextInt(26) + 65);

            enigma = new Enigma(rIzqAmp, rCenAmp, rDerAmp);
            enigma.setRotoresIniAmpliado(cI, cC, cD, plus);
            expResult = EjecutarEnigma.procesarAmpliadoPlus(enigma, mensaje, plus);

            enigma = new Enigma(rIzqAmp, rCenAmp, rDerAmp);
            enigma.setRotoresIniAmpliado(cI, cC, cD, plus);
            result = EjecutarEnigma.procesarAmpliadoPlus(enigma, expResult, plus);

            assertEquals(mensaje, result);
        }
    }

    /**
     * Test of traducción procesar method, of class EjecutarEnigma.
     */
    @Test
    public void testTraducirAmpPlus() {
        // traducir amp plus
        boolean plus = true;
        Rotor rIzqAmpPlus = new Rotor("#1q2OXy%x\"*(7GK+QPc$C4M5s!fEHrTiw8udRnFj&Y9)3aep0U6DBvAkJ'lIShgotmzZWVNLb", 'h'); //NUEVO tipo Izq Plus h
        Rotor rCenAmpPlus = new Rotor("R9(24Dpd%yKV0F!l#Jmz&gA)H87UQ3ErBSPv1TOc65'*n$LCwGsYa+WktMuf\"IeqhjXboiZNx", 'I'); //NUEVO tipo Cen Plus I 
        Rotor rDerAmpPlus = new Rotor("0xoOdh2G91DyXn+3kT8BP$(4\"V*#r6')R5%lbpt!ajigvCUYIsZN7zJWemwfcAFHuSEqKL&QM", 'i'); //NUEVO tipo Der Plus i
        char cI;
        char cC;
        char cD;
        ArrayList<String> mensajes = new ArrayList<>();
        Random random = new Random();
        Enigma enigma;
        int max = 1000;
        for (int i = 0; i < max; i++) {
            mensajes.add(generadorAmpPlus(random, max, plus));
        }
        String mensaje;
        String expResult;
        String result;
        for (int i = 0; i < mensajes.size(); i++) {
            mensaje = mensajes.get(i);
            cI = (char) (random.nextInt(26) + 65);
            cC = (char) (random.nextInt(26) + 65);
            cD = (char) (random.nextInt(26) + 65);

            enigma = new Enigma(rIzqAmpPlus, rCenAmpPlus, rDerAmpPlus);
            enigma.setRotoresIniAmpliado(cI, cC, cD, plus);
            EjecutarEnigma.setModo(0);
            expResult = EjecutarEnigma.procesarAmpliadoPlus(enigma, mensaje, plus);

            enigma = new Enigma(rIzqAmpPlus, rCenAmpPlus, rDerAmpPlus);
            enigma.setRotoresIniAmpliado(cI, cC, cD, plus);
            EjecutarEnigma.setModo(1);
            result = EjecutarEnigma.procesarAmpliadoPlus(enigma, expResult, plus);

            assertEquals(mensaje, result);
        }
    }

    private static String generadorAmpPlus(Random random, int num, boolean plus) {
        StringBuilder r = new StringBuilder();
        String s = ".";
        int alea;
        for (int i = 0; i < num; i++) {
            alea = alea(random, 4);          // Define siguiente caracter de la contraseña
            switch (alea) {
                case 0:
                    s = plus ? Character.toString(randomSymbol(random)) : Character.toString(randomSymbol2(random));   // Define símbolos
                    break;
                case 1:
                    s = Character.toString(randomCharMayus(random));   // Define MAYÚSCULAS
                    break;
                case 2:
                    s = Character.toString(randomCharMinus(random));   // Define minúsculas
                    break;
                case 3:
                    s = Character.toString(randomNumber(random));   // Define números
                    break;
                default:
                    break;
            }
            r.append(s);
        }
        return r.toString();
    }

    private static int alea(Random random, int n) {
        return random.nextInt(n);
    }

    /**
     * Genera un número de forma aleatoria
     *
     * @return número
     */
    private static char randomNumber(Random random) {
        return (char) (random.nextInt(10) + 48);
    }

    /**
     * Genera un caracter en mayúscula de forma aleatoria
     *
     * @return caracter en mayúscula
     */
    private static char randomCharMayus(Random random) {
        return (char) (random.nextInt(26) + 65);
    }

    /**
     * Genera un caracter en minúscula de forma aleatoria
     *
     * @return caracter en minúscula
     */
    private static char randomCharMinus(Random random) {
        return (char) (random.nextInt(26) + 97);
    }

    /**
     * Genera un símbolo de forma aleatoria incluyendo el "espacio" (bar space)
     *
     * @return símbolo
     */
    private static char randomSymbol(Random random) {
        return (char) (random.nextInt(11) + 33);
    }

    /**
     * Genera un símbolo de forma aleatoria
     *
     * @return símbolo
     */
    private static char randomSymbol2(Random random) {
        return (char) (random.nextInt(12) + 32);
    }

    /**
     * Test of procesar method, of class EjecutarEnigma.
     */
    @Test
    public void testProcesar() {
        /// procesar
        Rotor rI = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q'); //tipo I Q
        Rotor rII = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E'); //tipo II E
        Rotor rIII = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V'); //tipo III V
        Enigma enigma = new Enigma(rI, rII, rIII);
        enigma.setRotoresIni('A', 'A', 'A');
        String s = "hola";
        String expResult = "IIBG";
        String result = EjecutarEnigma.procesar(enigma, s);
        assertEquals(expResult, result);
    }

    /**
     * Test of procesarAmpliadoPlus method, of class EjecutarEnigma.
     */
    @Test
    public void testProcesarAmpliadoNoPlus() {
        // procesarAmpliadoPlus
        Rotor rIzqAmp = new Rotor("4\"HIr(c36!5fs+kB28l#$mjO%tGAo)*1Md9Dy7'EhWJgavVS0exRN ZuF&PnqYipCUzLwTbKQX", 'X'); //NUEVO tipo Izq X
        Rotor rCenAmp = new Rotor("YZh!+76W&Ig%fiPsnUuSQC21v49q3B'5j)(TA8kGdmX$ O#0KbRJF*HNtEwc\"zVxaoerMplDyL", '\''); //NUEVO tipo Cen '
        Rotor rDerAmp = new Rotor("X14&w92 5!\"khP#Ij6*BdvLQGUE%p$ql7Fa3KuDn(Z8MbmTo'iHSWA0YCRytJ+OfzVsgN)cxre", 'J'); //NUEVO tipo Der J
        boolean plus = false;
        Enigma enigma = new Enigma(rIzqAmp, rCenAmp, rDerAmp, plus);
        enigma.setRotoresIniAmpliado('A', 'A', 'A', plus);
        String s = "ho la";
        String expResult = "(4vY)";
        String result = EjecutarEnigma.procesarAmpliadoPlus(enigma, s, plus);
        assertEquals(expResult, result);
    }

    /**
     * Test of procesarAmpliadoPlus method, of class EjecutarEnigma.
     */
    @Test
    public void testProcesarAmpliadoPlusCifrado() {
        // procesarAmpliadoPlus
        Rotor rIzqAmpPlus = new Rotor("#1q2OXy%x\"*(7GK+QPc$C4M5s!fEHrTiw8udRnFj&Y9)3aep0U6DBvAkJ'lIShgotmzZWVNLb", 'h'); //NUEVO tipo Izq Plus h
        Rotor rCenAmpPlus = new Rotor("R9(24Dpd%yKV0F!l#Jmz&gA)H87UQ3ErBSPv1TOc65'*n$LCwGsYa+WktMuf\"IeqhjXboiZNx", 'I'); //NUEVO tipo Cen Plus I 
        Rotor rDerAmpPlus = new Rotor("0xoOdh2G91DyXn+3kT8BP$(4\"V*#r6')R5%lbpt!ajigvCUYIsZN7zJWemwfcAFHuSEqKL&QM", 'i'); //NUEVO tipo Der Plus i
        boolean plus = true;
        Enigma enigma = new Enigma(rIzqAmpPlus, rCenAmpPlus, rDerAmpPlus, plus);
        enigma.setRotoresIniAmpliado('A', 'A', 'A', plus);
        EjecutarEnigma.setModo(0);
        String s = "hola";
        String expResult = "t'uR";
        String result = EjecutarEnigma.procesarAmpliadoPlus(enigma, s, plus);
        assertEquals(expResult, result);
    }

    /**
     * Test of procesarAmpliadoPlus method, of class EjecutarEnigma.
     */
    @Test
    public void testProcesarAmpliadoPlusDescifrado() {
        // procesarAmpliadoPlus
        Rotor rIzqAmpPlus = new Rotor("#1q2OXy%x\"*(7GK+QPc$C4M5s!fEHrTiw8udRnFj&Y9)3aep0U6DBvAkJ'lIShgotmzZWVNLb", 'h'); //NUEVO tipo Izq Plus h
        Rotor rCenAmpPlus = new Rotor("R9(24Dpd%yKV0F!l#Jmz&gA)H87UQ3ErBSPv1TOc65'*n$LCwGsYa+WktMuf\"IeqhjXboiZNx", 'I'); //NUEVO tipo Cen Plus I 
        Rotor rDerAmpPlus = new Rotor("0xoOdh2G91DyXn+3kT8BP$(4\"V*#r6')R5%lbpt!ajigvCUYIsZN7zJWemwfcAFHuSEqKL&QM", 'i'); //NUEVO tipo Der Plus i
        boolean plus = true;
        Enigma enigma = new Enigma(rIzqAmpPlus, rCenAmpPlus, rDerAmpPlus, plus);
        enigma.setRotoresIniAmpliado('A', 'A', 'A', plus);
        EjecutarEnigma.setModo(1);
        String s = "t'uR";
        String expResult = "hola";
        String result = EjecutarEnigma.procesarAmpliadoPlus(enigma, s, plus);
        assertEquals(expResult, result);
    }

    /**
     * Test of pasarMayus method, of class EjecutarEnigma.
     */
    @Test
    public void testPasarMayus() {
        // pasarMayus
        char c = ' ';
        char expResult = ' ';
        char result = EjecutarEnigma.pasarMayus(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of setModo method, of class EjecutarEnigma.
     */
    @Test
    public void testSetModo() {
        // setModo
        int expResult = 0;
        EjecutarEnigma.setModo(expResult);
        int result = EjecutarEnigma.getModo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getModo method, of class EjecutarEnigma.
     */
    @Test
    public void testGetModo() {
        // getModo
        int expResult = 0;
        EjecutarEnigma.setModo(expResult);
        int result = EjecutarEnigma.getModo();
        assertEquals(expResult, result);
    }
}
