/**
 * Storage.java
 * Created on 12.02.2003, 0:58:42 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.util;

import main.java.memoranda.Note;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
import main.java.memoranda.TaskboardList;
import main.java.memoranda.SprintList;
import main.java.memoranda.BacklogList;
import main.java.memoranda.TeamList;
/**
 *
 */
/*$Id: Storage.java,v 1.4 2004/01/30 12:17:42 alexeya Exp $*/
public interface Storage {

    TeamList openTeamList(Project prj);
    void storeTeamList(TeamList tl, Project prj);

    TaskList openTaskList(Project prj);
    void storeTaskList(TaskList tl, Project prj);
    
    TaskboardList openTaskboardList(Project prj);
    void storeTaskboardList(TaskboardList tbl, Project prj);

    NoteList openNoteList(Project prj);
    void storeNoteList(NoteList nl, Project prj);

    void storeNote(Note note, javax.swing.text.Document doc);
    javax.swing.text.Document openNote(Note note);
    void removeNote(Note note);

    String getNoteURL(Note note);

    void openProjectManager();
    void storeProjectManager();

    void openEventsManager();
    void storeEventsManager();

    //void openSprintsManager();
    //void storeSprintsManager();

    void openMimeTypesList();
    void storeMimeTypesList();

    void createProjectStorage(Project prj);
    void removeProjectStorage(Project prj);

    ResourcesList openResourcesList(Project prj);
    void storeResourcesList(ResourcesList rl, Project prj);

    SprintList openSprintsList(Project prj);
    void storeSprintsList(SprintList sprl, Project prj);
	
	BacklogList openBacklogList(Project prj);
    void storeBacklogList(BacklogList back1, Project prj);

    void restoreContext();
    void storeContext();

}
