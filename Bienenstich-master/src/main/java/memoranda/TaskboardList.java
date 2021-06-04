package main.java.memoranda;
import java.util.Collection;

import nu.xom.*;

public interface TaskboardList {
    
    public Project getProject();
    public TaskboardTask getTask(String name);
    
    public Element getElement(String name);
    public TaskboardTask createTaskboardTask(String name, String description, String progress, String points);
    
    boolean removeTask(String name);
    
    nu.xom.Document getXMLContent();
    
    

}
