package administrarpass;

import static administrarpass.EjecutarEnigma.*;
import static administrarpass.GeneradorContrasenas.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
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
public class Menu extends JFrame {

    private final Rotor rI = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q'); //tipo I Q
    private final Rotor rII = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E'); //tipo II E
    private final Rotor rIII = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V'); //tipo III V
    private final Rotor rIV = new Rotor("ESOVPZJAYQUIRHXLNFTGKDCMWB", 'J'); //tipo IV J
    private final Rotor rV = new Rotor("VZBRGITYUPSDNHLXAWMJQOFECK", 'Z'); //tipo V Z
    private final Rotor rIzqAmp = new Rotor("4\"HIr(c36!5fs+kB28l#$mjO%tGAo)*1Md9Dy7'EhWJgavVS0exRN ZuF&PnqYipCUzLwTbKQX", 'X'); //NUEVO tipo Izq X
    private final Rotor rCenAmp = new Rotor("YZh!+76W&Ig%fiPsnUuSQC21v49q3B'5j)(TA8kGdmX$ O#0KbRJF*HNtEwc\"zVxaoerMplDyL", '\''); //NUEVO tipo Cen '
    private final Rotor rDerAmp = new Rotor("X14&w92 5!\"khP#Ij6*BdvLQGUE%p$ql7Fa3KuDn(Z8MbmTo'iHSWA0YCRytJ+OfzVsgN)cxre", 'J'); //NUEVO tipo Der J
    private Rotor rIzqAmpNuevo;
    private Rotor rCenAmpNuevo;
    private Rotor rDerAmpNuevo;
    private Rotor reflectorAmpNuevo;
    private final Rotor rIzqAmpPlus = new Rotor("#1q2OXy%x\"*(7GK+QPc$C4M5s!fEHrTiw8udRnFj&Y9)3aep0U6DBvAkJ'lIShgotmzZWVNLb", 'h'); //NUEVO tipo Izq Plus h
    private final Rotor rCenAmpPlus = new Rotor("R9(24Dpd%yKV0F!l#Jmz&gA)H87UQ3ErBSPv1TOc65'*n$LCwGsYa+WktMuf\"IeqhjXboiZNx", 'I'); //NUEVO tipo Cen Plus I 
    private final Rotor rDerAmpPlus = new Rotor("0xoOdh2G91DyXn+3kT8BP$(4\"V*#r6')R5%lbpt!ajigvCUYIsZN7zJWemwfcAFHuSEqKL&QM", 'i'); //NUEVO tipo Der Plus i
    private Rotor rIzqAmpPlusNuevo;
    private Rotor rCenAmpPlusNuevo;
    private Rotor rDerAmpPlusNuevo;
    private Rotor reflectorAmpPlusNuevo;
    private boolean eni = false;
    private boolean validarRotorIzqAmp = false;
    private boolean validarRotorCenAmp = false;
    private boolean validarRotorDerAmp = false;
    private boolean validarReflectorAmp = false;
    private boolean aceptarRotorIzqAmp = false;
    private boolean aceptarRotorCenAmp = false;
    private boolean aceptarRotorDerAmp = false;
    private boolean validarRotorIzqAmpPlus = false;
    private boolean validarRotorCenAmpPlus = false;
    private boolean validarRotorDerAmpPlus = false;
    private boolean validarReflectorAmpPlus = false;
    private boolean aceptarRotorIzqAmpPlus = false;
    private boolean aceptarRotorCenAmpPlus = false;
    private boolean aceptarRotorDerAmpPlus = false;
    private static int posIY;
    private static int posCY;
    private static int posDY;
    private static int posTY;
    private static int posRY;
    private static int posClaY;
    private static int contEleccion;
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
    private static char cI;
    private static char cC;
    private static char cD;
    private Enigma enigma;
    private Enigma enigmaAmp;
    private Enigma enigmaAmpPlus;
    private static ArrayList<Clavijas> conex = new ArrayList<>();
    private static ArrayList<Clavijas> conexAmp = new ArrayList<>();
    private static ArrayList<Clavijas> conexAmpPlus = new ArrayList<>();
    private TableroPintar tablero = new TableroPintar();

    /**
     * Creates new form menu
     */
    public Menu() {
        super();
        initComponents();
        addFiltersText();
        inicializarPosicionesYRotores();
        addActionListeners();
    }

    /**
     * Añade los filtros y los listener a las cadenas de texto.
     */
    private void addFiltersText() {
        ((AbstractDocument) jTextFieldMensaje.getDocument()).setDocumentFilter(new FiltroMensaje());
        jTextFieldMensaje.getDocument().addDocumentListener(new ListenerMensaje());

        ((AbstractDocument) jTextFieldClaveIzq.getDocument()).setDocumentFilter(new Filtro1char());
        ((AbstractDocument) jTextFieldClaveCen.getDocument()).setDocumentFilter(new Filtro1char());
        ((AbstractDocument) jTextFieldClaveDer.getDocument()).setDocumentFilter(new Filtro1char());

        ((AbstractDocument) jTextFieldClavija1.getDocument()).setDocumentFilter(new Filtro1char());
        ((AbstractDocument) jTextFieldClavija2.getDocument()).setDocumentFilter(new Filtro1char());

        ((AbstractDocument) jTextFieldLongitudPass.getDocument()).setDocumentFilter(new FiltroInt());
        jTextFieldLongitudPass.getDocument().addDocumentListener(new ListenerLongitudPass());

        /**
         * Ampliado
         */
        jTextFieldRotorIzqAmp.getDocument().addDocumentListener(new ListenerValidezRotoresYReflectores(jButtonValidarRIAmp, jLabelColorValidarIzq, jLabelColorAceptarIzq, false));
        jTextFieldRotorCenAmp.getDocument().addDocumentListener(new ListenerValidezRotoresYReflectores(jButtonValidarRCAmp, jLabelColorValidarCen, jLabelColorAceptarCen, false));
        jTextFieldRotorDerAmp.getDocument().addDocumentListener(new ListenerValidezRotoresYReflectores(jButtonValidarRDAmp, jLabelColorValidarDer, jLabelColorAceptarDer, false));
        jTextFieldReflectorAmp.getDocument().addDocumentListener(new ListenerValidezRotoresYReflectores(jButtonValidarRAmp, jLabelColorValidarR, null, true));

        ((AbstractDocument) jTextFieldClaveIzqAmp.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldClaveCenAmp.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldClaveDerAmp.getDocument()).setDocumentFilter(new Filtro1Amp());

        ((AbstractDocument) jTextFieldClavijaAmp1.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldClavijaAmp2.getDocument()).setDocumentFilter(new Filtro1Amp());

        ((AbstractDocument) jTextFieldRotorIzqAmpGiro.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldRotorCenAmpGiro.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldRotorDerAmpGiro.getDocument()).setDocumentFilter(new Filtro1Amp());

        ((AbstractDocument) jTextFieldMensajeAmp.getDocument()).setDocumentFilter(new FiltroMensajeAmp());

        /**
         * Ampliado Plus
         */
        jTextFieldRotorIzqAmpPlus.getDocument().addDocumentListener(new ListenerValidezRotoresYReflectores(jButtonValidarRIAmpPlus, jLabelColorValidarIzqPlus, jLabelColorAceptarIzqPlus, false));
        jTextFieldRotorCenAmpPlus.getDocument().addDocumentListener(new ListenerValidezRotoresYReflectores(jButtonValidarRCAmpPlus, jLabelColorValidarCenPlus, jLabelColorAceptarCenPlus, false));
        jTextFieldRotorDerAmpPlus.getDocument().addDocumentListener(new ListenerValidezRotoresYReflectores(jButtonValidarRDAmpPlus, jLabelColorValidarDerPlus, jLabelColorAceptarDerPlus, false));
        jTextFieldReflectorAmpPlus.getDocument().addDocumentListener(new ListenerValidezRotoresYReflectores(jButtonValidarRAmpPlus, jLabelColorValidarRPlus, null, true));

        ((AbstractDocument) jTextFieldClaveIzqAmpPlus.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldClaveCenAmpPlus.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldClaveDerAmpPlus.getDocument()).setDocumentFilter(new Filtro1Amp());

        ((AbstractDocument) jTextFieldClavijaAmpPlus1.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldClavijaAmpPlus2.getDocument()).setDocumentFilter(new Filtro1Amp());

        ((AbstractDocument) jTextFieldRotorIzqAmpGiroPlus.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldRotorCenAmpGiroPlus.getDocument()).setDocumentFilter(new Filtro1Amp());
        ((AbstractDocument) jTextFieldRotorDerAmpGiroPlus.getDocument()).setDocumentFilter(new Filtro1Amp());

        ((AbstractDocument) jTextFieldMensajeAmpPlus.getDocument()).setDocumentFilter(new FiltroMensajeAmpPlus());
    }

    class ListenerMensaje implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateFieldState(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateFieldState(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateFieldState(e);
        }

        public void updateFieldState(DocumentEvent e) {
            enigma = new Enigma(rotores.get(jComboBoxRotorIzq.getSelectedItem().toString()),
                    rotores.get(jComboBoxRotorCen.getSelectedItem().toString()),
                    rotores.get(jComboBoxRotorDer.getSelectedItem().toString())); // Crea la máquina enigma
            for (Clavijas cla : conex) {
                enigma.ponerClavija(cla.getA(), cla.getB());
            }
            char cIaux = 'A';
            char cCaux = 'A';
            char cDaux = 'A';
            if ((jTextFieldClaveIzq.getText().length() & jTextFieldClaveCen.getText().length() & jTextFieldClaveDer.getText().length()) > 0) {
                cIaux = EjecutarEnigma.pasarMayus(jTextFieldClaveIzq.getText().charAt(0));
                cCaux = EjecutarEnigma.pasarMayus(jTextFieldClaveCen.getText().charAt(0));
                cDaux = EjecutarEnigma.pasarMayus(jTextFieldClaveDer.getText().charAt(0));
            }
            enigma.setRotoresIni(cIaux, cCaux, cDaux);
            String mensaje = jTextFieldMensaje.getText();
            jTextFieldCifrado.setText(procesar(enigma, mensaje));
            int len = e.getDocument().getLength();
            String doc = "";
            try {
                doc = e.getDocument().getText(0, len);
            } catch (BadLocationException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!doc.isEmpty()) {
                eni = true;
                changeEnabled(false);
                repaint();
            } else {
                eni = false;
                changeEnabled(true);
                setcI(cIaux);
                setcC(cCaux);
                setcD(cDaux);
                repaint();
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
    }

    class ListenerLongitudPass implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateFieldState();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateFieldState();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateFieldState();
        }

        public void updateFieldState() {
            if (!jCheckBoxLongitudPass.isSelected()) {
                jButtonGenerarPass.setEnabled(!jTextFieldLongitudPass.getText().isEmpty());
                jButtonGenerarPassCopy.setEnabled(!jTextFieldLongitudPass.getText().isEmpty());
            }
        }
    }

    class ListenerValidezRotoresYReflectores implements DocumentListener {

        private javax.swing.JButton jbuttonValidar;
        private javax.swing.JLabel jlabelValidar;
        private javax.swing.JLabel jlabelAceptar;
        private boolean reflector;

        public ListenerValidezRotoresYReflectores(javax.swing.JButton jbValidar, javax.swing.JLabel jlValidar, javax.swing.JLabel jlAceptar, boolean reflector) {
            jbuttonValidar = jbValidar;
            jlabelValidar = jlValidar;
            if (!reflector) {
                jlabelAceptar = jlAceptar;
            }
            this.reflector = reflector;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateFieldState();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateFieldState();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateFieldState();
        }

        public void updateFieldState() {
            jbuttonValidar.setBackground(Color.magenta);
            jlabelValidar.setBackground(Color.magenta);
            if (!reflector) {
                jlabelAceptar.setBackground(Color.magenta);
            }
        }
    }

    public static void setcI(char cI) {
        Menu.cI = cI;
    }

    public static void setcC(char cC) {
        Menu.cC = cC;
    }

    public static void setcD(char cD) {
        Menu.cD = cD;
    }

    class FiltroMensaje extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            char c = 0;
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                c = text.charAt(i);
                if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                    s.append(c);
                }
            }
            super.replace(fb, offset, length, s.toString(), attrs);
        }
    }

    class FiltroMensajeAmp extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            char c = 0;
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                c = text.charAt(i);
                if (((c >= 32 && c <= 43) || (c >= 47 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122))) {
                    s.append(c);
                }
            }
            super.replace(fb, offset, length, s.toString(), attrs);
        }
    }
    
    class FiltroMensajeAmpPlus extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            char c = 0;
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                c = text.charAt(i);
                if (((c >= 33 && c <= 43) || (c >= 47 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122))) {
                    s.append(c);
                }
            }
            super.replace(fb, offset, length, s.toString(), attrs);
        }
    }

    class Filtro1char extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            boolean valido;
            char c = text.charAt(0);
            if (((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) && offset == 0) {
                valido = true;
            } else {
                valido = false;
            }
            String aux = Character.toString(EjecutarEnigma.pasarMayus(c));
            if (valido) {
                super.replace(fb, offset, length, aux, attrs);
            } else {
                super.replace(fb, offset, length, "", attrs);
            }
        }
    }

    class FiltroInt extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            char c = 0;
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                c = text.charAt(i);
                if (c >= 48 && c <= 57) {
                    s.append(c);
                }
            }
            super.replace(fb, offset, length, s.toString(), attrs);
        }
    }

    class Filtro1Amp extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            boolean valido;
            char c = text.charAt(0);
            if (((c >= 32 && c <= 43) || (c >= 47 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122)) && offset == 0) {
                valido = true;
            } else {
                valido = false;
            }
            String aux = Character.toString(c);
            if (valido) {
                super.replace(fb, offset, length, aux, attrs);
            } else {
                super.replace(fb, offset, length, "", attrs);
            }
        }
    }

    /**
     * Inicializa las posiciones de la imagen
     */
    private void inicializarPosicionesYRotores() {
        posRY = 150;    //Reflector
        posIY = 220;    //Izquierdo
        posCY = 300;    //Central
        posDY = 380;    //Derecho
        posClaY = 440;  //Clavijero
        posTY = 500;    //Teclado
        // Posiciones Y teclado, clavijero, reflector y rotores inicializadas"
        contEleccion = 0;
        // Colocación rotores inicializada
        rotoresString.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        rotoresString.put("II", "AJDKSIRUXBLHWTMCQGZNPYFVOE");
        rotoresString.put("III", "BDFHJLCPRTXVZNYEIWGAKMUSQO");
        rotoresString.put("IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB");
        rotoresString.put("V", "VZBRGITYUPSDNHLXAWMJQOFECK");
        // Rotores pintar inicializados
        rotores.put("I", rI);
        rotores.put("II", rII);
        rotores.put("III", rIII);
        rotores.put("IV", rIV);
        rotores.put("V", rV);
        // Rotores inicializados
        jTabbedPane1.setFont(new Font(jTabbedPane1.getFont().getFontName(), Font.BOLD, jTabbedPane1.getFont().getSize() + 6));
        // Fuente inicializada
    }

    /**
     * Configura todos los actionListeners de los objetos.
     */
    private void addActionListeners() {
        /**
         * VENTANA 1 - ENIGMA
         */
        jTabbedPane1.addChangeListener((ChangeEvent e) -> {
            if (jTabbedPane1.getSelectedIndex() == 0) {
                repaint();
            }
        });

        // COMBOBOX ROTORES
        jComboBoxRotorIzq.addActionListener((ActionEvent e) -> {
            repaint();
        });
        jComboBoxRotorCen.addActionListener((ActionEvent e) -> {
            repaint();
        });
        jComboBoxRotorDer.addActionListener((ActionEvent e) -> {
            repaint();
        });

        // BOTONES ADD Y DELETE CLAVIJAS
        jButtonClavijaAdd.addActionListener((ActionEvent e) -> {
            String[] s;
            boolean nuevo = false;
            if ((jTextFieldClavija1.getText().length() & jTextFieldClavija2.getText().length()) > 0) {
                char c1 = EjecutarEnigma.pasarMayus(jTextFieldClavija1.getText().charAt(0));
                char c2 = EjecutarEnigma.pasarMayus(jTextFieldClavija2.getText().charAt(0));
                if (c1 != c2) {
                    if (conex.isEmpty()) {
                        conex.add(new Clavijas(c1, c2));
                    } else {
                        for (Clavijas cla : conex) {
                            if (!(cla.getA() == c1 || cla.getB() == c1) && !(cla.getA() == c2 || cla.getB() == c2)) {
                                nuevo = true;
                            }
                        }
                        if (nuevo) {
                            conex.add(new Clavijas(c1, c2));
                        }
                    }
                    s = new String[conex.size()];
                    for (int i = 0; i < conex.size(); i++) {
                        s[i] = conex.get(i).getA() + " - " + conex.get(i).getB();
                    }
                    jListClavijas.setListData(s);
                }
            }
        });
        jButtonClavijaDelete.addActionListener((ActionEvent e) -> {
            String[] s;
            if (!jListClavijas.isSelectionEmpty()) {
                conex.remove(jListClavijas.getSelectedIndex());
                s = new String[conex.size()];
                for (int i = 0; i < conex.size(); i++) {
                    s[i] = conex.get(i).getA() + " - " + conex.get(i).getB();
                }
                jListClavijas.setListData(s);
            }
        });

        /**
         * VENTANA 2 - ENIGMA AMPLIADA
         */
        // CHECKBOX ROTORES Y REFLECTOR
        jCheckBoxRotorIzqAmp.addActionListener((ActionEvent e) -> {
            changeButtonStateValidar(jCheckBoxRotorIzqAmp, jTextFieldRotorIzqAmp, jButtonValidarRIAmp, jTextFieldRotorIzqAmpGiro, jButtonAceptarIzqAmp);
        });
        jCheckBoxRotorCenAmp.addActionListener((ActionEvent e) -> {
            changeButtonStateValidar(jCheckBoxRotorCenAmp, jTextFieldRotorCenAmp, jButtonValidarRCAmp, jTextFieldRotorCenAmpGiro, jButtonAceptarCenAmp);
        });
        jCheckBoxRotorDerAmp.addActionListener((ActionEvent e) -> {
            changeButtonStateValidar(jCheckBoxRotorDerAmp, jTextFieldRotorDerAmp, jButtonValidarRDAmp, jTextFieldRotorDerAmpGiro, jButtonAceptarDerAmp);
        });
        jCheckBoxReflectorAmp.addActionListener((ActionEvent e) -> {
            jTextFieldReflectorAmp.setEditable(!jCheckBoxReflectorAmp.isSelected());
            jButtonValidarRAmp.setEnabled(!jCheckBoxReflectorAmp.isSelected());
        });

        // VALIDAR ROTORES Y REFLECTOR AMPLIADOS
        jButtonValidarRIAmp.addActionListener((ActionEvent e) -> {
            validarRotorIzqAmp = checkRotorAmp(jButtonValidarRIAmp, jTextFieldRotorIzqAmp, jLabelColorValidarIzq, false);
        });
        jButtonValidarRCAmp.addActionListener((ActionEvent e) -> {
            validarRotorCenAmp = checkRotorAmp(jButtonValidarRCAmp, jTextFieldRotorCenAmp, jLabelColorValidarCen, false);
        });
        jButtonValidarRDAmp.addActionListener((ActionEvent e) -> {
            validarRotorDerAmp = checkRotorAmp(jButtonValidarRDAmp, jTextFieldRotorDerAmp, jLabelColorValidarDer, false);
        });
        jButtonValidarRAmp.addActionListener((ActionEvent e) -> {
            validarReflectorAmp = checkValidarRotorAmp(jTextFieldReflectorAmp.getText(), false, true);
            if (validarReflectorAmp) {
                reflectorAmpNuevo = new Rotor(jTextFieldReflectorAmp.getText(), '-');
            }
            jButtonValidarRAmp.setBackground(validarReflectorAmp ? Color.green : Color.red);
            jLabelColorValidarR.setBackground(validarReflectorAmp ? Color.green : Color.red);
        });

        // BOTONES ADD Y DELETE CLAVIJAS AMPLIADAS
        jButtonClavijaAddAmp.addActionListener((ActionEvent e) -> {
            String[] s;
            boolean nuevo = false;
            if ((jTextFieldClavijaAmp1.getText().length() & jTextFieldClavijaAmp2.getText().length()) > 0) {
                char cAmp1 = jTextFieldClavijaAmp1.getText().charAt(0);
                char cAmp2 = jTextFieldClavijaAmp2.getText().charAt(0);
                if (cAmp1 != cAmp2) {
                    if (conexAmp.isEmpty()) {
                        conexAmp.add(new Clavijas(cAmp1, cAmp2));
                    } else {
                        for (Clavijas cla : conexAmp) {
                            if (!(cla.getA() == cAmp1 || cla.getB() == cAmp1) && !(cla.getA() == cAmp2 || cla.getB() == cAmp2)) {
                                nuevo = true;
                            }
                        }
                        if (nuevo) {
                            conexAmp.add(new Clavijas(cAmp1, cAmp2));
                        }
                    }
                    s = new String[conexAmp.size()];
                    for (int i = 0; i < conexAmp.size(); i++) {
                        s[i] = conexAmp.get(i).getA() + " - " + conexAmp.get(i).getB();
                    }
                    jListClavijasAmp.setListData(s);
                }
            }
        });
        jButtonClavijaDeleteAmp.addActionListener((ActionEvent e) -> {
            String[] s;
            if (!jListClavijasAmp.isSelectionEmpty()) {
                conexAmp.remove(jListClavijasAmp.getSelectedIndex());
                s = new String[conexAmp.size()];
                for (int i = 0; i < conexAmp.size(); i++) {
                    s[i] = conexAmp.get(i).getA() + " - " + conexAmp.get(i).getB();
                }
                jListClavijasAmp.setListData(s);
            }
        });

        // BOTONES ACEPTAR ROTORES Y CIFRAR
        jButtonAceptarIzqAmp.addActionListener((ActionEvent e) -> {
            if (!jTextFieldRotorIzqAmpGiro.getText().isEmpty() && validarRotorIzqAmp) {
                rIzqAmpNuevo = new Rotor(jTextFieldRotorIzqAmp.getText(), jTextFieldRotorIzqAmpGiro.getText().charAt(0));
                jButtonAceptarIzqAmp.setBackground(Color.green);
                jLabelColorAceptarIzq.setBackground(Color.green);
                aceptarRotorIzqAmp = true;
            } else {
                jButtonAceptarIzqAmp.setBackground(Color.red);
                jLabelColorAceptarIzq.setBackground(Color.red);
            }
        });
        jButtonAceptarCenAmp.addActionListener((ActionEvent e) -> {
            if (!jTextFieldRotorCenAmpGiro.getText().isEmpty() && validarRotorCenAmp) {
                rCenAmpNuevo = new Rotor(jTextFieldRotorCenAmp.getText(), jTextFieldRotorCenAmpGiro.getText().charAt(0));
                jButtonAceptarCenAmp.setBackground(Color.green);
                jLabelColorAceptarCen.setBackground(Color.green);
                aceptarRotorCenAmp = true;
            } else {
                jButtonAceptarCenAmp.setBackground(Color.red);
                jLabelColorAceptarCen.setBackground(Color.red);
            }
        });
        jButtonAceptarDerAmp.addActionListener((ActionEvent e) -> {
            if (!jTextFieldRotorDerAmpGiro.getText().isEmpty() && validarRotorDerAmp) {
                rDerAmpNuevo = new Rotor(jTextFieldRotorDerAmp.getText(), jTextFieldRotorDerAmpGiro.getText().charAt(0));
                jButtonAceptarDerAmp.setBackground(Color.green);
                jLabelColorAceptarDer.setBackground(Color.green);
                aceptarRotorDerAmp = true;
            } else {
                jButtonAceptarDerAmp.setBackground(Color.red);
                jLabelColorAceptarDer.setBackground(Color.red);
            }
        });
        jButtonCifrarAmp.addActionListener((ActionEvent e) -> {
            boolean plus = false;
            enigmaAmp = new Enigma((aceptarRotorIzqAmp && !jCheckBoxRotorIzqAmp.isSelected()) ? rIzqAmpNuevo : rIzqAmp,
                    (aceptarRotorCenAmp && !jCheckBoxRotorCenAmp.isSelected()) ? rCenAmpNuevo : rCenAmp,
                    (aceptarRotorDerAmp && !jCheckBoxRotorDerAmp.isSelected()) ? rDerAmpNuevo : rDerAmp,
                    plus
            ); // Crea la máquina enigmaAmp
            for (Clavijas cla : conexAmp) {
                enigmaAmp.ponerClavijaAmpliado(cla.getA(), cla.getB(), plus);
            }
            char cIAmp = 'A';
            char cCAmp = 'A';
            char cDAmp = 'A';
            if ((jTextFieldClaveIzqAmp.getText().length() & jTextFieldClaveCenAmp.getText().length() & jTextFieldClaveDerAmp.getText().length()) > 0) {
                cIAmp = jTextFieldClaveIzqAmp.getText().charAt(0);
                cCAmp = jTextFieldClaveCenAmp.getText().charAt(0);
                cDAmp = jTextFieldClaveDerAmp.getText().charAt(0);
            }
            enigmaAmp.setRotoresIniAmpliado(cIAmp, cCAmp, cDAmp, plus);
            String mensaje = jTextFieldMensajeAmp.getText();
            if (validarReflectorAmp && !jCheckBoxReflectorAmp.isSelected()) {
                enigmaAmp.setReflectorAmpliado(reflectorAmpNuevo);
            }
            jTextFieldCifradoAmp.setText(procesarAmpliadoPlus(enigmaAmp, mensaje, plus));
        });

        /**
         * VENTANA 3 - ENIGMA AMPLIADA PLUS
         */
        // CHECKBOX ROTORES Y REFLECTOR
        jCheckBoxRotorIzqAmpPlus.addActionListener((ActionEvent e) -> {
            changeButtonStateValidar(jCheckBoxRotorIzqAmpPlus, jTextFieldRotorIzqAmpPlus, jButtonValidarRIAmpPlus, jTextFieldRotorIzqAmpGiroPlus, jButtonAceptarIzqAmpPlus);
        });
        jCheckBoxRotorCenAmpPlus.addActionListener((ActionEvent e) -> {
            changeButtonStateValidar(jCheckBoxRotorCenAmpPlus, jTextFieldRotorCenAmpPlus, jButtonValidarRCAmpPlus, jTextFieldRotorCenAmpGiroPlus, jButtonAceptarCenAmpPlus);
        });
        jCheckBoxRotorDerAmpPlus.addActionListener((ActionEvent e) -> {
            changeButtonStateValidar(jCheckBoxRotorDerAmpPlus, jTextFieldRotorDerAmpPlus, jButtonValidarRDAmpPlus, jTextFieldRotorDerAmpGiroPlus, jButtonAceptarDerAmpPlus);
        });
        jCheckBoxReflectorAmpPlus.addActionListener((ActionEvent e) -> {
            jTextFieldReflectorAmpPlus.setEditable(!jCheckBoxReflectorAmpPlus.isSelected());
            jButtonValidarRAmpPlus.setEnabled(!jCheckBoxReflectorAmpPlus.isSelected());
        });

        // VALIDAR ROTORES Y REFLECTOR AMPLIADOS
        jButtonValidarRIAmpPlus.addActionListener((ActionEvent e) -> {
            validarRotorIzqAmpPlus = checkRotorAmp(jButtonValidarRIAmpPlus, jTextFieldRotorIzqAmpPlus, jLabelColorValidarIzqPlus, true);
        });
        jButtonValidarRCAmpPlus.addActionListener((ActionEvent e) -> {
            validarRotorCenAmpPlus = checkRotorAmp(jButtonValidarRCAmpPlus, jTextFieldRotorCenAmpPlus, jLabelColorValidarCenPlus, true);
        });
        jButtonValidarRDAmpPlus.addActionListener((ActionEvent e) -> {
            validarRotorDerAmpPlus = checkRotorAmp(jButtonValidarRDAmpPlus, jTextFieldRotorDerAmpPlus, jLabelColorValidarDerPlus, true);
        });
        jButtonValidarRAmpPlus.addActionListener((ActionEvent e) -> {
            validarReflectorAmpPlus = checkValidarRotorAmp(jTextFieldReflectorAmpPlus.getText(), true, false);
            if (validarReflectorAmpPlus) {
                reflectorAmpPlusNuevo = new Rotor(jTextFieldReflectorAmpPlus.getText(), '-');
            }
            jButtonValidarRAmpPlus.setBackground(validarReflectorAmpPlus ? Color.green : Color.red);
            jLabelColorValidarRPlus.setBackground(validarReflectorAmpPlus ? Color.green : Color.red);
        });

        // BOTONES ADD Y DELETE CLAVIJAS AMPLIADAS
        jButtonClavijaAddAmpPlus.addActionListener((ActionEvent e) -> {
            String[] s;
            boolean nuevo = false;
            if ((jTextFieldClavijaAmpPlus1.getText().length() & jTextFieldClavijaAmpPlus2.getText().length()) > 0) {
                char cAmp1 = jTextFieldClavijaAmpPlus1.getText().charAt(0);
                char cAmp2 = jTextFieldClavijaAmpPlus2.getText().charAt(0);
                if (cAmp1 != cAmp2) {
                    if (conexAmpPlus.isEmpty()) {
                        conexAmpPlus.add(new Clavijas(cAmp1, cAmp2));
                    } else {
                        for (Clavijas cla : conexAmpPlus) {
                            if (!(cla.getA() == cAmp1 || cla.getB() == cAmp1) && !(cla.getA() == cAmp2 || cla.getB() == cAmp2)) {
                                nuevo = true;
                            }
                        }
                        if (nuevo) {
                            conexAmpPlus.add(new Clavijas(cAmp1, cAmp2));
                        }
                    }
                    s = new String[conexAmpPlus.size()];
                    for (int i = 0; i < conexAmpPlus.size(); i++) {
                        s[i] = conexAmpPlus.get(i).getA() + " - " + conexAmpPlus.get(i).getB();
                    }
                    jListClavijasAmpPlus.setListData(s);
                }
            }
        });
        jButtonClavijaDeleteAmpPlus.addActionListener((ActionEvent e) -> {
            String[] s;
            if (!jListClavijasAmpPlus.isSelectionEmpty()) {
                conexAmpPlus.remove(jListClavijasAmpPlus.getSelectedIndex());
                s = new String[conexAmpPlus.size()];
                for (int i = 0; i < conexAmpPlus.size(); i++) {
                    s[i] = conexAmpPlus.get(i).getA() + " - " + conexAmpPlus.get(i).getB();
                }
                jListClavijasAmpPlus.setListData(s);
            }
        });

        // BOTONES ACEPTAR ROTORES Y CIFRAR
        jButtonAceptarIzqAmpPlus.addActionListener((ActionEvent e) -> {
            if (!jTextFieldRotorIzqAmpGiroPlus.getText().isEmpty() && validarRotorIzqAmpPlus) {
                rIzqAmpPlusNuevo = new Rotor(jTextFieldRotorIzqAmpPlus.getText(), jTextFieldRotorIzqAmpGiroPlus.getText().charAt(0));
                jButtonAceptarIzqAmpPlus.setBackground(Color.green);
                jLabelColorAceptarIzqPlus.setBackground(Color.green);
                aceptarRotorIzqAmpPlus = true;
            } else {
                jButtonAceptarIzqAmpPlus.setBackground(Color.red);
                jLabelColorAceptarIzqPlus.setBackground(Color.red);
            }
        });
        jButtonAceptarCenAmpPlus.addActionListener((ActionEvent e) -> {
            if (!jTextFieldRotorCenAmpGiroPlus.getText().isEmpty() && validarRotorCenAmpPlus) {
                rCenAmpPlusNuevo = new Rotor(jTextFieldRotorCenAmpPlus.getText(), jTextFieldRotorCenAmpGiroPlus.getText().charAt(0));
                jButtonAceptarCenAmpPlus.setBackground(Color.green);
                jLabelColorAceptarCenPlus.setBackground(Color.green);
                aceptarRotorCenAmpPlus = true;
            } else {
                jButtonAceptarCenAmpPlus.setBackground(Color.red);
                jLabelColorAceptarCenPlus.setBackground(Color.red);
            }
        });
        jButtonAceptarDerAmpPlus.addActionListener((ActionEvent e) -> {
            if (!jTextFieldRotorDerAmpGiroPlus.getText().isEmpty() && validarRotorDerAmpPlus) {
                rDerAmpPlusNuevo = new Rotor(jTextFieldRotorDerAmpPlus.getText(), jTextFieldRotorDerAmpGiroPlus.getText().charAt(0));
                jButtonAceptarDerAmpPlus.setBackground(Color.green);
                jLabelColorAceptarDerPlus.setBackground(Color.green);
                aceptarRotorDerAmpPlus = true;
            } else {
                jButtonAceptarDerAmpPlus.setBackground(Color.red);
                jLabelColorAceptarDerPlus.setBackground(Color.red);
            }
        });
        jButtonCifrarAmpPlus.addActionListener((ActionEvent e) -> {
            boolean plus = true;
            enigmaAmpPlus = new Enigma((aceptarRotorIzqAmpPlus && !jCheckBoxRotorIzqAmpPlus.isSelected()) ? rIzqAmpPlusNuevo : rIzqAmpPlus,
                    (aceptarRotorCenAmpPlus && !jCheckBoxRotorCenAmpPlus.isSelected()) ? rCenAmpPlusNuevo : rCenAmpPlus,
                    (aceptarRotorDerAmpPlus && !jCheckBoxRotorDerAmpPlus.isSelected()) ? rDerAmpPlusNuevo : rDerAmpPlus,
                    plus
            ); // Crea la máquina enigmaAmpPlus
            for (Clavijas cla : conexAmpPlus) {
                enigmaAmpPlus.ponerClavijaAmpliado(cla.getA(), cla.getB(), plus);
            }
            char cIAmp = 'A';
            char cCAmp = 'A';
            char cDAmp = 'A';
            if ((jTextFieldClaveIzqAmpPlus.getText().length() & jTextFieldClaveCenAmpPlus.getText().length() & jTextFieldClaveDerAmpPlus.getText().length()) > 0) {
                cIAmp = jTextFieldClaveIzqAmpPlus.getText().charAt(0);
                cCAmp = jTextFieldClaveCenAmpPlus.getText().charAt(0);
                cDAmp = jTextFieldClaveDerAmpPlus.getText().charAt(0);
            }
            enigmaAmpPlus.setRotoresIniAmpliado(cIAmp, cCAmp, cDAmp, plus);
            String mensaje = jTextFieldMensajeAmpPlus.getText();
            setModo(jRadioButtonModo0.isSelected() ? 0 : 1);
            if (validarReflectorAmpPlus && !jCheckBoxReflectorAmpPlus.isSelected()) {
                enigmaAmpPlus.setReflectorAmpliado(reflectorAmpPlusNuevo);
            }
            jTextFieldCifradoAmpPlus.setText(procesarAmpliadoPlus(enigmaAmpPlus, mensaje, plus));
        });

        /**
         * VENTANA 4 - GENERADOR PASS
         */
        jCheckBoxLongitudPass.addActionListener((ActionEvent e) -> {
            setn(20);
            jTextFieldLongitudPass.setEditable(!jCheckBoxLongitudPass.isSelected());
            jButtonGenerarPass.setEnabled(jCheckBoxLongitudPass.isSelected());
            jButtonGenerarPassCopy.setEnabled(jCheckBoxLongitudPass.isSelected());
            if (!jCheckBoxLongitudPass.isSelected()) {
                jButtonGenerarPass.setEnabled(!jTextFieldLongitudPass.getText().isEmpty());
                jButtonGenerarPassCopy.setEnabled(!jTextFieldLongitudPass.getText().isEmpty());
            }
        });

        // GENERAR Y COPIAR PASS Y ROTOR/REFLECTOR
        jButtonGenerarPass.addActionListener((ActionEvent e) -> {
            setSymbolic(jCheckBoxSymbols.isSelected() ? 4 : 3);
            if (!jCheckBoxLongitudPass.isSelected()) {
                setn(Integer.parseInt(jTextFieldLongitudPass.getText()));
            }
            jTextFieldPass.setText(aleatorio());
        });
        jButtonGenerarPassCopy.addActionListener((ActionEvent e) -> {
            copyToClipboard(jTextFieldPass.getText());
        });
        jButtonGenerarRotor.addActionListener((ActionEvent e) -> {
            if (jRadioButtonRotorAmp.isSelected()) {
                jTextFieldRotorReflector.setText(rotoresAleatoriosAmp(false));
            } else if (jRadioButtonReflectorAmp.isSelected()) {
                jTextFieldRotorReflector.setText(rotorReflectorAmp());
            } else if (jRadioButtonRRAmpPlus.isSelected()) {
                jTextFieldRotorReflector.setText(rotoresAleatoriosAmp(true));
            }
        });
        jButtonGenerarRotorCopy.addActionListener((ActionEvent e) -> {
            copyToClipboard(jTextFieldRotorReflector.getText());
        });
    }

    /**
     * Aporta feedback visual sobre la validez de los rotores
     *
     * @param jbuttonValidar
     * @param rotorAmp
     * @param jlabelColorValidar
     * @param plus
     * @return
     */
    private boolean checkRotorAmp(javax.swing.JButton jbuttonValidar, javax.swing.JTextField rotorAmp, javax.swing.JLabel jlabelColorValidar, boolean plus) {
        boolean valido = checkValidarRotorAmp(rotorAmp.getText(), plus, false);
        jbuttonValidar.setBackground(valido ? Color.green : Color.red);
        jlabelColorValidar.setBackground(valido ? Color.green : Color.red);
        return valido;
    }

    /**
     * Habilita o deshabilita funciones de los rotores según se escoja su uso
     * por default o no
     *
     * @param jcheckbox
     * @param jtextfield
     * @param jbutton
     * @param rotorAmpGiro
     * @param jbuttonAceptar
     */
    private void changeButtonStateValidar(javax.swing.JCheckBox jcheckbox, javax.swing.JTextField jtextfield, javax.swing.JButton jbutton, javax.swing.JTextField rotorAmpGiro, javax.swing.JButton jbuttonAceptar) {
        jtextfield.setEditable(!jcheckbox.isSelected());
        jbutton.setEnabled(!jcheckbox.isSelected());
        rotorAmpGiro.setEditable(!jcheckbox.isSelected());
        jbuttonAceptar.setEnabled(!jcheckbox.isSelected());
    }

    /**
     * Comprueba si es válida la cadena de texto introducida
     *
     * @param s Texto a comprobar
     * @param plus Indica si es ampliado plus
     * @param reflector Indica si es un reflector
     * @return True si la cadena es válida
     */
    private boolean checkValidarRotorAmp(String s, boolean plus, boolean reflector) {
        boolean permutacion = (!reflector) ? true : checkPermutacion(s);
        int longitud = plus ? 73 : 74;
        return checkLongitud(s, longitud) && checkContenido(s, plus) && checkRepetido(s) && permutacion;
    }

    /**
     * Comprueba la longitud del rotor según sea ampliado o ampliado plus
     *
     * @param s
     * @param longitud
     * @return
     */
    private boolean checkLongitud(String s, int longitud) {
        return (s.length() == longitud);
    }

    /**
     * Comprueba que la cadena del rotor/reflector corresponde con el contenido
     * de su tipo
     *
     * @param s
     * @param plus
     * @return
     */
    private boolean checkContenido(String s, boolean plus) {
        char c;
        int longitud = plus ? 33 : 32;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (!((c >= longitud && c <= 43) || (c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si existen repetidos en el rotor/reflector
     *
     * @param s
     * @return
     */
    private boolean checkRepetido(String s) {
        String aux;
        while (!s.isEmpty()) {
            aux = s.substring(0, 1);
            s = s.substring(1);
            if (s.contains(aux)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba las permutaciones a pares del reflector ampliado
     *
     * @param s
     * @return
     */
    private boolean checkPermutacion(String s) {
        char cS;
        char cA;
        String alfabeto = " !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < s.length(); i++) {
            cS = s.charAt(i);
            cA = alfabeto.charAt(i);
            if (s.contains(Character.toString(cA))) {
                if (!(alfabeto.charAt(s.indexOf(cA)) == cS && s.charAt(alfabeto.indexOf(cS)) == cA)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Copia en el portapapeles el contenido de la cadena
     *
     * @param s
     */
    private void copyToClipboard(String s) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(s), null);
    }

    class TableroPintar implements Serializable {

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

        /**
         * Pinta las lineas entre los rotores, las de ida y las de vuelta
         *
         * @param g
         */
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

        /**
         * Pinta los rotores
         *
         * @param character
         * @param g
         * @param eleccion
         * @param offset
         */
        private void pintarBucle(int character, Graphics g, String eleccion, int offset) {
            String s = rotoresString.get(eleccion);
            String abc = "";
            String per = "";
            int n;
            char c = (char) character;
            for (int i = 0; i < 26; i++) {
                n = 30 + 20 * i;
                abc = Character.toString(c);
                if (!"T".equals(eleccion) && !"C".equals(eleccion) && !"R".equals(eleccion)) {
                    per = Character.toString(s.charAt(i));
                    g.setColor(Color.black);
                    g.drawString(per, n, offset);
                }
                switch (contEleccion) {
                    case 0: //Rotor Izquierdo
                        actualizarHashMap(posPerIX, per, posIX, abc, n);
                        pintarEleccion(c, cI, g, n, offset);
                        break;
                    case 1: //Rotor Central
                        actualizarHashMap(posPerCX, per, posCX, abc, n);
                        pintarEleccion(c, cC, g, n, offset);
                        break;
                    case 2: //Rotor Derecho
                        actualizarHashMap(posPerDX, per, posDX, abc, n);
                        pintarEleccion(c, cD, g, n, offset);
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
                c++;
            }
            contEleccion++;
        }

        /**
         * ACtualiza los HashMap de las posiciones de las letras de la imagen
         *
         * @param posPer HashMap de posiciones de permutaciones (rotores)
         * @param per cadena de permutaciones
         * @param pos HashMap de posiciones de filas
         * @param abc cadena de alfabeto
         * @param n nueva posición
         */
        private void actualizarHashMap(Map<String, Integer> posPer, String per, Map<String, Integer> pos, String abc, int n) {
            posPer.put(per, n + 5);
            pos.put(abc, n + 5);
        }

        /**
         * Pinta en la cadena la clave
         *
         * @param c caracter actual del rotor
         * @param clave clave
         * @param g gráfico (Graphics)
         * @param n nueva posición
         * @param offset distancia entre posiciones para pintar correctamente
         */
        private void pintarEleccion(char c, char clave, Graphics g, int n, int offset) {
            if (c == clave) {
                pintarClave(g, n, offset);
            }
        }

        /**
         * Resalta la clave en un tono de color cyan
         *
         * @param g
         * @param n
         * @param offset
         */
        private void pintarClave(Graphics g, int n, int offset) {
            g.setColor(Color.CYAN);
            g.fillRect(n, offset + 5, 15, 18);
        }

        /**
         * Acomoda las clavijas para que se traten correctamente
         */
        private void acomodarClavijas() {
            int primeraValue;
            int segundaValue;
            char primera;
            char segunda;
            for (Clavijas conexion : enigma.getPlugboard().getConexiones()) {
                primera = conexion.getA();
                segunda = conexion.getB();
                primeraValue = posClaX.get(Character.toString(primera));
                segundaValue = posClaX.get(Character.toString(segunda));
                posClaX.put(Character.toString(primera), segundaValue);
                posClaX.put(Character.toString(segunda), primeraValue);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        jLabelBufferedImage.setIcon(new ImageIcon(tablero.pintarTablero()));
        jPanel1.add(jLabelBufferedImage);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupGenerador = new javax.swing.ButtonGroup();
        buttonGroupModoAmpPlus = new javax.swing.ButtonGroup();
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
        jButtonValidarRIAmp = new javax.swing.JButton();
        jButtonValidarRCAmp = new javax.swing.JButton();
        jButtonValidarRDAmp = new javax.swing.JButton();
        jButtonCifrarAmp = new javax.swing.JButton();
        jButtonValidarRAmp = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jTextFieldRotorDerAmpGiro = new javax.swing.JTextField();
        jTextFieldRotorIzqAmpGiro = new javax.swing.JTextField();
        jTextFieldRotorCenAmpGiro = new javax.swing.JTextField();
        jLabelRotorIzqAmp1 = new javax.swing.JLabel();
        jLabelRotorIzqAmp2 = new javax.swing.JLabel();
        jLabelRotorIzqAmp3 = new javax.swing.JLabel();
        jButtonAceptarDerAmp = new javax.swing.JButton();
        jButtonAceptarIzqAmp = new javax.swing.JButton();
        jButtonAceptarCenAmp = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jLabelClavijasAmp1 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabelColorValidarIzq = new javax.swing.JLabel();
        jLabelColorValidarDer = new javax.swing.JLabel();
        jLabelColorValidarR = new javax.swing.JLabel();
        jLabelColorValidarCen = new javax.swing.JLabel();
        jLabelColorAceptarDer = new javax.swing.JLabel();
        jLabelColorAceptarIzq = new javax.swing.JLabel();
        jLabelColorAceptarCen = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabelRotorIzqAmpPlus = new javax.swing.JLabel();
        jCheckBoxRotorIzqAmpPlus = new javax.swing.JCheckBox();
        jTextFieldRotorIzqAmpPlus = new javax.swing.JTextField();
        jButtonValidarRIAmpPlus = new javax.swing.JButton();
        jLabelColorValidarIzqPlus = new javax.swing.JLabel();
        jTextFieldRotorIzqAmpGiroPlus = new javax.swing.JTextField();
        jLabelRotorIzqAmp5 = new javax.swing.JLabel();
        jButtonAceptarIzqAmpPlus = new javax.swing.JButton();
        jLabelColorAceptarIzqPlus = new javax.swing.JLabel();
        jLabelColorAceptarCenPlus = new javax.swing.JLabel();
        jButtonAceptarCenAmpPlus = new javax.swing.JButton();
        jTextFieldRotorCenAmpGiroPlus = new javax.swing.JTextField();
        jLabelRotorIzqAmp6 = new javax.swing.JLabel();
        jButtonValidarRCAmpPlus = new javax.swing.JButton();
        jLabelColorValidarCenPlus = new javax.swing.JLabel();
        jTextFieldRotorCenAmpPlus = new javax.swing.JTextField();
        jCheckBoxRotorCenAmpPlus = new javax.swing.JCheckBox();
        jLabelRotorCenAmp1 = new javax.swing.JLabel();
        jLabelRotorDerAmp1 = new javax.swing.JLabel();
        jTextFieldRotorDerAmpPlus = new javax.swing.JTextField();
        jCheckBoxRotorDerAmpPlus = new javax.swing.JCheckBox();
        jButtonValidarRDAmpPlus = new javax.swing.JButton();
        jLabelColorValidarDerPlus = new javax.swing.JLabel();
        jTextFieldRotorDerAmpGiroPlus = new javax.swing.JTextField();
        jLabelRotorIzqAmp7 = new javax.swing.JLabel();
        jButtonAceptarDerAmpPlus = new javax.swing.JButton();
        jLabelColorAceptarDerPlus = new javax.swing.JLabel();
        jLabelColorValidarRPlus = new javax.swing.JLabel();
        jButtonValidarRAmpPlus = new javax.swing.JButton();
        jTextFieldReflectorAmpPlus = new javax.swing.JTextField();
        jCheckBoxReflectorAmpPlus = new javax.swing.JCheckBox();
        jLabelReflectorAmp1 = new javax.swing.JLabel();
        jLabelClavijasAmp2 = new javax.swing.JLabel();
        jButtonClavijaAddAmpPlus = new javax.swing.JButton();
        jButtonClavijaDeleteAmpPlus = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListClavijasAmpPlus = new javax.swing.JList<>();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabelClavijasAmp3 = new javax.swing.JLabel();
        jTextFieldClavijaAmpPlus1 = new javax.swing.JTextField();
        jTextFieldClavijaAmpPlus2 = new javax.swing.JTextField();
        jTextFieldClaveDerAmpPlus = new javax.swing.JTextField();
        jTextFieldClaveCenAmpPlus = new javax.swing.JTextField();
        jTextFieldClaveIzqAmpPlus = new javax.swing.JTextField();
        jLabelClavesAmp1 = new javax.swing.JLabel();
        jLabelMensaje7 = new javax.swing.JLabel();
        jTextFieldMensajeAmpPlus = new javax.swing.JTextField();
        jLabelCifrado2 = new javax.swing.JLabel();
        jTextFieldCifradoAmpPlus = new javax.swing.JTextField();
        jButtonCifrarAmpPlus = new javax.swing.JButton();
        jRadioButtonModo0 = new javax.swing.JRadioButton();
        jRadioButtonModo1 = new javax.swing.JRadioButton();
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
        setTitle("Simulador Enigma Plus");
        setResizable(false);

        jTabbedPane1.setOpaque(true);
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
        jPanel2.add(jLabelMensaje1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, -1, -1));

        jTextFieldMensajeAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldMensajeAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 389, -1));

        jLabelCifrado1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCifrado1.setText("Mensaje cifrado:");
        jPanel2.add(jLabelCifrado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, -1, -1));

        jTextFieldCifradoAmp.setEditable(false);
        jTextFieldCifradoAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCifradoAmp.setDragEnabled(true);
        jPanel2.add(jTextFieldCifradoAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 590, 389, -1));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 280, 240, 10));

        jLabelClavesAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavesAmp.setText("Claves:");
        jPanel2.add(jLabelClavesAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, -1, -1));

        jTextFieldClaveIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveIzqAmp.setText("A");
        jTextFieldClaveIzqAmp.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, -1, -1));

        jTextFieldClaveCenAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveCenAmp.setText("A");
        jTextFieldClaveCenAmp.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, -1, -1));

        jTextFieldClaveDerAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveDerAmp.setText("A");
        jTextFieldClaveDerAmp.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClaveDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, -1, -1));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 430, 160, 10));

        jLabelClavijasAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavijasAmp.setText("Conexiones de Clavijas:");
        jPanel2.add(jLabelClavijasAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, -1, -1));

        jTextFieldClavijaAmp1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavijaAmp1.setText("A");
        jTextFieldClavijaAmp1.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClavijaAmp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 390, -1, -1));

        jTextFieldClavijaAmp2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavijaAmp2.setText("A");
        jTextFieldClavijaAmp2.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jTextFieldClavijaAmp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 390, -1, -1));

        jButtonClavijaAddAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaAddAmp.setText("Añadir");
        jPanel2.add(jButtonClavijaAddAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 360, -1, -1));

        jButtonClavijaDeleteAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaDeleteAmp.setText("Eliminar");
        jPanel2.add(jButtonClavijaDeleteAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 410, -1, -1));

        jListClavijasAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane3.setViewportView(jListClavijasAmp);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 340, 84, 302));

        jTextFieldRotorIzqAmp.setEditable(false);
        jTextFieldRotorIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 330, -1));

        jLabelRotorIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmp.setText("Rotor Izquierdo:");
        jPanel2.add(jLabelRotorIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabelRotorCenAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorCenAmp.setText("Rotor Central:");
        jPanel2.add(jLabelRotorCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jTextFieldRotorCenAmp.setEditable(false);
        jTextFieldRotorCenAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 330, -1));

        jLabelRotorDerAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorDerAmp.setText("Rotor Derecho:");
        jPanel2.add(jLabelRotorDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        jTextFieldRotorDerAmp.setEditable(false);
        jTextFieldRotorDerAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 330, -1));

        jTextFieldReflectorAmp.setEditable(false);
        jTextFieldReflectorAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldReflectorAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 330, -1));

        jLabelReflectorAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelReflectorAmp.setText("Reflector:");
        jPanel2.add(jLabelReflectorAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        jCheckBoxReflectorAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxReflectorAmp.setSelected(true);
        jCheckBoxReflectorAmp.setText("Por defecto");
        jPanel2.add(jCheckBoxReflectorAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, -1, -1));

        jCheckBoxRotorIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorIzqAmp.setSelected(true);
        jCheckBoxRotorIzqAmp.setText("Por defecto");
        jPanel2.add(jCheckBoxRotorIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, -1, -1));

        jCheckBoxRotorCenAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorCenAmp.setSelected(true);
        jCheckBoxRotorCenAmp.setText("Por defecto");
        jPanel2.add(jCheckBoxRotorCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, -1));

        jCheckBoxRotorDerAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorDerAmp.setSelected(true);
        jCheckBoxRotorDerAmp.setText("Por defecto");
        jPanel2.add(jCheckBoxRotorDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, -1, -1));

        jButtonValidarRIAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRIAmp.setText("Validar");
        jButtonValidarRIAmp.setEnabled(false);
        jPanel2.add(jButtonValidarRIAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, -1));

        jButtonValidarRCAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRCAmp.setText("Validar");
        jButtonValidarRCAmp.setEnabled(false);
        jPanel2.add(jButtonValidarRCAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, -1, -1));

        jButtonValidarRDAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRDAmp.setText("Validar");
        jButtonValidarRDAmp.setEnabled(false);
        jPanel2.add(jButtonValidarRDAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, -1, -1));

        jButtonCifrarAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCifrarAmp.setText("Cifrar");
        jPanel2.add(jButtonCifrarAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 510, -1, -1));

        jButtonValidarRAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRAmp.setText("Validar");
        jButtonValidarRAmp.setEnabled(false);
        jPanel2.add(jButtonValidarRAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, -1, -1));
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 190, 10));

        jTextFieldRotorDerAmpGiro.setEditable(false);
        jTextFieldRotorDerAmpGiro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorDerAmpGiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 220, 60, -1));

        jTextFieldRotorIzqAmpGiro.setEditable(false);
        jTextFieldRotorIzqAmpGiro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorIzqAmpGiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 60, -1));

        jTextFieldRotorCenAmpGiro.setEditable(false);
        jTextFieldRotorCenAmpGiro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jTextFieldRotorCenAmpGiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 60, -1));

        jLabelRotorIzqAmp1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmp1.setText("Punto de giro:");
        jPanel2.add(jLabelRotorIzqAmp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, -1, -1));

        jLabelRotorIzqAmp2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmp2.setText("Punto de giro:");
        jPanel2.add(jLabelRotorIzqAmp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, -1, -1));

        jLabelRotorIzqAmp3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmp3.setText("Punto de giro:");
        jPanel2.add(jLabelRotorIzqAmp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, -1, -1));

        jButtonAceptarDerAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAceptarDerAmp.setText("Aceptar");
        jButtonAceptarDerAmp.setEnabled(false);
        jPanel2.add(jButtonAceptarDerAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 220, -1, -1));

        jButtonAceptarIzqAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAceptarIzqAmp.setText("Aceptar");
        jButtonAceptarIzqAmp.setEnabled(false);
        jPanel2.add(jButtonAceptarIzqAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, -1, -1));

        jButtonAceptarCenAmp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAceptarCenAmp.setText("Aceptar");
        jButtonAceptarCenAmp.setEnabled(false);
        jPanel2.add(jButtonAceptarCenAmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, -1, -1));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 180, 10));

        jLabelClavijasAmp1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavijasAmp1.setText("Clavijas:");
        jPanel2.add(jLabelClavijasAmp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 390, -1, -1));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 160, 10));

        jLabelColorValidarIzq.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarIzq.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarIzq.setOpaque(true);
        jLabelColorValidarIzq.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarIzq.setRequestFocusEnabled(false);
        jPanel2.add(jLabelColorValidarIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 65, -1, -1));

        jLabelColorValidarDer.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarDer.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarDer.setOpaque(true);
        jLabelColorValidarDer.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarDer.setRequestFocusEnabled(false);
        jPanel2.add(jLabelColorValidarDer, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 225, -1, -1));

        jLabelColorValidarR.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarR.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarR.setOpaque(true);
        jLabelColorValidarR.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarR.setRequestFocusEnabled(false);
        jPanel2.add(jLabelColorValidarR, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 305, -1, -1));

        jLabelColorValidarCen.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarCen.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarCen.setOpaque(true);
        jLabelColorValidarCen.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarCen.setRequestFocusEnabled(false);
        jPanel2.add(jLabelColorValidarCen, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 145, -1, -1));

        jLabelColorAceptarDer.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarDer.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarDer.setOpaque(true);
        jLabelColorAceptarDer.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarDer.setRequestFocusEnabled(false);
        jPanel2.add(jLabelColorAceptarDer, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 225, -1, -1));

        jLabelColorAceptarIzq.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarIzq.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarIzq.setOpaque(true);
        jLabelColorAceptarIzq.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarIzq.setRequestFocusEnabled(false);
        jPanel2.add(jLabelColorAceptarIzq, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 65, -1, -1));

        jLabelColorAceptarCen.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarCen.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarCen.setOpaque(true);
        jLabelColorAceptarCen.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarCen.setRequestFocusEnabled(false);
        jPanel2.add(jLabelColorAceptarCen, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 145, -1, -1));

        jTabbedPane1.addTab("Enigma ampliada", jPanel2);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelRotorIzqAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmpPlus.setText("Rotor Izquierdo:");
        jPanel5.add(jLabelRotorIzqAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jCheckBoxRotorIzqAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorIzqAmpPlus.setSelected(true);
        jCheckBoxRotorIzqAmpPlus.setText("Por defecto");
        jPanel5.add(jCheckBoxRotorIzqAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, -1, -1));

        jTextFieldRotorIzqAmpPlus.setEditable(false);
        jTextFieldRotorIzqAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel5.add(jTextFieldRotorIzqAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 330, -1));

        jButtonValidarRIAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRIAmpPlus.setText("Validar");
        jButtonValidarRIAmpPlus.setEnabled(false);
        jPanel5.add(jButtonValidarRIAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, -1));

        jLabelColorValidarIzqPlus.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarIzqPlus.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarIzqPlus.setOpaque(true);
        jLabelColorValidarIzqPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarIzqPlus.setRequestFocusEnabled(false);
        jPanel5.add(jLabelColorValidarIzqPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 65, -1, -1));

        jTextFieldRotorIzqAmpGiroPlus.setEditable(false);
        jTextFieldRotorIzqAmpGiroPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel5.add(jTextFieldRotorIzqAmpGiroPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 60, -1));

        jLabelRotorIzqAmp5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmp5.setText("Punto de giro:");
        jPanel5.add(jLabelRotorIzqAmp5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, -1, -1));

        jButtonAceptarIzqAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAceptarIzqAmpPlus.setText("Aceptar");
        jButtonAceptarIzqAmpPlus.setEnabled(false);
        jPanel5.add(jButtonAceptarIzqAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, -1, -1));

        jLabelColorAceptarIzqPlus.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarIzqPlus.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarIzqPlus.setOpaque(true);
        jLabelColorAceptarIzqPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarIzqPlus.setRequestFocusEnabled(false);
        jPanel5.add(jLabelColorAceptarIzqPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 65, -1, -1));

        jLabelColorAceptarCenPlus.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarCenPlus.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarCenPlus.setOpaque(true);
        jLabelColorAceptarCenPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarCenPlus.setRequestFocusEnabled(false);
        jPanel5.add(jLabelColorAceptarCenPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 145, -1, -1));

        jButtonAceptarCenAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAceptarCenAmpPlus.setText("Aceptar");
        jButtonAceptarCenAmpPlus.setEnabled(false);
        jPanel5.add(jButtonAceptarCenAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, -1, -1));

        jTextFieldRotorCenAmpGiroPlus.setEditable(false);
        jTextFieldRotorCenAmpGiroPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel5.add(jTextFieldRotorCenAmpGiroPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 60, -1));

        jLabelRotorIzqAmp6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmp6.setText("Punto de giro:");
        jPanel5.add(jLabelRotorIzqAmp6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, -1, -1));

        jButtonValidarRCAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRCAmpPlus.setText("Validar");
        jButtonValidarRCAmpPlus.setEnabled(false);
        jPanel5.add(jButtonValidarRCAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, -1, -1));

        jLabelColorValidarCenPlus.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarCenPlus.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarCenPlus.setOpaque(true);
        jLabelColorValidarCenPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarCenPlus.setRequestFocusEnabled(false);
        jPanel5.add(jLabelColorValidarCenPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 145, -1, -1));

        jTextFieldRotorCenAmpPlus.setEditable(false);
        jTextFieldRotorCenAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel5.add(jTextFieldRotorCenAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 330, -1));

        jCheckBoxRotorCenAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorCenAmpPlus.setSelected(true);
        jCheckBoxRotorCenAmpPlus.setText("Por defecto");
        jPanel5.add(jCheckBoxRotorCenAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, -1));

        jLabelRotorCenAmp1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorCenAmp1.setText("Rotor Central:");
        jPanel5.add(jLabelRotorCenAmp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabelRotorDerAmp1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorDerAmp1.setText("Rotor Derecho:");
        jPanel5.add(jLabelRotorDerAmp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        jTextFieldRotorDerAmpPlus.setEditable(false);
        jTextFieldRotorDerAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel5.add(jTextFieldRotorDerAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 330, -1));

        jCheckBoxRotorDerAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxRotorDerAmpPlus.setSelected(true);
        jCheckBoxRotorDerAmpPlus.setText("Por defecto");
        jPanel5.add(jCheckBoxRotorDerAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, -1, -1));

        jButtonValidarRDAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRDAmpPlus.setText("Validar");
        jButtonValidarRDAmpPlus.setEnabled(false);
        jPanel5.add(jButtonValidarRDAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, -1, -1));

        jLabelColorValidarDerPlus.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarDerPlus.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarDerPlus.setOpaque(true);
        jLabelColorValidarDerPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarDerPlus.setRequestFocusEnabled(false);
        jPanel5.add(jLabelColorValidarDerPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 225, -1, -1));

        jTextFieldRotorDerAmpGiroPlus.setEditable(false);
        jTextFieldRotorDerAmpGiroPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel5.add(jTextFieldRotorDerAmpGiroPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 220, 60, -1));

        jLabelRotorIzqAmp7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelRotorIzqAmp7.setText("Punto de giro:");
        jPanel5.add(jLabelRotorIzqAmp7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, -1, -1));

        jButtonAceptarDerAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAceptarDerAmpPlus.setText("Aceptar");
        jButtonAceptarDerAmpPlus.setEnabled(false);
        jPanel5.add(jButtonAceptarDerAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 220, -1, -1));

        jLabelColorAceptarDerPlus.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarDerPlus.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarDerPlus.setOpaque(true);
        jLabelColorAceptarDerPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorAceptarDerPlus.setRequestFocusEnabled(false);
        jPanel5.add(jLabelColorAceptarDerPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 225, -1, -1));

        jLabelColorValidarRPlus.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarRPlus.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarRPlus.setOpaque(true);
        jLabelColorValidarRPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabelColorValidarRPlus.setRequestFocusEnabled(false);
        jPanel5.add(jLabelColorValidarRPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 305, -1, -1));

        jButtonValidarRAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonValidarRAmpPlus.setText("Validar");
        jButtonValidarRAmpPlus.setEnabled(false);
        jPanel5.add(jButtonValidarRAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, -1, -1));

        jTextFieldReflectorAmpPlus.setEditable(false);
        jTextFieldReflectorAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel5.add(jTextFieldReflectorAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 330, -1));

        jCheckBoxReflectorAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBoxReflectorAmpPlus.setSelected(true);
        jCheckBoxReflectorAmpPlus.setText("Por defecto");
        jPanel5.add(jCheckBoxReflectorAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, -1, -1));

        jLabelReflectorAmp1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelReflectorAmp1.setText("Reflector:");
        jPanel5.add(jLabelReflectorAmp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        jLabelClavijasAmp2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavijasAmp2.setText("Conexiones de Clavijas:");
        jPanel5.add(jLabelClavijasAmp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, -1, -1));

        jButtonClavijaAddAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaAddAmpPlus.setText("Añadir");
        jPanel5.add(jButtonClavijaAddAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 360, -1, -1));

        jButtonClavijaDeleteAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonClavijaDeleteAmpPlus.setText("Eliminar");
        jPanel5.add(jButtonClavijaDeleteAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 410, -1, -1));

        jListClavijasAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane4.setViewportView(jListClavijasAmpPlus);

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 340, 84, 302));
        jPanel5.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 160, 10));
        jPanel5.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 190, 10));
        jPanel5.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 180, 10));
        jPanel5.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 430, 160, 10));

        jLabelClavijasAmp3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavijasAmp3.setText("Clavijas:");
        jPanel5.add(jLabelClavijasAmp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 390, -1, -1));

        jTextFieldClavijaAmpPlus1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavijaAmpPlus1.setText("A");
        jTextFieldClavijaAmpPlus1.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel5.add(jTextFieldClavijaAmpPlus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 390, -1, -1));

        jTextFieldClavijaAmpPlus2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClavijaAmpPlus2.setText("A");
        jTextFieldClavijaAmpPlus2.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel5.add(jTextFieldClavijaAmpPlus2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 390, -1, -1));

        jTextFieldClaveDerAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveDerAmpPlus.setText("A");
        jTextFieldClaveDerAmpPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel5.add(jTextFieldClaveDerAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, -1, -1));

        jTextFieldClaveCenAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveCenAmpPlus.setText("A");
        jTextFieldClaveCenAmpPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel5.add(jTextFieldClaveCenAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, -1, -1));

        jTextFieldClaveIzqAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldClaveIzqAmpPlus.setText("A");
        jTextFieldClaveIzqAmpPlus.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel5.add(jTextFieldClaveIzqAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, -1, -1));

        jLabelClavesAmp1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClavesAmp1.setText("Claves:");
        jPanel5.add(jLabelClavesAmp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, -1, -1));

        jLabelMensaje7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMensaje7.setText("Introduce tu mensaje:");
        jPanel5.add(jLabelMensaje7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, -1, -1));

        jTextFieldMensajeAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel5.add(jTextFieldMensajeAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 389, -1));

        jLabelCifrado2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCifrado2.setText("Mensaje cifrado:");
        jPanel5.add(jLabelCifrado2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, -1, -1));

        jTextFieldCifradoAmpPlus.setEditable(false);
        jTextFieldCifradoAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCifradoAmpPlus.setDragEnabled(true);
        jPanel5.add(jTextFieldCifradoAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 590, 389, -1));

        jButtonCifrarAmpPlus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCifrarAmpPlus.setText("Cifrar");
        jPanel5.add(jButtonCifrarAmpPlus, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 510, -1, -1));

        buttonGroupModoAmpPlus.add(jRadioButtonModo0);
        jRadioButtonModo0.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButtonModo0.setSelected(true);
        jRadioButtonModo0.setText("Cifrado");
        jPanel5.add(jRadioButtonModo0, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 480, -1, -1));

        buttonGroupModoAmpPlus.add(jRadioButtonModo1);
        jRadioButtonModo1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButtonModo1.setText("Descifrado");
        jPanel5.add(jRadioButtonModo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, -1, -1));

        jTabbedPane1.addTab("Enigma ampliada ++", jPanel5);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonGenerarRotor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonGenerarRotor.setText("Generar");
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
        jPanel7.add(jButtonGenerarPassCopy, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 210, -1, -1));

        jButtonGenerarRotorCopy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonGenerarRotorCopy.setText("Copiar");
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater
        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new menu().setVisible(true);
            }
        });*/
        new Menu().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupGenerador;
    private javax.swing.ButtonGroup buttonGroupModoAmpPlus;
    private javax.swing.JButton jButtonAceptarCenAmp;
    private javax.swing.JButton jButtonAceptarCenAmpPlus;
    private javax.swing.JButton jButtonAceptarDerAmp;
    private javax.swing.JButton jButtonAceptarDerAmpPlus;
    private javax.swing.JButton jButtonAceptarIzqAmp;
    private javax.swing.JButton jButtonAceptarIzqAmpPlus;
    private javax.swing.JButton jButtonCifrarAmp;
    private javax.swing.JButton jButtonCifrarAmpPlus;
    private javax.swing.JButton jButtonClavijaAdd;
    private javax.swing.JButton jButtonClavijaAddAmp;
    private javax.swing.JButton jButtonClavijaAddAmpPlus;
    private javax.swing.JButton jButtonClavijaDelete;
    private javax.swing.JButton jButtonClavijaDeleteAmp;
    private javax.swing.JButton jButtonClavijaDeleteAmpPlus;
    private javax.swing.JButton jButtonGenerarPass;
    private javax.swing.JButton jButtonGenerarPassCopy;
    private javax.swing.JButton jButtonGenerarRotor;
    private javax.swing.JButton jButtonGenerarRotorCopy;
    private javax.swing.JButton jButtonValidarRAmp;
    private javax.swing.JButton jButtonValidarRAmpPlus;
    private javax.swing.JButton jButtonValidarRCAmp;
    private javax.swing.JButton jButtonValidarRCAmpPlus;
    private javax.swing.JButton jButtonValidarRDAmp;
    private javax.swing.JButton jButtonValidarRDAmpPlus;
    private javax.swing.JButton jButtonValidarRIAmp;
    private javax.swing.JButton jButtonValidarRIAmpPlus;
    private javax.swing.JCheckBox jCheckBoxLongitudPass;
    private javax.swing.JCheckBox jCheckBoxReflectorAmp;
    private javax.swing.JCheckBox jCheckBoxReflectorAmpPlus;
    private javax.swing.JCheckBox jCheckBoxRotorCenAmp;
    private javax.swing.JCheckBox jCheckBoxRotorCenAmpPlus;
    private javax.swing.JCheckBox jCheckBoxRotorDerAmp;
    private javax.swing.JCheckBox jCheckBoxRotorDerAmpPlus;
    private javax.swing.JCheckBox jCheckBoxRotorIzqAmp;
    private javax.swing.JCheckBox jCheckBoxRotorIzqAmpPlus;
    private javax.swing.JCheckBox jCheckBoxSymbols;
    private javax.swing.JComboBox<String> jComboBoxRotorCen;
    private javax.swing.JComboBox<String> jComboBoxRotorDer;
    private javax.swing.JComboBox<String> jComboBoxRotorIzq;
    private javax.swing.JLabel jLabelBufferedImage;
    private javax.swing.JLabel jLabelCifrado;
    private javax.swing.JLabel jLabelCifrado1;
    private javax.swing.JLabel jLabelCifrado2;
    private javax.swing.JLabel jLabelClaves;
    private javax.swing.JLabel jLabelClavesAmp;
    private javax.swing.JLabel jLabelClavesAmp1;
    private javax.swing.JLabel jLabelClavijas;
    private javax.swing.JLabel jLabelClavijasAmp;
    private javax.swing.JLabel jLabelClavijasAmp1;
    private javax.swing.JLabel jLabelClavijasAmp2;
    private javax.swing.JLabel jLabelClavijasAmp3;
    private javax.swing.JLabel jLabelColorAceptarCen;
    private javax.swing.JLabel jLabelColorAceptarCenPlus;
    private javax.swing.JLabel jLabelColorAceptarDer;
    private javax.swing.JLabel jLabelColorAceptarDerPlus;
    private javax.swing.JLabel jLabelColorAceptarIzq;
    private javax.swing.JLabel jLabelColorAceptarIzqPlus;
    private javax.swing.JLabel jLabelColorValidarCen;
    private javax.swing.JLabel jLabelColorValidarCenPlus;
    private javax.swing.JLabel jLabelColorValidarDer;
    private javax.swing.JLabel jLabelColorValidarDerPlus;
    private javax.swing.JLabel jLabelColorValidarIzq;
    private javax.swing.JLabel jLabelColorValidarIzqPlus;
    private javax.swing.JLabel jLabelColorValidarR;
    private javax.swing.JLabel jLabelColorValidarRPlus;
    private javax.swing.JLabel jLabelMensaje;
    private javax.swing.JLabel jLabelMensaje1;
    private javax.swing.JLabel jLabelMensaje2;
    private javax.swing.JLabel jLabelMensaje3;
    private javax.swing.JLabel jLabelMensaje4;
    private javax.swing.JLabel jLabelMensaje5;
    private javax.swing.JLabel jLabelMensaje6;
    private javax.swing.JLabel jLabelMensaje7;
    private javax.swing.JLabel jLabelReflectorAmp;
    private javax.swing.JLabel jLabelReflectorAmp1;
    private javax.swing.JLabel jLabelRotorCen;
    private javax.swing.JLabel jLabelRotorCenAmp;
    private javax.swing.JLabel jLabelRotorCenAmp1;
    private javax.swing.JLabel jLabelRotorDer;
    private javax.swing.JLabel jLabelRotorDerAmp;
    private javax.swing.JLabel jLabelRotorDerAmp1;
    private javax.swing.JLabel jLabelRotorIzq;
    private javax.swing.JLabel jLabelRotorIzqAmp;
    private javax.swing.JLabel jLabelRotorIzqAmp1;
    private javax.swing.JLabel jLabelRotorIzqAmp2;
    private javax.swing.JLabel jLabelRotorIzqAmp3;
    private javax.swing.JLabel jLabelRotorIzqAmp5;
    private javax.swing.JLabel jLabelRotorIzqAmp6;
    private javax.swing.JLabel jLabelRotorIzqAmp7;
    private javax.swing.JLabel jLabelRotorIzqAmpPlus;
    private javax.swing.JList<String> jListClavijas;
    private javax.swing.JList<String> jListClavijasAmp;
    private javax.swing.JList<String> jListClavijasAmpPlus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButtonModo0;
    private javax.swing.JRadioButton jRadioButtonModo1;
    private javax.swing.JRadioButton jRadioButtonRRAmpPlus;
    private javax.swing.JRadioButton jRadioButtonReflectorAmp;
    private javax.swing.JRadioButton jRadioButtonRotorAmp;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldCifrado;
    private javax.swing.JTextField jTextFieldCifradoAmp;
    private javax.swing.JTextField jTextFieldCifradoAmpPlus;
    private javax.swing.JTextField jTextFieldClaveCen;
    private javax.swing.JTextField jTextFieldClaveCenAmp;
    private javax.swing.JTextField jTextFieldClaveCenAmpPlus;
    private javax.swing.JTextField jTextFieldClaveDer;
    private javax.swing.JTextField jTextFieldClaveDerAmp;
    private javax.swing.JTextField jTextFieldClaveDerAmpPlus;
    private javax.swing.JTextField jTextFieldClaveIzq;
    private javax.swing.JTextField jTextFieldClaveIzqAmp;
    private javax.swing.JTextField jTextFieldClaveIzqAmpPlus;
    private javax.swing.JTextField jTextFieldClavija1;
    private javax.swing.JTextField jTextFieldClavija2;
    private javax.swing.JTextField jTextFieldClavijaAmp1;
    private javax.swing.JTextField jTextFieldClavijaAmp2;
    private javax.swing.JTextField jTextFieldClavijaAmpPlus1;
    private javax.swing.JTextField jTextFieldClavijaAmpPlus2;
    private javax.swing.JTextField jTextFieldLongitudPass;
    private javax.swing.JTextField jTextFieldMensaje;
    private javax.swing.JTextField jTextFieldMensajeAmp;
    private javax.swing.JTextField jTextFieldMensajeAmpPlus;
    private javax.swing.JTextField jTextFieldPass;
    private javax.swing.JTextField jTextFieldReflectorAmp;
    private javax.swing.JTextField jTextFieldReflectorAmpPlus;
    private javax.swing.JTextField jTextFieldRotorCenAmp;
    private javax.swing.JTextField jTextFieldRotorCenAmpGiro;
    private javax.swing.JTextField jTextFieldRotorCenAmpGiroPlus;
    private javax.swing.JTextField jTextFieldRotorCenAmpPlus;
    private javax.swing.JTextField jTextFieldRotorDerAmp;
    private javax.swing.JTextField jTextFieldRotorDerAmpGiro;
    private javax.swing.JTextField jTextFieldRotorDerAmpGiroPlus;
    private javax.swing.JTextField jTextFieldRotorDerAmpPlus;
    private javax.swing.JTextField jTextFieldRotorIzqAmp;
    private javax.swing.JTextField jTextFieldRotorIzqAmpGiro;
    private javax.swing.JTextField jTextFieldRotorIzqAmpGiroPlus;
    private javax.swing.JTextField jTextFieldRotorIzqAmpPlus;
    private javax.swing.JTextField jTextFieldRotorReflector;
    private javax.swing.JLabel jlabelLeyendaCaminoIda;
    private javax.swing.JLabel jlabelLeyendaCaminoVuelta;
    private javax.swing.JLabel jlabelLeyendaClave;
    // End of variables declaration//GEN-END:variables
}
