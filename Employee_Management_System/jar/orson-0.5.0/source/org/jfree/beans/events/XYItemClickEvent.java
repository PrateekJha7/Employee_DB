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

import org.jfree.beans.AbstractXYChart;
import org.jfree.data.xy.XYDataset;

/**
 * An event object that carries information about a mouse click on an item 
 * in an {@link AbstractXYChart}.
 */
public class XYItemClickEvent extends EventObject {
    
    /** The dataset. */
    private XYDataset dataset;
    
    /** The series index. */
    private int seriesIndex;
    
    /** The item index. */
    private int itemIndex;
    
    /**
     * Creates a new XY item click event.
     * 
     * @param source  the event source (typically the chart).
     * @param dataset  the dataset.
     * @param seriesIndex  the row key.
     * @param itemIndex  the column key.
     */
    public XYItemClickEvent(Object source, XYDataset dataset,
            int seriesIndex, int itemIndex) {
        super(source);
        this.dataset = dataset;
        this.seriesIndex = seriesIndex;
        this.itemIndex = itemIndex;
    }
    
    /**
     * Returns the dataset.
     * 
     * @return The dataset.
     */
    public XYDataset getDataset() {
        return this.dataset;
    }
    
    /**
     * Returns the series index.
     * 
     * @return The series index.
     */
    public int getSeriesIndex() {
        return this.seriesIndex;
    }
    
    /**
     * Returns the item index.
     * 
     * @return The item index.
     */
    public int getItemIndex() {
        return this.itemIndex;
    }
    
    /**
     * Returns a string that represents the state of this instance (suitable
     * for debugging purposes).
     * 
     * @return A string.
     */
    public String toString() {
        return "XYItemClickEvent: seriesIndex=" + this.seriesIndex 
                + ", itemIndex=" + this.itemIndex + ", dataset=" + this.dataset;
    }

}
