/**
 * EventsManager.java Created on 08.03.2003, 12:35:19 Alex Package:
 * net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Util;
import nu.xom.*;

import java.util.*;

//import nu.xom.Comment;

/**
 *  
 */
/*$Id: EventsManager.java,v 1.11 2004/10/06 16:00:11 ivanrise Exp $*/
public class BacklogManager {
/*	public static final String NS_JNEVENTS =
		"http://www.openmechanics.org/2003/jnotes-events-file";
*/
	//public static final int NO_REPEAT = 0;
	//public static final int REPEAT_DAILY = 1;
	//public static final int REPEAT_WEEKLY = 2;
	//public static final int REPEAT_MONTHLY = 3;
	//public static final int REPEAT_YEARLY = 4;

	public static ArrayList<Backlog> Backlog = new ArrayList<>();

	public static Document _doc = null;
	static Element _root = null;
	private Project _project = null;

	static {
		//CurrentStorage.get().openBacklogManager();
		if (_doc == null) {
			_root = new Element("Backloglist");
/*			_root.addNamespaceDeclaration("jnBacklog", NS_JNBacklog);
			_root.appendChild(
				new Comment("This is JNotes 2 data file. Do not modify.")); */
			_doc = new Document(_root);
		} else
			_root = _doc.getRootElement();

	}

	public static ArrayList<Backlog> getBacklog(){
		/*for(int i = 0; i < Backlog.size(); i++){
			System.out.println("in getBacklog " + Backlog.get(i).getName());
		}*/
		return Backlog;
	}

	public static Element createBacklog(CalendarDate dueDate, String name, String description) {
		Element el = new Element("Backlog");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("dueDate", dueDate.toString()));
		el.addAttribute(new Attribute("name", name));
		el.addAttribute(new Attribute("description", description));
		_root.appendChild(el);
		return el;
	}

	//TODO
	/*public static Collection getActiveBacklog() {

		Collection activeBacklog = null;

		for (int i = 0; i < Backlog.size(); i++){
			if(CalendarDate.today().inPeriod(Backlog.get(i).getDueDate())) {
				//Backlog is active add too collection
				activeBacklog.add(Backlog.get(i));
			}
		}

		return activeBacklog;
	}*/

	//todo returns all Backlog, doesnt filter by active attribute. need to implement this
	public static Collection getActiveBacklog(CalendarDate passedInDate) {
		Vector v = new Vector();
		Elements Backlog = _root.getChildElements("Backlog");

		for (int i = 0; i < Backlog.size(); i++) {
			Backlog spr = new BacklogImpl(Backlog.get(i));
			v.add(spr);
		}
		return v;
	}

	public static Backlog getBacklog(CalendarDate date, int hh, int mm) {
		Day d = getDay(date);
		if (d == null)
			return null;
		Elements els = d.getElement().getChildElements("Backlog");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			if ((new Integer(el.getAttribute("hour").getValue()).intValue()
				== hh)
				&& (new Integer(el.getAttribute("min").getValue()).intValue()
					== mm))
				return new BacklogImpl(el);
		}
		return null;
	}

	public static void removeBacklog(Backlog spr) {
		ParentNode parent = spr.getContent().getParent();
		parent.removeChild(spr.getContent());
	}

	private static Year getYear(int y) {
		Elements yrs = _root.getChildElements("year");
		String yy = new Integer(y).toString();
		for (int i = 0; i < yrs.size(); i++)
			if (yrs.get(i).getAttribute("year").getValue().equals(yy))
				return new Year(yrs.get(i));
		//return createYear(y);
		return null;
	}

	private static Day getDay(CalendarDate date) {
		Year y = getYear(date.getYear());
		if (y == null)
			return null;
		Month m = y.getMonth(date.getMonth());
		if (m == null)
			return null;
		return m.getDay(date.getDay());
	}

	static class Year {
		Element yearElement = null;

		public Year(Element el) {
			yearElement = el;
		}

		public int getValue() {
			return new Integer(yearElement.getAttribute("year").getValue())
				.intValue();
		}

		public Month getMonth(int m) {
			Elements ms = yearElement.getChildElements("month");
			String mm = new Integer(m).toString();
			for (int i = 0; i < ms.size(); i++)
				if (ms.get(i).getAttribute("month").getValue().equals(mm))
					return new Month(ms.get(i));
			//return createMonth(m);
			return null;
		}

		public Element getElement() {
			return yearElement;
		}

	}

	static class Month {
		Element mElement = null;

		public Month(Element el) {
			mElement = el;
		}

		public int getValue() {
			return new Integer(mElement.getAttribute("month").getValue())
				.intValue();
		}

		public Day getDay(int d) {
			if (mElement == null)
				return null;
			Elements ds = mElement.getChildElements("day");
			String dd = new Integer(d).toString();
			for (int i = 0; i < ds.size(); i++)
				if (ds.get(i).getAttribute("day").getValue().equals(dd))
					return new Day(ds.get(i));
			//return createDay(d);
			return null;
		}

		private Day createDay(int d) {
			Element el = new Element("day");
			el.addAttribute(new Attribute("day", new Integer(d).toString()));
			el.addAttribute(
				new Attribute(
					"date",
					new CalendarDate(
						d,
						getValue(),
						new Integer(
							((Element) mElement.getParent())
								.getAttribute("year")
								.getValue())
							.intValue())
						.toString()));

			mElement.appendChild(el);
			return new Day(el);
		}

		public Vector getDays() {
			if (mElement == null)
				return null;
			Vector v = new Vector();
			Elements ds = mElement.getChildElements("day");
			for (int i = 0; i < ds.size(); i++)
				v.add(new Day(ds.get(i)));
			return v;
		}

		public Element getElement() {
			return mElement;
		}

	}

	static class Day {
		Element dEl = null;

		public Day(Element el) {
			dEl = el;
		}

		public int getValue() {
			return new Integer(dEl.getAttribute("day").getValue()).intValue();
		}

		/*
		 * public Note getNote() { return new NoteImpl(dEl);
		 */

		public Element getElement() {
			return dEl;
		}
	}
/*
	static class EventsVectorSorter {

		private static Vector keys = null;

		private static int toMinutes(Object obj) {
			Event ev = (Event) obj;
			return ev.getHour() * 60 + ev.getMinute();
		}

		private static void doSort(int L, int R) { // Hoar's QuickSort
			int i = L;
			int j = R;
			int x = toMinutes(keys.get((L + R) / 2));
			Object w = null;
			do {
				while (toMinutes(keys.get(i)) < x) {
					i++;
				}
				while (x < toMinutes(keys.get(j))) {
					j--;
				}
				if (i <= j) {
					w = keys.get(i);
					keys.set(i, keys.get(j));
					keys.set(j, w);
					i++;
					j--;
				}
			}
			while (i <= j);
			if (L < j) {
				doSort(L, j);
			}
			if (i < R) {
				doSort(i, R);
			}
		}

		public static void sort(Vector theKeys) {
			if (theKeys == null)
				return;
			if (theKeys.size() <= 0)
				return;
			keys = theKeys;
			doSort(0, keys.size() - 1);
		}

	}
*/
}
