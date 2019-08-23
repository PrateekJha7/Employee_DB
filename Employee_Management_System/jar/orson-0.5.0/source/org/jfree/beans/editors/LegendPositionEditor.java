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

import org.jfree.beans.LegendPosition;

/**
 * A JavaBeans property editor for the {@link LegendPosition} class.
 */
public class LegendPositionEditor extends PropertyEditorSupport {

    /**
     * Returns a string representing the current value.  This will be one of
     * <code>TOP</code>, <code>BOTTOM</code>, <code>LEFT</code>, 
     * <code>RIGHT</code> and <code>NULL</code>.
     * 
     * @return A string representing the current value.
     */
    public String getAsText() {
        LegendPosition lp = (LegendPosition) getValue();
        if (lp.equals(LegendPosition.TOP)) {
            return "TOP";
        }
        else if (lp.equals(LegendPosition.BOTTOM)) {
            return "BOTTOM";
        }
        else if (lp.equals(LegendPosition.LEFT)) {
            return "LEFT";
        }
        else if (lp.equals(LegendPosition.RIGHT)) {
            return "RIGHT";
        }
        else if (lp.equals(LegendPosition.NONE)) {
            return "NONE";
        }
        throw new IllegalStateException("Bad LegendPosition.");
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
        if ("TOP".equals(text)) {
            setValue(LegendPosition.TOP);
        }
        else if ("BOTTOM".equals(text)) {
            setValue(LegendPosition.BOTTOM);
        }
        else if ("LEFT".equals(text)) {
            setValue(LegendPosition.LEFT);
        }
        else if ("RIGHT".equals(text)) {
            setValue(LegendPosition.RIGHT);
        }
        else if ("NONE".equals(text)) {
            setValue(LegendPosition.NONE);
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
        return new String[] {"TOP", "BOTTOM", "LEFT", "RIGHT", "NONE"};
    }

    /**
     * Returns a string for the property value.
     * 
     * @return A string for the property value.
     */
    public String getJavaInitializationString() {
        LegendPosition lp = (LegendPosition) getValue();
        return "org.jfree.beans." + lp.toString();
    }

}
