/**
 * Team.java.
 * Interface for implementing a Team.
 */
package main.java.memoranda;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Interface for Team implementation.
 */
public interface Team {

//    public boolean addMember(String name, String email, String id);
    public boolean addMember(String name, String email);
    public Member getMember(String id);
    public boolean removeMember(String memberId);
    public void setId(String newId);
    public String getId();
    public String getName();
    public void setName(String teamName);
    public Collection getMembers();
    public boolean hasMembers();
    public int compareTo(Object o);

    public nu.xom.Element getContent();
}