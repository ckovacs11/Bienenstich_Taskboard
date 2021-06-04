/**
 * TeamListImpl.java
 * Implementation of the list of teams
 */
package main.java.memoranda;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import main.java.memoranda.util.Util;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;

/**
 * Implementation of the Team List
 */
public class TeamListImpl implements TeamList {

    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
    private Hashtable elements = new Hashtable();

    /**
     * Constructor for an existing TeamList.
     * @param doc The document node
     * @param prj The project node
     */
    public TeamListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
        buildElements(_root);
    }

    /**
     * A constructor for a new TeamList.
     * @param prj A project xom node
     */
    public TeamListImpl(Project prj) {
        _root = new Element("teamlist");
        _doc = new Document(_root);
        _project = prj;
    }

    public Project getProject() {
        return _project;
    }

    /**
     * Builds a hash table of elements
     * @param parent The parent element
     */
    private void buildElements(Element parent) {
        Elements els = parent.getChildElements("team");
        for (int i = 0; i < els.size(); i++) {
            Element el = els.get(i);
            elements.put(el.getAttribute("id").getValue(), el);
//            buildElements(el); // shouldn't need to recusively check anymore
        }
    }

    /**
     * Gets a team object via id.
     * @param id The Team's id
     * @return A Team object
     */
    public Team getTeam(String id) {
        Util.debug("Getting a team object with id = " + id);
        return new TeamImpl(getTeamElement(id), this);
    }

    public Team createTeam(String name) {
        Element el = new Element("team");
        el.addAttribute(new Attribute("name", name));
        el.addAttribute(new Attribute("id", Integer.toString(elements.size())));
        _root.appendChild(el);
        elements.put(elements.size(), el);
        Util.debug("Created team with name " + name + " and id " + (elements.size() - 1));
        getAllRootTeams();
        buildElements(_root);
        return new TeamImpl(el, this);
    }

    // todo implement
    public boolean removeTeam(String teamId) {
        Elements teams = _root.getChildElements("team");
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getAttribute("id").getValue().equals(teamId)) {
                _root.removeChild(i);
                buildElements(_root);
                return true;
            }
        }
        return false;
    }

    /**
     * @see main.java.memoranda.TaskList#getXMLContent()
     */
    public Document getXMLContent() {
        return _doc;
    }

    /**
     *
     * @param id
     * @return
     */
    private Element getTeamElement(String id) {
        Element el = (Element)elements.get(id);
        if (el == null) {
            Util.debug("Team with id = " + id + " cannot be found in project " + _project.getTitle());
        }
        return el;
    }

    /**
     * Gets all the team child elements from the _root and returns a Collection of Objects.
     * @return A collection of Team objects
     */
    public Collection getAllRootTeams() {
        Elements teams = _root.getChildElements("team");
        return convertToTeamObjects(teams);
    }

    /**
     * Converts Elements to a vector of Team Objects.
     * @param teams Elements of "team"
     * @return A Collection of TeamImpl objects
     */
    private Collection convertToTeamObjects(Elements teams) {
        Vector v = new Vector();

        for (int i = 0; i < teams.size(); i++) {
            Team t = new TeamImpl(teams.get(i), this);
            v.add(t);
        }
        return v;
    }

    /**
     * Checks to see if a Team has Team Members.
     * @param id The team's id
     * @return True if the Team has members, else false
     */
    public boolean hasMembers(String id) {
        Element team = getTeamElement(id);
        if (team == null) return false;
        if(team.getChildElements("member").size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
}