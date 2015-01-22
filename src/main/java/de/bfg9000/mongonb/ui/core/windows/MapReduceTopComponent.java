package de.bfg9000.mongonb.ui.core.windows;

import com.mongodb.DBCollection;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.netbeans.api.editor.DialogBinding;
import org.netbeans.modules.mongodb.CollectionInfo;
import org.netbeans.modules.mongodb.DbInfo;
import org.netbeans.modules.mongodb.ui.windows.QueryResultPanel;
import org.netbeans.modules.mongodb.ui.windows.QueryResultPanel.QueryResultWorkerFactory;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.CloneableEditorSupport;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays two editors to enter map / reduce functions and a result table.
 */
//@ConvertAsProperties(dtd = "-//de.bfg9000.mongonb.ui.core.windows//MapReduce//EN", autostore = false)
@TopComponent.Description(
        preferredID = "MapReduceTopComponent",
      //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@Messages({
    "CTL_MapReduceTopComponent=MapReduce Window",
    "HINT_MapReduceTopComponent=This is a MapReduce window"
})
public final class MapReduceTopComponent extends TopComponent implements QueryResultWorkerFactory {

    private static final long serialVersionUID = 1L;

    private static final ResourceBundle bundle = NbBundle.getBundle(MapReduceTopComponent.class);

    private final QueryHistory queryHistory = new QueryHistory();
    
    private final QueryResultPanel resultPanel;
    
    @Getter
    @Setter
    private Lookup lookup;
    
    public MapReduceTopComponent(Lookup lookup) {
        super();
        setLookup(lookup);
        initComponents();
        cmbHistory.setModel(new MapReduceHistoryModel(queryHistory));
        cmbHistory.setRenderer(new MapReduceHistoryItemRenderer());
        setName(Bundle.CTL_MapReduceTopComponent());
        setToolTipText(Bundle.HINT_MapReduceTopComponent());
        resultPanel = new QueryResultPanel(lookup, this, true);
        pnlResults.add(resultPanel, BorderLayout.CENTER);
    }

    @Override
    public QueryResultWorker createWorker() {
        String map = epMap.getText();
        String reduce = epReduce.getText();
        DBCollection collection = getLookup().lookup(DBCollection.class);
        queryHistory.add(new MapReduceHistoryItem(map, reduce));
        return new MapReduceWorker(
            collection, 
            map, 
            reduce, getName(),
            200);
    }

    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbToolBar = new javax.swing.JToolBar();
        lblCollection = new javax.swing.JLabel();
        btnRun = new javax.swing.JButton();
        cmbHistory = new javax.swing.JComboBox<MapReduceHistoryItem>();
        spltInputOutput = new javax.swing.JSplitPane();
        pnlResults = new javax.swing.JPanel();
        pnlFunctions = new javax.swing.JPanel();
        scrMapReduce = new javax.swing.JSplitPane();
        pnlMap = new javax.swing.JPanel();
        scrMap = new javax.swing.JScrollPane();
        epMap = new javax.swing.JEditorPane();
        lblMap = new javax.swing.JLabel();
        pnlReduce = new javax.swing.JPanel();
        scrReduce = new javax.swing.JScrollPane();
        epReduce = new javax.swing.JEditorPane();
        lblReduce = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        tbToolBar.setRollover(true);
        tbToolBar.add(lblCollection);

        btnRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/bfg9000/mongonb/ui/core/images/media-playback-start.png"))); // NOI18N
        btnRun.setFocusable(false);
        btnRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });
        tbToolBar.add(btnRun);

        cmbHistory.setPreferredSize(new java.awt.Dimension(28, 30));
        cmbHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbHistoryActionPerformed(evt);
            }
        });
        tbToolBar.add(cmbHistory);

        add(tbToolBar, java.awt.BorderLayout.NORTH);

        spltInputOutput.setDividerLocation(200);
        spltInputOutput.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlResults.setLayout(new java.awt.BorderLayout());
        spltInputOutput.setBottomComponent(pnlResults);

        pnlFunctions.setLayout(new java.awt.BorderLayout());

        scrMapReduce.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scrMapReduce.setDividerLocation(100);
        scrMapReduce.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        scrMapReduce.setResizeWeight(0.5);

        pnlMap.setLayout(new java.awt.BorderLayout());

        epMap.setName("epMap"); // NOI18N
        scrMap.setViewportView(epMap);

        pnlMap.add(scrMap, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(lblMap, org.openide.util.NbBundle.getMessage(MapReduceTopComponent.class, "MapReduceTopComponent.lblMap.text")); // NOI18N
        pnlMap.add(lblMap, java.awt.BorderLayout.NORTH);

        scrMapReduce.setLeftComponent(pnlMap);

        pnlReduce.setLayout(new java.awt.BorderLayout());

        epReduce.setName("epReduce"); // NOI18N
        scrReduce.setViewportView(epReduce);

        pnlReduce.add(scrReduce, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(lblReduce, org.openide.util.NbBundle.getMessage(MapReduceTopComponent.class, "MapReduceTopComponent.lblReduce.text")); // NOI18N
        pnlReduce.add(lblReduce, java.awt.BorderLayout.PAGE_START);

        scrMapReduce.setRightComponent(pnlReduce);

        pnlFunctions.add(scrMapReduce, java.awt.BorderLayout.CENTER);

        spltInputOutput.setTopComponent(pnlFunctions);

        add(spltInputOutput, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        resultPanel.refreshResults();
    }//GEN-LAST:event_btnRunActionPerformed

    private void cmbHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbHistoryActionPerformed
        if(null != cmbHistory.getSelectedItem()) {
            final int index = cmbHistory.getSelectedIndex();
            final MapReduceHistoryItem item = (MapReduceHistoryItem)queryHistory.getItems().get(index);
            epMap.setText(item.getMapFunction());
            epReduce.setText(item.getReduceFunction());
        }
    }//GEN-LAST:event_cmbHistoryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRun;
    private javax.swing.JComboBox<MapReduceHistoryItem> cmbHistory;
    private javax.swing.JEditorPane epMap;
    private javax.swing.JEditorPane epReduce;
    private javax.swing.JLabel lblCollection;
    private javax.swing.JLabel lblMap;
    private javax.swing.JLabel lblReduce;
    private javax.swing.JPanel pnlFunctions;
    private javax.swing.JPanel pnlMap;
    private javax.swing.JPanel pnlReduce;
    private javax.swing.JPanel pnlResults;
    private javax.swing.JScrollPane scrMap;
    private javax.swing.JSplitPane scrMapReduce;
    private javax.swing.JScrollPane scrReduce;
    private javax.swing.JSplitPane spltInputOutput;
    private javax.swing.JToolBar tbToolBar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        initWindowName();
        initEditor(epMap);
        initEditor(epReduce);
        updateCollectionLabel();
    }

//    void writeProperties(java.util.Properties p) {
//        p.setProperty("version", "1.0");
//    }
//
//    void readProperties(java.util.Properties p) {
//        // String version = p.getProperty("version");
//    }

    public void updateCollectionLabel() {
        Lookup lookup = getLookup();
        final DbInfo dbInfo = lookup.lookup(DbInfo.class);
        final CollectionInfo collectionInfo = lookup.lookup(CollectionInfo.class);
        final StringBuilder builder = new StringBuilder();
        builder.append(dbInfo.getDbName())
               .append(".")
               .append(collectionInfo.getName());
        lblCollection.setText(builder.toString());
    }

    private void initWindowName() {
        final String nameTemplate = bundle.getString("MapReduceTopComponent.name");
        final Mode editorMode = WindowManager.getDefault().findMode("editor");
        final TopComponent[] openedTopComponents = WindowManager.getDefault().getOpenedTopComponents(editorMode);

        String name = "";
        int counter = 0;
        boolean found = true;
        while(found) {
            found = false;
            name = MessageFormat.format(nameTemplate, ++counter);
            for(TopComponent tc: openedTopComponents)
                if(name.equals(tc.getName())) {
                    found = true;
                    break;
                }
        }

        setName(name);
    }

    private void initEditor(JEditorPane epEditor) {
        epEditor.setEditorKit(CloneableEditorSupport.getEditorKit("text/x-javascript"));
        try {
            final FileObject fob = FileUtil.createMemoryFileSystem().getRoot().createData(epEditor.getName(), "js");
            epEditor.getDocument().putProperty(Document.StreamDescriptionProperty, DataObject.find(fob));
            DialogBinding.bindComponentToFile(fob, 0, 0, epEditor);
        } catch(IOException ex) {
        }
    }

    /**
     * A query that is stored in the history.
     */
    private static final class MapReduceHistoryItem implements QueryHistory.QueryHistoryItem {

        private static final int MAX_LENGTH = 100;

        @Getter private final String mapFunction;
        @Getter private final String reduceFunction;

        public MapReduceHistoryItem(String map, String reduce) {
            mapFunction = Objects.requireNonNull(map);
            reduceFunction = Objects.requireNonNull(reduce);
        }

        public String getMapString() {
            final String temp = mapFunction.replaceAll("\\s", " ").replaceAll("\\s+", " ");
            return temp.length() > MAX_LENGTH ? temp.substring(0, MAX_LENGTH) +"..." : temp;
        }

        public String getReduceString() {
            final String temp = reduceFunction.replaceAll("\\s", " ").replaceAll("\\s+", " ");
            return temp.length() > MAX_LENGTH ? temp.substring(0, MAX_LENGTH) +"..." : temp;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof MapReduceHistoryItem))
                return false;

            final MapReduceHistoryItem other  = (MapReduceHistoryItem) o;
            return new EqualsBuilder().append(mapFunction, other.mapFunction)
                                      .append(reduceFunction, other.reduceFunction).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(mapFunction)
                                        .append(reduceFunction).toHashCode();
        }

    }

    /**
     * The model of the history combobox. It displays the values of the QueryHistory and updates whenever there is a new
     * history entry .
     */
    private static final class MapReduceHistoryModel extends DefaultComboBoxModel<MapReduceHistoryItem>
                                                     implements PropertyChangeListener{

        private final QueryHistory qHistory;

        public MapReduceHistoryModel(QueryHistory qHistory) {
            this.qHistory = qHistory;
            this.qHistory.addPropertyChangeListener(this);
        }

        @Override
        public int getSize() {
            return qHistory.getItems().size();
        }

        @Override
        public MapReduceHistoryItem getElementAt(int index) {
            return (MapReduceHistoryItem) qHistory.getItems().get(index);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(QueryHistory.PROPERTY_ITEMS.equals(evt.getPropertyName()))
                fireContentsChanged(this, 0, getSize() -1);
        }

    }

    /**
     * A renderer for the map/reduce functions displayed in the history combobox.
     */
    private static final class MapReduceHistoryItemRenderer extends JPanel
                                                            implements ListCellRenderer<MapReduceHistoryItem> {

        private final JLabel lblMapFunction;
        private final JLabel lblReduceFunction;

        public MapReduceHistoryItemRenderer() {
            setLayout(new GridLayout(2, 1, 5, 0));
            setBorder(new EmptyBorder(0, 0, 2, 0));

            final JPanel pnlFirstRow = new JPanel(new BorderLayout());
            final JLabel lblMapDesc = new JLabel("Map: ");
            lblMapDesc.setFont(lblMapDesc.getFont().deriveFont(Font.BOLD));
            pnlFirstRow.add(lblMapDesc, BorderLayout.WEST);
            pnlFirstRow.add(lblMapFunction = new JLabel(), BorderLayout.CENTER);
            add(pnlFirstRow);

            final JPanel pnlSecondRow = new JPanel(new BorderLayout());
            final JLabel lblReduceDesc = new JLabel("Reduce: ");
            lblReduceDesc.setFont(lblReduceDesc.getFont().deriveFont(Font.BOLD));
            pnlSecondRow.add(lblReduceDesc, BorderLayout.WEST);
            pnlSecondRow.add(lblReduceFunction = new JLabel(), BorderLayout.CENTER);
            add(pnlSecondRow);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends MapReduceHistoryItem> list,
                         MapReduceHistoryItem value, int index, boolean isSelected, boolean cellHasFocus) {
            lblMapFunction.setText(null == value ? "" : value.getMapString());
            lblReduceFunction.setText(null == value ? "" : value.getReduceString());
            return this;
        }

    }

}
