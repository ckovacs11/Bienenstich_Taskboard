package main.java.memoranda.ui;

import main.java.memoranda.ProjectManager;
import main.java.memoranda.SprintList;
import main.java.memoranda.SprintListImpl;
import main.java.memoranda.SprintsManager;
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
public class SprintDialog extends JDialog implements WindowListener {
    public boolean CANCELLED = false;
    boolean ignoreStartChanged = false;
    boolean ignoreEndChanged = false;
    GridBagConstraints gbc;
    private Date sprintDate;
    private Date sprintStartDate;
    private Date sprintEndDate;

    //Labels
    JLabel lblStartDate = new JLabel(); //start date of the sprint
    JLabel lblEndDate = new JLabel(); //end date of the sprint
    public JLabel header = new JLabel();
    JLabel lblText = new JLabel();
    JLabel lblSince = new JLabel();
    JLabel lblName = new JLabel();


    //Panels
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel repeatPanel = new JPanel(new GridBagLayout());
    JPanel sprintPanel = new JPanel(new GridBagLayout());
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

    //buttons
    JButton okB = new JButton();
    JButton cancelB = new JButton();
    JButton setStartDateB = new JButton();
    JButton setEndDateB = new JButton();

    public JSpinner timeSpin = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
    public JSpinner startDate = new JSpinner(new SpinnerDateModel());
    public JSpinner endDate = new JSpinner(new SpinnerDateModel());

    public JTextField textField = new JTextField();

    CalendarFrame endCalFrame = new CalendarFrame();
    CalendarFrame startCalFrame = new CalendarFrame();


    public SprintDialog(Frame frame, String title) {
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
        header.setText(Local.getString("Sprint"));
        header.setIcon(new ImageIcon(SprintDialog.class.getResource(
            "/ui/icons/event48.png"))); //change this to be a sprint icon
        headerPanel.add(header);

        // Build sprintPanel

        //create components
        lblName.setText(Local.getString("Name"));
        lblName.setMinimumSize(new Dimension(60, 24));
        timeSpin.setPreferredSize(new Dimension(60, 24)); //time spinner by start date?
        lblText.setText(Local.getString("Name"));
        lblText.setMinimumSize(new Dimension(120, 24));
        textField.setMinimumSize(new Dimension(300, 24));
        textField.setPreferredSize(new Dimension(300, 24));
        startDate.setPreferredSize(new Dimension(80, 24));
        lblStartDate.setText(Local.getString("Start Date"));
        lblStartDate.setMinimumSize(new Dimension(60, 24));
        lblEndDate.setText(Local.getString("End Date"));
        lblEndDate.setMinimumSize(new Dimension(60, 24));

        /*-----------------*
        * place components *
        *------------------*/
        //add name label
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        sprintPanel.add(lblText, gbc);
        //add text field
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sprintPanel.add(textField, gbc);
        //add start date label
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        sprintPanel.add(lblStartDate, gbc);
        //add end date label
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        sprintPanel.add(lblEndDate, gbc);

        //add start date date selectors
        //Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
        //---------------------------------------------------
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
        startDate.setEditor(new JSpinner.DateEditor(startDate,
                sdf.toPattern()));
        //---------------------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        sprintPanel.add(startDate, gbc);
        //controls popup calendar
        setStartDateB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStartDateB_actionPerformed(e);
            }
        });
        setStartDateB.setIcon(
                new ImageIcon(AppFrame.class.getResource("/ui/icons/calendar.png")));
        setStartDateB.setText("");
        setStartDateB.setPreferredSize(new Dimension(24, 24));

        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 3;
        gbc.insets = new Insets(5, 110, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        sprintPanel.add(setStartDateB, gbc);

        //end date
        endDate.setPreferredSize(new Dimension(80, 24));

        //Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
        //---------------------------------------------------
        SimpleDateFormat sdfEnd = new SimpleDateFormat();
        sdfEnd = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
        endDate.setEditor(new JSpinner.DateEditor(endDate,
                sdfEnd.toPattern()));
        //---------------------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        sprintPanel.add(endDate, gbc);
        //controls popup calendar
        setEndDateB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEndDateB_actionPerformed(e);
            }
        });
        setEndDateB.setIcon(
                new ImageIcon(AppFrame.class.getResource("/ui/icons/calendar.png")));
        setEndDateB.setText("");
        setEndDateB.setPreferredSize(new Dimension(24, 24));

        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 4;
        gbc.insets = new Insets(5, 110, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        sprintPanel.add(setEndDateB, gbc);

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
        topPanel.add(sprintPanel, BorderLayout.CENTER);
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        
        // Do final things...
        startCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreStartChanged) return;
                startDate.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
            }
        });
        endCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreEndChanged)
                    return;
                endDate.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
            }
        });
        ((JSpinner.DateEditor) timeSpin.getEditor()).getFormat().applyPattern("HH:mm");
        enableEndDateCB_actionPerformed(null);
        
    }

    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        //Date startDateValue = (Date)startDate.getValue();
        //Date endDateValue = (Date)endDate.getValue();
        //String name = textField.getText();
        //SprintListImpl.createSprint(new CalendarDate(startDateValue), new CalendarDate(endDateValue), name);
        //SprintListImpl.getActiveSprints(new CalendarDate(startDateValue));

        //SprintsManager.createSprint(new CalendarDate(startDateValue), new CalendarDate(endDateValue), name);
        //SprintsManager.getSprints();
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    void setStartDateB_actionPerformed(ActionEvent e) {
        //startCalFrame.setLocation(setStartDateB.getLocation());
        startCalFrame.setSize(200, 190);
        startCalFrame.setTitle(Local.getString("Start date"));
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.show();
    }

    void setEndDateB_actionPerformed(ActionEvent e) {
        //endCalFrame.setLocation(setEndDateB.getLocation());
        endCalFrame.setSize(200, 190);
        endCalFrame.setTitle(Local.getString("End date"));
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.show();
    }

    public void enableEndDateCB_actionPerformed(ActionEvent e) {
        //endDate.setEnabled(enableEndDateCB.isSelected());
        //setEndDateB.setEnabled(enableEndDateCB.isSelected());
    }
    
    public void windowOpened( WindowEvent e ) {}

    public void windowClosing( WindowEvent e ) {
        CANCELLED = true;
        this.dispose();
    }
    
    public void setSprintDate(Date d) {
	    sprintDate = d;
	}
	
	public Date getSprintDate() {
		return sprintDate;
	}
	
    public void windowClosed( WindowEvent e ) {}

	public void windowIconified( WindowEvent e ) {}

	public void windowDeiconified( WindowEvent e ) {}

	public void windowActivated( WindowEvent e ) {}

	public void windowDeactivated( WindowEvent e ) {}

}