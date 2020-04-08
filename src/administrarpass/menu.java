package administrarpass;

import static administrarpass.EjecutarEnigma.procesar;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.JFrame;

/**
 * @author 49904022
 */
public class menu extends JFrame {

    private Rotor rI = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q'), //tipo I Q
            rII = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E'), //tipo II E
            rIII = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V'), //tipo III V
            rIV = new Rotor("ESOVPZJAYQUIRHXLNFTGKDCMWB", 'J'),
            rV = new Rotor("VZBRGITYUPSDNHLXAWMJQOFECK", 'Z');
    private boolean eni = false;
    private static int posIY, posCY, posDY, posTY, posRY, posClaY, contEleccion;
    private static Map<String, Integer> posRX = new HashMap<>();
    private static Map<String, Integer> posClaX = new HashMap<>();
    private static Map<String, Integer> posTX = new HashMap<>();
    private static Map<String, Integer> posIX = new HashMap<>();
    private static Map<String, Integer> posPerIX = new HashMap<>();
    private static Map<String, Integer> posCX = new HashMap<>();
    private static Map<String, Integer> posPerCX = new HashMap<>();
    private static Map<String, Integer> posDX = new HashMap<>();
    private static Map<String, Integer> posPerDX = new HashMap<>();
    private static Map<String, String> rotoresString = new HashMap<>();
    private static Map<String, Rotor> rotores = new HashMap<>();
    private static char cI, cC, cD;
    private static Enigma enigma;
    private static ArrayList<Clavijas> conex = new ArrayList<>();
    private tableroPintar tablero = new tableroPintar();

    /**
     * Creates new form menu
     */
    public menu() {
        super();
        //pruebaPanel();
        initComponents();
        iniciar();
        initialize();
    }

    private void showFrame() {

        jCheckBoxRotorIzqAmp2.isSelected();
    }

    private void iniciar() {
        ((AbstractDocument) jTextFieldMensaje.getDocument()).setDocumentFilter(new filtroMensaje());
        jTextFieldMensaje.getDocument().addDocumentListener(new listenerMensaje());

        ((AbstractDocument) jTextFieldClaveIzq.getDocument()).setDocumentFilter(new filtro1char());
        ((AbstractDocument) jTextFieldClaveCen.getDocument()).setDocumentFilter(new filtro1char());
        ((AbstractDocument) jTextFieldClaveDer.getDocument()).setDocumentFilter(new filtro1char());

        ((AbstractDocument) jTextFieldClavija1.getDocument()).setDocumentFilter(new filtro1char());
        ((AbstractDocument) jTextFieldClavija2.getDocument()).setDocumentFilter(new filtro1char());
    }

    class listenerMensaje implements DocumentListener {

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
            enigma = new Enigma(rotores.get(jComboBoxRotorIzq.getSelectedItem().toString()),
                    rotores.get(jComboBoxRotorCen.getSelectedItem().toString()),
                    rotores.get(jComboBoxRotorDer.getSelectedItem().toString())); // Crea la máquina enigma
            EjecutarEnigma.cifrado = 0;
            for (Clavijas cla : conex) {
                enigma.ponerClavija(cla.getPrimera(), cla.getSegunda());
            }
            char cI = 'A', cC = 'A', cD = 'A';
            if ((jTextFieldClaveIzq.getText().length() & jTextFieldClaveCen.getText().length() & jTextFieldClaveDer.getText().length()) > 0) {
                cI = EjecutarEnigma.pasarMayus(jTextFieldClaveIzq.getText().charAt(0));
                cC = EjecutarEnigma.pasarMayus(jTextFieldClaveCen.getText().charAt(0));
                cD = EjecutarEnigma.pasarMayus(jTextFieldClaveDer.getText().charAt(0));
            }
            enigma.setRotoresIni(cI, cC, cD);
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
                changeEnabled(false);
                repaint();
            } else {
                eni = false;
                changeEnabled(true);
                setcI(cI);
                setcC(cC);
                setcD(cD);
                repaint();
            }
        }
    }

    private void changeEnabled(boolean b) {
        jTextFieldClaveIzq.setEnabled(b);
        jTextFieldClaveCen.setEnabled(b);
        jTextFieldClaveDer.setEnabled(b);
        jTextFieldClavija1.setEnabled(b);
        jTextFieldClavija2.setEnabled(b);

        jButtonClavijaAdd.setEnabled(b);
        jButtonClavijaDelete.setEnabled(b);

        jComboBoxRotorIzq.setEnabled(b);
        jComboBoxRotorCen.setEnabled(b);
        jComboBoxRotorDer.setEnabled(b);

        jList1.setEnabled(b);
    }

    private static void acomodarClavijas() {
        int primeraValue = 0, segundaValue = 0;
        char primera = 0, segunda = 0;
        for (Clavijas conex : enigma.getPlugboard().getConexiones()) {
            primera = conex.getPrimera();
            segunda = conex.getSegunda();
            primeraValue = posClaX.get(Character.toString(primera));
            segundaValue = posClaX.get(Character.toString(segunda));
            posClaX.put(Character.toString(primera), segundaValue);
            posClaX.put(Character.toString(segunda), primeraValue);
        }
    }

    public static void setcI(char cI) {
        menu.cI = cI;
    }

    public static void setcC(char cC) {
        menu.cC = cC;
    }

    public static void setcD(char cD) {
        menu.cD = cD;
    }

    class filtroMensaje extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            char c = 0;
            String s = "";
            for (int i = 0; i < text.length(); i++) {
                c = text.charAt(i);
                if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                    s += c;
                }
            }
            super.replace(fb, offset, length, s, attrs);
        }
    }

    class filtro1char extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            boolean valido = false;
            char c = text.charAt(0);
            if (((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) && offset == 0) {
                valido = true;
            } else {
                valido = false;
            }
            text = Character.toString(EjecutarEnigma.pasarMayus(c));
            if (valido) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                super.replace(fb, offset, length, "", attrs);
            }
        }
    }

    private void initialize() {
        posRY = 140;    //Reflector
        posIY = 220;    //Izquierdo
        posCY = 300;    //Central
        posDY = 380;    //Derecho
        posClaY = 440;  //Clavijero
        posTY = 500;    //Teclado
        //System.out.println("Posiciones Y teclado, clavijero, reflector y rotores inicializadas");
        contEleccion = 0;
        //System.out.println("Colocación rotores inicializada");
        rotoresString.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        rotoresString.put("II", "AJDKSIRUXBLHWTMCQGZNPYFVOE");
        rotoresString.put("III", "BDFHJLCPRTXVZNYEIWGAKMUSQO");
        rotoresString.put("IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB");
        rotoresString.put("V", "VZBRGITYUPSDNHLXAWMJQOFECK");
        //System.out.println("Rotores pintar inicializados");
        rotores.put("I", rI);
        rotores.put("II", rII);
        rotores.put("III", rIII);
        rotores.put("IV", rIV);
        rotores.put("V", rV);
        //System.out.println("Rotores inicializados");
        jTabbedPane1.setFont(new Font(jTabbedPane1.getFont().getFontName(), Font.BOLD, jTabbedPane1.getFont().getSize() + 6));
        jTabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (jTabbedPane1.getSelectedIndex() == 0) {
                    repaint();
                }
            }
        });
        jComboBoxRotorIzq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        jComboBoxRotorCen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        jComboBoxRotorDer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }

    class tableroPintar {

        public BufferedImage pintarTablero() {
            Font font = new Font("Monospaced", Font.BOLD, 22);
            int character = 65;
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics pintar = image.createGraphics();
            contEleccion = 0;
            pintar.setColor(Color.black);
            pintar.setFont(font);
            pintarBucle(character, pintar, jComboBoxRotorIzq.getSelectedItem().toString(), posIY); //"I"
            pintarBucle(character, pintar, jComboBoxRotorCen.getSelectedItem().toString(), posCY); //"II"
            pintarBucle(character, pintar, jComboBoxRotorDer.getSelectedItem().toString(), posDY); //"III"
            pintarBucle(character, pintar, "R", posRY);
            pintarBucle(character, pintar, "C", posClaY);
            pintarBucle(character, pintar, "T", posTY);
            if (eni) {
                acomodarClavijas();
                pintarLinea(pintar);
            }
            pintar.dispose();
            return image;
        }

        private void pintarLinea(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g.setColor(Color.RED); //Pinta camino de ida
            g2d.drawLine(posTX.get(Character.toString(Enigma.pintar[0])), posTY + 5, posClaX.get(Character.toString(Enigma.pintar[0])), posClaY + 20); // Desde Teclado paraPintar[0] hasta Clavijero
            g2d.drawLine(posClaX.get(Character.toString(Enigma.pintar[0])), posClaY + 5, posDX.get(Character.toString(Enigma.pintar[1])), posDY + 20); // Desde Clavijero  hasta D ABC
            g2d.drawLine(posPerDX.get(Character.toString(Enigma.pintar[2])), posDY - 15, posCX.get(Character.toString(Enigma.pintar[3])), posCY + 20); // Desde D PER hasta C ABC
            g2d.drawLine(posPerCX.get(Character.toString(Enigma.pintar[4])), posCY - 15, posIX.get(Character.toString(Enigma.pintar[5])), posIY + 20); // Desde C PER hasta I ABC
            g2d.drawLine(posPerIX.get(Character.toString(Enigma.pintar[6])), posIY - 15, posRX.get(Character.toString(Enigma.pintar[7])), posRY + 20); // Desde I PER hasta R paraPintar[7]
            g2d.setColor(Color.MAGENTA); //Pinta camino de vueta
            g2d.drawLine(posRX.get(Character.toString(Enigma.pintar[8])), posRY + 20, posPerIX.get(Character.toString(Enigma.pintar[9])), posIY - 15); // Desde R paraPintar[8] hasta I PER
            g2d.drawLine(posIX.get(Character.toString(Enigma.pintar[10])), posIY + 20, posPerCX.get(Character.toString(Enigma.pintar[11])), posCY - 15); // Desde I ABC hasta C PER
            g2d.drawLine(posCX.get(Character.toString(Enigma.pintar[12])), posCY + 20, posPerDX.get(Character.toString(Enigma.pintar[13])), posDY - 15); // Desde C ABC hasta D PER
            g2d.drawLine(posDX.get(Character.toString(Enigma.pintar[14])), posDY + 20, posClaX.get(Character.toString(Enigma.pintar[15])), posClaY + 5); // Desde D ABC hasta Clavijero 
            g2d.drawLine(posClaX.get(Character.toString(Enigma.pintar[15])), posClaY + 20, posTX.get(Character.toString(Enigma.pintar[15])), posTY + 5); // Desde Clavijero hasta Teclado paraPintar[15]
        }

        private void pintarBucle(int character, Graphics g, String eleccion, int offset) {
            String s = rotoresString.get(eleccion), abc = "", per = "";
            int n = 0;
            char c = 0;
            for (int i = 0; i < 26; i++) {
                c = (char) character;
                n = 30 + 20 * i;
                abc = Character.toString(c);
                if (!"T".equals(eleccion) && !"C".equals(eleccion) && !"R".equals(eleccion)) {
                    per = Character.toString(s.charAt(i));
                    g.setColor(Color.black);
                    g.drawString(per, n, offset);
                }
                switch (contEleccion) {
                    case 0: //Rotor Izquierdo
                        pintarEleccion(posPerIX, per, posIX, abc, c, cI, g, n, offset);
                        break;
                    case 1: //Rotor Central
                        pintarEleccion(posPerCX, per, posCX, abc, c, cC, g, n, offset);
                        break;
                    case 2: //Rotor Derecho
                        pintarEleccion(posPerDX, per, posDX, abc, c, cD, g, n, offset);
                        break;
                    case 3: //Reflector
                        posRX.put(abc, n + 5);
                        break;
                    case 4: //Clavijero
                        posClaX.put(abc, n + 5);
                        break;
                    case 5: //Teclado
                        posTX.put(abc, n + 5);
                        break;
                    default:
                        break;
                }
                g.setColor(Color.black);
                g.drawString(abc, n, offset + 20);
                character++;
            }
            contEleccion++;
        }

        private void pintarEleccion(Map<String, Integer> posPer, String per, Map<String, Integer> pos, String abc, char c, char clave, Graphics g, int n, int offset) {
            posPer.put(per, n + 5);
            pos.put(abc, n + 5);
            if (c == clave) {
                pintarClave(g, n, offset);
            }
        }

        private void pintarClave(Graphics g, int n, int offset) {
            g.setColor(Color.CYAN);
            g.fillRect(n, offset + 5, 15, 18);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        jLabelBufferedImage.setIcon(new ImageIcon(tablero.pintarTablero()));
        jPanel1.add(jLabelBufferedImage);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxRotorIzq = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldClaveIzq = new javax.swing.JTextField();
        jTextFieldClaveCen = new javax.swing.JTextField();
        jTextFieldClaveDer = new javax.swing.JTextField();
        jTextFieldMensaje = new javax.swing.JTextField();
        jLabelMensaje = new javax.swing.JLabel();
        jLabelCifrado = new javax.swing.JLabel();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabelBufferedImage = new javax.swing.JLabel();
        jlabelClave = new javax.swing.JLabel();
        jlabelCaminoIda = new javax.swing.JLabel();
        jlabelCaminoVuelta = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabelMensaje1 = new javax.swing.JLabel();
        jTextFieldMensaje1 = new javax.swing.JTextField();
        jLabelCifrado1 = new javax.swing.JLabel();
        jTextFieldCifrado1 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldClaveIzq1 = new javax.swing.JTextField();
        jTextFieldClaveCen1 = new javax.swing.JTextField();
        jTextFieldClaveDer1 = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldClavija3 = new javax.swing.JTextField();
        jTextFieldClavija4 = new javax.swing.JTextField();
        jButtonClavijaAdd1 = new javax.swing.JButton();
        jButtonClavijaDelete1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jTextFieldRotorIzq1 = new javax.swing.JTextField();
        jLabelRotorIzq1 = new javax.swing.JLabel();
        jLabelRotorCen1 = new javax.swing.JLabel();
        jTextFieldRotorCen1 = new javax.swing.JTextField();
        jLabelRotorDer1 = new javax.swing.JLabel();
        jTextFieldRotorDer1 = new javax.swing.JTextField();
        jTextFieldReflector2 = new javax.swing.JTextField();
        jLabelReflector2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBoxRotorIzqAmp2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jSeparator5 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 700));

        jPanel1.setPreferredSize(new java.awt.Dimension(805, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Rotor Izquierdo:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 26, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Rotor Central:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 76, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Rotor Derecho:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 126, -1, -1));

        jComboBoxRotorIzq.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxRotorIzq.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I", "II", "III", "IV", "V" }));
        jPanel1.add(jComboBoxRotorIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 21, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Claves:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 181, -1, -1));

        jTextFieldClaveIzq.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveIzq.setText("A");
        jTextFieldClaveIzq.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel1.add(jTextFieldClaveIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(662, 182, -1, -1));

        jTextFieldClaveCen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveCen.setText("A");
        jTextFieldClaveCen.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel1.add(jTextFieldClaveCen, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 182, -1, -1));

        jTextFieldClaveDer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveDer.setText("A");
        jTextFieldClaveDer.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel1.add(jTextFieldClaveDer, new org.netbeans.lib.awtextra.AbsoluteConstraints(738, 182, -1, -1));

        jTextFieldMensaje.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(jTextFieldMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 510, 389, -1));

        jLabelMensaje.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje.setText("Introduce tu mensaje:");
        jPanel1.add(jLabelMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 482, -1, -1));

        jLabelCifrado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCifrado.setText("Mensaje cifrado:");
        jPanel1.add(jLabelCifrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 564, -1, -1));

        jTextFieldCifrado.setEditable(false);
        jTextFieldCifrado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCifrado.setDragEnabled(true);
        jPanel1.add(jTextFieldCifrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 592, 389, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Clavijas:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 226, -1, -1));

        jTextFieldClavija1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavija1.setText("A");
        jTextFieldClavija1.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel1.add(jTextFieldClavija1, new org.netbeans.lib.awtextra.AbsoluteConstraints(662, 227, -1, -1));

        jTextFieldClavija2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavija2.setText("A");
        jTextFieldClavija2.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel1.add(jTextFieldClavija2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 227, -1, -1));

        jButtonClavijaAdd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaAdd.setText("Añadir");
        jButtonClavijaAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonClavijaAddMouseClicked(evt);
            }
        });
        jPanel1.add(jButtonClavijaAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 260, -1, -1));

        jComboBoxRotorCen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxRotorCen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I", "II", "III", "IV", "V" }));
        jComboBoxRotorCen.setSelectedIndex(1);
        jPanel1.add(jComboBoxRotorCen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 71, -1, -1));

        jComboBoxRotorDer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxRotorDer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I", "II", "III", "IV", "V" }));
        jComboBoxRotorDer.setSelectedIndex(2);
        jPanel1.add(jComboBoxRotorDer, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 121, -1, -1));

        jButtonClavijaDelete.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaDelete.setText("Eliminar");
        jButtonClavijaDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonClavijaDeleteMouseClicked(evt);
            }
        });
        jPanel1.add(jButtonClavijaDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 260, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 165, 221, 10));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 211, 221, 9));

        jList1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(jList1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(621, 310, 84, 302));
        jPanel1.add(jLabelBufferedImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 480));

        jlabelClave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlabelClave.setForeground(new java.awt.Color(0, 220, 220));
        jlabelClave.setText("Clave");
        jPanel1.add(jlabelClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, -1, -1));

        jlabelCaminoIda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlabelCaminoIda.setForeground(Color.red);
        jlabelCaminoIda.setText("Camino Ida");
        jPanel1.add(jlabelCaminoIda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, -1, -1));

        jlabelCaminoVuelta.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlabelCaminoVuelta.setForeground(Color.magenta);
        jlabelCaminoVuelta.setText("Camino Vuelta");
        jPanel1.add(jlabelCaminoVuelta, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, -1, -1));

        jTabbedPane1.addTab("Enigma clásica", jPanel1);

        jPanel2.setName(""); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelMensaje1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje1.setText("Introduce tu mensaje:");
        jPanel2.add(jLabelMensaje1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 415, -1, -1));

        jTextFieldMensaje1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldMensaje1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 449, 389, -1));

        jLabelCifrado1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCifrado1.setText("Mensaje cifrado:");
        jPanel2.add(jLabelCifrado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 497, -1, -1));

        jTextFieldCifrado1.setEditable(false);
        jTextFieldCifrado1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCifrado1.setDragEnabled(true);
        jPanel2.add(jTextFieldCifrado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 525, 389, -1));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 157, 221, 10));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Claves:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 173, -1, -1));

        jTextFieldClaveIzq1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveIzq1.setText("A");
        jTextFieldClaveIzq1.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveIzq1, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 174, -1, -1));

        jTextFieldClaveCen1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveCen1.setText("A");
        jTextFieldClaveCen1.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveCen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(664, 174, -1, -1));

        jTextFieldClaveDer1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveDer1.setText("A");
        jTextFieldClaveDer1.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveDer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 174, -1, -1));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 207, 221, 9));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Clavijas:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 219, -1, -1));

        jTextFieldClavija3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavija3.setText("A");
        jTextFieldClavija3.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClavija3, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 220, -1, -1));

        jTextFieldClavija4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavija4.setText("A");
        jTextFieldClavija4.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClavija4, new org.netbeans.lib.awtextra.AbsoluteConstraints(664, 220, -1, -1));

        jButtonClavijaAdd1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaAdd1.setText("Añadir");
        jButtonClavijaAdd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonClavijaAdd1MouseClicked(evt);
            }
        });
        jPanel2.add(jButtonClavijaAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 253, -1, -1));

        jButtonClavijaDelete1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaDelete1.setText("Eliminar");
        jButtonClavijaDelete1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonClavijaDelete1MouseClicked(evt);
            }
        });
        jPanel2.add(jButtonClavijaDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(641, 253, -1, -1));

        jList2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane3.setViewportView(jList2);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 303, 84, 302));

        jTextFieldRotorIzq1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorIzq1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 76, 250, -1));

        jLabelRotorIzq1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzq1.setText("Rotor Izquierdo:");
        jPanel2.add(jLabelRotorIzq1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 42, -1, -1));

        jLabelRotorCen1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorCen1.setText("Rotor Central:");
        jPanel2.add(jLabelRotorCen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 124, -1, -1));

        jTextFieldRotorCen1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorCen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 158, 250, -1));

        jLabelRotorDer1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorDer1.setText("Rotor Derecho:");
        jPanel2.add(jLabelRotorDer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 206, -1, -1));

        jTextFieldRotorDer1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorDer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 240, 250, -1));

        jTextFieldReflector2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldReflector2, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 322, 250, -1));

        jLabelReflector2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelReflector2.setText("Reflector:");
        jPanel2.add(jLabelReflector2, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 288, -1, -1));

        jCheckBox1.setText("jCheckBox1");
        jPanel2.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, -1, -1));

        jCheckBoxRotorIzqAmp2.setText("Por defecto");
        jCheckBoxRotorIzqAmp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRotorIzqAmp2ActionPerformed(evt);
            }
        });
        jPanel2.add(jCheckBoxRotorIzqAmp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, -1, -1));

        jCheckBox3.setText("jCheckBox1");
        jPanel2.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, -1, -1));

        jCheckBox4.setText("jCheckBox1");
        jPanel2.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 208, -1, -1));

        jButton2.setText("Validar");
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, -1, -1));

        jButton3.setText("jButton3");
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, -1, -1));

        jButton4.setText("jButton4");
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, -1, -1));

        jButton5.setText("jButton5");
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, -1, -1));

        jTabbedPane1.addTab("Enigma ampliada", jPanel2);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("Enigma ampliada ++", jPanel5);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Generar");
        jPanel7.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, -1, -1));

        jLabel6.setText("Longitud");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, -1));

        jLabel10.setText("Contraseña");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        jCheckBox2.setText("Símbolos");
        jCheckBox2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel7.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1, -1));

        jTextField1.setText("jTextField1");
        jPanel7.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, -1, -1));

        jTextField2.setText("jTextField2");
        jPanel7.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, -1, -1));

        jCheckBox5.setText("Rotor Ampliado");
        jPanel7.add(jCheckBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, -1, -1));

        jCheckBox6.setText("Rotor/Reflector ampliado++");
        jPanel7.add(jCheckBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, -1, -1));

        jCheckBox8.setText("Reflector ampliado");
        jPanel7.add(jCheckBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, -1, -1));
        jPanel7.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 500, 10));

        jTabbedPane1.addTab("Generardor de contraseñas", jPanel7);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Simulador Enigma ++");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("Simulador de la Máquina Enigma original y de la ampliación");

        getAccessibleContext().setAccessibleName("jFrame");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonClavijaAdd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClavijaAdd1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClavijaAdd1MouseClicked

    private void jButtonClavijaDelete1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClavijaDelete1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClavijaDelete1MouseClicked

    private void jButtonClavijaDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClavijaDeleteMouseClicked
        String s[];
        if (!jList1.isSelectionEmpty()) {
            conex.remove(jList1.getSelectedIndex());
            s = new String[conex.size()];
            for (int i = 0; i < conex.size(); i++) {
                s[i] = conex.get(i).getPrimera() + " - " + conex.get(i).getSegunda();
            }
            jList1.setListData(s);
        }
    }//GEN-LAST:event_jButtonClavijaDeleteMouseClicked

    private void jButtonClavijaAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClavijaAddMouseClicked
        String s[];
        boolean nuevo = false;
        if ((jTextFieldClavija1.getText().length() & jTextFieldClavija2.getText().length()) > 0) {
            char c1 = EjecutarEnigma.pasarMayus(jTextFieldClavija1.getText().charAt(0)),
                    c2 = EjecutarEnigma.pasarMayus(jTextFieldClavija2.getText().charAt(0));
            if (c1 != c2) {
                if (conex.isEmpty()) {
                    conex.add(new Clavijas(c1, c2));
                } else {
                    for (Clavijas cla : conex) {
                        if (!(cla.getPrimera() == c1 || cla.getSegunda() == c1) && !(cla.getPrimera() == c2 || cla.getSegunda() == c2)) {
                            nuevo = true;
                        }
                    }
                    if (nuevo) {
                        conex.add(new Clavijas(c1, c2));
                    }
                }
                s = new String[conex.size()];
                for (int i = 0; i < conex.size(); i++) {
                    s[i] = conex.get(i).getPrimera() + " - " + conex.get(i).getSegunda();
                }
                jList1.setListData(s);
            }
        }
    }//GEN-LAST:event_jButtonClavijaAddMouseClicked

    private void jCheckBoxRotorIzqAmp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRotorIzqAmp2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxRotorIzqAmp2ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

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
        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new menu().setVisible(true);
            }
        });*/
        new menu().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonClavijaAdd;
    private javax.swing.JButton jButtonClavijaAdd1;
    private javax.swing.JButton jButtonClavijaDelete;
    private javax.swing.JButton jButtonClavijaDelete1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBoxRotorIzqAmp2;
    private javax.swing.JComboBox<String> jComboBoxRotorCen;
    private javax.swing.JComboBox<String> jComboBoxRotorDer;
    private javax.swing.JComboBox<String> jComboBoxRotorIzq;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelBufferedImage;
    private javax.swing.JLabel jLabelCifrado;
    private javax.swing.JLabel jLabelCifrado1;
    private javax.swing.JLabel jLabelMensaje;
    private javax.swing.JLabel jLabelMensaje1;
    private javax.swing.JLabel jLabelReflector2;
    private javax.swing.JLabel jLabelRotorCen1;
    private javax.swing.JLabel jLabelRotorDer1;
    private javax.swing.JLabel jLabelRotorIzq1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldCifrado;
    private javax.swing.JTextField jTextFieldCifrado1;
    private javax.swing.JTextField jTextFieldClaveCen;
    private javax.swing.JTextField jTextFieldClaveCen1;
    private javax.swing.JTextField jTextFieldClaveDer;
    private javax.swing.JTextField jTextFieldClaveDer1;
    private javax.swing.JTextField jTextFieldClaveIzq;
    private javax.swing.JTextField jTextFieldClaveIzq1;
    private javax.swing.JTextField jTextFieldClavija1;
    private javax.swing.JTextField jTextFieldClavija2;
    private javax.swing.JTextField jTextFieldClavija3;
    private javax.swing.JTextField jTextFieldClavija4;
    public javax.swing.JTextField jTextFieldMensaje;
    public javax.swing.JTextField jTextFieldMensaje1;
    public javax.swing.JTextField jTextFieldReflector2;
    public javax.swing.JTextField jTextFieldRotorCen1;
    public javax.swing.JTextField jTextFieldRotorDer1;
    public javax.swing.JTextField jTextFieldRotorIzq1;
    private javax.swing.JLabel jlabelCaminoIda;
    private javax.swing.JLabel jlabelCaminoVuelta;
    private javax.swing.JLabel jlabelClave;
    // End of variables declaration//GEN-END:variables
}
