package kardia;
//<editor-fold defaultstate="collapsed" desc="imports">
import static kardia.KARDIA.MainFrame;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//</editor-fold>
/**
 *
 * @author ADRIAN COSTA
 */
public class Funcs_Set{
    /// ///
    static NumberFormat formatter = NumberFormat.getInstance(Locale.US);
    /// ///
//<editor-fold defaultstate="collapsed" desc="GetVaulesforfileResource">
    public static short[] GetVaulesforfileResource( String rname ){
        ///
        short S_B[] = null;
        
            byte[] b_av = GetStingforfileResource(rname).getBytes();
            ///
            int npos = 0;
            for( int i=0; i<b_av.length; i++ ){
                if( b_av[i]==',' ){
                    npos++;
                }
            }
            npos++;
            S_B = new short[npos];
            /// System.out.println( "npos: " + npos );
            String str = new String( b_av );
            int S_i = 0;
            int S_e = 0;
            int I_P = 0;
            for( int i=0; i<str.length(); i++ ){
                if( str.charAt(i)==' ' || str.charAt(i)==',' || i==str.length()-1 ){
                    if( S_i<i && S_i>-1 ){
                        S_e = i;
                        S_B[I_P] = (short)Integer.parseInt(str.substring(S_i, S_e));
                        I_P++;
                        S_i = -1;
                    }
                }else{
                    if( S_i<0 ){
                        S_i = i;
                    }
                    S_e = i;
                }
            }
            ///System.out.println( "I_P: " + I_P );
        
        return S_B;
    }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//</editor-fold>
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="GetVaulesforfileResource">
    public static String GetStingforfileResource( String rname ){
        ///
        try {
            InputStream inputStream = Funcs_Set.class.getResourceAsStream(rname);
            /// System.out.println( "inputStream.available(): " + inputStream.available() );
            int iava = inputStream.available();
            byte[] b_av = new byte[ inputStream.available() ];
            byte[] b_avT = new byte[8192];
            int n_r = inputStream.read( b_av );
            int nadv = n_r;
            while( n_r>0 && n_r!=iava ){
                n_r = inputStream.read( b_avT );
                if( n_r<0 ){
                    break;
                }
                for( int i=0; i<n_r; i++ ){
                    b_av[i+nadv] = b_avT[i];
                }
                nadv = nadv + n_r;
            }
            
            
            
            /// System.out.println( "n_r: " + n_r );
            /// inputStream.read( b_av, 0, b_av.length );
            inputStream.close();
            return new String(b_av);
            ///System.out.println( "I_P: " + I_P );
        } catch (IOException ex) {
            Logger.getLogger(Funcs_Set.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//</editor-fold>
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="GetVaulesforfileResource">
    public static byte[] Getbytesfromimage( String rname ){
        ///
        try {
            InputStream inputStream = Funcs_Set.class.getResourceAsStream(rname);
            byte[] b_av = new byte[ inputStream.available() ];
            inputStream.read( b_av );
            inputStream.close();
            return b_av;
            ///System.out.println( "I_P: " + I_P );
        } catch (IOException ex) {
            Logger.getLogger(Funcs_Set.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//</editor-fold>
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="GetbytesfromimageFile">
    public static byte[] GetbytesfromimageFile( String rname ){
        ///
        try {
            InputStream inputStream = new FileInputStream(rname);
            byte[] b_av = new byte[ inputStream.available() ];
            inputStream.read( b_av );
            inputStream.close();
            return b_av;
            ///System.out.println( "I_P: " + I_P );
        } catch (IOException ex) {
            Logger.getLogger(Funcs_Set.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//</editor-fold>
    /// ///
//<editor-fold defaultstate="collapsed" desc="Num2Hour">
    public static String NumSeg2Hour( double Num ){
        int     Hour = (int)(Num/3600);
        int     Min = (int)( (Num - Hour * 3600)/60 );
        double  Seg = ( (Num - Hour * 3600 - Min * 60) );
        String Str = String.format( "%d:%d:%s", Hour, Min, formatter.format(Seg) );
        return Str;
    }
//</editor-fold>
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="ShowM">
    static int ShowM( String args, String title, int Opt ) {
        int OptionClose;
        OptionClose = JOptionPane.showConfirmDialog(MainFrame, args, title, Opt );
        /*
        if( Opt==JOptionPane.YES_NO_OPTION ){
        
        }else{
        JOptionPane.showMessageDialog(MainFrame, args );
        }
        //*/
        return OptionClose;
    }
    
    static void ShowErrorMsg( String args, String title ) {
        String ArgWS = "";
        int NEsp = 0;
        for( int i=0; i<args.length(); i++ ){
            if( args.charAt(i)==' ' ){
                NEsp++;
                if( NEsp>10 ){
                    NEsp = 0;
                    ArgWS = ArgWS + " \n";
                }else{
                    ArgWS = ArgWS + " ";
                }
            }else{
                ArgWS = ArgWS + args.charAt(i);
            }
        }
        
        
        JOptionPane.showMessageDialog( MainFrame, ArgWS, title, JOptionPane.ERROR_MESSAGE  );
        
    }
    
    
//</editor-fold>
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="imresize">
    /*
    void imresize(){
        BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage( scaledWidth, scaledHeight, imageType );
        Graphics2D g = scaledBI.createGraphics();
        if ( preserveAlpha ) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage( originalImage, 0, 0, scaledWidth, scaledHeight, null );
        g.dispose();
        return scaledBI;
    }
        //*/
//</editor-fold>
    /// ///
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="ParseInt">
    public static int ParseInt( String str ){
        try{
            return Integer.parseInt(str);
        }catch( java.lang.NumberFormatException ex){
            return 0xFFFFFFFF;
        }
    }
//</editor-fold>
    /// ///
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="CommpressDatas">
    public static byte[] DecompressGzip(byte[] inputs, String outputs) {
        try{
            File output = new File( outputs );
            GZIPInputStream in = new GZIPInputStream( new ByteArrayInputStream(inputs) );
            FileOutputStream out = new FileOutputStream(output);
            byte[] buffer = new byte[1024];
            int len;
            while((len = in.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            InputStream targetStream = new FileInputStream(outputs);
            byte[] b2ret = new byte[targetStream.available()];
            targetStream.read(b2ret);
            targetStream.close();
            output.delete();
            return b2ret;
        }catch( Exception ex ){
            ShowErrorMsg( ex.getLocalizedMessage(), "Error DecompressGzip" );
            Logger.getLogger(Funcs_Set.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
    public static byte[] CommpressGzip( byte[] buffer ){
        GZIPOutputStream zipout;
        try {
            File file = new File("zsad345yrhngbfds45ytgf.zip");
            ///
            ///
            FileOutputStream out = new FileOutputStream(file);
            zipout = new GZIPOutputStream(out);
            zipout.write(buffer, 0, buffer.length);
            zipout.close();
            ///
            ///
            InputStream targetStream = new FileInputStream(file);
            byte[] b2ret = new byte[targetStream.available()];
            targetStream.read(b2ret);
            targetStream.close();
            ///
            ///
            ///
            ///
            file.delete();
            return b2ret;
        }catch( Exception ex ){
            ShowErrorMsg( ex.getLocalizedMessage(), "Error DecompressGzip" );
            Logger.getLogger(Funcs_Set.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
//</editor-fold>
    /// ///
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="HHMMSS_PLUS">
    static public int[] HHMMSS( String time ){
        int HMS[] = new int[3];
        String str = "";
        int ind = 0;
        for( int i=0; i<time.length(); i++ ){
            if( time.charAt(i)!=':' && (i<time.length()-1) ){
                str = str + time.charAt( i );
            }else{
                if( (i==time.length()-1) ){
                    str = str + time.charAt( i );
                }
                if( str.startsWith("0") ){
                    str = str.substring(0, 1);
                }
                HMS[ind] = ParseInt(str);
                ind++;
                str = "";
            }
        }
        return HMS;
    }
//</editor-fold>
    /// ///
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="GetVaulesforString">
    public static short[] GetVaulesforString( byte[] b_av ){
        ///
        short S_B[] = null;
            ///
            int npos = 0;
            for( int i=0; i<b_av.length; i++ ){
                if( b_av[i]==',' ){
                    npos++;
                }
            }
            npos++;
            /// System.out.println( "npos INIT: " + npos );
            S_B = new short[npos];
            /// System.out.println( "npos: " + npos );
            String str = new String( b_av );
            int S_i = 0;
            int S_e = 0;
            int I_P = 0;
            for( int i=0; i<str.length(); i++ ){
                if( str.charAt(i)==' ' || str.charAt(i)==',' || i==str.length()-1 ){
                    if( S_i<i && S_i>-1 ){
                        S_e = i;
                        S_B[I_P] = (short)Integer.parseInt(str.substring(S_i, S_e));
                        I_P++;
                        S_i = -1;
                    }
                }else{
                    if( S_i<0 ){
                        S_i = i;
                    }
                    S_e = i;
                }
            }
            ///System.out.println( "I_P: " + I_P );
        return S_B;
    }
//</editor-fold>
    /// ///
    /// ///
    /// ///
    /// ///
    /// ///
    /// ///
//<editor-fold defaultstate="collapsed" desc="GetVaulesforString">
    public static short[] GetVaulesforString2( byte[] b_av ){
        ///
        short S_B[] = null;
            ///
            int npos = 0;
            for( int i=0; i<b_av.length; i++ ){
                if( b_av[i]==',' ){
                    npos++;
                }
            }
            npos++;
            System.out.println( "npos INIT: " + npos );
            System.exit( 0 );
            S_B = new short[npos];
            /// System.out.println( "npos: " + npos );
            String str = new String( b_av );
            int S_i = 0;
            int S_e = 0;
            int I_P = 0;
            for( int i=0; i<str.length(); i++ ){
                if( str.charAt(i)==' ' || str.charAt(i)==',' || i==str.length()-1 ){
                    if( S_i<i && S_i>-1 ){
                        S_e = i;
                        S_B[I_P] = (short)Integer.parseInt(str.substring(S_i, S_e));
                        I_P++;
                        S_i = -1;
                    }
                }else{
                    if( S_i<0 ){
                        S_i = i;
                    }
                    S_e = i;
                }
            }
            ///System.out.println( "I_P: " + I_P );
        
        return S_B;
    }
//</editor-fold>
    /// ///
    /// ///
    /// ///
    
//<editor-fold defaultstate="collapsed" desc="SetLookAndFeel">
    
    public static boolean SetLookAndFeel( int LookOption ){
        try {
            UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();;
            UIManager.setLookAndFeel( looks[LookOption].getClassName() ); 
            return true;
        }catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException | 
                                                ArrayIndexOutOfBoundsException ex ){
        }
        return false;
    }
//</editor-fold>
    /// ///
    /// ///
    /// ///
}
