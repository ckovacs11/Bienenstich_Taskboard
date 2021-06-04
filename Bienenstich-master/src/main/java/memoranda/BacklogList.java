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
public interface BacklogList {

	Project getProject();
    Backlog getBacklog(String id);

    Backlog createBacklog(CalendarDate dueDate, String name, String description);

    void removeBacklog(Backlog Backlog);

    //public Collection getActiveBacklog(CalendarDate passedInDate);

    nu.xom.Document getXMLContent();

}
