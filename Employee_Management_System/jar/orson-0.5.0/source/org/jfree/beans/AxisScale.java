/* ======================================================
 * Orson : a free chart beans library based on JFreeChart
 * ======================================================
 *
 * (C) Copyright 2007, by Object Refinery Limited.
 *
 * Project Info:  http://www.jfree.org/orson/
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
 * 
 */

package org.jfree.beans;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * A token representing the axis scale type.
 */
public final class AxisScale implements Serializable {
    
    /** Integer. */
    public static final AxisScale INTEGER = new AxisScale("AxisScale.INTEGER");

    /** Floating point. */
    public static final AxisScale FLOAT = new AxisScale("AxisScale.FLOAT");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private AxisScale(String name) {
        this.name = name;
    }

    /**
     * Returns a string representing the object.
     *
     * @return the string (never <code>null</code>).
     */
    public String toString() {
        return this.name;
    }


    /**
     * Compares this object for equality with an other object.
     * Implementation note: This simply compares the factor instead
     * of the name.
     *
     * @param obj  the other object
     * 
     * @return true or false
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AxisScale)) {
            return false;
        }
        AxisScale that = (AxisScale) obj;
        return this.name.equals(that.name);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return the hashcode
     */
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Ensures that serialization returns the unique instances.
     * 
     * @return the object.
     * 
     * @throws ObjectStreamException if there is a problem.
     */
    private Object readResolve() throws ObjectStreamException {
        if (this.equals(AxisScale.INTEGER)) {
            return AxisScale.INTEGER;
        }
        else if (this.equals(AxisScale.FLOAT)) {
            return AxisScale.FLOAT;
        }           
        return null;
    }

}
