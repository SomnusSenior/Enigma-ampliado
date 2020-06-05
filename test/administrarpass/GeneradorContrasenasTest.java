/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrarpass;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 49904022
 */
public class GeneradorContrasenasTest {

    /**
     * Test of rotoresAleatorios method, of class GeneradorContrasenas.
     */
    @Test
    public void testRotoresAleatoriosPlus() {
        // rotoresAleatorios Plus 
        boolean plus = true;
        int longExpect = 73;
        String rotor = GeneradorContrasenas.rotoresAleatoriosAmp(plus);
        assertEquals(longExpect, rotor.length());
    }

    /**
     * Test of rotoresAleatorios method, of class GeneradorContrasenas.
     */
    @Test
    public void testRotoresAleatoriosNoPlus() {
        // rotoresAleatorios no Plus
        boolean plus = false;
        int longExpect = 74;
        String rotor = GeneradorContrasenas.rotoresAleatoriosAmp(plus);
        assertEquals(longExpect, rotor.length());
    }

    /**
     * Test of rotoresReflector method, of class GeneradorContrasenas.
     */
    @Test
    public void testRotoresReflector() {
        // rotoresReflector
        String result = GeneradorContrasenas.rotorReflectorAmp();
        boolean boolResult = false;
        boolean expect = true;
        char cS;
        char cA;
        String alfabeto = " !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < result.length(); i++) {
            cS = result.charAt(i);
            cA = alfabeto.charAt(i);
            if (result.contains(Character.toString(cA))) {
                if (!(alfabeto.charAt(result.indexOf(cA)) == cS && result.charAt(alfabeto.indexOf(cS)) == cA)) {
                    boolResult = false;
                } else {
                    boolResult = true;
                }
            } else {
                boolResult = false;
            }
        }
        assertEquals(expect, boolResult);
    }

    /**
     * Test of alea method, of class GeneradorContrasenas.
     */
    @Test
    public void testAleatorioNoSymbol() {
        // aleatorio sin símbolos
        int n = 30;
        int sym = 3;
        boolean equal = false;
        GeneradorContrasenas.setn(n);
        GeneradorContrasenas.setSymbolic(sym);
        int longResult = n;
        String result = GeneradorContrasenas.aleatorio();
        assertEquals(longResult, result.length());
        for (int i = 0; i < result.length(); i++) {
            equal = ((result.charAt(i) >= 48 && result.charAt(i) <= 57)
                    || (result.charAt(i) >= 65 && result.charAt(i) <= 90)
                    || (result.charAt(i) >= 97 && result.charAt(i) <= 122));
        }
        assertEquals(true, equal);
    }

    /**
     * Test of alea method, of class GeneradorContrasenas.
     */
    @Test
    public void testAleatorioSymbol() {
        // aleatorio con símbolos
        int n = 30;
        int sym = 4;
        boolean equal = false;
        GeneradorContrasenas.setn(n);
        GeneradorContrasenas.setSymbolic(sym);
        int longResult = n;
        String result = GeneradorContrasenas.aleatorio();
        assertEquals(longResult, result.length());
        for (int i = 0; i < result.length(); i++) {
            equal = ((result.charAt(i) >= 33 && result.charAt(i) <= 43)
                    || (result.charAt(i) >= 48 && result.charAt(i) <= 57)
                    || (result.charAt(i) >= 65 && result.charAt(i) <= 90)
                    || (result.charAt(i) >= 97 && result.charAt(i) <= 122));
        }
        assertEquals(true, equal);
    }
}
