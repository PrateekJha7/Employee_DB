/* ======================================================
 * Orson : a free chart beans library based on JFreeChart
 * ======================================================
 *
 * (C) Copyright 2007, by Object Refinery Limited.
 *
 * Project Info:  not-yet-released
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 */

package org.jfree.beans.editors;

import java.beans.PropertyEditorSupport;

import org.jfree.ui.RectangleEdge;

/**
 * A JavaBeans property editor for the {@link RectangleEdge} class.
 */
public class RectangleEdgeEditor extends PropertyEditorSupport {

    /**
     * Returns a string representing the current value.  This will be one of
     * the following:
     * <ul>
     *   <li>RectangleEdge.TOP</code></li>
     *   <li>RectangleEdge.BOTTOM</code></li>
     *   <li>RectangleEdge.LEFT</code></li>
     *   <li>RectangleEdge.RIGHT</code></li>
     * </ul>
     * 
     * @return A string representing the current value.
     */
    public String getAsText() {
        RectangleEdge r = (RectangleEdge) getValue();
        return r.toString();
    }
    
    /**
     * Sets the current value by parsing the supplied string.
     * 
     * @param text  the string value.
     * 
     * @throws IllegalArgumentException if <code>text</code> is not one of
     *     the values listed in {@link #getAsText()}.
     */
    public void setAsText(String text) throws IllegalArgumentException {
        if (RectangleEdge.TOP.toString().equals(text)) {
            setValue(RectangleEdge.TOP);
        }
        else if (RectangleEdge.BOTTOM.toString().equals(text)) {
            setValue(RectangleEdge.BOTTOM);
        }
        else if (RectangleEdge.LEFT.toString().equals(text)) {
            setValue(RectangleEdge.LEFT);
        }
        else if (RectangleEdge.RIGHT.toString().equals(text)) {
            setValue(RectangleEdge.RIGHT);
        }
        else {
            throw new IllegalArgumentException("Unrecognised 'text' argument.");
        }
    }
    
    /**
     * Returns the valid string values for this property.
     * 
     * @return The valid string values for this property.
     */
    public String[] getTags() {
        return new String[] {"RectangleEdge.TOP", "RectangleEdge.BOTTOM", 
                "RectangleEdge.LEFT", "RectangleEdge.RIGHT" };
    }

    /**
     * Returns a string for the property value.
     * 
     * @return A string for the property value.
     */
    public String getJavaInitializationString() {
        RectangleEdge r = (RectangleEdge) getValue();
        return "org.jfree.ui." + r.toString();
    }

}
