package main.java.memoranda;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import main.java.memoranda.util.Util;

import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;


public class TaskboardListImpl implements TaskboardList {
    
    private Project _project = null;
    private Document _doc = null;
    private static Element _root = null;
    private static Hashtable elements = new Hashtable();
    
    public TaskboardListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
        buildElements(_root);
    }
    
    public TaskboardListImpl(Project prj) {            
        _root = new Element("taskboardtasklist");
        _doc = new Document(_root);
        _project = prj;
    }

    public Project getProject() {
    return _project;
    }
    
    
    public TaskboardTask createTaskboardTask(String name, String description, String progress, String points) {
        Element el = new Element("taskboardtask");
        el.addAttribute(new Attribute("name", name));
        el.addAttribute(new Attribute("description", description));
        el.addAttribute(new Attribute("progress", progress));
        el.addAttribute(new Attribute("points", points));
        
        _root.appendChild(el);
        
        return new TaskboardTask(el, this);
    }
    
    public Element getElement(String name) {
        Element el = (Element) elements.get(name);
        return el;
    }
    
    public static Collection getTasks() {
        buildElements(_root);
        Elements els = _root.getChildElements("taskboardtask");
        Vector v = new Vector();
        
        for(int i = 0; i < els.size(); i++) {
            TaskboardTask t = new TaskboardTask(els.get(i));
            v.add(t);
            
        }
        return v;
    }
    
    public TaskboardTask getTask(String name) {
        Util.debug("Getting task " + name);
        Elements tasks = _root.getChildElements("taskboardtask");
        Vector v = new Vector();
        TaskboardTask t = null;
        for (int i = 0; i < tasks.size(); i++) {
            t = new TaskboardTask(tasks.get(i), this);
            if(t.getName().contentEquals(name)) {
                return t;
            }                      
        }
        return t;
                 
    }
    
    public boolean removeTask(String name) {
        boolean ret = false;
        
        Element el = (Element)elements.get(name);
        if(el == null) {
            return ret;
        }
        else {
            
            Elements tasks = _root.getChildElements("taskboardtask");
            for(int i = 0; i < tasks.size(); i++) {
                if(tasks.get(i).getAttribute("name").getValue().contentEquals(name)) {
                    _root.removeChild(tasks.get(i));
                }
            }
            
            
            elements.remove(name);
            
            
            ret = true;
            return ret;
        }
        
    }
    
    /*
     * Build the hashtable recursively
     */
    public static void buildElements(Element parent) {
        Elements els = parent.getChildElements("taskboardtask");
        for (int i = 0; i < els.size(); i++) {
            Element el = els.get(i);
            elements.put(el.getAttribute("name").getValue(), el);

        }
    }
    
   
    private Element getTaskboardTaskElement(String name) {
               
        //grab task from hashtable
        Element el = (Element)elements.get(name);
        
        //if task isn't found
        if (el == null) {
            Util.debug("Task " + name + " cannot be found in project " + _project.getTitle());
        }
        
        return el;
    }
    
    public Document getXMLContent() {
        return _doc;
    }

}
