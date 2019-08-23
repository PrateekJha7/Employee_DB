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

import javax.swing.event.EventListenerList;

import org.jfree.beans.editors.RotationEditor;
import org.jfree.beans.events.SectionClickEvent;
import org.jfree.beans.events.SectionClickListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * A JavaBean that displays a pie chart.
 */
public class JPieChart extends AbstractChart {

    static {
        PropertyEditorManager.registerEditor(Rotation.class, 
                RotationEditor.class);
    }
    
    /** The section label format string. */
    private String labelFormat;
    
    /** Storage for registered sectionClickListeners. */
    private EventListenerList sectionClickListeners;

    /**
     * Creates a new pie chart bean.
     */
    public JPieChart() {
        super();
        this.sectionClickListeners = new EventListenerList();
        this.labelFormat = "{0}";
    }
    
    /**
     * Creates a default chart.
     * 
     * @return The default chart.
     */
    protected JFreeChart createDefaultChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 5.0);
        dataset.setValue("B", 7.0);
        dataset.setValue("C", 6.0);
        PiePlot plot = new PiePlot(dataset);
        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("JPieChart - Title");
        plot.setToolTipGenerator(new StandardPieToolTipGenerator());
        return chart;
    }
    
    /**
     * Returns the direction (clockwise or anti-clockwise) in which the pie 
     * segments are drawn.
     * 
     * @return The direction.
     * 
     * @see #setDirection(Rotation)
     */
    public Rotation getDirection() {
        Rotation result = null;
        PiePlot plot = (PiePlot) this.chart.getPlot();
        if (plot != null) {
            result = plot.getDirection();
        }
        return result;        
    }
    
    /**
     * Sets the direction in which the pie sections are drawn and fires a
     * {@link PropertyChangeEvent} for the <code>direction</code> property.
     * 
     * @param direction  the new direction (<code>null</code> not permitted).
     * 
     * @see #getDirection()
     */
    public void setDirection(Rotation direction) {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        if (plot != null) {
            Rotation old = plot.getDirection();
            plot.setDirection(direction);
            firePropertyChange("direction", old, direction);
        }        
    }
    
    /**
     * Returns the dataset used by the chart.
     * 
     * @return The dataset (possibly <code>null</code>).
     * 
     * @see #setDataset(PieDataset)
     */
    public PieDataset getDataset() {
        PieDataset result = null;
        PiePlot plot = (PiePlot) this.chart.getPlot();
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
    public void setDataset(PieDataset dataset) {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        if (plot != null) {
            PieDataset old = plot.getDataset();
            plot.setDataset(dataset);
            firePropertyChange("dataset", old, dataset);
        }
    }
    
    /**
     * Returns a flag that controls whether the plot is circular or
     * elliptical.
     * 
     * @return A flag.
     * 
     * @see #setCircular(boolean)
     */
    public boolean isCircular() {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        return plot.isCircular();
    }
    
    /**
     * Sets the flag that controls whether the pie chart is drawn as a circle
     * or an ellipse and fires a {@link PropertyChangeEvent} for the 
     * <code>circular</code> property.
     * 
     * @param circular  the flag.
     * 
     * @see #isCircular()
     */
    public void setCircular(boolean circular) {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        boolean old = plot.isCircular();
        plot.setCircular(circular);
        firePropertyChange("circular", old, circular);
    }
    
    /**
     * Returns the angle from which the first pie section starts.
     * 
     * @return The angle.
     * 
     * @see #setPieStartingAngle(double)
     */
    public double getPieStartingAngle() {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        return plot.getStartAngle();
    }
    
    /**
     * Sets the angle at which the first pie section starts and fires a 
     * {@link PropertyChangeEvent} for the <code>pieStartingAngle</code> 
     * property.
     * 
     * @param angle  the angle.
     * 
     * @see #getPieStartingAngle()
     */
    public void setPieStartingAngle(double angle) {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        double old = plot.getStartAngle();
        plot.setStartAngle(angle);
        firePropertyChange("pieStartingAngle", old, angle);
    }
    
    /**
     * Returns the label format used by the plot.
     * 
     * @return The label format.
     * 
     * @see #setLabelFormat(String)
     */
    public String getLabelFormat() {
        String result = null;
        PiePlot plot = (PiePlot) this.chart.getPlot();
        PieSectionLabelGenerator g = plot.getLabelGenerator();
        if (g instanceof StandardPieSectionLabelGenerator) {
            StandardPieSectionLabelGenerator gg 
                    = (StandardPieSectionLabelGenerator) g;
            result = gg.getLabelFormat();
        }
        return result;
    }
    
    /**
     * Returns the format string for the section labels and fires a 
     * {@link PropertyChangeEvent} for the <code>labelFormat</code> property.
     * 
     * @param format  the format string.
     * 
     * @see #getLabelFormat()
     */
    public void setLabelFormat(String format) {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        String old = this.labelFormat;
        this.labelFormat = format;
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));
        firePropertyChange("labelFormat", old, format);
    }
    
    /**
     * Returns the font used to display the section labels.
     * 
     * @return The font.
     * 
     * @see #setLabelFont(Font)
     */
    public Font getLabelFont() {
        Font result = null;
        PiePlot plot = (PiePlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getLabelFont();
        }
        return result;
    }
    
    /**
     * Sets the font used to draw the section labels and fires a 
     * {@link PropertyChangeEvent} for the <code>labelFont</code> property.
     * 
     * @param font  the font.
     * 
     * @see #getLabelFont()
     */
    public void setLabelFont(Font font) {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        if (plot != null) {
            Font old = plot.getLabelFont();
            plot.setLabelFont(font);
            firePropertyChange("labelFont", old, font);
        }
    }
    
    /**
     * Returns the paint used to draw the section labels.
     * 
     * @return The paint.
     * 
     * @see #setLabelPaint(Paint)
     */
    public Paint getLabelPaint() {
        Paint result = null;
        PiePlot plot = (PiePlot) this.chart.getPlot();
        if (plot != null) {
            return plot.getLabelPaint();
        }
        return result;
    }
    
    /**
     * Sets the paint used to draw the section labels and fires a 
     * {@link PropertyChangeEvent} for the <code>labelPaint</code> property.
     * 
     * @param paint  the paint.
     * 
     * @see #getLabelPaint()
     */
    public void setLabelPaint(Paint paint) {
        PiePlot plot = (PiePlot) this.chart.getPlot();
        if (plot != null) {
            Paint old = plot.getLabelPaint();
            plot.setLabelPaint(paint);
            firePropertyChange("labelPaint", old, paint);
        }
    }
    
    /**
     * Returns the format string for the section tool tips.
     * 
     * @return The format string.
     * 
     * @see #setSectionToolTipFormat(String)
     */
    public String getSectionToolTipFormat() {
        PiePlot p = (PiePlot) this.chart.getPlot();
        if (p == null) {
            return "";
        }
        StandardPieToolTipGenerator g = (StandardPieToolTipGenerator) 
                p.getToolTipGenerator();
        if (g == null) {
            return "";
        }
        return g.getLabelFormat();
    }
    
    /**
     * Sets the format string for the section tool tips and fires a 
     * {@link PropertyChangeEvent} for the <code>sectionToolTipFormat</code>.
     * 
     * @param format  the format string.
     * 
     * @see #getSectionToolTipFormat()
     */
    public void setSectionToolTipFormat(String format) {
        PiePlot p = (PiePlot) this.chart.getPlot();
        if (p == null) {
            return;
        }
        if (format.equals("")) {
            p.setToolTipGenerator(null);
        }
        else {
            p.setToolTipGenerator(new StandardPieToolTipGenerator(format));   
        } 
        // FIXME: the old value needs to be specified
        firePropertyChange("sectionToolTipFormat", "", format);
    }
    
    /**
     * Registers a listener to receive notification of section clicks.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void addSectionClickListener(SectionClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.sectionClickListeners.add(SectionClickListener.class, listener);
    }
    
    /**
     * Unregisters a listener so that it no longer receives notification of 
     * section clicks.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void removeSectionClickListener(SectionClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.sectionClickListeners.remove(SectionClickListener.class, listener);        
    }
    
    /**
     * Fires a section click event.
     * 
     * @param event  the event.
     */
    public void fireSectionClickEvent(SectionClickEvent event) {
        Object[] listeners = this.sectionClickListeners.getListeners(
                SectionClickListener.class);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((SectionClickListener) listeners[i]).onSectionClick(event);
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
        Object[] listeners = this.sectionClickListeners.getListeners(
                SectionClickListener.class);
        if (listeners.length == 0) {
            super.mouseClicked(event);
            return;
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
        if (entity instanceof PieSectionEntity) {
            PieSectionEntity pse = (PieSectionEntity) entity;
            SectionClickEvent sce = new SectionClickEvent(this, 
                    pse.getSectionKey());
            fireSectionClickEvent(sce);
        }
        else  {
            super.mouseClicked(event);
        }
    }

}
