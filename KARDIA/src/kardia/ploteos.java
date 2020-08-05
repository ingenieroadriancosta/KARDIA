package kardia;
//<editor-fold defaultstate="collapsed" desc="imports">
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static javax.swing.SwingConstants.*;
import static kardia.PERMITIDO.BARLOW;
import static kardia.PERMITIDO.Fs;
import static kardia.PERMITIDO.Visible_Signal;
//</editor-fold>

public class ploteos extends JPanel{
//<editor-fold defaultstate="collapsed" desc="VARS">
    ploteos                                         OwnPloteos = this;
    JPanel                                          PanelF      = null;
    JPanel                                          PanelProp   = null;
    JPanel                                          PanelCtrls   = null;
    JButton                                         Reestablecer   = null;
    JToggleButton                                   ZoomP   = null;
    JToggleButton                                   PanP   = null;
    Ploter                                          PanelPlot   = null;
    JLabel                                          JTextInfo   = null;
    JLabel                                          LogoUPC   = null;
    JScrollBar                                      JSlTime = null;
    JFrame                                          jfrm = null;
    JButton                                         JSignal = null;
    JButton                                         JSoporte = null;
    
    int                                             WidthP      = 1;
    int                                             HeightP     = 1;
    //
    ///
    String                                          StrPI = "0.0005";
    Font                                            FntPl = new Font("TimesRoman", Font.PLAIN, 10);
    ///
    final           int                             w_tx = 38;
    final           int                             h_tx = 16;
    final           int                             x_p = 30;
    final           int                             y_p = 34;
    final           int                             L_X = 30;
    final           int                             L_Y = 30;
    ///
    static boolean                                  Fs_Changed = false;
    int                                             PosI = 0;
    int                                             PosE = (int)Fs;
    int                                             HMSADD = 0;
    short[]                                         FBuffP = new short[(int)Fs];
    ///
    ///
//</editor-fold>
/// ////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="prepare">
    private void prepare(){
        set_Fs( 1500 );
        FBuffP = new short[(int)Fs];
        PosI = 0;
        PosE = (int)Fs;
        PanelF.setLayout( null );
        PanelF.setToolTipText(null);
        WidthP  = PanelF.getWidth();
        HeightP = PanelF.getHeight();
        PanelProp = this;
        PanelProp.setLocation( 0, 0 );
        PanelProp.setSize( WidthP, HeightP );
        PanelProp.setVisible(true);
        PanelProp.setLayout( null );
        ///
        JTextInfo   = new JLabel();
        JTextInfo.setSize( 150, 32 );
        JTextInfo.setLocation( 1, 1 );
        JTextInfo.setText( "<html>" + 
                            "X:0" + 
                            "<br>" + 
                            "Y:0" + 
                            "<html>" );
        PanelProp.add( JTextInfo );
        ///
        ///
        ///
        ///
        Reestablecer = new JButton();
        Reestablecer.setSize( 88, 26 );
        /// 
        /// 
        Reestablecer.setText( "Reestablecer" );
        Reestablecer.setMargin( new Insets(-20,-20,-20,-20) );
        Reestablecer.setToolTipText( "Reestablecer gráfica" );
        Reestablecer.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZoomP.setSelected( false );
                PanP.setSelected( false );
                PanelPlot.set_set_zoom( false );
                PanelPlot.set_set_pan( false );
                JSlTime.setValue( 0 );
                plot(FBuffP);
            }
        });
        ///
        PanP = new JToggleButton();
        PanP.setSize( 60, 26 );
        /// PanP.setLocation( PanelProp.getWidth()-PanP.getWidth()-Reestablecer.getWidth()-2, 1 );
        PanP.setMargin( new Insets(-11,-11,-10,-10) );
        /// ZoomP.setFont( new Font("TimesRoman", Font.PLAIN, 20) );
        PanP.setText( "\u2A01" );
        PanP.setText( "Pan" );
        PanP.setMargin( new Insets(0,0,0,0) );
        
        PanP.setToolTipText( "Pan" );
        PanP.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZoomP.setSelected( false );
                PanelPlot.set_set_zoom( false );
                PanelPlot.set_set_pan( PanP.isSelected() );
            }
        });
        ///
        ///
        ///
        ZoomP = new JToggleButton();
        ZoomP.setSize( 60, 26 );
        ZoomP.setMaximumSize( new Dimension( 60, 26) );
        /// ZoomP.setLocation( PanelProp.getWidth()-ZoomP.getWidth()-Reestablecer.getWidth()-PanP.getWidth()-4, 1 );
        ZoomP.setMargin( new Insets(-11,-11,-10,-10) );
        /// ZoomP.setFont( new Font("TimesRoman", Font.PLAIN, 20) );
        ZoomP.setText( "\u2A01" );
        ZoomP.setText( "Zoom" );
        ZoomP.setMargin( new Insets(0,0,0,0) );
        
        ZoomP.setToolTipText( "Zoom" );
        ZoomP.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanP.setSelected( false );
                PanelPlot.set_set_pan( false );
                PanelPlot.set_set_zoom( ZoomP.isSelected() );
            }
        });
        ///
        ///
        ///
        PanelProp.setVisible(true);
        PanelProp.setBackground(Color.LIGHT_GRAY);
        ///
        //
        PanelPlot = new Ploter();
        PanelPlot.setjlabel(JTextInfo);
        PanelPlot.setploteos(this);
        PanelPlot.setToolTipText(null);
        PanelPlot.setLayout( null );
        PanelProp.add(PanelPlot);
        PanelPlot.setVisible(true);
        ///
        PanelPlot.setLocation( x_p, y_p );
        
        int w_p = WidthP - x_p - L_X - 130;
        int h_p = HeightP - y_p - L_Y;
        int adv_WWW = w_p;
        while( (w_p% (h_p/20) )!=0 ){
            w_p--;
        }
        w_p++;
        adv_WWW = (adv_WWW - w_p)/2;
        PanelPlot.setSize( w_p, h_p );
        PanelPlot.prepare();
        PanelProp.repaint();
        PanelF.add(PanelProp);
        //
        ///
        PanelProp.setLayout( null );
        ///
        ///
        BufferedImage ii = null;
        try {
            ii = ImageIO.read(KardiaWindow.class.getResourceAsStream("Logo UPC.png"));
        } catch (IOException ex) {
            Logger.getLogger(ploteos.class.getName()).log(Level.SEVERE, null, ex);
        }
        LogoUPC = new JLabel( new ImageIcon(ii) );
        LogoUPC.setText( "" );
        
        
        int wlog = PanelProp.getWidth() - PanelPlot.getX() - PanelPlot.getWidth() - 2;
        LogoUPC.setSize( wlog, wlog );
        /// System.out.println( "wlog: " + wlog );
        
        LogoUPC.setLocation( PanelPlot.getWidth() + PanelPlot.getX() + 1, 
                                PanelPlot.getHeight() + PanelPlot.getY() - LogoUPC.getHeight() );
        LogoUPC.setVisible( true );
        PanelProp.add( LogoUPC );
        //PanelProp.setBackground(Color.red);
        //LogoUPC.setBackground(Color.blue);
        LogoUPC.setText( "" );
        ///
        ///
        PanelProp.setLayout( null );
        ///
        ///
        ///
        ///
        ///
        ZoomP.setLocation( 1, 1);
        PanP.setLocation( 1, 1);
        PanP.setVisible( false );
        ///Reestablecer.setLocation( 1, 1);
        PanelCtrls = new JPanel();
        PanelCtrls.setLayout( null );
        ///
        ///
        ///
        ///
        PanelCtrls.add(ZoomP);
        PanelCtrls.add(PanP);
        PanelCtrls.add(Reestablecer);
        ///
        int x_x_1 = PanelPlot.getWidth() + PanelPlot.getX();
        int w_w_1 = PanelF.getWidth() - PanelProp.getWidth();
        System.out.println( "" + w_w_1 );
        PanelCtrls.setLocation( x_x_1+2, PanelPlot.getY() );
        PanelCtrls.setSize( wlog-2, 200 );
        ///
        ///
        ///
        ///
        ///
        ///      O L D   P O S C 
        ZoomP.setLocation( (PanelCtrls.getWidth() - ZoomP.getWidth())/2 , 1 );
        PanP.setLocation( (PanelCtrls.getWidth() - PanP.getWidth())/2 , 
                                            ZoomP.getY() + ZoomP.getHeight() + PERMITIDO.SEPARA0);
        Reestablecer.setLocation( (PanelCtrls.getWidth() - Reestablecer.getWidth())/2 , 
                                                   PanP.getY() + PanP.getHeight() + PERMITIDO.SEPARA0 );
        ///
        PanelCtrls.setBackground( PanelProp.getBackground() );
        ///
        ///
        ///
        ///
        ///
        /// ZoomP.setLocation( (PanelCtrls.getWidth() - ZoomP.getWidth())/2 + adv_WWW , 1 );
        ZoomP.setLocation( (PanelCtrls.getWidth() - ZoomP.getWidth())/2, 1 );
        Reestablecer.setLocation( (PanelCtrls.getWidth() - Reestablecer.getWidth())/2 + adv_WWW , 
                                                   ZoomP.getY() + ZoomP.getHeight() + PERMITIDO.SEPARA0 );
        Reestablecer.setLocation( (PanelCtrls.getWidth() - Reestablecer.getWidth())/2 , 
                                                   ZoomP.getY() + ZoomP.getHeight() + PERMITIDO.SEPARA0 );
        ///
        PanelCtrls.setBackground( PanelProp.getBackground() );
        ///
        ///
        ///
        ///
        ///         ///
        PanelProp.add( PanelCtrls );
        ///
        ///
        ///
        ///
        ///
        ///
        ///
        ///
        JSlTime = new JScrollBar( HORIZONTAL );
        JSlTime.setVisible( true );
        JSlTime.setOrientation( HORIZONTAL );
        JSlTime.setMaximum( 3600 + 5 );
        JSlTime.setMaximum( (int)(FBuffP.length/(Fs)) + 5 );
        int xbd = 10;
        int ybd = 10;
        
        
        if( BARLOW ){
            JSlTime.setSize( 150, 40 );
            JSlTime.setSize( (int)(0.91*PanelPlot.getWidth()) , 40 );
            
            JSlTime.setBounds( xbd, ybd, JSlTime.getWidth() - xbd, JSlTime.getHeight() - ybd );  
            JSlTime.setLocation( PanelPlot.getX() + PanelPlot.getWidth()/2 - JSlTime.getWidth()/2, 
                                        2 );
            /// PanelProp.add( JSlTime );
            
            PanelCtrls.setLocation( x_x_1+2, PanelPlot.getY() + PanelProp.getHeight()/2 - PanelCtrls.getHeight() );
            
        }else{
            JSlTime.setSize( 150, 40 );
            JSlTime.setBounds( xbd, ybd, JSlTime.getWidth() - xbd, JSlTime.getHeight() - ybd );  
            /// adv_WWW = 0;
            JSlTime.setLocation( (PanelCtrls.getWidth() - JSlTime.getWidth())/2 + adv_WWW,
                                    Reestablecer.getY() + Reestablecer.getHeight() + 20 );
            /// PanelCtrls.add( JSlTime );
        }
        
        
        JSlTime.addAdjustmentListener( new AdjustmentListener(){ 
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Integer itt = e.getValue();
                int lop = PosE - PosI;
                int i_init = ((int) ( (itt) *Fs) );
                int i_NDat = ((int) ( (itt*Fs+lop)  ) );
                /*
                i_init = ((int) ( (itt) *Fs) );
                i_NDat = ((int) ( (itt*Fs+lop)  ) );
                //*/
                /// KARDIA.MainFrame.setTitle( itt.toString() );
                if( FBuffP.length>(i_NDat) ){
                    plot( FBuffP, i_init, i_NDat );
                    PanelPlot.repaint();
                    repaint();
                }
            }
        });
        JSlTime.setFocusable( true );
        JSlTime.setRequestFocusEnabled( true );
        JSlTime.setFocusTraversalKeysEnabled( true );
        ///
        ///
        ///
        JSignal = new JButton( "Señal" );
        JSignal.setSize( ZoomP.getWidth(), ZoomP.getHeight() );
        if( BARLOW ){
            JSignal.setLocation( (PanelProp.getWidth() - JSignal.getWidth())/2, JSlTime.getY() + JSlTime.getHeight() + 30 );
            ///PanelProp.add( JSignal );
        }else{
            JSignal.setLocation( (PanelCtrls.getWidth() - JSignal.getWidth())/2 + adv_WWW, JSlTime.getY() + JSlTime.getHeight() + 10 );
            ///PanelCtrls.add( JSignal );
        }
        ///
        JSignal.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d6 = new JDialog( jfrm, "", Dialog.ModalityType.DOCUMENT_MODAL );
                d6.setIconImage(jfrm.getIconImage());
                d6.setTitle( "Opciones de señal." );
                Signal_Panel sigpan = new Signal_Panel();
                sigpan.Prepare( PanelPlot, OwnPloteos );
                d6.getContentPane().add( sigpan, BorderLayout.CENTER );
                d6.setResizable( false );
                d6.pack();
                d6.setLocationRelativeTo( null );
                d6.setAlwaysOnTop(jfrm.isAlwaysOnTop());
                d6.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
                d6.setVisible(true);
                /// PanelPlot
                /// plotFBuffP();
            }
        });
        ///
        ///
        ///
        JSoporte = new JButton( "Señal" );
        JSoporte.setSize( 80, ZoomP.getHeight() );
        ///
        JSoporte.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d6 = new JDialog( jfrm, "Soporte KARDIA", Dialog.ModalityType.DOCUMENT_MODAL );
                d6.setIconImage(jfrm.getIconImage());
                /// d6.setTitle( "Soporte KARDIA" );
                
                
                BufferedImage ii = null;
                try {
                    ii = ImageIO.read(KardiaWindow.class.getResourceAsStream("Soporteh.png"));
                } catch (IOException ex) {
                    return;
                }
                
                JLabel jlb = new JLabel( new ImageIcon(ii) );
                
                d6.getContentPane().add( jlb, BorderLayout.CENTER );
                d6.setResizable( false );
                d6.pack();
                d6.setLocationRelativeTo( null );
                d6.setAlwaysOnTop(jfrm.isAlwaysOnTop());
                d6.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
                d6.setVisible(true);
            }
        });
        ///
        ///
        ///
        
        
        /// JSlTime.setVisible( false );
        JSignal.setLocation( (PanelCtrls.getWidth() - JSignal.getWidth())/2 + adv_WWW, 
                            Reestablecer.getY() + Reestablecer.getHeight() + PERMITIDO.SEPARA0 );
        JSignal.setLocation( (PanelCtrls.getWidth() - JSignal.getWidth())/2 - 2, 
                            Reestablecer.getY() + Reestablecer.getHeight() + PERMITIDO.SEPARA0 );
        PanelCtrls.add( JSignal );
        ///
        ///
        ///
        JSoporte.setText( "Soporte" );
        JSoporte.setSize( JSignal.getWidth() + 20, JSignal.getHeight() );
        JSoporte.setLocation( (PanelCtrls.getWidth() - JSoporte.getWidth())/2 - 2, 
                            JSignal.getY() + JSignal.getHeight() + PERMITIDO.SEPARA0 );
        PanelCtrls.add( JSoporte );
        ///
        ///
        ///
        System.out.println( "getWidth" + PanelCtrls.getWidth() );
        JSlTime.setSize( 170, 40 );
        JSlTime.setLocation( (PanelCtrls.getWidth() - JSlTime.getWidth())/2 + adv_WWW, 
                                        JSoporte.getY() + JSoporte.getHeight() + PERMITIDO.SEPARA0 );
        JSlTime.setLocation( (PanelCtrls.getWidth() - JSlTime.getWidth())/2, 
                                        JSoporte.getY() + JSoporte.getHeight() + PERMITIDO.SEPARA0 );
        PanelCtrls.add( JSlTime );
        // PanelCtrls.setBackground(Color.red);
        PanelCtrls.setSize( wlog-2, 
                4*JSoporte.getHeight() + JSlTime.getHeight() + 2*ZoomP.getY() + 4*PERMITIDO.SEPARA0
        );
        PanelCtrls.setLocation( PanelCtrls.getX(), PanelCtrls.getY() + 60 );
        PanelCtrls.repaint();
        ///
        ///
        ///
        ///
        JSignal.setVisible( Visible_Signal );
        ///
        ///
        ///
        /// JSlTime.setVisible( false );
        ///
        ///
    }
//</editor-fold>
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc=" plotFBuffP ">
    public void plotFBuffP(){ plot(FBuffP); }
    public void SetJFrm( JFrame jfr_I ){ jfrm = jfr_I; }
    public void ShowSignal(){
        JSignal.setVisible( true );
    }
    
    
//</editor-fold>
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="PLOT">
    
    public void setplot( int I_B, int E_B ){
        plot( FBuffP, I_B, E_B );
    }
    public void plot( short[] FBuff ){
        FBuffP = FBuff;
        if( FBuff==null ){
            FBuffP = new short[4];
            FBuffP[0] = 100;
            FBuffP[1] = 256;
            FBuffP[2] = 768;
            FBuffP[3] = 1023;
        }
        
        for( int i=0; i<FBuffP.length; i++ ){
            if( FBuffP[i]>1023 ){
                FBuffP[i] = 1023;
            }
            if( FBuffP[i]<0 ){
                FBuffP[i] = 0;
            }
        }
        int iin = (int)(FBuffP.length/(Fs));
        if( iin<5 ){
            iin = 5;
        }
        JSlTime.setMinimum( 0 );
        JSlTime.setMaximum( iin );
        /// JSlTime.setMaximum( FBuffP.length );
        /// System.out.println( "getMaximum0: " + iin );
        /// System.out.println( "getMaximum1: " + JSlTime.getMaximum() );
        
        PosI = 0;
        PosE = ((int) (5*Fs) );
        PanelPlot.plot( FBuffP, ((int) (5*Fs) ), 0 );
        PanelPlot.repaint();
        repaint();
    }
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
    public void SetPlotCallExt(){
        plot( FBuffP, PosI, PosE );
    }
    
    
    public void plot( short[] FBuff, int PosI_F, int PosE_F ){
        PosI = PosI_F;
        PosE = PosE_F;
        FBuffP = FBuff;
        PanelPlot.plot( FBuff, PosE - PosI, PosI );
        PanelPlot.repaint();
        repaint();
    }
    /// 
    public ploteos( JPanel PanelPat ){
        PanelF = PanelPat;
        prepare();
    }
//</editor-fold>
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="GetPosI">
    public int GetPosI(){
        return PosI;
    }
    public int GetPosE(){
        return PosE;
    }
//</editor-fold>
/// ////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCION paintComponent">
    //*
    @Override
    public void paintComponent( Graphics Graph ){
        LogoUPC.setText( "" );
        super.paintComponent(Graph);
        if( true!=false ){
            ///return;
        }
        Graph.setFont(FntPl);
        //GBIm = BImage.getGraphics();
        //GBIm.setColor(Color.red);
        //Graph.drawString( "10", x_p, HeightP - y_p - L_Y + 34 );
        ///
        double dtmp = (PosI/Fs) + HMSADD;
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        StrPI = String.format( "%s", formatter.format(dtmp) );
        ///
        FontMetrics metrics = Graph.getFontMetrics( FntPl );
        Rectangle rect = new Rectangle( w_tx, h_tx );
        int x = ( x_p - metrics.stringWidth(StrPI)/2 );
        int y_i = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        int y_f = HeightP - y_p + 4;
        int y = y_f + y_i;
        Graph.drawString( Funcs_Set.NumSeg2Hour( dtmp ), x, y );
        /// Graph.drawRect(x, y, w_tx, h_tx);
        ///
        dtmp = ((PosE)/Fs) + HMSADD;
        StrPI = String.format( "%s", formatter.format(dtmp) );
        x = ( PanelPlot.getWidth() + x_p - metrics.stringWidth(StrPI)/2 ) - 10;
        Graph.drawString( Funcs_Set.NumSeg2Hour( dtmp ), x, y );
        ///
    }
    //*/
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="set_Fs">
    static public void set_Fs( double FsI ){
        if( !Fs_Changed ){
            Fs_Changed = true;
            Fs = FsI;
            Ploter.set_Fs( Fs );
        }
    }
    ///
    ///
    ///
    public void Set_HMSADD( int HMSADD_I ){
        HMSADD = HMSADD_I;
        PanelPlot.Set_HMSADD( HMSADD );
    }
    ///
    ///
    ///
//</editor-fold>
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="ResetPlotOpt">
    public void ResetPlotOpt(){
        ZoomP.setSelected( false );
        PanP.setSelected( false );
        PanelPlot.set_set_zoom( false );
        PanelPlot.set_set_pan( false );
    }
//</editor-fold>
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////    
}
