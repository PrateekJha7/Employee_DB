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

import java.beans.PropertyChangeEvent;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;

/**
 * An XY chart where both the x and y-axes are numerical.
 */
public abstract class NumericalXYChart extends AbstractXYChart {

    /**
     * The x-axis scale type.  Note that a property editor for this type is 
     * already registered by the {@link AbstractXYChart} class.
     */
    private AxisScale xAxisScale;

    /**
     * Default constructor.
     */
    public NumericalXYChart() {
        super();
        this.xAxisScale = AxisScale.FLOAT;
    }
    
    /**
     * Returns the flag that controls whether or not the auto range calculation
     * is forced to include zero.
     * 
     * @return A boolean.
     * 
     * @see #setXAxisAutoRangeIncludesZero(boolean)
     */
    public boolean getXAxisAutoRangeIncludesZero() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot == null) {
            return false;
        }
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        return xAxis.getAutoRangeIncludesZero();
    }
    
    /**
     * Sets the flag that controls whether or not the auto range calculation
     * is forced to include zero and sends a {@link PropertyChangeEvent} for
     * the <code>xAxisAutoRangeIncludesZero</code> property to all registered
     * listeners.
     * 
     * @param include  the new flag value.
     * 
     * @see #getXAxisAutoRangeIncludesZero()
     */
    public void setXAxisAutoRangeIncludesZero(boolean include) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot == null) {
            return;
        }
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        boolean old = xAxis.getAutoRangeIncludesZero();
        xAxis.setAutoRangeIncludesZero(include);
        firePropertyChange("xAxisAutoRangeIncludesZero", old, include);
    }
    
    /**
     * Returns the scale type for the x-axis.
     * 
     * @return The scale type for the x-axis.
     * 
     * @see #setXAxisScale(AxisScale)
     */
    public AxisScale getXAxisScale() {
        return this.xAxisScale;
    }

    /**
     * Sets the scale type for the x-axis and sends a 
     * {@link PropertyChangeEvent} for the <code>xAxisScale</code> property to
     * all registered listeners.
     * 
     * @param scale  the scale type (<code>null</code> not permitted).
     * 
     * @see #getXAxisScale()
     */
    public void setXAxisScale(AxisScale scale) {
        if (scale == null) {
            throw new IllegalArgumentException("Null 'scale' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            AxisScale old = this.xAxisScale;
            ValueAxis axis = plot.getDomainAxis();
            if (AxisScale.INTEGER.equals(scale)) {
                axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            }
            else if (AxisScale.FLOAT.equals(scale)) {
                axis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
            }
            firePropertyChange("xAxisScsale", old, scale);
        }
    }

}
