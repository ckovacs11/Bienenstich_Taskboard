package main.java.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;

import main.java.memoranda.*;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.ui.htmleditor.HTMLEditor;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.ProjectExporter;
import main.java.memoranda.util.ProjectPackager;
import main.java.memoranda.util.Util;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import main.java.memoranda.SprintList;
import main.java.memoranda.SprintListImpl;
import main.java.memoranda.BacklogList;
import main.java.memoranda.BacklogListImpl;

// These classes are used create JMenu and JMenuItem objects with colors applied
class SepD extends JSeparator {
    public SepD() {
        this.setBackground(Color.gray);
        this.setForeground(Color.black);
    }
}
class JMenuD extends JMenu {
    public JMenuD() {
        initMenu();
    }
    private void initMenu() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setBorderPainted(false);
        this.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        this.setBackground(Color.darkGray);
        this.setForeground(Color.white);
        this.getPopupMenu().setBorder(BorderFactory.createLineBorder(Color.black, 1));
        this.setOpaque(true);
    }
}
class JMenuItemD extends JMenuItem {
    public JMenuItemD() {
        this.initItem();
    }
    public JMenuItemD(javax.swing.Action a) {
        super(a);
        this.initItem();
    }
    private void initItem() {
        this.setFont(new java.awt.Font("Dialog", Font.PLAIN, 14));
        this.setBackground(Color.darkGray);
        this.setForeground(Color.white);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setBorderPainted(false);
//        this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        this.setOpaque(true);
    }
}

/**
* Contains menu bar buttons
*/
public class AppFrame extends JFrame {
    JPanel contentPane;
    JMenuBar menuBar = new JMenuBar();
    JMenu jMenuFile = new JMenuD();
    JMenuItem jMenuFileExit = new JMenuItemD();
    JToolBar toolBar = new JToolBar();
    JButton jButton3 = new JButton();
    ImageIcon image1;
    ImageIcon image2;
    ImageIcon image3;
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JSplitPane splitPane = new JSplitPane();
    ProjectsPanel projectsPanel = new ProjectsPanel();
    boolean prPanelExpanded = false;

    // colors
//    Color menuBackgroundColor = Color.gray;
//    Color menuTextColor = Color.white;

    // New jMenu
    JMenu jMenuEdit = new JMenuD();
    JMenu jMenuFormat = new JMenuD();
    JMenu jMenuInsert = new JMenuD();

    public WorkPanel workPanel = new WorkPanel();
    HTMLEditor editor = workPanel.dailyItemsPanel.editorPanel.editor;
    static Vector exitListeners = new Vector();

    // Actions
    public Action prjPackAction = new AbstractAction("Pack current project") {
        public void actionPerformed(ActionEvent e) { doPrjPack(); }
    };
    public Action prjUnpackAction = new AbstractAction("Unpack project") {
        public void actionPerformed(ActionEvent e) { doPrjUnPack(); }
    };
    public Action minimizeAction = new AbstractAction("Close the window") {
        public void actionPerformed(ActionEvent e) { doMinimize(); }
    };
    public Action preferencesAction = new AbstractAction("Preferences") {
        public void actionPerformed(ActionEvent e) { showPreferences(); }
    };
    public Action exportNotesAction = new AbstractAction(Local.getString("Export notes") + "...") {
        public void actionPerformed(ActionEvent e) { ppExport_actionPerformed(e); }
    };
    public Action importNotesAction = new AbstractAction(Local.getString("Import multiple notes")) {
        public void actionPerformed(ActionEvent e) { ppImport_actionPerformed(e); }
    };
    public Action importOneNoteAction = new AbstractAction(Local.getString("Import one note")) {
        public void actionPerformed(ActionEvent e) { p1Import_actionPerformed(e); }
    };

    JMenuItem jMenuFileNewPrj = new JMenuItemD();
    JMenuItem jMenuFileNewNote = new JMenuItemD(workPanel.dailyItemsPanel.editorPanel.newAction);
    JMenuItem jMenuFilePackPrj = new JMenuItemD(prjPackAction);
    JMenuItem jMenuFileUnpackPrj = new JMenuItemD(prjUnpackAction);
    JMenuItem jMenuFileExportPrj = new JMenuItemD(exportNotesAction);
    JMenuItem jMenuFileImportPrj = new JMenuItemD(importNotesAction);
    JMenuItem jMenuFileImportNote = new JMenuItemD(importOneNoteAction);
    JMenuItem jMenuFileExportNote = new JMenuItemD(workPanel.dailyItemsPanel.editorPanel.exportAction);
    JMenuItem jMenuFileMin = new JMenuItemD(minimizeAction);


    JMenuItem jMenuItem1 = new JMenuItemD();
    JMenuItem jMenuEditUndo = new JMenuItemD(editor.undoAction);
    JMenuItem jMenuEditRedo = new JMenuItemD(editor.redoAction);
    JMenuItem jMenuEditCut = new JMenuItemD(editor.cutAction);
    JMenuItem jMenuEditCopy = new JMenuItemD(editor.copyAction);
    JMenuItem jMenuEditPaste = new JMenuItemD(editor.pasteAction);
    JMenuItem jMenuEditPasteSpec = new JMenuItemD(editor.stylePasteAction);
    JMenuItem jMenuEditSelectAll = new JMenuItemD(editor.selectAllAction);
    JMenuItem jMenuEditFind = new JMenuItemD(editor.findAction);

    // Go
    JMenu jMenuGo = new JMenuD();
    JMenuItem jMenuInsertImage = new JMenuItemD(editor.imageAction);
    JMenuItem jMenuInsertTable = new JMenuItemD(editor.tableAction);
    JMenuItem jMenuInsertLink = new JMenuItemD(editor.linkAction);

    // Insert
    JMenu jMenuInsertList = new JMenuD();
    JMenuItem jMenuInsertListUL = new JMenuItemD(editor.ulAction);
    JMenuItem jMenuInsertListOL = new JMenuItemD(editor.olAction);
    JMenuItem jMenuInsertBR = new JMenuItemD(editor.breakAction);
    JMenuItem jMenuInsertHR = new JMenuItemD(editor.insertHRAction);
    JMenuItem jMenuInsertChar = new JMenuItemD(editor.insCharAction);
    JMenuItem jMenuInsertDate = new JMenuItemD(workPanel.dailyItemsPanel.editorPanel.insertDateAction);
    JMenuItem jMenuInsertTime = new JMenuItemD(workPanel.dailyItemsPanel.editorPanel.insertTimeAction);
    JMenuItem jMenuInsertFile = new JMenuItemD(workPanel.dailyItemsPanel.editorPanel.importAction);

    JMenu jMenuFormatPStyle = new JMenuD();
    JMenuItem jMenuFormatP = new JMenuItemD(editor.new BlockAction(editor.T_P, ""));
    JMenuItem jMenuFormatH1 = new JMenuItemD(editor.new BlockAction(editor.T_H1, ""));
    JMenuItem jMenuFormatH2 = new JMenuItemD(editor.new BlockAction(editor.T_H2, ""));
    JMenuItem jMenuFormatH3 = new JMenuItemD(editor.new BlockAction(editor.T_H3, ""));
    JMenuItem jMenuFormatH4 = new JMenuItemD(editor.new BlockAction(editor.T_H4, ""));
    JMenuItem jMenuFormatH5 = new JMenuItemD(editor.new BlockAction(editor.T_H5, ""));
    JMenuItem jMenuFormatH6 = new JMenuItemD(editor.new BlockAction(editor.T_H6, ""));
    JMenuItem jMenuFormatPRE = new JMenuItemD(editor.new BlockAction(editor.T_PRE, ""));
    JMenuItem jMenuFormatBLCQ = new JMenuItemD(editor.new BlockAction(editor.T_BLOCKQ, ""));
    JMenu jjMenuFormatChStyle = new JMenuD();
    JMenuItem jMenuFormatChNorm = new JMenuItemD(editor.new InlineAction(editor.I_NORMAL, ""));
    JMenuItem jMenuFormatChEM = new JMenuItemD(editor.new InlineAction(editor.I_EM, ""));
    JMenuItem jMenuFormatChSTRONG = new JMenuItemD(editor.new InlineAction(editor.I_STRONG, ""));
    JMenuItem jMenuFormatChCODE = new JMenuItemD(editor.new InlineAction(editor.I_CODE, ""));
    JMenuItem jMenuFormatChCite = new JMenuItemD(editor.new InlineAction(editor.I_CITE, ""));
    JMenuItem jMenuFormatChSUP = new JMenuItemD(editor.new InlineAction(editor.I_SUPERSCRIPT, ""));
    JMenuItem jMenuFormatChSUB = new JMenuItemD(editor.new InlineAction(editor.I_SUBSCRIPT, ""));
    JMenuItem jMenuFormatChCustom = new JMenuItemD(editor.new InlineAction(editor.I_CUSTOM, ""));
    JMenuItem jMenuFormatChB = new JMenuItemD(editor.boldAction);
    JMenuItem jMenuFormatChI = new JMenuItemD(editor.italicAction);
    JMenuItem jMenuFormatChU = new JMenuItemD(editor.underAction);
    JMenu jMenuFormatAlign = new JMenuD();
    JMenuItem jMenuFormatAlignL = new JMenuItemD(editor.lAlignAction);
    JMenuItem jMenuFormatAlignC = new JMenuItemD(editor.cAlignAction);
    JMenuItem jMenuFormatAlignR = new JMenuItemD(editor.rAlignAction);
    JMenu jMenuFormatTable = new JMenuD();
    JMenuItem jMenuFormatTableInsR = new JMenuItemD(editor.insertTableRowAction);
    JMenuItem jMenuFormatTableInsC = new JMenuItemD(editor.insertTableCellAction);
    JMenuItem jMenuFormatProperties = new JMenuItemD(editor.propsAction);
    JMenuItem jMenuGoHBack = new JMenuItemD(History.historyBackAction);
    JMenuItem jMenuGoFwd = new JMenuItemD(History.historyForwardAction);

    JMenuItem jMenuGoDayBack = new JMenuItemD(workPanel.dailyItemsPanel.calendar.dayBackAction);
    JMenuItem jMenuGoDayFwd = new JMenuItemD(workPanel.dailyItemsPanel.calendar.dayForwardAction);
    JMenuItem jMenuGoToday = new JMenuItemD(workPanel.dailyItemsPanel.calendar.todayAction);

    JMenuItem jMenuEditPref = new JMenuItemD(preferencesAction);

    JMenu jMenuInsertSpecial = new JMenuD();

    JMenu jMenuHelp = new JMenuD();

    JMenuItem jMenuHelpSlack = new JMenuItemD();
    JMenuItem jMenuHelpGitHub = new JMenuItemD();
    JMenuItem jMenuHelpTrello = new JMenuItemD();
    JMenuItem jMenuHelpAbout = new JMenuItemD();

    //Construct the frame
    public AppFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch (Exception e) {
            new ExceptionDialog(e);
        }
    }
    //Component initialization
    private void jbInit() throws Exception {
        // Set the icon
        this.setIconImage(new ImageIcon(AppFrame.class.getResource("/ui/icons/bienenstich_icon.png")).getImage());
        menuBar.setBackground(Color.darkGray);
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        //this.setSize(new Dimension(800, 500));

        // Set the title
        this.setTitle("Bienenstich Scrum Board - " + CurrentProject.get().getTitle());

        //Added a space to App.VERSION_INFO to make it look some nicer
        statusBar.setText(" Version:" + App.VERSION_INFO + " (Build "
                + App.BUILD_INFO + " )");

        jMenuFile.setText(Local.getString("File"));
        jMenuFileExit.setText(Local.getString("Exit"));
        jMenuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });
        jMenuHelp.setText(Local.getString("Help"));

        jMenuHelpSlack.setText(Local.getString("Bienenstich General Slack"));
        jMenuHelpSlack.setIcon(new ImageIcon(AppFrame.class.getResource("/ui/icons/slack_64.png")));
//        jMenuHelpSlack.setBackground(menuBackgroundColor);


        jMenuHelpSlack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpSlack_actionPerformed(e);
            }
        });

        jMenuHelpGitHub.setText(Local.getString("Bienenstich Github"));
        jMenuHelpGitHub.setIcon(new ImageIcon(AppFrame.class.getResource(
                "/ui/icons/github_64.png")));
        jMenuHelpGitHub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpGitHub_actionPerformed(e);
            }
        });

        jMenuHelpTrello.setText(Local.getString("Bienenstich Trello"));
        jMenuHelpTrello.setIcon(new ImageIcon(AppFrame.class.getResource(
                "/ui/icons/trello_64.png")));
        jMenuHelpTrello.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpTrello_actionPerformed(e);
            }
        });

        jMenuHelpAbout.setText(Local.getString("About Bienenstich Scrum Board"));
        jMenuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpAbout_actionPerformed(e);
            }
        });
        //jButton3.setIcon(image3);
        jButton3.setToolTipText(Local.getString("Help"));
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(3);
        //splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(28);
        //projectsPanel.setMaximumSize(new Dimension(2147483647, 200));
        projectsPanel.setMinimumSize(new Dimension(10, 24));
        projectsPanel.setPreferredSize(new Dimension(10, 28));
        workPanel.setMinimumSize(new Dimension(734, 300));
        workPanel.setPreferredSize(new Dimension(1073, 300));
        splitPane.setDividerLocation(24);

        /* jMenuFileNewPrj.setText(Local.getString("New project") + "...");
         jMenuFileNewPrj.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         ProjectDialog.newProject();
         }
         });
         */
        jMenuFileNewPrj.setAction(projectsPanel.newProjectAction);

        jMenuFileUnpackPrj.setText(Local.getString("Unpack project") + "...");
        jMenuFileExportNote.setText(Local.getString("Export current note") + "...");
        jMenuFileImportNote.setText(Local.getString("Import one note") + "...");
        jMenuFilePackPrj.setText(Local.getString("Pack project") + "...");
        jMenuFileMin.setText(Local.getString("Close the window"));
        jMenuFileMin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, InputEvent.ALT_MASK));

        jMenuEdit.setText(Local.getString("Edit"));

        jMenuEditUndo.setText(Local.getString("Undo"));
        jMenuEditUndo.setToolTipText(Local.getString("Undo"));
        jMenuEditRedo.setText(Local.getString("Redo"));
        jMenuEditRedo.setToolTipText(Local.getString("Redo"));
        jMenuEditCut.setText(Local.getString("Cut"));
        jMenuEditCut.setToolTipText(Local.getString("Cut"));
        jMenuEditCopy.setText((String) Local.getString("Copy"));
        jMenuEditCopy.setToolTipText(Local.getString("Copy"));
        jMenuEditPaste.setText(Local.getString("Paste"));
        jMenuEditPaste.setToolTipText(Local.getString("Paste"));
        jMenuEditPasteSpec.setText(Local.getString("Paste special"));
        jMenuEditPasteSpec.setToolTipText(Local.getString("Paste special"));
        jMenuEditSelectAll.setText(Local.getString("Select all"));

        jMenuEditFind.setText(Local.getString("Find & replace") + "...");

        jMenuEditPref.setText(Local.getString("Preferences") + "...");

        jMenuInsert.setText(Local.getString("Insert"));

        jMenuInsertImage.setText(Local.getString("Image") + "...");
        jMenuInsertImage.setToolTipText(Local.getString("Insert Image"));
        jMenuInsertTable.setText(Local.getString("Table") + "...");
        jMenuInsertTable.setToolTipText(Local.getString("Insert Table"));
        jMenuInsertLink.setText(Local.getString("Hyperlink") + "...");
        jMenuInsertLink.setToolTipText(Local.getString("Insert Hyperlink"));
        jMenuInsertList.setText(Local.getString("List"));

        jMenuInsertListUL.setText(Local.getString("Unordered"));
        jMenuInsertListUL.setToolTipText(Local.getString("Insert Unordered"));
        jMenuInsertListOL.setText(Local.getString("Ordered"));

        jMenuInsertSpecial.setText(Local.getString("Special"));
        jMenuInsertBR.setText(Local.getString("Line break"));
        jMenuInsertHR.setText(Local.getString("Horizontal rule"));

        jMenuInsertListOL.setToolTipText(Local.getString("Insert Ordered"));

        jMenuInsertChar.setText(Local.getString("Special character") + "...");
        jMenuInsertChar.setToolTipText(Local.getString("Insert Special character"));
        jMenuInsertDate.setText(Local.getString("Current date"));
        jMenuInsertTime.setText(Local.getString("Current time"));
        jMenuInsertFile.setText(Local.getString("File") + "...");

        jMenuFormat.setText(Local.getString("Format"));
        jMenuFormatPStyle.setText(Local.getString("Paragraph style"));
        jMenuFormatP.setText(Local.getString("Paragraph"));
        jMenuFormatH1.setText(Local.getString("Header") + " 1");
        jMenuFormatH2.setText(Local.getString("Header") + " 2");
        jMenuFormatH3.setText(Local.getString("Header") + " 3");
        jMenuFormatH4.setText(Local.getString("Header") + " 4");
        jMenuFormatH5.setText(Local.getString("Header") + " 5");
        jMenuFormatH6.setText(Local.getString("Header") + " 6");
        jMenuFormatPRE.setText(Local.getString("Preformatted text"));
        jMenuFormatBLCQ.setText(Local.getString("Blockquote"));
        jjMenuFormatChStyle.setText(Local.getString("Character style"));
        jMenuFormatChNorm.setText(Local.getString("Normal"));
        jMenuFormatChEM.setText(Local.getString("Emphasis"));
        jMenuFormatChSTRONG.setText(Local.getString("Strong"));
        jMenuFormatChCODE.setText(Local.getString("Code"));
        jMenuFormatChCite.setText(Local.getString("Cite"));
        jMenuFormatChSUP.setText(Local.getString("Superscript"));
        jMenuFormatChSUB.setText(Local.getString("Subscript"));
        jMenuFormatChCustom.setText(Local.getString("Custom style") + "...");
        jMenuFormatChB.setText(Local.getString("Bold"));
        jMenuFormatChB.setToolTipText(Local.getString("Bold"));
        jMenuFormatChI.setText(Local.getString("Italic"));
        jMenuFormatChI.setToolTipText(Local.getString("Italic"));
        jMenuFormatChU.setText(Local.getString("Underline"));
        jMenuFormatChU.setToolTipText(Local.getString("Underline"));
        jMenuFormatAlign.setText(Local.getString("Alignment"));
        jMenuFormatAlignL.setText(Local.getString("Left"));
        jMenuFormatAlignL.setToolTipText(Local.getString("Left"));
        jMenuFormatAlignC.setText(Local.getString("Center"));
        jMenuFormatAlignC.setToolTipText(Local.getString("Center"));
        jMenuFormatAlignR.setText(Local.getString("Right"));
        jMenuFormatAlignR.setToolTipText(Local.getString("Right"));
        jMenuFormatTable.setText(Local.getString("Table"));
        jMenuFormatTableInsR.setText(Local.getString("Insert row"));
        jMenuFormatTableInsC.setText(Local.getString("Insert cell"));
        jMenuFormatProperties.setText(Local.getString("Object properties") + "...");
        jMenuFormatProperties.setToolTipText(Local.getString("Object properties"));

        jMenuGo.setText(Local.getString("Go"));
        jMenuGoHBack.setText(Local.getString("History back"));
        jMenuGoHBack.setToolTipText(Local.getString("History back"));
        jMenuGoFwd.setText(Local.getString("History forward"));
        jMenuGoFwd.setToolTipText(Local.getString("History forward"));
        jMenuGoDayBack.setText(Local.getString("One day back"));
        jMenuGoDayFwd.setText(Local.getString("One day forward"));
        jMenuGoToday.setText(Local.getString("To today"));

        jMenuInsertSpecial.setText(Local.getString("Special"));
        jMenuInsertBR.setText(Local.getString("Line break"));
        jMenuInsertBR.setToolTipText(Local.getString("Insert break"));
        jMenuInsertHR.setText(Local.getString("Horizontal rule"));
        jMenuInsertHR.setToolTipText(Local.getString("Insert Horizontal rule"));

        toolBar.add(jButton3);
        jMenuFile.add(jMenuFileNewPrj);
        jMenuFile.add(jMenuFileNewNote);
        jMenuFile.add(new SepD());
        jMenuFile.add(jMenuFilePackPrj);
        jMenuFile.add(jMenuFileUnpackPrj);
        jMenuFile.add(new SepD());
        jMenuFile.add(jMenuFileExportPrj);
        jMenuFile.add(jMenuFileExportNote);
        jMenuFile.add(jMenuFileImportNote);
        jMenuFile.add(jMenuFileImportPrj);
        jMenuFile.add(new SepD());
        jMenuFile.add(jMenuEditPref);
        jMenuFile.add(new SepD());
        jMenuFile.add(jMenuFileMin);
        jMenuFile.add(new SepD());
        jMenuFile.add(jMenuFileExit);

        jMenuHelp.add(jMenuHelpSlack);
        jMenuHelp.add(jMenuHelpGitHub);
        jMenuHelp.add(jMenuHelpTrello);
        jMenuHelp.add(new SepD());
        jMenuHelp.add(jMenuHelpAbout);

        menuBar.add(jMenuFile);
        menuBar.add(jMenuEdit);
        menuBar.add(jMenuInsert);
        menuBar.add(jMenuFormat);
        menuBar.add(jMenuGo);
        menuBar.add(jMenuHelp);
        this.setJMenuBar(menuBar);
//        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(splitPane, BorderLayout.CENTER);
        splitPane.add(projectsPanel, JSplitPane.TOP);
        splitPane.add(workPanel, JSplitPane.BOTTOM);
        jMenuEdit.add(jMenuEditUndo);
        jMenuEdit.add(jMenuEditRedo);
        jMenuEdit.add(new SepD());
        jMenuEdit.add(jMenuEditCut);
        jMenuEdit.add(jMenuEditCopy);
        jMenuEdit.add(jMenuEditPaste);
        jMenuEdit.add(jMenuEditPasteSpec);
        jMenuEdit.add(new SepD());
        jMenuEdit.add(jMenuEditSelectAll);
        jMenuEdit.add(new SepD());
        jMenuEdit.add(jMenuEditFind);

        jMenuInsert.add(jMenuInsertImage);
        jMenuInsert.add(jMenuInsertTable);
        jMenuInsert.add(jMenuInsertLink);
        jMenuInsert.add(jMenuInsertList);
        //jMenuInsert.add(jMenuInsertSpecial);
        jMenuInsertList.add(jMenuInsertListUL);
        jMenuInsertList.add(jMenuInsertListOL);
        jMenuInsert.add(new SepD());
        jMenuInsert.add(jMenuInsertBR);
        jMenuInsert.add(jMenuInsertHR);
        jMenuInsert.add(jMenuInsertChar);
        jMenuInsert.add(new SepD());
        jMenuInsert.add(jMenuInsertDate);
        jMenuInsert.add(jMenuInsertTime);
        jMenuInsert.add(new SepD());
        jMenuInsert.add(jMenuInsertFile);

        jMenuFormat.add(jMenuFormatPStyle);
        jMenuFormat.add(jjMenuFormatChStyle);
        jMenuFormat.add(jMenuFormatAlign);
        jMenuFormat.add(new SepD());
        jMenuFormat.add(jMenuFormatTable);
        jMenuFormat.add(new SepD());
        jMenuFormat.add(jMenuFormatProperties);
        jMenuFormatPStyle.add(jMenuFormatP);
        jMenuFormatPStyle.add(new SepD());
        jMenuFormatPStyle.add(jMenuFormatH1);
        jMenuFormatPStyle.add(jMenuFormatH2);
        jMenuFormatPStyle.add(jMenuFormatH3);
        jMenuFormatPStyle.add(jMenuFormatH4);
        jMenuFormatPStyle.add(jMenuFormatH5);
        jMenuFormatPStyle.add(jMenuFormatH6);
        jMenuFormatPStyle.add(new SepD());
        jMenuFormatPStyle.add(jMenuFormatPRE);
        jMenuFormatPStyle.add(jMenuFormatBLCQ);
        jjMenuFormatChStyle.add(jMenuFormatChNorm);
        jjMenuFormatChStyle.add(new SepD());
        jjMenuFormatChStyle.add(jMenuFormatChB);
        jjMenuFormatChStyle.add(jMenuFormatChI);
        jjMenuFormatChStyle.add(jMenuFormatChU);
        jjMenuFormatChStyle.add(new SepD());
        jjMenuFormatChStyle.add(jMenuFormatChEM);
        jjMenuFormatChStyle.add(jMenuFormatChSTRONG);
        jjMenuFormatChStyle.add(jMenuFormatChCODE);
        jjMenuFormatChStyle.add(jMenuFormatChCite);
        jjMenuFormatChStyle.add(new SepD());
        jjMenuFormatChStyle.add(jMenuFormatChSUP);
        jjMenuFormatChStyle.add(jMenuFormatChSUB);
        jjMenuFormatChStyle.add(new SepD());
        jjMenuFormatChStyle.add(jMenuFormatChCustom);
        jMenuFormatAlign.add(jMenuFormatAlignL);
        jMenuFormatAlign.add(jMenuFormatAlignC);
        jMenuFormatAlign.add(jMenuFormatAlignR);
        jMenuFormatTable.add(jMenuFormatTableInsR);
        jMenuFormatTable.add(jMenuFormatTableInsC);
        jMenuGo.add(jMenuGoHBack);
        jMenuGo.add(jMenuGoFwd);
        jMenuGo.add(new SepD());
        jMenuGo.add(jMenuGoDayBack);
        jMenuGo.add(jMenuGoDayFwd);
        jMenuGo.add(jMenuGoToday);

        splitPane.setBorder(null);
        workPanel.setBorder(null);

        setEnabledEditorMenus(false);

        projectsPanel.AddExpandListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (prPanelExpanded) {
                    prPanelExpanded = false;
                    splitPane.setDividerLocation(24);
                }
                else {
                    prPanelExpanded = true;
                    splitPane.setDividerLocation(0.2);
                }
            }
        });

        java.awt.event.ActionListener setMenusDisabled = new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEnabledEditorMenus(false);
            }
        };

        this.workPanel.dailyItemsPanel.taskB.addActionListener(setMenusDisabled);
        this.workPanel.dailyItemsPanel.alarmB.addActionListener(setMenusDisabled);

        this.workPanel.backlogB.addActionListener(setMenusDisabled);
        this.workPanel.tasksB.addActionListener(setMenusDisabled);
        this.workPanel.eventsB.addActionListener(setMenusDisabled);
        this.workPanel.filesB.addActionListener(setMenusDisabled);


        this.workPanel.notesB.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setEnabledEditorMenus(true);
                    }
                });

        Object fwo = Context.get("FRAME_WIDTH");
        Object fho = Context.get("FRAME_HEIGHT");
        if ((fwo != null) && (fho != null)) {
            int w = new Integer((String) fwo).intValue();
            int h = new Integer((String) fho).intValue();
            this.setSize(w, h);
        }
        else
            this.setExtendedState(Frame.MAXIMIZED_BOTH);

        Object xo = Context.get("FRAME_XPOS");
        Object yo = Context.get("FRAME_YPOS");
        if ((xo != null) && (yo != null)) {
            int x = new Integer((String) xo).intValue();
            int y = new Integer((String) yo).intValue();
            this.setLocation(x, y);
        }

        String pan = (String) Context.get("CURRENT_PANEL");
        if (pan != null) {
            workPanel.selectPanel(pan);
            setEnabledEditorMenus(pan.equalsIgnoreCase("NOTES"));
        }

        CurrentProject.addProjectListener(new ProjectListener() {


            public void projectChange(Project prj, NoteList nl, TaskList tl, TaskboardList tbl, 
                    ResourcesList rl, SprintList sprintList, TeamList teal, BacklogList back1) {

            }

            public void projectWasChanged() {
                setTitle("Bienenstich Scrum Board - " + CurrentProject.get().getTitle());
            }
        });

    }

    protected void jMenuHelpTrello_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.BUGS_TRACKER_URL);
    }

    protected void jMenuHelpGitHub_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.WEBSITE_URL);
    }

    protected void jMenuHelpSlack_actionPerformed(ActionEvent e) {
        Util.runBrowser(App.GUIDE_URL);
    }

    //File | Exit action performed
    public void doExit() {
        if (Configuration.get("ASK_ON_EXIT").equals("yes")) {
                        Dimension frmSize = this.getSize();
                        Point loc = this.getLocation();

                        ExitConfirmationDialog dlg = new ExitConfirmationDialog(this,Local.getString("Exit"));
                        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
                        dlg.setVisible(true);
                        if(dlg.CANCELLED) return;
        }

        Context.put("FRAME_WIDTH", new Integer(this.getWidth()));
        Context.put("FRAME_HEIGHT", new Integer(this.getHeight()));
        Context.put("FRAME_XPOS", new Integer(this.getLocation().x));
        Context.put("FRAME_YPOS", new Integer(this.getLocation().y));
        exitNotify();
        System.exit(0);
    }

    /*Minimize the window*/
    public void doMinimize() {
    	App.getFrame().setState(Frame.ICONIFIED);
    }

    //Help | About action performed
    public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
         AppFrame_AboutBox dlg = new AppFrame_AboutBox(this);
         Dimension dlgSize = dlg.getSize();
         Dimension frmSize = getSize();
         Point loc = getLocation();
         dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
         dlg.setModal(true);
         dlg.setVisible(true);
    }

    /*Proccesses windows events*/
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            //if (Configuration.get("ON_CLOSE").equals("exit"))
                doExit();
            //else
               // doMinimize();
        }
        else if (e.getID() == WindowEvent.WINDOW_ICONIFIED) {
            doMinimize();
        }
        else
            super.processWindowEvent(e);
    }

    public static void addExitListener(ActionListener al) {
        exitListeners.add(al);
    }

    public static Collection getExitListeners() {
        return exitListeners;
    }

    private static void exitNotify() {
        for (int i = 0; i < exitListeners.size(); i++)
            ((ActionListener) exitListeners.get(i)).actionPerformed(null);
    }

    public void setEnabledEditorMenus(boolean enabled) {
        this.jMenuEdit.setEnabled(enabled);
        this.jMenuFormat.setEnabled(enabled);
        this.jMenuInsert.setEnabled(enabled);
        this.jMenuFileNewNote.setEnabled(enabled);
        this.jMenuFileExportNote.setEnabled(enabled);
    }

    public void doPrjPack() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.saveInLabelText", Local
                .getString("Save in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                "Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                "Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local
                .getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                .getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                "File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                "Files of Type:"));
        UIManager.put("FileChooser.saveButtonText", Local.getString("Save"));
        UIManager.put("FileChooser.saveButtonToolTipText", Local.getString(
                "Save selected file"));
        UIManager
                .put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                "Cancel"));

        JFileChooser chooser = new JFileChooser();
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Pack project"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.RTF));
        chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.ZIP));
        // fixes XP style look cosmetical problems JVM 1.4.2 and 1.4.2_01
        chooser.setPreferredSize(new Dimension(550, 375));

        //Added to fix the problem with packing a file then deleting that file.
        //(jcscoobyrs) 17-Nov-2003 at 14:57:06 PM
        //---------------------------------------------------------------------
        File lastSel = null;

        try {
            lastSel = (java.io.File) Context.get("LAST_SELECTED_PACK_FILE");
        }
        catch (ClassCastException cce) {
            lastSel = new File(System.getProperty("user.dir") + File.separator);
        }
        //---------------------------------------------------------------------

        if (lastSel != null)
            chooser.setCurrentDirectory(lastSel);
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        Context.put("LAST_SELECTED_PACK_FILE", chooser.getSelectedFile());
        java.io.File f = chooser.getSelectedFile();
        ProjectPackager.pack(CurrentProject.get(), f);
    }

    public void doPrjUnPack() {
        // Fix until Sun's JVM supports more locales...
        UIManager.put("FileChooser.lookInLabelText", Local
                .getString("Look in:"));
        UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                "Up One Level"));
        UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                "Create New Folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", Local
                .getString("List"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                .getString("Details"));
        UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                "File Name:"));
        UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                "Files of Type:"));
        UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
        UIManager.put("FileChooser.openButtonToolTipText", Local.getString(
                "Open selected file"));
        UIManager
                .put("FileChooser.cancelButtonText", Local.getString("Cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                "Cancel"));

        JFileChooser chooser = new JFileChooser();
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Unpack project"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.ZIP));
        //chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.RTF));
        // fixes XP style look cosmetical problems JVM 1.4.2 and 1.4.2_01
        chooser.setPreferredSize(new Dimension(550, 375));

        //Added to fix the problem with packing a file then deleting that file.
        //(jcscoobyrs) 17-Nov-2003 at 14:57:06 PM
        //---------------------------------------------------------------------
        File lastSel = null;

        try {
            lastSel = (java.io.File) Context.get("LAST_SELECTED_PACK_FILE");
        }
        catch (ClassCastException cce) {
            lastSel = new File(System.getProperty("user.dir") + File.separator);
        }
        //---------------------------------------------------------------------

        if (lastSel != null)
            chooser.setCurrentDirectory(lastSel);
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        Context.put("LAST_SELECTED_PACK_FILE", chooser.getSelectedFile());
        java.io.File f = chooser.getSelectedFile();
        ProjectPackager.unpack(f);
        projectsPanel.prjTablePanel.updateUI();
    }

    /**
    * Opens a preferences dialog
    */
    public void showPreferences() {
        PreferencesDialog dlg = new PreferencesDialog();
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

            protected void ppExport_actionPerformed(ActionEvent e) {
                // Fix until Sun's JVM supports more locales...
                UIManager.put(
                        "FileChooser.lookInLabelText",
                        Local.getString("Save in:"));
                UIManager.put(
                        "FileChooser.upFolderToolTipText",
                        Local.getString("Up One Level"));
                UIManager.put(
                        "FileChooser.newFolderToolTipText",
                        Local.getString("Create New Folder"));
                UIManager.put(
                        "FileChooser.listViewButtonToolTipText",
                        Local.getString("List"));
                UIManager.put(
                        "FileChooser.detailsViewButtonToolTipText",
                        Local.getString("Details"));
                UIManager.put(
                        "FileChooser.fileNameLabelText",
                        Local.getString("File Name:"));
                UIManager.put(
                        "FileChooser.filesOfTypeLabelText",
                        Local.getString("Files of Type:"));
                UIManager.put("FileChooser.saveButtonText", Local.getString("Save"));
                UIManager.put(
                        "FileChooser.saveButtonToolTipText",
                        Local.getString("Save selected file"));
                UIManager.put(
                        "FileChooser.cancelButtonText",
                        Local.getString("Cancel"));
                UIManager.put(
                        "FileChooser.cancelButtonToolTipText",
                        Local.getString("Cancel"));

                JFileChooser chooser = new JFileChooser();
                chooser.setFileHidingEnabled(false);
                chooser.setDialogTitle(Local.getString("Export notes"));
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.addChoosableFileFilter(
                        new AllFilesFilter(AllFilesFilter.XHTML));
                chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));

                String lastSel = (String) Context.get("LAST_SELECTED_EXPORT_FILE");
                if (lastSel != null)
                        chooser.setCurrentDirectory(new File(lastSel));

                ProjectExportDialog dlg =
                        new ProjectExportDialog(
                                App.getFrame(),
                                Local.getString("Export notes"),
                                chooser);
                String enc = (String) Context.get("EXPORT_FILE_ENCODING");
                if (enc != null)
                        dlg.encCB.setSelectedItem(enc);
                String spl = (String) Context.get("EXPORT_SPLIT_NOTES");
                if (spl != null)
                        dlg.splitChB.setSelected(spl.equalsIgnoreCase("true"));
                String ti = (String) Context.get("EXPORT_TITLES_AS_HEADERS");
                if (ti != null)
                        dlg.titlesAsHeadersChB.setSelected(ti.equalsIgnoreCase("true"));
                Dimension dlgSize = new Dimension(550, 500);
                dlg.setSize(dlgSize);
                Dimension frmSize = App.getFrame().getSize();
                Point loc = App.getFrame().getLocation();
                dlg.setLocation(
                        (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
                dlg.setVisible(true);
                if (dlg.CANCELLED)
                        return;

                        Context.put(
                                "LAST_SELECTED_EXPORT_FILE",
                                chooser.getSelectedFile().getPath());
                        Context.put("EXPORT_SPLIT_NOTES", new Boolean(dlg.splitChB.isSelected()).toString());
                        Context.put("EXPORT_TITLES_AS_HEADERS", new Boolean(dlg.titlesAsHeadersChB.isSelected()).toString());

                int ei = dlg.encCB.getSelectedIndex();
                enc = null;
                if (ei == 1)
                        enc = "UTF-8";
                boolean nument = (ei == 2);
                File f = chooser.getSelectedFile();
                boolean xhtml =
                        chooser.getFileFilter().getDescription().indexOf("XHTML") > -1;
                 CurrentProject.save();
                 ProjectExporter.export(CurrentProject.get(), chooser.getSelectedFile(), enc, xhtml,
                                 dlg.splitChB.isSelected(), true, nument, dlg.titlesAsHeadersChB.isSelected(), false);
                }

            protected void ppImport_actionPerformed(ActionEvent e) {

            UIManager.put("FileChooser.lookInLabelText", Local
                    .getString("Look in:"));
            UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                    "Up One Level"));
            UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                    "Create New Folder"));
            UIManager.put("FileChooser.listViewButtonToolTipText", Local
                    .getString("List"));
            UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                    .getString("Details"));
            UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                    "File Name:"));
            UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                    "Files of Type:"));
            UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
            UIManager.put("FileChooser.openButtonToolTipText", Local.getString(
                    "Open selected file"));
            UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
            UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                    "Cancel"));

            JFileChooser chooser = new JFileChooser();
            chooser.setFileHidingEnabled(false);
            chooser.setDialogTitle(Local.getString("Import notes"));
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));
            chooser.setPreferredSize(new Dimension(550, 375));

            File lastSel = null;

            try {
                lastSel = (java.io.File) Context.get("LAST_SELECTED_NOTE_FILE");
            }
            catch (ClassCastException cce) {
                lastSel = new File(System.getProperty("user.dir") + File.separator);
            }
            //---------------------------------------------------------------------

            if (lastSel != null)
                chooser.setCurrentDirectory(lastSel);
            if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
                return;
            Context.put("LAST_SELECTED_NOTE_FILE", chooser.getSelectedFile());
            java.io.File f = chooser.getSelectedFile();
            HashMap<String,String> notesName = new HashMap<String,String>();
                HashMap<String,String> notesContent = new HashMap<String,String>();
            Builder parser = new Builder();
            String id="", name="", content = "";
            try{
                    Document document = parser.build(f);
                    Element body = document.getRootElement().getFirstChildElement("body");
                    Element names = body.getFirstChildElement("div").getFirstChildElement("ul");
                    Elements namelist = names.getChildElements("li");
                    Element item;

                    for(int i = 0;i<namelist.size();i++){
                            item = namelist.get(i);
                            id = item.getFirstChildElement("a").getAttributeValue("href").replace("\"","").replace("#","");
                            name = item.getValue();
                            notesName.put(id,name);
                    }
                    System.out.println("id: "+id+" name: "+name);

                    Elements contlist = body.getChildElements("a");
                    for(int i = 0;i<(contlist.size()-1);i++){
                            item = contlist.get(i);
                            id = item.getAttributeValue("name").replace("\"","");
                            content = item.getFirstChildElement("div").getValue();
                            notesContent.put(id,content);
                    }

                    JEditorPane p = new JEditorPane();
                    p.setContentType("text/html");
                    for (Map.Entry<String,String> entry : notesName.entrySet()){
                            id = entry.getKey();
                            name = entry.getValue().substring(11);
                            content = notesContent.get(id);
                            p.setText(content);
                            HTMLDocument doc = (HTMLDocument)p.getDocument();
                            Note note = CurrentProject.getNoteList().createNoteForDate(CurrentDate.get());
                    note.setTitle(name);
                            note.setId(Util.generateId());
                    CurrentStorage.get().storeNote(note, doc);
                    }
                    workPanel.dailyItemsPanel.notesControlPane.refresh();

            }catch(Exception exc){
                    exc.printStackTrace();
            }
        }
            protected void p1Import_actionPerformed(ActionEvent e) {

            UIManager.put("FileChooser.lookInLabelText", Local
                    .getString("Look in:"));
            UIManager.put("FileChooser.upFolderToolTipText", Local.getString(
                    "Up One Level"));
            UIManager.put("FileChooser.newFolderToolTipText", Local.getString(
                    "Create New Folder"));
            UIManager.put("FileChooser.listViewButtonToolTipText", Local
                    .getString("List"));
            UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
                    .getString("Details"));
            UIManager.put("FileChooser.fileNameLabelText", Local.getString(
                    "File Name:"));
            UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString(
                    "Files of Type:"));
            UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
            UIManager.put("FileChooser.openButtonToolTipText", Local.getString(
                    "Open selected file"));
            UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
            UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString(
                    "Cancel"));

            JFileChooser chooser = new JFileChooser();
            chooser.setFileHidingEnabled(false);

            chooser.setDialogTitle(Local.getString("Import notes"));
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));
            chooser.setPreferredSize(new Dimension(550, 375));

            File lastSel = null;

            try {
                lastSel = (java.io.File) Context.get("LAST_SELECTED_NOTE_FILE");
            }
            catch (ClassCastException cce) {
                lastSel = new File(System.getProperty("user.dir") + File.separator);
            }
            //---------------------------------------------------------------------

            if (lastSel != null)
                chooser.setCurrentDirectory(lastSel);
            if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
                return;
            Context.put("LAST_SELECTED_NOTE_FILE", chooser.getSelectedFile());
            java.io.File f = chooser.getSelectedFile();
            HashMap<String,String> notesName = new HashMap<String,String>();
            HashMap<String,String> notesContent = new HashMap<String,String>();
            Builder parser = new Builder();
            String id="", name="", content = "";
            try{
                    Document document = parser.build(f);
                    content = document.getRootElement().getFirstChildElement("body").getValue();
                    content = content.substring(content.indexOf("\n", content.indexOf("-")));
                    content = content.replace("<p>","").replace("</p>","\n");
                    name = f.getName().substring(0,f.getName().lastIndexOf("."));
                    Element item;
                    id=Util.generateId();
                    System.out.println(id+" "+name+" "+content);
                    notesName.put(id, name);
                    notesContent.put(id, content);
                    JEditorPane p = new JEditorPane();
                    p.setContentType("text/html");

                    for (Map.Entry<String,String> entry : notesName.entrySet()){
                            id = entry.getKey();
                            System.out.println(id+" "+name+" "+content);
                            p.setText(content);
                            HTMLDocument doc = (HTMLDocument)p.getDocument();
                            Note note = CurrentProject.getNoteList().createNoteForDate(CurrentDate.get());
                    note.setTitle(name);
                            note.setId(Util.generateId());
                    CurrentStorage.get().storeNote(note, doc);
                    }
                    workPanel.dailyItemsPanel.notesControlPane.refresh();

            }catch(Exception exc){
                    exc.printStackTrace();
            }
        }

}
