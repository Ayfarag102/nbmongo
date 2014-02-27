/*
 * The MIT License
 *
 * Copyright 2014 Yann D'Isanto.
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
package org.netbeans.modules.mongodb.ui.windows.collectionview.actions;

import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.modules.mongodb.Images;
import org.netbeans.modules.mongodb.ui.windows.CollectionView;
import org.netbeans.modules.mongodb.ui.windows.CollectionView.ResultView;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Yann D'Isanto
 */
@Messages({
    "ACTION_displayResultsAsTreeTable=Display results in tree table",
    "ACTION_displayResultsAsTreeTable_tooltip=Display results in tree table",
    "ACTION_displayResultsAsFlatTable=Display results in flat table",
    "ACTION_displayResultsAsFlatTable_tooltip=Display results in flat table"
})
public final class ChangeResultViewAction extends CollectionViewAction {

    private final ResultView resultView;

    private ChangeResultViewAction(CollectionView view, ResultView resultView, String name, Icon icon, String shortDescription) {
        super(view, name, icon, shortDescription);
        this.resultView = resultView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getView().changeResultView(resultView);
    }

    public static ChangeResultViewAction create(CollectionView view, ResultView resultView) {
        switch (resultView) {
            case FLAT_TABLE:
                return new ChangeResultViewAction(view, resultView, 
                    Bundle.ACTION_displayResultsAsFlatTable(), 
                    new ImageIcon(Images.FLAT_TABLE_VIEW_ICON), 
                    Bundle.ACTION_displayResultsAsFlatTable_tooltip());
            case TREE_TABLE:
                return new ChangeResultViewAction(view, resultView, 
                    Bundle.ACTION_displayResultsAsTreeTable(), 
                    new ImageIcon(Images.TREE_TABLE_VIEW_ICON), 
                    Bundle.ACTION_displayResultsAsTreeTable_tooltip());
            default:
                throw new AssertionError();
        }
    }
}
