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

import java.util.List;

import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.PieDataset;

/**
 * A pie dataset bean - develop this into something that can be edited with
 * a gui.
 */
public class PieDatasetBean extends AbstractDataset implements PieDataset {

    /* (non-Javadoc)
     * @see org.jfree.data.Values#getItemCount()
     */
    public int getItemCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see org.jfree.data.Values#getValue(int)
     */
    public Number getValue(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.jfree.data.KeyedValues#getIndex(java.lang.Comparable)
     */
    public int getIndex(Comparable arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see org.jfree.data.KeyedValues#getKey(int)
     */
    public Comparable getKey(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.jfree.data.KeyedValues#getKeys()
     */
    public List getKeys() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.jfree.data.KeyedValues#getValue(java.lang.Comparable)
     */
    public Number getValue(Comparable arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
