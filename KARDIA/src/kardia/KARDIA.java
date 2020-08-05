package kardia;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.*;
public class KARDIA {
    static public JFrame                                       MainFrame = null;
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        DB_KARDIA kdb = new DB_KARDIA();
        byte[] b2r = kdb.GetFileFromURL( "http://www.kardia.ga/uploads/JSON_Alejandro_Utria_20191205_104642.txt" );
        System.out.println( "b2r.length: " + b2r.length );
        System.exit( 0 );
        /// GetFileFromURL
        //*/
        
        
        /*/
        
        
        ///
        ///
        JDialog d6 = new JDialog( null, "", Dialog.ModalityType.DOCUMENT_MODAL );
        d6.setSize( 200, 140 );
        d6.setResizable( false );
        d6.setTitle( "Ingrese contraseña:" );
        PassPanel ppan = new PassPanel();
        d6.add(ppan);
        d6.setLocationRelativeTo( null );
        d6.setVisible( true );
        System.exit( 0 );
        //*/
        
        
        ///
        ///
        ///
        MainFrame = new KardiaWindow();
        MainFrame.setVisible(true);
    }
}
