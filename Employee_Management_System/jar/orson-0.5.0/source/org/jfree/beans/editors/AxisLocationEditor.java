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

import org.jfree.chart.axis.AxisLocation;

/**
 * A JavaBeans property editor for the {@link AxisLocation} class.
 */
public class AxisLocationEditor extends PropertyEditorSupport {

    /**
     * Returns a string representing the current value.
     * 
     * @return A string representing the current value.
     */
    public String getAsText() {
        AxisLocation l = (AxisLocation) getValue();
        if (AxisLocation.BOTTOM_OR_LEFT.equals(l)) {
            return "BOTTOM OR LEFT";
        }
        else if (AxisLocation.BOTTOM_OR_RIGHT.equals(l)) {
            return "BOTTOM OR RIGHT";
        }
        else if (AxisLocation.TOP_OR_LEFT.equals(l)) {
            return "TOP OR LEFT";
        }
        else if (AxisLocation.TOP_OR_RIGHT.equals(l)) {
            return "TOP OR RIGHT";
        }
        throw new RuntimeException("Unrecognised location.");
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
        if ("BOTTOM OR LEFT".equals(text)) {
            setValue(AxisLocation.BOTTOM_OR_LEFT);
        }
        else if ("BOTTOM OR RIGHT".equals(text)) {
            setValue(AxisLocation.BOTTOM_OR_RIGHT);
        }
        else if ("TOP OR LEFT".equals(text)) {
            setValue(AxisLocation.TOP_OR_LEFT);
        }
        else if ("TOP OR RIGHT".equals(text)) {
            setValue(AxisLocation.TOP_OR_RIGHT);
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
        return new String[] {"TOP OR LEFT", "TOP OR RIGHT", "BOTTOM OR LEFT", 
                "BOTTOM OR RIGHT"};
    }

    /**
     * Returns a string for the property value.
     * 
     * @return A string for the property value.
     */
    public String getJavaInitializationString() {
        AxisLocation l = (AxisLocation) getValue();
        return "org.jfree.chart.axis." + l.toString();
    }

}
