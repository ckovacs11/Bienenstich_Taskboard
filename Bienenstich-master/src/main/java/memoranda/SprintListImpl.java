/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Util;
import nu.xom.*;

import java.util.*;
//import nu.xom.converters.*;
//import org.apache.xerces.dom.*;
//import nux.xom.xquery.XQueryUtil;

/**
 * 
 */
/*$Id: TaskListImpl.java,v 1.14 2006/07/03 11:59:19 alexeya Exp $*/
public class SprintListImpl implements SprintList {

    private Project _project = null;
    private Document _doc = null;
    private static Element _root = null;

	/*
	 * Hastable of "task" XOM elements for quick searching them by ID's
	 * (ID => element)
	 */
	private Hashtable elements = new Hashtable();

    /**
     * Constructor for TaskListImpl.
     */
    public SprintListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
		buildElements(_root);
    }

    public SprintListImpl(Project prj) {
            _root = new Element("sprintList");
            _doc = new Document(_root);
            _project = prj;
    }

    public SprintListImpl() {
        _root = new Element("sprintList");
        _doc = new Document(_root);
    }

	public Project getProject() {
		return _project;
	}

	/*
	 * Build the hashtable recursively
	 */
	private void buildElements(Element parent) {
		Elements els = parent.getChildElements("sprint");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			elements.put(el.getAttribute("id").getValue(), el);
			buildElements(el);
		}
	}

    public static Collection getActiveSprints(CalendarDate passedInDate) {
        Vector v = new Vector();
        Elements sprints = _root.getChildElements("sprint");

        for (int i = 0; i < sprints.size(); i++) {
            Sprint spr = new SprintImpl(sprints.get(i));
            v.add(spr);
        }
        return v;
    }


    public Sprint createSprint(CalendarDate startDate, CalendarDate endDate, String name) {
	    Element el = new Element("sprint");
        el.addAttribute(new Attribute("id", Util.generateId()));
        el.addAttribute(new Attribute("startDate", startDate.toString()));
        el.addAttribute(new Attribute("endDate", endDate.toString()));
        el.addAttribute(new Attribute("name", name));
        _root.appendChild(el);

        Util.debug("Created sprint " + name);

        return new SprintImpl(el, this);
    }

   public void removeSprint(Sprint spr) {
        ParentNode parent = spr.getContent().getParent();
        parent.removeChild(spr.getContent());
    }

    public Sprint getSprint(String id) {
        Util.debug("Getting sprint " + id);
        return new SprintImpl(getSprintElement(id), this); //todo
    }
   /**
     * @see TaskList#getXMLContent()
     */	 
    public Document getXMLContent() {
        return _doc;
    }

    /*
     * private methods below this line
     */
    private Element getSprintElement(String id) {
               
		/*Nodes nodes = XQueryUtil.xquery(_doc, "//task[@id='" + id + "']");
        if (nodes.size() > 0) {
            Element el = (Element) nodes.get(0);
            return el;            
        }
        else {
            Util.debug("Task " + id + " cannot be found in project " + _project.getTitle());
            return null;
        } */
		Element el = (Element)elements.get(id);
		if (el == null) {
			Util.debug("Sprint " + id + " cannot be found in project " + _project.getTitle());
		}
		return el;
    }
    
     private Collection convertToSprintObjects(Elements sprints) {
        Vector v = new Vector();

        for (int i = 0; i < sprints.size(); i++) {
            Sprint spr = new SprintImpl(sprints.get(i), this);
            v.add(spr);
        }
        return v;
    }
}
