
package main.java.memoranda.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import main.java.memoranda.Project;
import main.java.memoranda.Task;
import main.java.memoranda.Team;
import main.java.memoranda.Member;
import main.java.memoranda.date.CurrentDate;

/**
 *
 */
public class TeamTreeTableCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer, TableCellRenderer {
    static ImageIcon PR_HIGHEST_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/pr_highest.png"));
    static ImageIcon PR_HIGH_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/pr_high.png"));
    static ImageIcon PR_NORMAL_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/pr_normal.png"));
    static ImageIcon PR_LOW_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/pr_low.png"));
    static ImageIcon PR_LOWEST_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/pr_lowest.png"));
    static ImageIcon TASK_ACTIVE_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/task_active.png"));
    static ImageIcon TASK_SCHEDULED_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/task_scheduled.png"));
    static ImageIcon TASK_DEADLINE_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/task_deadline.png"));
    static ImageIcon TASK_FAILED_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/task_failed.png"));
    static ImageIcon TASK_COMPLETED_ICON = new ImageIcon(main.java.memoranda.ui.AppFrame.class
            .getResource("/ui/icons/task_completed.png"));
    // reusable cellrenderers
    JLabel label = new JLabel();
    //JLabel tree_label = new JLabel();
//    TaskProgressLabel progressLabel;
    JPanel empty_panel = new JPanel();
    // get Team objects via table (maybe not most elegant solution)
    TeamTable table;

    //SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.yyyy");
    //  use localized date format, modified from default locale's short format if possible
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);//createModifiedShortFormat();

    public TeamTreeTableCellRenderer(TeamTable table) {
        super();
        this.table = table;
//        progressLabel = new TeamProgressLabel(table);
        label.setOpaque(true);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                                                  boolean expanded, boolean leaf, int row, boolean hasFocus) {
        // if root then just return some component
        // it is not shown anyway
        super.getTreeCellRendererComponent(
                tree, value, selected,
                expanded, leaf, row,
                hasFocus);
        if (value instanceof Project)
            return empty_panel;
        if (!(value instanceof Team))
            return empty_panel;
        Team t = (Team) value;
        setText(t.getName());
        setToolTipText(t.getName());
        setIcon(getStatusIcon(t));
        applyFont(t, this);
        //return getTaskTreeCellRenderer(t, selected, hasFocus);
        return this;
    }

    public Component getTableCellRendererComponent(JTable ignore, Object value, boolean selected,
                                                   boolean hasFocus, int row, int column) {
        Team t = null;
        Member m = null;
        if (value instanceof Team)
            t = (Team) table.getValueAt(row, 1);
        if (value instanceof Member)
            m = (Member) table.getValueAt(row, 1);
//        Team t = new TeamImpl();
        if (column == 1) {
            // this never happens because
            // column 1 contains TreeTableModel
            // and default renderer for it
            // is JTree directly
            return table.getTree();
        }
        // default values
        // label.setOpaque(true);
        label.setForeground(Color.BLACK);
        label.setIcon(null);
        // label.setToolTipText(t.getDescription()); //XXX Disabled because of bug 1596966
        if (t != null)
            applyFont(t, label);
        applySelectionStyle(selected, label);
        applyFocus(hasFocus, label);
        if (value == null) {
            label.setText("");
            return label;
        }
        // if( column_name.equals("% " + Local.getString("done")) ){
        if (column == 6) {
//            return getProgressCellRenderer(t, selected, hasFocus, column);
            return null;
        }
        // if( column_name.equals("") ){
        if (column == 0) {
            if (t != null)
                return getPriorityIconCellRenderer(t, selected, hasFocus);
            if (m != null)
                return getPriorityIconCellRenderer(m, selected, hasFocus);
        }
        // if( column_name.equals(Local.getString("Start date")) ||
        // column_name.equals(Local.getString("End date")) ){
        if ((column == 2) || (column == 3)) {
            label.setText(dateFormat.format((Date) value));
            return label;
        }
        // if( column_name.equals( Local.getString("Status") ) ){
        if (column == 5) {
            label.setText(value.toString());
            label.setForeground(getColorForTeamStatus(t, false));
            return label;
        }
        label.setText(value.toString());
        return label;
    }

    /**
     * Component used to render tree cells in treetable
     */
    private Component getTeamTreeCellRenderer(Team t, boolean selected, boolean hasFocus) {
        JLabel tree_label = new JLabel();
        tree_label.setText(t.getName());
        // XXX [alexeya] Disabled coz a bug with tooltips in TreeTables:
        //tree_label.setToolTipText(t.getDescription());
        tree_label.setIcon(getStatusIcon(t));
        applyFont(t, tree_label);
        return tree_label;
    }
    private Component getTeamTreeCellRenderer(Member t, boolean selected, boolean hasFocus) {
        JLabel tree_label = new JLabel();
        tree_label.setText(t.getName());
        // XXX [alexeya] Disabled coz a bug with tooltips in TreeTables:
        //tree_label.setToolTipText(t.getDescription());
        tree_label.setIcon(getStatusIcon(t));
        applyFont(t, tree_label);
        return tree_label;
    }

    /**
     * Component showing task progress
     */
//    private Component getProgressCellRenderer(Task t, boolean selected, boolean hasFocus, int column) {
//        progressLabel.setTask(t);
//        progressLabel.setColumn(column);
//        applyFocus(hasFocus, progressLabel);
//        return progressLabel;
//    }

    private Component getPriorityIconCellRenderer(Team t, boolean selected, boolean hasFocus) {
        applyFocus(false, label); // disable focus borders
        label.setIcon(getPriorityIcon(t));
        label.setToolTipText(t.getName());
        return label;
    }

    private Component getPriorityIconCellRenderer(Member t, boolean selected, boolean hasFocus) {
        applyFocus(false, label); // disable focus borders
        label.setIcon(getPriorityIcon(t));
        label.setToolTipText(t.getName());
        return label;
    }

    // some convenience methods
    private void applySelectionStyle(boolean selected, JComponent c) {
        if (selected)
            c.setBackground(table.getSelectionBackground());
        else
            c.setBackground(table.getBackground());
    }

    private void applyFocus(boolean hasFocus, JComponent c) {
        if (hasFocus) {
            c.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, table.getSelectionBackground()
                    .darker()));
        } else {
            if (c.getBorder() != null) {
                c.setBorder(null);
            }
        }
    }

    private void applyFont(Team t, JComponent c) {
        c.setFont(c.getFont().deriveFont(Font.PLAIN));
    }
    private void applyFont(Member t, JComponent c) {
        c.setFont(c.getFont().deriveFont(Font.PLAIN));
    }

    /**
     * Color representing task status, "light" color is useful for backgrounds
     * and other for text
     */
    public static Color getColorForTeamStatus(Team t, boolean light) {
        return new Color(192, 255, 192);
    }

    public static ImageIcon getStatusIcon(Team t) {
        return TASK_ACTIVE_ICON;
    }
    public static ImageIcon getStatusIcon(Member t) {
        return TASK_ACTIVE_ICON;
    }

    public static ImageIcon getPriorityIcon(Team t) {
        return PR_NORMAL_ICON;
    }
    public static ImageIcon getPriorityIcon(Member t) {
        return PR_NORMAL_ICON;
    }


}
