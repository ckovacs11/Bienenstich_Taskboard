/**
 * DefaultEventNotifier.java Created on 10.03.2003, 21:18:02 Alex Package:
 * net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.ui.SprintNotificationDialog;

/**
 *  
 */
/*$Id: DefaultEventNotifier.java,v 1.4 2004/01/30 12:17:41 alexeya Exp $*/
public class DefaultSprintNotifier implements SprintNotificationListener {

	/**
	 * Constructor for DefaultEventNotifier.
	 */
	public DefaultSprintNotifier() {
		super();
	}

	/**
	 * @see SprintNotificationListener eventIsOccured(Sprint)
	 */
	public void sprintIsOccured(Sprint spr) {
		new SprintNotificationDialog(
			"Memoranda sprint",
			spr.getText(), //need to come back and fix this
			spr.getText());
	}
	/**
	 * @see EventNotificationListener#eventsChanged()
	 */
	public void sprintChanged() {
		//
	}

	
}
