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

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * A GUI for editing a pie dataset.
 */
public class PieDatasetGUI extends JPanel implements TableModelListener {

    class MyPieDatasetTableModel extends AbstractTableModel 
            implements TableModel {

        /* (non-Javadoc)
         * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                if (aValue == null) {
                    throw new IllegalArgumentException("'aValue' is null.");
                }
                Comparable key = aValue.toString();
                int keyIndex = this.dataset.getIndex(key);
                Comparable oldKey = this.dataset.getKey(rowIndex);
                if (keyIndex == rowIndex) {
                    return;  // nothing to do
                } 
                if (keyIndex >= 0) {
                    throw new IllegalArgumentException("Key already exists!");
                }
                Number value = this.dataset.getValue(rowIndex);
                this.dataset.insertValue(rowIndex, key, value);
                this.dataset.remove(oldKey);
                fireTableCellUpdated(rowIndex, columnIndex);
            }
            else if (columnIndex == 1) {
                Comparable key = this.dataset.getKey(rowIndex);
                Double value = Double.valueOf(aValue.toString());
                this.dataset.insertValue(rowIndex, key, value);
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        private DefaultPieDataset dataset;
        
        private int editingRow;
        
        private Comparable newKey;
        
        private Number newValue;
        
        /**
         * Creates a new table model.
         * 
         * @param dataset
         */
        public MyPieDatasetTableModel(DefaultPieDataset dataset) {
            this.dataset = dataset;
            this.editingRow = -1;
        }
        
        public int getColumnCount() {
            return 2;
        }

        /**
         * Returns the current row count.
         * 
         * @return The row count.
         */
        public int getRowCount() {
            int result = this.dataset.getItemCount();
            if (this.editingRow >= 0) {
                result++;
            }
            return result;
        }

        /**
         * Returns a value from the table model.
         * 
         * @param rowIndex  the row index.
         * @param columnIndex  the column index.
         * 
         * @return The value.
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            
            // if there is no editing row, or rowIndex is less than the current
            // editing row, we can just fetch directly from the dataset...
            if (this.editingRow < 0 || rowIndex < this.editingRow) {
                if (columnIndex == 0) {
                    return this.dataset.getKey(rowIndex);
                }
                else if (columnIndex == 1) {
                    return this.dataset.getValue(rowIndex);
                }
            }
            
            // ...otherwise, if rowIndex is the current editing row, we should
            // return the edit values...
            else if (rowIndex == this.editingRow) {
                if (columnIndex == 0) {
                    return this.newKey;
                }
                else if (columnIndex ==1) {
                    return this.newValue;
                }
                
            }
            
            // ...finally, this is the case where there is an editing row, but
            // we're looking for an item further on...
            else {
                if (columnIndex == 0) {
                    return this.dataset.getKey(rowIndex - 1);
                }
                else if (columnIndex == 1) {
                    return this.dataset.getValue(rowIndex - 1);
                }
            }
  
            // we really shouldn't get this far...
            return null;
        }

        
    }

    /**
     * Creates a new instance.
     * 
     * @param dataset  the dataset.
     */
    public PieDatasetGUI(DefaultPieDataset dataset) {
        super(new BorderLayout());
        setBorder(new EmptyBorder(2, 2, 2, 2));
        JTable table = new JTable();
        table.setModel(new MyPieDatasetTableModel(dataset));
        table.getModel().addTableModelListener(this);
        add(new JScrollPane(table));
    }

    /* (non-Javadoc)
     * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
     */
    public void tableChanged(TableModelEvent e) {
        System.out.println(e.toString());
    }
    
    /**
     * Starting point for a test app.
     * 
     * @param args  ignored.
     */
    public static void main(String[] args) {
        JFrame app = new JFrame("PieDataset Editor");
        JSplitPane gui = new JSplitPane();
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 1.0);
        dataset.setValue("B", 3.0);
        dataset.setValue("C", 3.0);
        dataset.setValue("D", 3.0);
        dataset.setValue("E", 3.0);
        dataset.setValue("F", 3.0);
        dataset.setValue("G", 3.0);
        dataset.setValue("H", 3.0);
        dataset.setValue("I", 3.0);
        dataset.setValue("J", 3.0);
        JFreeChart chart = ChartFactory.createPieChart("Pie Chart", dataset, 
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        PieDatasetGUI datasetTable = new PieDatasetGUI(dataset);
        gui.add(chartPanel, JSplitPane.TOP);
        gui.add(datasetTable, JSplitPane.BOTTOM);
        app.getContentPane().add(gui);
        app.pack();
        app.setVisible(true);
    }
}
