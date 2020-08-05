package kardia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.SwingConstants;

public class Signal_Panel extends javax.swing.JPanel {
    Ploter              pltr = null;
    ploteos             plt_ins = null;
    public Signal_Panel() {
        initComponents();
    }
    ///
    public void Prepare( Ploter p_l, ploteos plt_ins  ){
        pltr = p_l;
        Offset.setModel( new DefaultComboBoxModel(pltr.GetOffsetModel()) );
        Offset.setSelectedIndex( pltr.GetOffsetInd() );
        ///
        ///
        Ampl.setModel( new DefaultComboBoxModel(pltr.GetAmplModel()) );
        Ampl.setSelectedIndex( pltr.GetAmplInd() );
        ///
        Invert.setSelected( pltr.GetInvSig() );
        Invert.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pltr.SetInv_Val( Invert.isSelected() );
                plt_ins.SetPlotCallExt();
                ///pltr.plotFBuffP();
            }
        });
        ///
        Ampl.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                if(arg0.getStateChange() == ItemEvent.SELECTED){
                    pltr.SetAmpl_Val( Ampl.getSelectedIndex() );
                    plt_ins.SetPlotCallExt();
                    ///pltr.plotFBuffP();
                }
            }
        });
        ///
        Offset.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                if(arg0.getStateChange() == ItemEvent.SELECTED){
                    pltr.SetOffset_Val( Offset.getSelectedIndex() );
                    ///pltr.plotFBuffP();
                    plt_ins.SetPlotCallExt();
                }
            }
        });
        
    }
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Ampl = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        Offset = new javax.swing.JComboBox();
        Invert = new javax.swing.JToggleButton();

        jLabel1 = new javax.swing.JLabel("The Label", SwingConstants.CENTER);
        jLabel1.setText("Amplificar");

        Ampl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2 = new javax.swing.JLabel("The Label", SwingConstants.CENTER);
        jLabel2.setText(" Offset ");

        Offset.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Invert.setText("Invertir señal");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Ampl, 0, 70, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Offset, 0, 70, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addComponent(Invert)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Ampl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Offset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(Invert)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Ampl;
    private javax.swing.JToggleButton Invert;
    private javax.swing.JComboBox Offset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
