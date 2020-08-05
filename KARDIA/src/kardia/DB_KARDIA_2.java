package kardia;
//<editor-fold defaultstate="collapsed" desc="import">
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import static kardia.PERMITIDO.Fs;
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
public class DB_KARDIA_2{
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="VARIABLES">
/// http://www.kardiaupc.ga/
    static final String                     Columns_registro[] = {"id", "lat", "lng", "file", "time", "user_id" };
    static final String                     Columns_user[] = {"id", "name", "image", "google_id", "cedula", "email", "anotaciones" };
/// ////////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
    class Params{
        public int                      id          = 0;
        public String                   lat         = "";
        public String                   lng         = "";
        public String                   file        = "";
        public String                   time        = "";
        public String                   timeC        = "";
        public int                      user_id     = 0;
        
        public int                      id_of_user  = 0;
        public String                   name        = "";
        public Image                    image       = null;
        public String                   google_id   = "";
        public String                   cedula      = "";
        public String                   email       = "";
        public String                   Anotaciones = "";
        
        public String                   HoraInicio  = "";
        
        public short                    Datas[]     = null;
        
    };
    Params                                  Prms[] = null;
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
    
    Connection                              myConnection = null;
    Statement                               stmt = null;
    int                                     Codigo = 0;
    String                                  Kardia_URL  = Servidor_Info.Kardia_URL;
    String                                  PassWordDB = Servidor_Info.PassWord;
    String                                  DataBase = Servidor_Info.DataBase;
    String                                  Table_name = "USER_77000000";
    String                                  Cedula_User = "77000000";
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="DB_KARDIA">
    boolean OpenConnection( boolean ShowME ){
        try {
            try {
            Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            if( myConnection!=null ){
                try{
                    myConnection.close();
                }catch (SQLException ex) {}
            }
            myConnection = DriverManager.getConnection(
                    "jdbc:mysql://" + Servidor_Info.Servidor, Servidor_Info.Usuario, PassWordDB
            );
            stmt = myConnection.createStatement();
            stmt.execute( "USE " + DataBase );
            return true;
        } catch (SQLException ex) {
             Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
            if( ShowME ){
                Funcs_Set.ShowErrorMsg( ex.getLocalizedMessage(), " Error DB_KARDIA " );
            }
            myConnection = null;
        }
        return false;
    }
    public DB_KARDIA_2(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
        OpenConnection( false );
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="pruebas">
    public void pruebas(){
        ///
        ///String ftpUrl = "ftp://tom:secret@www.myserver.com/project/2012/Project.zip;type=i";
        ///String saveFile = "E:\\AIIF\\SegAll\\ENTERPRISE\\TESIS\\Alejandro_Rebeca\\JAVA\\PRUEBAS\\_Datas.txt";
        ///byte b_t[] = GetFileFromURL( ftpUrl );
        ///
        /// UPDATE user_7700000 SET Orden = 4 WHERE id = 000000;
        try{
            ///
            // 
            ///
            /// http://www.kardia.ga/
            ///
            ///
            ///
            /// http://www.kardiaupc.ga/uploads/JSON_Alejandro_Utria_20191010_121505.txt
            /*
            byte[] buffer = Funcs_Set.Getbytesfromimage( "Datos1H.txt" );
            buffer = Funcs_Set.CommpressGzip(buffer);
            System.out.println( buffer.length );
            buffer = Funcs_Set.DecompressGzip( buffer, buffer.toString()+"zsad345yrhngbfds45ytgf.datas" );
            System.out.println( buffer.length );
            //*/
            ///
            System.out.println( myConnection.getCatalog() );
            /// System.out.println( "CONNECTION: " + myConnection );
            /// myConnection.close();System.exit(0);
            stmt = myConnection.createStatement();
            ///
            ///
            stmt.execute( "USE " + DataBase );
            /// Borrar_tabla( myConnection, DataBase, Table_name );
            
            CreateUser( myConnection, DataBase, Table_name );
            
            ///stmt.execute( "SET @max_allowed_packet = 67108864;" );
            ///stmt.execute( "SHOW VARIABLES;" );
            
            
            
            
            //byte[] b_y_image_2 = new byte[b_y_image.length/2];
            //b_y_image = new String(b_y_image_2).getBytes();
            byte[] b_y_image = Funcs_Set.Getbytesfromimage( "0.png" );
            ///
            ///
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern( "yyyy/MM/dd" );
            LocalDateTime now2 = LocalDateTime.now();
            System.out.println(dtf2.format(now2));
            ///System.exit( -2134 );
            ///
            ///
            byte[] b_y_DatasO = Funcs_Set.Getbytesfromimage( "Datos1H.txt" );
            //System.out.println( b_y_DatasO.length );
            b_y_DatasO = Funcs_Set.CommpressGzip(b_y_DatasO);
            //System.out.println( b_y_DatasO.length );
            ///
            ///
            ///
            int NRows2 = GetRowOfTable(myConnection, DataBase, Table_name);
            String time = "9:42:33";
            String timeinit = "9:42:33";
            if( NRows2>0 ){
                ResultSet rs = stmt.executeQuery( "SELECT time" + " FROM " + Table_name + " where " + NRows2 );
                //rs.next();
                while( rs.next() ){
                    time = rs.getString(1);
                    ///System.out.println("time : " + time );
                }
                ///
                ///
                ///
                int HMS[] = Funcs_Set.HHMMSS(time);
                time = (HMS[0]+1) + ":" + HMS[1] + ":" + HMS[2];
                
                ///
                timeinit = (HMS[0]) + ":" + HMS[1] + ":" + HMS[2];
                ///
                System.out.println( timeinit );
                System.out.println( time );
                ///
                rs = stmt.executeQuery( "SELECT Orden" + " FROM " + Table_name + " where " + NRows2 );
                ///
                ///
                ///
                while(rs.next()){
                    NRows2 = Funcs_Set.ParseInt( rs.getString(1) );
                    System.out.println("available Orden :" + NRows2 );
                }
                NRows2 = NRows2 + 1;
            }else{
                int HMS[] = Funcs_Set.HHMMSS(time);
                time = (HMS[0]+1) + ":" + HMS[1] + ":" + HMS[2];
                ///
                timeinit = (HMS[0]) + ":" + HMS[1] + ":" + HMS[2];
                System.out.println( timeinit );
                System.out.println( time );
                NRows2 = 0;
            }
            ///
///
/// UPDATE BLOB_table SET BLOB_field = CONCAT(BLOB_field, BLOB_data);
///
            String query =  "INSERT INTO " + Table_name + 
                            " (id, " + 
                            "Fecha, " + 
                            "Orden, " + 
                            "HoraInicio, " + 
                            "Apellidos, " + 
                            "Nombres, " + 
                            "Edad, " + 
                            "City, " + 
                            "email, " + 
                            "image, " + 
                            "latitude, " + 
                            "longitude, " + 
                            "time, " + 
                            "file, " + 
                            "Anotaciones, " + 
                            "Datas) " + 
                            " VALUES " + 
                    "(1, " + 
                    "'2019/12/06', " + 
                    NRows2 + ", " + 
                    "'" + timeinit + "', " + ///"'9:42:33', " + 
                    "'Peres', " + 
                    "'Juan', " + 
                    "55, " + 
                    "'Valledupar', " + 
                    "'juanperez@mail.com', " + 
                    "?, " + 
                    "'0.0', " + 
                    "'0.0', " + 
                    "'" + time + "', " + 
                    "'uploads/JSON_Alejandro_Utria_20191205_104642.txt', " + 
                    "'', " + 
                    "?)";
            PreparedStatement pstmt = myConnection.prepareStatement( query );
            pstmt.setBytes( 1, b_y_image );
            pstmt.setBytes( 2, b_y_DatasO );
            pstmt.execute();
            ///
            /// www.kardia.ga/uploads/JSON_Alejandro_Utria_20191205_104642.txt
            ///             /// www.kardia.ga/uploads/JSON_Alejandro_Utria_20191205_104642.txt
            ///
            ///
            ///
            ///
            ///
            /// UPDATE Users SET weight = 160, desiredWeight = 145 WHERE id = 1;
            /// stmt.execute( "UPDATE " + Table_name + " SET image = CONCAT(image, image) where indice=1;" );
            ///
            System.exit( 0 );
                
                
                ///Borrar_tabla( myConnection, "db_a4e8fe_kardia", "PASSWORD_ADMIN" );
                
            ///
            ///
            
            String StrImage = "";
            /// System.out.println("" + String.format( "%02x", b_y_image[0] ) );
            for( int i=0; i<b_y_image.length; i++ ){
                ///StrImage = StrImage + String.format( "%02x", b_y_image[i] );
            }
            /// System.out.println( "Datas: " + strdatas );
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now();
            /// System.out.println(dtf.format(now));
            
            
            /// System.out.println( "StrImage.length(): " + StrImage.length() );
            int NRows = GetRowOfTable( myConnection, DataBase, Table_name );
            System.out.println("NRows :" + NRows );
            //*
            stmt.execute("INSERT INTO " + Table_name + 
                        "(  " +
                    /// Campos:
                            "Fecha," +
                            "id," +
                            "Orden," +
                            "HoraInicio," +
                            "Apellidos," +
                            "Nombres," +
                            "Edad," +
                            "City," + 
                            "email," +
                            "image," +
                            "latitude," +
                            "longitude," +
                            "time," +
                            /// 2019-10-16 12:42:33
                            "file," + 
                            ///
                                
                        "Anotaciones)" + 
                        "\n" + 
                     ///VALORES
                        "VALUES( " + 
                        "'" + "12:42:33" + "', " + 
                        "'0', " + 
                        "    0, " + 
                        "'12:42:33', " +
                        "'Peres', " + 
                        "'Juan', " + 
                        "25, " + 
                        "'Valledupar', " + 
                        "'juanperez@mail.com', " +
                    ///
                        "x'" + StrImage + "', " +
                    ///
                        "'0.0', " +
                        "'0.0', " +
                        "'" + now.getHour() + ":" + now.getMinute() + ":" + now.getMinute() + "', " + 
                        "'uploads/JSON_Alejandro_Utria_20191010_121505.txt', " + 
                        "'Ninguna' );"       
                         );
            //*/
            ///
            ///
            ///
            /// 1023, 
            /// 6 * 1500 * 3600
            ///
            ///
            ///
            ///
            /// stmt.execute("CREATE  DATABASE USER_77039429;"); // NO SE PUEDE
            ///
            ///
            ///
            ///
            /// stmt.execute("USE db_a4e8fe_kardia");
            //// stmt.execute("DROP  TABLE USER_77039429;"); // BORRAR TABLA
            //// stmt.execute("CREATE TABLE USER_77039429( task_id INT AUTO_INCREMENT PRIMARY KEY)  ENGINE=INNODB;");
            /*
            String strQuery = "SELECT image FROM `usuario`;";
            ResultSet rs = stmt.executeQuery( strQuery );
            String Countrow = "";
            while(rs.next()){
                Blob b_l = rs.getBlob(1);
                b_l.getBinaryStream().available()
                Countrow = rs.getString(1);
                System.out.println("Total Row :" +Countrow);
            }
            
            // get the number of rows from the result set
            rs.next();
            System.out.println("rowCount " + rs.toString() );
            //*/
            ///int rowCount = rs.getInt(1);
            ///System.out.println("rowCount " + rowCount );
            
            
            /// stmt.execute("CREATE TABLE `asds` (id INT, data VARCHAR(100));");
            System.exit(0);
            
            stmt.executeQuery("SELECT * FROM `MYSQL5022`");
            
            System.exit(0);
            
            
            stmt.executeUpdate("DELETE  FROM `test` WHERE  sad");
            System.exit(0);
            int c =stmt.executeUpdate("CREATE TABLE Accounts (Name VARCHAR(30))");
            System.exit(0);
        ///}catch(ClassNotFoundException e){
            ////System.out.println("Failed to get connection");
            ///System.exit(-1);
        } catch (SQLException ex) {
            System.out.println("Failed to get connection");
            Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        } catch (Exception ex) {
            Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit( -1 );
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="MakeAllDatasForUser">
    public void MakeAllDatasForUser( String USERMAKE, int id ){
        try {
            /// USERMAKE = "USER_77000000";
            System.out.println( myConnection.getCatalog() );
            stmt = myConnection.createStatement();
            stmt.execute( "USE " + DataBase );
            if( id==0 ){
                Borrar_tabla( myConnection, DataBase, USERMAKE );
            }
            CreateUser( myConnection, DataBase, USERMAKE );
            byte[] b_y_image = Funcs_Set.Getbytesfromimage( "0.png" );
            byte[] b_y_DatasO = Funcs_Set.Getbytesfromimage( "Datos1H.txt" );
            b_y_DatasO = Funcs_Set.CommpressGzip(b_y_DatasO);
            ///
            for( int iuser=0; iuser<24; iuser++ ){
            
            ///
            ///
            ///
            ///
            ///
            int NRows2 = GetRowOfTable(myConnection, DataBase, USERMAKE);
            String time = "9:42:33";
            String timeinit = "9:42:33";
            if( NRows2>0 ){
                ResultSet rs = stmt.executeQuery( "SELECT time" + " FROM " + USERMAKE + " where " + NRows2 );
                while( rs.next() ){
                    time = rs.getString( 1 );
                }
                ///
                int HMS[] = Funcs_Set.HHMMSS(time);
                timeinit = HMS[0] + ":" + HMS[1] + ":" + HMS[2];
                HMS[0] = HMS[0] + 1;
                if( HMS[0]>=24 ){
                    HMS[0] = HMS[0] - 24;
                }
                time = HMS[0] + ":" + HMS[1] + ":" + HMS[2];
                ///
                rs = stmt.executeQuery( "SELECT Orden" + " FROM " + USERMAKE + " where " + NRows2 );
                ///
                rs.next();
                while(rs.next()){
                    NRows2 = Funcs_Set.ParseInt( rs.getString(1) );
                }
                NRows2 = NRows2 + 1;
            }else{
                int HMS[] = Funcs_Set.HHMMSS(time);
                timeinit = HMS[0] + ":" + HMS[1] + ":" + HMS[2];
                HMS[0] = HMS[0] + 1;
                if( HMS[0]>=24 ){
                    HMS[0] = HMS[0] - 24;
                }
                time = HMS[0] + ":" + HMS[1] + ":" + HMS[2];
                NRows2 = 1;
            }
            ///
            ///
            String query =  "INSERT INTO " + USERMAKE +
                " (id, " +
                "Fecha, " +
                "Orden, " +
                "HoraInicio, " +
                "Apellidos, " +
                "Nombres, " +
                "Edad, " +
                "City, " +
                "email, " +
                "image, " +
                "latitude, " +
                "longitude, " +
                "time, " +
                "file, " +
                "Anotaciones, " +
                "Datas) " +
                " VALUES " +
                "(" + id + ", " +
                "'2019/12/06', " +
                NRows2 + ", " +
                "'" + timeinit + "', " +
                "'Peres', " +
                "'Juan', " +
                "55, " +
                "'Valledupar', " +
                "'juanperez@mail.com', " +
                "?, " +
                "'0.0', " +
                "'0.0', " +
                "'" + time + "', " + 
                "'uploads/JSON_Alejandro_Utria_20191205_104642.txt', " +
                "'', " +
                "?)";
                PreparedStatement pstmt = myConnection.prepareStatement( query );
                pstmt.setBytes( 1, b_y_image );
                pstmt.setBytes( 2, b_y_DatasO );
                pstmt.execute();
                System.out.println("iuser :" + iuser + " : " + NRows2 );
            }
        } catch ( SQLException ex ){
            Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="Borrar_tabla">
    boolean Borrar_tabla( Connection myConnectionF, String db_name, String table_name ){
        try{
            stmt.execute("USE " + db_name );
            stmt.execute("DROP  TABLE if EXISTS " + table_name );
            return true;
        }catch(Exception ex ){
            Funcs_Set.ShowErrorMsg( ex.getLocalizedMessage(), " Error Borrar_tabla" );
            return false;
        }
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="GetFileFromURL">
    public byte[] GetFileFromURL( String UrlOfFile ){ return GetFileFromURL( UrlOfFile, null, null ); }
    public byte[] GetFileFromURL( String UrlOfFile, JProgressBar jpb, String StrTitleplus ){
        ///String ftpUrl = "ftp://tom:secret@www.myserver.com/project/2012/Project.zip;type=i";
        try {
            URL url = new URL( UrlOfFile );
            //System.out.println( UrlOfFile );
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
            // always check HTTP response code first
            if ( responseCode == HttpURLConnection.HTTP_OK ) {
                //System.out.println( "if ( responseCode == HttpURLConnection.HTTP_OK ) {: " );
            }
            URLConnection conn = url.openConnection();
            URLConnection uc = url.openConnection();
            uc.connect();
            int gcl = uc.getContentLength();
            byte[] b_2 = new byte[gcl];
            byte[] b_tmp = new byte[65536];
            //// System.out.println( "getContentLength: " + gcl );
            ////
            InputStream inputStream = conn.getInputStream();
            //// System.out.println( "available: " + inputStream.available() );
            int NBP = 0;
            int n2r = inputStream.available();
            while( n2r>0 ){
                n2r = inputStream.read( b_tmp );
                if( n2r<0 ){
                    break;
                }
                for( int i=0; i<n2r; i++ ){
                    b_2[i+NBP] = b_tmp[i];
                }
                NBP = NBP+n2r;
                if( jpb!=null ){
                    JDialog topFrame = (JDialog) SwingUtilities.getWindowAncestor(jpb);
                    topFrame.setTitle( StrTitleplus + " (" + (int)( (100.0*NBP)/( (double)gcl ) ) + "%)" );
                    jpb.setValue( (int)( (100.0*NBP)/( (double)gcl ) ) );
                }
            }
            inputStream.close();
            if( gcl==0 ){
                return ( "0, ".getBytes() );
            }
            return b_2;
        } catch (MalformedURLException ex) {
            ///Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
            Funcs_Set.ShowErrorMsg( ex.getLocalizedMessage(), " Error " );
        } catch (IOException ex) {
            ///Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
            Funcs_Set.ShowErrorMsg( ex.getLocalizedMessage(), " Error " );
        }
        return null;
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="CreateUser">
    public boolean CreateUser( String C_C_Number ){
        return CreateUser( myConnection, DataBase, C_C_Number );
    }
    public boolean CreateUser( Connection myConnectionF, String db_name, String C_C_Number ){
        try {
            if( myConnectionF==null ){
                return false;
            }
            stmt = myConnectionF.createStatement();
            stmt.execute("USE " + db_name);
            stmt.execute(" CREATE TABLE IF NOT EXISTS " + C_C_Number + "(" +
                    "indice int NOT NULL AUTO_INCREMENT," + 
                    "id TEXT(255)," +
                    "Fecha varchar(255)," +
                    "Orden int," +
                    "HoraInicio TEXT(255)," +
                    "Apellidos TEXT(255)," +
                    "Nombres TEXT(255)," +
                    "Edad int," +
                    "City TEXT(255)," +
                    "email TEXT(255)," +
                    "image BLOB(4194304)," +
                    "latitude TEXT(255)," +
                    "longitude TEXT(255)," +
                    "time TEXT(255)," +
                    "file TEXT(255)," +
                    ///
                    "Anotaciones TEXT(8388608)," +
                    "Datas BLOB(33554432)," +
                    "PRIMARY KEY (indice)" +
                    ");"
            );
            ///  stmt.execute( "INSERT INTO " + C_C_Number + " (indice) VALUES(0);" );
            return true;
        } catch (SQLException ex) {
            /// Logger.getLogger(PRUEBAS.class.getName()).log(Level.SEVERE, null, ex);
            Funcs_Set.ShowErrorMsg( ex.getLocalizedMessage(), " Error CreateUser" );
        }
        return false;
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="GetRowOfTable">
    int GetRowOfTable(Connection myConnectionF, String db_name, String C_C_Number ){
        int NRows = -1;
        try {
            stmt = myConnectionF.createStatement();
            stmt.execute("USE " + db_name);
            ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM " + C_C_Number + ";" );
            rs.next();
            NRows = Integer.parseInt( rs.getString(1) );
            while( rs.next() ){
            }
        } catch (SQLException ex) {
            ///Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
        } catch( java.lang.NumberFormatException N_E){
            ///Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, N_E);
        }
        return NRows;
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="ShowImage">
    public void ShowImage( JFrame JFP, int ind ){
        if( ind<0 || ind>=Prms.length ){
            Funcs_Set.ShowErrorMsg( "El usuario no tiene imagen.", " Error ShowImage " );
            return;
        }
        if( Prms[ind].image!=null ){
            final int indf = ind;
            JDialog d6 = new JDialog( JFP, "", Dialog.ModalityType.DOCUMENT_MODAL );
            d6.setIconImage(JFP.getIconImage());
            d6.setTitle( "Información de usuario." );
            /// d6.getContentPane().add( label, BorderLayout.CENTER );
            
            Image imT = ResizeImage( Prms[ind].image, 238, 238 );
            
            User_Info uinfo = new User_Info();
            uinfo.SetName( Prms[indf].name );
            uinfo.SetCedula( Prms[indf].cedula );
            uinfo.Setemail( Prms[indf].email );
            uinfo.SetImage( new ImageIcon(imT) );
            
            int ims[] = uinfo.GetImageSize();
            
            
            d6.getContentPane().add( uinfo, BorderLayout.CENTER );
            
            
            
            d6.setResizable( false );
            d6.pack();
            d6.setLocationRelativeTo( null );
            d6.setVisible(true);
            d6.setAlwaysOnTop(JFP.isAlwaysOnTop());
            d6.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        }else{
            Funcs_Set.ShowErrorMsg( "El usuario no tiene imagen.", " Error ShowImage " );
        }
    }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc=" RESIZE_IMAGE ">
    Image ResizeImage( Image im_I, int WWW, int HHH ){
        int W_I = im_I.getWidth(null);
        int H_I = im_I.getHeight(null);
        
        int NewW = WWW;
        int NewH = (WWW * H_I)/W_I;
        if( NewH>HHH ){
            NewH = HHH;
            NewW = (HHH * W_I)/H_I;
        }
        
        
        
        System.out.println( W_I + " - " + H_I );
        Image newImage = im_I.getScaledInstance( NewW, NewH, Image.SCALE_DEFAULT);
        return newImage;
    }
    
    
    
    
    
    
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCTIONS SET">
    
    public void SetDataBase( String D_B ){
        DataBase = D_B;
        OpenConnection( true );
    }
    ///
    ///
    ///
    public boolean SetPassWord( String D_B_PW ){
        PassWordDB = D_B_PW;
        if( !OpenConnection( true ) ){
            Funcs_Set.ShowErrorMsg( "Contraseña inválida!!!.", " Error SetPassWord " );
            return false;
        }
        return true;
    }
    ///
    ///
    ///
    public void FillAnotaciones( String D_B_PW ){
        for( int i=0; i<Prms.length; i++ ){
            Prms[i].Anotaciones = D_B_PW;
        }
    }
    ///
    ///
    ///
    public boolean SetAnotaciones( String D_B_PW, int IndDatas ){
        try {
            String query =  "UPDATE " + " user " + " SET anotaciones = ? WHERE " + 
                    "(id=" + Prms[IndDatas].id_of_user + " and cedula=" + Cedula_User + ");";
            
            if( myConnection.isClosed() ){
                if( !OpenConnection(false) ){
                    if( !OpenConnection(false) ){
                        Funcs_Set.ShowErrorMsg( "Se produjo un error.!!!.\n",
                                    " Error SetAnotaciones " );
                    }
                }
            }
            PreparedStatement pstmt = myConnection.prepareStatement( query );
            pstmt.setBytes( 1, D_B_PW.getBytes() );
            pstmt.execute();
            Prms[IndDatas].Anotaciones = D_B_PW;
        } catch (SQLException ex){
            try {
                if( !OpenConnection(false) ){
                    SetAnotaciones( D_B_PW, IndDatas );
                }
            } catch (Exception ex1) {
                Funcs_Set.ShowErrorMsg( "Se produjo un error.!!!.\n" + ex.getLocalizedMessage(),
                                    " Error SetAnotaciones " );
            }
            
        }
        return true;
    }
    ///
    ///
    ///
    public void SetCodigo( int CodigoI ){
        Codigo = CodigoI;
    }
    ///
    ///
    ///
    public boolean  SetUSER( String USER_D, boolean Setreconn ){
        Table_name = USER_D;
        Cedula_User = USER_D;
        if( !Setreconn ){
            return true;
        }
        try {
            stmt.execute( "SELECT 1 FROM " + Table_name + " LIMIT 1;" );
            return true;
        } catch (SQLException ex) {
            Funcs_Set.ShowErrorMsg( "El usuario no existe!!!.", " Error SetUSER " );
            return false;
        }
    }
    ///
    ///
    ///
    ///
    ///
    ///
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="GetPARAMS">
    ///
    public String Get_time_Params( int IndDatas ){
        
        if( IndDatas<0 || IndDatas>=Prms.length ){
            Funcs_Set.ShowErrorMsg( "No existen datos!!!.", " Error Get_time_Params " );
            return null;
        }
        String Str2Ret = Prms[IndDatas].time;
        /*
        String idt = Codigo + "";
        for( int i=0; i<Prms.length; i++ ){
            if( Prms[i].id==Codigo && Prms[i].Orden==IndDatas ){
                Str2Ret = Prms[i].HoraInicio;
                break;
            }
        }
        
        return Prms[IndDatas].HoraInicio;
        //*/
        return Str2Ret;
    }
    ///
    
    ///
    public String Get_time_ParamsC( int IndDatas ){
        
        if( IndDatas<0 || IndDatas>=Prms.length ){
            Funcs_Set.ShowErrorMsg( "No existen datos!!!.", " Error Get_time_Params " );
            return null;
        }
        String Str2Ret = Prms[IndDatas].timeC;
        /*
        String idt = Codigo + "";
        for( int i=0; i<Prms.length; i++ ){
            if( Prms[i].id==Codigo && Prms[i].Orden==IndDatas ){
                Str2Ret = Prms[i].HoraInicio;
                break;
            }
        }
        
        return Prms[IndDatas].HoraInicio;
        //*/
        return Str2Ret;
    }
    ///
    ///
    public int GetNParams(){
        if( Prms==null ){
            return 0;
        }
        return Prms.length;
    }
    ///
    ///
    public short[] GetDatas( int IndDatas ){
        if( IndDatas<0 || IndDatas>=Prms.length ){
            Funcs_Set.ShowErrorMsg( "No existen datos!!!.", " Error GetDatas " );
            return null;
        }
        return Prms[IndDatas].Datas;
    }
    ///
    ///
    ///
    public int[] GetHInit( int IndDatas ){
        if( IndDatas<0 || IndDatas>=Prms.length ){
            Funcs_Set.ShowErrorMsg( "No existen datos!!!.", " Error GetHInit " );
            return null;
        }
        int HMS[] = Funcs_Set.HHMMSS(Prms[IndDatas].HoraInicio);
        return HMS;
    }
    ///
    ///
    ///
    public String GetAnotaciones( int IndDatas ){
        if( IndDatas<0 || IndDatas>=Prms.length ){
            Funcs_Set.ShowErrorMsg( "No existen datos!!!.", " Error GetHInit " );
            return null;
        }
        return Prms[IndDatas].Anotaciones;
    }
    ///
    ///
    ///
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="Consultar">
    public boolean Consultar( JDialog jdlg, javax.swing.JProgressBar jpgr ){
        try {
            jdlg.setTitle( "Consultando..." );
            ResultSet rs;
            rs = stmt.executeQuery( "SELECT COUNT(*) FROM " + " registro " + " WHERE user_id=" + Codigo );
            /// System.out.println( "SELECT COUNT(*) FROM " + "registro " + " WHERE user_id=" + Codigo );
            rs.next();
            int RowN = Integer.parseInt( rs.getString(1) );
            if( RowN<1 ){
                Prms = null;
                Funcs_Set.ShowErrorMsg( "Sin datos.\n", " Error de consulta " );
                return false;
            }
            ///
            ///
            rs = stmt.executeQuery( "SELECT COUNT(*) " + " cedula " + " FROM " + " user " + " WHERE (id=" + Codigo + " and cedula=" + Cedula_User + ")"  );
            rs.next();
            int RowN2 = Integer.parseInt( rs.getString(1) );
            if( RowN2<1 ){
                Prms = null;
                Funcs_Set.ShowErrorMsg( "Sin datos.\n", " Error de consulta " );
                return false;
            }
            ///
            ///
            jdlg.setTitle( "Extrayendo..." );
            ///
            ///
            Prms = new Params[RowN];
            for( int i=0; i<Prms.length; i++ ){
                Prms[i] = new Params();
            }
            System.out.println( "RowN: "+ RowN );
            double vpgr;
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            String StrPI;
            int i_c;
            for( int ind = 0; ind<Columns_registro.length; ind++ ){
                i_c = 0;
                rs = stmt.executeQuery( "SELECT " + Columns_registro[ind] + " FROM " + "registro" + " WHERE user_id=" + Codigo  );
                /// System.out.println( "ROWS: " +  Columns_registro[ind] );
                while( rs.next() ){
                    vpgr = 50 + (  ((50.0* (ind+i_c) )/ (Columns_registro.length+RowN) ) );
                    StrPI = String.format( "Descargando: %s%%"+Columns_registro[ind], formatter.format((vpgr)) );
                    jdlg.setTitle( StrPI );
                    jpgr.setValue( (int)(vpgr) );
                    if( Columns_registro[ind].matches("id") ){
                        Prms[i_c].id = Funcs_Set.ParseInt(rs.getString(1));
                    }
                    ///
                    if( Columns_registro[ind].matches("time") ){
                        Prms[i_c].time = rs.getString(1);
                        int ispc = Prms[i_c].time.indexOf( " " );
                        Prms[i_c].timeC = Prms[i_c].time.intern();
                        Prms[i_c].time = Prms[i_c].time.subSequence( ispc+1, Prms[i_c].time.length() ) + "";
                    }
                    ///
                    if( Columns_registro[ind].matches("file") ){
                        Prms[i_c].file = Kardia_URL + rs.getString(1);
                    }
                    ///
                    if( Columns_registro[ind].matches("lat") ){
                        Prms[i_c].lat = rs.getString(1);
                    }
                    ///
                    if( Columns_registro[ind].matches("lng") ){
                        Prms[i_c].lng = rs.getString(1);
                    }
                    ///
                    i_c++;
                }
            }
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ///
            for( int ind = 0; ind<Prms.length; ind++ ){
                Prms[ind].HoraInicio = Prms[ind].time;
                ///System.out.println( "HoraInicio:" + Prms[ind].time );
            }
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            for( int ind = 0; ind<Columns_user.length; ind++ ){
                i_c = 0;
                rs = stmt.executeQuery( "SELECT " + Columns_user[ind] + " FROM " + " user " + " WHERE (id=" + Codigo + " and cedula=" + Cedula_User + ")"  );
                /// System.out.println( "ROWS: " +  Columns_user[ind] );
                while( rs.next() ){
                    vpgr = 50 + (  ((50.0* (ind+i_c) )/ (Columns_user.length+RowN) ) );
                    StrPI = String.format( "Descargando: %s%%"+Columns_user[ind], formatter.format((vpgr)) );
                    jdlg.setTitle( StrPI );
                    jpgr.setValue( (int)(vpgr) );
                    ///
                    if( Columns_user[ind].matches("id") ){
                        String str_T2 = rs.getString( 1 );
                        Prms[0].id_of_user = Funcs_Set.ParseInt( str_T2 );
                        for( int ind2 = 1; ind2<Prms.length; ind2++ ){
                            Prms[ind2].id_of_user = Prms[0].id_of_user;
                        }
                    }
                    ///
                    if( Columns_user[ind].matches("name") ){
                        Prms[0].name = rs.getString(1);
                        for( int ind2 = 1; ind2<Prms.length; ind2++ ){
                            Prms[ind2].name = Prms[0].name;
                        }
                    }
                    ///
                    if( Columns_user[ind].matches("image") ){
                        for( int ind2 = 0; ind2<Prms.length; ind2++ ){
                            Prms[ind2].image = null;
                        }
                        String strT = rs.getString(1);
                        byte b_t[] = GetFileFromURL( strT );
                        InputStream targetStream = new ByteArrayInputStream(b_t);
                        Image Im;
                        Im = ImageIO.read(targetStream);
                        if( Im!=null ){
                            Prms[0].image = Im;
                            for( int ind2 = 1; ind2<Prms.length; ind2++ ){
                                Prms[ind2].image = Prms[0].image;
                            }
                        }
                    }
                    ///
                    if( Columns_user[ind].matches("google_id") ){
                        Prms[0].google_id = rs.getString(1);
                        for( int ind2 = 1; ind2<Prms.length; ind2++ ){
                            Prms[ind2].google_id = Prms[0].google_id;
                        }
                    }
                    ///
                    if( Columns_user[ind].matches("cedula") ){
                        Prms[0].cedula = rs.getString(1);
                        for( int ind2 = 1; ind2<Prms.length; ind2++ ){
                            Prms[ind2].cedula = Prms[0].cedula;
                        }
                    }
                    ///
                    if( Columns_user[ind].matches("email") ){
                        Prms[0].email = rs.getString(1);
                        for( int ind2 = 1; ind2<Prms.length; ind2++ ){
                            Prms[ind2].email = Prms[0].email;
                        }
                    }
                    ///
                    if( Columns_user[ind].matches("anotaciones") ){
                        Prms[0].Anotaciones = rs.getString(1);
                        if( Prms[0].Anotaciones==null ){
                            Prms[0].Anotaciones = " ";
                        }
                        for( int ind2 = 1; ind2<Prms.length; ind2++ ){
                            Prms[ind2].Anotaciones = Prms[0].Anotaciones;
                        }
                    }
                    ///
                    i_c++;
                }
            }
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            for( int ind = 0; ind<Prms.length; ind++ ){
                /// System.out.println( "" + Prms[ind].file );
            }
            final int lpass = (int)( (5*Fs) );
            for( int ind = 0; ind<Prms.length; ind++ ){
                vpgr = 50 + (  ((50.0* (ind) )/ (Prms.length+1) ) );
                StrPI = String.format( "Descargando datos: %s%%", formatter.format((vpgr)) );
                jdlg.setTitle( StrPI );
                jpgr.setValue( (int)(vpgr) );
                Prms[ind].Datas = null;
                byte b_t[] = GetFileFromURL( Prms[ind].file, jpgr, StrPI );
                if( b_t!=null ){
                    Prms[ind].Datas = Funcs_Set.GetVaulesforString( b_t );
                    if( Prms[ind].Datas.length<lpass ){
                        short datT[] = new short[ lpass ];
                        for( int i=0; i<datT.length; i++ ){
                            datT[i] = 512;
                        }
                        for( int i=0; i<Prms[ind].Datas.length; i++ ){
                            datT[i] = Prms[ind].Datas[i];
                        }
                        Prms[ind].Datas = datT;
                    }
                }
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
                /*/
                for( int ind2 = 1; ind2<Prms.length; ind2++ ){
                    Prms[ind2].Datas = Funcs_Set.GetVaulesforString( b_t );
                }
                System.out.println( "Datas.length: " + Prms[ind].Datas.length );
                break;
                //*/
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            }
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// 
            /// System.out.println( "" + Prms[i_c].file );
            /// System.exit( 0 );
        } catch (SQLException ex) {
            //System.out.println( ex.getLocalizedMessage() );
            Funcs_Set.ShowErrorMsg( "Consultar!!!.\n" + ex.getLocalizedMessage(), " Error de consulta " );
            Logger.getLogger(DB_KARDIA.class.getName()).log(Level.SEVERE, null, ex);
            System.exit( -1 );
            return false;
        } catch (IOException ex) {
            Logger.getLogger(DB_KARDIA_2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
}
