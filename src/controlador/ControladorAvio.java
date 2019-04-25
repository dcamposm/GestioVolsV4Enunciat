package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import model.Avio;
import principal.Component;
import vista.FormAvio;
import vista.LlistatAvions;
import vista.MenuAvio;

/**
 *
 * @author root
 */
public class ControladorAvio {
    private MenuAvio menuAvio;
    private FormAvio formAvio = null;
    private LlistatAvions llistatAvions = null;
    private Avio avio = null;
    private int opcioSeleccionada = 0;

    /*  
    CONSTRUCTOR
    Paràmetres:cap
    Accions:
    - S'inicialitza l'atribut menuAvio (això mostrarà el menú d'avions)
    - Es crida a afegirListenersMenu
     */
    public ControladorAvio() {
        menuAvio = new MenuAvio();
        afegirListenersMenu();
    }

    /*  
    Paràmetres: cap    
    Acció: A cada botó de la vista del menú avió se li afegeix el listener 
    tenint en compte què el mètode actionPerformed, està implementat en aquesta classe.        
    Retorn: cap
     */
    private void afegirListenersMenu() {
        for (int i = 0; i < menuAvio.getMenuButtons().length; i++) {
            menuAvio.getMenuButtons()[i].addActionListener((ActionListener) this);
        }
    }

    /*  
    Paràmetres: cap    
    Acció: A cada botó de la vista del formulari de l'avió se li afegeix el listener 
    tenint en compte què el mètode actionPerformed, està implementat en aquesta classe.        
    Retorn: cap
     */
    private void afegirListenersForm() {
        formAvio.getDesar().addActionListener((ActionListener) this);
        formAvio.getSortir().addActionListener((ActionListener) this);
    }

    /*  
    Paràmetres: cap    
    Acció: Al botó sortir de la vista del llistat d'avions se li afegeix el listener 
    tenint en compte què el mètode actionPerformed, està implementat en aquesta classe.        
    Retorn: cap
     */
    private void afegirListenersLlistat() {
        llistatAvions.getSortir().addActionListener((ActionListener) this);
    }

    /*  
    Paràmetres: cap    
    
    Acció: 
    - S'ha de mostrar a l'usuari un JPane perquè l'usuari pugui seleccionar un dels avions
    de la companyia actual del controlador principal.
    - Per seleccionar els avions, l'usuari ha de disposar en el JPane d'un botó per cada avió
    amb el seu codi, amb el missatge "Selecciona un avió", el títol "Seleccionar avió" i la 
    icona de qüestió.
    
    NOTA: Si abans de seleccionar un avió tanquem la finestra, el mètode de JPane que heu d'utilitzar,
    retornarà -1.
    
    Retorn: avió seleccionat de la companyia actual.
     */
    private Avio seleccionarAvio() {
        
        int cont = 0;
        
        for (int i = 0; i < ControladorPrincipal.getCompanyiaActual().getComponents().size(); i++) {
            if (ControladorPrincipal.getCompanyiaActual().getComponents().get(i) instanceof Avio) {
                cont++;
            }
        }
        
        Object[] avions = new Object[cont];
        Object[] codis = new Object[cont];
        int cont2 = 0;
        
        for (int i = 0; i < ControladorPrincipal.getCompanyiaActual().getComponents().size(); i++) {
            if (ControladorPrincipal.getCompanyiaActual().getComponents().get(i) instanceof Avio) {
                codis[cont2]=((Avio)ControladorPrincipal.getCompanyiaActual().getComponents().get(i)).getCodi();
                avions[cont2]=ControladorPrincipal.getCompanyiaActual().getComponents().get(i);
                cont2++;
            }
        }
        
        opcioSeleccionada = JOptionPane.showOptionDialog(null,"Selecciona un avió","Seleccionar avió",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null, codis, null);
        
        if (opcioSeleccionada > -1){
            return ((Avio)avions[opcioSeleccionada]);
        } 
        
        return null;
                
    }

    /*  
    Paràmetres: cap    
    Acció: 
    - Comprova que l'usuari hagi introduït algun valor en els camps de text del formulari.
    - Si no hi ha cap buit, retornarà verdader.
    - Si hi ha algun camp buit, mostrarà a l'usuari una JPane amb el missatge "S'han d'introduir dades a tots els camps",
    el títol "ATENCIÓ!!!" i la icona d'avís (warning).
    Retorn: Verdader si s'han introduït totes les dades. Fals en cas contrari.
     */
    private Boolean validarAvio() {
        
        if (formAvio.gettCodi().getText().isEmpty() || formAvio.gettFabricant().getText().isEmpty() || formAvio.gettModel().getText().isEmpty() || formAvio.gettCapacitat().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "S'han d'introduir dades a tots els camps", "ATENCIÓ!!!", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }

    }

    /*
    Paràmetres: ActionEvent
        
    Nota:    
    Com ControladorAvio té els listeners del menú, del formulari i del llistat d'avió, llavors en aquest mètode
    actionPerformed heu de controlar si l'usuari ha premut algun botó de qualsevol de les finestres esmentades.
            
    En el cas del formulari i del llistat, com provenen del menú avió (s'activen des del menú avió), heu de verificar
    primer que els objectes FormAvio o LlistatAvio no són nulls, per tal de saber si podeu comparar-los amb
    algun botó d'aquestes finestres.
    
    Accions per al menú:
    
        S'ha de cridar a seleccionarOpcio segons l'opció premuda. Penseu que l'opció es correspon amb
        la posició que el botó ocupa a l'array de botons de menuPrincipal. També, heu d'actualitzar 
        l'atribut opcioSeleccionada amb l'opció que ha premut l'usuari.
        
    Accions per al formulari: (La finestra del formulari està oberta)
            
        ---- DESAR ----
            Si el botó premut per l'usuari és el botó de desar, llavors:
                Si l'opció seleccionada en el menú avió és 1 (alta), llavors:
                    Es validen les dades mitjançant el mètode validarAvio():
                       Si no són correctes, validarAvio() mostrarà l'avís corresponent (penseu que no heu de fer res, ja que
                       és el mètode el que mostra l'avis directament)
                       Si són correctes:
                        - Es crea un nou objecte Avio amb les dades del formulari
                        - S'afegeix l'avió creat a la companyia actual del ControladorPrincipal mitjançant el mètode escaient de la classe Companyia.
                       Si es produeix alguna excepció, s'ha de capturar i mostrar en un JPane, amb el missatge corresponent al codi de l'excepció,
                       el títol "EXCEPCIÓ!!!" i la icona d'avís (warning).
                Si l'opció seleccionada en el menú avió és 2 (modificar), llavors:
                    Es validen les dades mitjançant el mètode validarAvio():
                       Si no són correctes, validarAvio() mostrarà un missatge (No heu de fer res, ja ho fa validarAvio())
                       Si són correctes:
                        - Es modifica l'objecte Avio amb les dades introduides mitjançant el formulari.
        
        ---- SORTIR ----
            Si el botó premut per l'usuari és el botó de sortir del formulari, llavors:
                Heu de tornar al menú avió i amagar el formulari.
        
    Accions per al llistat:
        
        ---- SORTIR ----
            Si el botó premut per l'usuari és el botó de sortir del llistat, llavors:
                Heu de tornar al menú avió i amagar el llistat.
        
        Retorn: cap
     */
    public void actionPerformed(ActionEvent e) {
       
        Object gestorEsdeveniments = e.getSource();
        
        if (gestorEsdeveniments.equals(menuAvio.getMenuButtons())) {
            if (gestorEsdeveniments.equals(menuAvio.getMenuButtons()[0])) {
                opcioSeleccionada = 0;
            } else if (gestorEsdeveniments.equals(menuAvio.getMenuButtons()[1])){
                opcioSeleccionada = 1;
            } else if (gestorEsdeveniments.equals(menuAvio.getMenuButtons()[2])){
                opcioSeleccionada = 2;
            } else if (gestorEsdeveniments.equals(menuAvio.getMenuButtons()[3])){
                opcioSeleccionada = 3;
            } else if (gestorEsdeveniments.equals(menuAvio.getMenuButtons()[4])){
                opcioSeleccionada = 4;
            }
            seleccionarOpcio(opcioSeleccionada);
        } 
        else if (gestorEsdeveniments.equals(formAvio.getDesar())){
            if (opcioSeleccionada == 1){
                if (validarAvio() == true){
                    try {
                    Avio nAvio = new Avio(formAvio.gettCodi().getText(), formAvio.gettFabricant().getText(), formAvio.gettModel().getText(), Integer.parseInt(formAvio.gettCapacitat().getText()));
                    ControladorPrincipal.getCompanyiaActual().getComponents().add(nAvio);
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(null, ex, "EXCEPCIÓ!!!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else if (opcioSeleccionada == 2){
               if (validarAvio() == true){
                    Avio nAvio = new Avio(formAvio.gettCodi().getText(), formAvio.gettFabricant().getText(), formAvio.gettModel().getText(), Integer.parseInt(formAvio.gettCapacitat().getText()));
                        for (int i = 0; i < ControladorPrincipal.getCompanyiaActual().getComponents().size(); i++) {
                            if (((Avio)ControladorPrincipal.getCompanyiaActual().getComponents().get(i)).getCodi() == formAvio.gettCodi().getText()){
                                ControladorPrincipal.getCompanyiaActual().getComponents().set(i, nAvio);
                            }
                   }
                    
                } 
            }
        } else if (gestorEsdeveniments.equals(formAvio.getSortir())){
            formAvio.getFrame().setVisible(false);
            menuAvio.getFrame().setVisible(true);
        } else if (gestorEsdeveniments.equals(llistatAvions.getSortir())){
            llistatAvions.getFrame().setVisible(false);
            menuAvio.getFrame().setVisible(true);
        }
    }

    private void seleccionarOpcio(int opcio) {
        switch (opcio) {

            case 0: //sortir
                ControladorPrincipal.getMenuPrincipal().getFrame().setVisible(true);
                break;

            case 1: // alta
                formAvio = new FormAvio();
                afegirListenersForm();
                break;

            case 2: //modificar
                avio = seleccionarAvio();
                formAvio = new FormAvio(avio.getCodi(), avio.getFabricant(), avio.getModel(), avio.getCapacitat());
                afegirListenersForm();
                break;

            case 3: // llistar
                llistatAvions = new LlistatAvions();
                afegirListenersLlistat();
                break;
        }
    }
}
