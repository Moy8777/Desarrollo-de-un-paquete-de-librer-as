/**
 * GestorController es una clase de utilidad que proporciona métodos para gestionar 
 * una ventana flotante con funcionalidad de ajuste de opacidad y tamaño de fuente.
 * También permite limpiar el área de texto restableciendo un mensaje por defecto.
 */
package pedro.com.app.mvc.controller;

import javax.swing.*;
import java.awt.*;

public class GestorController {
    
    /**
     * Limpia el área de texto, restableciendo el mensaje por defecto.
     * 
     * @param textArea El JTextArea que se desea limpiar.
     */
    public static void limpiarTexto(JTextArea textArea) {
        textArea.setText("Ingresa o arrastra aquí cualquier texto");
    }

    /**
     * Crea y configura un slider para ajustar la opacidad de la ventana.
     * 
     * @param ventana La JWindow cuya opacidad se ajustará.
     * @return Un JSlider configurado para modificar la opacidad de la ventana.
     */
    public static JSlider crearSliderOpacidad(JWindow ventana) {
        JSlider sliderOpacidad = new JSlider(30, 100, (int) (ventana.getOpacity() * 100));
        sliderOpacidad.addChangeListener(e -> {
            float opacidad = sliderOpacidad.getValue() / 100f;
            ventana.setOpacity(opacidad);
        });
        return sliderOpacidad;
    }

    /**
     * Crea y configura un slider para ajustar el tamaño de fuente del texto.
     * 
     * @param textArea El JTextArea cuyo tamaño de fuente se ajustará.
     * @return Un JSlider configurado para modificar el tamaño de fuente del JTextArea.
     */
    public static JSlider crearSliderFuente(JTextArea textArea) {
        JSlider sliderFuente = new JSlider(10, 40, textArea.getFont().getSize());
        sliderFuente.addChangeListener(e -> {
            int nuevoTamano = sliderFuente.getValue();
            textArea.setFont(new Font("Arial", Font.BOLD, nuevoTamano));
        });
        return sliderFuente;
    }

    /**
     * Crea un panel con sliders de opacidad y tamaño de fuente.
     * 
     * @param ventana  La JWindow cuya opacidad será ajustable.
     * @param textArea El JTextArea cuyo tamaño de fuente será ajustable.
     * @return Un JPanel que contiene los sliders de opacidad y tamaño de fuente.
     */
    public static JPanel crearPanelSliders(JWindow ventana, JTextArea textArea) {
        JSlider sliderOpacidad = crearSliderOpacidad(ventana);
        JSlider sliderFuente = crearSliderFuente(textArea);

        JLabel labelOpacidad = new JLabel("Opacidad:");
        labelOpacidad.setForeground(Color.WHITE);

        JLabel labelFuente = new JLabel("Fuente:");
        labelFuente.setForeground(Color.WHITE);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setOpaque(false);
        panel.add(labelOpacidad);
        panel.add(sliderOpacidad);
        panel.add(labelFuente);
        panel.add(sliderFuente);

        return panel;
    }
}
