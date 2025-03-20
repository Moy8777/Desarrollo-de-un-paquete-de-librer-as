/**
 * VentanaFlotante es una ventana flotante que permite la entrada de texto,
 * ajuste de opacidad y tamaño de fuente. También soporta arrastre y funcionalidad
 * de "Drag & Drop" para agregar texto.
 */
package pedro.com.app.mvc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import pedro.com.app.mvc.controller.GestorController;

public class VentanaFlotante extends JWindow {
    private JTextArea textArea; // Área de texto donde se ingresa o arrastra el texto
    private JScrollPane scrollPane;
    private JPanel dragPanel; // Panel superior que permite arrastrar la ventana
    private JButton closeButton, clearButton;
    private int mouseX, mouseY;
    private static final String DEFAULT_TEXT = "Ingresa o arrastra aquí cualquier texto";

    /**
     * Constructor de la ventana flotante.
     * Inicializa la ventana con sus componentes y funcionalidades.
     */
    public VentanaFlotante() {
        configurarVentana();
        configurarTexto();
        configurarControles();
        habilitarArrastre();
        habilitarDragAndDrop();
    }

    /**
     * Configura la apariencia de la ventana.
     * Define el tamaño, la opacidad y el diseño general.
     */
    private void configurarVentana() {
        setSize(450, 300);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 180)); // Fondo semitransparente
        setOpacity(0.9f); // Opacidad inicial

        // Panel de arrastre en la parte superior
        dragPanel = new JPanel(new BorderLayout());
        dragPanel.setPreferredSize(new Dimension(getWidth(), 30));
        dragPanel.setBackground(new Color(0, 0, 0, 180));
        add(dragPanel, BorderLayout.NORTH);
    }

    /**
     * Configura el área de texto dentro de la ventana.
     * Permite la escritura y visualización del texto ingresado o arrastrado.
     */
    private void configurarTexto() {
        textArea = new JTextArea(DEFAULT_TEXT);
        textArea.setFont(new Font("Arial", Font.BOLD, 14));
        textArea.setOpaque(false);
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Configura los controles de la ventana.
     * Agrega botones de limpiar y cerrar, además de sliders para opacidad y tamaño de fuente.
     */
    private void configurarControles() {
        closeButton = new JButton("X");
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> System.exit(0));

        clearButton = new JButton("Limpiar");
        clearButton.setBackground(new Color(0, 150, 255));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> GestorController.limpiarTexto(textArea));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);
        dragPanel.add(buttonPanel, BorderLayout.EAST);

        JPanel slidersPanel = GestorController.crearPanelSliders(this, textArea);
        add(slidersPanel, BorderLayout.SOUTH);
    }

    /**
     * Permite arrastrar la ventana a través del panel superior.
     * Detecta eventos de arrastre con el mouse y actualiza la posición de la ventana.
     */
    private void habilitarArrastre() {
        dragPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        dragPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - mouseX;
                int y = getLocation().y + e.getY() - mouseY;
                setLocation(x, y);
            }
        });
    }

    /**
     * Permite arrastrar y soltar texto en el área de texto.
     * Maneja eventos de "Drag & Drop" para insertar texto en el área.
     */
    private void habilitarDragAndDrop() {
        new DropTarget(textArea, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    String droppedData = (String) dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    if (textArea.getText().equals(DEFAULT_TEXT)) {
                        textArea.setText(droppedData);
                    } else {
                        textArea.append("\n" + droppedData);
                    }
                } catch (Exception e) {
                    textArea.append("\nError al cargar el texto");
                }
            }
        });
    }
}
