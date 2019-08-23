Orson 0.5.0
===========

(C)opyright 2007, by Object Refinery Limited.

4 July 2007


Introduction
------------
Orson is a collection of JavaBeans for charting, backed by the popular 
JFreeChart library.  This package provides a simple way for developers to use 
the most common chart types/features from JFreeChart from within a JavaBeans 
design time environment (such as NetBeans).

THIS IS A PREVIEW RELEASE AND THEREFORE THE API IS SUBJECT TO CHANGE IN FUTURE
RELEASES.

For more information about Orson, refer to:

    http://www.jfree.org/orson/


Licence
-------
Orson is licensed under the terms of the GNU Lesser General Public 
Licence (LGPL).  This is the same licence as used by JFreeChart.


Runtime Requirements
--------------------
At runtime, Orson requires:

- JRE 1.5.0 or later (only tested with 1.6.0 so far);
- jfreechart-1.0.6.jar (or later);
- jcommon-1.0.0.jar (or later).

The JFreeChart and JCommon jar files can be found in the 'lib' subdirectory.
Due to API additions and bug fixes, JFreeChart 1.0.5 and earlier versions will 
not work.


Build
-----
To rebuild Orson, simply run the Ant script in the 'ant' subdirectory.


Installation
------------
Installation will vary according to the development environment.  To date,
we've tested only with NetBeans, by selecting the "Add from JAR..." option 
in the Palette Manager.


To Do List
----------
Here are some things that still need to be done:

- stacked bar charts and stacked area charts;
- dials and thermometers;
- vector plots;
- implement property editors for "per series" attributes (such as the series
      paint and stroke), or "per section" attributes in the JPieChart case;
- implement property editors for datasets (or perhaps make the datasets
      JavaBeans as well);
- complete popup menu support;
- item labels;
- consider which additional JFreeChart/Plot/Dataset methods need to be exposed
      via the API for each bean;
- should more inherited Swing properties be exposed?
- which inherited Swing events should be exposed via BeanInfo?
- provide alternative localisations of the LocalizationBundle.properties file;
- add support for serialization of beans?
- implement some form of chart theming, maybe linked with the current 
      LookAndFeel?


Feedback
--------
Please send comments, suggestions and bug reports to the project mailing list:

    https://sourceforge.net/mail/?group_id=200108