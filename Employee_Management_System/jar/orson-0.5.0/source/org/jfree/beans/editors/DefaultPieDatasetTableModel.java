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

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.jfree.data.general.DefaultPieDataset;

/**
 * A TableModel based on a DefaultPieDataset.  Using this to experiment with
 * editing.
 */
public class DefaultPieDatasetTableModel extends AbstractTableModel 
        implements TableModel {

    private DefaultPieDataset dataset;
    
    private int insertionRow = -1;
    
    /** 
     * If this is true, the model has a column zero containing a boolean flag
     * indicating whether or not this is the current row for editing.
     */
    private boolean hasEditingColumn;
    
    /**
     * Creates a new instance.
     * 
     * @param dataset  the underlying dataset.
     */
    public DefaultPieDatasetTableModel(DefaultPieDataset dataset) {
        if (dataset == null) {
            throw new IllegalArgumentException("Null 'dataset' argument.");
        }
        this.dataset = dataset;   
    }

    public int getColumnCount() {
        int result = 2;
        if (this.hasEditingColumn) {
            result++;
        }
        return result;
    }

    /**
     * Returns the number of rows in the table, which is equal to the number
     * of items in the dataset, plus an editing row if it is being used.
     * 
     * @return The row count.
     */
    public int getRowCount() {
        int result = this.dataset.getItemCount();
        if (this.insertionRow >= 0) {
            result++;
        }
        return result;
    }

    /**
     * Returns the item at the specified row and column.  We return a value 
     * from the underlying dataset.
     * 
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The value.
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return this.dataset.getKey(rowIndex);
        }
        else if (columnIndex == 1) {
            return this.dataset.getValue(rowIndex);
        }
        else if (columnIndex == 2) {
//            if (this.hasEditingColumn) {
//                return this.dataset.getValue(rowIndex;)
//            }
        }
            return null;
        
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            Comparable key;
            if (!(value instanceof Comparable)) {
                key = value.toString();
            }
            else {
                key = (Comparable) value;
            }
            Comparable oldKey = this.dataset.getKey(rowIndex);
            Number n = this.dataset.getValue(oldKey);
            this.dataset.remove(oldKey);
            this.dataset.setValue(key, n);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
//        else if (columnIndex == 1) {
//            Comparable key = this.dataset.getKey(rowIndex);
//            Number n = null;
//            if (value instanceof Number) {
//                n = (Number) value;
//            }
//            this.dataset.setValue(key, n);
//            fireTableCellUpdated(rowIndex, columnIndex);
//        }
    }
    
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Key";
        }
        else if (columnIndex == 1) {
            return "Value";
        }
        else {
            return null;
        }
    }
}
