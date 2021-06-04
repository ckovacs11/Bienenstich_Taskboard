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
public class BacklogListImpl implements BacklogList {

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
    public BacklogListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
		buildElements(_root);
    }

    public BacklogListImpl(Project prj) {
            _root = new Element("BacklogList");
            _doc = new Document(_root);
            _project = prj;
    }

    public BacklogListImpl() {
        _root = new Element("BacklogList");
        _doc = new Document(_root);
    }

	public Project getProject() {
		return _project;
	}

	/*
	 * Build the hashtable recursively
	 */
	private void buildElements(Element parent) {
		Elements els = parent.getChildElements("Backlog");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			elements.put(el.getAttribute("id").getValue(), el);
			buildElements(el);
		}
	}

    public static Collection getActiveBacklog(CalendarDate passedInDate) {
        Vector v = new Vector();
        Elements Backlog = _root.getChildElements("Backlog");

        for (int i = 0; i < Backlog.size(); i++) {
            Backlog spr = new BacklogImpl(Backlog.get(i));
            v.add(spr);
        }
        return v;
    }


    public Backlog createBacklog(CalendarDate dueDate, String name, String description) {
	    Element el = new Element("Backlog");
        el.addAttribute(new Attribute("id", Util.generateId()));
        el.addAttribute(new Attribute("dueDate", dueDate.toString()));
        el.addAttribute(new Attribute("name", name));
        el.addAttribute(new Attribute("description", description));
        _root.appendChild(el);

        Util.debug("Created Backlog " + name);

        return new BacklogImpl(el, this);
    }

   public void removeBacklog(Backlog spr) {
        ParentNode parent = spr.getContent().getParent();
        parent.removeChild(spr.getContent());
    }

    public Backlog getBacklog(String id) {
        Util.debug("Getting Backlog " + id);
        return new BacklogImpl(getBacklogElement(id), this); //todo
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
    private Element getBacklogElement(String id) {
               
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
			Util.debug("Backlog " + id + " cannot be found in project " + _project.getTitle());
		}
		return el;
    }
    
     private Collection convertToBacklogObjects(Elements Backlog) {
        Vector v = new Vector();

        for (int i = 0; i < Backlog.size(); i++) {
            Backlog spr = new BacklogImpl(Backlog.get(i), this);
            v.add(spr);
        }
        return v;
    }
}
