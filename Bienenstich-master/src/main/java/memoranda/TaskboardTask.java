package main.java.memoranda;

import nu.xom.Attribute;
import nu.xom.Element;

public class TaskboardTask {
    
    private String name;
    private String description;
    private String progress;
    private int points;
    //private Team
    
    private Element _element = null;
    private TaskboardList _tl = null;
    
    public TaskboardTask(Element el) {
        _element = el;
    }
    
    public TaskboardTask(Element taskElement, TaskboardList list) {
        _element = taskElement;
        _tl = list;
    }
    
    public String getName() {
        Attribute a = _element.getAttribute("name");
        if(a == null) {
            return null;
        }
        return a.getValue();
    }

    

    public String getDescription() {
        
        Attribute a = _element.getAttribute("description");
        if(a == null) {
            return null;
        }
        return a.getValue();
        
    }
    
    public String getProgress() {
        
        Attribute a = _element.getAttribute("progress");
        if(a == null) {
            return null;
        }
        return a.getValue();
        
    }

    
    //get the points value of a task
    public String getPoints() {
        Attribute a = _element.getAttribute("points");
        String s = null;
        if(a == null) {
            return s;
        }
        else {
            s = a.getValue();
        }
        return s;
    }
    
    //set the points value of a task
    public void setPoints(String s) {
        setAttr("points", s);
    }
    
    public void setProgress(String s) {
        setAttr("progress", s);
    }
    
    public void setName(String s) {
        setAttr("name", s);
    }
    
    public void setDescription(String s) {
        setAttr("description", s);
    }
    
    private void setAttr(String a, String value) {
        Attribute attr = _element.getAttribute(a);
        if (attr == null) {
           _element.addAttribute(new Attribute(a, value));
        }
        else {
            attr.setValue(value);
        }
    }
    
    
    

}
