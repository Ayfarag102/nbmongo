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

import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.modules.mongodb.indexes.Index.KeySort;
import org.netbeans.modules.mongodb.ui.util.ValidablePanel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Yann D'Isanto
 */
@Messages({
    "IndexKeyPanel_title=Index key",
    "VALIDATION_emptyField=specify the key field",
    "# {0} - field name",
    "VALIDATION_fieldAlreadyInKey=field {0} is already in the key"
})
public class IndexKeyPanel extends ValidablePanel {

    private static final long serialVersionUID = 1L;

    private final List<String> forbiddenKeys;
    
    IndexKeyPanel(List<String> forbiddenKeys) {
        initComponents();
        this.forbiddenKeys = forbiddenKeys;
        sortComboBox.setSelectedItem(KeySort.ASCENDING);
        keyField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                performValidation();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performValidation();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                performValidation();
            }
        });
    }

    @Override
    protected String computeValidationProblem() {
        if(keyField.getText().isEmpty()) {
            return Bundle.VALIDATION_emptyField();
        }
        String fieldName = keyField.getText();
        if(forbiddenKeys.contains(fieldName)) {
            return Bundle.VALIDATION_fieldAlreadyInKey(fieldName);
        }
        return null;
    }

    Index.Key getKey() {
        return new Index.Key(keyField.getText(), (KeySort) sortComboBox.getSelectedItem());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        keyField = new javax.swing.JTextField();
        sortComboBox = new javax.swing.JComboBox<>(KeySort.values())
        ;

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(keyField, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField keyField;
    private javax.swing.JComboBox<KeySort> sortComboBox;
    // End of variables declaration//GEN-END:variables

    public static Index.Key showCreateDialog(List<String> usedKeys) {
        final IndexKeyPanel panel = new IndexKeyPanel(usedKeys);
        final DialogDescriptor desc = new DialogDescriptor(panel, Bundle.IndexKeyPanel_title());
        panel.setNotificationLineSupport(desc.createNotificationLineSupport());
        panel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                desc.setValid(panel.isValidationSuccess());
            }
        });
        if (NotifyDescriptor.OK_OPTION.equals(DialogDisplayer.getDefault().notify(desc))) {
            return panel.getKey();
        }
        return null;
    }
}
