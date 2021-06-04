package test.java;

import static org.junit.Assert.*;


import main.java.memoranda.Task;
import main.java.memoranda.TaskList;
import main.java.memoranda.TaskListImpl;
import main.java.memoranda.date.CalendarDate;

import nu.xom.Element;


import org.junit.After;
import org.junit.Test;

public class TaskImplTest {
    

    TaskListImpl tl;
    Task t;
    Element _element = null;
    TaskList _tl = null; 
    CalendarDate start = null;
    CalendarDate end = null;
    String txt = null;
    String sub = null;
    int points;
    long effort = 0;
    String parent = null;
    


    @org.junit.Before
    public void setUp() throws Exception {
        tl = new TaskListImpl();
        start = new CalendarDate(3, 3, 1999);
        end = new CalendarDate(4, 3, 1999);
        parent = "one";
        points = 5;
        t = tl.createTask(start, end, txt, points, effort, sub, parent);
    }
    @After
    public void tearDown() throws Exception {
    }

    
    
    //test the getPoints() method
    @Test
    public void testGetPoints() {
        int expected = 5;
        assertEquals(t.getPoints(), expected);       
    }

    //test the setPoints(int) method
    @Test
    public void testSetPoints() {
        int expected2 = 10;
        t.setPoints(10);
        assertEquals(t.getPoints(), expected2);
        
    }

}
