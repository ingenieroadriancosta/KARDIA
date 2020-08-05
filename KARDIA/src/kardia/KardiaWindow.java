package kardia;
//<editor-fold defaultstate="collapsed" desc="imports">
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static kardia.KARDIA.MainFrame;
import static kardia.PERMITIDO.Fs;
//</editor-fold>
public final class KardiaWindow extends javax.swing.JFrame {
///
    ///              uploads/JSON_3__20200116_235546.txt
    ///              uploads/JSON_3__20200116_131252.txt
    /// http://kardia.ga/uploads/JSON_3__20200116_235546.txt
    /*
    ALTER TABLE user_77000000 ADD added_column varchar(1024) NOT NULL AFTER Datas;
    ALTER TABLE user_77000000 ADD added_column varchar(1024) NOT NULL;
    //*/
///
//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    static JDialog                                          JD_PGR = null;
    static javax.swing.JProgressBar                         JPgr = null;
    static ploteos                                          plt = null;
    static DB_KARDIA_2                                      K_DB = null;
    
    JFrame                                                  OwnJFrm = this;
    boolean                                                 In_Thr_Plot = false;
    boolean                                                 Thr_Quit = false;
    static double                                           Time     = 24;
    static short                                            Buff[]   = new short[(int)(Time*Fs)];
    String                                                  SAnots = "";
//</editor-fold>
///
///
//<editor-fold defaultstate="collapsed" desc="KardiaWindow, InitMain">
    public KardiaWindow(){
        Funcs_Set.SetLookAndFeel(1);
        setResizable( true );
        setVisible( false );
        setExtendedState( JFrame.MAXIMIZED_BOTH );
        initComponents();
        InitMain();
    }
    ///
    ///
    ///
    void InitMain(){
        
        
        
        JPassword.setVisible( false );
        this.jLabel1.setVisible( false );
        
        setLocationRelativeTo( null );
        Image IconI = Toolkit.getDefaultToolkit().createImage( getClass().getResource( "Logo cardia principal.png" ) );
        setIconImage(IconI);
        BufferedImage ii;
        try {
            Logo1.setHorizontalAlignment( JLabel.CENTER );
            ii = ImageIO.read(KardiaWindow.class.getResourceAsStream("Logo cardia.png"));
            Logo1.setIcon( new ImageIcon(ii) );
            
            
            Logo2.setHorizontalAlignment( JLabel.CENTER );
            ii = ImageIO.read(KardiaWindow.class.getResourceAsStream("logo kardia n.png"));
            Logo2.setIcon( new ImageIcon(ii) );
            
            
            Logo3.setHorizontalAlignment( JLabel.CENTER );
            ii = ImageIO.read(KardiaWindow.class.getResourceAsStream("Logo Ing. EL.png"));
            Logo3.setIcon( new ImageIcon(ii) );
        } catch( IOException ex ){
            Logger.getLogger(KardiaWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        ///
        ///
        ///
        K_DB = new DB_KARDIA_2();
        ///
        ///
        ///
        JD_PGR = new JDialog( this, "", Dialog.ModalityType.DOCUMENT_MODAL );
        JD_PGR.setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
        JPgr = new javax.swing.JProgressBar();
        JD_PGR.setSize( 300, 60 );
        JPgr.setSize( 300, 60 );
        JPgr.setValue( 50 );
        JD_PGR.getContentPane().add( JPgr, BorderLayout.CENTER );
        JD_PGR.setResizable( false );
        ///JD_PGR.pack();
        JD_PGR.setLocationRelativeTo( null );
        JD_PGR.setAlwaysOnTop(true);
        ///
        ///
        ///
        
        JPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke){
                if( ((int)ke.getKeyChar())==10 ){
                    JConsultar.setSelected( !JConsultar.isSelected() );
                    JConsultarActionPerformed( null );
                }
            }
        });
        JPassword.setToolTipText( "Contraseña de la base de datos" );
        JPassword.setText( Servidor_Info.PassWord );
        ///
        ///
        JCedula.setToolTipText( "Cédula del paciente" );
        JCedula.addKeyListener(new KeyAdapter() {
            javax.swing.JTextField JTF = JCedula;
            void FillOnlyNumber( KeyEvent ke ){
                if( ((int)ke.getKeyChar())==10 ){
                    JConsultar.setSelected( !JConsultar.isSelected() );
                    JConsultarActionPerformed( null );
                    return;
                }
                if (ke.getKeyChar()>='0' && ke.getKeyChar()<='9') {
                    String str2 = JTF.getText();
                    if( str2.length()>11 ){
                        JTF.setText(str2.substring(0, 11));
                    }else{
                        JTF.setText(str2);
                    }
                } else {
                    if( ke.getKeyChar()<32 || ke.getKeyChar()>255 ){
                        return;
                    }
                    ke.setKeyChar( (char)0 );
                    String value = JTF.getText();
                    String str2 = "";
                    for( int i=0; i<value.length(); i++ ){
                        if( value.charAt(i)>='0' && value.charAt(i)<='9' ){
                            str2 = str2 + value.charAt(i);
                        }
                    }
                    if( str2.length()>11 ){
                        JTF.setText(str2.substring(0, 11));
                    }else{
                        JTF.setText(str2);
                    }
                }
            }
            
            @Override
            public void keyPressed(KeyEvent ke){
                FillOnlyNumber( ke );
            }
            ///
            @Override
            public void keyReleased(KeyEvent ke){
                FillOnlyNumber( ke );
            }
            ///
            @Override
            public void keyTyped(KeyEvent ke){
                FillOnlyNumber( ke );
            }
            ///
        });
        ///
        JCodigo.setToolTipText( "Código del examen" );
        JCodigo.addKeyListener(new KeyAdapter() {
            javax.swing.JTextField JTF = JCodigo;
            void FillOnlyNumber( KeyEvent ke ){
                if( ((int)ke.getKeyChar())==10 ){
                    JConsultar.setSelected( !JConsultar.isSelected() );
                    JConsultarActionPerformed( null );
                    return;
                }
                if (ke.getKeyChar()>='0' && ke.getKeyChar()<='9') {
                    String str2 = JTF.getText();
                    if( str2.length()>6 ){
                        JTF.setText(str2.substring(0, 6));
                    }else{
                        JTF.setText(str2);
                    }
                } else {
                    if( ke.getKeyChar()<32 || ke.getKeyChar()>255 ){
                        return;
                    }
                    ke.setKeyChar( (char)0 );
                    String value = JTF.getText();
                    String str2 = "";
                    for( int i=0; i<value.length(); i++ ){
                        if( value.charAt(i)>='0' && value.charAt(i)<='9' ){
                            str2 = str2 + value.charAt(i);
                        }
                    }
                    if( str2.length()>6 ){
                        JTF.setText(str2.substring(0, 6));
                    }else{
                        JTF.setText(str2);
                    }
                }
            }
            
            @Override
            public void keyPressed(KeyEvent ke){
                FillOnlyNumber( ke );
            }
            ///
            @Override
            public void keyReleased(KeyEvent ke){
                FillOnlyNumber( ke );
            }
            ///
            @Override
            public void keyTyped(KeyEvent ke){
                FillOnlyNumber( ke );
            }
            ///
        });
        ///
        User_Picture.setEnabled( false );
        ///PlotPanel.repaint();
        ///
        ///
        ///
        JDatos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                if(arg0.getStateChange() == ItemEvent.SELECTED){
                    //// System.out.println( "SelectedIndex: " + JDatos.getSelectedIndex() );
                    //*
                    short[] sht = K_DB.GetDatas( JDatos.getSelectedIndex() );
                    int[] HMS = K_DB.GetHInit( JDatos.getSelectedIndex() );
                    plt.Set_HMSADD( 3600*HMS[0] + 60*HMS[1] + HMS[2] );
                    plt.plot( sht );
                    //// System.out.println( "sht.length: " + sht.length );
                    //*/
                }
            }
        });
        ///
        ///
        Rectangle bounds;
        Insets insets;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        insets = Toolkit.getDefaultToolkit().getScreenInsets(ge.getDefaultScreenDevice()
                .getDefaultConfiguration());
        bounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= (insets.left + insets.right);
        bounds.height -= (insets.top + insets.bottom);
        
        
        setLocationRelativeTo( null );
        
        
        if( PERMITIDO.WWW==0 || PERMITIDO.HHH==0 ){
            setSize( (int)bounds.width, (int)bounds.height );
        }
        
        
        setMinimumSize( new Dimension( bounds.width, bounds.height) );
        setResizable( true );
        setVisible( false );
        setExtendedState( JFrame.MAXIMIZED_BOTH );
        setVisible( true );
        
        
        
        
        
        
        
        
        
        
        
        
        ploteos.set_Fs( 1500 );
        setTitle( "KARDIASOFT" );
        setVisible( true );
        setAlwaysOnTop( PERMITIDO.AlwaysOnTop );
        ShowProgress();
        new Thread(){
            @Override
            public void run(){
                In_Thr_Plot = true;
                /// try {Thread.sleep( 200 );} catch (InterruptedException ex) {}
                PlotPanel.setLayout( null );
                plt = new ploteos(PlotPanel);
                plt.SetJFrm( OwnJFrm );
                
                
                
                /// new Signal( K_DB );
                try {
                    /// K_DB.stmt.execute( "DROP TABLE  SHOWSIGNAL " );
                    ///K_DB.stmt.execute( "CREATE TABLE  IF NOT EXISTS  SHOWSIGNAL( id INT )" );
                    K_DB.stmt.execute( "SELECT * FROM SHOWSIGNAL" );
                    //plt.ShowSignal();
                } catch (SQLException ex) {
                    /// Logger.getLogger(KardiaWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                
                /// Buff = Funcs_Set.GetVaulesforfileResource( "JSON_Alejandro_Utria_INICIO2.txt" );
                Buff = Funcs_Set.GetVaulesforfileResource( "datos1H.txt" );
                ///try {Thread.sleep( 10000 );} catch (InterruptedException ex) {}
                ///
                plt.plot( Buff );
                ///
                Time = Buff.length/Fs;
                for( double i=0; (i<(Time-1) && !Thr_Quit); i = i + 1/10.0 ){
                    ///plt.plot( Buff, (int)(i*Fs), (int)((i+1)*Fs) );
                    ///try {Thread.sleep( 100 );} catch (InterruptedException ex) {}
                }
                ///
                JD_PGR.setVisible( false );
                In_Thr_Plot = false;
                ///
            }
        }.start();
        /// System.exit(-1);
        
        
        
        //
        
        ///
        ///
        ///
    }
    ///
    ///
    ///
//</editor-fold>
///
///
///
//<editor-fold defaultstate="collapsed" desc="CONSULTAR">
    boolean in_cons = false;
    boolean SetRet_Cons = false;
    void Consultar(){
        if( in_cons ){
            return;
        }
        new Thread(){
            @Override
            public void run(){
            in_cons = true;
            ///
            ///
            String PassS = new String( JPassword.getPassword() );
            ///
            JDialog d6 = new JDialog( OwnJFrm, "", Dialog.ModalityType.DOCUMENT_MODAL );
            d6.setSize( 200, 140 );
            d6.setResizable( false );
            d6.setTitle( "Ingrese contraseña:" );
            PassPanel ppan = new PassPanel();
            
            
            ppan.GetPText().setText( Servidor_Info.PassWord );
            
            ppan.GetBAc().addActionListener( new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    d6.setVisible( false );
                }
            });
            
            d6.add(ppan);
            d6.setLocationRelativeTo( null );
            
            
            
            SetRet_Cons = false;
            d6.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                    SetRet_Cons = true;
                }}
            );
            
            
            d6.setVisible( true );
            
            
            
            
            
            if( SetRet_Cons ){
                JConsultar.setSelected( false );
                JD_PGR.setVisible( false );
                setEnabled( true );
                Anotaciones.setEnabled( false );
                User_Picture.setEnabled( false );
                in_cons = false;
                return;
            }
            
            
            
            
            
            
            
            
            PassS = new String( ppan.GetPText().getPassword() );
            // JPasswordField
            ///
            ///
            ///
            SAnots = "";
            JD_PGR.setTitle( "Conectando..." );
            JPgr.setValue( 10 );
            ShowProgress();
            JPgr.setValue( 20 );
            /// MainFrame.setEnabled( false );
            K_DB.SetCodigo( Funcs_Set.ParseInt( JCodigo.getText() ) );
            K_DB.SetUSER( JCedula.getText(), false );
            JPgr.setValue( 50 );
            
            
            
            
            
            
            
            
            if( !K_DB.SetPassWord( PassS ) ){
                JConsultar.setSelected( false );
                JD_PGR.setVisible( false );
                setEnabled( true );
                Anotaciones.setEnabled( false );
                User_Picture.setEnabled( false );
                in_cons = false;
                return;
            }
            ///
            ///
            ///
            boolean bok = K_DB.Consultar( JD_PGR, JPgr );
            ///
            if( bok ){
                String Mod[] = new String[K_DB.GetNParams()];
                for( int i=0; i<Mod.length; i++ ){
                    Mod[i] = K_DB.Get_time_ParamsC(i);
                }
                JDatos.setModel(new javax.swing.DefaultComboBoxModel( Mod ));
                JDatos.setSelectedIndex( -1 );
                ///
                ///
                ///
                JPgr.setValue( 100 );
                setEnabled( true );
                JDatos.setEnabled( true );
                JD_PGR.setVisible(false);
                JConsultar.setText( "En consulta" );
                ///K_DB.ShowImage(MainFrame);
                JCedula.setEnabled( false );
                JCodigo.setEnabled( false );
                JPassword.setEnabled( false );
                User_Picture.setEnabled( true );
                Anotaciones.setEnabled( true );
                ///
                JD_PGR.setVisible( false );
            }else{
                JConsultar.setSelected( false );
                JCodigo.setEnabled( true );
                JPassword.setEnabled( true );
                ///
                JD_PGR.setVisible( false );
                User_Picture.setEnabled( false );
                Anotaciones.setEnabled( false );
                in_cons = false;
            }
            ///
            ///
            ///
            ///
            
            ///
            ///
            ///
            ///
            in_cons = false;
            ///
            }
        }.start();
    }
//</editor-fold>
///
///
///
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="ShowProgress">
    boolean bl_inshpgr = false;
    void ShowProgress(){
        if( bl_inshpgr ){
            return;
        }
        new Thread(){
            @Override
            public void run(){
            bl_inshpgr = true;
            ///
            JD_PGR.setAlwaysOnTop( PERMITIDO.AlwaysOnTop );
            JD_PGR.setVisible(true);
            ///
            bl_inshpgr = false;
            ///
            }
        }.start();
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelLogos = new javax.swing.JPanel();
        Logo1 = new javax.swing.JLabel();
        Logo3 = new javax.swing.JLabel();
        Logo2 = new javax.swing.JLabel();
        PanelUsuario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        JPassword = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        JCedula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        JCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        JDatos = new javax.swing.JComboBox<String>();
        Anotaciones = new javax.swing.JButton();
        User_Picture = new javax.swing.JButton();
        JConsultar = new javax.swing.JToggleButton();
        PlotPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setName("MainWindow"); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        PanelLogos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelLogos.setMaximumSize(new java.awt.Dimension(32767, 200));

        javax.swing.GroupLayout PanelLogosLayout = new javax.swing.GroupLayout(PanelLogos);
        PanelLogos.setLayout(PanelLogosLayout);
        PanelLogosLayout.setHorizontalGroup(
            PanelLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLogosLayout.createSequentialGroup()
                .addComponent(Logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Logo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Logo3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PanelLogosLayout.setVerticalGroup(
            PanelLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Logo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Logo3, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
            .addComponent(Logo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        PanelUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Contraseña:");

        JPassword.setText("Utria2021");

        jLabel2.setText("Cédula:");

        JCedula.setText("1121303493");
        JCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCedulaActionPerformed(evt);
            }
        });

        jLabel3.setText("Código de examen:");

        JCodigo.setText("6");

        jLabel4.setText("Datos:");

        JDatos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        JDatos.setToolTipText("Partes del examen");
        JDatos.setEnabled(false);
        JDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JDatosActionPerformed(evt);
            }
        });

        Anotaciones.setText("Anotaciones");
        Anotaciones.setToolTipText("Anotaciones");
        Anotaciones.setEnabled(false);
        Anotaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnotacionesActionPerformed(evt);
            }
        });

        User_Picture.setText("Informacion");
        User_Picture.setToolTipText("Imagen del usuario");
        User_Picture.setEnabled(false);
        User_Picture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                User_PictureActionPerformed(evt);
            }
        });

        JConsultar.setText("Consultar");
        JConsultar.setToolTipText("Iniciar o terminar consulta");
        JConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelUsuarioLayout = new javax.swing.GroupLayout(PanelUsuario);
        PanelUsuario.setLayout(PanelUsuarioLayout);
        PanelUsuarioLayout.setHorizontalGroup(
            PanelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUsuarioLayout.createSequentialGroup()
                .addComponent(JConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JCedula, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Anotaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(User_Picture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelUsuarioLayout.setVerticalGroup(
            PanelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(JPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2)
                .addComponent(JCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3)
                .addComponent(JCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel4)
                .addComponent(JDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(Anotaciones)
                .addComponent(User_Picture)
                .addComponent(JConsultar))
        );

        PlotPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout PlotPanelLayout = new javax.swing.GroupLayout(PlotPanel);
        PlotPanel.setLayout(PlotPanelLayout);
        PlotPanelLayout.setHorizontalGroup(
            PlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 948, Short.MAX_VALUE)
        );
        PlotPanelLayout.setVerticalGroup(
            PlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelLogos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PlotPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelLogos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PlotPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:
        /// 
        
    }//GEN-LAST:event_formWindowStateChanged

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formComponentResized

    private void JConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JConsultarActionPerformed
        // TODO add your handling code here:
        if( JConsultar.isSelected() ){
            
            
            Consultar();
        }else{
            JDatos.setEnabled( false );
            JConsultar.setText( "Consultar" );
            JCedula.setEnabled( true );
            JCodigo.setEnabled( true );
            JPassword.setEnabled( true );
            User_Picture.setEnabled( false );
            Anotaciones.setEnabled( false );
        }
    }//GEN-LAST:event_JConsultarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseReleased

    private void AnotacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnotacionesActionPerformed
        // TODO add your handling code here:
        
        if( (JConsultar.isSelected() || true) && JDatos.getSelectedIndex()>-1 ){
            JDialog d6 = new JDialog( this, "", Dialog.ModalityType.DOCUMENT_MODAL );
            d6.setSize( 500, 300 );
            d6.setLocationRelativeTo( null );
            d6.setAlwaysOnTop( isAlwaysOnTop() );
            
            
            AnotaClass ancla = new AnotaClass();
            d6.getContentPane().add( ancla, BorderLayout.CENTER );
            
            SAnots = K_DB.GetAnotaciones( JDatos.getSelectedIndex() );
            K_DB.FillAnotaciones( SAnots );
            K_DB.SetAnotaciones(SAnots, JDatos.getSelectedIndex() );
            
            
            ancla.GetTextA().setText(SAnots);
            ancla.GetTextA().setBorder( BorderFactory.createEmptyBorder(2, 2, 2, 2) );
            ancla.GetTextA().setFont( new Font( "Serif", Font.ITALIC, 16 ) );
            
            d6.setTitle( "Anotaciones" );
            
            
            ancla.GetButtS().addActionListener(new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e) {
                    SAnots = ancla.GetTextA().getText();
                    K_DB.FillAnotaciones( SAnots );
                    /// K_DB.Prms[JDatos.getSelectedIndex()]
                    /// System.out.println( SAnots );
                    if( !K_DB.SetAnotaciones(SAnots, JDatos.getSelectedIndex() ) ){
                        if( !K_DB.SetAnotaciones(SAnots, JDatos.getSelectedIndex() ) ){
                            Funcs_Set.ShowErrorMsg( "No se actualizaron los datos.!!!.\n",
                                            " Error  " );
                        }else{
                            JOptionPane.showMessageDialog( null, "Los datos se guardaron correctamente.!!!.\n",
                                    " Guardar  ", JOptionPane.INFORMATION_MESSAGE );
                            
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog( null, "Los datos se guardaron correctamente.!!!.\n",
                                    " Guardar  ", JOptionPane.INFORMATION_MESSAGE );
                    }
                }
            });
            
            d6.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                    
                }}
            );
            
            
            d6.setVisible( true );
            
            
            SAnots = ancla.GetTextA().getText();
            K_DB.FillAnotaciones( SAnots );
            /// K_DB.Prms[JDatos.getSelectedIndex()]
            /// System.out.println( SAnots );
            if( !K_DB.SetAnotaciones(SAnots, JDatos.getSelectedIndex() ) ){
                if( !K_DB.SetAnotaciones(SAnots, JDatos.getSelectedIndex() ) ){
                    Funcs_Set.ShowErrorMsg( "No se actualizaron los datos.!!!.\n",
                                    " Error  " );
                }else{
                    JOptionPane.showMessageDialog( null, "Los datos se guardaron correctamente.!!!.\n",
                                    " Guardar  ", JOptionPane.INFORMATION_MESSAGE );
                }
            }else{
                JOptionPane.showMessageDialog( null, "Los datos se guardaron correctamente.!!!.\n",
                                    " Guardar  ", JOptionPane.INFORMATION_MESSAGE );
            }
            
            
            
            boolean setr = true;
            if( setr ){
                return;
            }
            JTextArea J_E = new JTextArea();
            JButton  S_B = new JButton();
            S_B.setText( "Guardar" );
            S_B.setSize( 120, 60 );
            
            
            Container JCP = new Container();
            JCP.setLayout(null);
            
            
            
            JCP.setSize( 300, 64 );
            S_B.setLocation( 1, 1 );
            S_B.setSize( 120, 60 );
            
            JCP.add( S_B );
            
            
            
            
            SAnots = K_DB.GetAnotaciones( JDatos.getSelectedIndex() );
            K_DB.FillAnotaciones( SAnots );
            K_DB.SetAnotaciones(SAnots, JDatos.getSelectedIndex() );
            J_E.setText(SAnots);
            
            
            
            
            
            d6.setTitle( "Anotaciones" );
            
            
            
            
            J_E.setBorder( BorderFactory.createEmptyBorder(2, 2, 2, 2) );
            J_E.setFont( new Font( "Serif", Font.ITALIC, 16 ) );
            
            
            
            d6.add(JCP);
            // d6.getContentPane().add( JCP, BorderLayout.NORTH );
            
            
            
            
            
            
            d6.getContentPane().add( J_E, BorderLayout.CENTER );
            d6.setResizable( true );
            JScrollPane JSP = new JScrollPane(J_E, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            
            
            
            // JSP.setLocation( 62, 120 );
            
            
            JSP.setLocation( J_E.getLocation() );
            JSP.setSize( J_E.getSize() );
            JSP.setVisible(true);
            d6.getContentPane().add( JSP, BorderLayout.CENTER );
            d6.setSize( 500, 300 );
            d6.setLocationRelativeTo( null );
            d6.setAlwaysOnTop( isAlwaysOnTop() );
            d6.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
            d6.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                    
                }}
            );
            d6.setVisible(true);
            SAnots = J_E.getText();
            K_DB.FillAnotaciones( SAnots );
            /// K_DB.Prms[JDatos.getSelectedIndex()]
            /// System.out.println( SAnots );
            if( !K_DB.SetAnotaciones(SAnots, JDatos.getSelectedIndex() ) ){
                if( !K_DB.SetAnotaciones(SAnots, JDatos.getSelectedIndex() ) ){
                    Funcs_Set.ShowErrorMsg( "No se actualizaron los datos.!!!.\n",
                                    " Error  " );
                }else{
                    JOptionPane.showMessageDialog( null, "Los datos se guardaron correctamente.!!!.\n",
                                    " Guardar  ", JOptionPane.INFORMATION_MESSAGE );
                }
            }else{
                JOptionPane.showMessageDialog( null, "Los datos se guardaron correctamente.!!!.\n",
                                    " Guardar  ", JOptionPane.INFORMATION_MESSAGE );
            }
        }else{
            Funcs_Set.ShowErrorMsg( "Sin datos.!!!.\n",
                                    " Error  " );
        }
    }//GEN-LAST:event_AnotacionesActionPerformed

    private void User_PictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_User_PictureActionPerformed
        // TODO add your handling code here:
        if( JConsultar.isSelected() ){
            K_DB.ShowImage( this, JDatos.getSelectedIndex() );
        }  
    }//GEN-LAST:event_User_PictureActionPerformed

    private void JCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JCedulaActionPerformed

    private void JDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JDatosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JDatosActionPerformed

/*
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KardiaWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KardiaWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KardiaWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KardiaWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KardiaWindow().setVisible(true);
            }
        });
    }
//*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Anotaciones;
    private javax.swing.JTextField JCedula;
    private javax.swing.JTextField JCodigo;
    private javax.swing.JToggleButton JConsultar;
    private javax.swing.JComboBox<String> JDatos;
    private javax.swing.JPasswordField JPassword;
    private javax.swing.JLabel Logo1;
    private javax.swing.JLabel Logo2;
    private javax.swing.JLabel Logo3;
    private javax.swing.JPanel PanelLogos;
    private javax.swing.JPanel PanelUsuario;
    private javax.swing.JPanel PlotPanel;
    private javax.swing.JButton User_Picture;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
