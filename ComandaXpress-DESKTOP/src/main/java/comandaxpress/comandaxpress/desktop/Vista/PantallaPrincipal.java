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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ComandaXpress Desktop Application");

        Titulo.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        Titulo.setForeground(new java.awt.Color(118, 171, 167));
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo.setText("COMANDAXPRESS");

        btnGestionUsuarios.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionUsuarios.setForeground(new java.awt.Color(118, 171, 167));
        btnGestionUsuarios.setText("Gestionar Usuarios");
        btnGestionUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGestionUsuariosMouseClicked(evt);
            }
        });

        btnGestionTickets.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionTickets.setForeground(new java.awt.Color(118, 171, 167));
        btnGestionTickets.setText("Gestionar Tickets");
        btnGestionTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionTicketsActionPerformed(evt);
            }
        });

        btnGestionProductos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionProductos.setForeground(new java.awt.Color(118, 171, 167));
        btnGestionProductos.setText("Gestionar Productos");
        btnGestionProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionProductosActionPerformed(evt);
            }
        });

        btnGestionarMesas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionarMesas.setForeground(new java.awt.Color(118, 171, 167));
        btnGestionarMesas.setText("Gestionar Mesas");
        btnGestionarMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarMesasActionPerformed(evt);
            }
        });

        btnGestionarTicketDetalles.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionarTicketDetalles.setForeground(new java.awt.Color(118, 171, 167));
        btnGestionarTicketDetalles.setText("Gestionar Detalles de los Tickets");
        btnGestionarTicketDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarTicketDetallesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGestionTickets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGestionUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGestionProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGestionarMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGestionarTicketDetalles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnGestionUsuarios)
                .addGap(18, 18, 18)
                .addComponent(btnGestionarMesas)
                .addGap(18, 18, 18)
                .addComponent(btnGestionTickets)
                .addGap(18, 18, 18)
                .addComponent(btnGestionarTicketDetalles)
                .addGap(13, 13, 13)
                .addComponent(btnGestionProductos)
                .addContainerGap(98, Short.MAX_VALUE))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton btnGestionProductos;
    private javax.swing.JButton btnGestionTickets;
    private javax.swing.JButton btnGestionUsuarios;
    private javax.swing.JButton btnGestionarMesas;
    private javax.swing.JButton btnGestionarTicketDetalles;
    // End of variables declaration//GEN-END:variables
}
