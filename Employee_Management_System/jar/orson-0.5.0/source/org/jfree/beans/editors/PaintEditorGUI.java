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
import java.awt.Color;
import java.awt.Paint;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A GUI for editing paint instances.  INCOMPLETE.
 */
public class PaintEditorGUI extends JPanel implements ChangeListener {

    JColorChooser chooser;
    
    /**
     * Default constructor.
     */
    public PaintEditorGUI() {
        setLayout(new BorderLayout());
        this.chooser = new JColorChooser();
        this.chooser.getSelectionModel().addChangeListener(this);
        add(this.chooser);
    }

    /**
     * Returns the paint.
     * 
     * @return The paint.
     */
    public Paint getPaint() {
        return this.chooser.getColor();
    }
    
    /**
     * Sets the paint.
     * 
     * @param paint  the paint.
     */
    public void setPaint(Paint paint) {
        if (paint instanceof Color) {
            this.chooser.getSelectionModel().setSelectedColor((Color) paint);
        }
    }
    
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        firePropertyChange("paint", null, this.chooser.getColor());  
    }

}
