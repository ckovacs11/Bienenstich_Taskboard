
package main.java.memoranda;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 * Class to represent a Team Member
 */
public class Member {
    private String name;
    private String email;
    private String id;

    private Element _element = null;
    private Team _team = null;

    /**
     * Constructor for nu.xom elements.
     * @param memberElement A member element
     * @param team The members team element
     */
    public Member(Element memberElement, Team team) {
        _element = memberElement;
        _team = team;
        this.name = _element.getAttribute("name").getValue();
        this.email = _element.getAttribute("email").getValue();
        this.id = _element.getAttribute("id").getValue();
    }

    /**
     * Constructor for creating a new, teamless element.
     * @param name Member's name
     * @param email Member's email
     * @param id Member's id
     */
    public Member(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.id = id;
        if (_element == null) {
            _element = new Element("member");
            setAttr(_element, "name", name);
            setAttr(_element, "email", email);
            setAttr(_element, "id", id);
        }
    }

    /**
     * Gets the member's name.
     * @return The member's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the member's name (also in _element).
     * @param newName The member's new name
     */
    public void setName(String newName) {
        this.name = newName;
        setAttr(_element, "name", newName);
    }

    /**
     * Gets the member's email.
     * @return The member's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the member's email.
     * @param newEmail The member's new email
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
        setAttr(_element, "email", newEmail);
    }

    /**
     * Gets the member's id.
     * @return The member's id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the member's id. Does not check for uniqueness at the moment.
     * @param newId The member's new id.
     */
    public void setId(String newId) {
        this.id = newId;
        setAttr(_element, "id", newId);
    }

    /**
     * Gets the xml element.
     * @return The element
     */
    public Element getContent() {
        return _element;
    }

    /**
     * Gets the Team xom element
     * @return The Team xom element
     */
    public Team getTeam() {
        return _team;
    }

    /**
     * Sets the team xom element
     * @param team The team xom element
     */
    public void setTeam(Team team) {
        this._team = team;
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