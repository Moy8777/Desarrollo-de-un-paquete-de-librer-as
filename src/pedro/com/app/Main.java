/**
 * Clase principal que inicia la aplicación.
 * Crea y muestra una instancia de VentanaFlotante utilizando Swing.
 */
package pedro.com.app;

import pedro.com.app.mvc.view.VentanaFlotante;
import javax.swing.*;

public class Main {
    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaFlotante ventana = new VentanaFlotante();
            ventana.setVisible(true);
        });
    }
}
