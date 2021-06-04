/**
 * DefaultEventNotifier.java Created on 10.03.2003, 21:18:02 Alex Package:
 * net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.ui.BacklogNotificationDialog;

/**
 *
 */
/*$Id: DefaultEventNotifier.java,v 1.4 2004/01/30 12:17:41 alexeya Exp $*/
public class DefaultBacklogNotifier implements BacklogNotificationListener {

    /**
     * Constructor for DefaultEventNotifier.
     */
    public DefaultBacklogNotifier() {
        super();
    }

    /**
     * @see SprintNotificationListener eventIsOccured(Sprint)
     */
    public void BacklogIsOccured(Backlog back) {
        new BacklogNotificationDialog(
                "Memoranda backlog",
                back.getText(), //need to come back and fix this
                back.getText());
    }
    /**
     * @see EventNotificationListener#eventsChanged()
     */
    public void BacklogChanged() {
        //
    }


}
