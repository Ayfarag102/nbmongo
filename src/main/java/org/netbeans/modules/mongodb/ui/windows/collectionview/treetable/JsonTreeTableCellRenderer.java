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
package org.netbeans.modules.mongodb.ui.windows.collectionview.treetable;

import com.mongodb.DBObject;
import org.netbeans.modules.mongodb.options.JsonCellRenderingOptions;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import org.bson.types.ObjectId;
import org.jdesktop.swingx.treetable.TreeTableNode;
import org.netbeans.modules.mongodb.options.LabelCategory;
import org.netbeans.modules.mongodb.options.LabelFontConf;
import org.netbeans.modules.mongodb.util.JsonProperty;

/**
 *
 * @author Yann D'Isanto
 */
public final class JsonTreeTableCellRenderer extends JPanel implements TreeCellRenderer {

    private static final long serialVersionUID = 1L;

    private static final Map<Class<?>, LabelCategory> LABEL_CATEGORIES = new HashMap<>();

    static {
        LABEL_CATEGORIES.put(String.class, LabelCategory.STRING_VALUE);
        LABEL_CATEGORIES.put(Integer.class, LabelCategory.INT_VALUE);
        LABEL_CATEGORIES.put(Double.class, LabelCategory.DECIMAL_VALUE);
        LABEL_CATEGORIES.put(Boolean.class, LabelCategory.BOOLEAN_VALUE);
        LABEL_CATEGORIES.put(ObjectId.class, LabelCategory.ID);
    }

//    private static final String ARRAY_COMMENT = "[ ]";
    private static final String OBJECT_COMMENT = "{ }";

    private final JsonCellRenderingOptions options = JsonCellRenderingOptions.INSTANCE;

    private final JLabel indexLabel = new JLabel();

    private final JLabel keyLabel = new JLabel();

    private final JLabel valueLabel = new JLabel();

    private final Border selectionBorder = BorderFactory.createLineBorder(Color.BLACK);

    private final Border nonSelectionBorder = BorderFactory.createEmptyBorder();

    /**
     * Color to use for the foreground for selected nodes.
     */
    private Color textSelectionColor;

    /**
     * Color to use for the background when a node is selected.
     */
    private Color backgroundSelectionColor;

    /**
     * Color to use for the background when the node isn't selected.
     */
    private Color backgroundNonSelectionColor;

    /**
     * Set to true after the constructor has run.
     */
    private final boolean inited;

    public JsonTreeTableCellRenderer() {
        super(new GridBagLayout());
        add(indexLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.WEST,
            GridBagConstraints.NONE,
            new Insets(0, 0, 0, 3), 0, 0));
        add(keyLabel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.WEST,
            GridBagConstraints.NONE,
            new Insets(0, 0, 0, 2), 0, 0));
        add(valueLabel, new GridBagConstraints(2, 0, 1, 1, 10.0, 1.0,
            GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 1), 2, 0));
        setOpaque(true);
        indexLabel.setOpaque(true);
        keyLabel.setOpaque(true);
        valueLabel.setOpaque(true);
        inited = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Component getTreeCellRendererComponent(JTree tree, Object node, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        setBackground(selected ? getBackgroundSelectionColor() : getBackgroundNonSelectionColor());
        setBorder(selected ? selectionBorder : nonSelectionBorder);

        final LabelFontConf commentFontConf = options.getLabelFontConf(LabelCategory.COMMENT);
        indexLabel.setText("");
        indexLabel.setFont(commentFontConf.getFont());
        if (selected) {
            indexLabel.setForeground(getTextSelectionColor());
            indexLabel.setBackground(getBackgroundSelectionColor());
        } else {
            indexLabel.setForeground(commentFontConf.getForeground());
            indexLabel.setBackground(commentFontConf.getBackground());
        }
        if (node instanceof TreeTableNode) {
            TreeTableNode parent = ((TreeTableNode) node).getParent();
            if (parent instanceof JsonNode && ((JsonNode) parent).isArrayValue()) {
                int index = parent.getIndex((TreeNode) node);
                if (index > -1) {
                    indexLabel.setText(String.format("%d -", index));
                }
            }
        }

        if (node instanceof DBObjectNode) {
            DBObject value = ((DBObjectNode) node).getUserObject();
            final boolean isDocumentNode = node instanceof DocumentNode;
            final LabelFontConf keyFontConf = options.getLabelFontConf(isDocumentNode
                ? LabelCategory.DOCUMENT
                : LabelCategory.KEY);
            final LabelFontConf valueFontConf = commentFontConf;
            keyLabel.setFont(keyFontConf.getFont());
            valueLabel.setFont(valueFontConf.getFont());
            valueLabel.setText("");
            if (isDocumentNode) {
                final Object id = value.get("_id");
                if (id != null) {
                    keyLabel.setText(String.valueOf(id));
                } else {
                    keyLabel.setText("");
                    valueLabel.setText(OBJECT_COMMENT);
                }
            } else {
                keyLabel.setText("");
                if (value instanceof List) {
                    valueLabel.setText(computeArrayLabel(((List) value).size()));
                } else if (value instanceof Map) {
                    valueLabel.setText(OBJECT_COMMENT);
                }
            }
            if (selected) {
                keyLabel.setForeground(getTextSelectionColor());
                keyLabel.setBackground(getBackgroundSelectionColor());
                valueLabel.setForeground(getTextSelectionColor());
                valueLabel.setBackground(getBackgroundSelectionColor());
            } else {
                keyLabel.setForeground(keyFontConf.getForeground());
                keyLabel.setBackground(keyFontConf.getBackground());
                valueLabel.setForeground(valueFontConf.getForeground());
                valueLabel.setBackground(valueFontConf.getBackground());
                if (isDocumentNode) {
                    setBackground(keyFontConf.getBackground());
                }
            }
        } else if (node instanceof JsonPropertyNode) {
            computeRendererForJsonPropertyNode((JsonPropertyNode) node, selected);
        } else if (node instanceof JsonValueNode) {
            computeRendererForJsonValuePropertyNode((JsonValueNode) node, selected);
        }
        return this;
    }

    private void computeRendererForJsonPropertyNode(JsonPropertyNode node, boolean selected) {
        final JsonProperty property = node.getUserObject();
        final Object value = property.getValue();
        LabelFontConf keyFontConf = options.getLabelFontConf(LabelCategory.KEY);
        LabelFontConf valueFontConf = options.getLabelFontConf(LabelCategory.COMMENT);
//        if (node.isLeaf() && value != null && (value instanceof List) == false && (value instanceof Map) == false) {
        if (node.isLeaf() && node.isSimpleValue()) {
            final LabelCategory valueLabelCategory = LABEL_CATEGORIES.get(value.getClass());
            keyFontConf = options.getLabelFontConf((value instanceof ObjectId) ? LabelCategory.ID : LabelCategory.KEY);
            if (valueLabelCategory != null) {
                valueFontConf = options.getLabelFontConf(valueLabelCategory);
            }

            keyLabel.setText(buildJsonKey(property.getName()));
            valueLabel.setText(value instanceof String ? buildJsonString(value) : value.toString());
        } else {
            keyLabel.setText(property.getName());
            if (node.isArrayValue()) {
                valueLabel.setText(computeArrayLabel((node.getArrayValue()).size()));
            } else if (node.isObjectValue()) {
                valueLabel.setText(OBJECT_COMMENT);
            } else {
                valueLabel.setText("");
            }
        }
        keyLabel.setFont(keyFontConf.getFont());
        valueLabel.setFont(valueFontConf.getFont());
        if (selected) {
            keyLabel.setForeground(getTextSelectionColor());
            keyLabel.setBackground(getBackgroundSelectionColor());
            valueLabel.setForeground(getTextSelectionColor());
            valueLabel.setBackground(getBackgroundSelectionColor());
        } else {
            keyLabel.setForeground(keyFontConf.getForeground());
            keyLabel.setBackground(keyFontConf.getBackground());
            valueLabel.setForeground(valueFontConf.getForeground());
            valueLabel.setBackground(valueFontConf.getBackground());
        }

    }

    private void computeRendererForJsonValuePropertyNode(JsonValueNode node, boolean selected) {
        final Object value = node.getUserObject();
        final LabelCategory valueLabelCategory = LABEL_CATEGORIES.get(value.getClass());
        final LabelFontConf keyFontConf = options.getLabelFontConf((value instanceof ObjectId) ? LabelCategory.ID : LabelCategory.KEY);
        final LabelFontConf valueFontConf = options.getLabelFontConf(valueLabelCategory);
        keyLabel.setText("");
        valueLabel.setText(value instanceof String ? buildJsonString(value) : value.toString());
        valueLabel.setFont(valueFontConf.getFont());
        if (selected) {
            keyLabel.setForeground(getTextSelectionColor());
            keyLabel.setBackground(getBackgroundSelectionColor());
            valueLabel.setForeground(getTextSelectionColor());
            valueLabel.setBackground(getBackgroundSelectionColor());
        } else {
            keyLabel.setForeground(keyFontConf.getForeground());
            keyLabel.setBackground(keyFontConf.getBackground());
            valueLabel.setForeground(valueFontConf.getForeground());
            valueLabel.setBackground(valueFontConf.getBackground());
        }
    }

    private String buildJsonKey(Object value) {
        return new StringBuilder().append(value).append(":").toString();
    }

    private String buildJsonString(Object value) {
        return new StringBuilder().append('"').append(value).append('"').toString();
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.7
     */
    @Override
    public void updateUI() {
        super.updateUI();
        // To avoid invoking new methods from the constructor, the
        // inited field is first checked. If inited is false, the constructor
        // has not run and there is no point in checking the value. As
        // all look and feels have a non-null value for these properties,
        // a null value means the developer has specifically set it to
        // null. As such, if the value is null, this does not reset the
        // value.
        if (!inited || (getTextSelectionColor() instanceof UIResource)) {
            setTextSelectionColor(
                UIManager.getColor("Tree.selectionForeground"));
        }
        if (!inited || (getBackgroundSelectionColor() instanceof UIResource)) {
            setBackgroundSelectionColor(
                UIManager.getColor("Tree.selectionBackground"));
        }
        if (!inited
            || (getBackgroundNonSelectionColor() instanceof UIResource)) {
            setBackgroundNonSelectionColor(
                UIManager.getColor("Tree.textBackground"));
        }
    }

    /**
     * Sets the color the text is drawn with when the node is selected.
     */
    public void setTextSelectionColor(Color newColor) {
        textSelectionColor = newColor;
    }

    /**
     * Returns the color the text is drawn with when the node is selected.
     */
    public Color getTextSelectionColor() {
        return textSelectionColor;
    }

    /**
     * Sets the color to use for the background if node is selected.
     */
    public void setBackgroundSelectionColor(Color newColor) {
        backgroundSelectionColor = newColor;
    }

    /**
     * Returns the color to use for the background if node is selected.
     */
    public Color getBackgroundSelectionColor() {
        return backgroundSelectionColor;
    }

    /**
     * Sets the background color to be used for non selected nodes.
     */
    public void setBackgroundNonSelectionColor(Color newColor) {
        backgroundNonSelectionColor = newColor;
    }

    /**
     * Returns the background color to be used for non selected nodes.
     */
    public Color getBackgroundNonSelectionColor() {
        return backgroundNonSelectionColor;
    }

    private String computeArrayLabel(int arraySize) {
        return new StringBuilder()
            .append('[')
            .append(arraySize)
            .append(']')
            .toString();
    }
}
