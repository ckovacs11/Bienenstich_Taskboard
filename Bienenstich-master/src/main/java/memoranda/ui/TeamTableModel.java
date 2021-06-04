/**
 * TaskTableModel.java
 * -----------------------------------------------------------------------------
 * Project           Memoranda
 * Package           net.sf.memoranda.ui
 * Original author   Alex V. Alishevskikh
 *                   [alexeya@gmail.com]
 * Created           18.05.2005 15:16:11
 * Revision info     $RCSfile: TaskTableModel.java,v $ $Revision: 1.7 $ $State: Exp $
 *
 * Last modified on  $Date: 2005/12/01 08:12:26 $
 *               by  $Author: alexeya $
 *
 * @VERSION@
 *
 * @COPYRIGHT@
 *
 * @LICENSE@
 */

package main.java.memoranda.ui;

import javax.swing.event.*;
import javax.swing.tree.TreePath;

import main.java.memoranda.*;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.ui.treetable.AbstractTreeTableModel;
import main.java.memoranda.ui.treetable.TreeTableModel;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;

import java.util.Hashtable;

import nu.xom.Element;

/**
 * JAVADOC:
 * <h1>TaskTableModel</h1>
 *
 * @version $Id: TaskTableModel.java,v 1.7 2005/12/01 08:12:26 alexeya Exp $
 * @author $Author: alexeya $
 */
public class TeamTableModel extends AbstractTreeTableModel implements TreeTableModel {

//    String[] columnNames = {"", Local.getString("To-do"),
//            Local.getString("Start date"), Local.getString("End date"),
//            Local.getString("Priority"), Local.getString("Status"),
//            "% " + Local.getString("done") };
    String[] columnNames = {"", Local.getString("Name")};

    protected EventListenerList listenerList = new EventListenerList();

    /**
     * JAVADOC: Constructor of <code>TaskTableModel</code>
     *
     * @param root
     */
    public TeamTableModel(){
        super(CurrentProject.get());
    }

    /**
     * @see main.java.memoranda.ui.treetable.TreeTableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * @see main.java.memoranda.ui.treetable.TreeTableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * @see main.java.memoranda.ui.treetable.TreeTableModel#getValueAt(java.lang.Object,
     *      int)
     */
    public Object getValueAt(Object node, int column) {
        if (node instanceof Project)
            return null;
        if (node instanceof Member) {
            Member m = (Member) node;
            switch (column) {
                case TeamTable.TEAM_ID:
                    return m.getId();
                case TeamTable.TEAM:
                    return m;
                case TeamTable.TEAM_NAME:
                    return m.getName();
                default:
                    return null;
            }
        }
        Team t = (Team) node;
        switch (column) {
            case TeamTable.TEAM_ID:
                return t.getId();
            case TeamTable.TEAM:
                return t;
            case TeamTable.TEAM_NAME:
                return t.getName();
            default:
                return null;
        }
    }

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if (parent instanceof Project) {
            return CurrentProject.getTeamList().getAllRootTeams().size();
        } if (parent instanceof Member) {
            return 0;
        }
        Team t = (Team) parent;
        return t.getMembers().size();
    }

    /**
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        if (index >= 0) {
            return new TeamImpl(new Element("team"), null);
        }
//        if (parent instanceof Project)
//            return CurrentProject.getTeamList().getAllRootTeams().toArray()[index];
//        return null;
        Team t = (Team) parent;
        return t.getMembers().toArray()[index];
    }

    /**
     * @see main.java.memoranda.ui.treetable.TreeTableModel#getColumnClass(int)
     */
    public Class getColumnClass(int column) {
        try {
            switch (column) {
                case 1:
                    return TreeTableModel.class;
                case 0:
                    return TeamTable.class;
                case 4:
                case 5:
                    return Class.forName("java.lang.String");
                case 2:
                case 3:
                    return Class.forName("java.util.Date");
                case 6:
                    return Class.forName("java.lang.Integer");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void fireTreeStructureChanged(){
        fireTreeStructureChanged( this,
                new Object[]{getRoot()},
                new int[0],
                new Object[0]
        );
    }


    /**
     * Update cached data
     */
//    public void fireUpdateCache(){
//        activeOnly = check_activeOnly();
//    }

//    public static boolean check_activeOnly(){
//        Object o = Context.get("SHOW_ACTIVE_TASKS_ONLY");
//        if(o == null) return false;
//        return o.toString().equals("true");
//    }
//
//    public boolean activeOnly(){
//        return activeOnly;
//    }

    public boolean isCellEditable(Object node, int column) {
        if(column == 6) return true;
        return super.isCellEditable(node, column);
    }

}