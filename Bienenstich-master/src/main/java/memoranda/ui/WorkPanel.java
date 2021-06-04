package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;

/**
 * WorkPanel represents the left hand side menu bar
 */
public class WorkPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	JPanel panel = new JPanel();
	CardLayout cardLayout1 = new CardLayout();

	public JButton notesB = new JButton();
	public DailyItemsPanel dailyItemsPanel = new DailyItemsPanel(this);
	public ResourcesPanel filesPanel = new ResourcesPanel();
	Border border1;

	// buttons
	// button variables
	private int bMaxX = 120;
	private int bMaxY = 90;
	private int bMinX = 30;
	private int bMinY = 30;
	private int bPrefX = 90;
	private int bPrefY = 90;
	// bienenstich buttons
	public JButton backlogB = new JButton();
	public JButton sprintB = new JButton();
	public JButton teamB = new JButton();
	public JButton taskboardB = new JButton();

	// old buttons
	public JButton tasksB = new JButton();
	public JButton eventsB = new JButton();
	public JButton filesB = new JButton();
	JButton currentB = null;


	// color variables
	public Color backgroundColor = Color.darkGray;
	private Color selectedColor = Color.gray;
	private Color textColor = Color.lightGray;
	private Color selectedTextColor = Color.white;

	public WorkPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	void jbInit() throws Exception {
		border1 =
			BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(
					BevelBorder.LOWERED,
					Color.white,
					Color.white,
					new Color(124, 124, 124),
					new Color(178, 178, 178)),
				BorderFactory.createEmptyBorder(0, 2, 0, 0));

		this.setLayout(borderLayout1);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBackground(backgroundColor);

		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		panel.setLayout(cardLayout1);

		// setup the backlog button
		backlogB.setBackground(Color.white);
		backlogB.setForeground(textColor);
		backlogB.setMaximumSize(new Dimension(bMaxX, bMaxY));
		backlogB.setMinimumSize(new Dimension(bMinX, bMinY));

		backlogB.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		backlogB.setPreferredSize(new Dimension(bPrefX, bPrefY));
		backlogB.setBorderPainted(false);
		backlogB.setContentAreaFilled(false);
		backlogB.setFocusPainted(false);
		backlogB.setHorizontalTextPosition(SwingConstants.CENTER);
		backlogB.setText(Local.getString("Backlog"));
		backlogB.setVerticalAlignment(SwingConstants.CENTER);
		backlogB.setVerticalTextPosition(SwingConstants.BOTTOM);
		backlogB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backlogB_actionPerformed(e);
			}
		});
		backlogB.setIcon(
				new ImageIcon(
						main.java.memoranda.ui.AppFrame.class.getResource(
								"/ui/icons/Backlog_64.png")));
		backlogB.setOpaque(false);
		backlogB.setMargin(new Insets(0, 0, 0, 0));
		backlogB.setSelected(true);

		// setup the sprint button
		sprintB.setBackground(Color.white);
		sprintB.setForeground(textColor);
		sprintB.setMaximumSize(new Dimension(bMaxX, bMaxY));
		sprintB.setMinimumSize(new Dimension(bMinX, bMinY));

		sprintB.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		sprintB.setPreferredSize(new Dimension(bPrefX, bPrefY));
		sprintB.setBorderPainted(false);
		sprintB.setContentAreaFilled(false);
		sprintB.setFocusPainted(false);
		sprintB.setHorizontalTextPosition(SwingConstants.CENTER);
		sprintB.setText(Local.getString("Sprint"));
		sprintB.setVerticalAlignment(SwingConstants.CENTER);
		sprintB.setVerticalTextPosition(SwingConstants.BOTTOM);
		sprintB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sprintB_actionPerformed(e);
			}
		});
		sprintB.setIcon(
				new ImageIcon(
						main.java.memoranda.ui.AppFrame.class.getResource(
								"/ui/icons/Sprint_64.png")));
		sprintB.setOpaque(false);
		sprintB.setMargin(new Insets(0, 0, 0, 0));
		sprintB.setSelected(true);

		// setup the team button
		teamB.setBackground(Color.white);
		teamB.setForeground(textColor);
		teamB.setMaximumSize(new Dimension(bMaxX, bMaxY));
		teamB.setMinimumSize(new Dimension(bMinX, bMinY));

		teamB.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		teamB.setPreferredSize(new Dimension(bPrefX, bPrefY));
		teamB.setBorderPainted(false);
		teamB.setContentAreaFilled(false);
		teamB.setFocusPainted(false);
		teamB.setHorizontalTextPosition(SwingConstants.CENTER);
		teamB.setText(Local.getString("Team"));
		teamB.setVerticalAlignment(SwingConstants.CENTER);
		teamB.setVerticalTextPosition(SwingConstants.BOTTOM);
		teamB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamB_actionPerformed(e);
			}
		});
		teamB.setIcon(
				new ImageIcon(
						main.java.memoranda.ui.AppFrame.class.getResource(
								"/ui/icons/Team_64.png")));
		teamB.setOpaque(false);
		teamB.setMargin(new Insets(0, 0, 0, 0));
		teamB.setSelected(true);

		// setup the taskboard button
		taskboardB.setBackground(Color.white);
		taskboardB.setForeground(textColor);
		taskboardB.setMaximumSize(new Dimension(bMaxX, bMaxY));
		taskboardB.setMinimumSize(new Dimension(bMinX, bMinY));

		taskboardB.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		taskboardB.setPreferredSize(new Dimension(bPrefX, bPrefY));
		taskboardB.setBorderPainted(false);
		taskboardB.setContentAreaFilled(false);
		taskboardB.setFocusPainted(false);
		taskboardB.setHorizontalTextPosition(SwingConstants.CENTER);
		taskboardB.setText(Local.getString("Task Board"));
		taskboardB.setVerticalAlignment(SwingConstants.CENTER);
		taskboardB.setVerticalTextPosition(SwingConstants.BOTTOM);
		taskboardB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taskboardB_actionPerformed(e);
			}
		});
		taskboardB.setIcon(
				new ImageIcon(
						main.java.memoranda.ui.AppFrame.class.getResource(
								"/ui/icons/taskboard_64.png")));
		taskboardB.setOpaque(false);
		taskboardB.setMargin(new Insets(0, 0, 0, 0));
		taskboardB.setSelected(true);
	
		// setup the events button
		eventsB.setBackground(backgroundColor);
		eventsB.setForeground(textColor);
		eventsB.setMaximumSize(new Dimension(bMaxX, bMaxY));
		eventsB.setMinimumSize(new Dimension(bMinX, bMinY));

		eventsB.setFont(new java.awt.Font("Dialog", 1, 14));
		eventsB.setPreferredSize(new Dimension(bPrefX, bPrefY));
		eventsB.setBorderPainted(false);
		eventsB.setContentAreaFilled(false);
		eventsB.setFocusPainted(false);
		eventsB.setHorizontalTextPosition(SwingConstants.CENTER);
		eventsB.setText(Local.getString("Events"));
		eventsB.setVerticalAlignment(SwingConstants.CENTER);
		eventsB.setVerticalTextPosition(SwingConstants.BOTTOM);
		eventsB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventsB_actionPerformed(e);
			}
		});
		eventsB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/events.png")));
		eventsB.setOpaque(false);
		eventsB.setMargin(new Insets(0, 0, 0, 0));
		//eventsB.setSelected(true);
		 
		 

		// setup the tasks button
		tasksB.setSelected(true);
		tasksB.setFont(new java.awt.Font("Dialog", 1, 14));
		tasksB.setMargin(new Insets(0, 0, 0, 0));
		tasksB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/tasks.png")));
		tasksB.setVerticalTextPosition(SwingConstants.BOTTOM);
		tasksB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tasksB_actionPerformed(e);
			}
		});
		tasksB.setVerticalAlignment(SwingConstants.CENTER);
		tasksB.setText(Local.getString("Tasks"));
		tasksB.setHorizontalTextPosition(SwingConstants.CENTER);
		tasksB.setFocusPainted(false);
		tasksB.setBorderPainted(false);
		tasksB.setContentAreaFilled(false);
		tasksB.setPreferredSize(new Dimension(bPrefX, bPrefY));
		tasksB.setMinimumSize(new Dimension(bMinX, bMinY));
		tasksB.setOpaque(false);
		tasksB.setMaximumSize(new Dimension(bMaxX, bMaxY));
		tasksB.setBackground(backgroundColor);
		tasksB.setForeground(textColor);

		// setup the notes button
		notesB.setFont(new java.awt.Font("Dialog", 1, 14));
		notesB.setBackground(backgroundColor);
		notesB.setForeground(textColor);
		notesB.setBorder(null);
		notesB.setMaximumSize(new Dimension(bMaxX, bMaxY));
		notesB.setMinimumSize(new Dimension(bMinX, bMinY));
		notesB.setOpaque(false);
		notesB.setPreferredSize(new Dimension(bPrefX, bPrefY));
		notesB.setBorderPainted(false);
		notesB.setContentAreaFilled(false);
		notesB.setFocusPainted(false);
		notesB.setHorizontalTextPosition(SwingConstants.CENTER);
		notesB.setText(Local.getString("Notes"));
		notesB.setVerticalAlignment(SwingConstants.CENTER);
		notesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		notesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notesB_actionPerformed(e);
			}
		});
		notesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/notes.png")));
		notesB.setMargin(new Insets(0, 0, 0, 0));
		notesB.setSelected(true);
		this.setPreferredSize(new Dimension(1073, 300));

		// setup the files button
		filesB.setSelected(true);
		filesB.setMargin(new Insets(0, 0, 0, 0));
		filesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/files.png")));
		filesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		filesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesB_actionPerformed(e);
			}
		});
		filesB.setFont(new java.awt.Font("Dialog", 1, 14));
		filesB.setVerticalAlignment(SwingConstants.CENTER);
		filesB.setText(Local.getString("Resources"));
		filesB.setHorizontalTextPosition(SwingConstants.CENTER);
		filesB.setFocusPainted(false);
		filesB.setBorderPainted(false);
		filesB.setContentAreaFilled(false);
		filesB.setPreferredSize(new Dimension(bPrefX, bPrefY));
		filesB.setMinimumSize(new Dimension(bMinX, bMinY));
		filesB.setOpaque(false);
		filesB.setMaximumSize(new Dimension(bMaxX, bMaxY));
		filesB.setBackground(backgroundColor);
		filesB.setForeground(textColor);

		// add buttons to toolbar
		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);
		panel.add(dailyItemsPanel, "DAILYITEMS");
		panel.add(filesPanel, "FILES"); // might not need
		// bienenstich buttons
		toolBar.add(backlogB, null);
		toolBar.add(sprintB, null);
		toolBar.add(taskboardB, null);
		toolBar.add(teamB, null);


		

		// set the current button
		currentB = backlogB;
		currentB.setBackground(selectedColor);
		currentB.setOpaque(true);

		// set borders
		toolBar.setBorder(null);
		panel.setBorder(null);
		dailyItemsPanel.setBorder(null);
		filesPanel.setBorder(null);

	}

	public void selectPanel(String pan) {
		if (pan != null) {
			if (pan.equals("NOTES"))
				notesB_actionPerformed(null);
			else if (pan.equals("TASKS"))
				tasksB_actionPerformed(null);
			else if (pan.equals("EVENTS"))
				eventsB_actionPerformed(null);
			else if (pan.equals("FILES"))
				filesB_actionPerformed(null);
			else if (pan.equals("BACKLOG"))
				backlogB_actionPerformed(null);
			else if (pan.equals("SPRINT"))
				sprintB_actionPerformed(null);
			else if (pan.equals("TEAM"))
				teamB_actionPerformed(null);
			else if (pan.equals("TASKBOARD"))
				taskboardB_actionPerformed(null);
		}
	}

	public void backlogB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("BACKLOG");
		unsetCurrentButton();
		setCurrentButton(backlogB);
		Context.put("CURRENT_PANEL", "BACKLOG");
	}

	public void sprintB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("SPRINT");
		unsetCurrentButton();
		setCurrentButton(sprintB);
		Context.put("CURRENT_PANEL", "SPRINT");
	}

	public void taskboardB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKBOARD");
		unsetCurrentButton();
		setCurrentButton(taskboardB);
		Context.put("CURRENT_PANEL", "TASKBOARD");
	}

	public void teamB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TEAM");
		unsetCurrentButton();
		setCurrentButton(teamB);
		Context.put("CURRENT_PANEL", "TEAM");
	}


	public void notesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("NOTES");
		unsetCurrentButton();
		setCurrentButton(notesB);
		Context.put("CURRENT_PANEL", "NOTES");
	}

	public void tasksB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		unsetCurrentButton();
		setCurrentButton(tasksB);
		Context.put("CURRENT_PANEL", "TASKS");
	}

	public void eventsB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("EVENTS");
		unsetCurrentButton();
		setCurrentButton(eventsB);
		Context.put("CURRENT_PANEL", "EVENTS");
	}

	public void filesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "FILES");
		unsetCurrentButton();
		setCurrentButton(filesB);
		Context.put("CURRENT_PANEL", "FILES");
	}

	void setCurrentButton(JButton cb) {
		currentB.setBackground(Color.white);
		currentB.setOpaque(false);
		currentB = cb;
		// Default color blue
		currentB.setBackground(selectedColor);
		currentB.setForeground(selectedTextColor);
		currentB.setOpaque(true);
	}

	void unsetCurrentButton() {
		currentB.setOpaque(false);
		currentB.setBackground(backgroundColor);
		currentB.setForeground(textColor);
		currentB.setOpaque(true);
	}
}