/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

/**
 *
 * @author jorge pc
 */
public class PnlTotales extends javax.swing.JPanel {

    /**
     * Creates new form pnlTotales
     */
    public PnlTotales() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jsc1 = new javax.swing.JScrollPane();
        txtJson = new javax.swing.JTextArea();
        jsoResutal = new javax.swing.JScrollPane();
        TxtResulta = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel1.setText("Total");
        jPanel3.add(jLabel1);

        total.setColumns(10);
        jPanel3.add(total);

        add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(new java.awt.GridLayout());

        jsc1.setBorder(javax.swing.BorderFactory.createTitledBorder("Json"));

        txtJson.setColumns(20);
        txtJson.setLineWrap(true);
        txtJson.setRows(5);
        txtJson.setWrapStyleWord(true);
        jsc1.setViewportView(txtJson);

        jPanel2.add(jsc1);

        jsoResutal.setBorder(javax.swing.BorderFactory.createTitledBorder("Resuesta"));

        TxtResulta.setColumns(20);
        TxtResulta.setLineWrap(true);
        TxtResulta.setRows(5);
        jsoResutal.setViewportView(TxtResulta);

        jPanel2.add(jsoResutal);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextArea TxtResulta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JScrollPane jsc1;
    public javax.swing.JScrollPane jsoResutal;
    public javax.swing.JTextField total;
    public javax.swing.JTextArea txtJson;
    // End of variables declaration//GEN-END:variables
}
