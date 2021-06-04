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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

/**
 * panel for Backlog
 */
/*$Id: EventsPanel.java,v 1.25 2005/02/19 10:06:25 rawsushi Exp $*/
public class BacklogPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar backlogToolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JButton newBacklogB = new JButton();
    JButton editBacklogB = new JButton();
    JButton removeBacklogB = new JButton();
    JScrollPane scrollPane = new JScrollPane();
    BacklogTable backlogTable = new BacklogTable();
    JPopupMenu backlogPPMenu = new JPopupMenu();
    JMenuItem ppEditBacklog = new JMenuItem();
    JMenuItem ppRemoveBacklog = new JMenuItem();
    JMenuItem ppNewBacklog = new JMenuItem();
    DailyItemsPanel parentPanel = null;

    public BacklogPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {
        backlogToolBar.setFloatable(false);

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

        newBacklogB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png"))); //TODO UPDATE ICON
        newBacklogB.setEnabled(true);
        newBacklogB.setMaximumSize(new Dimension(24, 24));
        newBacklogB.setMinimumSize(new Dimension(24, 24));
        newBacklogB.setToolTipText(Local.getString("New Backlog"));
        newBacklogB.setRequestFocusEnabled(false);
        newBacklogB.setPreferredSize(new Dimension(24, 24));
        newBacklogB.setFocusable(false);
        newBacklogB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newBacklogB_actionPerformed(e);
            }
        });
        newBacklogB.setBorderPainted(false);

        editBacklogB.setBorderPainted(false);
        editBacklogB.setFocusable(false);
        editBacklogB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editBacklogB_actionPerformed(e);
            }
        });
        editBacklogB.setPreferredSize(new Dimension(24, 24));
        editBacklogB.setRequestFocusEnabled(false);
        editBacklogB.setToolTipText(Local.getString("Edit event"));
        editBacklogB.setMinimumSize(new Dimension(24, 24));
        editBacklogB.setMaximumSize(new Dimension(24, 24));
        editBacklogB.setEnabled(true);
        editBacklogB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));

        removeBacklogB.setBorderPainted(false);
        removeBacklogB.setFocusable(false);
        removeBacklogB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeBacklogB_actionPerformed(e);
            }
        });
        removeBacklogB.setPreferredSize(new Dimension(24, 24));
        removeBacklogB.setRequestFocusEnabled(false);
        removeBacklogB.setToolTipText(Local.getString("Remove Backlog"));
        removeBacklogB.setMinimumSize(new Dimension(24, 24));
        removeBacklogB.setMaximumSize(new Dimension(24, 24));
        removeBacklogB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        backlogTable.setMaximumSize(new Dimension(32767, 32767));
        backlogTable.setRowHeight(24);
        backlogPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppEditBacklog.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditBacklog.setText(Local.getString("Edit Backlog") + "...");
        ppEditBacklog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditBacklog_actionPerformed(e);
            }
        });
        ppEditBacklog.setEnabled(false);
        ppEditBacklog.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));
        ppRemoveBacklog.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveBacklog.setText(Local.getString("Remove Backlog"));
        ppRemoveBacklog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveBacklog_actionPerformed(e);
            }
        });
        ppRemoveBacklog.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));
        ppRemoveBacklog.setEnabled(false);
        ppNewBacklog.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewBacklog.setText(Local.getString("New Backlog") + "...");
        ppNewBacklog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewBacklog_actionPerformed(e);
            }
        });
        ppNewBacklog.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));
        scrollPane.getViewport().add(backlogTable, null); //add BacklogTable to BacklogPanel
        this.add(scrollPane, BorderLayout.CENTER);
        backlogToolBar.add(historyBackB, null);
        backlogToolBar.add(historyForwardB, null);
        backlogToolBar.addSeparator(new Dimension(8, 24));

        backlogToolBar.add(newBacklogB, null);
        backlogToolBar.add(removeBacklogB, null);
        backlogToolBar.addSeparator(new Dimension(8, 24));
        backlogToolBar.add(editBacklogB, null);

        this.add(backlogToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        backlogTable.addMouseListener(ppListener);

        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                backlogTable.initTable(d);
                boolean enbl = d.after(CalendarDate.today()) || d.equals(CalendarDate.today());
                newBacklogB.setEnabled(enbl);
                ppNewBacklog.setEnabled(enbl);
                editBacklogB.setEnabled(false);
                ppEditBacklog.setEnabled(false);
                removeBacklogB.setEnabled(false);
                ppRemoveBacklog.setEnabled(false);
            }
        });


        backlogTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = backlogTable.getSelectedRow() > -1;
                editBacklogB.setEnabled(enbl);
                ppEditBacklog.setEnabled(enbl);
                removeBacklogB.setEnabled(enbl);
                ppRemoveBacklog.setEnabled(enbl);
            }
        });
        editBacklogB.setEnabled(false);
        removeBacklogB.setEnabled(false);
        backlogPPMenu.add(ppEditBacklog);
        backlogPPMenu.addSeparator();
        backlogPPMenu.add(ppNewBacklog);
        backlogPPMenu.add(ppRemoveBacklog);
		
		// remove events using the DEL key
        backlogTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(backlogTable.getSelectedRows().length>0
					&& e.getKeyCode()==KeyEvent.VK_DELETE)
                    ppRemoveBacklog_actionPerformed(null);
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});
    }

        void editBacklogB_actionPerformed(ActionEvent e) {
        BacklogDialog dlg = new BacklogDialog(App.getFrame(), Local.getString("Backlog"));
        main.java.memoranda.Backlog ev =
            (main.java.memoranda.Backlog) backlogTable.getModel().getValueAt(
                    backlogTable.getSelectedRow(),
                BacklogTable.BACKLOG);

        dlg.textField.setText(ev.getText());

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        BacklogManager.removeBacklog(ev);
        
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Fix deprecated methods to get hours
		//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
		calendar.setTime(((Date)dlg.timeSpin.getModel().getValue()));//Fix deprecated methods to get hours
		//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
		int hh = calendar.get(Calendar.HOUR_OF_DAY);//Fix deprecated methods to get hours
		//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
		int mm = calendar.get(Calendar.MINUTE);//Fix deprecated methods to get hours
		//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
        
        //int hh = ((Date) dlg.timeSpin.getModel().getValue()).getHours();
        //int mm = ((Date) dlg.timeSpin.getModel().getValue()).getMinutes();
        String text = dlg.textField.getText();
        /*if (dlg.noRepeatRB.isSelected())
   	    BacklogManager.createBacklog(CurrentDate.get(), hh, mm, text);
        else {
	    //updateBacklog(dlg,hh,mm,text);
	}    */

	saveBacklog();
    }

    void newBacklogB_actionPerformed(ActionEvent e) {
        Calendar cdate = CurrentDate.get().getCalendar();
        // round down to hour
        cdate.set(Calendar.MINUTE,0);  
        Util.debug("Default time is " + cdate);
        
    	newBacklogB_actionPerformed(e, null, cdate.getTime(), null);
    }
    
    void newBacklogB_actionPerformed(ActionEvent e, String tasktext, Date dueDate, String descriptiontext) {
    	BacklogDialog dlg = new BacklogDialog(App.getFrame(), Local.getString("New Backlog"));
    	Dimension frmSize = App.getFrame().getSize();
    	Point loc = App.getFrame().getLocation();
    	if (tasktext != null) {
    		dlg.textField.setText(tasktext);
    	}
		dlg.dueDate.getModel().setValue(dueDate);
		dlg.timeSpin.getModel().setValue(dueDate);

    	dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
    	dlg.setBacklogDate(dueDate);
		dlg.setVisible(true);
    	if (dlg.CANCELLED)
    		return;
    	Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Fix deprecated methods to get hours
    	//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
    	calendar.setTime(((Date)dlg.timeSpin.getModel().getValue()));//Fix deprecated methods to get hours
    	//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
    	int hh = calendar.get(Calendar.HOUR_OF_DAY);//Fix deprecated methods to get hours
    	//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM
    	int mm = calendar.get(Calendar.MINUTE);//Fix deprecated methods to get hours
    	//by (jcscoobyrs) 14-Nov-2003 at 10:24:38 AM

        Date dueDateValue = (Date)dlg.dueDate.getValue();
        String name = dlg.textField.getText();
        String description = dlg.descriptionField.getText();
        Backlog newBacklog = CurrentProject.getBacklogList().createBacklog(new CalendarDate(dueDateValue),name, description);
        CurrentStorage.get().storeBacklogList(CurrentProject.getBacklogList(),CurrentProject.get());
        //System.out.println("get backlog list in new event " + CurrentProject.getBacklogList().toString());

    	saveBacklog();
    }

    private void saveBacklog() {
	//CurrentStorage.get().storeBacklogManager();

        backlogTable.refresh();
        BacklogScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
    }

    private void updateBacklog(BacklogDialog dlg, int hh, int mm, String text) {
	int rtype;
        int period;
        CalendarDate sd = new CalendarDate((Date) dlg.dueDate.getModel().getValue());
        CalendarDate ed = null;
    }

    void removeBacklogB_actionPerformed(ActionEvent e) {
		String msg;
		main.java.memoranda.Backlog ev;

		if(backlogTable.getSelectedRows().length > 1)
			msg = Local.getString("Remove") + " " + backlogTable.getSelectedRows().length
				+ " " + Local.getString("Backlog") + "\n" + Local.getString("Are you sure?");
		else {
			ev = (main.java.memoranda.Backlog) backlogTable.getModel().getValueAt(
                    backlogTable.getSelectedRow(),
                    backlogTable.BACKLOG);
			msg = Local.getString("Remove Backlog") + "\n'"
				+ ev.getText() + "'\n" + Local.getString("Are you sure?");
		}

        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove Backlog"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION) return;

        for(int i=0; i< backlogTable.getSelectedRows().length;i++) {
			ev = (main.java.memoranda.Backlog) backlogTable.getModel().getValueAt(
                    backlogTable.getSelectedRows()[i], BacklogTable.BACKLOG);
        BacklogManager.removeBacklog(ev);
		}
        backlogTable.getSelectionModel().clearSelection();
/*        CurrentStorage.get().storeEventsManager();
        BacklogTable.refresh();
        EventsScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
*/ saveBacklog();
  }

    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (backlogTable.getSelectedRow() > -1))
                editBacklogB_actionPerformed(null);
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                backlogPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }
    void ppEditBacklog_actionPerformed(ActionEvent e) {
        editBacklogB_actionPerformed(e);
    }
    void ppRemoveBacklog_actionPerformed(ActionEvent e) {
        removeBacklogB_actionPerformed(e);
    }
    void ppNewBacklog_actionPerformed(ActionEvent e) {
        newBacklogB_actionPerformed(e);
    }
}