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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.ArrayList;

/**
 * 
 */

/*$Id: EventImpl.java,v 1.9 2004/10/06 16:00:11 ivanrise Exp $*/
public class BacklogImpl implements Backlog {

    private Element _elem = null;
    private Project _project = null;
    private ArrayList<UserStoryImpl> userStories = new ArrayList<UserStoryImpl>();
    private boolean _isActive;
    private String _name;
    private CalendarDate _dueDate;
    private BacklogListImpl _BacklogList = null;
    private int _totalPoints;
    private int _remainingPoints;
    private int _id;
    private String _description;

    /**
     * Constructor for EventImpl.
     */
    public BacklogImpl(Element elem) {
        _elem = elem;
    }

    public BacklogImpl(Element elem, BacklogListImpl BacklogList){
        _elem = elem;
        _BacklogList = BacklogList;
    }

    public BacklogImpl(int id, String name, CalendarDate dueDate, String description) {
        _isActive = true; //when creating a Backlog it is automatically set to active
        _id = id;
        _dueDate = dueDate;
        _name = name;
        _description = description;
    }

    public boolean getIsActive(){
        return _isActive;
    }

    public String getDescription() {
        return new String(_elem.getAttribute("description").getValue()).toString();
    }

    public String getName() {
            return new String(_elem.getAttribute("name").getValue()).toString();
        //return _name;
    }

    /**
     * @see Event#getText()
     */
    public String getText() {
        return _elem.getValue();
    }

    /**
     * @see Event#getContent()
     */
    public Element getContent() {
        return _elem;
    }

    /**
     * @see Event#getStartDate()
     */
    public CalendarDate getDueDate() {
        Attribute a = _elem.getAttribute("dueDate");
        if (a != null) {
            return new CalendarDate(a.getValue());
        }
        else {
            return null;
        }
    }
    /**
     * @see Event#getId()
     */
    public String getId() { //was int
        Attribute a = _elem.getAttribute("id");
        if (a != null) return a.getValue();
        return null;
        //return _id;
    }


}
