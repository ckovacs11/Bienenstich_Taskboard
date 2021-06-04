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
public class TaskboardTable extends JTable {

    public static final int TASK = 100;
    public static final int TASK_ID = 101;
    public static final int TASKNAME = 1;
    protected TaskTable.TreeTableCellRenderer tree;
    protected TaskTableModel model;
    protected TreeTableModelAdapter modelAdapter;
    protected TaskTreeTableCellRenderer renderer;
    protected TaskTable.ExpansionHandler expansion;

    Vector tasks = new Vector();
    /**
     * Constructor for EventsTable.
     */
    public TaskboardTable() {
        
        super();
        setModel(new TaskboardTableModel());
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

        refresh();
    }

    public void initTable(CalendarDate d) {
        tasks = (Vector) TaskboardListImpl.getTasks();
        //User-Story
        getColumnModel().getColumn(0).setPreferredWidth(80);
        //getColumnModel().getColumn(0).setMaxWidth(80);
        //name
        getColumnModel().getColumn(1).setPreferredWidth(80);
        //getColumnModel().getColumn(1).setMaxWidth(80);
        //Desc
        getColumnModel().getColumn(2).setPreferredWidth(200);
        //getColumnModel().getColumn(2).setMaxWidth(200);
        //Points
        getColumnModel().getColumn(3).setPreferredWidth(50);
        getColumnModel().getColumn(3).setMaxWidth(50);
        //Progress
        getColumnModel().getColumn(4).setPreferredWidth(150);
        //getColumnModel().getColumn(4).setMaxWidth(150);
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
                TaskboardTask ev = (TaskboardTask)getModel().getValueAt(row, TASK);
                comp.setForeground(java.awt.Color.gray);
                if (CurrentDate.get().after(CalendarDate.today())) {
                  comp.setForeground(java.awt.Color.black);
                }
                return comp;
            }
        };

    }

    class TaskboardTableModel extends AbstractTableModel {

        //set display column names in taskboard panel
        String[] columnNames = {
            Local.getString("User-Story"),
            Local.getString("Task"),
                Local.getString("Description"), Local.getString("Points"), Local.getString("Progress")
        };

        TaskboardTableModel() {
            super();
        }

        //sets the number of columns that will be visible in the taskboard columns
        public int getColumnCount() {
            return 5;
        }

        public int getRowCount() {
            int i;
            try {
                i = tasks.size();
            }
            catch(NullPointerException e) {
                i = 1;
            }
            return i;
        }

        public Object getValueAt(int row, int col) throws NullPointerException {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //used to format CalendarDate properly
            CalendarDate date = new CalendarDate();
               TaskboardTask spr = (TaskboardTask)tasks.get(row);
               if (col == 0) {
                   return "User-Story";
               }
               else if (col == 1) {
                   
                   return spr.getName();
               }
               else if (col == 2) {
                   
                   return spr.getDescription();
               }
               else if (col == 3) {
                   return spr.getPoints();
               }
               else if (col == 4) {
                   return spr.getProgress();
               }              
               else {
                   return spr;
               }
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }
    }
}
