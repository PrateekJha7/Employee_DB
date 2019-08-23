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
import java.text.NumberFormat;

import org.jfree.beans.events.CategoryItemClickEvent;
import org.jfree.beans.events.CategoryItemClickListener;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

/**
 * A base class for chart beans that use a {@link CategoryPlot}.
 */
public abstract class AbstractCategoryChart extends AbstractChart {

    /**
     * Creates a new instance.
     */
    public AbstractCategoryChart() {
        super();
    }
    
    /**
     * Returns the orientation for the plot.
     * 
     * @return The orientation for the plot.
     * 
     * @see #setOrientation(PlotOrientation)
     */
    public PlotOrientation getOrientation() {
        PlotOrientation result = null;
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
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
     * @see #getOrientation()
     */
    public void setOrientation(PlotOrientation orientation) {
        if (orientation == null) {
            throw new IllegalArgumentException("Null 'orientation' argument.");
        }
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            PlotOrientation old = plot.getOrientation();
            plot.setOrientation(orientation);
            firePropertyChange("orientation", old, orientation);
        }        
    }
    
    /**
     * Returns the category axis label.
     * 
     * @return The category axis label (possibly <code>null</code>).
     * 
     * @see #setCategoryAxisLabel(String)
     */
    public String getCategoryAxisLabel() {
        String result = null;
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getDomainAxis().getLabel();
        }
        return result;
    }
    
    /**
     * Sets the category axis label and fires a {@link PropertyChangeEvent} for 
     * the <code>categoryAxisLabel</code> property.
     * 
     * @param label  the label (<code>null</code> permitted).
     * 
     * @see #getCategoryAxisLabel()
     */
    public void setCategoryAxisLabel(String label) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            String old = plot.getDomainAxis().getLabel();
            plot.getDomainAxis().setLabel(label);
            firePropertyChange("categoryAxisLabel", old, label);
        }                
    }
    
    /**
     * Returns the font used for the main label on the category axis.
     * 
     * @return The font.
     * 
     * @see #setCategoryAxisLabelFont(Font)
     */
    public Font getCategoryAxisLabelFont() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().getLabelFont();
        }
        return null;
    }
    
    /**
     * Sets the font used for the main label on the category axis and fires a
     * {@link PropertyChangeEvent} for the <code>categoryAxisLabelFont</code> 
     * property.
     * 
     * @param font  the font (<code>null</code> permitted).
     */
    public void setCategoryAxisLabelFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            Font old = plot.getDomainAxis().getLabelFont();
            plot.getDomainAxis().setLabelFont(font);
            firePropertyChange("categoryAxisLabelFont", old, font);
        }
        
    }

    /**
     * Returns the paint used for the main label on the category axis.
     * 
     * @return The paint.
     * 
     * @see #setCategoryAxisLabelPaint(Paint)
     */
    public Paint getCategoryAxisLabelPaint() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().getLabelPaint();
        }
        return null;
    }
    
    /**
     * Sets the paint used for the main label on the category axis and fires a 
     * {@link PropertyChangeEvent} for the <code>categoryAxisLabelPaint</code> 
     * property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getCategoryAxisLabelPaint()
     */
    public void setCategoryAxisLabelPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            Paint old = plot.getDomainAxis().getLabelPaint();
            plot.getDomainAxis().setLabelPaint(paint);
            firePropertyChange("categoryAxisLabelPaint", old, paint);
        }
    }
    
    /**
     * Returns the lower margin for the category axis.
     * 
     * @return The lower margin.
     * 
     * @see #setCategoryAxisLowerMargin(double)
     */
    public double getCategoryAxisLowerMargin() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().getLowerMargin();
        }
        return -1.0;
    }
    
    /**
     * Sets the lower margin for the category axis and fires a 
     * {@link PropertyChangeEvent} for the <code>categoryAxisLowerMargin</code>
     * property.
     * 
     * @param margin  the margin.
     * 
     * @see #getCategoryAxisLowerMargin()
     */
    public void setCategoryAxisLowerMargin(double margin) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            double old = plot.getDomainAxis().getLowerMargin();
            plot.getDomainAxis().setLowerMargin(margin);
            firePropertyChange("categoryAxisLowerMargin", old, margin);
        }                
    }
    
    /**
     * Returns the upper margin for the category axis.
     * 
     * @return The upper margin for the category axis.
     * 
     * @see #setCategoryAxisUpperMargin(double)
     */
    public double getCategoryAxisUpperMargin() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().getUpperMargin();
        }
        return -1.0;
    }

    /**
     * Sets the upper margin for the category axis and fires a 
     * {@link PropertyChangeEvent} for the <code>categoryAxisUpperMargin</code>
     * property.
     * 
     * @param margin  the margin.
     * 
     * @see #getCategoryAxisUpperMargin()
     */
    public void setCategoryAxisUpperMargin(double margin) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            double old = plot.getDomainAxis().getUpperMargin();
            plot.getDomainAxis().setUpperMargin(margin);
            firePropertyChange("categoryAxisUpperMargin", old, margin);
        }                
    }
    
    /**
     * Returns the margin between categories along the axis.
     * 
     * @return The margin.
     * 
     * @see #setCategoryAxisMargin(double)
     */
    public double getCategoryAxisMargin() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().getCategoryMargin();
        }
        return -1.0;
    }
    
    /**
     * Sets the total space allocated to the margin between categories 
     * along the axis and fires a {@link PropertyChangeEvent} for 
     * the <code>categoryAxisMargin</code> property.
     * 
     * @param margin  the margin.
     * 
     * @see #getCategoryAxisMargin()
     */
    public void setCategoryAxisMargin(double margin) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            double old = plot.getDomainAxis().getCategoryMargin();
            plot.getDomainAxis().setCategoryMargin(margin);
            firePropertyChange("categoryAxisMargin", old, margin);
        }                
    }
    
    /**
     * Returns the label for the value axis.
     * 
     * @return The label for the value axis.
     * 
     * @see #setValueAxisLabel(String)
     */
    public String getValueAxisLabel() {
        String result = null;
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getRangeAxis().getLabel();
        }
        return result;
    }
    
    /**
     * Sets the label for the value axis and fires a 
     * {@link PropertyChangeEvent} for the <code>valueAxisLabel</code> property.
     * 
     * @param label  the label.
     * 
     * @see #getValueAxisLabel()
     */
    public void setValueAxisLabel(String label) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            String old = plot.getRangeAxis().getLabel();
            plot.getRangeAxis().setLabel(label);
            firePropertyChange("valueAxisLabel", old, label);
        }                
    }
    
    /**
     * Returns <code>true</code> if the value axis is inverted, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setValueAxisInverted(boolean)
     */
    public boolean isValueAxisInverted() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().isInverted();
        }
        return false;
    }

    /**
     * Sets a flag that controls whether or not the value axis is inverted and 
     * fires a {@link PropertyChangeEvent} for the 
     * <code>valueAxisInverted</code> property.
     * 
     * @param inverted  the new flag value.
     * 
     * @see #isValueAxisInverted()
     */
    public void setValueAxisInverted(boolean inverted) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            boolean old = plot.getRangeAxis().isInverted();
            plot.getRangeAxis().setInverted(inverted);
            firePropertyChange("valueAxisInverted", old, inverted);
        }                
    }
    
    /**
     * Returns the lower margin for the value axis.
     * 
     * @return The lower margin.
     * 
     * @see #setValueAxisLowerMargin(double)
     */
    public double getValueAxisLowerMargin() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().getLowerMargin();
        }
        return -1.0;
    }
    
    /**
     * Sets the lower margin for the value axis and fires a 
     * {@link PropertyChangeEvent} for the <code>valueAxisLowerMargin</code> 
     * property.
     * 
     * @param margin  the margin.
     * 
     * @see #getValueAxisLowerMargin()
     */
    public void setValueAxisLowerMargin(double margin) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            double old = plot.getRangeAxis().getLowerMargin();
            plot.getRangeAxis().setLowerMargin(margin);
            firePropertyChange("valueAxisLowerMargin", old, margin);
        }                
    }
    
    /**
     * Returns the upper margin for the value axis.
     * 
     * @return The upper margin for the value axis.
     * 
     * @see #setValueAxisUpperMargin(double)
     */
    public double getValueAxisUpperMargin() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().getUpperMargin();
        }
        return -1.0;
    }

    /**
     * Sets the upper margin for the value axis and fires a 
     * {@link PropertyChangeEvent} for the <code>valueAxisUpperMargin</code> 
     * property.
     * 
     * @param margin  the margin.
     * 
     * @see #getValueAxisUpperMargin()
     */
    public void setValueAxisUpperMargin(double margin) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            double old = plot.getRangeAxis().getUpperMargin();
            plot.getRangeAxis().setUpperMargin(margin);
            firePropertyChange("valueAxisUpperMargin", old, margin);
        }                
    }
    
    /**
     * Returns <code>true</code> if the value axis gridlines are visible, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setValueAxisGridlinesVisible(boolean)
     */
    public boolean isValueAxisGridlinesVisible() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.isRangeGridlinesVisible();
        }
        return false;
    }

    /**
     * Sets a flag that controls whether or not the value-axis gridlines are
     * drawn and fires a {@link PropertyChangeEvent} for the 
     * <code>valueAxisGridlinesVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isValueAxisGridlinesVisible()
     */
    public void setValueAxisGridlinesVisible(boolean visible) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            boolean old = plot.isRangeGridlinesVisible();
            plot.setRangeGridlinesVisible(visible);
            firePropertyChange("valueAxisGridlinesVisible", old, visible);
        }                
    }
    
    /**
     * Returns the flag that controls whether or not the value axis draws a
     * line running the length of the axis.
     * 
     * @return A boolean.
     * 
     * @see #setValueAxisLineVisible(boolean)
     */
    public boolean isValueAxisLineVisible() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxis().isAxisLineVisible();
        }
        return false;        
    }
    
    /**
     * Sets the flag that controls whether or not the value axis draws a line
     * running the length of the axis and fires a {@link PropertyChangeEvent} 
     * for the <code>valueAxisLineVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isValueAxisLineVisible()
     */
    public void setValueAxisLineVisible(boolean visible) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            boolean old = plot.getRangeAxis().isAxisLineVisible();
            plot.getRangeAxis().setAxisLineVisible(visible);
            firePropertyChange("valueAxisLineVisible", old, visible);
        }                
    }

    /**
     * Returns a flag that conrtols whether or not the category axis
     * draws a line running the length of the axis.
     * 
     * @return A boolean.
     * 
     * @see #setCategoryAxisLineVisible(boolean)
     */
    public boolean isCategoryAxisLineVisible() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxis().isAxisLineVisible();
        }
        return false;        
    }
    
    /**
     * Sets the flag that controls whether or not the category axis draws a
     * line running the length of the axis and fires a 
     * {@link PropertyChangeEvent} for the <code>categoryAxisLineVisible</code>
     * property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isCategoryAxisLineVisible()
     */
    public void setCategoryAxisLineVisible(boolean visible) {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            boolean old = plot.getDomainAxis().isAxisLineVisible();
            plot.getDomainAxis().setAxisLineVisible(visible);
            firePropertyChange("categoryAxisLineVisible", old, visible);
        }                
    }

    /**
     * Returns the permitted axis locations for the category axis.
     * 
     * @return The axis location.
     * 
     * @see #setCategoryAxisLocation(AxisLocation)
     */
    public AxisLocation getCategoryAxisLocation() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getDomainAxisLocation();
        }
        return null;                
    }
    
    /**
     * Sets the axis location for the category axis and fires a 
     * {@link PropertyChangeEvent} for the <code>categoryAxisLocation</code> 
     * property.
     * 
     * @param location  the location (<code>null</code> not permitted).
     * 
     * @see #getCategoryAxisLocation()
     */
    public void setCategoryAxisLocation(AxisLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("Null 'location' argument.");
        }
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            AxisLocation old = plot.getDomainAxisLocation();
            plot.setDomainAxisLocation(location);
            firePropertyChange("categoryAxisLocation", old, location);
        }
    }
    
    /**
     * Returns the permitted axis locations for the value axis.
     * 
     * @return The axis location.
     * 
     * @see #setValueAxisLocation(AxisLocation)
     */
    public AxisLocation getValueAxisLocation() {
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getRangeAxisLocation();
        }
        return null;                
    }
    
    /**
     * Sets the axis location for the value axis and fires a 
     * {@link PropertyChangeEvent} for the <code>valueAxisLocation</code> 
     * property.
     * 
     * @param location  the location (<code>null</code> not permitted).
     */
    public void setValueAxisLocation(AxisLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("Null 'location' argument.");
        }
        CategoryPlot plot = (CategoryPlot) this.chart.getPlot();
        if (plot != null) {
            AxisLocation old = plot.getRangeAxisLocation();
            plot.setRangeAxisLocation(location);
            firePropertyChange("valueAxisLocation", old, location);
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
        CategoryPlot p = (CategoryPlot) this.chart.getPlot();
        if (p == null) {
            return "";
        }
        CategoryItemRenderer r = p.getRenderer();
        if (r == null) {
            return "";
        }
        StandardCategoryToolTipGenerator g = (StandardCategoryToolTipGenerator) 
                r.getBaseToolTipGenerator();
        if (g == null) {
            return "";
        }
        return g.getLabelFormat();
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
        CategoryPlot p = (CategoryPlot) this.chart.getPlot();
        if (p == null) {
            return;
        }
        CategoryItemRenderer r = p.getRenderer();
        if (r == null) {
            return;
        }
        if (format.equals("")) {
            r.setBaseToolTipGenerator(null);
        }
        else {
            r.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
                    format, NumberFormat.getInstance()));   
        }
        // FIXME: what to use for the oldValue
        firePropertyChange("toolTipFormat", null, format);
    }
    
    /**
     * Registers a listener to receive notification of category item clicks.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void addCategoryItemClickListener(
            CategoryItemClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.listeners.add(CategoryItemClickListener.class, listener);
    }
    
    /**
     * Unregisters a listener so that it no longer receives notification of 
     * category item clicks.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void removeCategoryItemClickListener(CategoryItemClickListener 
            listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.listeners.remove(CategoryItemClickListener.class, listener);        
    }
    
    /**
     * Fires a category item click event.
     * 
     * @param event  the event.
     */
    public void fireCategoryItemClickEvent(CategoryItemClickEvent event) {
        Object[] listeners = this.listeners.getListeners(
                CategoryItemClickListener.class);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((CategoryItemClickListener) listeners[i]).onCategoryItemClick(
                    event);
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
                CategoryItemClickListener.class);
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
        if (entity instanceof CategoryItemEntity) {
            CategoryItemEntity cie = (CategoryItemEntity) entity;
            CategoryItemClickEvent lce = new CategoryItemClickEvent(this, 
                    cie.getDataset(), cie.getRowKey(), cie.getColumnKey());
            fireCategoryItemClickEvent(lce);
        }
        else {
            super.mouseClicked(event);
        }
        
    }

}
