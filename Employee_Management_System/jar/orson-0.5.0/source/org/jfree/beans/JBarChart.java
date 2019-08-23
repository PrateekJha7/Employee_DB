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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

/**
 * A JavaBean that displays a bar chart.
 */
public class JBarChart extends AbstractCategoryChart {
   
    /**
     * Creates a new bar chart bean.
     */
    public JBarChart() {
        super();
    }

    /**
     * Creates a default chart.
     * 
     * @return The default chart.
     */
    protected JFreeChart createDefaultChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(5.0, "Series 1", "Category A");
        dataset.setValue(6.0, "Series 1", "Category B");
        dataset.setValue(7.0, "Series 2", "Category A");
        dataset.setValue(4.0, "Series 2", "Category B");
        dataset.setValue(3.0, "Series 3", "Category A");
        dataset.setValue(9.0, "Series 3", "Category B");
        JFreeChart chart = ChartFactory.createBarChart("JBarChart - Title", 
                "Category", "Value", dataset, PlotOrientation.VERTICAL, true, 
                true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(4, 4, 4, 4));
        
        CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryMargin(0.05);
        
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemMargin(0.0);
        renderer.setDrawBarOutline(false);
        return chart;
    }
    
    /**
     * Returns the dataset used by the chart.
     * 
     * @return The dataset (possibly <code>null</code>).
     * 
     * @see #setDataset(CategoryDataset)
     */
    public CategoryDataset getDataset() {
        CategoryDataset result = null;
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
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
    public void setDataset(CategoryDataset dataset) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            CategoryDataset old = plot.getDataset();
            plot.setDataset(dataset);
            firePropertyChange("dataset", old, dataset);
        }
    }
    
    /**
     * Returns the flag that controls whether or not the bar outlines are
     * drawn.
     * 
     * @return A boolean.
     * 
     * @see #setBarOutlineVisible(boolean)
     */
    public boolean isBarOutlineVisible() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot == null) {
            return false;
        }
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        if (renderer == null) {
            return false;
        }
        return renderer.isDrawBarOutline();
    }
    
    /**
     * Sets the flag that controls whether or not the bar outlines are drawn 
     * and fires a {@link PropertyChangeEvent} for the 
     * <code>barOutlineVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isBarOutlineVisible()
     */
    public void setBarOutlineVisible(boolean visible) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot == null) {
            return;
        }
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        if (renderer == null) {
            return;
        }
        boolean old = renderer.isDrawBarOutline();
        renderer.setDrawBarOutline(visible);
        firePropertyChange("barOutlineVisible", old, visible);
    }

    /**
     * Returns the overall margin between bars within each category.
     * 
     * @return The item margin.
     * 
     * @see #setBarItemMargin(double)
     */
    public double getBarItemMargin() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot == null) {
            return 0.0;
        }
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        if (renderer == null) {
            return 0.0;
        }
        return renderer.getItemMargin();
    }
    
    /**
     * Sets the margin between items within each category and fires a 
     * {@link PropertyChangeEvent} for the <code>barItemMargin</code> property.
     * 
     * @param margin  the new margin value.
     * 
     * @see #getBarItemMargin()
     */
    public void setBarItemMargin(double margin) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot == null) {
            return;
        }
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        if (renderer == null) {
            return;
        }
        double old = renderer.getItemMargin();
        renderer.setItemMargin(margin);
        firePropertyChange("barItemMargin", old, margin);
    }
    
    /**
     * Returns the base value for the bars.
     * 
     * @return The item margin.
     * 
     * @see #setBarBaseValue(double)
     */
    public double getBarBaseValue() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot == null) {
            return 0.0;
        }
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        if (renderer == null) {
            return 0.0;
        }
        return renderer.getBase();
    }
    
    /**
     * Sets the bar base value and fires a {@link PropertyChangeEvent} for 
     * the <code>barBaseValue</code> property.
     * 
     * @param base  the new base value.
     * 
     * @see #getBarBaseValue()
     */
    public void setBarBaseValue(double base) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot == null) {
            return;
        }
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        if (renderer == null) {
            return;
        }
        double old = renderer.getBase();
        renderer.setBase(base);
        firePropertyChange("barBaseValue", old, base);
    }

}
