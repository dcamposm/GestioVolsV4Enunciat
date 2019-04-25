package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Avio;
import model.Companyia;
import principal.GestioVolsExcepcio;

/**
 *
 * @author root
 */
public class GestorJDBC implements ProveedorPersistencia {
    private Companyia companyia;

    private Connection conn; //Connexió a la base de dades

    public Companyia getCompanyia() {
        return companyia;
    }

    public void setCompanyia(Companyia companyia) {
        this.companyia = companyia;
    }

    /*
     *TODO
     * 
     *Paràmetres: cap
     *
     *Acció:
     * - Heu d'establir la connexio JDBC amb la base de dades GestorVols
     * - Heu de fer el catch de les possibles excepcions SQL mostrant el missatge
     *   de l'excepció capturada mitjançant getMessage().
     * - Si es produeix una excepció a l'establir la connexió, assignareu el 
     *   valor null a l'atribut conn.
     *
     *Retorn: cap
     *
     */
    public void estableixConnexio() throws SQLException {
        try{
            //Carreguem el controlador MySQL. Class.forName retorna un objecte
            //associat al paràmetre, en el nostre cas un controlador per mysql
            Class.forName("com.mysql.jdbc.Driver");
            //Connectem amb la base de dades
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestorVols", "root", "");
            System.out.println("Ens hem connectat");
        }catch (ClassNotFoundException e1){
           //Error si no es pot llegir el controlador 
           conn = null;
           System.out.println("ERROR: no s'ha trobat el controlador de la BD: "+e1.getMessage());
        }catch (SQLException e2) {
           //Error SQL: de usuari o contrasenya
           conn = null;
           System.out.println("ERROR: SQL ha fallat: "+e2.getMessage());
	}
    }

   /*
     * Heu de tancar la connexió i assignar-li el valor null a l'atribut conn, es 
     * produeixi o no una excepció.
     *
     */
    public void tancaConnexio() throws SQLException {
        if (conn!=null){ //Si existeix la connexió....
            conn.close(); //Tanquem la connexió
            conn = null;
        }
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer i la companyia a desar
     *
     *Acció:
     * Heu de desar la companyia passada com a paràmetre en la base de dades:
     *   - S'ha de desar en la taula companyes (nomFitxer és el codi de la companyia)
     *   - Cada avió de la companyia, s'ha de desar com registre de la taula avions
     *   - Heu de tenir en compte que si la companyia ja existeix a la base de 
     *     dades, aleshores heu de fer el següent:
     *        - Actualitzar la companyia ja existent
     *        - Eliminar totes els avions d'aquesta companyia de la taula avions i 
     *          després insertar els avions de la companyia.
     *   - Si al fer qualsevol operació es produeix una excepció, llavors heu de 
     *     llançar l'excepció GestioVolsExcepcio amb codi "GestorJDBC.desar"
     *
     *Retorn: cap
     *
     */
    @Override
    public void desarDades(String nomFitxer, Companyia companyia) throws GestioVolsExcepcio {
        Statement cerca;
        try {
            cerca = conn.createStatement();
            //recollim el resultat de la cerca
            if (cerca.execute("SELECT *  FROM companyies WHERE codi = "+nomFitxer)){
                cerca.executeUpdate("UPDATE companyies set nom='"+companyia.getNom()+"' where codi="+nomFitxer);
                
                cerca.execute("DELETE FROM avions where codiCompanyia="+nomFitxer);
                
                for (int i = 0; i < companyia.getComponents().size(); i++) {
                    if (companyia.getComponents().get(i) instanceof Avio){
                        cerca.executeUpdate("INSERT INTO avio(codi,fabricant,model,capacitat,codiCompanyia)"
                                + " VALUES ('"+((Avio)companyia.getComponents().get(i)).getCodi()+"','"+((Avio)companyia.getComponents().get(i)).getFabricant()+"',"
                                        + "'"+((Avio)companyia.getComponents().get(i)).getModel()+"',"+nomFitxer+"');");
                    }
                }
            } else {
                cerca.executeUpdate("INSERT INTO companyies(codi,nom) VALUES ('"+nomFitxer+"','"+companyia.getNom()+"');");
            } 
        } catch (Exception ex) {
            throw new GestioVolsExcepcio("GestorJDBC.desar");
        } 
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer de la companyia
     *
     *Acció:
     * Heu de carregar la companyia des de la base de dades (nomFitxer és el codi 
     * de la companyia). Per fer això, heu de:
     *   - Cercar el registre companyia de la taula companyies amb codi = nomFitxer. 
     *   - Heu d'afegir els avions al vector de components de la companyia a partir de 
     *     la taula avions.
     *   - Si al fer qualsevol operació es produeix una excepció, llavors heu de 
     *     llançar l'excepció GestioVolsExcepcio amb codi "GestorJDBC.carrega"
     *   - Si el nomFitxer donat no existeix a la taula companyies (és a dir, el 
     *     codi = nomFitxer no existeix), aleshores heu de llançar l'excepció 
     *     GestioVolsExcepcio amb codi "GestorJDBC.noexist"
     *
     *Retorn: cap
     *
     */
    @Override
    public Companyia carregarDades(String nomFitxer) throws ParseException, GestioVolsExcepcio {
        Statement cerca;
        
        try {
            cerca = conn.createStatement();
            //recollim el resultat de la cerca
            try {
                cerca.execute("SELECT *  FROM companyies WHERE codi LIKE '"+nomFitxer+"'");
            }catch (SQLException ex) {
                throw new GestioVolsExcepcio("GestorJDBC.noexist");
            } 
            
            String resultatNom=cerca.getResultSet().getString("nom");
            int resultatCodi=cerca.getResultSet().getInt("codi");
            companyia = new Companyia(resultatCodi, resultatNom);
            cerca.execute("SELECT *  FROM avions WHERE codiCompanyia LIKE '"+resultatCodi+"'");
            while(cerca.getResultSet().next()){ 
                Avio nAvio = new Avio(cerca.getResultSet().getString("codi"), cerca.getResultSet().getString("fabricant"), cerca.getResultSet().getString("model"), cerca.getResultSet().getInt("capacitat"));
                companyia.getComponents().add(nAvio);
            }           
            return companyia;
        } catch (SQLException ex) {
            throw new GestioVolsExcepcio("GestorJDBC.carrega");
        } 
    }
}