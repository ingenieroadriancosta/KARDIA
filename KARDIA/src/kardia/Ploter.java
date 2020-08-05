package kardia;
//<editor-fold defaultstate="collapsed" desc="imports">
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import static kardia.PERMITIDO.Fs;
//</editor-fold>

public class Ploter extends JPanel implements MouseListener, MouseMotionListener { // MouseMotionListener
//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    int                                             Width      = 1;
    int                                             Height     = 1;
    BufferedImage                                   BImage      = null;
    Graphics                                        GBIm        = null;
    int                                             BufferGrid[]  = null;
    int                                             BufferIm[]  = null;
    int                                             CFondo = -1;
    int                                             CGrid = 0;
    int                                             CLine = CGrid;
    short[]                                         FBuffP = new short[(int)Fs];
    int                                             B_I = 0;
    int                                             B_E = FBuffP.length;
    JLabel                                          JTextInfo   = null;
    JLabel                                          Zoom_pat   = null;
    ploteos                                         plt_Ins = null;
    double                                          X_form = Fs;
    double                                          Y_form = 1;
    double                                          Ampl_Val = 1;
    double                                          Inv_Val = 1;
    double                                          Offset_Val = 512;
    int                                             HMSADD = 0;
    
    
    String                                          AmplModel[] = null;
    int                                             AmplInd = 0;
    String                                          OffsetModel[] = null;
    int                                             OffsetInd = 0;
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCIONES VARIAS">
////////////////////////////////////////////////////////////////////////////////
    public static int RGB( int r, int g, int b ){
        int Res = ( (b) | ((g)<<8) | ( (r) << 16) | ( (255) << 24) );
        return Res;
    }
    
    
    
    public String[] GetAmplModel(){ return AmplModel; }
    public String[] GetOffsetModel(){ return OffsetModel; }
    public int GetAmplInd(){ return AmplInd; }
    public int GetOffsetInd(){ return OffsetInd; }
    
    public boolean GetInvSig(){ return (Inv_Val<1); }
    
    public void plotFBuffP(){
        plt_Ins.plotFBuffP();
    }
    
    
    
    public void SetAmpl_Val( int AmplI ){
        AmplInd = AmplI;
        Ampl_Val = Double.parseDouble( AmplModel[AmplInd] );
        
    }
    
    public void SetInv_Val( boolean Ampl_Val_I ){
        if( Ampl_Val_I ){
            Inv_Val = -1;
        }else{
            Inv_Val = 1;
        }
    }
    
    public void SetOffset_Val( int Off_I ){
        OffsetInd = Off_I;
        Offset_Val = 512 - 512 * Double.parseDouble( OffsetModel[OffsetInd] );
    }
    
    
    public void SetPlotCallExt(){
        plt_Ins.setplot( B_I, B_E );
    }
    
////////////////////////////////////////////////////////////////////////////////
    public void SetLineColor( int R, int G, int B ){ CLine = RGB( R, G, B ); }
    public void SetGridColor( int R, int G, int B ){ CGrid = RGB( R, G, B ); }
    public void SetBkColor( int R, int G, int B ){ CFondo = RGB( R, G, B ); }
///
//<editor-fold defaultstate="collapsed" desc="set_Fs">
    public static void set_Fs( double FsI ){
        Fs = FsI;
    }
    public void setjlabel( JLabel JTextInfoI ){
        JTextInfo = JTextInfoI;
    }
    public void setploteos( ploteos plt_InsI ){
        plt_Ins = plt_InsI;
    }
//</editor-fold>
///
//<editor-fold defaultstate="collapsed" desc="Set_HMSADD">
    public void Set_HMSADD( int HMSADD_I ){
        HMSADD = HMSADD_I;
    }
//</editor-fold>
    ///
    ///
    ///
    ///
//<editor-fold defaultstate="collapsed" desc="SetCursorInfo">
    
    void SetCursorInfo( int x, int y ){
        if( JTextInfo!=null ){
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            double X_N = (x)/((double)(getWidth()-1));
            if( X_N>1 ){
                X_N = 1;
            }
            if( X_N<0 ){
                X_N = 0;
            }
            X_N = (B_E*X_N + B_I)/1500 + HMSADD;
            ///
            ///
            ///
            double Y_N = Y_form * ( 1 - 2 * y/((double)(getHeight()-1)) );
            if( Y_N>Y_form ){
                Y_N = Y_form;
            }
            if( Y_N<(-Y_form) ){
                Y_N = -Y_form;
            }
            ///
            ///
            Y_N = Y_N * PERMITIDO.VOLTS;
            ///
            String StrX = String.format( "%s", formatter.format(X_N) );
            String StrY = String.format( "%s", formatter.format(Y_N) );
            ///
            StrX = Funcs_Set.NumSeg2Hour(X_N );
            ///
            JTextInfo.setText(  "<html>" +
                    "X:" + StrX + " "+
                    "<br>" +
                    "Y:" + StrY +
                    "<html>" );
            
        }
    }
//</editor-fold>
    ///
    ///
    ///
    ///
    @Override
    public void mouseMoved(MouseEvent m_e){
        SetCursorInfo( m_e.getX(), m_e.getY() );
        /*
        if( set_pan ){
            ///setCursor( new Cursor(Cursor.HAND_CURSOR) );
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage("OH.png");
            Cursor c = toolkit.createCustomCursor(image , new Point(64,64), "img");
            setCursor (c);
        }else{
            setCursor( new Cursor(Cursor.DEFAULT_CURSOR) );
        }
        //*/
    }
    ///
    ///
    ///
    ///
    @Override
    public void mouseDragged(MouseEvent m_e){
        SetCursorInfo( m_e.getX(), m_e.getY() );
        if( inzooming && set_zoom && !set_pan ){
            xp_e = m_e.getX();
            xp_i_R = (int)Math.min(xp_i, xp_e);
            xp_e_R = (int)Math.max(xp_i, xp_e);
            ///
            if( xp_i_R<0 ){
                xp_i_R = 0;
            }
            if( xp_e_R>getWidth() ){
                xp_e_R = getWidth();
            }
            ///
            Zoom_pat.setSize( (xp_e_R-xp_i_R), 4 );
            Zoom_pat.setLocation( xp_i_R, getHeight()/2 - Zoom_pat.getHeight()/2 );
        
        }
        ///
        if( inzooming && set_pan && !set_zoom ){
            xp_e = m_e.getX();
            if( xp_i!=xp_e && plt_Ins!=null ){
                xp_i_R = xp_i;
                xp_e_R = xp_e;
                if( xp_i_R<0 ){
                    xp_i_R = 0;
                }
                if( xp_e_R>=getWidth() ){
                    xp_e_R = getWidth()-1;
                }
                B_I = (int)( I_PAN - W_PAN/1.0 * (xp_e_R - xp_i_R)/((double)getWidth()) );
                if( B_I<0 ){
                    B_I = 0;
                }
                if( B_I>=(FBuffP.length-W_PAN) ){
                    B_I = (FBuffP.length-W_PAN)-1;
                }
                ///
                ///
                B_E = B_I+W_PAN;
                ///
                ///
                if( B_E>=FBuffP.length ){
                    B_I = FBuffP.length - W_PAN;
                    B_E = FBuffP.length;
                }
                ///
                ///
                ///
                ///System.out.println( "(" + W_PAN + ", " +  B_I + ")" );
                ///
                /// System.out.println( "(" + B_I + ", " + B_E + ")" );
                ///System.out.println( "" + (xp_e_R - xp_i_R) );
                plt_Ins.setplot( B_I, B_E );
            }
        }
        ///
    }
    ///
    ///
    ///
    ///
    int xp_i = 0;
    int xp_e = 0;
    ///
    ///
    int xp_i_R = 0;
    int xp_e_R = 0;
    ///
    ///
    int W_PAN = 0;
    int I_PAN = 0;
    ///
    ///
    boolean set_zoom = false;
    boolean set_pan = false;
    public void set_set_zoom( boolean set_zoomI ){ set_zoom = set_zoomI; }
    public void set_set_pan( boolean set_panI ){ set_pan = set_panI; }
    boolean inzooming = false;
    ///
    @Override
    public void mouseReleased(MouseEvent m_e){
        if( xp_i!=xp_e && plt_Ins!=null && set_zoom ){
            xp_e = m_e.getX();
            xp_i_R = (int)Math.min(xp_i, xp_e);
            xp_e_R = (int)Math.max(xp_i, xp_e);
            ///
            if( xp_i_R<0 ){
                xp_i_R = 0;
            }
            if( xp_e_R>=getWidth() ){
                xp_e_R = getWidth()-1;
            }
            ///
            ///System.out.println( W_PAN + "(" + B_I + "," + (W_PAN*(xp_i_R/((double)getWidth()))) + ")");
            ///
            B_I = (int)( B_I + W_PAN * (xp_i_R/((double)getWidth())) );
            int B_E_P = B_I + (int)( W_PAN * ((xp_e_R-xp_i_R)/((double)getWidth())) );
            ///
            /// System.out.println( W_PAN + "(" + B_I + "," + B_E_P + ")");
            ///
            if( B_I>=B_E_P ){
                B_E_P = B_I + 2;
            }
            plt_Ins.setplot( B_I, B_E_P );
        }
        if( set_pan && !set_zoom ){
            I_PAN = B_I;
            W_PAN = B_E;
        }
        inzooming = false;
        Zoom_pat.setVisible(false);
    }
    ///
    @Override
    public void mousePressed(MouseEvent m_e){
        inzooming = true;
        xp_i = m_e.getX();
        xp_e = xp_i;
        ///
        if( set_zoom ){
            Zoom_pat.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
            ///
            Zoom_pat.setSize( 1, 4 );
            Zoom_pat.setLocation( 10, getHeight()/2 - Zoom_pat.getHeight()/2 );
            ///
            ///
            Zoom_pat.setVisible(true);
        }
        ///
    }
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    @Override
    public void mouseClicked(MouseEvent m_e){}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    ///
    ///
    ///
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCION prepare">
    public void prepare(){
        FBuffP = new short[(int)Fs];
        B_E = FBuffP.length;
        X_form = Fs;
        setLayout( null );
        ///
        addMouseMotionListener(this);
        addMouseListener(this);
        ///
        Zoom_pat = new JLabel();
        Zoom_pat.setSize( 200, 20 );
        add( Zoom_pat );
        ///
        Width   = getWidth();
        Height  = getHeight();
        ///
        BufferIm    = new int[Width*Height];
        BufferGrid  = new int[Width*Height];
        for( int i=0; i<BufferIm.length; i++ ){
            BufferGrid[i]   = 0;
            BufferIm[i]     = 0;
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        BImage = gc.createCompatibleImage( Width, Height );
        GBIm = BImage.getGraphics();
        
        float frgb[] = Color.RGBtoHSB( 255, 255, 238, null );
        Color CB = Color.getHSBColor( frgb[0], frgb[1], frgb[2] );
        
        Border brd = BorderFactory.createLineBorder(CB);
        setBorder( brd );
        ///
        SetLineColor( PERMITIDO.R_L, PERMITIDO.G_L, PERMITIDO.B_L );
        SetGridColor( PERMITIDO.R_G, PERMITIDO.G_G, PERMITIDO.B_G );
        ///
        
        switch( PERMITIDO.TYPE_Plot ){
            case 0:
                ClearBuff();
            case 1:
                ClearBuffmilli();
        }
        
        
        
        
    //String                                          AmplModel[] = null;
    //String                                          OffsetModel[] = null;
        
        int istop = 0;
        int nt = 0;
        for( int id=-10; id<=10; id = id + 1 ){
            nt++;
        }
        OffsetModel = new String[nt];
        int ie = 0;
        for( int id=-10; id<=10; id = id + 1 ){
            OffsetModel[ie] = "" + id/10.0;
            ie++;
            if( id==0 ){
                istop = ie - 1;
            }
        }
        OffsetInd = istop;
        
        
        
        
        nt = 0;
        for( int id=-10; id<=10; id = id + 1 ){
            nt++;
        }
        AmplModel = new String[nt-1];
        ie = 0;
        for( int id=1; id<=20; id = id + 1 ){
            AmplModel[ie] = "" + id/10.0;
            ie++;
            if( id==10 ){
                istop = ie - 1;
            }
        }
        AmplInd = istop;
        
        
        
        
        
        FillGrid();
        BImage.setRGB( 0, 0, Width, Height, BufferIm, 0, Width );
        ///
        plot(FBuffP);
        ///
        repaint();
    }
    
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCION paintComponent">
    @Override
    public void paintComponent( Graphics Graph ){
        /// super.paintComponent( Graph );
        //GBIm = BImage.getGraphics();
        Graph.drawImage( BImage, 0, 0, this );
        //GBIm.setColor(Color.red);
        //GBIm.drawString( "NTimes, " + NTimes , 30, 30 );
        //Graph.drawImage( BImage, 0, 0, this);
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCION ClearBuff">
    public void ClearBuff(){
        try{
            int IndexP;
            
            for( int y=0; y<Height; y++ ){
                for( int x=0; x<Width; x++ ){
                    IndexP = x + Width*y;
                    BufferGrid[IndexP] = 0;
                }
            }
            int IndDivY = 0;
            int Advance = 1;
            int W_Grid = Width;
            int H_Grid = Height;
            for (int y=0; y<Height; y = y + Advance) {
                int IndDivX = 0;
                for (int x=0; x<Width; x = x + Advance) {
                    int FromIndex = x + Width*y;
                    BufferGrid[FromIndex] = CFondo;
                    
                    
                    if ((IndDivY*H_Grid)/10==y){
                        BufferGrid[FromIndex] = CGrid;
                    }
                    if ((IndDivX*W_Grid)/10==x ){
                        BufferGrid[FromIndex] = CGrid;
                        IndDivX++;
                    }
                    if (x==W_Grid-1 || y==H_Grid-1 ){
                        BufferGrid[FromIndex] = CGrid;
                    }
                    
                }
                if ((IndDivY*H_Grid)/10==y ){
                    IndDivY++;
                }
            }
            
            int AdvGrid = 0;
            int Grid_Style = 2;
            
            for( int y=W_Grid/100; y<H_Grid-W_Grid/100; y = y + 1 ){
                for( int x=W_Grid/100; x<W_Grid-W_Grid/100; x = x + 1 ){
                    int FromIndex = x + W_Grid*y;
                    if( BufferGrid[FromIndex]==CGrid ){
                        AdvGrid++;
                        if( AdvGrid==Grid_Style ){
                            BufferGrid[FromIndex] = CFondo;
                            AdvGrid = 0;
                        }
                    }
                }
            }
            
            
            for( int x=0; x<Width; x++ ){
                IndexP = x + (Height-1) * Width;
                BufferGrid[x] = CGrid;
                BufferGrid[IndexP] = CGrid;
            }
            
            for( int y=0; y<Height; y++ ){
                IndexP = Width*y;
                BufferGrid[IndexP] = CGrid;
                BufferGrid[IndexP+Width-1] = CGrid;
            }
            
            
        }catch( ArrayIndexOutOfBoundsException ArEx ){
            System.out.println(ArEx);
        }
        repaint();
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCION ClearBuff">
    public void ClearBuffmilli(){
        try{
            int IndexP;
            ///
            for( int i=0; i<BufferGrid.length; i++ ){
                BufferGrid[i] = CFondo;
            }
            ///
            int IndDivY = 0;
            int Advance = 1;
            int W_Grid = Width;
            int H_Grid = Height;
            int P_Y = (Height)/20;
            for ( int y=0; y<Height; y = y + Advance ){
                int IndDivX = 0;
                for (int x=0; x<Width; x = x + Advance) {
                    int FromIndex = x + Width*y;
                    BufferGrid[FromIndex] = CFondo;
                    if ((IndDivY*H_Grid)/20==y){
                        BufferGrid[FromIndex] = CGrid;
                    }
                    if ( (x%P_Y)==0 ){
                        BufferGrid[FromIndex] = CGrid;
                        IndDivX++;
                    }
                    if (x==W_Grid-1 || y==H_Grid-1 ){
                        BufferGrid[FromIndex] = CGrid;
                    }
                    
                }
                if ((IndDivY*H_Grid)/20==y ){
                    IndDivY++;
                }
            }
            
            
            for (int x=0; x<Width; x = x + Advance) {
                IndDivY = x + (Width*Height)/2;
                BufferGrid[IndDivY] = 0;
            }
            ///
            int AdvGrid = 0;
            int Grid_Style = 2;
            ///
            for( int y=W_Grid/100; y<H_Grid-W_Grid/100; y = y + 1 ){
                for( int x=W_Grid/100; x<W_Grid-W_Grid/100; x = x + 1 ){
                    int FromIndex = x + W_Grid*y;
                    if( BufferGrid[FromIndex]==CGrid ){
                        AdvGrid++;
                        if( AdvGrid==Grid_Style ){
                            //BufferGrid[FromIndex] = CFondo;
                            AdvGrid = 0;
                        }
                    }
                }
            }
            
            
            for( int x=0; x<Width; x++ ){
                IndexP = x + (Height-1) * Width;
                BufferGrid[x] = CGrid;
                BufferGrid[IndexP] = CGrid;
            }
            
            for( int y=0; y<Height; y++ ){
                IndexP = Width*y;
                BufferGrid[IndexP] = CGrid;
                BufferGrid[IndexP+Width-1] = CGrid;
            }
            
            
        }catch( ArrayIndexOutOfBoundsException ArEx ){
            System.out.println(ArEx);
        }
        repaint();
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCION FillGrid">
    void FillGrid(){
        for( int i=0; i<BufferGrid.length; i++ ){
            BufferIm[i] = BufferGrid[i];
        }
        /// BImage.setRGB( 0, 0, Width, Height, BufferIm, 0, Width );
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCION plot">
    public int[] plot( short[] FBuff ){
        if( FBuff==null ){
            return plot( FBuff, 0, 0 );
        }
        return plot( FBuff, FBuff.length, 0 );
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
//<editor-fold defaultstate="collapsed" desc="FUNCION plot">
    public int[] plot( short[] FBuff, int NDatas, int Begin ){
        if( FBuff==null || NDatas<1 ){ return BufferGrid; }
        if( NDatas>FBuff.length ){
            ///System.exit( -3 );
            NDatas = FBuff.length;
        }
        
        if( NDatas<2 ){
            NDatas = 2;
        }
        
        if( Begin<0 ){
            Begin = 0;
        }
        
        if( NDatas<2 ){
            NDatas = 2;
        }
        
        if( NDatas>=FBuff.length ){
            NDatas = FBuff.length;
        }
        if( (NDatas+Begin)>=FBuff.length ){
            Begin = 0;
            NDatas = FBuff.length;
        }
        
        
        B_I = Begin;
        B_E = NDatas;
        
        
        
        if( !set_pan ){
            W_PAN = NDatas;
            I_PAN = B_I;
        }
        FillGrid();
        FBuffP = FBuff;
        int W_Grid = Width - 1;
        int H_Grid = Height - 1;
        int x;
        int y;
        int Nx;
        int Ny;
        int YW;
        final int GNPosC = NDatas - 1;
        double NewF;
        double ValT;
        
        
        final double ValProp = Ampl_Val * Inv_Val;
        
        /// Ampl_Val = 1;
        /// Inv_Val = 1;
        /// Offset_Val = 1;
    
        for( int Index=0; Index<NDatas; Index++ ){
            // if( Index>=NDatas ){ break; }
            ValT = ValProp * (FBuff[Index+Begin]-Offset_Val);
            if( ValT>511 ){
                ValT = 511;
            }
            if( ValT<-511 ){
                ValT = -511;
            }
            NewF = 1 - ( (ValT)/512.0 + 1 )/2;
            if( NewF>1 || NewF<0 ){
                ///continue;
            }
            x = (int)( ((long)Index * (long)W_Grid)/GNPosC );
            y = ( (int)(H_Grid * NewF) );
            YW = y * Width;
            BufferIm[ x + YW ] = CLine;
            if( Index==GNPosC ){
                continue;
            }
            ValT = ValProp * (FBuff[Index+1+Begin]-Offset_Val);
            if( ValT>511 ){
                ValT = 511;
            }
            if( ValT<-511 ){
                ValT = -511;
            }
            
            NewF = 1 - ( (ValT)/512.0 +1 )/2;
            Nx = (int)( ( (long)(Index+1) * (long)W_Grid )/GNPosC );
            Ny = (int)(H_Grid*NewF );
            while( x!=Nx && x<W_Grid ){
                x++;
                BufferIm[ x + YW ] = CLine;
            }
            //
            while( y>Ny ){
                y--;
                if( y<0 ){
                    continue;
                }
                BufferIm[ x + y * Width ] = CLine;
            }
            //
            while( y!=Ny && y<H_Grid ){
                y++;
                if( y>=H_Grid ){
                    continue;
                }
                BufferIm[ x + y * Width ] = CLine;
            }
            //
        }
        BImage.setRGB( 0, 0, Width, Height, BufferIm, 0, Width );
        return BufferIm;
    }
//</editor-fold>
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
}
