package main.java.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.*;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.ui.BacklogPanel.PopupListener;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

/**
 * panel for teams
 */
class TeamPanel extends JPanel {

    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar teamToolBar = new JToolBar();
    JButton newTeamB = new JButton();
    JButton memberB = new JButton();
    JButton editTeamB = new JButton();
    JButton removeTeamB = new JButton();
    JScrollPane scrollPane = new JScrollPane();
    TeamTable teamTable = new TeamTable();
    JMenuItem ppEditTeam = new JMenuItem();
    JPopupMenu teamPPMenu = new JPopupMenu();
    JMenuItem ppRemoveTeam = new JMenuItem();
    JMenuItem ppNewTeam = new JMenuItem();
    //	JMenuItem ppCompleteTeam = new JMenuItem();
    //JMenuItem ppSubTeams = new JMenuItem();
    //JMenuItem ppParentTeam = new JMenuItem();
    JMenuItem ppAddMember = new JMenuItem();
    //	JMenuItem ppCalcTeam = new JMenuItem();
    DailyItemsPanel parentPanel = null;

    /**
     * Constructor for TeamPanel
     * @param _parentPanel
     */
    public TeamPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initializes the TeamPanel
     * @throws Exception
     */
    void jbInit() throws Exception {
        teamToolBar.setFloatable(false);

        // New Team Button
        newTeamB.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));
        newTeamB.setEnabled(true);
        newTeamB.setMaximumSize(new Dimension(24, 24));
        newTeamB.setMinimumSize(new Dimension(24, 24));
        newTeamB.setToolTipText(Local.getString("Create new team"));
        newTeamB.setRequestFocusEnabled(false);
        newTeamB.setPreferredSize(new Dimension(24, 24));
        newTeamB.setFocusable(false);
        newTeamB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newTeamB_actionPerformed(e);
            }
        });
        newTeamB.setBorderPainted(false);

        // Add member button
        memberB.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new_sub.png")));
        memberB.setEnabled(true);
        memberB.setMaximumSize(new Dimension(24, 24));
        memberB.setMinimumSize(new Dimension(24, 24));
        memberB.setToolTipText(Local.getString("Add member"));
        memberB.setRequestFocusEnabled(false);
        memberB.setPreferredSize(new Dimension(24, 24));
        memberB.setFocusable(false);
        memberB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMember_actionPerformed(e);
            }
        });
        memberB.setBorderPainted(false);

        // Edit team button
        editTeamB.setBorderPainted(false);
        editTeamB.setFocusable(false);
        editTeamB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editTeamB_actionPerformed(e);
            }
        });
        editTeamB.setPreferredSize(new Dimension(24, 24));
        editTeamB.setRequestFocusEnabled(false);
        editTeamB.setToolTipText(Local.getString("Edit team"));
        editTeamB.setMinimumSize(new Dimension(24, 24));
        editTeamB.setMaximumSize(new Dimension(24, 24));
        editTeamB.setEnabled(false);
        editTeamB.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));

        // Remove team button
        removeTeamB.setBorderPainted(false);
        removeTeamB.setFocusable(false);
        removeTeamB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTeamB_actionPerformed(e);
            }
        });
        removeTeamB.setPreferredSize(new Dimension(24, 24));
        removeTeamB.setRequestFocusEnabled(false);
        removeTeamB.setToolTipText(Local.getString("Remove team"));
        removeTeamB.setMinimumSize(new Dimension(24, 24));
        removeTeamB.setMaximumSize(new Dimension(24, 24));
        removeTeamB.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));



        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        /*teamTable.setMaximumSize(new Dimension(32767, 32767));
        teamTable.setRowHeight(24);*/
        ppEditTeam.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditTeam.setText(Local.getString("Edit team")+"...");
        ppEditTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditTeam_actionPerformed(e);
            }
        });
        ppEditTeam.setEnabled(false);
        ppEditTeam.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));
        teamPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        ppRemoveTeam.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveTeam.setText(Local.getString("Remove team"));
        ppRemoveTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveTeam_actionPerformed(e);
            }
        });
        ppRemoveTeam.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));
        ppRemoveTeam.setEnabled(false);
        ppNewTeam.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewTeam.setText(Local.getString("New team")+"...");
        ppNewTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewTeam_actionPerformed(e);
            }
        });
        ppNewTeam.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));

        ppAddMember.setFont(new java.awt.Font("Dialog", 1, 11));
        ppAddMember.setText(Local.getString("Add member"));
        ppAddMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppAddMember_actionPerformed(e);
            }
        });
        ppAddMember.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new_sub.png")));

//	ppCompleteTeam.setFont(new java.awt.Font("Dialog", 1, 11));
//	ppCompleteTeam.setText(Local.getString("Complete team"));
//	ppCompleteTeam.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ppCompleteTeam_actionPerformed(e);
//			}
//		});
//	ppCompleteTeam.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_complete.png")));
//	ppCompleteTeam.setEnabled(false);
//
//	ppCalcTeam.setFont(new java.awt.Font("Dialog", 1, 11));
//	ppCalcTeam.setText(Local.getString("Calculate team data"));
//	ppCalcTeam.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ppCalcTeam_actionPerformed(e);
//			}
//		});
//	ppCalcTeam.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_complete.png")));
//	ppCalcTeam.setEnabled(false);

        scrollPane.getViewport().add(teamTable, null);
        this.add(scrollPane, BorderLayout.CENTER);
//        teamToolBar.add(historyBackB, null);
//        teamToolBar.add(historyForwardB, null);
        teamToolBar.addSeparator(new Dimension(8, 24));

        teamToolBar.add(newTeamB, null);
        teamToolBar.add(memberB, null);
        teamToolBar.add(removeTeamB, null);
        teamToolBar.addSeparator(new Dimension(8, 24));
        teamToolBar.add(editTeamB, null);
//        teamToolBar.add(completeTeamB, null);
//        teamToolBar.add(showActiveOnly, null);


        this.add(teamToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        teamTable.addMouseListener(ppListener);

        // Date change listener
//        CurrentDate.addDateListener(new DateListener() {
//            public void dateChange(CalendarDate d) {
//                newTeamB.setEnabled(d.inPeriod(CurrentProject.get().getStartDate(), CurrentProject.get().getEndDate()));
//            }
//        });

        // Project Listener
        CurrentProject.addProjectListener(new ProjectListener() {

            public void projectChange(Project p, NoteList nl, TaskList tl, TaskboardList tbl, ResourcesList rl, SprintList spr1, TeamList teal, BacklogList back1) {

                newTeamB.setEnabled(
                        CurrentDate.get().inPeriod(p.getStartDate(), p.getEndDate()));
            }
            public void projectWasChanged() {
                //teamTable.setCurrentRootTeam(null); //XXX
            }
        });

        // Get the selection model
        teamTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (teamTable.getRowCount() > 0)&&(teamTable.getSelectedRow() > -1);
                editTeamB.setEnabled(enbl);ppEditTeam.setEnabled(enbl);
                removeTeamB.setEnabled(enbl);ppRemoveTeam.setEnabled(enbl);

//				ppCompleteTeam.setEnabled(enbl);
//				completeTeamB.setEnabled(enbl);
                ppAddMember.setEnabled(enbl);
                //ppSubTeams.setEnabled(enbl); // default value to be over-written later depending on whether it has sub teams
//				ppCalcTeam.setEnabled(enbl); // default value to be over-written later depending on whether it has sub teams

				/*if (teamTable.getCurrentRootTeam() == null) {
					ppParentTeam.setEnabled(false);
				}
				else {
					ppParentTeam.setEnabled(true);
				}XXX*/

                if (enbl) {
    				String thisTeamId = (String)teamTable.getModel().getValueAt(teamTable.getSelectedRow(), TeamTable.TEAM_ID);
    				Util.debug("teamTable.getSelectedRow() = " + teamTable.getSelectedRow() + "\n");
    				Util.debug("TeamID is " + thisTeamId + "\n");
//                    boolean hasMembers = CurrentProject.getTeamList().getTeam(thisTeamId).hasMembers();
                    //ppSubTeams.setEnabled(hasSubTeams);
//    				ppCalcTeam.setEnabled(hasSubTeams);
//                    Team t = CurrentProject.getTeamList().getTeam(thisTeamId);
//                    parentPanel.calendar.jnCalendar.renderer.setTeam(t);
                    parentPanel.calendar.jnCalendar.updateUI();
                }
                else {
//                    parentPanel.calendar.jnCalendar.renderer.setTeam(null);
                    parentPanel.calendar.jnCalendar.updateUI();
                }
            }
        });

        // Disable buttons
        editTeamB.setEnabled(false);
        removeTeamB.setEnabled(false);
//		completeTeamB.setEnabled(false);
        ppAddMember.setEnabled(false);
//        ppSubTeams.setEnabled(false);
//        ppParentTeam.setEnabled(false);
        teamPPMenu.add(ppEditTeam);

        teamPPMenu.addSeparator();
        teamPPMenu.add(ppNewTeam);
        teamPPMenu.add(ppAddMember);
        teamPPMenu.add(ppRemoveTeam);

        teamPPMenu.addSeparator();
//	      teamPPMenu.add(ppCompleteTeam);
//	      teamPPMenu.add(ppCalcTeam);
//        teamPPMenu.addSeparator();
//        teamPPMenu.add(ppSubTeams);
//        teamPPMenu.addSeparator();
//        teamPPMenu.add(ppParentTeam);
//        teamPPMenu.addSeparator();
//	      teamPPMenu.add(ppShowActiveOnlyChB);


        // define key actions in TeamPanel:
        // - KEY:DELETE => delete teams (recursivly).
        // - KEY:INTERT => insert new Subteam if another is selected.
        // - KEY:INSERT => insert new Team if nothing is selected.
        // - KEY:SPACE => finish Team.
        teamTable.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e){
                if(teamTable.getSelectedRows().length>0
                        && e.getKeyCode()==KeyEvent.VK_DELETE)
                    ppRemoveTeam_actionPerformed(null);

                else if(e.getKeyCode()==KeyEvent.VK_INSERT) {
                    if(teamTable.getSelectedRows().length>0) {
                        ppAddMember_actionPerformed(null);
                    }
                    else {
                        ppNewTeam_actionPerformed(null);
                    }
                }

                else if(e.getKeyCode()==KeyEvent.VK_SPACE
                        && teamTable.getSelectedRows().length>0) {
//					ppCompleteTeam_actionPerformed(null);
                }
            }
            public void	keyReleased(KeyEvent e){}
            public void keyTyped(KeyEvent e){}
        });

    }
    // END OF JBINIT!!!!

    /**
     * Edit team button action performed
     * @param e
     */
    void editTeamB_actionPerformed(ActionEvent e) {
        Team t =
                CurrentProject.getTeamList().getTeam(
                        teamTable.getModel().getValueAt(teamTable.getSelectedRow(), TeamTable.TEAM_ID).toString());
        TeamDialog dlg = new TeamDialog(App.getFrame(), Local.getString("Edit team"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.teamnameField.setText(t.getName());

        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;


        t.setName(dlg.teamnameField.getText());

        CurrentStorage.get().storeTeamList(CurrentProject.getTeamList(), CurrentProject.get());
        teamTable.tableChanged();
        parentPanel.updateIndicators();
        //teamTable.updateUI();
    }

    /**
     * New team button action performed
     * @param e
     */
    void newTeamB_actionPerformed(ActionEvent e) {
        TeamDialog dlg = new TeamDialog(App.getFrame(), Local.getString("New Team"));

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        Team newTeam = CurrentProject.getTeamList().createTeam(dlg.teamnameField.getText());
        CurrentStorage.get().storeTeamList(CurrentProject.getTeamList(), CurrentProject.get());
        teamTable.tableChanged();
        parentPanel.updateIndicators();
    }

    /**
     * Add member action performed
     * @param e
     */
    void addMember_actionPerformed(ActionEvent e) {
        MemberDialog dlg = new MemberDialog(App.getFrame(), Local.getString("New Team Member"));
        String parentTeamId = (String)teamTable.getModel().getValueAt(teamTable.getSelectedRow(), TeamTable.TEAM_ID);

        Util.debug("Adding member under " + parentTeamId);

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        Team parent = CurrentProject.getTeamList().getTeam(parentTeamId);
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;

//        Team newTeam = CurrentProject.getTeamList().createTeam(sd, ed, dlg.todoField.getText(), dlg.pointsCombobox.getSelectedIndex(),effort, dlg.descriptionField.getText(),parentTeamId);
////		CurrentProject.getTeamList().adjustParentTeams(newTeam);
//

        CurrentProject.getTeamList().getTeam(parentTeamId).addMember(dlg.nameField.getText(), dlg.emailField.getText());

        CurrentStorage.get().storeTeamList(CurrentProject.getTeamList(), CurrentProject.get());
        teamTable.tableChanged();
        parentPanel.updateIndicators();
//        //teamTable.updateUI();
    }

//    /**
//     * CalcTeam action performed.
//     * @param e
//     */
//    void calcTeam_actionPerformed(ActionEvent e) {
//        TeamCalcDialog dlg = new TeamCalcDialog(App.getFrame());
//        dlg.pack();
//        Team t = CurrentProject.getTeamList().getTeam(teamTable.getModel().getValueAt(teamTable.getSelectedRow(), TeamTable.TASK_ID).toString());
//
//        Dimension frmSize = App.getFrame().getSize();
//        Point loc = App.getFrame().getLocation();
//
//        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
//        dlg.setVisible(true);
//        if (dlg.CANCELLED) {
//            return;
//        }
//
//        TeamList tl = CurrentProject.getTeamList();
//        if(dlg.calcEffortChB.isSelected()) {
//            t.setEffort(tl.calculateTotalEffortFromSubTeams(t));
//        }
//
//        if(dlg.compactDatesChB.isSelected()) {
//            t.setStartDate(tl.getEarliestStartDateFromSubTeams(t));
//            t.setEndDate(tl.getLatestEndDateFromSubTeams(t));
//        }
//
//        if(dlg.calcCompletionChB.isSelected()) {
//            long[] res = tl.calculateCompletionFromSubTeams(t);
//            int thisProgress = (int) Math.round((((double)res[0] / (double)res[1]) * 100));
//            t.setProgress(thisProgress);
//        }
//
////        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
//////        CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
////          CalendarDate ed;
//// 		if(dlg.chkEndDate.isSelected())
//// 			ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
//// 		else
//// 			ed = new CalendarDate(0,0,0);
////        long effort = Util.getMillisFromHours(dlg.effortField.getText());
////		Team newTeam = CurrentProject.getTeamList().createTeam(sd, ed, dlg.todoField.getText(), dlg.priorityCB.getSelectedIndex(),effort, dlg.descriptionField.getText(),parentTeamId);
////
//
//        CurrentStorage.get().storeTeamList(CurrentProject.getTeamList(), CurrentProject.get());
//        teamTable.tableChanged();
////        parentPanel.updateIndicators();
//        //teamTable.updateUI();
//    }

    /**
     * Lists "subteams"
     * @param e
     */
    void listSubTeams_actionPerformed(ActionEvent e) {
        String parentTeamId = teamTable.getModel().getValueAt(teamTable.getSelectedRow(), TeamTable.TEAM_ID).toString();

        //XXX teamTable.setCurrentRootTeam(parentTeamId);
        teamTable.tableChanged();

//        parentPanel.updateIndicators();
//        //teamTable.updateUI();
    }

    /**
     * Gets the parent Team
     * @param e
     */
    void parentTeam_actionPerformed(ActionEvent e) {
//    	String teamId = teamTable.getModel().getValueAt(teamTable.getSelectedRow(), TeamTable.TASK_ID).toString();
//
//    	Team t = CurrentProject.getTeamList().getTeam(teamId);
//        Team t2 = CurrentProject.getTeamList().getTeam(teamTable.getCurrentRootTeam());
//
//    	String parentTeamId = t2.getParent();
//    	if((parentTeamId == null) || (parentTeamId.equals(""))) {
//    		parentTeamId = null;
//    	}
//    	teamTable.setCurrentRootTeam(parentTeamId);
//    	teamTable.tableChanged();
//
////      parentPanel.updateIndicators();
////      //teamTable.updateUI();
    }

    /**
     * Remove Team button action performed
     * @param e
     */
    void removeTeamB_actionPerformed(ActionEvent e) {
        String msg;
        String thisTeamId = (String)teamTable.getModel().getValueAt(teamTable.getSelectedRow(), TeamTable.TEAM_ID);

        if (teamTable.getSelectedRows().length > 1)
            msg = Local.getString("Remove")+" "+teamTable.getSelectedRows().length +" "+Local.getString("teams")+"?"
                    + "\n"+Local.getString("Are you sure?");
        else {
            Team t = CurrentProject.getTeamList().getTeam(thisTeamId);
            // check if there are subteams
            if(CurrentProject.getTeamList().getTeam(thisTeamId).hasMembers()) {
                msg = Local.getString("Remove team")+"\n'" + t.getName() + Local.getString("' and all members") +"\n"+Local.getString("Are you sure?");
            }
            else {
                msg = Local.getString("Remove team")+"\n'" + t.getName() + "'\n"+Local.getString("Are you sure?");
            }
        }
        int n =
                JOptionPane.showConfirmDialog(
                        App.getFrame(),
                        msg,
                        Local.getString("Remove team"),
                        JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        Vector toremove = new Vector();
        for (int i = 0; i < teamTable.getSelectedRows().length; i++) {
            Team t =
                    CurrentProject.getTeamList().getTeam(
                            teamTable.getModel().getValueAt(teamTable.getSelectedRows()[i], TeamTable.TEAM_ID).toString());
            if (t != null)
                toremove.add(t);
        }
        for (int i = 0; i < toremove.size(); i++) {
            CurrentProject.getTeamList().removeTeam(((Team)toremove.get(i)).getId());
        }
        teamTable.tableChanged();
        CurrentStorage.get().storeTeamList(CurrentProject.getTeamList(), CurrentProject.get());
        parentPanel.updateIndicators();
        //teamTable.updateUI();

    }

    /**
     * PopuListener
     */
    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (teamTable.getSelectedRow() > -1)){
                // ignore "tree" column
                //if(teamTable.getSelectedColumn() == 1) return;

                editTeamB_actionPerformed(null);
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
                teamPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }

    // Assign same button actions to popup menu
    void ppEditTeam_actionPerformed(ActionEvent e) {
        editTeamB_actionPerformed(e);
    }
    void ppRemoveTeam_actionPerformed(ActionEvent e) {
        removeTeamB_actionPerformed(e);
    }
    void ppNewTeam_actionPerformed(ActionEvent e) {
        newTeamB_actionPerformed(e);
    }

    void ppAddMember_actionPerformed(ActionEvent e) {
        addMember_actionPerformed(e);
    }

//    void ppListSubTeams_actionPerformed(ActionEvent e) {
//        listSubTeams_actionPerformed(e);
//    }

//    void ppParentTeam_actionPerformed(ActionEvent e) {
//        parentTeam_actionPerformed(e);
//    }

//    void ppCalcTeam_actionPerformed(ActionEvent e) {
//        calcTeam_actionPerformed(e);
//    }
}
