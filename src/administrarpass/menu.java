package administrarpass;

import static administrarpass.EjecutarEnigma.procesar;
import static administrarpass.GeneradorContrasenas.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.Clipboard.getSystemClipboard;
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

    private void iniciar() {
        ((AbstractDocument) jTextFieldMensaje.getDocument()).setDocumentFilter(new filtroMensaje());
        jTextFieldMensaje.getDocument().addDocumentListener(new listenerMensaje());

        ((AbstractDocument) jTextFieldClaveIzq.getDocument()).setDocumentFilter(new filtro1char());
        ((AbstractDocument) jTextFieldClaveCen.getDocument()).setDocumentFilter(new filtro1char());
        ((AbstractDocument) jTextFieldClaveDer.getDocument()).setDocumentFilter(new filtro1char());

        ((AbstractDocument) jTextFieldClavija1.getDocument()).setDocumentFilter(new filtro1char());
        ((AbstractDocument) jTextFieldClavija2.getDocument()).setDocumentFilter(new filtro1char());

        ((AbstractDocument) jTextFieldLongitudPass.getDocument()).setDocumentFilter(new filtroInt());
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

        jListClavijas.setEnabled(b);
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

    class filtroInt extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            char c = 0;
            String s = "";
            for (int i = 0; i < text.length(); i++) {
                c = text.charAt(i);
                if (c >= 48 && c <= 57) {
                    s += c;
                }
            }
            super.replace(fb, offset, length, s, attrs);
        }
    }

    private void initialize() {
        posRY = 150;    //Reflector
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

        buttonGroupGenerador = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabelRotorIzq = new javax.swing.JLabel();
        jLabelRotorCen = new javax.swing.JLabel();
        jLabelRotorDer = new javax.swing.JLabel();
        jComboBoxRotorIzq = new javax.swing.JComboBox<>();
        jLabelClaves = new javax.swing.JLabel();
        jTextFieldClaveIzq = new javax.swing.JTextField();
        jTextFieldClaveCen = new javax.swing.JTextField();
        jTextFieldClaveDer = new javax.swing.JTextField();
        jTextFieldMensaje = new javax.swing.JTextField();
        jLabelMensaje = new javax.swing.JLabel();
        jLabelCifrado = new javax.swing.JLabel();
        jTextFieldCifrado = new javax.swing.JTextField();
        jLabelClavijas = new javax.swing.JLabel();
        jTextFieldClavija1 = new javax.swing.JTextField();
        jTextFieldClavija2 = new javax.swing.JTextField();
        jButtonClavijaAdd = new javax.swing.JButton();
        jComboBoxRotorCen = new javax.swing.JComboBox<>();
        jComboBoxRotorDer = new javax.swing.JComboBox<>();
        jButtonClavijaDelete = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListClavijas = new javax.swing.JList<>();
        jLabelBufferedImage = new javax.swing.JLabel();
        jlabelLeyendaClave = new javax.swing.JLabel();
        jlabelLeyendaCaminoIda = new javax.swing.JLabel();
        jlabelLeyendaCaminoVuelta = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabelMensaje1 = new javax.swing.JLabel();
        jTextFieldMensajeAmp = new javax.swing.JTextField();
        jLabelCifrado1 = new javax.swing.JLabel();
        jTextFieldCifradoAmp = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabelClavesAmp = new javax.swing.JLabel();
        jTextFieldClaveIzqAmp = new javax.swing.JTextField();
        jTextFieldClaveCenAmp = new javax.swing.JTextField();
        jTextFieldClaveDerAmp = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabelClavijasAmp = new javax.swing.JLabel();
        jTextFieldClavijaAmp1 = new javax.swing.JTextField();
        jTextFieldClavijaAmp2 = new javax.swing.JTextField();
        jButtonClavijaAddAmp = new javax.swing.JButton();
        jButtonClavijaDeleteAmp = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListClavijasAmp = new javax.swing.JList<>();
        jTextFieldRotorIzqAmp = new javax.swing.JTextField();
        jLabelRotorIzqAmp = new javax.swing.JLabel();
        jLabelRotorCenAmp = new javax.swing.JLabel();
        jTextFieldRotorCenAmp = new javax.swing.JTextField();
        jLabelRotorDerAmp = new javax.swing.JLabel();
        jTextFieldRotorDerAmp = new javax.swing.JTextField();
        jTextFieldReflectorAmp = new javax.swing.JTextField();
        jLabelReflectorAmp = new javax.swing.JLabel();
        jCheckBoxReflectorAmp = new javax.swing.JCheckBox();
        jCheckBoxRotorIzqAmp = new javax.swing.JCheckBox();
        jCheckBoxRotorCenAmp = new javax.swing.JCheckBox();
        jCheckBoxRotorDerAmp = new javax.swing.JCheckBox();
        jButtonValidarRI = new javax.swing.JButton();
        jButtonValidarRC = new javax.swing.JButton();
        jButtonValidarRD = new javax.swing.JButton();
        jButtonCifrarAmp = new javax.swing.JButton();
        jButtonValidarR = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButtonGenerarRotor = new javax.swing.JButton();
        jCheckBoxSymbols = new javax.swing.JCheckBox();
        jSeparator5 = new javax.swing.JSeparator();
        jTextFieldLongitudPass = new javax.swing.JTextField();
        jLabelMensaje2 = new javax.swing.JLabel();
        jTextFieldRotorReflector = new javax.swing.JTextField();
        jTextFieldPass = new javax.swing.JTextField();
        jLabelMensaje3 = new javax.swing.JLabel();
        jLabelMensaje4 = new javax.swing.JLabel();
        jButtonGenerarPass = new javax.swing.JButton();
        jLabelMensaje5 = new javax.swing.JLabel();
        jLabelMensaje6 = new javax.swing.JLabel();
        jCheckBoxLongitudPass = new javax.swing.JCheckBox();
        jRadioButtonRRAmpPlus = new javax.swing.JRadioButton();
        jRadioButtonRotorAmp = new javax.swing.JRadioButton();
        jRadioButtonReflectorAmp = new javax.swing.JRadioButton();
        jButtonGenerarPassCopy = new javax.swing.JButton();
        jButtonGenerarRotorCopy = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 700));

        jPanel1.setPreferredSize(new java.awt.Dimension(805, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelRotorIzq.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzq.setText("Rotor Izquierdo:");
        jPanel1.add(jLabelRotorIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 26, -1, -1));

        jLabelRotorCen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorCen.setText("Rotor Central:");
        jPanel1.add(jLabelRotorCen, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 76, -1, -1));

        jLabelRotorDer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorDer.setText("Rotor Derecho:");
        jPanel1.add(jLabelRotorDer, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 126, -1, -1));

        jComboBoxRotorIzq.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxRotorIzq.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I", "II", "III", "IV", "V" }));
        jPanel1.add(jComboBoxRotorIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 21, -1, -1));

        jLabelClaves.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClaves.setText("Claves:");
        jPanel1.add(jLabelClaves, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 181, -1, -1));

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

        jLabelClavijas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavijas.setText("Clavijas:");
        jPanel1.add(jLabelClavijas, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 226, -1, -1));

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
        jPanel1.add(jButtonClavijaAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 270, -1, -1));

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
        jPanel1.add(jButtonClavijaDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 270, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 165, 221, 10));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 211, 221, 9));

        jListClavijas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(jListClavijas);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 330, 84, 302));
        jPanel1.add(jLabelBufferedImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 480));

        jlabelLeyendaClave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlabelLeyendaClave.setForeground(new java.awt.Color(0, 220, 220));
        jlabelLeyendaClave.setText("Clave");
        jPanel1.add(jlabelLeyendaClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, -1, -1));

        jlabelLeyendaCaminoIda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlabelLeyendaCaminoIda.setForeground(Color.red);
        jlabelLeyendaCaminoIda.setText("Camino Ida");
        jPanel1.add(jlabelLeyendaCaminoIda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, -1, -1));

        jlabelLeyendaCaminoVuelta.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlabelLeyendaCaminoVuelta.setForeground(Color.magenta);
        jlabelLeyendaCaminoVuelta.setText("Camino Vuelta");
        jPanel1.add(jlabelLeyendaCaminoVuelta, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, -1, -1));

        jTabbedPane1.addTab("Enigma clásica", jPanel1);

        jPanel2.setName(""); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelMensaje1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje1.setText("Introduce tu mensaje:");
        jPanel2.add(jLabelMensaje1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 415, -1, -1));

        jTextFieldMensajeAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldMensajeAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 449, 389, -1));

        jLabelCifrado1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCifrado1.setText("Mensaje cifrado:");
        jPanel2.add(jLabelCifrado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 497, -1, -1));

        jTextFieldCifradoAmp.setEditable(false);
        jTextFieldCifradoAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCifradoAmp.setDragEnabled(true);
        jPanel2.add(jTextFieldCifradoAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 525, 389, -1));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 157, 221, 10));

        jLabelClavesAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavesAmp.setText("Claves:");
        jPanel2.add(jLabelClavesAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 173, -1, -1));

        jTextFieldClaveIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveIzqAmp.setText("A");
        jTextFieldClaveIzqAmp.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 174, -1, -1));

        jTextFieldClaveCenAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveCenAmp.setText("A");
        jTextFieldClaveCenAmp.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(664, 174, -1, -1));

        jTextFieldClaveDerAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveDerAmp.setText("A");
        jTextFieldClaveDerAmp.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 174, -1, -1));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 207, 221, 9));

        jLabelClavijasAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavijasAmp.setText("Clavijas:");
        jPanel2.add(jLabelClavijasAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 219, -1, -1));

        jTextFieldClavijaAmp1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavijaAmp1.setText("A");
        jTextFieldClavijaAmp1.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClavijaAmp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 220, -1, -1));

        jTextFieldClavijaAmp2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavijaAmp2.setText("A");
        jTextFieldClavijaAmp2.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClavijaAmp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(664, 220, -1, -1));

        jButtonClavijaAddAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaAddAmp.setText("Añadir");
        jButtonClavijaAddAmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonClavijaAddAmpMouseClicked(evt);
            }
        });
        jPanel2.add(jButtonClavijaAddAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 270, -1, -1));

        jButtonClavijaDeleteAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaDeleteAmp.setText("Eliminar");
        jButtonClavijaDeleteAmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonClavijaDeleteAmpMouseClicked(evt);
            }
        });
        jPanel2.add(jButtonClavijaDeleteAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 270, -1, -1));

        jListClavijasAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane3.setViewportView(jListClavijasAmp);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, 84, 302));

        jTextFieldRotorIzqAmp.setEditable(false);
        jTextFieldRotorIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 76, 330, -1));

        jLabelRotorIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmp.setText("Rotor Izquierdo:");
        jPanel2.add(jLabelRotorIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 42, -1, -1));

        jLabelRotorCenAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorCenAmp.setText("Rotor Central:");
        jPanel2.add(jLabelRotorCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 124, -1, -1));

        jTextFieldRotorCenAmp.setEditable(false);
        jTextFieldRotorCenAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 158, 330, -1));

        jLabelRotorDerAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorDerAmp.setText("Rotor Derecho:");
        jPanel2.add(jLabelRotorDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 206, -1, -1));

        jTextFieldRotorDerAmp.setEditable(false);
        jTextFieldRotorDerAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 240, 330, -1));

        jTextFieldReflectorAmp.setEditable(false);
        jTextFieldReflectorAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldReflectorAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 322, 330, -1));

        jLabelReflectorAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelReflectorAmp.setText("Reflector:");
        jPanel2.add(jLabelReflectorAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 288, -1, -1));

        jCheckBoxReflectorAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxReflectorAmp.setSelected(true);
        jCheckBoxReflectorAmp.setText("Por defecto");
        jCheckBoxReflectorAmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBoxReflectorAmpMouseClicked(evt);
            }
        });
        jPanel2.add(jCheckBoxReflectorAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 290, -1, -1));

        jCheckBoxRotorIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorIzqAmp.setSelected(true);
        jCheckBoxRotorIzqAmp.setText("Por defecto");
        jCheckBoxRotorIzqAmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBoxRotorIzqAmpMouseClicked(evt);
            }
        });
        jPanel2.add(jCheckBoxRotorIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 40, -1, -1));

        jCheckBoxRotorCenAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorCenAmp.setSelected(true);
        jCheckBoxRotorCenAmp.setText("Por defecto");
        jCheckBoxRotorCenAmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBoxRotorCenAmpMouseClicked(evt);
            }
        });
        jPanel2.add(jCheckBoxRotorCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 120, -1, -1));

        jCheckBoxRotorDerAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorDerAmp.setSelected(true);
        jCheckBoxRotorDerAmp.setText("Por defecto");
        jCheckBoxRotorDerAmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBoxRotorDerAmpMouseClicked(evt);
            }
        });
        jPanel2.add(jCheckBoxRotorDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 208, -1, -1));

        jButtonValidarRI.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRI.setText("Validar");
        jButtonValidarRI.setEnabled(false);
        jButtonValidarRI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonValidarRIMouseClicked(evt);
            }
        });
        jPanel2.add(jButtonValidarRI, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, -1));

        jButtonValidarRC.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRC.setText("Validar");
        jButtonValidarRC.setEnabled(false);
        jButtonValidarRC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonValidarRCMouseClicked(evt);
            }
        });
        jPanel2.add(jButtonValidarRC, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, -1, -1));

        jButtonValidarRD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRD.setText("Validar");
        jButtonValidarRD.setEnabled(false);
        jButtonValidarRD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonValidarRDMouseClicked(evt);
            }
        });
        jPanel2.add(jButtonValidarRD, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, -1, -1));

        jButtonCifrarAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCifrarAmp.setText("Cifrar");
        jButtonCifrarAmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonCifrarAmpMouseClicked(evt);
            }
        });
        jPanel2.add(jButtonCifrarAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 440, -1, -1));

        jButtonValidarR.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarR.setText("Validar");
        jButtonValidarR.setEnabled(false);
        jButtonValidarR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonValidarRMouseClicked(evt);
            }
        });
        jPanel2.add(jButtonValidarR, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 310, -1, -1));

        jTabbedPane1.addTab("Enigma ampliada", jPanel2);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("Enigma ampliada ++", jPanel5);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonGenerarRotor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonGenerarRotor.setText("Generar");
        jButtonGenerarRotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGenerarRotorMouseClicked(evt);
            }
        });
        jPanel7.add(jButtonGenerarRotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 480, -1, -1));

        jCheckBoxSymbols.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBoxSymbols.setText("Símbolos");
        jCheckBoxSymbols.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel7.add(jCheckBoxSymbols, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));
        jPanel7.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, 680, 10));

        jTextFieldLongitudPass.setEditable(false);
        jTextFieldLongitudPass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel7.add(jTextFieldLongitudPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 70, -1));

        jLabelMensaje2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje2.setText("Longitud:");
        jPanel7.add(jLabelMensaje2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        jTextFieldRotorReflector.setEditable(false);
        jTextFieldRotorReflector.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel7.add(jTextFieldRotorReflector, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, 700, -1));

        jTextFieldPass.setEditable(false);
        jTextFieldPass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel7.add(jTextFieldPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 700, -1));

        jLabelMensaje3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje3.setText("Generador de contraseñas:");
        jPanel7.add(jLabelMensaje3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabelMensaje4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje4.setText("Contraseña:");
        jPanel7.add(jLabelMensaje4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        jButtonGenerarPass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonGenerarPass.setText("Generar");
        jButtonGenerarPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGenerarPassMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonGenerarPassMouseEntered(evt);
            }
        });
        jPanel7.add(jButtonGenerarPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, -1, -1));

        jLabelMensaje5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje5.setText("Rotor:");
        jPanel7.add(jLabelMensaje5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, -1, -1));

        jLabelMensaje6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje6.setText("Generador de rotores y reflectores:");
        jPanel7.add(jLabelMensaje6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, -1, -1));

        jCheckBoxLongitudPass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxLongitudPass.setSelected(true);
        jCheckBoxLongitudPass.setText("Por defecto");
        jCheckBoxLongitudPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBoxLongitudPassMouseClicked(evt);
            }
        });
        jPanel7.add(jCheckBoxLongitudPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, -1));

        buttonGroupGenerador.add(jRadioButtonRRAmpPlus);
        jRadioButtonRRAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonRRAmpPlus.setText("Rotor/Reflector Ampliado++");
        jPanel7.add(jRadioButtonRRAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 420, -1, -1));

        buttonGroupGenerador.add(jRadioButtonRotorAmp);
        jRadioButtonRotorAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonRotorAmp.setSelected(true);
        jRadioButtonRotorAmp.setText("Rotor Ampliado");
        jPanel7.add(jRadioButtonRotorAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, -1));

        buttonGroupGenerador.add(jRadioButtonReflectorAmp);
        jRadioButtonReflectorAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonReflectorAmp.setText("Reflector Ampliado");
        jPanel7.add(jRadioButtonReflectorAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, -1, -1));

        jButtonGenerarPassCopy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonGenerarPassCopy.setText("Copiar");
        jButtonGenerarPassCopy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGenerarPassCopyMouseClicked(evt);
            }
        });
        jPanel7.add(jButtonGenerarPassCopy, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 210, -1, -1));

        jButtonGenerarRotorCopy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonGenerarRotorCopy.setText("Copiar");
        jButtonGenerarRotorCopy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGenerarRotorCopyMouseClicked(evt);
            }
        });
        jPanel7.add(jButtonGenerarRotorCopy, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 480, -1, -1));

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

    private void jButtonClavijaAddAmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClavijaAddAmpMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClavijaAddAmpMouseClicked

    private void jButtonClavijaDeleteAmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClavijaDeleteAmpMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClavijaDeleteAmpMouseClicked

    private void jButtonClavijaDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClavijaDeleteMouseClicked
        String s[];
        if (!jListClavijas.isSelectionEmpty()) {
            conex.remove(jListClavijas.getSelectedIndex());
            s = new String[conex.size()];
            for (int i = 0; i < conex.size(); i++) {
                s[i] = conex.get(i).getPrimera() + " - " + conex.get(i).getSegunda();
            }
            jListClavijas.setListData(s);
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
                jListClavijas.setListData(s);
            }
        }
    }//GEN-LAST:event_jButtonClavijaAddMouseClicked

    private void jButtonGenerarPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGenerarPassMouseClicked
        setSymbolic(jCheckBoxSymbols.isSelected() ? 4 : 3);
        if (!jCheckBoxLongitudPass.isSelected()) {
            setN(Integer.parseInt(jTextFieldLongitudPass.getText()));
        }
        jTextFieldPass.setText(aleatorio());
    }//GEN-LAST:event_jButtonGenerarPassMouseClicked

    private void jButtonGenerarRotorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGenerarRotorMouseClicked
        if (jRadioButtonRotorAmp.isSelected()) {
            jTextFieldRotorReflector.setText(rotoresAleatoriosAmp(false));
        } else if (jRadioButtonReflectorAmp.isSelected()) {
            jTextFieldRotorReflector.setText(rotoresReflectorAmp());
        } else if (jRadioButtonRRAmpPlus.isSelected()) {
            jTextFieldRotorReflector.setText(rotoresAleatoriosAmp(true));
        }
    }//GEN-LAST:event_jButtonGenerarRotorMouseClicked

    private void jButtonValidarRIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonValidarRIMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonValidarRIMouseClicked

    private void jButtonValidarRCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonValidarRCMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonValidarRCMouseClicked

    private void jButtonValidarRDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonValidarRDMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonValidarRDMouseClicked

    private void jButtonValidarRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonValidarRMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonValidarRMouseClicked

    private void jButtonCifrarAmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCifrarAmpMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCifrarAmpMouseClicked

    private void jCheckBoxRotorIzqAmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBoxRotorIzqAmpMouseClicked
        changeButtonStateValidar(jTextFieldRotorIzqAmp, jButtonValidarRI);
    }//GEN-LAST:event_jCheckBoxRotorIzqAmpMouseClicked

    private void jCheckBoxRotorCenAmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBoxRotorCenAmpMouseClicked
        changeButtonStateValidar(jTextFieldRotorCenAmp, jButtonValidarRC);
    }//GEN-LAST:event_jCheckBoxRotorCenAmpMouseClicked

    private void jCheckBoxRotorDerAmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBoxRotorDerAmpMouseClicked
        changeButtonStateValidar(jTextFieldRotorDerAmp, jButtonValidarRD);
    }//GEN-LAST:event_jCheckBoxRotorDerAmpMouseClicked

    private void jCheckBoxReflectorAmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBoxReflectorAmpMouseClicked
        changeButtonStateValidar(jTextFieldReflectorAmp, jButtonValidarR);
    }//GEN-LAST:event_jCheckBoxReflectorAmpMouseClicked

    private void jCheckBoxLongitudPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBoxLongitudPassMouseClicked
        setN(20);
        changeButtonStateValidar(jTextFieldLongitudPass);
    }//GEN-LAST:event_jCheckBoxLongitudPassMouseClicked

    private void jButtonGenerarPassCopyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGenerarPassCopyMouseClicked
        copyToClipboard(jTextFieldPass);
    }//GEN-LAST:event_jButtonGenerarPassCopyMouseClicked

    private void jButtonGenerarRotorCopyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGenerarRotorCopyMouseClicked
        copyToClipboard(jTextFieldRotorReflector);
    }//GEN-LAST:event_jButtonGenerarRotorCopyMouseClicked

    private void jButtonGenerarPassMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGenerarPassMouseEntered
        if (!jCheckBoxLongitudPass.isSelected()) {
            if (jTextFieldLongitudPass.getText().isEmpty()) {
                jButtonGenerarPass.setEnabled(false);
                jButtonGenerarPassCopy.setEnabled(false);
            } else {
                jButtonGenerarPass.setEnabled(true);
                jButtonGenerarPassCopy.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jButtonGenerarPassMouseEntered

    private void copyToClipboard(javax.swing.JTextField jtextfield) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(jtextfield.getText()), null);
    }

    private void changeButtonStateValidar(javax.swing.JTextField jtextfield) {
        jtextfield.setEditable(!jtextfield.isEditable());
    }

    private void changeButtonStateValidar(javax.swing.JTextField jtextfield, javax.swing.JButton jbutton) {
        jtextfield.setEditable(!jtextfield.isEditable());
        jbutton.setEnabled(!jbutton.isEnabled());
    }

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
    private javax.swing.ButtonGroup buttonGroupGenerador;
    private javax.swing.JButton jButtonCifrarAmp;
    private javax.swing.JButton jButtonClavijaAdd;
    private javax.swing.JButton jButtonClavijaAddAmp;
    private javax.swing.JButton jButtonClavijaDelete;
    private javax.swing.JButton jButtonClavijaDeleteAmp;
    private javax.swing.JButton jButtonGenerarPass;
    private javax.swing.JButton jButtonGenerarPassCopy;
    private javax.swing.JButton jButtonGenerarRotor;
    private javax.swing.JButton jButtonGenerarRotorCopy;
    private javax.swing.JButton jButtonValidarR;
    private javax.swing.JButton jButtonValidarRC;
    private javax.swing.JButton jButtonValidarRD;
    private javax.swing.JButton jButtonValidarRI;
    private javax.swing.JCheckBox jCheckBoxLongitudPass;
    private javax.swing.JCheckBox jCheckBoxReflectorAmp;
    private javax.swing.JCheckBox jCheckBoxRotorCenAmp;
    private javax.swing.JCheckBox jCheckBoxRotorDerAmp;
    private javax.swing.JCheckBox jCheckBoxRotorIzqAmp;
    private javax.swing.JCheckBox jCheckBoxSymbols;
    private javax.swing.JComboBox<String> jComboBoxRotorCen;
    private javax.swing.JComboBox<String> jComboBoxRotorDer;
    private javax.swing.JComboBox<String> jComboBoxRotorIzq;
    private javax.swing.JLabel jLabelBufferedImage;
    private javax.swing.JLabel jLabelCifrado;
    private javax.swing.JLabel jLabelCifrado1;
    private javax.swing.JLabel jLabelClaves;
    private javax.swing.JLabel jLabelClavesAmp;
    private javax.swing.JLabel jLabelClavijas;
    private javax.swing.JLabel jLabelClavijasAmp;
    private javax.swing.JLabel jLabelMensaje;
    private javax.swing.JLabel jLabelMensaje1;
    private javax.swing.JLabel jLabelMensaje2;
    private javax.swing.JLabel jLabelMensaje3;
    private javax.swing.JLabel jLabelMensaje4;
    private javax.swing.JLabel jLabelMensaje5;
    private javax.swing.JLabel jLabelMensaje6;
    private javax.swing.JLabel jLabelReflectorAmp;
    private javax.swing.JLabel jLabelRotorCen;
    private javax.swing.JLabel jLabelRotorCenAmp;
    private javax.swing.JLabel jLabelRotorDer;
    private javax.swing.JLabel jLabelRotorDerAmp;
    private javax.swing.JLabel jLabelRotorIzq;
    private javax.swing.JLabel jLabelRotorIzqAmp;
    private javax.swing.JList<String> jListClavijas;
    private javax.swing.JList<String> jListClavijasAmp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButtonRRAmpPlus;
    private javax.swing.JRadioButton jRadioButtonReflectorAmp;
    private javax.swing.JRadioButton jRadioButtonRotorAmp;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldCifrado;
    private javax.swing.JTextField jTextFieldCifradoAmp;
    private javax.swing.JTextField jTextFieldClaveCen;
    private javax.swing.JTextField jTextFieldClaveCenAmp;
    private javax.swing.JTextField jTextFieldClaveDer;
    private javax.swing.JTextField jTextFieldClaveDerAmp;
    private javax.swing.JTextField jTextFieldClaveIzq;
    private javax.swing.JTextField jTextFieldClaveIzqAmp;
    private javax.swing.JTextField jTextFieldClavija1;
    private javax.swing.JTextField jTextFieldClavija2;
    private javax.swing.JTextField jTextFieldClavijaAmp1;
    private javax.swing.JTextField jTextFieldClavijaAmp2;
    public javax.swing.JTextField jTextFieldLongitudPass;
    public javax.swing.JTextField jTextFieldMensaje;
    public javax.swing.JTextField jTextFieldMensajeAmp;
    public javax.swing.JTextField jTextFieldPass;
    public javax.swing.JTextField jTextFieldReflectorAmp;
    public javax.swing.JTextField jTextFieldRotorCenAmp;
    public javax.swing.JTextField jTextFieldRotorDerAmp;
    public javax.swing.JTextField jTextFieldRotorIzqAmp;
    public javax.swing.JTextField jTextFieldRotorReflector;
    private javax.swing.JLabel jlabelLeyendaCaminoIda;
    private javax.swing.JLabel jlabelLeyendaCaminoVuelta;
    private javax.swing.JLabel jlabelLeyendaClave;
    // End of variables declaration//GEN-END:variables
}
