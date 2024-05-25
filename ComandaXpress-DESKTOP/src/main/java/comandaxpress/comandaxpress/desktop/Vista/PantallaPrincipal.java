/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package comandaxpress.comandaxpress.desktop.Vista;

import comandaxpress.comandaxpress.desktop.Controlador.ControladorPrincipal;

/**
 *
 * @author Juegos
 */
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

        btnGestionProductos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionProductos.setForeground(new java.awt.Color(118, 171, 167));
        btnGestionProductos.setText("Gestionar Productos");

        btnGestionarMesas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionarMesas.setForeground(new java.awt.Color(118, 171, 167));
        btnGestionarMesas.setText("Gestionar Mesas");

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
                            .addComponent(btnGestionProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(btnGestionarMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addComponent(btnGestionProductos)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGestionUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGestionUsuariosMouseClicked
        ControladorPrincipal.iniciarPantallaUsuarios();
    }//GEN-LAST:event_btnGestionUsuariosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton btnGestionProductos;
    private javax.swing.JButton btnGestionTickets;
    private javax.swing.JButton btnGestionUsuarios;
    private javax.swing.JButton btnGestionarMesas;
    // End of variables declaration//GEN-END:variables
}
