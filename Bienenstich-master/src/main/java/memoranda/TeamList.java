/**
 * TeamList.java
 * Interface for implementing a list of Teams
 */
package main.java.memoranda;

import java.util.Collection;

public interface TeamList {
    public Project getProject();
    public Team getTeam(String id);
    public Team createTeam(String teamName);
    public boolean removeTeam(String id);
    public Collection getAllRootTeams();

    public nu.xom.Document getXMLContent();
}