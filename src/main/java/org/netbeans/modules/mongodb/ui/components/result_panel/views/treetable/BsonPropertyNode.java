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
package org.netbeans.modules.mongodb.ui.components.result_panel.views.treetable;

import lombok.Getter;
import lombok.Setter;
import org.bson.BsonValue;
import org.netbeans.modules.mongodb.util.BsonProperty;

/**
 *
 * @author Yann D'Isanto
 */
public class BsonPropertyNode extends BsonValueNode {

    @Getter
    @Setter
    private String propertyName;
    
    public BsonPropertyNode(String propertyName, BsonValue value, boolean sortDocumentsFields) {
        super(value, sortDocumentsFields);
        this.propertyName = propertyName;
    }
    
    public BsonProperty getBsonProperty() {
        return new BsonProperty(propertyName, getValue());
    }
}
