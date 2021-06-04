/**
 * EventImpl.java
 * Created on 08.03.2003, 13:20:13 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Local;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 * 
 */
/*$Id: EventImpl.java,v 1.9 2004/10/06 16:00:11 ivanrise Exp $*/
public class UserStoryImpl implements UserStory {

    private Element _elem = null;
    private String _description = null;
    private int _points = 0;


    /**
     * Constructor for EventImpl.
     */
    public UserStoryImpl(Element elem) {
        _elem = elem;
        _description = null;
        _points = 0;
    }

    /**
    * Overloaded constructor to pass in description and points upon creation
    */
    public UserStoryImpl(Element elem, String description, int points) {
        _elem = elem;
        _description = description;
        _points = points;
    }

    /**
     * @see Event#getId()
     */
    public String getId() {
        Attribute a = _elem.getAttribute("id");
        if (a != null) return a.getValue();
        return null;
    }

    public String getDescription(){
        return _description;
    }
    public int getPoints() {
        return _points;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public void setPoints(int points) {
        _points = points;
    }


}
