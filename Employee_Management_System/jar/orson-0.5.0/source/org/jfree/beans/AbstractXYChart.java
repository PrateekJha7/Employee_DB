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

import java.awt.Font;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyEditorManager;
import java.text.NumberFormat;

import org.jfree.beans.editors.AxisScaleEditor;
import org.jfree.beans.events.XYItemClickEvent;
import org.jfree.beans.events.XYItemClickListener;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;

/**
 * A base class for beans that use the {@link XYPlot} class.
 */
public abstract class AbstractXYChart extends AbstractChart {
    
    static {
        PropertyEditorManager.registerEditor(AxisScale.class, 
                AxisScaleEditor.class);
    }

    /** The scale for the y-axis. */
    private AxisScale yAxisScale;
    
    /**
     * Creates a new instance.
     */
    public AbstractXYChart() {
        super();
        this.yAxisScale = AxisScale.FLOAT;
    }
    
    /**
     * Returns the orientation for the plot.
     * 
     * @return The orientation.
     * 
     * @see #setOrientation(PlotOrientation)
     */
    public PlotOrientation getOrientation() {
        PlotOrientation result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getOrientation();
        }
        return result;        
    }
    
    /**
     * Sets the orientation for the plot and fires a 
     * {@link PropertyChangeEvent} for the <code>orientation</code> property.
     * 
     * @param orientation  the orientation (<code>null</code> not permitted).
     * 
     * @see #setOrientation(PlotOrientation)
     */
    public void setOrientation(PlotOrientation orientation) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            PlotOrientation old = plot.getOrientation();
            plot.setOrientation(orientation);
            firePropertyChange("orientation", old, orientation);
        }        
    }
    
    /**
     * Returns the x-axis label.
     * 
     * @return The x-axis label.
     * 
     * @see #setXAxisLabel(String)
     */
    public String getXAxisLabel() {
        String result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getDomainAxis().getLabel();
        }
        return result;
    }
    
    /**
     * Sets the x-axis label and fires a {@link PropertyChangeEvent} for the
     * <code>xAxisLabel</code> property.
     * 
     * @param label  the new label.
     * 
     * @see #getXAxisLabel()
     */
    public void setXAxisLabel(String label) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            String old = axis.getLabel();
            axis.setLabel(label);
            firePropertyChange("xAxisLabel", old, label);
        }                
    }
    
    /**
     * Returns the font for the x-axis label.
     * 
     * @return The font for the x-axis label.
     * 
     * @see #setXAxisLabelFont(Font)
     */
    public Font getXAxisLabelFont() {
        Font result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getDomainAxis().getLabelFont();
        }
        return result;   
    }
    
    /**
     * Sets the font for the x-axis label and fires a 
     * {@link PropertyChangeEvent} for the <code>xAxisLabelFont</code> 
     * property.
     * 
     * @param font  the font (<code>null</code> not permitted).
     * 
     * @see #getXAxisLabelFont()
     */
    public void setXAxisLabelFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            Font old = axis.getLabelFont();
            axis.setLabelFont(font);
            firePropertyChange("xAxisLabelFont", old, font);
        }
    }

    /**
     * Returns the paint for the x-axis label.
     * 
     * @return The paint for the x-axis label.
     * 
     * @see #setXAxisLabelPaint(Paint)
     */
    public Paint getXAxisLabelPaint() {
        Paint result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getDomainAxis().getLabelPaint();
        }
        return result;   
    }
    
    /**
     * Sets the paint for the x-axis label and fires a 
     * {@link PropertyChangeEvent} for the <code>xAxisLabelPaint</code> 
     * property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getXAxisLabelPaint()
     */
    public void setXAxisLabelPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            Paint old = axis.getLabelPaint();
            axis.setLabelPaint(paint);
            firePropertyChange("xAxisLabelPaint", old, paint);
        }
    }

    /**
     * Returns <code>true</code> if the x-axis is inverted, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setXAxisInverted(boolean)
     */
    public boolean isXAxisInverted() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().isInverted();
        }
        return false;
    }

    /**
     * Sets a flag that controls whether or not the x-axis is inverted and
     * fires a {@link PropertyChangeEvent} for the <code>xAxisInverted</code>
     * property.
     * 
     * @param inverted  the new flag value.
     * 
     * @see #isXAxisInverted()
     */
    public void setXAxisInverted(boolean inverted) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            boolean old = axis.isInverted();
            axis.setInverted(inverted);
            firePropertyChange("xAxisInverted", old, inverted);
        }                
    }
    
    /**
     * Returns the lower margin for the x-axis.
     * 
     * @return The lower margin.
     * 
     * @see #setXAxisLowerMargin(double)
     */
    public double getXAxisLowerMargin() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().getLowerMargin();
        }
        return -1.0;
    }
    
    /**
     * Sets the lower margin for the x-axis and fires a 
     * {@link PropertyChangeEvent} for the <code>xAxisLowerMargin</code>
     * property.
     * 
     * @param margin  the margin.
     * 
     * @see #getXAxisLowerMargin()
     */
    public void setXAxisLowerMargin(double margin) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            double old = axis.getLowerMargin();
            axis.setLowerMargin(margin);
            firePropertyChange("xAxisLowerMargin", old, margin);
        }                
    }
    
    /**
     * Returns the upper margin for the x-axis.
     * 
     * @return The upper margin for the x-axis.
     * 
     * @see #setXAxisUpperMargin(double)
     */
    public double getXAxisUpperMargin() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().getUpperMargin();
        }
        return -1.0;
    }

    /**
     * Sets the upper margin for the x-axis and fires a 
     * {@link PropertyChangeEvent} for the <code>xAxisUpperMargin</code> 
     * property.
     * 
     * @param margin  the margin.
     * 
     * @see #getXAxisUpperMargin()
     */
    public void setXAxisUpperMargin(double margin) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            double old = axis.getUpperMargin();
            axis.setUpperMargin(margin);
            firePropertyChange("xAxisUpperMargin", old, margin);
        }                
    }
    
    /**
     * Returns <code>true</code> if the x-axis gridlines are visible, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setXAxisGridlinesVisible(boolean)
     */
    public boolean isXAxisGridlinesVisible() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.isDomainGridlinesVisible();
        }
        return false;
    }

    /**
     * Sets a flag that controls whether or not the x-axis gridlines are
     * drawn and fires a {@link PropertyChangeEvent} for the 
     * <code>xAxisGridlinesVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isXAxisGridlinesVisible()
     */
    public void setXAxisGridlinesVisible(boolean visible) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            boolean old = plot.isDomainGridlinesVisible();
            plot.setDomainGridlinesVisible(visible);
            firePropertyChange("xAxisGridlinesVisible", old, visible);
        }                
    }
    
    /**
     * Returns the font for the x-axis tick labels.
     * 
     * @return The font for the x-axis tick labels.
     * 
     * @see #setXAxisTickLabelFont(Font)
     */
    public Font getXAxisTickLabelFont() {
        Font result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getDomainAxis().getTickLabelFont();
        }
        return result;   
    }
    
    /**
     * Sets the font for the x-axis tick labels and fires a 
     * {@link PropertyChangeEvent} for the <code>xAxisTickLabelFont</code>
     * property.
     * 
     * @param font  the font (<code>null</code> not permitted).
     * 
     * @see #getXAxisTickLabelFont()
     */
    public void setXAxisTickLabelFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            Font old = axis.getTickLabelFont();
            axis.setTickLabelFont(font);
            firePropertyChange("xAxisTickLabelFont", old, font);
        }
    }

    /**
     * Returns the paint for the x-axis tick labels.
     * 
     * @return The paint for the x-axis tick labels.
     * 
     * @see #setXAxisTickLabelPaint(Paint)
     */
    public Paint getXAxisTickLabelPaint() {
        Paint result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getDomainAxis().getTickLabelPaint();
        }
        return result;   
    }
    
    /**
     * Sets the paint for the x-axis tick labels and fires a 
     * {@link PropertyChangeEvent} for the <code>xAxisTickLabelPaint</code>
     * property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getXAxisTickLabelPaint()
     */
    public void setXAxisTickLabelPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            Paint old = axis.getTickLabelPaint();
            axis.setTickLabelPaint(paint);
            firePropertyChange("xAxisTickLabelPaint", old, paint);
        }
    }
    
    /**
     * Returns the y-axis label.
     * 
     * @return The y-axis label.
     * 
     * @see #setYAxisLabel(String)
     */
    public String getYAxisLabel() {
        String result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getRangeAxis().getLabel();
        }
        return result;
    }
    
    /**
     * Sets the y-axis label and fires a {@link PropertyChangeEvent} for the
     * <code>yAxisLabel</code> property.
     * 
     * @param label  the label.
     * 
     * @see #getYAxisLabel()
     */
    public void setYAxisLabel(String label) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            String old = axis.getLabel();
            axis.setLabel(label);
            firePropertyChange("yAxisLabel", old, label);
        }                
    }

    /**
     * Returns the font for the y-axis label.
     * 
     * @return The font for the y-axis label.
     * 
     * @see #setYAxisLabelFont(Font)
     */
    public Font getYAxisLabelFont() {
        Font result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getRangeAxis().getLabelFont();
        }
        return result;   
    }
    
    /**
     * Sets the font for the y-axis label and fires a 
     * {@link PropertyChangeEvent} for the <code>yAxisLabelFont</code>
     * property.
     * 
     * @param font  the font (<code>null</code> not permitted).
     * 
     * @see #getYAxisLabelFont()
     */
    public void setYAxisLabelFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            Font old = axis.getLabelFont();
            axis.setLabelFont(font);
            firePropertyChange("yAxisLabelFont", old, font);
        }
    }

    /**
     * Returns the paint for the y-axis label.
     * 
     * @return The paint for the y-axis label.
     * 
     * @see #setYAxisLabelPaint(Paint)
     */
    public Paint getYAxisLabelPaint() {
        Paint result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getRangeAxis().getLabelPaint();
        }
        return result;   
    }
    
    /**
     * Sets the paint for the y-axis label and fires a 
     * {@link PropertyChangeEvent} for the <code>yAxisLabelPaint</code> 
     * property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getYAxisLabelPaint()
     */
    public void setYAxisLabelPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            Paint old = axis.getLabelPaint();
            axis.setLabelPaint(paint);
            firePropertyChange("yAxisLabelPaint", old, paint);
        }
    }
    
    /**
     * Returns the scale type for the y-axis.
     * 
     * @return The scale type.
     * 
     * @see #setYAxisScale(AxisScale)
     */
    public AxisScale getYAxisScale() {
        return this.yAxisScale;
    }

    /**
     * Sets the scale type for the y-axis and fires a 
     * {@link PropertyChangeEvent} for the <code>yAxisScale</code> property.
     * 
     * @param scale  the scale type.
     * 
     * @see #getYAxisScale()
     */
    public void setYAxisScale(AxisScale scale) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            AxisScale old = this.yAxisScale;
            ValueAxis axis = plot.getRangeAxis();
            if (AxisScale.INTEGER.equals(scale)) {
                axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            }
            else if (AxisScale.FLOAT.equals(scale)) {
                axis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
            }

            firePropertyChange("yAxisScale", old, scale);
        }
    }
    
    /**
     * Returns <code>true</code> if the y-axis is inverted, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setYAxisInverted(boolean)
     */
    public boolean isYAxisInverted() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().isInverted();
        }
        return false;
    }

    /**
     * Sets a flag that controls whether or not the y-axis is inverted and
     * fires a {@link PropertyChangeEvent} for the <code>yAxisInverted</code>
     * property.
     * 
     * @param inverted  the new flag value.
     * 
     * @see #isYAxisInverted()
     */
    public void setYAxisInverted(boolean inverted) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            boolean old = axis.isInverted();
            axis.setInverted(inverted);
            firePropertyChange("yAxisInverted", old, inverted);
        }                
    }
    
    /**
     * Returns the flag that controls whether or not the auto range calculation
     * is forced to include zero.
     * 
     * @return A boolean.
     * 
     * @see #setYAxisAutoRangeIncludesZero(boolean)
     */
    public boolean getYAxisAutoRangeIncludesZero() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot == null) {
            return false;
        }
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        return yAxis.getAutoRangeIncludesZero();
    }
    
    /**
     * Sets the flag that controls whether or not the auto range calculation
     * is forced to include zero, and fires a {@link PropertyChangeEvent} 
     * for the <code>yAxisAutoRangeIncludesZero</code> property.
     * 
     * @param include  the new flag value.
     * 
     * @see #getYAxisAutoRangeIncludesZero()
     */
    public void setYAxisAutoRangeIncludesZero(boolean include) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot == null) {
            return;
        }
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        boolean old = yAxis.getAutoRangeIncludesZero();
        yAxis.setAutoRangeIncludesZero(include);
        firePropertyChange("yAxisAutoRangeIncludesZero", old, include);
    }

    /**
     * Returns the lower margin for the y-axis.
     * 
     * @return The lower margin.
     * 
     * @see #setYAxisLowerMargin(double)
     */
    public double getYAxisLowerMargin() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().getLowerMargin();
        }
        return -1.0;
    }
    
    /**
     * Sets the lower margin for the y-axis and fires a 
     * {@link PropertyChangeEvent} for the <code>yAxisLowerMargin</code>
     * property.
     * 
     * @param margin  the margin.
     * 
     * @see #getYAxisLowerMargin()
     */
    public void setYAxisLowerMargin(double margin) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            double old = axis.getLowerMargin();
            axis.setLowerMargin(margin);
            firePropertyChange("yAxisLowerMargin", old, margin);
        }                
    }
    
    /**
     * Returns the upper margin for the y-axis.
     * 
     * @return The upper margin for the y-axis.
     * 
     * @see #setYAxisUpperMargin(double)
     */
    public double getYAxisUpperMargin() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().getUpperMargin();
        }
        return -1.0;
    }

    /**
     * Sets the upper margin for the y-axis and fires a 
     * {@link PropertyChangeEvent} for the <code>yAxisUpperMargin</code> 
     * property.
     * 
     * @param margin  the margin.
     * 
     * @see #getYAxisUpperMargin()
     */
    public void setYAxisUpperMargin(double margin) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            double old = axis.getUpperMargin();
            axis.setUpperMargin(margin);
            firePropertyChange("yAxisUpperMargin", old, margin);
        }                
    }
    
    /**
     * Returns <code>true</code> if the y-axis gridlines are visible, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setYAxisGridlinesVisible(boolean)
     */
    public boolean isYAxisGridlinesVisible() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.isRangeGridlinesVisible();
        }
        return false;
    }

    /**
     * Sets a flag that controls whether or not the y-axis gridlines are
     * drawn and fires a {@link PropertyChangeEvent} for the
     * <code>yAxisGridlinesVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isYAxisGridlinesVisible()
     */
    public void setYAxisGridlinesVisible(boolean visible) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            boolean old = plot.isRangeGridlinesVisible();
            plot.setRangeGridlinesVisible(visible);
            firePropertyChange("yAxisGridlinesVisible", old, visible);
        }                
    }
    
    /**
     * Returns the grid line paint for the gridlines perpendicular to the
     * x-axis.
     * 
     * @return The paint.
     * 
     * @see #setXAxisGridlinePaint(Paint)
     */
    public Paint getXAxisGridlinePaint() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainGridlinePaint();
        }
        return null;
    }
    
    /**
     * Sets the paint for the x-axis gridlines and fires a 
     * {@link PropertyChangeEvent} for the <code>xAxisGridlinePaint</code>
     * property.
     * 
     * @param paint  the paint.
     * 
     * @see #getXAxisGridlinePaint()
     */
    public void setXAxisGridlinePaint(Paint paint) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            Paint old = plot.getDomainGridlinePaint();
            plot.setDomainGridlinePaint(paint);
            firePropertyChange("xAxisGridlinePaint", old, paint);
        }
    }
    
    /**
     * Returns the y-axis gridline paint.
     * 
     * @return The y-axis gridline paint.
     * 
     * @see #setYAxisGridlinePaint(Paint)
     */
    public Paint getYAxisGridlinePaint() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeGridlinePaint();
        }
        return null;
    }
    
    /**
     * Sets the y-axis gridline paint and fires a {@link PropertyChangeEvent}
     * for the <code>yAxisGridlinePaint</code> property.
     * 
     * @param paint  the paint.
     * 
     * @see #getYAxisGridlinePaint()
     */
    public void setYAxisGridlinePaint(Paint paint) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            Paint old = plot.getRangeGridlinePaint();
            plot.setRangeGridlinePaint(paint);
            firePropertyChange("yAxisGridlinePaint", old, paint);
        }
    }
    
    /**
     * Returns the font for the y-axis tick labels.
     * 
     * @return The font for the y-axis tick labels.
     * 
     * @see #setYAxisTickLabelFont(Font)
     */
    public Font getYAxisTickLabelFont() {
        Font result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getRangeAxis().getTickLabelFont();
        }
        return result;   
    }
    
    /**
     * Sets the font for the y-axis tick labels and fires a 
     * {@link PropertyChangeEvent} for the <code>yAxisTickLabelFont</code>
     * property.
     * 
     * @param font  the font (<code>null</code> not permitted).
     * 
     * @see #getYAxisTickLabelFont()
     */
    public void setYAxisTickLabelFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            Font old = axis.getTickLabelFont();
            axis.setTickLabelFont(font);
            firePropertyChange("yAxisTickLabelFont", old, font);
        }
    }

    /**
     * Returns the paint for the y-axis tick labels.
     * 
     * @return The paint for the y-axis tick labels.
     * 
     * @see #setYAxisTickLabelPaint(Paint)
     */
    public Paint getYAxisTickLabelPaint() {
        Paint result = null;
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getRangeAxis().getTickLabelPaint();
        }
        return result;   
    }
    
    /**
     * Sets the paint for the y-axis tick labels and fires a 
     * {@link PropertyChangeEvent} for the <code>yAxisTickLabelPaint</code>
     * property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getYAxisTickLabelPaint()
     */
    public void setYAxisTickLabelPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            Paint old = axis.getTickLabelPaint();
            axis.setTickLabelPaint(paint);
            firePropertyChange("yAxisTickLabelPaint", old, paint);
        }
    }
    
    /**
     * Returns the permitted axis locations for the x-axis.
     * 
     * @return The axis location.
     * 
     * @see #setXAxisLocation(AxisLocation)
     */
    public AxisLocation getXAxisLocation() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxisLocation();
        }
        return null;                
    }
    
    /**
     * Sets the axis location for the x-axis and fires a 
     * {@link PropertyChangeEvent} for the <code>xAxisLocation</code> argument.
     * 
     * @param location  the location (<code>null</code> not permitted).
     * 
     * @see #getXAxisLocation()
     */
    public void setXAxisLocation(AxisLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("Null 'location' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            AxisLocation old = plot.getDomainAxisLocation();
            plot.setDomainAxisLocation(location);
            firePropertyChange("xAxisLocation", old, location);
        }
    }
    
    /**
     * Returns the permitted axis locations for the y-axis.
     * 
     * @return The axis location.
     * 
     * @see #setYAxisLocation(AxisLocation)
     */
    public AxisLocation getYAxisLocation() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxisLocation();
        }
        return null;                
    }
    
    /**
     * Sets the axis location for the y-axis and fires a 
     * {@link PropertyChangeEvent} for the <code>yAxisLocation</code>
     * property.
     * 
     * @param location  the location (<code>null</code> not permitted).
     * 
     * @see #getYAxisLocation()
     */
    public void setYAxisLocation(AxisLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("Null 'location' argument.");
        }
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            AxisLocation old = plot.getRangeAxisLocation();
            plot.setRangeAxisLocation(location);
            firePropertyChange("yAxisLocation", old, location);
        }
    }
    
    /**
     * Returns the format string for the item tool tips.
     * 
     * @return The format string.
     * 
     * @see #setToolTipFormat(String)
     */
    public String getToolTipFormat() {
        XYPlot p = (XYPlot) this.chart.getPlot();
        if (p == null) {
            return "";
        }
        XYItemRenderer r = p.getRenderer();
        if (r == null) {
            return "";
        }
        StandardXYToolTipGenerator g = (StandardXYToolTipGenerator) 
                r.getBaseToolTipGenerator();
        if (g == null) {
            return "";
        }
        return g.getFormatString();
    }
    
    /**
     * Sets the format string for the section tool tips and fires a 
     * {@link PropertyChangeEvent} for the <code>toolTipFormat</code> property.
     * 
     * @param format  the format string.
     * 
     * @see #getToolTipFormat()
     */
    public void setToolTipFormat(String format) {
        XYPlot p = (XYPlot) this.chart.getPlot();
        if (p == null) {
            return;
        }
        XYItemRenderer r = p.getRenderer();
        if (r == null) {
            return;
        }
        if (format.equals("")) {
            r.setBaseToolTipGenerator(null);
        }
        else {
            r.setBaseToolTipGenerator(new StandardXYToolTipGenerator(format, 
                    NumberFormat.getInstance(), NumberFormat.getInstance()));   
        }
        // FIXME: what to use for the oldValue
        firePropertyChange("toolTipFormat", null, format);
    }
    
    /**
     * Returns a flag that controls whether or not an arrow-head is displayed
     * at the positive end of the x-axis.
     * 
     * @return A boolean.
     * 
     * @see #setXAxisPositiveArrowVisible(boolean)
     */
    public boolean isXAxisPositiveArrowVisible() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().isPositiveArrowVisible();
        }
        return false;                
    }
    
    /**
     * Sets the flag that controls whether or not an arrow-head is displayed
     * at the positive end of the y-axis and fires a {@link PropertyChangeEvent} 
     * for the <code>yAxisPositiveArrowVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isYAxisPositiveArrowVisible()
     */
    public void setXAxisPositiveArrowVisible(boolean visible) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            boolean old = axis.isPositiveArrowVisible();
            axis.setPositiveArrowVisible(visible);
            firePropertyChange("xAxisPositiveArrowVisible", old, visible);
        }                
    }

    /**
     * Returns a flag that controls whether or not an arrow-head is displayed
     * at the negative end of the x-axis.
     * 
     * @return A boolean.
     * 
     * @see #setXAxisNegativeArrowVisible(boolean)
     */
    public boolean isXAxisNegativeArrowVisible() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().isNegativeArrowVisible();
        }
        return false;                
    }
    
    /**
     * Sets the flag that controls whether or not an arrow-head is displayed
     * at the negative end of the x-axis and fires a {@link PropertyChangeEvent} 
     * for the <code>xAxisNegativeArrowVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isXAxisNegativeArrowVisible()
     */
    public void setXAxisNegativeArrowVisible(boolean visible) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getDomainAxis();
            boolean old = axis.isNegativeArrowVisible();
            axis.setNegativeArrowVisible(visible);
            firePropertyChange("xAxisNegativeArrowVisible", old, visible);
        }                
    }

    /**
     * Returns a flag that controls whether or not an arrow-head is displayed
     * at the positive end of the y-axis.
     * 
     * @return A boolean.
     * 
     * @see #setYAxisPositiveArrowVisible(boolean)
     */
    public boolean isYAxisPositiveArrowVisible() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().isPositiveArrowVisible();
        }
        return false;                
    }
    
    /**
     * Sets the flag that controls whether or not an arrow-head is displayed
     * at the positive end of the y-axis and fires a {@link PropertyChangeEvent} 
     * for the <code>yAxisPositiveArrowVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isYAxisPositiveArrowVisible()
     */
    public void setYAxisPositiveArrowVisible(boolean visible) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            boolean old = axis.isPositiveArrowVisible();
            axis.setPositiveArrowVisible(visible);
            firePropertyChange("yAxisPositiveArrowVisible", old, visible);
        }                
    }

    /**
     * Returns a flag that controls whether or not an arrow-head is displayed
     * at the negative end of the y-axis.
     * 
     * @return A boolean.
     * 
     * @see #setYAxisNegativeArrowVisible(boolean)
     */
    public boolean isYAxisNegativeArrowVisible() {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().isNegativeArrowVisible();
        }
        return false;                
    }
    
    /**
     * Sets the flag that controls whether or not an arrow-head is displayed
     * at the negative end of the y-axis and fires a {@link PropertyChangeEvent} 
     * for the <code>yAxisNegativeArrowVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isYAxisNegativeArrowVisible()
     */
    public void setYAxisNegativeArrowVisible(boolean visible) {
        XYPlot plot = (XYPlot) this.chart.getPlot();
        if (plot != null) {
            ValueAxis axis = plot.getRangeAxis();
            boolean old = axis.isNegativeArrowVisible();
            axis.setNegativeArrowVisible(visible);
            firePropertyChange("yAxisNegativeArrowVisible", old, visible);
        }                
    }

    /**
     * Registers a listener to receive notification of category item clicks.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void addXYItemClickListener(XYItemClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.listeners.add(XYItemClickListener.class, listener);
    }
    
    /**
     * Unregisters a listener so that it no longer receives notification of 
     * category item clicks.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void removeXYItemClickListener(XYItemClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.listeners.remove(XYItemClickListener.class, listener);        
    }
    
    /**
     * Fires a category item click event.
     * 
     * @param event  the event.
     */
    public void fireXYItemClickEvent(XYItemClickEvent event) {
        Object[] listeners = this.listeners.getListeners(
                XYItemClickListener.class);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((XYItemClickListener) listeners[i]).onXYItemClick(event);
        }                
        
    }
    
    /**
     * If the user clicks on the chart, see if that translates into an event
     * that we report...
     * 
     * @param event  the event.
     */
    public void mouseClicked(MouseEvent event) {
        // if no-one is listening, just return...
        Object[] listeners = this.listeners.getListeners(
                XYItemClickListener.class);
        if (listeners.length == 0) {
            super.mouseClicked(event);
        }

        Insets insets = getInsets();
        int x = event.getX() - insets.left;
        int y = event.getY() - insets.top;

        ChartEntity entity = null;
        if (this.info != null) {
            EntityCollection entities = this.info.getEntityCollection();
            if (entities != null) {
                entity = entities.getEntity(x, y);
            }
        }
        if (entity instanceof XYItemEntity) {
            XYItemEntity xyie = (XYItemEntity) entity;
            XYItemClickEvent lce = new XYItemClickEvent(this, 
                    xyie.getDataset(), xyie.getSeriesIndex(), xyie.getItem());
            fireXYItemClickEvent(lce);
        }
        else {
            super.mouseClicked(event);
        }
        
    }

}
