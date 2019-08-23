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

import org.jfree.beans.AbstractCategoryChart;
import org.jfree.data.category.CategoryDataset;

/**
 * An event object that carries information about a mouse click on an item 
 * in an {@link AbstractCategoryChart}.
 */
public class CategoryItemClickEvent extends EventObject {
    
    /** The dataset. */
    private CategoryDataset dataset;
    
    /** The row key. */
    private Comparable rowKey;
    
    /** The column key. */
    private Comparable columnKey;
    
    /**
     * Creates a new section click event.
     * 
     * @param source  the event source (typically the chart).
     * @param dataset  the dataset.
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     */
    public CategoryItemClickEvent(Object source, CategoryDataset dataset,
            Comparable rowKey, Comparable columnKey) {
        super(source);
        this.dataset = dataset;
        this.rowKey = rowKey;
        this.columnKey = columnKey;
    }
    
    /**
     * Returns the dataset.
     * 
     * @return The dataset.
     */
    public CategoryDataset getDataset() {
        return this.dataset;
    }
    
    /**
     * Returns the row key.
     * 
     * @return The row key.
     */
    public Comparable getRowKey() {
        return this.rowKey;
    }
    
    /**
     * Returns the column key.
     * 
     * @return The column key.
     */
    public Comparable getColumnKey() {
        return this.columnKey;
    }
    
    /**
     * Returns a string that represents the state of this instance (suitable
     * for debugging purposes).
     * 
     * @return A string.
     */
    public String toString() {
        return "CategoryItemClickEvent: rowKey=" + this.rowKey + ", columnKey="
                + this.columnKey + ", dataset=" + this.dataset;
    }

}
