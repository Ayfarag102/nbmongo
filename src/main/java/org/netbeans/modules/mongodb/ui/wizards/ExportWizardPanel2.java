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

import java.io.File;
import java.nio.charset.Charset;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.mongodb.ui.util.DialogNotification;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle.Messages;

@Messages({
    "validation_file_missing=specify a file for export",
    "overwrite_file_confirmation_title=Overwrite confirmation",
    "# {0} - file name",
    "overwrite_file_confirmation=Overwrite \"{0}\" file?"})
public class ExportWizardPanel2 implements WizardDescriptor.ValidatingPanel<WizardDescriptor>, ChangeListener {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private ExportVisualPanel2 component;

    private final ChangeSupport changeSupport = new ChangeSupport(this);

    @Override
    public ExportVisualPanel2 getComponent() {
        if (component == null) {
            component = new ExportVisualPanel2();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void validate() throws WizardValidationException {
        final JFileChooser fileChooser = getComponent().getFileChooser();
        final File file = fileChooser.getSelectedFile();
        if (file == null) {
            throw new WizardValidationException(fileChooser, Bundle.validation_file_missing(), null);
        }
        if (file.exists()) {
            final String title = Bundle.overwrite_file_confirmation_title();
            final String message = Bundle.overwrite_file_confirmation(file.getName());
            if(DialogNotification.confirm(message, title) == false) {
                throw new WizardValidationException(null, null, null);
            }
        }
    }

    @Override
    public boolean isValid() {
        final File file = getComponent().getFileChooser().getSelectedFile();
        return file != null;
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        final ExportVisualPanel2 panel = getComponent();
        panel.setWizard(wiz);
        final JFileChooser fileChooser = panel.getFileChooser();
        File file = (File) wiz.getProperty(ExportWizardAction.PROP_FILE);
        if (file == null) {
            final String collection = (String) wiz.getProperty(ExportWizardAction.PROP_COLLECTION);
            if (collection != null) {
                file = new File(fileChooser.getCurrentDirectory(), collection + ".json");
            }
        }
        fileChooser.setSelectedFile(file);
        panel.getFileField().setText(file != null ? file.getAbsolutePath() : "");
        final Charset charset = (Charset) wiz.getProperty(ExportWizardAction.PROP_ENCODING);
        panel.getEncodingComboBox().setSelectedItem(charset != null ? charset : DEFAULT_CHARSET);
        final Boolean jsonArray = (Boolean) wiz.getProperty(ExportWizardAction.PROP_JSON_ARRAY);
        panel.getJsonArrayCheckBox().setSelected(jsonArray != null ? jsonArray : false);
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        final ExportVisualPanel2 panel = getComponent();
        wiz.putProperty(ExportWizardAction.PROP_FILE, 
            panel.getFileChooser().getSelectedFile());
        wiz.putProperty(ExportWizardAction.PROP_ENCODING, 
            panel.getEncodingComboBox().getSelectedItem());
        wiz.putProperty(ExportWizardAction.PROP_JSON_ARRAY, 
            panel.getJsonArrayCheckBox().isSelected());
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        changeSupport.fireChange();
    }

}
