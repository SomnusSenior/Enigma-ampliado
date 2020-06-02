///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package administrarpass;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.util.HashMap;
//import java.util.Map;
//import javax.swing.JFrame;
//
///**
// *
// * @author 49904022
// */
//public class prueba extends JFrame {   //REVISAR!!!
//
//    private boolean ida = true;
//    private double[] origen = new double[2];
//    private static double[][] puntos = new double[26][2];
//
//    //private static char[] Enigma.pintar = new char[16];
//    //private static int indicePintar = 0;
//
//    private static int posIY, posCY, posDY, posTX, posTY, posRX, posRY, posClaX, posClaY;
//    private static Map<String, Integer> posIX = new HashMap<>();
//    private static Map<String, Integer> posPerIX = new HashMap<>();
//    private static Map<String, Integer> posCX = new HashMap<>();
//    private static Map<String, Integer> posPerCX = new HashMap<>();
//    private static Map<String, Integer> posDX = new HashMap<>();
//    private static Map<String, Integer> posPerDX = new HashMap<>();
//    private static int contEleccion;
//    private static char cI, cC, cD;
//
//    private static Map<String, String> rotores = new HashMap<>();
//
//    // 26 lados = 360º/26 lados * lado específico n
////    public static void main(String[] args) {
////        new prueba();
////    }
//    public double[] getOrigen() {
//        return origen;
//    }
//
//    public void setOrigen(double[] origen) {
//        this.origen = origen;
//    }
//
//    public static void resta() {
//        /*double x, y, radio;
//        double[] origen = new double[2], punto = new double[2];
//        origen[0] = 400;
//        origen[1] = 600;
//        punto[0] = 400;
//        punto[1] = 700;
//        radio = radio(origen, punto);
//        int n = 0;
//        double ang = (360 / 26) * n;
//        for (int i = 0; i < 26; i++) {
//            ang = (double) (360d / 26d) * (double) n;
//            puntos[i] = auto((ang - 90), radio, origen);
//            n++;
//        }*/
//        //new prueba(origen);
//        //JFrame frame = new prueba();
//        //frame.setSize(800, 800);
//        //frame.setVisible(true);
//        //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//    }
//
//    public static double radio(double[] origen, double[] punto) {
//        double auxX = punto[0] - origen[0];
//        double auxY = punto[1] - origen[1];
//        return Math.sqrt(Math.pow(auxX, 2) + Math.pow(auxY, 2));
//    }
//
//    public static double[] auto(double a, double radio, double[] origen) {
//        double sinA = Math.sin(Math.toRadians(a));
//        double cosA = Math.cos(Math.toRadians(a));
//        double[] aux = {cosA * radio + origen[0], sinA * radio + origen[1]};
//        return aux;
//    }
//
//    public prueba() {
//        //super();
//        //setSize(800, 800);
//        //setVisible(true);
//        //setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        //setOrigen(origen);
//    }
//
//    private void initialize() {
//        /*for (int i = 0; i < paraPintar.length; i++) {
//            paraPintar[i] = Enigma.pintar[i];
//        }*/
//        //System.arraycopy(Enigma.pintar, 0, Enigma.pintar, 0, Enigma.pintar.length);
//        //System.out.println("Izq: " + this.rotorIzquierda.obtenerContEscritura().charAt(0) + " Cen: " + this.rotorCentral.obtenerContEscritura().charAt(0) + " Der: " + this.rotorDerecha.obtenerContEscritura().charAt(0));
//        
//        
//        posRX = 400;
//        posRY = 100;
//        posClaX = 400;
//        posClaY = 600;
//        posTX = 400;
//        posTY = 700;
//        System.out.println("Posiciones Y teclado, clavijero y reflector inicializadas");
//
//        cI = EjecutarEnigma.cI;
//        cC = EjecutarEnigma.cC;
//        cD = EjecutarEnigma.cD;
//        System.out.println("Initialize * cI: " + cI + " cC: " + cC + " cD: " + cD);
//
//        contEleccion = 0;
//        System.out.println("Colocación rotores inicializada");
//
//        posIY = 200;
//        posCY = 300;
//        posDY = 400;
//        System.out.println("Posiciones rotores Y inicializadas");
//
//        rotores.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
//        rotores.put("II", "AJDKSIRUXBLHWTMCQGZNPYFVOE");
//        rotores.put("III", "BDFHJLCPRTXVZNYEIWGAKMUSQO");
//        rotores.put("IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB");
//        rotores.put("V", "VZBRGITYUPSDNHLXAWMJQOFECK");
//        System.out.println("Rotores inicializados");
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        Font f = new Font("Monospaced", Font.BOLD, 22);
//        initialize();
//        super.paint(g);
//        int character = 65;
//        Graphics pintar = (Graphics) g;
//        pintar.setColor(Color.black);
//        pintar.setFont(f);
//
//        /*for (int i = 0; i < Enigma.pintar.length; i++) {
//            System.out.println("camino pintar: " + Enigma.pintar[i]);
//        }*/
//        pintarBucle(character, pintar, "I", posIY);
//        pintarBucle(character, pintar, "II", posCY);
//        pintarBucle(character, pintar, "III", posDY);
//        
//        pintarBucle(character, pintar, "R", posRY);
//        pintarBucle(character, pintar, "C", posClaY);
//        pintarBucle(character, pintar, "T", posTY);
//        
//        pintarLinea(g);
//
//        //----
//        //---DIBUJAR CÍRCULO
//        /*for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//            pintar.drawString(Character.toString(c), (int) puntos[i][0], (int) puntos[i][1]);
//            character++;
//            if (character > 90) {
//                character = 65;
//            }
//        }*/
// /*//REFLECTOR
//        for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//            pintar.drawString(Character.toString(c), (100 + 20 * i), 80);
//            
//            //INICIALIZAR VARIABLES DE POSICIONES
//            
//            
//            character++;
//            if (character > 90) {
//                character = 65;
//            }
//        }
//
//        //SALIDA ROTOR I
//        for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//            pintar.drawString(Character.toString(c), (100 + 20 * i), 130);
//            character++;
//            if (character > 90) {
//                character = 65;
//            }
//        }
//
//        //ENTRADA ROTOR I
//        for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//
//            if (character == 72) {
//                pintar.setColor(Color.CYAN);
//                pintar.fillRect((100 + 20 * i), 142, 15, 22);
//            }
//            pintar.setColor(Color.black);
//
//            pintar.drawString(Character.toString(c), (100 + 20 * i), 170);
//            character++;
//            if (character > 90) {
//                character = 65;
//            }
//        }
//
//        //SALIDA ROTOR II
//        for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//            pintar.drawString(Character.toString(c), (100 + 20 * i), 210);
//            character++;
//            if (character > 90) {
//                character = 65;
//            }
//        }
//
//        //ENTRADA ROTOR II
//        for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//
//            if (character == 89) {
//                pintar.setColor(Color.RED);
//                pintar.drawLine((110 + 20 * i), 320, (110 + 20 * i), 250);
//            }
//
//            if (character == 68) {
//                pintar.setColor(Color.CYAN);
//                pintar.fillRect((100 + 20 * i), 222, 15, 22);
//            }
//            pintar.setColor(Color.black);
//
//            pintar.drawString(Character.toString(c), (100 + 20 * i), 240);
//            character++;
//            if (character > 90) {
//                character = 65;
//            }
//        }
//
//        //SALIDA ROTOR III
//        for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//
//            if (character == 89) {
//                pintar.setColor(Color.RED);
//                pintar.drawLine((110 + 20 * i), 282, (110 + 20 * i), 325);
//            }
//
//            pintar.setColor(Color.black);
//
//            pintar.drawString(Character.toString(c), (100 + 20 * i), 295);
//            character++;
//            if (character > 90) {
//                character = 65;
//            }
//        }
//        //ENTRADA ROTOR III
//        for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//
//            if (character == 89) {
//                pintar.setColor(Color.RED);
//                e3i = (110 + 20 * i);
//                pintar.drawLine(e3i, 325, 400, 400);
//            }
//
//            if (character == 88) { //(int) Enigma.getRotorDerecha().obtenerContEscritura().charAt(0)
//                pintar.setColor(Color.CYAN);
//                pintar.fillRect((100 + 20 * i), 307, 15, 22);
//            }
//            pintar.setColor(Color.black);
//
//            pintar.drawString(Character.toString(c), (100 + 20 * i), 325);
//            character++;
//            if (character > 90) {
//                character = 65;
//            }
//        }*/
//    }
//
//    private void pintarLinea(Graphics g) {
//        g.setColor(Color.RED);
//        g.drawLine(posTX, posTY, posClaX, posClaY + 20);// Desde Teclado entrada hasta Clavijero paraPintar[0]
//        g.drawLine(posClaX, posClaY, posDX.get(Character.toString(Enigma.pintar[1])), posDY + 20); // Desde Clavijero paraPintar[0] hasta D ABC
//        g.drawLine(posPerDX.get(Character.toString(Enigma.pintar[2])), posDY - 15, posCX.get(Character.toString(Enigma.pintar[3])), posCY + 20);    // Desde D PER hasta C ABC
//        g.drawLine(posPerCX.get(Character.toString(Enigma.pintar[4])), posCY - 15, posIX.get(Character.toString(Enigma.pintar[5])), posIY + 20);   // Desde C PER hasta I ABC
//        g.drawLine(posPerIX.get(Character.toString(Enigma.pintar[6])), posIY - 15, posRX, posRY + 20); // Desde I PER hasta R paraPintar[7]
//
//        g.setColor(Color.MAGENTA);
//        g.drawLine(posRX, posRY + 20, posPerIX.get(Character.toString(Enigma.pintar[9])), posIY + 20);// Desde R paraPintar[8] hasta I PER
//        g.drawLine(posIX.get(Character.toString(Enigma.pintar[10])), posIY + 20, posPerCX.get(Character.toString(Enigma.pintar[11])), posCY - 15);// Desde I ABC hasta C PER
//        g.drawLine(posCX.get(Character.toString(Enigma.pintar[12])), posCY + 20, posPerDX.get(Character.toString(Enigma.pintar[13])), posDY - 15);// Desde C ABC hasta D PER
//        g.drawLine(posDX.get(Character.toString(Enigma.pintar[14])), posDY + 20, posClaX, posClaY);// Desde D ABC hasta Clavijero paraPintar[15]
//        g.drawLine(posClaX, posClaY + 20, posTX, posTY);// Desde Clavijero paraPintar[15] hasta Teclado entrada
//    }
//
//    public void pintarBucle(int character, Graphics g, String eleccion, int offset) {
//        String s = rotores.get(eleccion);
//        int n = 0;
//        String abc = "", per = "";
//        for (int i = 0; i < 26; i++) {
//            char c = (char) character;
//            n = 100 + 20 * i;
//            abc = Character.toString(c);
//
//            if (!"T".equals(eleccion) && !"C".equals(eleccion) && !"R".equals(eleccion)) {
//                per = Character.toString(s.charAt(i));
//                switch (contEleccion) {
//                    case 0:
//                        posPerIX.put(per, n + 5);
//                        posIX.put(abc, n + 5);
//                        if (c == cI) {
//                            pintarClave(g, n, offset);
//                        }
//                        break;
//                    case 1:
//                        posPerCX.put(per, n + 5);
//                        posCX.put(abc, n + 5);
//                        if (c == cC) {
//                            pintarClave(g, n, offset);
//                        }
//                        break;
//                    case 2:
//                        posPerDX.put(per, n + 5);
//                        posDX.put(abc, n + 5);
//                        if (c == cD) {
//                            pintarClave(g, n, offset);
//                        }
//                        break;
//                    default:
//                        System.out.println("Error al colocar los rotores (prueba.pintarBucle)");
//                        break;
//                }
//                g.setColor(Color.black);
//                g.drawString(per, n, offset);
//            }
//            g.setColor(Color.black);
//            g.drawString(abc, n, offset + 20);
//            character++;
//        }
//        contEleccion++;
//    }
//
//    private void pintarClave(Graphics g, int n, int offset) {
//        g.setColor(Color.CYAN);
//        g.fillRect(n, offset + 5, 15, 18);
//    }
//}
