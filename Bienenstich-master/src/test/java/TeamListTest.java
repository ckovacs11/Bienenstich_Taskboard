package test.java;

import java.util.Collection;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;

import main.java.memoranda.Project;
import main.java.memoranda.ProjectImpl;
import main.java.memoranda.TeamList;
import main.java.memoranda.TeamListImpl;
import main.java.memoranda.Team;
import main.java.memoranda.TeamImpl;
import main.java.memoranda.Member;

import nu.xom.Document;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 * Unit tests for Team and TeamImpl
 */
public class TeamListTest {
    TeamList teamList;

    @org.junit.Before
    public void setUp() throws Exception {
        teamList = null;
    }
    @org.junit.After
    public void tearDown() throws Exception {
        teamList = null;
    }

    @Test
    public void testConstructor() {
        Element el = new Element("TestProjectRoot");
        Project project = new ProjectImpl(el);
        teamList = new TeamListImpl(new Document(el), project);
        assertEquals(project, teamList.getProject());
    }

    @Test
    public void testCreateGetRemoveTeam() {
        Element el = new Element("TestProjectRoot");
        Project project = new ProjectImpl(el);
        teamList = new TeamListImpl(new Document(el), project);
    }
//    @Test
//    public void testConstructorXml() {
//        Element el = new Element("team");
//        Attribute at1 = new Attribute("name", "The Beatles");
//        Attribute at2 = new Attribute("id", Integer.toString(100));
//        el.addAttribute(at1);
//        el.addAttribute(at2);
//        team = new TeamImpl(el, null);
//        assertEquals("The Beatles", team.getName());
//        assertEquals("The Beatles", team.getContent().getAttribute("name").getValue());
//        assertEquals(100, team.getId());
//        assertEquals(100, Integer.parseInt(team.getContent().getAttribute("id").getValue()));
//    }
//
//    @Test
//    public void testAddRemoveMembers() {
//        team = new TeamImpl("The Beatles", 100);
//        assertFalse(team.hasMembers());
//        team.addMember("Steve", "steve@steve.com", 22);
//        assertEquals("Steve", team.getMember(22).getName());
//        assertTrue(team.hasMembers());
//        assertEquals(null, team.getMember(23));
//        team.removeMember(22);
//        assertEquals(null, team.getMember(22));
//        assertFalse(team.hasMembers());
//    }
//
//    @Test
//    public void testGettersSetters() {
//        team = new TeamImpl("The Beatles", 100);
//        assertEquals("The Beatles", team.getName());
//        assertEquals("The Beatles", team.getContent().getAttribute("name").getValue());
//        assertEquals(100, team.getId());
//        assertEquals(100, Integer.parseInt(team.getContent().getAttribute("id").getValue()));
//        team.setName("Megadeath");
//        team.setId(666);
//        assertEquals("Megadeath", team.getName());
//        assertEquals("Megadeath", team.getContent().getAttribute("name").getValue());
//        assertEquals(666, team.getId());
//        assertEquals(666, Integer.parseInt(team.getContent().getAttribute("id").getValue()));
//    }
//
//    @Test
//    public void testMembers() {
//        team = new TeamImpl("The Beatles", 100);
//        assertFalse(team.hasMembers());
//        team.addMember("Steve", "steve@steve.com", 22);
//        team.addMember("Bill", "bill@bill.com", 29);
//        team.addMember("Chad", "chad@chad.com", 31);
//        int[] idArr = {22, 29, 31};
//        assertTrue(team.hasMembers());
//        Collection members = team.getMembers();
//        int index = 0;
//        for (Object m : members) {
//            assertEquals(idArr[index], ((Member)m).getId());
//            index++;
//        }
//    }
//
//    @Test
//    public void testEqualsAndCompareTo() {
//        Team team1 = new TeamImpl("Team1", 1);
//        Team team2 = new TeamImpl("Team2", 2);
//        Team team3 = new TeamImpl("Team3", 1);
//        Team team4 = new TeamImpl("Team1", 4);
//        assertTrue(team1.equals(team3));
//        assertFalse(team1.equals(team2));
//        assertEquals(-1, team1.compareTo(team2));
//        assertEquals(0, team1.compareTo(team4));
//        assertEquals(1, team2.compareTo(team1));
//    }

}