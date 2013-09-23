/*
 * The MIT License
 *
 * Copyright 2013 Yann.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.timboudreau.netbeans.mongodb.views;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import com.timboudreau.netbeans.mongodb.CollectionInfo;
import com.timboudreau.netbeans.mongodb.util.Json;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.EditorKit;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.util.lookup.Lookups;

/**
 * Top component which displays something.
 */
@TopComponent.Description(
        preferredID = "CollectionViewTopComponent",
        iconBase = "com/timboudreau/netbeans/mongodb/mongo-collection.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@Messages({"addDocumentTitle=Add new document", "editDocumentTitle=Edit document", "invalidJson=invalid json"})
public final class CollectionViewTopComponent extends TopComponent {

    private static final Integer[] ITEMS_PER_PAGE_VALUES = {10, 20, 50, 100};

    private final CollectionInfo collectionInfo;

    private final Lookup lookup;

    private final DocumentsListModel listModel;

    public CollectionViewTopComponent(CollectionInfo collectionInfo, Lookup lookup) {
        this.collectionInfo = collectionInfo;
        this.lookup = lookup;
        associateLookup(Lookups.singleton(collectionInfo));
        initComponents();
        setName(collectionInfo.getName());
        nameValueLabel.setText(collectionInfo.getName());
        final DBCollection dbCollection = lookup.lookup(DBCollection.class);
        listModel = new DocumentsListModel(dbCollection);
        documentsList.setModel(listModel);
        documentsList.setCellRenderer(new MongoDocumentListCellRenderer());
        documentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        documentsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    updateDocumentButtonsState();
                }
            }
        });
        documentsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && documentsList.getSelectedValue() != null) {
                    editSelectedDocument();
                }
            }
        });
        reload();
    }

    private void reload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listModel.setPage(1);
                listModel.update();
                updatePagination();
                updateDocumentButtonsState();
            }
        }).start();
    }

    private void updatePagination() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int page = listModel.getPage();
                int pageCount = listModel.getPageCount();
                pageLabel.setText(String.valueOf(page));
                pageCountLabel.setText(String.valueOf(pageCount));
                boolean leftButtonsEnabled = page > 1;
                firstButton.setEnabled(leftButtonsEnabled);
                previousButton.setEnabled(leftButtonsEnabled);
                boolean rightButtonsEnabled = page < pageCount;
                nextButton.setEnabled(rightButtonsEnabled);
                lastButton.setEnabled(rightButtonsEnabled);
            }
        });
    }

    private void updateDocumentButtonsState() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boolean itemSelected = documentsList.getSelectedIndex() > -1;
                deleteButton.setEnabled(itemSelected);
                editButton.setEnabled(itemSelected);
            }
        });
    }

    private DBObject showJsonEditor(String title, String defaultJson) {
        final JEditorPane editor = new JEditorPane();
        final EditorKit jsonEditorKit = MimeLookup.getLookup("text/x-json").lookup(EditorKit.class);
        if (jsonEditorKit != null) {
            editor.setEditorKit(jsonEditorKit);
        }
        editor.setPreferredSize(new Dimension(450, 300));
        String json = Json.prettify(defaultJson);
        boolean doLoop = true;
        while (doLoop) {
            doLoop = false;
            editor.setText(json);
            final DialogDescriptor desc = new DialogDescriptor(editor, title);
            final Object dlgResult = DialogDisplayer.getDefault().notify(desc);
            if (dlgResult.equals(NotifyDescriptor.OK_OPTION)) {
                try {
                    json = editor.getText().trim();
                    return (DBObject) JSON.parse(json);
                } catch (JSONParseException ex) {
                    DialogDisplayer.getDefault().notify(
                            new NotifyDescriptor.Message(Bundle.invalidJson(), NotifyDescriptor.ERROR_MESSAGE));
                    doLoop = true;
                }
            }
        }
        return null;
    }

    private void editSelectedDocument() {
        final DBObject document = documentsList.getSelectedValue();
        final DBObject modifiedDocument = showJsonEditor(
                Bundle.editDocumentTitle(),
                JSON.serialize(document));
        if (modifiedDocument != null) {
            try {
                final DBCollection dbCollection = lookup.lookup(DBCollection.class);
                dbCollection.save(modifiedDocument);
                reload();
            } catch (MongoException ex) {
                DialogDisplayer.getDefault().notify(
                        new NotifyDescriptor.Message(ex.getLocalizedMessage(), NotifyDescriptor.ERROR_MESSAGE));
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameValueLabel = new javax.swing.JLabel();
        listScrollPane = new javax.swing.JScrollPane();
        documentsList = new javax.swing.JList<DBObject>();
        itemsPerPageLabel = new javax.swing.JLabel();
        itemsPerPageComboBox = new JComboBox(ITEMS_PER_PAGE_VALUES);
        lastButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        firstButton = new javax.swing.JButton();
        previousButton = new javax.swing.JButton();
        pageCountLabel = new javax.swing.JLabel();
        pageLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.nameLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(nameValueLabel, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.nameValueLabel.text")); // NOI18N

        listScrollPane.setViewportView(documentsList);

        org.openide.awt.Mnemonics.setLocalizedText(itemsPerPageLabel, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.itemsPerPageLabel.text")); // NOI18N

        itemsPerPageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemsPerPageComboBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(lastButton, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.lastButton.text")); // NOI18N
        lastButton.setEnabled(false);
        lastButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(nextButton, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.nextButton.text")); // NOI18N
        nextButton.setEnabled(false);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(firstButton, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.firstButton.text")); // NOI18N
        firstButton.setEnabled(false);
        firstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(previousButton, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.previousButton.text")); // NOI18N
        previousButton.setEnabled(false);
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(pageCountLabel, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.pageCountLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(pageLabel, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.pageLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addButton, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.addButton.text")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(editButton, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.editButton.text")); // NOI18N
        editButton.setEnabled(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deleteButton, org.openide.util.NbBundle.getMessage(CollectionViewTopComponent.class, "CollectionViewTopComponent.deleteButton.text")); // NOI18N
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(firstButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(previousButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pageCountLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(itemsPerPageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(itemsPerPageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameValueLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(itemsPerPageLabel)
                    .addComponent(itemsPerPageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton)
                    .addComponent(deleteButton)
                    .addComponent(editButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastButton)
                    .addComponent(nextButton)
                    .addComponent(firstButton)
                    .addComponent(previousButton)
                    .addComponent(pageCountLabel)
                    .addComponent(pageLabel)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void firstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstButtonActionPerformed
        reload();
    }//GEN-LAST:event_firstButtonActionPerformed

    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                int page = listModel.getPage();
                if (page > 1) {
                    listModel.setPage(page - 1);
                    listModel.update();
                    updatePagination();
                }
            }
        }).start();
    }//GEN-LAST:event_previousButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                int page = listModel.getPage();
                if (page < listModel.getPageCount()) {
                    listModel.setPage(page + 1);
                    listModel.update();
                    updatePagination();
                }
            }
        }).start();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void lastButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastButtonActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                listModel.setPage(listModel.getPageCount());
                listModel.update();
                updatePagination();
            }
        }).start();
    }//GEN-LAST:event_lastButtonActionPerformed

    private void itemsPerPageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemsPerPageComboBoxActionPerformed
        listModel.setItemsPerPage((Integer) itemsPerPageComboBox.getSelectedItem());
        reload();
    }//GEN-LAST:event_itemsPerPageComboBoxActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        final DBObject document = showJsonEditor(
                Bundle.addDocumentTitle(),
                "{}");
        if (document != null) {
            try {
                final DBCollection dbCollection = lookup.lookup(DBCollection.class);
                dbCollection.insert(document);
                reload();
            } catch (MongoException ex) {
                DialogDisplayer.getDefault().notify(
                        new NotifyDescriptor.Message(ex.getLocalizedMessage(), NotifyDescriptor.ERROR_MESSAGE));
            }
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        final DBObject document = documentsList.getSelectedValue();
        final Object dlgResult = DialogDisplayer.getDefault().notify(
                new NotifyDescriptor.Confirmation("Permanently delete this document?", NotifyDescriptor.YES_NO_OPTION));
        if (dlgResult.equals(NotifyDescriptor.OK_OPTION)) {
            try {
                final DBCollection dbCollection = lookup.lookup(DBCollection.class);
                dbCollection.remove(document);
                reload();
            } catch (MongoException ex) {
                DialogDisplayer.getDefault().notify(
                        new NotifyDescriptor.Message(ex.getLocalizedMessage(), NotifyDescriptor.ERROR_MESSAGE));
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        editSelectedDocument();
    }//GEN-LAST:event_editButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JList<DBObject> documentsList;
    private javax.swing.JButton editButton;
    private javax.swing.JButton firstButton;
    private javax.swing.JComboBox itemsPerPageComboBox;
    private javax.swing.JLabel itemsPerPageLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton lastButton;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameValueLabel;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel pageCountLabel;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JButton previousButton;
    // End of variables declaration//GEN-END:variables

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
