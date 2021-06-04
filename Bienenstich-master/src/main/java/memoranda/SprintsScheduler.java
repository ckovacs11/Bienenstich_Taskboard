/**
 * EventsScheduler.java
 * Created on 10.03.2003, 20:20:08 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.*;

/**
 *
 */
/*$Id: EventsScheduler.java,v 1.4 2004/01/30 12:17:41 alexeya Exp $*/
public class SprintsScheduler {

    static Vector _timers = new Vector();
    static Vector _listeners = new Vector();

    static Timer changeDateTimer = new Timer();

    static {
        addListener(new DefaultSprintNotifier());
    }

    public static void init() {

    }

    public static void cancelAll() {
        for (int i = 0; i < _timers.size(); i++) {
            SprintTimer t = (SprintTimer)_timers.get(i);
            t.cancel();
        }
    }
    
    public static Vector getScheduledSprints() {
        Vector v = new Vector();
        for (int i = 0; i < _timers.size(); i++) 
            v.add(((SprintTimer)_timers.get(i)).getSprint());
        return v;
    }


    public static void addListener(SprintNotificationListener enl) {
        _listeners.add(enl);
    }

    public static boolean isSprintScheduled() {
        return _timers.size() > 0;
    }
        
    private static void notifyListeners(Sprint spr) {
        for (int i = 0; i < _listeners.size(); i++)
            ((SprintNotificationListener)_listeners.get(i)).sprintIsOccured(spr);
    }

    private static void notifyChanged() {
        for (int i = 0; i < _listeners.size(); i++)
            ((SprintNotificationListener)_listeners.get(i)).sprintChanged();
    }

    private static Date getMidnight() {
       Calendar cal = Calendar.getInstance();
       cal.set(Calendar.HOUR_OF_DAY, 0);
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND,0);
       cal.set(Calendar.MILLISECOND,0);
       cal.add(Calendar.DAY_OF_MONTH,1);
       return cal.getTime();
    }

    static class NotifyTask extends TimerTask {
        
        SprintTimer _timer;

        public NotifyTask(SprintTimer t) {
            super();            
            _timer = t;
        }
        
        public void run() {            
            _timer.cancel();
            _timers.remove(_timer);
            notifyListeners(_timer.getSprint());
            notifyChanged();
        }
    }
    
    static class SprintTimer extends Timer {
        Sprint _sprint;
        
        public SprintTimer(Sprint spr) {
            super();
            _sprint = spr;
        }
        
        public Sprint getSprint() {
            return _sprint;
        }
    }


}
