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

import java.util.List;
import java.util.Map;

/**
 *
 * @author Yann D'Isanto
 */
public interface JsonNode {
    
    Object getValue();
    
    List<Object> getArrayValue();
    
    Map<String, Object> getObjectValue();
    
    /**
     * @return true if this node value is a json array
     */
    boolean isArrayValue();

    /**
     * @return true if this node value is not null
     */
    boolean isNotNullValue();

    /**
     * @return true if this node value is null
     */
    boolean isNullValue();

    /**
     * @return true if this node value is a json object
     */
    boolean isObjectValue();
    
    /**
     * @return true if this node value is a json simple value (boolean, string, numeric)
     */
    boolean isSimpleValue();
    
}
