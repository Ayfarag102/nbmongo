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
package org.netbeans.modules.mongodb.ui.wizards;

import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.nio.charset.Charset;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.mongodb.ui.util.JsonFileFilter;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.ChangeSupport;
import org.openide.util.NbBundle.Messages;

@Messages({
    "ImportOptionsStep=Import options"})
public final class ImportVisualPanel1 extends JPanel {

    private final ChangeSupport changeSupport = new ChangeSupport(this);

    private final JFileChooser fileChooser;

    private final JTextComponent collectionEditor;

    /**
     * Creates new form ImportVisualPanel1
     */
    public ImportVisualPanel1(MongoDatabase db) {
        initComponents();
        collectionEditor = (JTextComponent) collectionComboBox.getEditor().getEditorComponent();
        dropCheckBox.setVisible(false);
        final File home = new File(System.getProperty("user.home"));
        fileChooser = new FileChooserBuilder("import-export-filechooser")
            .setTitle("Import documents")
            .setDefaultWorkingDirectory(home)
            .setApproveText("Import")
            .setFileFilter(new JsonFileFilter())
            .setFilesOnly(true)
            .createFileChooser();
        for (String collection : db.listCollectionNames()) {
            collectionComboBox.addItem(collection);
        }
        for (Charset charset : Charset.availableCharsets().values()) {
            encodingComboBox.addItem(charset);
        }
        final DocumentListener documentListener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }

        };
        fileField.getDocument().addDocumentListener(documentListener);
        collectionEditor.getDocument().addDocumentListener(documentListener);
    }

    @Override
    public String getName() {
        return Bundle.ImportOptionsStep();
    }

    JFileChooser getFileChooser() {
        return fileChooser;
    }

    JTextField getFileField() {
        return fileField;
    }

    JComboBox getEncodingComboBox() {
        return encodingComboBox;
    }

    JTextComponent getCollectionEditor() {
        return collectionEditor;
    }

    JCheckBox getDropCheckBox() {
        return dropCheckBox;
    }

    public final void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    public final void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileLabel = new javax.swing.JLabel();
        fileField = new javax.swing.JTextField();
        browseFileButton = new javax.swing.JButton();
        encodingLabel = new javax.swing.JLabel();
        encodingComboBox = new javax.swing.JComboBox<Charset>();
        collectionComboBox = new javax.swing.JComboBox<String>();
        collectionLabel = new javax.swing.JLabel();
        dropCheckBox = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(fileLabel, org.openide.util.NbBundle.getMessage(ImportVisualPanel1.class, "ImportVisualPanel1.fileLabel.text")); // NOI18N

        fileField.setEditable(false);
        fileField.setText(org.openide.util.NbBundle.getMessage(ImportVisualPanel1.class, "ImportVisualPanel1.fileField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(browseFileButton, org.openide.util.NbBundle.getMessage(ImportVisualPanel1.class, "ImportVisualPanel1.browseFileButton.text")); // NOI18N
        browseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(encodingLabel, org.openide.util.NbBundle.getMessage(ImportVisualPanel1.class, "ImportVisualPanel1.encodingLabel.text")); // NOI18N

        collectionComboBox.setEditable(true);

        org.openide.awt.Mnemonics.setLocalizedText(collectionLabel, org.openide.util.NbBundle.getMessage(ImportVisualPanel1.class, "ImportVisualPanel1.collectionLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(dropCheckBox, org.openide.util.NbBundle.getMessage(ImportVisualPanel1.class, "ImportVisualPanel1.dropCheckBox.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dropCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(collectionLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(collectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(encodingLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(encodingComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fileLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileField)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseFileButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLabel)
                    .addComponent(fileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encodingLabel)
                    .addComponent(encodingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(collectionLabel)
                    .addComponent(collectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(dropCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void browseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFileButtonActionPerformed
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            if (file != null) {
                fileField.setText(file.getAbsolutePath());
                if (collectionEditor.getText().trim().isEmpty()) {
                    final String collectionName = file.getName().replaceAll("\\.json$", "");
                    collectionEditor.setText(collectionName);
                }
            }
        }
    }//GEN-LAST:event_browseFileButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseFileButton;
    private javax.swing.JComboBox<String> collectionComboBox;
    private javax.swing.JLabel collectionLabel;
    private javax.swing.JCheckBox dropCheckBox;
    private javax.swing.JComboBox<Charset> encodingComboBox;
    private javax.swing.JLabel encodingLabel;
    private javax.swing.JTextField fileField;
    private javax.swing.JLabel fileLabel;
    // End of variables declaration//GEN-END:variables
}
