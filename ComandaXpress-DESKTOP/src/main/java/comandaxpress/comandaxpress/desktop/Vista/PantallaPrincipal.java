package comandaxpress.comandaxpress.desktop.Vista;

import comandaxpress.comandaxpress.desktop.Controlador.ControladorPrincipal;
import lombok.Data;

@Data
public class PantallaPrincipal extends javax.swing.JFrame {
    public PantallaPrincipal() {
        initComponents();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Titulo = new javax.swing.JLabel();
        btnGestionUsuarios = new javax.swing.JButton();
        btnGestionTickets = new javax.swing.JButton();
        btnGestionProductos = new javax.swing.JButton();
        btnGestionarMesas = new javax.swing.JButton();
        btnGestionarTicketDetalles = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ComandaXpress Desktop Application");

        Titulo.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        Titulo.setForeground(new java.awt.Color(118, 171, 167));
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo.setText("COMANDAXPRESS");

        btnGestionUsuarios.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionUsuarios.setText("Usuarios");
        btnGestionUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGestionUsuariosMouseClicked(evt);
            }
        });

        btnGestionTickets.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionTickets.setText("Tickets");
        btnGestionTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionTicketsActionPerformed(evt);
            }
        });

        btnGestionProductos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionProductos.setText("Productos");
        btnGestionProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionProductosActionPerformed(evt);
            }
        });

        btnGestionarMesas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionarMesas.setText("Mesas");
        btnGestionarMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarMesasActionPerformed(evt);
            }
        });

        btnGestionarTicketDetalles.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionarTicketDetalles.setText("TicketDetalles");
        btnGestionarTicketDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarTicketDetallesActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Categorias");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Gestión de la tablas de la BD:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGestionUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGestionarTicketDetalles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGestionarMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnGestionProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGestionTickets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))))
                        .addGap(45, 45, 45))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGestionUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionarMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionTickets, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGestionarTicketDetalles, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGestionUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGestionUsuariosMouseClicked
        ControladorPrincipal.iniciarPantallaUsuarios();
    }//GEN-LAST:event_btnGestionUsuariosMouseClicked

    private void btnGestionarMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarMesasActionPerformed
       ControladorPrincipal.iniciarPantallaMesas();
    }//GEN-LAST:event_btnGestionarMesasActionPerformed

    private void btnGestionTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionTicketsActionPerformed
        ControladorPrincipal.iniciarPantallaTickets();
    }//GEN-LAST:event_btnGestionTicketsActionPerformed

    private void btnGestionarTicketDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarTicketDetallesActionPerformed
        ControladorPrincipal.inciarPantallaTicketDetalle();
    }//GEN-LAST:event_btnGestionarTicketDetallesActionPerformed

    private void btnGestionProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionProductosActionPerformed
        ControladorPrincipal.iniciarPantallaProductos();
    }//GEN-LAST:event_btnGestionProductosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ControladorPrincipal.iniciarPantallaCategorias();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton btnGestionProductos;
    private javax.swing.JButton btnGestionTickets;
    private javax.swing.JButton btnGestionUsuarios;
    private javax.swing.JButton btnGestionarMesas;
    private javax.swing.JButton btnGestionarTicketDetalles;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
