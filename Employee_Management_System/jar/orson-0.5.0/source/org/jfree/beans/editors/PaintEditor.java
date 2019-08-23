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

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditorSupport;

/**
 * A JavaBeans property editor for {@link Paint} instances.  Obviously, we
 * can't provide editing for every type of <code>Paint</code>, but we'll try
 * to cover {@link Paint} and {@link GradientPaint}.
 */
public class PaintEditor extends PropertyEditorSupport 
        implements PropertyChangeListener {


    /* (non-Javadoc)
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent evt) {
        firePropertyChange();
    }

    PaintEditorGUI customEditor;
    
    /**
     * Creates a new instance.
     */
    public PaintEditor() {
        this.customEditor = new PaintEditorGUI();
        this.customEditor.addPropertyChangeListener(this);
    }
    
    public boolean isPaintable() {
        return true;
    }
    
    public void paintValue(Graphics g, Rectangle clipRect) {
        Graphics2D g2 = (Graphics2D) g;
        Paint p = this.customEditor.getPaint();
        if (p != null) {
            g2.setPaint(p);
            int cy = (int) clipRect.getCenterY();
            int x = (int) clipRect.getMinX() + 2;
            Rectangle box = new Rectangle(x, cy - 4, 8, 8);
            g2.fill(box);
            g2.setPaint(Color.black);
            g2.draw(box);
        }
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditorSupport#getValue()
     */
    public Object getValue() {
        return this.customEditor.getPaint();
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditorSupport#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        this.customEditor.setPaint((Paint) value);
    }

    /**
     * Returns a string for the property value.
     * 
     * @return A string for the property value.
     */
    public String getJavaInitializationString() {
        Paint p = (Paint) getValue();
        if (p == null) {
            return "null";
        }
        else if (p instanceof Color) {
            Color c = (Color) p;
            return "new java.awt.Color(" + c.getRed() + ", " + c.getGreen() 
            + ", " + c.getBlue() + ", " + c.getAlpha() + ")";
        }
        // FIXME: not a color
        return "new java.awt.GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f, Color.blue);";
    }


    /** 
     * Returns a component for editing a <code>Paint</code> instance.
     * 
     * @return A component for editing.
     */
    public Component getCustomEditor() {
        return this.customEditor;
    }

    /**
     * Returns <code>true</code> to indicate that we provide a custom editor
     * via the {@link #getCustomEditor()} method.
     * 
     * @return <code>true</code>.
     */
    public boolean supportsCustomEditor() {
        return true;
    }

}
