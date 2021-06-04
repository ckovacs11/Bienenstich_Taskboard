package main.java.memoranda.ui;

import main.java.memoranda.ProjectManager;
import main.java.memoranda.BacklogList;
import main.java.memoranda.BacklogListImpl;
import main.java.memoranda.BacklogManager;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Local;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*$Id: EventDialog.java,v 1.28 2005/02/19 10:06:25 rawsushi Exp $*/
public class BacklogDialog extends JDialog implements WindowListener {
    public boolean CANCELLED = false;
    boolean ignoreDueChanged = false;
    boolean ignoreEndChanged = false;
    GridBagConstraints gbc;
    private Date BacklogDate;
    private Date BacklogtartDate;
    private Date BacklogEndDate;

    //Labels
    JLabel lblDueDate = new JLabel(); //due date of the Backlog
    JLabel lblEndDate = new JLabel(); //end date of the Backlog
    public JLabel header = new JLabel();
    JLabel lblText = new JLabel();
    JLabel lblSince = new JLabel();
    JLabel lblName = new JLabel();
    JLabel lblDescription = new JLabel();


    //Panels
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel repeatPanel = new JPanel(new GridBagLayout());
    JPanel BacklogPanel = new JPanel(new GridBagLayout());
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

    //buttons
    JButton okB = new JButton();
    JButton cancelB = new JButton();
    JButton setDueDateB = new JButton();
    JButton setEndDateB = new JButton();

    public JSpinner timeSpin = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
    public JSpinner dueDate = new JSpinner(new SpinnerDateModel());
    //public JSpinner endDate = new JSpinner(new SpinnerDateModel());

    public JTextField textField = new JTextField();
    public JTextField descriptionField = new JTextField();

    CalendarFrame endCalFrame = new CalendarFrame();
    CalendarFrame dueCalFrame = new CalendarFrame();


    public BacklogDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        super.addWindowListener(this);
    }

    void jbInit() throws Exception {
    	this.setResizable(false);
        // Build headerPanel
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Backlog"));
        header.setIcon(new ImageIcon(BacklogDialog.class.getResource(
            "/ui/icons/event48.png"))); //change this to be a Backlog icon
        headerPanel.add(header);

        // Build BacklogPanel

        //create components
        lblName.setText(Local.getString("Name"));
        lblName.setMinimumSize(new Dimension(60, 24));
        timeSpin.setPreferredSize(new Dimension(60, 24));
        lblText.setText(Local.getString("Name"));
        lblText.setMinimumSize(new Dimension(120, 24));
        textField.setMinimumSize(new Dimension(300, 24));
        textField.setPreferredSize(new Dimension(300, 24));
        dueDate.setPreferredSize(new Dimension(80, 24));
        lblDueDate.setText(Local.getString("Due Date"));
        lblDueDate.setMinimumSize(new Dimension(60, 24));
        lblDescription.setText(Local.getString("Description"));
        lblDescription.setMinimumSize(new Dimension(60, 24));
		descriptionField.setText(Local.getString("Description"));
        descriptionField.setMinimumSize(new Dimension(120, 24));

        /*-----------------*
        * place components *
        *------------------*/
        //add name label
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        BacklogPanel.add(lblText, gbc);
        //add text field
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        BacklogPanel.add(textField, gbc);
        //add description label
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        BacklogPanel.add(lblDescription, gbc);
        //add description field
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        BacklogPanel.add(descriptionField, gbc);
        //add due date label
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        BacklogPanel.add(lblDueDate, gbc);

        //add start date date selectors
        //Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
        //---------------------------------------------------
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
        dueDate.setEditor(new JSpinner.DateEditor(dueDate,
                sdf.toPattern()));
        //---------------------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        BacklogPanel.add(dueDate, gbc);
        //controls popup calendar
        setDueDateB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setDueDateB_actionPerformed(e);
            }
        });
        setDueDateB.setIcon(
                new ImageIcon(AppFrame.class.getResource("/ui/icons/calendar.png")));
        setDueDateB.setText("");
        setDueDateB.setPreferredSize(new Dimension(24, 24));

        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 5;
        gbc.insets = new Insets(5, 110, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        BacklogPanel.add(setDueDateB, gbc);

        // Build ButtonsPanel
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(okB);
        cancelB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        
        // Finally build the Dialog
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(BacklogPanel, BorderLayout.CENTER);
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        
        // Do final things...
        dueCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreDueChanged) return;
                dueDate.getModel().setValue(dueCalFrame.cal.get().getCalendar().getTime());
            }
        });
        ((JSpinner.DateEditor) timeSpin.getEditor()).getFormat().applyPattern("HH:mm");
    }

    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    void setDueDateB_actionPerformed(ActionEvent e) {
        //startCalFrame.setLocation(setStartDateB.getLocation());
        dueCalFrame.setSize(200, 190);
        dueCalFrame.setTitle(Local.getString("Due date"));
        this.getLayeredPane().add(dueCalFrame);
        dueCalFrame.show();
    }
    
    public void windowOpened( WindowEvent e ) {}

    public void windowClosing( WindowEvent e ) {
        CANCELLED = true;
        this.dispose();
    }
    
    public void setBacklogDate(Date d) {
	    BacklogDate = d;
	}
	
	public Date getBacklogDate() {
		return BacklogDate;
	}
	
    public void windowClosed( WindowEvent e ) {}

	public void windowIconified( WindowEvent e ) {}

	public void windowDeiconified( WindowEvent e ) {}

	public void windowActivated( WindowEvent e ) {}

	public void windowDeactivated( WindowEvent e ) {}

}