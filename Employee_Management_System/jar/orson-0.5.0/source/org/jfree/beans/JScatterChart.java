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

import java.awt.Color;
import java.beans.PropertyChangeEvent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 * A JavaBean that displays a scatter chart.
 */
public class JScatterChart extends NumericalXYChart {

    /**
     * Creates a new pie chart bean.
     */
    public JScatterChart() {
        super();
    }
    
    /**
     * Creates a default chart.
     * 
     * @return The default chart.
     */
    protected JFreeChart createDefaultChart() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("Series 1");
        s1.add(1.0, 5.0);
        s1.add(2.0, 7.0);
        s1.add(3.0, 3.0);
        s1.add(4.0, 6.0);
        dataset.addSeries(s1);
        XYSeries s2 = new XYSeries("Series 2");
        s2.add(1.2, 3.0);
        s2.add(2.7, 8.0);
        s2.add(3.4, 5.0);
        s2.add(4.3, 1.0);
        dataset.addSeries(s2);
        JFreeChart chart = ChartFactory.createScatterPlot(
                "JScatterChart - Title", "X", "Y", dataset, 
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(4, 4, 4, 4));
        
        return chart;
    }
    
    /**
     * Returns the dataset used by the chart.
     * 
     * @return The dataset (possibly <code>null</code>).
     * 
     * @see #setDataset(XYDataset)
     */
    public XYDataset getDataset() {
        XYDataset result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getDataset();
        }
        return result;
    }
    
    /**
     * Sets the dataset used by the chart and fires a 
     * {@link PropertyChangeEvent} for the <code>dataset</code> property.
     * 
     * @param dataset  the dataset (<code>null</code> permitted).
     * 
     * @see #getDataset()
     */
    public void setDataset(XYDataset dataset) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            XYDataset old = plot.getDataset();
            plot.setDataset(dataset);
            firePropertyChange("dataset", old, dataset);
        }
    }
    
}
