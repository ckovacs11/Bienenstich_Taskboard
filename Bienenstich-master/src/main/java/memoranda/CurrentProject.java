/**
 * CurrentProject.java
 * Created on 13.02.2003, 13:16:52 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *
 */
package main.java.memoranda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import main.java.memoranda.ui.AppFrame;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Storage;
import main.java.memoranda.SprintList;
import main.java.memoranda.SprintListImpl;
import main.java.memoranda.BacklogList;
import main.java.memoranda.BacklogListImpl;

/**
 *
 */
/*$Id: CurrentProject.java,v 1.6 2005/12/01 08:12:26 alexeya Exp $*/
public class CurrentProject {

    private static Project _project = null;
    private static TaskboardList _taskboardlist = null;
    private static TaskList _tasklist = null;
    private static NoteList _notelist = null;
    private static SprintList _sprints = null;
	private static BacklogList _backlog = null;
    private static ResourcesList _resources = null;
    private static TeamList _teamlist = null;
    private static Vector projectListeners = new Vector();


    static {
        String prjId = (String)Context.get("LAST_OPENED_PROJECT_ID");
        if (prjId == null) {
            prjId = "__default";
            Context.put("LAST_OPENED_PROJECT_ID", prjId);
        }
        //ProjectManager.init();
        _project = ProjectManager.getProject(prjId);

		if (_project == null) {
			// alexeya: Fixed bug with NullPointer when LAST_OPENED_PROJECT_ID
			// references to missing project
			_project = ProjectManager.getProject("__default");
			if (_project == null)
				_project = (Project)ProjectManager.getActiveProjects().get(0);
            Context.put("LAST_OPENED_PROJECT_ID", _project.getID());

		}

        _tasklist = CurrentStorage.get().openTaskList(_project);
        _taskboardlist = CurrentStorage.get().openTaskboardList(_project);
        _notelist = CurrentStorage.get().openNoteList(_project);
        _resources = CurrentStorage.get().openResourcesList(_project);
        _sprints = CurrentStorage.get().openSprintsList(_project);
        _backlog = CurrentStorage.get().openBacklogList(_project);
        _teamlist = CurrentStorage.get().openTeamList(_project);
        AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }


    public static Project get() {
        return _project;
    }

    public static TaskList getTaskList() {
            return _tasklist;
    }
    
    public static TaskboardList getTaskboardList() {
        return _taskboardlist;
    }

    public static NoteList getNoteList() {
            return _notelist;
    }

    public static ResourcesList getResourcesList() {
            return _resources;
    }


    public static SprintList getSprintsList(){ return _sprints; }
	public static BacklogList getBacklogList(){ return _backlog; }

    public static TeamList getTeamList() {
        return _teamlist;
    }

    public static void set(Project project) {
        if (project.getID().equals(_project.getID())) return;
        TaskList newtasklist = CurrentStorage.get().openTaskList(project);
        TaskboardList newtaskboardlist = CurrentStorage.get().openTaskboardList(project);
        NoteList newnotelist = CurrentStorage.get().openNoteList(project);
        ResourcesList newresources = CurrentStorage.get().openResourcesList(project);
        SprintList newsprints = CurrentStorage.get().openSprintsList(project);
		BacklogList newbacklog = CurrentStorage.get().openBacklogList(project);
//        notifyListenersBefore(project, newnotelist, newtasklist, newresources,newsprints);
        TeamList newteamlist = CurrentStorage.get().openTeamList(project);
        notifyListenersBefore(project, newnotelist, newtasklist, newtaskboardlist, newresources, newsprints, newteamlist, newbacklog);
        _project = project;
        _tasklist = newtasklist;
        _taskboardlist = newtaskboardlist;
        _notelist = newnotelist;
        _resources = newresources;
        _sprints = newsprints;
        _backlog = newbacklog;
        _teamlist = newteamlist;
        notifyListenersAfter();
        Context.put("LAST_OPENED_PROJECT_ID", project.getID());
    }

    public static void addProjectListener(ProjectListener pl) {
        projectListeners.add(pl);
    }

    public static Collection getChangeListeners() {
        return projectListeners;
    }


    private static void notifyListenersBefore(Project project, NoteList nl, TaskList tl, TaskboardList tbl, ResourcesList rl, SprintList spr1, TeamList teal, BacklogList back1) {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((ProjectListener)projectListeners.get(i)).projectChange(project, nl, tl, tbl, rl, spr1, teal, back1);

            /*DEBUGSystem.out.println(projectListeners.get(i));*/
        }
    }

    private static void notifyListenersAfter() {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((ProjectListener)projectListeners.get(i)).projectWasChanged();
        }
    }

    public static void save() {
        Storage storage = CurrentStorage.get();

        storage.storeNoteList(_notelist, _project);
        storage.storeTaskList(_tasklist, _project);
        storage.storeTaskboardList(_taskboardlist, _project);
        storage.storeResourcesList(_resources, _project);
        storage.storeSprintsList(_sprints, _project);
		storage.storeBacklogList(_backlog, _project);
        storage.storeTeamList(_teamlist, _project);
        storage.storeProjectManager();
    }

    public static void free() {
        _project = null;
        _tasklist = null;
        _taskboardlist = null;
        _notelist = null;
        _resources = null;
        _sprints = null;
        _backlog = null;
        _teamlist = null;
    }
}
