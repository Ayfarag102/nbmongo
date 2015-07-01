/*
 * Copyright (C) 2015 Yann D'Isanto
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.netbeans.modules.mongodb.indexes;

import org.netbeans.modules.mongodb.indexes.Index.GeoHaystackOptions;

/**
 *
 * @author Yann D'Isanto
 */
public class GeoHaystackOptionsPanel extends javax.swing.JPanel {

    /**
     * Creates new form GeoHaystackOptionsPanel
     */
    public GeoHaystackOptionsPanel() {
        initComponents();
    }

    public GeoHaystackOptions getGeoHaystackOptions() {
        Double bucketSize = bucketSizeCheckBox.isSelected() ? (Double) bucketSizeSpinner.getValue() : null;
        return new GeoHaystackOptions(bucketSize);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bucketSizeSpinner = new javax.swing.JSpinner();
        bucketSizeCheckBox = new javax.swing.JCheckBox();

        bucketSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, null, 0.1d));
        bucketSizeSpinner.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(bucketSizeCheckBox, org.openide.util.NbBundle.getMessage(GeoHaystackOptionsPanel.class, "GeoHaystackOptionsPanel.bucketSizeCheckBox.text")); // NOI18N
        bucketSizeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bucketSizeCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bucketSizeCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bucketSizeSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bucketSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucketSizeCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bucketSizeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bucketSizeCheckBoxActionPerformed
        bucketSizeSpinner.setEnabled(bucketSizeCheckBox.isSelected());
    }//GEN-LAST:event_bucketSizeCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox bucketSizeCheckBox;
    private javax.swing.JSpinner bucketSizeSpinner;
    // End of variables declaration//GEN-END:variables
}
