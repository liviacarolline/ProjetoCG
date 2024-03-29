package panels;

import javax.swing.JRadioButton;

import auxiliares.TransformacoesImagemEnum;

/**
 * Representa o menu para manipula��o da circunfer�ncia
 *
 */
public class PanelMenuTransformacoes extends javax.swing.JPanel {

    private static PanelMenuTransformacoes instance;
    private TransformacoesImagemEnum tipoAlgoritimo;
    private float dados;

    public static synchronized PanelMenuTransformacoes getInstance() {
        if (instance == null) {
            instance = new PanelMenuTransformacoes();
        }
        return instance;
    }
    
    /**
     * Construtor
     */
    private PanelMenuTransformacoes() {
        initComponents();
        panelConfigGamma.setVisible(false);
    }

    public float getDados() {
        return (float) tfGammaY.getValue();
    }

    public TransformacoesImagemEnum getTipoAlgoritimo() {
        if (rbNegativo.isSelected()) {
            setTipoAlgoritimo(TransformacoesImagemEnum.NEGATIVO);
        } else if (rbGamma.isSelected()) {
            setTipoAlgoritimo(TransformacoesImagemEnum.GAMMA);
        } else if (rbLog.isSelected()) {
            setTipoAlgoritimo(TransformacoesImagemEnum.LOG);
        }
        return tipoAlgoritimo;
    }

    public void setTipoAlgoritimo(TransformacoesImagemEnum tipoAlgoritimo) {
        this.tipoAlgoritimo = tipoAlgoritimo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupFiltros = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        rbNegativo = new javax.swing.JRadioButton();
        rbGamma = new javax.swing.JRadioButton();
        rbLog = new javax.swing.JRadioButton();
        panelConfigGamma = new javax.swing.JPanel();
        lbInfor = new javax.swing.JLabel();
        tfGammaY = new javax.swing.JSpinner();

        setMaximumSize(new java.awt.Dimension(240, 32767));
        setMinimumSize(new java.awt.Dimension(240, 0));
        setPreferredSize(new java.awt.Dimension(240, 779));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        buttonGroupFiltros.add(rbNegativo);
        rbNegativo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        rbNegativo.setSelected(true);
        rbNegativo.setText("Negativo");
        rbNegativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSelect(evt);
            }
        });

        buttonGroupFiltros.add(rbGamma);
        rbGamma.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        rbGamma.setText("Gamma");
        rbGamma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSelect(evt);
            }
        });

        buttonGroupFiltros.add(rbLog);
        rbLog.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        rbLog.setText("Logaritmo");
        rbLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSelect(evt);
            }
        });

        panelConfigGamma.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para operação"));

        lbInfor.setText("c = 1 e y = (0 <= y <= 1)");

        tfGammaY.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));
        tfGammaY.setMinimumSize(new java.awt.Dimension(29, 30));

        javax.swing.GroupLayout panelConfigGammaLayout = new javax.swing.GroupLayout(panelConfigGamma);
        panelConfigGamma.setLayout(panelConfigGammaLayout);
        panelConfigGammaLayout.setHorizontalGroup(
            panelConfigGammaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGammaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfigGammaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigGammaLayout.createSequentialGroup()
                        .addComponent(lbInfor)
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addComponent(tfGammaY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelConfigGammaLayout.setVerticalGroup(
            panelConfigGammaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigGammaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbInfor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfGammaY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelConfigGamma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbNegativo)
                            .addComponent(rbGamma)
                            .addComponent(rbLog))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbNegativo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbGamma)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(panelConfigGamma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(481, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rbSelect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSelect
        JRadioButton radio = (JRadioButton) evt.getSource();
        PanelTranformacoes.getInstance().setTitle("Transformação " + radio.getText());
        PanelTranformacoes.panelImgOutput.removeAll();
        PanelTranformacoes.panelImgOutput.repaint();
        
        if (rbNegativo.isSelected()) {
            panelConfigGamma.setVisible(false);
        } else {
            panelConfigGamma.setVisible(true);
            if (rbGamma.isSelected()) {
                lbInfor.setText("c = 1 e y = (0 <= y <= 1)");
            } else {
                lbInfor.setText("Constante \"a\"");
            }
        }
    }//GEN-LAST:event_rbSelect


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupFiltros;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbInfor;
    private javax.swing.JPanel panelConfigGamma;
    private javax.swing.JRadioButton rbGamma;
    private javax.swing.JRadioButton rbLog;
    private javax.swing.JRadioButton rbNegativo;
    private javax.swing.JSpinner tfGammaY;
    // End of variables declaration//GEN-END:variables
}
