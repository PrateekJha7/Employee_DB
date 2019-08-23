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

import java.awt.Paint;
import java.io.Serializable;

import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

/**
 * A (key, paint) pair for use by the {@link JPieChart} class (and possibly 
 * others later).
 */
public class KeyedPaint implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 0L;
    
    /** The key. */
    private Comparable key;

    /** The paint. */
    private Paint paint;

    /**
     * Creates a new (key, paint) pair.
     *
     * @param key  the key.
     * @param paint  the paint (<code>null</code> permitted).
     */
    public KeyedPaint(Comparable key, Paint paint) {
        this.key = key;
        this.paint = paint;
    }

    /**
     * Returns the key.
     *
     * @return The key.
     */
    public Comparable getKey() {
        return this.key;
    }

    /**
     * Returns the paint.
     *
     * @return The paint (possibly <code>null</code>).
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the paint.
     *
     * @param paint  the paint (<code>null</code> permitted).
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    
    /**
     * Returns a clone of this object.  It is assumed that the key is an 
     * immutable object, so it is not deep-cloned.  The object is deep-cloned 
     * if it implements {@link PublicCloneable}, otherwise a shallow clone is 
     * made.
     * 
     * @return A clone.
     * 
     * @throws CloneNotSupportedException if there is a problem cloning.
     */
    public Object clone() throws CloneNotSupportedException {
        KeyedPaint clone = (KeyedPaint) super.clone();
        return clone;      
    }
    
    /**
     * Tests if this object is equal to another.
     *
     * @param obj  the other object.
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof KeyedPaint)) {
            return false;
        }
        KeyedPaint that = (KeyedPaint) obj;
        if (!ObjectUtilities.equal(this.key, that.key)) {
            return false;
        }

        if (!PaintUtilities.equal(this.paint, that.paint)) {
            return false;
        }

        return true;
    }
    
}
