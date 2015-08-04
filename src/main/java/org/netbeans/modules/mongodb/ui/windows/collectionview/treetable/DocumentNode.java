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
import java.util.Map;
import org.netbeans.modules.mongodb.util.JsonProperty;

/**
 *
 * @author Yann D'Isanto
 */
public final class DocumentNode extends DBObjectNode {
    
    public DocumentNode(DBObject document) {
        super(document);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setUserObject(Object object) {
        super.setUserObject(object);
        children.clear();
        final Map<String, Object> map = getUserObject().toMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            add(new JsonPropertyNode(new JsonProperty(entry.getKey(), entry.getValue())));
        }
    }
}