package vista;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author root
 */
public class FormAvio {

    private JFrame frame;
    private final int AMPLADA = 300;
    private final int ALCADA = 200;

    private JLabel lCodi;
    private JTextField tCodi;
    private JLabel lFabricant;
    private JTextField tFabricant;
    private JLabel lModel;
    private JTextField tModel;
    private JLabel lCapacitat;
    private JTextField tCapacitat;

    private JButton bDesar;
    private JButton bSortir;

    /* 
    CONSTRUCTOR
    Paràmetres:cap
    Accions:
    Heu d'inicialitzar els atributs d'aquesta classe fent el següent (no afegiu cap listener a cap control):
            
     - Heu d'inicialitzar l'objecte JFrame amb títol "Formulari Avió" i layout Grid d'una columna
     - Heu d'inicialitzar els objectes JLabel amb el nom corresponent segons l'atribut de l'avió que representen.
     - Heu d'inicialitzar els objectes JTextField amb una longitud de 20 caràcters.
     - Heu d'inicialitzar els objectes JButton amb els noms "Desar" i "Sortir" respectivament.
     - Heu d'afegir-ho tot a l'atribut frame
     - Heu de fer visible el frame amb l'amplada i alçada que proposen els atributs alcada i amplada
     - Heu de fer que la finestra es tanqui quan l'usuari ho fa amb el control "X" de la finestra
        
     */
    public FormAvio() {
        frame = new JFrame("Formulari Avió");
        
        frame.setLayout(new GridLayout(1,1));
        
        lCodi = new JLabel("Codi");
        lFabricant = new JLabel("Fabricant");
        lModel = new JLabel("Model");
        lCapacitat = new JLabel("Capacitat");
        
        tCodi = new JTextField(20);
        tFabricant = new JTextField(20);
        tModel = new JTextField(20);
        tCapacitat = new JTextField(20);
        
        bDesar = new JButton("Desar");
        bSortir = new JButton("Sortir");
        
        frame.add(lCodi);
        frame.add(tCodi);
        frame.add(lFabricant);
        frame.add(tFabricant);
        frame.add(lModel);
        frame.add(tModel);
        frame.add(lCapacitat);
        frame.add(tCapacitat);
        frame.add(bDesar);
        frame.add(bSortir);
        
        frame.setSize(AMPLADA, ALCADA);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     CONSTRUCTOR
     Paràmetres: valors del codi, fabricant, model i capacitat
     Accions:
     - Crear un formulari amb l'atra constructor d'aquesta classe FormAvio().
     - Mostrar els valors passats per paràmetre en els camps de text pertinents del formulari.
     */
    public FormAvio(String codi, String fabricant, String model, int capacitat) {
        frame = new JFrame("Formulari Avió");
        
        frame.setLayout(new GridLayout(1,1));
        
        lCodi = new JLabel("Codi",20);
        lFabricant = new JLabel("Fabricant",20);
        lModel = new JLabel("Model",20);
        lCapacitat = new JLabel("Capacitat",20);
        
        tCodi = new JTextField(codi,20);
        tFabricant = new JTextField(fabricant,20);
        tModel = new JTextField(model,20);
        tCapacitat = new JTextField(String.valueOf(capacitat),20);
        
        bDesar = new JButton("Desar");
        bSortir = new JButton("Sortir");
        
        frame.add(lCodi);
        frame.add(tCodi);
        frame.add(lFabricant);
        frame.add(tFabricant);
        frame.add(lModel);
        frame.add(tModel);
        frame.add(lCapacitat);
        frame.add(tCapacitat);
        frame.add(bDesar);
        frame.add(bSortir);
        
        frame.setSize(AMPLADA, ALCADA);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTextField gettCodi() {
        return tCodi;
    }

    public void setCodi(JTextField tCodi) {
        this.tCodi = tCodi;
    }

    public JTextField gettFabricant() {
        return tFabricant;
    }

    public void settFabricant(JTextField tFabricant) {
        this.tFabricant = tFabricant;
    }

    public JTextField gettModel() {
        return tModel;
    }

    public void settModel(JTextField tModel) {
        this.tModel = tModel;
    }

    public JTextField gettCapacitat() {
        return tCapacitat;
    }

    public void settCapacitat(JTextField tCapacitat) {
        this.tCapacitat = tCapacitat;
    }

    public JButton getDesar() {
        return bDesar;
    }

    public void setDesar(JButton bDesar) {
        this.bDesar = bDesar;
    }

    public JButton getSortir() {
        return bSortir;
    }

    public void setSortir(JButton bSortir) {
        this.bSortir = bSortir;
    }
}
