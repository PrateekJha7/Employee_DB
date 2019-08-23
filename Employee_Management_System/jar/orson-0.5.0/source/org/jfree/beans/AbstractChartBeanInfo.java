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

import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.List;
import java.util.ResourceBundle;

import org.jfree.beans.events.LegendClickListener;

/**
 * A description of the {@link AbstractChart} bean.
 */
public class AbstractChartBeanInfo extends SimpleBeanInfo {

    /** The resourceBundle for the localization. */
    static ResourceBundle localizationResources = 
            ResourceBundle.getBundle("org.jfree.beans.LocalizationBundle");

    
    /**
     * Returns some property descriptors.
     * 
     * @return The property descriptors.
     */
    public PropertyDescriptor[] getPropertyDescriptors() {

        // a prefix for the localised string keys
        final String prefix = "AbstractChart.";
        
        // these regular properties are defined...
        Object[][] props = new Object[][] { 
            {"antiAlias", Boolean.FALSE, "Category.Miscellaneous"},
            {"border", Boolean.FALSE, "Category.SWING"},
            {"chartBackgroundPaint", Boolean.FALSE, "Category.Miscellaneous"},
            {"chartBorderVisible", Boolean.FALSE, "Category.Miscellaneous"},
            {"chartBorderPaint", Boolean.FALSE, "Category.Miscellaneous"},
            {"plotOutlineVisible", Boolean.FALSE, "Category.Plot"},
            {"plotBackgroundAlpha", Boolean.TRUE, "Category.Plot"},
            {"plotBackgroundPaint", Boolean.FALSE, "Category.Plot"},
            {"title", Boolean.FALSE, "Category.Titles"},
            {"titleFont", Boolean.FALSE, "Category.Titles"},
            {"titlePaint", Boolean.TRUE, "Category.Titles"},
            {"legendPosition", Boolean.FALSE, "Category.Legend"},
            {"legendItemFont", Boolean.FALSE, "Category.Legend"},
            {"legendItemPaint", Boolean.TRUE, "Category.Legend"},
            {"minimumSize", Boolean.FALSE, "Category.SWING"},
            {"maximumSize", Boolean.FALSE, "Category.SWING"},
            {"name", Boolean.FALSE, "Category.SWING"},
            {"preferredSize", Boolean.FALSE, "Category.SWING"},
            {"source", Boolean.FALSE, "Category.Titles"},
            {"sourceFont", Boolean.FALSE, "Category.Titles"},
            {"sourcePaint", Boolean.TRUE, "Category.Titles"},
            {"subtitle", Boolean.FALSE, "Category.Titles"},
            {"subtitleFont", Boolean.FALSE, "Category.Titles"},
            {"subtitlePaint", Boolean.TRUE, "Category.Titles"}
        };
        
        // create property descriptors for the regular properties...
        List pds = new java.util.ArrayList();
        for (int i = 0; i < props.length; i++) {
            try {
                String name = (String) props[i][0];
                PropertyDescriptor pd = new PropertyDescriptor(name, 
                        AbstractChart.class);
                Boolean expert = (Boolean) props[i][1];
                pd.setExpert(expert.booleanValue());
                String desc = localizationResources.getString(prefix + name);
                pd.setShortDescription(desc);
                pd.setValue("category", localizationResources.getString(
                        (String) props[i][2]));
                pds.add(pd);
            }
            catch (IntrospectionException e) {
                // swallow...
            }
        }

        // create and return an array of all property descriptors...
        PropertyDescriptor[] result = new PropertyDescriptor[pds.size()];
        for (int i = 0; i < pds.size(); i++) {
            result[i] = (PropertyDescriptor) pds.get(i);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see java.beans.SimpleBeanInfo#getEventSetDescriptors()
     */
    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor e1 = null;
        try {
            e1 = new EventSetDescriptor(AbstractChart.class, "legendClick", 
                    LegendClickListener.class, "onLegendClick");
            e1.setInDefaultEventSet(true);
            e1.setExpert(false);
            e1.setPreferred(false);
        }
        catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getEventSetDescriptors();
        }
        return new EventSetDescriptor[] {e1};
    }

}
