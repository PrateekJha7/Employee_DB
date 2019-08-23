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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyEditorManager;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.swing.event.EventListenerList;

import org.jfree.beans.editors.AxisLocationEditor;
import org.jfree.beans.editors.LegendPositionEditor;
import org.jfree.beans.editors.PaintEditor;
import org.jfree.beans.editors.PlotOrientationEditor;
import org.jfree.beans.editors.RectangleEdgeEditor;
import org.jfree.beans.editors.StrokeEditor;
import org.jfree.beans.events.LegendClickEvent;
import org.jfree.beans.events.LegendClickListener;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.LegendItemEntity;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.Zoomable;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.ExtensionFileFilter;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

/**
 * A base class for creating chart beans.
 */
public abstract class AbstractChart extends JComponent 
        implements ChartChangeListener, ActionListener, MouseListener, 
        MouseMotionListener {

    // here we register some custom editors that make the chart beans a little
    // easier to work with...
    static {
        PropertyEditorManager.registerEditor(Paint.class, PaintEditor.class);
        PropertyEditorManager.registerEditor(Stroke.class, StrokeEditor.class);
        PropertyEditorManager.registerEditor(PlotOrientation.class, 
                PlotOrientationEditor.class);
        PropertyEditorManager.registerEditor(RectangleEdge.class,
                RectangleEdgeEditor.class);
        PropertyEditorManager.registerEditor(AxisLocation.class, 
                AxisLocationEditor.class);
        PropertyEditorManager.registerEditor(LegendPosition.class,
                LegendPositionEditor.class);
    }

    /** The underlying chart. */
    protected JFreeChart chart;
    
    /** 
     * The chart's legend.  We keep a separate reference to this, so that
     * the legend can be added/removed from the chart.
     */
    protected LegendTitle legend;
    
    /**
     * The current legend position (TOP, BOTTOM, LEFT, RIGHT or NONE).
     */
    protected LegendPosition legendPosition;
  
    /** 
     * A subtitle for the chart.
     */
    protected TextTitle subtitle;
    
    /**
     * A subtitle that shows the data source.
     */
    protected TextTitle sourceSubtitle;
    
    /** 
     * The chart rendering info, which is used for tooltips and mouse 
     * events. 
     */
    protected ChartRenderingInfo info;
    
    /** Storage for registered listeners. */
    protected EventListenerList listeners;

    /** A buffer for the rendered chart. */
    protected Image chartBuffer;

    /** The height of the chart buffer. */
    protected int chartBufferHeight;

    /** The width of the chart buffer. */
    protected int chartBufferWidth;

    /** A flag that indicates that the buffer should be refreshed. */
    private boolean refreshBuffer;

    /** The scale factor used to draw the chart. */
    protected double scaleX;

    /** The scale factor used to draw the chart. */
    protected double scaleY;

    /** The chart anchor point. */
    private Point2D anchor;

    /** The zoom rectangle (selected by the user with the mouse). */
    private transient Rectangle2D zoomRectangle = null;

    /** 
     * The zoom rectangle starting point (selected by the user with a mouse 
     * click).  This is a point on the screen, not the chart (which may have
     * been scaled up or down to fit the panel).  
     */
    private Point zoomPoint = null;

    /** The minimum distance required to drag the mouse to trigger a zoom. */
    private int zoomTriggerDistance;
    
    /** Controls if the zoom rectangle is drawn as an outline or filled. */
    private boolean fillZoomRectangle = false;

    /**
     * Default constructor.
     */
    public AbstractChart() {
        this.info = new ChartRenderingInfo();
        this.chart = createDefaultChart();
        this.chart.addChangeListener(this);
        this.chart.getTitle().setFont(new Font("Dialog", Font.BOLD, 14));
        this.chart.setBackgroundPaint(Color.white);
        this.legend = this.chart.getLegend();
        this.legend.setItemFont(new Font("Dialog", Font.PLAIN, 10));
        this.legendPosition = LegendPosition.BOTTOM;
        this.subtitle = new TextTitle("Chart Subtitle", new Font("Dialog", 
                Font.ITALIC, 10));
        this.chart.addSubtitle(0, this.subtitle);
        this.sourceSubtitle = new TextTitle("http://www.jfree.org/jfreechart",
                new Font("Dialog", Font.PLAIN, 8));
        this.sourceSubtitle.setPosition(RectangleEdge.BOTTOM);
        this.sourceSubtitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        this.chart.addSubtitle(0, this.sourceSubtitle);
        addMouseListener(this);
        addMouseMotionListener(this);
        setToolTipsEnabled(true);
        setPreferredSize(new Dimension(360, 230));
        this.listeners = new EventListenerList();
    }

    /**
     * Creates the default chart for initial display to the user.  Subclasses
     * implement this as appropriate for the chart type.
     * 
     * @return The default chart.
     */
    protected abstract JFreeChart createDefaultChart();
    
    /**
     * Returns the flag that controls whether or not the chart is drawn
     * with antialiasing.
     * 
     * @return The antialiasing flag.
     * 
     * @see #setAntiAlias(boolean)
     */
    public boolean getAntiAlias() {
        return this.chart.getAntiAlias();
    }
    
    /**
     * Sets the flag that controls whether or not the chart is drawn with 
     * antialiasing, and fires a {@link PropertyChangeEvent} for the 
     * <code>antiAlias</code> property.
     * 
     * @param flag  the new flag value.
     * 
     * @see #getAntiAlias()
     */
    public void setAntiAlias(boolean flag) {
        boolean old = this.chart.getAntiAlias();
        this.chart.setAntiAlias(flag);
        firePropertyChange("antiAlias", old, flag);
    }
    
    /**
     * Returns a flag that controls whether or not the chart border is visible.
     * In general, it makes more sense to use a Swing border around the 
     * component, but when saving a chart to an image, it is sometimes useful
     * to display an outline border.
     * 
     * @return A flag that controls whether or not the chart border is visible.
     * 
     * @see #setChartBorderVisible(boolean)
     */
    public boolean isChartBorderVisible() {
        return this.chart.isBorderVisible();
    }
    
    /**
     * Sets the flag that controls whether or not a border is drawn around
     * the chart, and fires a {@link PropertyChangeEvent} for the
     * <code>chartBorderVisible</code> property.
     * 
     * @param visible  the new value for the flag.
     * 
     * @see #isChartBorderVisible()
     */
    public void setChartBorderVisible(boolean visible) {
        boolean old = this.chart.isBorderVisible();
        this.chart.setBorderVisible(visible);
        firePropertyChange("chartBorderVisible", old, visible);
    }
    
    /**
     * Returns the stroke used to draw the outline for the chart.
     * 
     * @return The stroke.
     * 
     * @see #setChartBorderStroke(Stroke)
     */
    public Stroke getChartBorderStroke() {
        return this.chart.getBorderStroke();
    }
    
    /**
     * Sets the stroke used to draw the outline for the chart and 
     * sends a {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>chartBorderPaint</code> property.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted).
     * 
     * @see #getChartBorderStroke()
     */
    public void setChartBorderStroke(Stroke stroke) {
        Stroke old = this.chart.getBorderStroke();
        this.chart.setBorderStroke(stroke);
        firePropertyChange("chartBorderStroke", old, stroke);
    }

    /**
     * Returns the paint used to draw the chart border, if it is visible.
     * 
     * @return The paint used to draw the chart border (never 
     *         <code>null</code>).
     *         
     * @see #setChartBorderPaint(Paint)
     */
    public Paint getChartBorderPaint() {
        return this.chart.getBorderPaint();
    }
    
    /**
     * Sets the paint used to draw the chart border, if it is visible, and 
     * sends a {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>chartBorderPaint</code> property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getChartBorderPaint()
     */
    public void setChartBorderPaint(Paint paint) {
        Paint old = getChartBorderPaint();
        this.chart.setBorderPaint(paint);
        firePropertyChange("chartBorderPaint", old, paint);
    }
    
    /**
     * Returns the background paint for the chart.
     * 
     * @return The background paint for the chart (possibly <code>null</code>).
     * 
     * @see #setChartBackgroundPaint(Paint)
     */
    public Paint getChartBackgroundPaint() {
        return this.chart.getBackgroundPaint();
    }
    
    /**
     * Sets the background paint for the chart and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>chartBackgroundPaint</code> property.
     * 
     * @param paint  the paint (<code>null</code> permitted).
     * 
     * @see #getChartBackgroundPaint()
     */
    public void setChartBackgroundPaint(Paint paint) {
        Paint old = this.chart.getBackgroundPaint();
        this.chart.setBackgroundPaint(paint);
        firePropertyChange("chartBackgroundPaint", old, paint);
    }

    // FIXME: the chartBackgroundImage isn't yet covered in the BeanInfo
    
    /**
     * Returns the background image for the chart.
     * 
     * @return The image (possibly <code>null</code>).
     * 
     * @see #setChartBackgroundImage(Image)
     */
    public Image getChartBackgroundImage() {
        return this.chart.getBackgroundImage();
    }
    
    /**
     * Sets the background image for the chart and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>chartBackgroundImage</code> property.
     * 
     * @param image  the image (<code>null</code> permitted).
     * 
     * @see #getChartBackgroundImage()
     */
    public void setChartBackgroundImage(Image image) {
        Image old = this.chart.getBackgroundImage();
        this.chart.setBackgroundImage(image);
        firePropertyChange("chartBackgroundImage", old, image);
    }
    
    // FIXME: chartBackgroundImageAlpha is not yet covered in BeanInfo
    
    /**
     * Returns the alpha-transparency for the background image.
     * 
     * @return The alpha value.
     * 
     * @see #setChartBackgroundImageAlpha(float)
     */
    public float getChartBackgroundImageAlpha() {
        return this.chart.getBackgroundImageAlpha();
    }
    
    /**
     * Sets the alpha transparency for the background image.
     * 
     * @param alpha  the new value.
     * 
     * @see #getChartBackgroundImageAlpha()
     */
    public void setChartBackgroundImageAlpha(float alpha) {
        float old = this.chart.getBackgroundImageAlpha();
        this.chart.setBackgroundImageAlpha(alpha);
        firePropertyChange("chartBackgroundImageAlpha", old, alpha);
    }
    
    // FIXME:  the chartPadding is not yet covered in the BeanInfo.
    
    /**
     * Returns the chart padding.
     * 
     * @return The chart padding.
     */
    public RectangleInsets getChartPadding() {
        return this.chart.getPadding();
    }
    
    /**
     * Sets the chart padding.
     * 
     * @param padding  the padding.
     */
    public void setChartPadding(RectangleInsets padding) {
        this.chart.setPadding(padding);
    }
    
    /**
     * Returns the text for the chart title.
     * 
     * @return The text for the chart title.
     * 
     * @see #setTitle(String)
     */
    public String getTitle() {
        String result = null;
        TextTitle title = this.chart.getTitle();
        if (title != null) {
            result = title.getText();
        }
        return result;
    }
    
    /**
     * Sets the text for the chart title and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>title</code> property.
     * 
     * @param title  the title (<code>null</code> not permitted).
     * 
     * @see #getTitle()
     */
    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Null 'title' argument.");
        }
        TextTitle t = this.chart.getTitle();
        if (t != null) {
            String old = getTitle();
            t.setText(title);
            firePropertyChange("title", old, title);
        }
    }
    
    /**
     * Returns the font for the chart title.
     * 
     * @return The font for the chart title.
     * 
     * @see #setTitleFont(Font)
     */
    public Font getTitleFont() {
        Font result= null;
        TextTitle title = this.chart.getTitle();
        if (title != null) {
            result = title.getFont();
        }
        return result;
    }
    
    /**
     * Sets the font for the chart title and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>titleFont</code> property.
     * 
     * @param font  the font.
     * 
     * @see #getTitleFont()
     */
    public void setTitleFont(Font font) {
        TextTitle t = this.chart.getTitle();
        if (t != null) {
            Font old = t.getFont();
            t.setFont(font);
            firePropertyChange("titleFont", old, font);
        }
    }
    
    /**
     * Returns the paint used to draw the chart title.
     * 
     * @return The paint used to draw the chart title.
     * 
     * @see #getTitlePaint()
     */
    public Paint getTitlePaint() {
        Paint result = null;
        TextTitle title = this.chart.getTitle();
        if (title != null) {
            result = title.getPaint();
        }
        return result;
    }
    
    /**
     * Sets the paint for the chart title and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>titlePaint</code> property.
     * 
     * @param paint  the paint.
     * 
     * @see #getTitlePaint()
     */
    public void setTitlePaint(Paint paint) {
        TextTitle t = this.chart.getTitle();
        if (t != null) {
            Paint old = t.getPaint();
            t.setPaint(paint);
            firePropertyChange("titlePaint", old, paint);
        }
    }    
    
    /**
     * Returns the text for the chart's subtitle.
     * 
     * @return The text for the chart's subtitle.
     * 
     * @see #setSubtitle(String)
     */
    public String getSubtitle() {
        return this.subtitle.getText();
    }
    
    /**
     * Sets the text for the chart's subtitle and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>subtitle</code> property.
     * 
     * @param title  the title.
     * 
     * @see #getSubtitle()
     */
    public void setSubtitle(String title) {
        String old = this.subtitle.getText();
        this.subtitle.setText(title);
        firePropertyChange("subtitle", old, title);        
    }
    
    /**
     * Returns the font for the chart's subtitle.
     * 
     * @return The font for the chart's subtitle.
     * 
     * @see #setSubtitleFont(Font)
     */
    public Font getSubtitleFont() {
        return this.subtitle.getFont();
    }
    
    /**
     * Sets the font for the chart's subtitle and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>subtitleFont</code> property.
     * 
     * @param font  the font (<code>null</code> not permitted).
     * 
     * @see #getSubtitleFont()
     */
    public void setSubtitleFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        Font old = this.subtitle.getFont();
        this.subtitle.setFont(font);
        firePropertyChange("subtitleFont", old, font);        
    }

    /**
     * Returns the paint used to draw the chart's subtitle.
     * 
     * @return The paint used to draw the chart's subtitle.
     * 
     * @see #setSubtitlePaint(Paint)
     */
    public Paint getSubtitlePaint() {
        return this.subtitle.getPaint();
    }
    
    /**
     * Sets the paint for the chart's subtitle and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>subtitlePaint</code> property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getSubtitlePaint()
     */
    public void setSubtitlePaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument");
        }
        Paint old = this.subtitle.getPaint();
        this.subtitle.setPaint(paint);
        firePropertyChange("subtitlePaint", old, paint);        
    }    
    
    /**
     * Returns the text for the chart's source subtitle.
     * 
     * @return The text for the chart's sourcesubtitle.
     * 
     * @see #setSource(String)
     */
    public String getSource() {
        return this.sourceSubtitle.getText();
    }
    
    /**
     * Sets the text for the chart's source subtitle and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>source</code> property.
     * 
     * @param title  the title.
     * 
     * @see #getSource()
     */
    public void setSource(String title) {
        String old = this.sourceSubtitle.getText();
        this.sourceSubtitle.setText(title);
        firePropertyChange("source", old, title);        
    }
    
    /**
     * Returns the font for the chart's source subtitle.
     * 
     * @return The font for the chart's source subtitle.
     * 
     * @see #setSourceFont(Font)
     */
    public Font getSourceFont() {
        return this.sourceSubtitle.getFont();
    }
    
    /**
     * Sets the font for the chart's source subtitle and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>sourceFont</code> property.
     * 
     * @param font  the font (<code>null</code> not permitted).
     * 
     * @see #getSourceFont()
     */
    public void setSourceFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        Font old = this.sourceSubtitle.getFont();
        this.sourceSubtitle.setFont(font);
        firePropertyChange("sourceFont", old, font);        
    }

    /**
     * Returns the paint used to draw the chart's source subtitle.
     * 
     * @return The paint used to draw the chart's source subtitle.
     * 
     * @see #setSourcePaint(Paint)
     */
    public Paint getSourcePaint() {
        return this.sourceSubtitle.getPaint();
    }
    
    /**
     * Sets the paint for the chart's source subtitle and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>sourcePaint</code> property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getSourcePaint()
     */
    public void setSourcePaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument");
        }
        Paint old = this.sourceSubtitle.getPaint();
        this.sourceSubtitle.setPaint(paint);
        firePropertyChange("sourcePaint", old, paint);        
    }    
    
    /**
     * Returns the flag that controls whether or not the plot outline is
     * visible.
     * 
     * @return The flag that controls whether or not the plot outline is
     *         visible.
     *         
     * @see #setPlotOutlineVisible(boolean)
     */
    public boolean isPlotOutlineVisible() {
        Plot plot = this.chart.getPlot();
        if (plot != null) {
            return plot.isOutlineVisible();
        }
        return false;
    }
    
    /**
     * Sets the flag that controls whether or not the plot outline is
     * visible and sends a {@link PropertyChangeEvent} to all
     * registered listeners for the <code>plotOutlineVisible</code> property.
     * 
     * @param visible  the new flag value.
     * 
     * @see #isPlotOutlineVisible()
     */
    public void setPlotOutlineVisible(boolean visible) {
        Plot plot = this.chart.getPlot();
        if (plot != null) {
            boolean old = plot.isOutlineVisible();
            plot.setOutlineVisible(visible);
            firePropertyChange("plotOutlineVisible", old, visible);
        }
    }
    
    /**
     * Returns the alpha transparency used when filling the background of the
     * plot area.
     * 
     * @return The alpha transparency.
     * 
     * @see #setPlotBackgroundAlpha(float)
     */
    public float getPlotBackgroundAlpha() {
        float result = 1.0f;
        Plot plot = this.chart.getPlot();
        if (plot != null) {
            result = plot.getBackgroundAlpha();
        }
        return result;
    }
    
    /**
     * Sets the alpha transparency used when filling the background of the
     * plot area and sends a {@link PropertyChangeEvent} to all
     * registered listeners for the <code>plotBackgroundAlpha</code> property.
     * 
     * @param alpha  the alpha transparency (in the range 0.0 to 1.0).
     * 
     * @see #getPlotBackgroundAlpha()
     */
    public void setPlotBackgroundAlpha(float alpha) {
        Plot plot = this.chart.getPlot();
        if (plot != null) {
            float old = plot.getBackgroundAlpha();
            plot.setBackgroundAlpha(alpha);
            firePropertyChange("plotBackgroundAlpha", old, alpha);
        }
    }
    
    /**
     * Returns the background paint for the plot, or <code>null</code>.
     * 
     * @return The background paint (possibly <code>null</code>).
     * 
     * @see #setPlotBackgroundPaint(Paint)
     */
    public Paint getPlotBackgroundPaint() {
        Paint result = null;
        Plot plot = this.chart.getPlot();
        if (plot != null) {
            result = plot.getBackgroundPaint();
        }
        return result;        
    }

    /**
     * Sets the background paint and sends a {@link PropertyChangeEvent} to all
     * registered listeners for the <code>plotBackgroundPaint</code> property.
     * 
     * @param paint  the paint (<code>null</code> permitted).
     * 
     * @see #getPlotBackgroundPaint()
     */
    public void setPlotBackgroundPaint(Paint paint) {
        Plot plot = this.chart.getPlot();
        if (plot != null) {
            Paint old = plot.getBackgroundPaint();
            plot.setBackgroundPaint(paint);
            firePropertyChange("plotBackgroundPaint", old, paint);
        }
    }
    
    /**
     * Returns the legend position.
     * 
     * @return The legend position.
     * 
     * @see #setLegendPosition(LegendPosition)
     */
    public LegendPosition getLegendPosition() {
        return this.legendPosition;
    }
    
    /**
     * Sets the legend position and sends a {@link PropertyChangeEvent} to all
     * registered listeners for the <code>legendPosition</code> property.
     * 
     * @param position  the position (<code>null</code> not permitted).
     * 
     * @see #getLegendPosition()
     */
    public void setLegendPosition(LegendPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("Null 'position' argument.");
        }
        LegendPosition old = this.legendPosition;
        if (position.equals(LegendPosition.NONE)) {
            if (!old.equals(LegendPosition.NONE)) {
                this.chart.removeSubtitle(this.legend);
            }
        }
        else {
            if (old.equals(LegendPosition.NONE)) {
                this.chart.addSubtitle(1, this.legend);
            }
            if (position.equals(LegendPosition.TOP)) {
                this.legend.setPosition(RectangleEdge.TOP);
            }
            else if (position.equals(LegendPosition.BOTTOM)) {
                this.legend.setPosition(RectangleEdge.BOTTOM);
            }
            else if (position.equals(LegendPosition.LEFT)) {
                this.legend.setPosition(RectangleEdge.LEFT);
            }
            else if (position.equals(LegendPosition.RIGHT)) {
                this.legend.setPosition(RectangleEdge.RIGHT);
            }
        }
        this.legendPosition = position;
        firePropertyChange("legendPosition", old, position);
    }
    
    /**
     * Returns the font for the legend items.
     * 
     * @return The font.
     * 
     * @see #setLegendItemFont(Font)
     */
    public Font getLegendItemFont() {
        return this.legend.getItemFont();
    }
    
    /**
     * Sets the font for the legend items and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the
     * <code>legendItemFont</code> property.
     * 
     * @param font  the font (<code>null</code> not permitted).
     * 
     * @see #getLegendItemFont()
     */
    public void setLegendItemFont(Font font) {
        Font old = this.legend.getItemFont();
        this.legend.setItemFont(font);
        firePropertyChange("legendItemFont", old, font);
    }

    /**
     * Returns the paint used to display the legend items.
     * 
     * @return The paint.
     * 
     * @see #setLegendItemPaint(Paint)
     */
    public Paint getLegendItemPaint() {
        return this.legend.getItemPaint();
    }
    
    /**
     * Sets the paint used to display the legend items and sends a 
     * {@link PropertyChangeEvent} to all registered listeners for the 
     * <code>legendItemPaint</code> property.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getLegendItemPaint()
     */
    public void setLegendItemPaint(Paint paint) {
        Paint old = this.legend.getItemPaint();
        this.legend.setItemPaint(paint);
        firePropertyChange("legendItemPaint", old, paint);
    }

    private static final int MIN_DRAW_WIDTH = 200;
    
    private static final int MIN_DRAW_HEIGHT = 150;
    
    /**
     * Paints this component, including the chart it contains.
     * 
     * @param g  the graphics target.
     */
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        if (this.chart == null) {
            return;
        }
        
        Graphics2D g2 = (Graphics2D) g.create();

        // first determine the size of the chart rendering area...
        Dimension size = getSize();
        Insets insets = getInsets();
        Rectangle2D available = new Rectangle2D.Double(insets.left, insets.top,
                size.getWidth() - insets.left - insets.right,
                size.getHeight() - insets.top - insets.bottom);

        // work out if scaling is required (when the component is small, the
        // chart will be drawn at a larger size and scaled down to fit the
        // space...
        boolean scale = false;
        double drawWidth = available.getWidth();
        double drawHeight = available.getHeight();
        this.scaleX = 1.0;
        this.scaleY = 1.0;

        if (drawWidth < MIN_DRAW_WIDTH) {
            this.scaleX = drawWidth / MIN_DRAW_WIDTH;
            drawWidth = MIN_DRAW_WIDTH;
            scale = true;
        }

        if (drawHeight < MIN_DRAW_HEIGHT) {
            this.scaleY = drawHeight / MIN_DRAW_HEIGHT;
            drawHeight = MIN_DRAW_HEIGHT;
            scale = true;
        }

        Rectangle2D chartArea = new Rectangle2D.Double(0.0, 0.0, drawWidth, 
                drawHeight);

        if ((this.chartBuffer == null) 
                || (this.chartBufferWidth != available.getWidth())
                || (this.chartBufferHeight != available.getHeight())) {
            this.chartBufferWidth = (int) available.getWidth();
            this.chartBufferHeight = (int) available.getHeight();
            GraphicsConfiguration gc = g2.getDeviceConfiguration();
            this.chartBuffer = gc.createCompatibleImage(
                    this.chartBufferWidth, this.chartBufferHeight, 
                    Transparency.TRANSLUCENT);
            this.refreshBuffer = true;
        }

        // do we need to redraw the buffer?
        if (this.refreshBuffer) {

            Rectangle2D bufferArea = new Rectangle2D.Double(0, 0, 
                    this.chartBufferWidth, this.chartBufferHeight);

            Graphics2D bufferG2 = (Graphics2D) this.chartBuffer.getGraphics();
            if (scale) {
                AffineTransform saved = bufferG2.getTransform();
                AffineTransform st = AffineTransform.getScaleInstance(
                        this.scaleX, this.scaleY);
                bufferG2.transform(st);
                this.chart.draw(bufferG2, chartArea, this.anchor, this.info);
                bufferG2.setTransform(saved);
            }
            else {
                this.chart.draw(bufferG2, bufferArea, this.anchor, this.info);
            }

            this.refreshBuffer = false;

        }

        // zap the buffer onto the panel...
        g2.drawImage(this.chartBuffer, insets.left, insets.top, this);
        g2.dispose();
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
                LegendClickListener.class);
        if (listeners.length == 0) {
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
        if (entity instanceof LegendItemEntity) {
            LegendItemEntity lie = (LegendItemEntity) entity;
            LegendClickEvent lce = new LegendClickEvent(this, lie.getDataset(),
                    lie.getSeriesKey());
            fireLegendClickEvent(lce);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        // ignore   
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        // ignore   
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        if (this.zoomRectangle == null) {
            Rectangle2D screenDataArea = getScreenDataArea();
            if (screenDataArea != null) {
                this.zoomPoint = getPointInRectangle(e.getX(), e.getY(), 
                        screenDataArea);
            }
            else {
                this.zoomPoint = null;
            }
        }
        if (e.isPopupTrigger()) {
            JPopupMenu popup = createPopup();
            popup.show(this, e.getX(), e.getY());
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        if (this.zoomRectangle != null) {
            boolean hZoom = false;
            boolean vZoom = false;
            Plot plot = this.chart.getPlot();
            if (plot instanceof Zoomable) {
                Zoomable z = (Zoomable) plot;
                PlotOrientation orientation = z.getOrientation();
                if (orientation == PlotOrientation.HORIZONTAL) {
                    hZoom = z.isRangeZoomable();
                    vZoom = z.isDomainZoomable();
                }
                else {
                    hZoom = z.isDomainZoomable();           
                    vZoom = z.isRangeZoomable();
                }
            }
            
            boolean zoomTrigger1 = hZoom && Math.abs(e.getX() 
                - this.zoomPoint.getX()) >= this.zoomTriggerDistance;
            boolean zoomTrigger2 = vZoom && Math.abs(e.getY() 
                - this.zoomPoint.getY()) >= this.zoomTriggerDistance;
            if (zoomTrigger1 || zoomTrigger2) {
                if ((hZoom && (e.getX() < this.zoomPoint.getX())) 
                    || (vZoom && (e.getY() < this.zoomPoint.getY()))) {
                    restoreAutoBounds();
                }
                else {
                    double x, y, w, h;
                    Rectangle2D screenDataArea = getScreenDataArea();
                    // for mouseReleased event, (horizontalZoom || verticalZoom)
                    // will be true, so we can just test for either being false;
                    // otherwise both are true
                    if (!vZoom) {
                        x = this.zoomPoint.getX();
                        y = screenDataArea.getMinY();
                        w = Math.min(this.zoomRectangle.getWidth(),
                                screenDataArea.getMaxX() 
                                - this.zoomPoint.getX());
                        h = screenDataArea.getHeight();
                    }
                    else if (!hZoom) {
                        x = screenDataArea.getMinX();
                        y = this.zoomPoint.getY();
                        w = screenDataArea.getWidth();
                        h = Math.min(this.zoomRectangle.getHeight(),
                                screenDataArea.getMaxY() 
                                - this.zoomPoint.getY());
                    }
                    else {
                        x = this.zoomPoint.getX();
                        y = this.zoomPoint.getY();
                        w = Math.min(this.zoomRectangle.getWidth(),
                                screenDataArea.getMaxX() 
                                - this.zoomPoint.getX());
                        h = Math.min(this.zoomRectangle.getHeight(),
                                screenDataArea.getMaxY() 
                                - this.zoomPoint.getY());
                    }
                    Rectangle2D zoomArea = new Rectangle2D.Double(x, y, w, h);
                    zoom(zoomArea);
                }
                this.zoomPoint = null;
                this.zoomRectangle = null;
            }
            else {
                // Erase the zoom rectangle
                Graphics2D g2 = (Graphics2D) getGraphics();
                drawZoomRectangle(g2);
                g2.dispose();
                this.zoomPoint = null;
                this.zoomRectangle = null;
            }

        }

        else if (e.isPopupTrigger()) {
            JPopupMenu popup = createPopup();
            popup.show(this, e.getX(), e.getY());
        }
    }

    /**
     * Implementation of the MouseMotionListener's method.
     *
     * @param e  the event.
     */
    public void mouseMoved(MouseEvent e) {
        // do nothing
    }
    
    /**
     * Handles a 'mouse dragged' event.
     *
     * @param e  the mouse event.
     */
    public void mouseDragged(MouseEvent e) {
        // FIXME: handle popup
        // if the popup menu has already been triggered, then ignore dragging...
//        if (this.popup != null && this.popup.isShowing()) {
//            return;
//        }
        // if no initial zoom point was set, ignore dragging...
        if (this.zoomPoint == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) getGraphics();

        // Erase the previous zoom rectangle (if any)...
        drawZoomRectangle(g2);

        boolean hZoom = false;
        boolean vZoom = false;
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable z = (Zoomable) plot;
            PlotOrientation orientation = z.getOrientation();
            if (orientation == PlotOrientation.HORIZONTAL) {
                hZoom = z.isRangeZoomable();
                vZoom = z.isDomainZoomable();
            }
            else {
                hZoom = z.isDomainZoomable();           
                vZoom = z.isRangeZoomable();
            }
        }
        Rectangle2D scaledDataArea = getScreenDataArea();
        if (hZoom && vZoom) {
            // selected rectangle shouldn't extend outside the data area...
            double xmax = Math.min(e.getX(), scaledDataArea.getMaxX());
            double ymax = Math.min(e.getY(), scaledDataArea.getMaxY());
            this.zoomRectangle = new Rectangle2D.Double(
                    this.zoomPoint.getX(), this.zoomPoint.getY(),
                    xmax - this.zoomPoint.getX(), ymax - this.zoomPoint.getY());
        }
        else if (hZoom) {
            double xmax = Math.min(e.getX(), scaledDataArea.getMaxX());
            this.zoomRectangle = new Rectangle2D.Double(
                    this.zoomPoint.getX(), scaledDataArea.getMinY(),
                    xmax - this.zoomPoint.getX(), scaledDataArea.getHeight());
        }
        else if (vZoom) {
            double ymax = Math.min(e.getY(), scaledDataArea.getMaxY());
            this.zoomRectangle = new Rectangle2D.Double(
                    scaledDataArea.getMinX(), this.zoomPoint.getY(),
                    scaledDataArea.getWidth(), ymax - this.zoomPoint.getY());
        }

        // Draw the new zoom rectangle...
        drawZoomRectangle(g2);
        
        g2.dispose();

    }
    
    /**
     * Creates a popup menu for display on the component.
     * 
     * @return A popup menu.
     */
    protected JPopupMenu createPopup() {
        // This is still a work-in-progress, obviously!
        JPopupMenu popup = new JPopupMenu();
        JMenuItem saveAs = new JMenuItem("Save As...");
        saveAs.addActionListener(this);
        saveAs.setActionCommand("SAVE_AS");
        popup.add(saveAs);
        return popup;
    }
    
    /**
     * Returns the data area for the chart (the area inside the axes) with the
     * current scaling applied (that is, the area as it appears on screen).
     *
     * @return The scaled data area.
     */
    public Rectangle2D getScreenDataArea() {
        Rectangle2D dataArea = this.info.getPlotInfo().getDataArea();
        Insets insets = getInsets();
        double x = dataArea.getX() * this.scaleX + insets.left;
        double y = dataArea.getY() * this.scaleY + insets.top;
        double w = dataArea.getWidth() * this.scaleX;
        double h = dataArea.getHeight() * this.scaleY;
        return new Rectangle2D.Double(x, y, w, h);
    }

    /**
     * Returns a point based on (x, y) but constrained to be within the bounds
     * of the given rectangle.  This method could be moved to JCommon.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param area  the rectangle (<code>null</code> not permitted).
     * 
     * @return A point within the rectangle.
     */
    private Point getPointInRectangle(int x, int y, Rectangle2D area) {
        x = (int) Math.max(Math.ceil(area.getMinX()), Math.min(x, 
                Math.floor(area.getMaxX())));   
        y = (int) Math.max(Math.ceil(area.getMinY()), Math.min(y, 
                Math.floor(area.getMaxY())));
        return new Point(x, y);
    }

    /**
     * Restores the auto-range calculation on both axes.
     */
    public void restoreAutoBounds() {
        restoreAutoDomainBounds();
        restoreAutoRangeBounds();
    }

    /**
     * Restores the auto-range calculation on the domain axis.
     */
    public void restoreAutoDomainBounds() {
        Plot p = this.chart.getPlot();
        if (p instanceof Zoomable) {
            Zoomable z = (Zoomable) p;
            // we need to guard against this.zoomPoint being null
            Point zp = (this.zoomPoint != null ? this.zoomPoint : new Point());
            z.zoomDomainAxes(0.0, this.info.getPlotInfo(), zp);
        }
    }

    /**
     * Restores the auto-range calculation on the range axis.
     */
    public void restoreAutoRangeBounds() {
        Plot p = this.chart.getPlot();
        if (p instanceof Zoomable) {
            Zoomable z = (Zoomable) p;
            // we need to guard against this.zoomPoint being null
            Point zp = (this.zoomPoint != null ? this.zoomPoint : new Point());
            z.zoomRangeAxes(0.0, this.info.getPlotInfo(), zp);
        }
    }

    /**
     * Translates a Java2D point on the chart to a screen location.
     *
     * @param java2DPoint  the Java2D point.
     *
     * @return The screen location.
     */
    public Point translateJava2DToScreen(Point2D java2DPoint) {
        Insets insets = getInsets();
        int x = (int) (java2DPoint.getX() * this.scaleX + insets.left);
        int y = (int) (java2DPoint.getY() * this.scaleY + insets.top);
        return new Point(x, y);
    }

    /**
     * Translates a screen location to a Java2D point.
     *
     * @param screenPoint  the screen location.
     *
     * @return The Java2D coordinates.
     */
    public Point2D translateScreenToJava2D(Point screenPoint) {
        Insets insets = getInsets();
        double x = (screenPoint.getX() - insets.left) / this.scaleX;
        double y = (screenPoint.getY() - insets.top) / this.scaleY;
        return new Point2D.Double(x, y);
    }

    /**
     * Zooms in on a selected region.
     *
     * @param selection  the selected region.
     */
    public void zoom(Rectangle2D selection) {

        // get the origin of the zoom selection in the Java2D space used for
        // drawing the chart (that is, before any scaling to fit the panel)
        Point2D selectOrigin = translateScreenToJava2D(new Point(
                (int) Math.ceil(selection.getX()), 
                (int) Math.ceil(selection.getY())));
        PlotRenderingInfo plotInfo = this.info.getPlotInfo();
        Rectangle2D scaledDataArea = getScreenDataArea();
        if ((selection.getHeight() > 0) && (selection.getWidth() > 0)) {

            double hLower = (selection.getMinX() - scaledDataArea.getMinX()) 
                / scaledDataArea.getWidth();
            double hUpper = (selection.getMaxX() - scaledDataArea.getMinX()) 
                / scaledDataArea.getWidth();
            double vLower = (scaledDataArea.getMaxY() - selection.getMaxY()) 
                / scaledDataArea.getHeight();
            double vUpper = (scaledDataArea.getMaxY() - selection.getMinY()) 
                / scaledDataArea.getHeight();

            Plot p = this.chart.getPlot();
            if (p instanceof Zoomable) {
                Zoomable z = (Zoomable) p;
                if (z.getOrientation() == PlotOrientation.HORIZONTAL) {
                    z.zoomDomainAxes(vLower, vUpper, plotInfo, selectOrigin);
                    z.zoomRangeAxes(hLower, hUpper, plotInfo, selectOrigin);
                }
                else {
                    z.zoomDomainAxes(hLower, hUpper, plotInfo, selectOrigin);
                    z.zoomRangeAxes(vLower, vUpper, plotInfo, selectOrigin);
                }
            }

        }

    }

    /**
     * Switches the display of tooltips for the panel on or off.  Note that 
     * tooltips can only be displayed if the chart has been configured to
     * generate tooltip items.
     *
     * @param flag  <code>true</code> to enable tooltips, <code>false</code> to
     *              disable tooltips.
     */
    protected void setToolTipsEnabled(boolean flag) {
        if (flag) {
            ToolTipManager.sharedInstance().registerComponent(this);
        }
        else {
            ToolTipManager.sharedInstance().unregisterComponent(this);
        }
    }

    /**
     * Returns a string for the tooltip.
     *
     * @param e  the mouse event.
     *
     * @return A tool tip or <code>null</code> if no tooltip is available.
     */
    public String getToolTipText(MouseEvent e) {
        String result = null;
        if (this.info != null) {
            EntityCollection entities = this.info.getEntityCollection();
            if (entities != null) {
                Insets insets = getInsets();
                ChartEntity entity = entities.getEntity(e.getX() - insets.left,
                        e.getY() - insets.top);
                if (entity != null) {
                    result = entity.getToolTipText();
                }
            }
        }
        return result;
    }

    /**
     * Registers a listener to receive notification of legend clicks.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void addLegendClickListener(LegendClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.listeners.add(LegendClickListener.class, listener);
    }
    
    /**
     * Unregisters a listener so that it no longer receives notification of 
     * legend clicks.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void removeLegendClickListener(LegendClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.listeners.remove(LegendClickListener.class, listener);        
    }
    
    /**
     * Fires a legend click event.
     * 
     * @param event  the event.
     */
    public void fireLegendClickEvent(LegendClickEvent event) {
        Object[] listeners = this.listeners.getListeners(
                LegendClickListener.class);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((LegendClickListener) listeners[i]).onLegendClick(event);
        }                
        
    }
    
    /**
     * Draws zoom rectangle (if present).
     * The drawing is performed in XOR mode, therefore
     * when this method is called twice in a row,
     * the second call will completely restore the state
     * of the canvas.
     * 
     * @param g2 the graphics device. 
     */
    private void drawZoomRectangle(Graphics2D g2) {
        // Set XOR mode to draw the zoom rectangle
        g2.setXORMode(Color.gray);
        if (this.zoomRectangle != null) {
            if (this.fillZoomRectangle) {
                g2.fill(this.zoomRectangle);
            }
            else {
                g2.draw(this.zoomRectangle);
            }
        }
        // Reset to the default 'overwrite' mode
        g2.setPaintMode();
    }
    
    /**
     * Receives notification of changes to the chart, and redraws the chart.
     *
     * @param event  details of the chart change event.
     */
    public void chartChanged(ChartChangeEvent event) {
        this.refreshBuffer = true;
        //Plot plot = this.chart.getPlot();
        //if (plot instanceof Zoomable) {
            //Zoomable z = (Zoomable) plot;
            //this.orientation = z.getOrientation();
        //}
        repaint();
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("SAVE_AS")) {
            try {
                doSaveAs();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
    protected void doSaveAs() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        ExtensionFileFilter filter = new ExtensionFileFilter("PNG Files", 
                ".png");
        fileChooser.addChoosableFileFilter(filter);

        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            ChartUtilities.saveChartAsPNG(new File(filename), this.chart, 
                    getWidth(), getHeight());
        }
    
    }
    
}
