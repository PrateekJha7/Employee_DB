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
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 * A JavaBean that displays a line chart.
 */
public class JLineChart extends NumericalXYChart {
    
    /**
     * Creates a new pie chart bean.
     */
    public JLineChart() {
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
        JFreeChart chart = ChartFactory.createXYLineChart("JLineChart - Title", 
                "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(4, 4, 4, 4));
        
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        r.setUseFillPaint(true);
        r.setBaseFillPaint(Color.white);
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
     * Sets the dataset used by the chart and sends a 
     * {@link PropertyChangeEvent} for the <code>dataset</code> property to all
     * registered listeners.
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
    
    /**
     * Returns <code>true</code> if a shape is drawn to indicate each data 
     * item, and <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setShapesVisible(boolean)
     */
    public boolean getShapesVisible() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot == null) {
            return false;
        }
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        if (r == null)  {
            return false;
        }
        return r.getBaseShapesVisible();
    }
    
    /**
     * Sets a flag that controls whether or not shapes are drawn to highlight
     * each data item and sends a {@link PropertyChangeEvent} for the 
     * <code>shapesVisible</code> property to all registered listeners.
     * 
     * @param visible  the new flag value.
     * 
     * @see #getShapesVisible()
     */
    public void setShapesVisible(boolean visible) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot == null) {
            return;
        }
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        if (r == null)  {
            return;
        }
        boolean old = r.getBaseShapesVisible();
        r.setBaseShapesVisible(visible);       
        firePropertyChange("shapesVisible", old, visible);
    }
    
}
