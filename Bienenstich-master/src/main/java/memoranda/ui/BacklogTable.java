/**
 * EventsTable.java
 * Created on 09.03.2003, 9:52:02 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.ui;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.ui.treetable.TreeTableModelAdapter;
import main.java.memoranda.util.Local;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 *
 */
/*$Id: EventsTable.java,v 1.6 2004/10/11 08:48:20 alexeya Exp $*/
public class BacklogTable extends JTable {

    public static final int BACKLOG = 100;
    public static final int BACKLOG_ID = 101;
    protected TaskTable.TreeTableCellRenderer tree;
    protected TaskTableModel model;
    protected TreeTableModelAdapter modelAdapter;
    protected TaskTreeTableCellRenderer renderer;
    protected TaskTable.ExpansionHandler expansion;

    Vector backlog = new Vector();
    /**
     * Constructor for EventsTable.
     */
    public BacklogTable() {
        super();
        setModel(new BacklogTableModel());
        initTable(CurrentDate.get());
        this.setShowGrid(false);
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                //updateUI();
                initTable(d);
            }
        });
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl, TaskboardList tbl, 
                                      ResourcesList rl, SprintList sprintList, TeamList teal, BacklogList back1) {
            }

            public void projectWasChanged() {
                //initTable();
                tableChanged();
            }
        });
    }

    public void tableChanged() {
        //model.fireUpdateCache();
        //model.fireTreeStructureChanged();
        //expansion.expand(tree);
        //updateUI();
        refresh();
    }

    public void initTable(CalendarDate d) {
        backlog = (Vector)BacklogListImpl.getActiveBacklog(d);
        getColumnModel().getColumn(0).setPreferredWidth(250);
        getColumnModel().getColumn(0).setMaxWidth(250);
        getColumnModel().getColumn(1).setPreferredWidth(120);
        getColumnModel().getColumn(1).setMaxWidth(120);
	clearSelection();
        updateUI();
    }

    public void refresh() {
        initTable(CurrentDate.get());
    }

     public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
                Component comp;
                comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Backlog ev = (Backlog)getModel().getValueAt(row, BACKLOG);
                comp.setForeground(java.awt.Color.gray);
                if (CurrentDate.get().after(CalendarDate.today())) {
                  comp.setForeground(java.awt.Color.black);
                }
                return comp;
            }
        };
    }

    class BacklogTableModel extends AbstractTableModel {

        //set display column names in Backlog panel
        String[] columnNames = {
            Local.getString("Name"),
            Local.getString("Due Date"),
                Local.getString("Description")
        };

        BacklogTableModel() {
            super();
        }

        //sets the number of columns that will be visible in the Backlog columns
        public int getColumnCount() {
            return 3;
        }

        public int getRowCount() {
			int i;
			try {
				i = backlog.size();
			}
			catch(NullPointerException e) {
				i = 1;
			}
			return i;
        }

        public Object getValueAt(int row, int col) throws NullPointerException {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //used to format CalendarDate properly
            CalendarDate date = new CalendarDate();
            String description = null;
               Backlog spr = (Backlog)backlog.get(row);
               if (col == 0) {
                   return spr.getName();
               }
               else if (col == 1) {
                   date = spr.getDueDate();
                   //used formatted calendar date for display so month shows up correctly
                   return sdf.format(date.getCalendar().getTime());
               }
               else if (col == 2) {
                   return spr.getDescription();
               }
               else if (col == BACKLOG_ID)
                   return spr.getId();
               else
                   return spr;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }
    }
}
