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

package org.jfree.beans.events;

import java.util.EventObject;

import org.jfree.data.general.Dataset;

/**
 * An event object that carries information about a mouse click on a series
 * in a legend.
 */
public class LegendClickEvent extends EventObject {
    
    /** A reference to the source dataset. */
    private Dataset dataset;
    
    /** The series key. */
    private Comparable key;
    
    /**
     * Creates a new legend click event.
     * 
     * @param source  the event source.
     * @param dataset  the dataset.
     * @param key  the series key.
     */
    public LegendClickEvent(Object source, Dataset dataset, Comparable key) {
        super(source);
        this.dataset = dataset;
        this.key = key;
    }
    
    /**
     * Returns the dataset.
     * 
     * @return The dataset.
     */
    public Dataset getDataset() {
        return this.dataset;
    }
    
    /**
     * Returns the series key.
     * 
     * @return The series key.
     */
    public Comparable getSeriesKey() {
        return this.key;
    }
    
    /**
     * Returns a string that represents the state of this instance (suitable
     * for debugging purposes).
     * 
     * @return A string.
     */
    public String toString() {
        return "LegendClickEvent: seriesKey=" + this.key 
                + ", dataset=" + this.dataset;
    }

}
