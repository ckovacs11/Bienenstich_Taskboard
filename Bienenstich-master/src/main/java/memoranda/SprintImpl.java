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
public class SprintImpl implements Sprint {

    private Element _elem = null;
    private Project _project = null;
    private ArrayList<UserStoryImpl> userStories = new ArrayList<UserStoryImpl>();
    private boolean _isActive;
    private String _name;
    private CalendarDate _startDate;
    private CalendarDate _endDate;
    private SprintListImpl _sprintList = null;
    private int _totalPoints;
    private int _remainingPoints;
    private int _id;

    /**
     * Constructor for EventImpl.
     */
    public SprintImpl(Element elem) {
        _elem = elem;
    }

    public SprintImpl(Element elem, SprintListImpl sprintList){
        _elem = elem;
        _sprintList = sprintList;
    }

    public SprintImpl(int id, String name, CalendarDate startDate, CalendarDate endDate) {
        _isActive = true; //when creating a sprint it is automatically set to active
        _id = id;
        _startDate = startDate;
        _endDate = endDate;
        _name = name;
    }

    public boolean getIsActive(){
        return _isActive;
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
    public CalendarDate getStartDate() {
        Attribute a = _elem.getAttribute("startDate");
        if (a != null) {
            return new CalendarDate(a.getValue());
        }
        else {
            return null;
        }
    }
    /**
     * @see Event#getEndDate()
     */
    public CalendarDate getEndDate() {
        Attribute a = _elem.getAttribute("endDate");
        if (a != null) return new CalendarDate(a.getValue());
        return null;
        //return _endDate;
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
