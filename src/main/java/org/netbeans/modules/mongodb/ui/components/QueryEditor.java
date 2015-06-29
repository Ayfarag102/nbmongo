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
package org.netbeans.modules.mongodb.ui.components;

import com.mongodb.util.JSONParseException;
import org.netbeans.modules.mongodb.util.Json;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.text.EditorKit;
import lombok.Getter;
import org.bson.Document;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

/**
 *
 * @author Yann D'Isanto
 */
@NbBundle.Messages({
    "invalidCriteriaJson=invalid criteria json",
    "invalidProjectionJson=invalid projection json",
    "invalidSortJson=invalid sort json"})
public final class QueryEditor extends javax.swing.JPanel {

    private final EditorKit jsonEditorKit = MimeLookup.getLookup("text/x-json").lookup(EditorKit.class);

    private JDialog dialog;
    
    private int dialogResult;

    @Getter
    private Document criteria;

    @Getter
    private Document projection;

    @Getter
    private Document sort;

    /**
     * Creates new form QueryPanel
     */
    public QueryEditor() {
        initComponents();
    }

    public Document parseCriteria() throws JSONParseException {
        return getEditorValue(criteriaEditor);
    }

    public void setCriteria(Document criteria) {
        setEditorValue(criteriaEditor, criteria);
        this.criteria = criteria;
    }

    public Document parseProjection() throws JSONParseException {
        return getEditorValue(projectionEditor);
    }

    public void setProjection(Document projection) {
        setEditorValue(projectionEditor, projection);
        this.projection = projection;
    }

    public Document parseSort() throws JSONParseException {
        return getEditorValue(sortEditor);
    }

    public void setSort(Document sort) {
        setEditorValue(sortEditor, sort);
        this.sort = sort;
    }

    private Document getEditorValue(JEditorPane editor) throws JSONParseException {
        final String json = editor.getText().trim();
        return Document.parse(json);
    }

    private void setEditorValue(JEditorPane editor, Document value) {
        final String text = value != null
            ? Json.prettify(value)
            : "{}";
        editor.setText(text);
    }

    public boolean validateInput() {
        try {
            parseCriteria();
        } catch (JSONParseException ex) {
            DialogDisplayer.getDefault().notify(
                new NotifyDescriptor.Message(Bundle.invalidCriteriaJson(),
                    NotifyDescriptor.ERROR_MESSAGE));
            return false;
        }
        try {
            parseProjection();
        } catch (JSONParseException ex) {
            DialogDisplayer.getDefault().notify(
                new NotifyDescriptor.Message(Bundle.invalidProjectionJson(),
                    NotifyDescriptor.ERROR_MESSAGE));
            return false;
        }
        try {
            parseSort();
        } catch (JSONParseException ex) {
            DialogDisplayer.getDefault().notify(
                new NotifyDescriptor.Message(Bundle.invalidSortJson(),
                    NotifyDescriptor.ERROR_MESSAGE));
            return false;
        }
        return true;
    }
    
    private void refreshInput() {
        setEditorValue(criteriaEditor, criteria);
        setEditorValue(projectionEditor, projection);
        setEditorValue(sortEditor, sort);
    }

    public boolean showDialog() {
        final Frame owner = WindowManager.getDefault().getMainWindow();
        dialog = new JDialog(owner, "Edit query", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        refreshInput();
        dialogResult = JOptionPane.CLOSED_OPTION;
        dialog.setVisible(true);
        if (dialogResult == JOptionPane.OK_OPTION) {
            criteria = parseCriteria();
            projection = parseProjection();
            sort = parseSort();
            return true;
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogPanel = new javax.swing.JPanel();
        panel = this;
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        splitPane1 = new javax.swing.JSplitPane();
        criteriaPanel = new javax.swing.JPanel();
        criteriaLabel = new javax.swing.JLabel();
        criteriaScrollPane = new javax.swing.JScrollPane();
        criteriaEditor = new javax.swing.JEditorPane();
        splitPane2 = new javax.swing.JSplitPane();
        projectionPanel = new javax.swing.JPanel();
        projectionLabel = new javax.swing.JLabel();
        projectionScrollPane = new javax.swing.JScrollPane();
        projectionEditor = new javax.swing.JEditorPane();
        sortPanel = new javax.swing.JPanel();
        sortLabel = new javax.swing.JLabel();
        sortScrollPane = new javax.swing.JScrollPane();
        sortEditor = new javax.swing.JEditorPane();

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, org.openide.util.NbBundle.getMessage(QueryEditor.class, "QueryEditor.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(okButton, org.openide.util.NbBundle.getMessage(QueryEditor.class, "QueryEditor.okButton.text")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogPanelLayout = new javax.swing.GroupLayout(dialogPanel);
        dialogPanel.setLayout(dialogPanelLayout);
        dialogPanelLayout.setHorizontalGroup(
            dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogPanelLayout.createSequentialGroup()
                        .addGap(0, 119, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        dialogPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        dialogPanelLayout.setVerticalGroup(
            dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        splitPane1.setDividerLocation(150);
        splitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitPane1.setResizeWeight(0.5);

        org.openide.awt.Mnemonics.setLocalizedText(criteriaLabel, org.openide.util.NbBundle.getMessage(QueryEditor.class, "QueryEditor.criteriaLabel.text")); // NOI18N

        criteriaEditor.setEditorKit(jsonEditorKit);
        criteriaScrollPane.setViewportView(criteriaEditor);

        javax.swing.GroupLayout criteriaPanelLayout = new javax.swing.GroupLayout(criteriaPanel);
        criteriaPanel.setLayout(criteriaPanelLayout);
        criteriaPanelLayout.setHorizontalGroup(
            criteriaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(criteriaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(criteriaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(criteriaPanelLayout.createSequentialGroup()
                        .addComponent(criteriaLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(criteriaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
                .addContainerGap())
        );
        criteriaPanelLayout.setVerticalGroup(
            criteriaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(criteriaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(criteriaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(criteriaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitPane1.setTopComponent(criteriaPanel);

        splitPane2.setDividerLocation(200);
        splitPane2.setResizeWeight(0.5);

        org.openide.awt.Mnemonics.setLocalizedText(projectionLabel, org.openide.util.NbBundle.getMessage(QueryEditor.class, "QueryEditor.projectionLabel.text")); // NOI18N

        projectionEditor.setEditorKit(jsonEditorKit);
        projectionScrollPane.setViewportView(projectionEditor);

        javax.swing.GroupLayout projectionPanelLayout = new javax.swing.GroupLayout(projectionPanel);
        projectionPanel.setLayout(projectionPanelLayout);
        projectionPanelLayout.setHorizontalGroup(
            projectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectionScrollPane)
                    .addGroup(projectionPanelLayout.createSequentialGroup()
                        .addComponent(projectionLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        projectionPanelLayout.setVerticalGroup(
            projectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitPane2.setLeftComponent(projectionPanel);

        org.openide.awt.Mnemonics.setLocalizedText(sortLabel, org.openide.util.NbBundle.getMessage(QueryEditor.class, "QueryEditor.sortLabel.text")); // NOI18N

        sortEditor.setEditorKit(jsonEditorKit);
        sortScrollPane.setViewportView(sortEditor);

        javax.swing.GroupLayout sortPanelLayout = new javax.swing.GroupLayout(sortPanel);
        sortPanel.setLayout(sortPanelLayout);
        sortPanelLayout.setHorizontalGroup(
            sortPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sortPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sortPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sortScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addGroup(sortPanelLayout.createSequentialGroup()
                        .addComponent(sortLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sortPanelLayout.setVerticalGroup(
            sortPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sortPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sortLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sortScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitPane2.setRightComponent(sortPanel);

        splitPane1.setRightComponent(splitPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (validateInput()) {
            dialogResult = JOptionPane.OK_OPTION;
            dialog.dispose();
        }
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dialogResult = JOptionPane.CANCEL_OPTION;
        dialog.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JEditorPane criteriaEditor;
    private javax.swing.JLabel criteriaLabel;
    private javax.swing.JPanel criteriaPanel;
    private javax.swing.JScrollPane criteriaScrollPane;
    private javax.swing.JPanel dialogPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel panel;
    private javax.swing.JEditorPane projectionEditor;
    private javax.swing.JLabel projectionLabel;
    private javax.swing.JPanel projectionPanel;
    private javax.swing.JScrollPane projectionScrollPane;
    private javax.swing.JEditorPane sortEditor;
    private javax.swing.JLabel sortLabel;
    private javax.swing.JPanel sortPanel;
    private javax.swing.JScrollPane sortScrollPane;
    private javax.swing.JSplitPane splitPane1;
    private javax.swing.JSplitPane splitPane2;
    // End of variables declaration//GEN-END:variables
}
