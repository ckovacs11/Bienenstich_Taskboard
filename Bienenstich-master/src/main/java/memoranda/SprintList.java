/**
 * TaskList.java
 * Created on 21.02.2003, 12:25:16 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;
import nu.xom.Element;

import java.util.Collection;
import java.util.Date;

/**
 * 
 */
/*$Id: TaskList.java,v 1.8 2005/12/01 08:12:26 alexeya Exp $*/
public interface SprintList {

	Project getProject();
    Sprint getSprint(String id);

    Sprint createSprint(CalendarDate startDate, CalendarDate endDate, String name);

    void removeSprint(Sprint sprint);

    //public Collection getActiveSprints(CalendarDate passedInDate);

    nu.xom.Document getXMLContent();

}
