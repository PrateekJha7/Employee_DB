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

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import org.jfree.beans.events.SectionClickListener;

/**
 * Bean info for the {@link JPieChart} bean.
 */
public class JPieChartBeanInfo extends SimpleBeanInfo {

    /** The resourceBundle for the localization. */
    static ResourceBundle localizationResources = 
            ResourceBundle.getBundle("org.jfree.beans.LocalizationBundle");
    
    /**
     * Returns the bean info from the super class.
     * 
     * @return Additional bean info.
     */
    public BeanInfo[] getAdditionalBeanInfo() {
        return new BeanInfo[] {new AbstractChartBeanInfo()};    
    }
    
    /**
     * Returns some property descriptors.
     * 
     * @return The property descriptors.
     */
    public PropertyDescriptor[] getPropertyDescriptors() {

        // a prefix for the localised string keys
        final String prefix = "JPieChart.";
        
        // these regular properties are defined...
        Object[][] props = new Object[][] { 
            {"dataset", Boolean.FALSE, "Category.Dataset"},
            {"circular", Boolean.FALSE, "Category.Plot"},
            {"direction", Boolean.FALSE, "Category.Plot"},
            {"startingAngle", Boolean.FALSE, "Category.Plot"},
            {"labelFormat", Boolean.FALSE, "Category.Sections"},
            {"labelFont", Boolean.FALSE, "Category.Sections"},
            {"labelPaint", Boolean.FALSE, "Category.Sections"},
            {"sectionToolTipFormat", Boolean.FALSE, "Category.ToolTips"}
        };
        
        // and these indexed properties...
        Object[][] indexedProps = new Object[][] { 
            {"sectionPaint", Boolean.FALSE}
        };

        // create property descriptors for the regular properties...
        List pds = new java.util.ArrayList();
        for (int i = 0; i < props.length; i++) {
            try {
                String name = (String) props[i][0];
                PropertyDescriptor pd = new PropertyDescriptor(name, 
                        JPieChart.class);
                Boolean expert = (Boolean) props[i][1];
                pd.setExpert(expert.booleanValue());
                String desc = localizationResources.getString(prefix + name);
                pd.setValue("category", localizationResources.getString(
                        (String) props[i][2]));
                pd.setShortDescription(desc);
                pds.add(pd);
            }
            catch (IntrospectionException e) {
                // swallow...
            }
        }

        // create property descriptors for the indexed properties...
        for (int i = 0; i < indexedProps.length; i++) {
            try {
                String name = (String) props[i][0];
                PropertyDescriptor pd = new IndexedPropertyDescriptor(name, 
                        JPieChart.class);
                Boolean expert = (Boolean) props[i][1];
                pd.setExpert(expert.booleanValue());
                String desc = localizationResources.getString(prefix + name);
                pd.setShortDescription(desc);
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
            e1 = new EventSetDescriptor(JPieChart.class, "sectionClick", 
                    SectionClickListener.class, "onSectionClick");
            e1.setInDefaultEventSet(true);
            e1.setExpert(false);
            e1.setPreferred(true);
        }
        catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getEventSetDescriptors();
        }
        return new EventSetDescriptor[] {e1};
    }
    
    /* (non-Javadoc)
     * @see java.beans.SimpleBeanInfo#getIcon(int)
     */
    @Override
    public Image getIcon(int iconKind) {       
        if (iconKind == BeanInfo.ICON_COLOR_16x16) {
            URL imageURL = this.getClass().getClassLoader().getResource(
                "org/jfree/beans/pie16.png");
            if (imageURL != null) {
                ImageIcon temp = new ImageIcon(imageURL);  
                // use ImageIcon because it waits for the image to load...
                return temp.getImage();
            }
            else {
                return super.getIcon(iconKind);
            }
        }
        else {
            return super.getIcon(iconKind);
        }
    }

}
