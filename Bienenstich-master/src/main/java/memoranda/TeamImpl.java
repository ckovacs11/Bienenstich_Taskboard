/**
 * TeamImpl.java
 * Implementation of Team.java
 */
package main.java.memoranda;

import java.util.Collection;
import java.util.Vector;
import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 * Implemtation of a Team class.
 */
public class TeamImpl implements Team {
    private int memberIdCount = 0;
    private String name;
    private String id;
    private Collection members;

    private Element _element = null;
    private TeamList _tl = null;

    /**
     * Constructor for XML.
     */
    public TeamImpl(Element teamElement, TeamList tl) {
        _element = teamElement;
        _tl = tl;
        this.name = _element.getAttribute("name").getValue();
//        this.id = Integer.parseInt(_element.getAttribute("id").getValue());
        this.id = _element.getAttribute("id").getValue();
        this.getMembers();
    }

    public TeamImpl(String name, String id) {
        this.name = name;
        this.id = id;
        _element = new Element("team");
        setAttr(_element, "name", name);
//        setAttr(_element, "id", Integer.toString(id));
        setAttr(_element, "id", id);
        members = new ArrayList<>();
    }

    /**
     * Adds a member to this team.
     * @param name The member's name.
     * @param email The member's email.
     * @param id The member's id.
     * @return True if member was added successfully.
     */
    public boolean addMember(String name, String email) {
        memberIdCount++;
        Element member = new Element("member");
        setAttr(member, "name", name);
        setAttr(member, "email", email);
        setAttr(member, "id", Integer.toString(memberIdCount));
        _element.appendChild(member);
        getMembers();
        return true;
    }

    /**
     * Gets a member based on their id.
     * @param id The member's id.
     * @return A member object if found, null if no member found.
     * @throws IndexOutOfBoundsException.
     */
    public Member getMember(String id) throws IndexOutOfBoundsException {
        Elements members = _element.getChildElements("member");
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getAttribute("id").getValue().equals(id))
                return new Member(members.get(i), this);
        }
        return null;
    }

    /**
     * Removes a member from the team.
     * @param memberId The member's id
     * @return True if succeeded, else false
     */
    public boolean removeMember(String memberId) {
        Elements members = _element.getChildElements("member");
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getAttribute("id").getValue().equals(memberId)) {
                _element.removeChild(i);
                getMembers();
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the Team's id.
     * @return The team's id number.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the Team's id.
     * @return The team's id number.
     */
    public void setId(String id) {
        this.id = id;
        setAttr(_element, "id", id);
    }

    /**
     * Gets the team's name.
     * @return The team's name.
     */
    public String getName() {
        Attribute attr = _element.getAttribute("name");
        return attr.getValue();
    }

    /**
     * Sets the team's name
     * @param name The team's new name.
     */
    public void setName(String name) {
        setAttr(_element, "name", name);
        this.name = name;
    }

    /**
     * Gets the xml element.
     * @return The element.
     */
    public Element getContent() {
        return _element;
    }

    /**
     * Compare's this object to another (for sorting).
     * @param o The other object.
     * @return An integer representing the comparison.
     */
    public int compareTo(Object o) {
        Team team = (Team) o;
//        if(getName() > team.getName())
//            return 1;
//        else if(getName() < team.getName())
//            return -1;
//        else
//            return 0;
        return getName().compareTo(team.getName());
    }

    /**
     * Checks to see if this object is equal to another.
     * @param o The other object.
     * @return True or false.
     */
    public boolean equals(Object o) {
        return ((o instanceof Team) && (((Team)o).getId() == (this.getId())));
    }

    /**
     * Gets all the team members
     * @return A collection of member objects
     */
    public Collection getMembers() {
        Elements members = _element.getChildElements("member");
        return convertToMemberObjects(members);
    }

    /**
     * Converts xml collection of members to Member objects
     * @param members The member elements
     * @return A collection of Member objects
     */
    private Collection convertToMemberObjects(Elements members) {
        Vector v = new Vector();
        int highId = 0;
        for (int i = 0; i < members.size(); i++) {
            Member m = new Member(members.get(i), this);
            if (Integer.parseInt(m.getId()) > highId) {
                highId = Integer.parseInt(m.getId());
            }
            v.add(m);
        }
        memberIdCount = highId;
        this.members = v;
        return v;
    }

    /**
     * Checks if the team has any members.
     * @return A boolean - true is team has any members, else false.
     */
    public boolean hasMembers() {
        if(_element.getChildElements("member").size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Sets the attribute for an element.
     * @param e The element.
     * @param a A string representing the attribute.
     * @param value A string representing the value.
     */
    private void setAttr(Element e, String a, String value) {
        Attribute attr = e.getAttribute(a);
        if (attr == null)
            e.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }
}