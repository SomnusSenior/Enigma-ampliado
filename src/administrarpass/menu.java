package administrarpass;

import static administrarpass.EjecutarEnigma.procesar;
//import static administrarpass.prueba.resta;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * @author 49904022
 */
public class menu extends javax.swing.JFrame {

    JTextField jt = new JTextField();
    Rotor rIzq = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q'), //tipo I Q
            rCen = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E'), //tipo II E
            rDer = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V'); //tipo III V
    JFrame f;
    public boolean eni = false;

    //----------
    private boolean ida = true;
    private double[] origen = new double[2];
    private static double[][] puntos = new double[26][2];

    private static int posIY, posCY, posDY, posTX, posTY, posRX, posRY, posClaX, posClaY;
    private static Map<String, Integer> posIX = new HashMap<>();
    private static Map<String, Integer> posPerIX = new HashMap<>();
    private static Map<String, Integer> posCX = new HashMap<>();
    private static Map<String, Integer> posPerCX = new HashMap<>();
    private static Map<String, Integer> posDX = new HashMap<>();
    private static Map<String, Integer> posPerDX = new HashMap<>();
    private static int contEleccion;
    private static char cI, cC, cD;

    private static Map<String, String> rotores = new HashMap<>();

    //-----------
    /**
     * Creates new form menu
     */
    public menu() {
        super();
        initComponents();
        prueba1();
        initialize();
    }

    private void prueba1() {
        /**
         * - Si se ha introducido un mensaje se inhabilitan los botones para el
         * cambio de la clave.... Cada cambio de clave refresca la pantalla para
         * actualizar el dibujo. - Sobre las clavijas: Pensar después qué hacer
         * :( (Con respecto a mostrarlas en la interfaz)
         */

        System.out.println("Antes add listeners");
        ((AbstractDocument) jTextFieldMensaje.getDocument()).setDocumentFilter(new MyFilter());
        jTextFieldMensaje.getDocument().addDocumentListener(new pruebaListener());
        jTextFieldMensaje.getDocument().putProperty("name", "prueba");
        System.out.println("Listeners listos");
    }

    class pruebaListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateFieldState(e, "insert");
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateFieldState(e, "remove");
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateFieldState(e, "change");
        }

        public void updateFieldState(DocumentEvent e, String action) {
            //System.out.println("updateField: " + action);
            Enigma enigma = new Enigma(rIzq, rCen, rDer); // Crea la máquina enigma
            EjecutarEnigma.cifrado = 0;

            char c1 = jTextFieldClavija1.getText().charAt(0),
                    c2 = jTextFieldClavija2.getText().charAt(0);
            System.out.println("c1: " + c1 + " c2: " + c2);
            enigma.ponerClavija(c1, c2);

            char cI = jTextFieldClaveIzq.getText().charAt(0),
                    cC = jTextFieldClaveCen.getText().charAt(0),
                    cD = jTextFieldClaveDer.getText().charAt(0);
            System.out.println("cI: " + cI + " cC: " + cC + " cD: " + cD);
            enigma.setRotorsIni(cI, cC, cD);

            String mensaje = jTextFieldMensaje.getText();
            //modo = 0;
            jTextFieldCifrado.setText(procesar(enigma, mensaje));

            int len = e.getDocument().getLength();
            String doc = "";
            try {
                doc = e.getDocument().getText(0, len);
            } catch (BadLocationException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!doc.isEmpty()) {
                eni = true;
                repaint();
            } else {
                eni = false;
            }
        }
    }

    class MyFilter extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                String text, AttributeSet attrs) throws BadLocationException {
            boolean valido = false;
            /*System.out.println("Replace");
            if (offset >= fb.getDocument().getLength()) {
                System.out.println("Added: " + text);
            } else {
                String old = fb.getDocument().getText(offset, length);
                System.out.println("Replaced " + old + " with " + text);
            }*/
            char c = text.charAt(0);

            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                valido = true;
            } else {
                valido = false;
            }

            if (valido) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                super.replace(fb, offset, length, "", attrs);
            }
        }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset,
                String text, AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, text, attr);
        }

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
                throws BadLocationException {
            //System.out.println("Remove: " + fb.getDocument().getText(offset, length));
            super.remove(fb, offset, length);
        }
    }

    //--------
    private void initialize() {
        //System.out.println("Izq: " + this.rotorIzquierda.obtenerContEscritura().charAt(0) + " Cen: " + this.rotorCentral.obtenerContEscritura().charAt(0) + " Der: " + this.rotorDerecha.obtenerContEscritura().charAt(0));

        posRX = 400;
        posRY = 100;
        posClaX = 400;
        posClaY = 600;
        posTX = 400;
        posTY = 700;
        System.out.println("Posiciones Y teclado, clavijero y reflector inicializadas");

        cI = EjecutarEnigma.cI;
        cC = EjecutarEnigma.cC;
        cD = EjecutarEnigma.cD;
        System.out.println("Initialize * cI: " + cI + " cC: " + cC + " cD: " + cD);

        contEleccion = 0;
        System.out.println("Colocación rotores inicializada");

        posIY = 200;
        posCY = 300;
        posDY = 400;
        System.out.println("Posiciones rotores Y inicializadas");

        rotores.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        rotores.put("II", "AJDKSIRUXBLHWTMCQGZNPYFVOE");
        rotores.put("III", "BDFHJLCPRTXVZNYEIWGAKMUSQO");
        rotores.put("IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB");
        rotores.put("V", "VZBRGITYUPSDNHLXAWMJQOFECK");
        System.out.println("Rotores inicializados");
    }
    
    public void repaint(){
        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        Font f = new Font("Monospaced", Font.BOLD, 22);
        //initialize();
        super.paint(g);
        int character = 65;
        Graphics pintar = (Graphics) g;
        pintar.setColor(Color.black);
        pintar.setFont(f);

        pintarBucle(character, pintar, "I", posIY);
        pintarBucle(character, pintar, "II", posCY);
        pintarBucle(character, pintar, "III", posDY);

        pintarBucle(character, pintar, "R", posRY);
        pintarBucle(character, pintar, "C", posClaY);
        pintarBucle(character, pintar, "T", posTY);

        if (eni) {
            pintarLinea(g);
        }
    }

    private void pintarLinea(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(posTX, posTY, posClaX, posClaY + 20);// Desde Teclado entrada hasta Clavijero paraPintar[0]
        g.drawLine(posClaX, posClaY, posDX.get(Character.toString(Enigma.pintar[1])), posDY + 20); // Desde Clavijero paraPintar[0] hasta D ABC
        g.drawLine(posPerDX.get(Character.toString(Enigma.pintar[2])), posDY - 15, posCX.get(Character.toString(Enigma.pintar[3])), posCY + 20);    // Desde D PER hasta C ABC
        g.drawLine(posPerCX.get(Character.toString(Enigma.pintar[4])), posCY - 15, posIX.get(Character.toString(Enigma.pintar[5])), posIY + 20);   // Desde C PER hasta I ABC
        g.drawLine(posPerIX.get(Character.toString(Enigma.pintar[6])), posIY - 15, posRX, posRY + 20); // Desde I PER hasta R paraPintar[7]

        g.setColor(Color.MAGENTA);
        g.drawLine(posRX, posRY + 20, posPerIX.get(Character.toString(Enigma.pintar[9])), posIY - 15);// Desde R paraPintar[8] hasta I PER
        g.drawLine(posIX.get(Character.toString(Enigma.pintar[10])), posIY + 20, posPerCX.get(Character.toString(Enigma.pintar[11])), posCY - 15);// Desde I ABC hasta C PER
        g.drawLine(posCX.get(Character.toString(Enigma.pintar[12])), posCY + 20, posPerDX.get(Character.toString(Enigma.pintar[13])), posDY - 15);// Desde C ABC hasta D PER
        g.drawLine(posDX.get(Character.toString(Enigma.pintar[14])), posDY + 20, posClaX, posClaY);// Desde D ABC hasta Clavijero paraPintar[15]
        g.drawLine(posClaX, posClaY + 20, posTX, posTY);// Desde Clavijero paraPintar[15] hasta Teclado entrada
    }

    public void pintarBucle(int character, Graphics g, String eleccion, int offset) {
        String s = rotores.get(eleccion);
        int n = 0;
        String abc = "", per = "";
        for (int i = 0; i < 26; i++) {
            char c = (char) character;
            n = 100 + 20 * i;
            abc = Character.toString(c);

            if (!"T".equals(eleccion) && !"C".equals(eleccion) && !"R".equals(eleccion)) {
                per = Character.toString(s.charAt(i));
                switch (contEleccion) {
                    case 0:
                        posPerIX.put(per, n + 5);
                        posIX.put(abc, n + 5);
                        if (c == cI) {
                            pintarClave(g, n, offset);
                        }
                        break;
                    case 1:
                        posPerCX.put(per, n + 5);
                        posCX.put(abc, n + 5);
                        if (c == cC) {
                            pintarClave(g, n, offset);
                        }
                        break;
                    case 2:
                        posPerDX.put(per, n + 5);
                        posDX.put(abc, n + 5);
                        if (c == cD) {
                            pintarClave(g, n, offset);
                        }
                        break;
                    default:
                        System.out.println("Error al colocar los rotores (prueba.pintarBucle)");
                        break;
                }
                g.setColor(Color.black);
                g.drawString(per, n, offset);
            }
            g.setColor(Color.black);
            g.drawString(abc, n, offset + 20);
            character++;
        }
        contEleccion++;
    }

    private void pintarClave(Graphics g, int n, int offset) {
        g.setColor(Color.CYAN);
        g.fillRect(n, offset + 5, 15, 18);
    }

    //---------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxRotorIzq = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldClaveIzq = new javax.swing.JTextField();
        jTextFieldClaveCen = new javax.swing.JTextField();
        jTextFieldClaveDer = new javax.swing.JTextField();
        jTextFieldMensaje = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldCifrado = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldClavija1 = new javax.swing.JTextField();
        jTextFieldClavija2 = new javax.swing.JTextField();
        jButtonClavijaAdd = new javax.swing.JButton();
        jComboBoxRotorCen = new javax.swing.JComboBox<>();
        jComboBoxRotorDer = new javax.swing.JComboBox<>();
        jButtonClavijaDelete = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 1000));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Rotor Izquierdo:");

        jLabel2.setText("Rotor Central:");

        jLabel3.setText("Rotor Derecho:");

        jComboBoxRotorIzq.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I", "II", "III", "IV", "V" }));

        jLabel4.setText("Claves:");

        jTextFieldClaveIzq.setText("A");
        jTextFieldClaveIzq.setPreferredSize(new java.awt.Dimension(20, 20));
        jTextFieldClaveIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClaveIzqActionPerformed(evt);
            }
        });

        jTextFieldClaveCen.setText("A");
        jTextFieldClaveCen.setPreferredSize(new java.awt.Dimension(20, 20));
        jTextFieldClaveCen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClaveCenActionPerformed(evt);
            }
        });

        jTextFieldClaveDer.setText("A");
        jTextFieldClaveDer.setPreferredSize(new java.awt.Dimension(20, 20));
        jTextFieldClaveDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClaveDerActionPerformed(evt);
            }
        });

        jTextFieldMensaje.setText("Mensaje");
        jTextFieldMensaje.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                mensajeOnChange(evt);
            }
        });
        jTextFieldMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mensaje(evt);
            }
        });

        jLabel5.setText("Introduce tu mensaje:");

        jLabel6.setText("Mensaje cifrado:");

        jTextFieldCifrado.setEditable(false);
        jTextFieldCifrado.setText("Prueba");
        jTextFieldCifrado.setDragEnabled(true);
        jTextFieldCifrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCifradoActionPerformed(evt);
            }
        });

        jLabel7.setText("Clavijas:");

        jTextFieldClavija1.setText("A");
        jTextFieldClavija1.setPreferredSize(new java.awt.Dimension(20, 20));

        jTextFieldClavija2.setText("A");
        jTextFieldClavija2.setPreferredSize(new java.awt.Dimension(20, 20));
        jTextFieldClavija2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClavija2ActionPerformed(evt);
            }
        });

        jButtonClavijaAdd.setText("Añadir");

        jComboBoxRotorCen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I", "II", "III", "IV", "V" }));

        jComboBoxRotorDer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I", "II", "III", "IV", "V" }));

        jButtonClavijaDelete.setText("Eliminar");
        jButtonClavijaDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClavijaDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldCifrado, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldMensaje, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBoxRotorDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBoxRotorCen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(38, 38, 38)
                                    .addComponent(jComboBoxRotorIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldClaveIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldClaveCen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldClaveDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldClavija1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldClavija2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonClavijaAdd)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonClavijaDelete))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBoxRotorIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBoxRotorCen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBoxRotorDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldClaveDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldClaveCen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldClaveIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldClavija1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldClavija2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonClavijaAdd)
                            .addComponent(jButtonClavijaDelete))))
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCifrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Enigma clásica", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 647, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Simulador Enigma");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("Simulador de la Máquina Enigma original y de la ampliación");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldClaveIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClaveIzqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClaveIzqActionPerformed

    private void jTextFieldClaveCenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClaveCenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClaveCenActionPerformed

    private void jTextFieldClaveDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClaveDerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClaveDerActionPerformed

    private void mensaje(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mensaje
        // TODO add your handling code here:
    }//GEN-LAST:event_mensaje

    private void mensajeOnChange(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_mensajeOnChange
        // TODO add your handling code here:
    }//GEN-LAST:event_mensajeOnChange

    private void jTextFieldCifradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCifradoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCifradoActionPerformed

    private void jTextFieldClavija2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClavija2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClavija2ActionPerformed

    private void jButtonClavijaDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClavijaDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClavijaDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClavijaAdd;
    private javax.swing.JButton jButtonClavijaDelete;
    private javax.swing.JComboBox<String> jComboBoxRotorCen;
    private javax.swing.JComboBox<String> jComboBoxRotorDer;
    private javax.swing.JComboBox<String> jComboBoxRotorIzq;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldCifrado;
    private javax.swing.JTextField jTextFieldClaveCen;
    private javax.swing.JTextField jTextFieldClaveDer;
    private javax.swing.JTextField jTextFieldClaveIzq;
    private javax.swing.JTextField jTextFieldClavija1;
    private javax.swing.JTextField jTextFieldClavija2;
    public javax.swing.JTextField jTextFieldMensaje;
    // End of variables declaration//GEN-END:variables
}
