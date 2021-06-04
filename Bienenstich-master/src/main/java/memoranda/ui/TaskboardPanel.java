package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

/**
* Class for a TaskBoard Panel. Scrumboard modifications should be made here and not in
* TaskPanel.java
*/
public class TaskboardPanel extends JPanel {
    
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar tasksToolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JButton newTaskB = new JButton();
    JButton editTaskB = new JButton();
    JButton removeTaskB = new JButton();

    JScrollPane scrollPane = new JScrollPane();
    TaskboardTable taskboardTable = new TaskboardTable();
	JMenuItem ppEditTask = new JMenuItem();
	JPopupMenu taskPPMenu = new JPopupMenu();
	JMenuItem ppRemoveTask = new JMenuItem();
	JMenuItem ppNewTask = new JMenuItem();
	DailyItemsPanel parentPanel = null;

    public TaskboardPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void jbInit() throws Exception {
        
        tasksToolBar.setFloatable(false);
        historyBackB.setAction(History.historyBackAction);
        historyBackB.setFocusable(false);
        historyBackB.setBorderPainted(false);
        historyBackB.setToolTipText(Local.getString("History back"));
        historyBackB.setRequestFocusEnabled(false);
        historyBackB.setPreferredSize(new Dimension(24, 24));
        historyBackB.setMinimumSize(new Dimension(24, 24));
        historyBackB.setMaximumSize(new Dimension(24, 24));
        historyBackB.setText("");

        historyForwardB.setAction(History.historyForwardAction);
        historyForwardB.setBorderPainted(false);
        historyForwardB.setFocusable(false);
        historyForwardB.setPreferredSize(new Dimension(24, 24));
        historyForwardB.setRequestFocusEnabled(false);
        historyForwardB.setToolTipText(Local.getString("History forward"));
        historyForwardB.setMinimumSize(new Dimension(24, 24));
        historyForwardB.setMaximumSize(new Dimension(24, 24));
        historyForwardB.setText("");

        newTaskB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));
        newTaskB.setEnabled(true);
        newTaskB.setMaximumSize(new Dimension(24, 24));
        newTaskB.setMinimumSize(new Dimension(24, 24));
        newTaskB.setToolTipText(Local.getString("Create new task"));
        newTaskB.setRequestFocusEnabled(false);
        newTaskB.setPreferredSize(new Dimension(24, 24));
        newTaskB.setFocusable(false);
        newTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newTaskB_actionPerformed(e);
            }
        });
        newTaskB.setBorderPainted(false);

        editTaskB.setBorderPainted(false);
        editTaskB.setFocusable(false);
        editTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editTaskB_actionPerformed(e);
            }
        });
        editTaskB.setPreferredSize(new Dimension(24, 24));
        editTaskB.setRequestFocusEnabled(false);
        editTaskB.setToolTipText(Local.getString("Edit task"));
        editTaskB.setMinimumSize(new Dimension(24, 24));
        editTaskB.setMaximumSize(new Dimension(24, 24));
        editTaskB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));

        removeTaskB.setBorderPainted(false);
        removeTaskB.setFocusable(false);
        removeTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTaskB_actionPerformed(e);
            }
        });
        removeTaskB.setPreferredSize(new Dimension(24, 24));
        removeTaskB.setRequestFocusEnabled(false);
        removeTaskB.setToolTipText(Local.getString("Remove task"));
        removeTaskB.setMinimumSize(new Dimension(24, 24));
        removeTaskB.setMaximumSize(new Dimension(24, 24));
        removeTaskB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));



        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        ppEditTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditTask.setText(Local.getString("Edit task")+"...");
        ppEditTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditTask_actionPerformed(e);
            }
        });
        ppEditTask.setEnabled(false);
        ppEditTask.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));
        taskPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppRemoveTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveTask.setText(Local.getString("Remove task"));
        ppRemoveTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveTask_actionPerformed(e);
            }
        });
        ppRemoveTask.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));
        ppRemoveTask.setEnabled(false);
        ppNewTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewTask.setText(Local.getString("New task")+"...");
        ppNewTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewTask_actionPerformed(e);
            }
        });
        ppNewTask.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));


        scrollPane.getViewport().add(taskboardTable, null);
        this.add(scrollPane, BorderLayout.CENTER);
        tasksToolBar.add(historyBackB, null);
        tasksToolBar.add(historyForwardB, null);
        tasksToolBar.addSeparator(new Dimension(8, 24));

        tasksToolBar.add(newTaskB, null);
        tasksToolBar.add(removeTaskB, null);
        tasksToolBar.addSeparator(new Dimension(8, 24));
        tasksToolBar.add(editTaskB, null);

        this.add(tasksToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        taskboardTable.addMouseListener(ppListener);

        CurrentProject.addProjectListener(new ProjectListener() {

            public void projectChange(Project p, NoteList nl, TaskList tl, TaskboardList tbl, ResourcesList rl, SprintList sprintList, TeamList teal, BacklogList back1) {

                newTaskB.setEnabled(
                    CurrentDate.get().inPeriod(p.getStartDate(), p.getEndDate()));
            }
            public void projectWasChanged() {
            	
            }
        });
        taskboardTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (taskboardTable.getRowCount() > 0)&&(taskboardTable.getSelectedRow() > -1);
                editTaskB.setEnabled(enbl);
                ppEditTask.setEnabled(enbl);
                removeTaskB.setEnabled(enbl);
                ppRemoveTask.setEnabled(enbl);



                if (enbl) {
    				String name = (String) taskboardTable.getModel().getValueAt(taskboardTable.getSelectedRow(), TaskboardTable.TASKNAME);

    				
    				TaskboardTask t = CurrentProject.getTaskboardList().getTask(name);
                    
                }
                
            }
        });
        editTaskB.setEnabled(false);
        removeTaskB.setEnabled(false);
		taskPPMenu.add(ppEditTask);
		taskPPMenu.addSeparator();
		taskPPMenu.add(ppNewTask);
		taskPPMenu.add(ppRemoveTask);
		taskPPMenu.addSeparator();


    }

    void editTaskB_actionPerformed(ActionEvent e) {
        TaskboardTask t =
             CurrentProject.getTaskboardList().getTask(
                (String)taskboardTable.getModel().getValueAt(taskboardTable.getSelectedRow(), TaskboardTable.TASKNAME));
        TaskboardDialog dlg = new TaskboardDialog(App.getFrame(), Local.getString("Edit task"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.nameField.setText(t.getName());
        dlg.descriptionField.setText(t.getDescription());
        dlg.pointsCombobox.setSelectedIndex(Integer.parseInt(t.getPoints()));
        //change String to index
        String value = t.getProgress();
        int index = 0;
        switch(value) {
        case "New":
            index = 0;
            break;
        case "In-Progress":
            index = 1;
            break;
        case "Ready for Test":
            index = 2;
            break;
        case "Closed":
            index = 3;
            break;
        }
        dlg.progressCombobox.setSelectedIndex(index);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;

        t.setName(dlg.nameField.getText());
        t.setDescription(dlg.descriptionField.getText());
        t.setPoints((String) dlg.pointsCombobox.getSelectedItem());
        
        
       
        t.setProgress((String) dlg.progressCombobox.getSelectedItem());

//		CurrentProject.getTaskList().adjustParentTasks(t);

        CurrentStorage.get().storeTaskboardList(CurrentProject.getTaskboardList(), CurrentProject.get());
        taskboardTable.tableChanged();
        parentPanel.updateIndicators();

    }

    void newTaskB_actionPerformed(ActionEvent e) {
        
        //create dialog box
        TaskboardDialog dlg = new TaskboardDialog(App.getFrame(), Local.getString("New task"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;

        
		
		TaskboardTask newTask = CurrentProject.getTaskboardList().createTaskboardTask(dlg.nameField.getText(), dlg.descriptionField.getText(), (String) dlg.progressCombobox.getSelectedItem(), (String) dlg.pointsCombobox.getSelectedItem());
        CurrentStorage.get().storeTaskboardList(CurrentProject.getTaskboardList(), CurrentProject.get());
        taskboardTable.tableChanged();
        parentPanel.updateIndicators();
    }


    void removeTaskB_actionPerformed(ActionEvent e) {
        
        String name = (String) taskboardTable.getModel().getValueAt(taskboardTable.getSelectedRow(), TaskboardTable.TASKNAME);

        CurrentProject.getTaskboardList().removeTask(name);
        
        taskboardTable.tableChanged();
        taskboardTable.refresh();
        CurrentStorage.get().storeTaskboardList(CurrentProject.getTaskboardList(), CurrentProject.get());
        parentPanel.updateIndicators();


    }



    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
		if ((e.getClickCount() == 2) && (taskboardTable.getSelectedRow() > -1)){


			editTaskB_actionPerformed(null);
		}
        }

                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }

                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }

                private void maybeShowPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        taskPPMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }

    }

    void ppEditTask_actionPerformed(ActionEvent e) {
    editTaskB_actionPerformed(e);
    }
    void ppRemoveTask_actionPerformed(ActionEvent e) {
    removeTaskB_actionPerformed(e);
    }
    void ppNewTask_actionPerformed(ActionEvent e) {
    newTaskB_actionPerformed(e);
    }





}
