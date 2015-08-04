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
package org.netbeans.modules.mongodb.ui.explorer;

import com.mongodb.DBCollection;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import lombok.AllArgsConstructor;
import org.netbeans.modules.mongodb.indexes.Index;
import org.netbeans.modules.mongodb.resources.Images;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author Yann D'Isanto
 */
@NbBundle.Messages({
    "ACTION_dropIndex=Drop index",
    "# {0} - index name",
    "dropIndexConfirmText=Permanently drop ''{0}'' index?"
})
class IndexNode extends AbstractNode {

    private final IndexKeyNodesFactory childFactory;

    private final Index index;

    IndexNode(Index index, Lookup lookup) {
        this(index, new IndexKeyNodesFactory(lookup, index), lookup);
    }

    private IndexNode(Index index, IndexKeyNodesFactory childFactory, Lookup lookup) {
        super(Children.create(childFactory, true), lookup);
        this.index = index;
        this.childFactory = childFactory;
        setIconBaseWithExtension(Images.KEY_ICON_PATH);
    }

    @Override
    public String getName() {
        return index.getName();
    }

    @Override
    public Action[] getActions(boolean ignored) {
        final List<Action> actions = new LinkedList<>();
        if ("_id_".equals(index.getName()) == false) {
            actions.add(new DropIndexAction());
        }
        final Action[] orig = super.getActions(ignored);
        if (orig.length > 0) {
            actions.add(null);
        }
        actions.addAll(Arrays.asList(orig));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.put(new LocalizedProperties(IndexNode.class)
                .stringProperty("name", index.getName())
                .stringProperty("nameSpace", index.getNameSpace())
                .booleanProperty("sparse", index.isSparse())
                .booleanProperty("unique", index.isUnique())
                .booleanProperty("dropDuplicates", index.isDropDuplicates())
                .toArray());
        sheet.put(set);
        return sheet;
    }

    public void refreshChildren() {
        childFactory.refresh();
    }

    private class DropIndexAction extends AbstractAction {

        public DropIndexAction() {
            super(Bundle.ACTION_dropIndex());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DBCollection collection = getLookup().lookup(DBCollection.class);
            final Object dlgResult = DialogDisplayer.getDefault().notify(new NotifyDescriptor.Confirmation(
                    Bundle.dropIndexConfirmText(index.getName()),
                    NotifyDescriptor.YES_NO_OPTION));
            if (dlgResult.equals(NotifyDescriptor.OK_OPTION)) {
                collection.dropIndex(index.getName());
                ((CollectionNode) getParentNode()).refreshChildren();
            }
        }
    }

}