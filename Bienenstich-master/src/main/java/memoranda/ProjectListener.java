package main.java.memoranda;

/*$Id: ProjectListener.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public interface ProjectListener {

  void projectChange(Project prj, NoteList nl, TaskList tl, TaskboardList tbl, ResourcesList rl, SprintList spr1, TeamList teal, BacklogList back1);

  void projectWasChanged();
}
