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
 * panel for sprints
 */
/*class SprintPanel extends JPanel {
    DailyItemsPanel parentPanel = null;

    public SprintPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception {
        JLabel label = new JLabel("Sprint");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Dialog", Font.BOLD, 60));
        this.add(label);
        this.setBackground(new Color(89, 169, 179));
    }
}*/





/*$Id: EventsPanel.java,v 1.25 2005/02/19 10:06:25 rawsushi Exp $*/
public class SprintPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar sprintsToolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JButton newSprintB = new JButton();
    JButton editSprintB = new JButton();
    JButton removeSprintB = new JButton();
    JScrollPane scrollPane = new JScrollPane();
    SprintsTable sprintsTable = new SprintsTable();
    JPopupMenu sprintPPMenu = new JPopupMenu();
    JMenuItem ppEditSprint = new JMenuItem();
    JMenuItem ppRemoveSprint = new JMenuItem();
    JMenuItem ppNewSprint = new JMenuItem();
    DailyItemsPanel parentPanel = null;

    public SprintPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {
        sprintsToolBar.setFloatable(false);

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

        newSprintB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png"))); //TODO UPDATE ICON
        newSprintB.setEnabled(true);
        newSprintB.setMaximumSize(new Dimension(24, 24));
        newSprintB.setMinimumSize(new Dimension(24, 24));
        newSprintB.setToolTipText(Local.getString("New sprint"));
        newSprintB.setRequestFocusEnabled(false);
        newSprintB.setPreferredSize(new Dimension(24, 24));
        newSprintB.setFocusable(false);
        newSprintB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newSprintB_actionPerformed(e);
            }
        });
        newSprintB.setBorderPainted(false);

        editSprintB.setBorderPainted(false);
        editSprintB.setFocusable(false);
        editSprintB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editSprintB_actionPerformed(e);
            }
        });
        editSprintB.setPreferredSize(new Dimension(24, 24));
        editSprintB.setRequestFocusEnabled(false);
        editSprintB.setToolTipText(Local.getString("Edit event"));
        editSprintB.setMinimumSize(new Dimension(24, 24));
        editSprintB.setMaximumSize(new Dimension(24, 24));
        editSprintB.setEnabled(true);
        editSprintB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));

        removeSprintB.setBorderPainted(false);
        removeSprintB.setFocusable(false);
        removeSprintB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSprintB_actionPerformed(e);
            }
        });
        removeSprintB.setPreferredSize(new Dimension(24, 24));
        removeSprintB.setRequestFocusEnabled(false);
        removeSprintB.setToolTipText(Local.getString("Remove sprint"));
        removeSprintB.setMinimumSize(new Dimension(24, 24));
        removeSprintB.setMaximumSize(new Dimension(24, 24));
        removeSprintB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        sprintsTable.setMaximumSize(new Dimension(32767, 32767));
        sprintsTable.setRowHeight(24);
        sprintPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppEditSprint.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditSprint.setText(Local.getString("Edit sprint") + "...");
        ppEditSprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditSprint_actionPerformed(e);
            }
        });
        ppEditSprint.setEnabled(false);
        ppEditSprint.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));
        ppRemoveSprint.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveSprint.setText(Local.getString("Remove sprint"));
        ppRemoveSprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveSprint_actionPerformed(e);
            }
        });
        ppRemoveSprint.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));
        ppRemoveSprint.setEnabled(false);
        ppNewSprint.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewSprint.setText(Local.getString("New sprint") + "...");
        ppNewSprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewSprint_actionPerformed(e);
            }
        });
        ppNewSprint.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));
        scrollPane.getViewport().add(sprintsTable, null); //add SprintsTable to sprintPanel
        this.add(scrollPane, BorderLayout.CENTER);
        sprintsToolBar.add(historyBackB, null);
        sprintsToolBar.add(historyForwardB, null);
        sprintsToolBar.addSeparator(new Dimension(8, 24));

        sprintsToolBar.add(newSprintB, null);
        sprintsToolBar.add(removeSprintB, null);
        sprintsToolBar.addSeparator(new Dimension(8, 24));
        sprintsToolBar.add(editSprintB, null);

        this.add(sprintsToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        sprintsTable.addMouseListener(ppListener);

        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                sprintsTable.initTable(d);
                boolean enbl = d.after(CalendarDate.today()) || d.equals(CalendarDate.today());
                newSprintB.setEnabled(enbl);
                ppNewSprint.setEnabled(enbl);
                editSprintB.setEnabled(false);
                ppEditSprint.setEnabled(false);
                removeSprintB.setEnabled(false);
                ppRemoveSprint.setEnabled(false);
            }
        });

        sprintsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = sprintsTable.getSelectedRow() > -1;
                editSprintB.setEnabled(enbl);
                ppEditSprint.setEnabled(enbl);
                removeSprintB.setEnabled(enbl);
                ppRemoveSprint.setEnabled(enbl);
            }
        });
        editSprintB.setEnabled(false);
        removeSprintB.setEnabled(false);
        sprintPPMenu.add(ppEditSprint);
        sprintPPMenu.addSeparator();
        sprintPPMenu.add(ppNewSprint);
        sprintPPMenu.add(ppRemoveSprint);
		
		// remove events using the DEL key
        sprintsTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(sprintsTable.getSelectedRows().length>0
					&& e.getKeyCode()==KeyEvent.VK_DELETE)
                    ppRemoveSprint_actionPerformed(null);
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});
    }

    void editSprintB_actionPerformed(ActionEvent e) {
        SprintDialog dlg = new SprintDialog(App.getFrame(), Local.getString("Sprint"));
        main.java.memoranda.Sprint ev =
            (main.java.memoranda.Sprint) sprintsTable.getModel().getValueAt(
                    sprintsTable.getSelectedRow(),
                SprintsTable.SPRINT);

        dlg.textField.setText(ev.getText());

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        SprintsManager.removeSprint(ev);
        
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
   	    SprintsManager.createSprint(CurrentDate.get(), hh, mm, text);
        else {
	    //updateSprints(dlg,hh,mm,text);
	}    */

	saveSprint();
    }

    void newSprintB_actionPerformed(ActionEvent e) {
        Calendar cdate = CurrentDate.get().getCalendar();
        // round down to hour
        cdate.set(Calendar.MINUTE,0);  
        Util.debug("Default time is " + cdate);
        
    	newSprintB_actionPerformed(e, null, cdate.getTime(), cdate.getTime());
    }
    
    void newSprintB_actionPerformed(ActionEvent e, String tasktext, Date startDate, Date endDate) {
    	SprintDialog dlg = new SprintDialog(App.getFrame(), Local.getString("New sprint"));
    	Dimension frmSize = App.getFrame().getSize();
    	Point loc = App.getFrame().getLocation();
    	if (tasktext != null) {
    		dlg.textField.setText(tasktext);
    	}
		dlg.startDate.getModel().setValue(startDate);
		dlg.endDate.getModel().setValue(endDate);
		dlg.timeSpin.getModel().setValue(startDate);

    	dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
    	dlg.setSprintDate(startDate);
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

        Date startDateValue = (Date)dlg.startDate.getValue();
        Date endDateValue = (Date)dlg.endDate.getValue();
        String name = dlg.textField.getText();
        Sprint newSprint = CurrentProject.getSprintsList().createSprint(new CalendarDate(startDateValue), new CalendarDate(endDateValue), name);
        CurrentStorage.get().storeSprintsList(CurrentProject.getSprintsList(),CurrentProject.get());

    	saveSprint();
    }

    private void saveSprint() {
	//CurrentStorage.get().storeSprintsManager();

        sprintsTable.refresh();
        SprintsScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
    }

    private void updateSprints(EventDialog dlg, int hh, int mm, String text) {
	int rtype;
        int period;
        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate ed = null;
        if (dlg.enableEndDateCB.isSelected())
            ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        if (dlg.dailyRepeatRB.isSelected()) {
            //rtype = SprintsManager.REPEAT_DAILY;
            period = ((Integer) dlg.daySpin.getModel().getValue()).intValue();
        }
        else if (dlg.weeklyRepeatRB.isSelected()) {
            //rtype = SprintsManager.REPEAT_WEEKLY;
            period = dlg.weekdaysCB.getSelectedIndex() + 1;
	    if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
		if(period==7) period=1;
		else period++;
	    }
        }
	else if (dlg.yearlyRepeatRB.isSelected()) {
	    //rtype = SprintsManager.REPEAT_YEARLY;
	    period = sd.getCalendar().get(Calendar.DAY_OF_YEAR);
	    if((sd.getYear() % 4) == 0 && sd.getCalendar().get(Calendar.DAY_OF_YEAR) > 60) period--;
	}
        else {
            //rtype = SprintsManager.REPEAT_MONTHLY;
            period = ((Integer) dlg.dayOfMonthSpin.getModel().getValue()).intValue();
        }
        //SprintsManager.createRepeatableEvent(rtype, sd, ed, period, hh, mm, text, dlg.workingDaysOnlyCB.isSelected());
    }

    void removeSprintB_actionPerformed(ActionEvent e) {
		String msg;
		main.java.memoranda.Sprint ev;

		if(sprintsTable.getSelectedRows().length > 1)
			msg = Local.getString("Remove") + " " + sprintsTable.getSelectedRows().length
				+ " " + Local.getString("sprints") + "\n" + Local.getString("Are you sure?");
		else {
			ev = (main.java.memoranda.Sprint) sprintsTable.getModel().getValueAt(
                    sprintsTable.getSelectedRow(),
                SprintsTable.SPRINT);
			msg = Local.getString("Remove sprint") + "\n'"
				+ ev.getText() + "'\n" + Local.getString("Are you sure?");
		}

        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove sprint"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION) return;

        for(int i=0; i< sprintsTable.getSelectedRows().length;i++) {
			ev = (main.java.memoranda.Sprint) sprintsTable.getModel().getValueAt(
                    sprintsTable.getSelectedRows()[i], SprintsTable.SPRINT);
        SprintsManager.removeSprint(ev);
		}
        sprintsTable.getSelectionModel().clearSelection();
/*        CurrentStorage.get().storeEventsManager();
        sprintsTable.refresh();
        EventsScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
*/ saveSprint();
  }

    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (sprintsTable.getSelectedRow() > -1))
                editSprintB_actionPerformed(null);
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                sprintPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }
    void ppEditSprint_actionPerformed(ActionEvent e) {
        editSprintB_actionPerformed(e);
    }
    void ppRemoveSprint_actionPerformed(ActionEvent e) {
        removeSprintB_actionPerformed(e);
    }
    void ppNewSprint_actionPerformed(ActionEvent e) {
        newSprintB_actionPerformed(e);
    }
}