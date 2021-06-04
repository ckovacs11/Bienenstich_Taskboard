/**
 * Sprint.java
 * Created on 08.03.2003, 12:21:40 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *-----------------------------------------------------
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;

import java.util.Date;

/**
 * 
 */
/*$Id: Sprint.java,v 1.4 2004/07/21 17:51:25 ivanrise Exp $*/
public interface UserStory {
    
    String getId();
    String getDescription();
    int getPoints();
    
}
