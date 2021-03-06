package administrarpass;

import java.util.ArrayList;
import java.util.List;

public class Clavijero {

    private ArrayList<Character> clavijas = new ArrayList<>(); // Todas las clavijas
    private ArrayList<Character> clavijasLibres = new ArrayList<>(); // Clavijas libres
    private ArrayList<Clavijas> conexiones = new ArrayList<>(); // Conexiones realizadas

    /**
     * Crea el panel de clavijas
     */
    public Clavijero() {
        String panel;
        panel = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < panel.length(); i++) { // crea las clavijas del panel
            char a = panel.charAt(i);
            this.clavijas.add(a);
            this.clavijasLibres.add(a);
        }
    }

    /**
     * Crea el panel ampliado de clavijas
     *
     * @param plus Indica si el abecedario es plus
     */
    public Clavijero(boolean plus) {
        String panel;
        panel = plus ? "!\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" : " !\"#$%&'()*+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < panel.length(); i++) { // crea las clavijas del panel
            char a = panel.charAt(i);
            this.clavijas.add(a);
            this.clavijasLibres.add(a);
        }
    }

    /**
     * Establece una conexion en el panel de clavijas
     *
     * @param a clavija a
     * @param b clavija b
     * @return devuelve true si se puedo establecer la conexión
     */
    public boolean establecerConexion(char a, char b) {
        if (this.clavijas.contains(a) && this.clavijas.contains(b)) { // comprueba si existen las clavijas
            if (this.clavijasLibres.contains(a) && this.clavijasLibres.contains(b)) { // comprueba si alguna de las clavijas está ocupada
                this.conexiones.add(new Clavijas(a, b)); // crea la conexión de las clavijas
                for (int i = 0; i < this.clavijasLibres.size(); i++) { // busca las clavijas y las elimina de las clavijas libres
                    char aux = this.clavijasLibres.get(i);
                    if (a == aux || b == aux) {
                        this.clavijasLibres.remove(i);
                    }
                }
            } else {
                // La clavija tiene otra conexión
                return false;
            }
        } else {
            // No existe la clavija
            return false;
        }
        return true;
    }

    /**
     * Devuelve indice de la conexion entre 2 clavijas
     *
     * @param a clavija a
     * @param b clavija b
     * @return índice de la conexion si la encuentra, -1 si no la encuentra
     */
    private int encontrarConexion(char a, char b) {
        for (int i = 0; i < this.conexiones.size(); i++) {
            Clavijas aux = this.conexiones.get(i); // busca si hay una conexión entre las clavijas
            if ((aux.getA() == a && aux.getB() == b) || (aux.getA() == b && aux.getB() == a)) { // comprueba las entradas (indiferente del orden)
                return i;
            }
        }
        return -1;
    }

    /**
     * Elimina la conexion del panel de clavijas
     *
     * @param a clavija a
     * @param b clavija b
     * @return true si la conexion fue eliminada, false si no se encuentra la
     * conexion
     */
    public boolean eliminarConexion(char a, char b) {
        int i = encontrarConexion(a, b); // comprueba si existe conexión
        if (i != -1) { // elimina la conexión
            this.clavijasLibres.add(a);
            this.clavijasLibres.add(b);
            this.conexiones.remove(i);
        } else {
            // No existe la conexion a eliminar
            return false;
        }
        return true;
    }

    /**
     * Obtiene la lista de Clavijas
     *
     * @return lista de clavijas
     */
    public List<Clavijas> getConexiones() {
        return conexiones;
    }
}
