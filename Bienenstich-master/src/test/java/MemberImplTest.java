package test.java;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import main.java.memoranda.Team;
import main.java.memoranda.TeamImpl;
import main.java.memoranda.Member;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 * Unit tests for Member class
 */
public class MemberImplTest {
    Team testTeam;
    Member member;

    @org.junit.Before
    public void setUp() throws Exception {
        testTeam = null;
        member = null;
    }
    @org.junit.After
    public void tearDown() throws Exception {
        testTeam = null;
        member = null;
    }

    @Test
    public void testXmlConstructor() {
        Element element = new Element("member");
        element.addAttribute(new Attribute("name", "Dave"));
        element.addAttribute(new Attribute("email","dave@dave.com"));
        element.addAttribute(new Attribute("id", "42"));
        testTeam = new TeamImpl("The Beatles", Integer.toString(100));
        member = new Member(element, testTeam);
        assertEquals("Dave", member.getName());
        assertEquals("Dave", member.getContent().getAttribute("name").getValue());
        assertEquals("dave@dave.com", member.getEmail());
        assertEquals("dave@dave.com", member.getContent().getAttribute("email").getValue());
        assertEquals(Integer.toString(42), member.getId());
        assertEquals(42, Integer.parseInt(member.getContent().getAttribute("id").getValue()));
        assertEquals("The Beatles", member.getTeam().getName());
    }

    @Test
    public void testConstructor() {
        member = new Member("Steve", "steve@steve.com", Integer.toString(22));
        assertEquals("Steve", member.getName());
        assertEquals("Steve", member.getContent().getAttribute("name").getValue());
        assertEquals("steve@steve.com", member.getEmail());
        assertEquals("steve@steve.com", member.getContent().getAttribute("email").getValue());
        assertEquals(Integer.toString(22), member.getId());
        assertEquals(22, Integer.parseInt(member.getContent().getAttribute("id").getValue()));
    }

    @Test
    public void testGettersSetters() {
        testTeam = new TeamImpl("Bienenstich", Integer.toString(1));
        member = new Member("Steve", "steve@steve.com", Integer.toString(22));
        member.setName("Dave");
        member.setEmail("dave@dave.com");
        member.setId(Integer.toString(42));
        member.setTeam(testTeam);
        assertEquals("Dave", member.getContent().getAttribute("name").getValue());
        assertEquals("Dave", member.getName());
        assertEquals("dave@dave.com", member.getContent().getAttribute("email").getValue());
        assertEquals("dave@dave.com", member.getEmail());
        assertEquals(42, Integer.parseInt(member.getContent().getAttribute("id").getValue()));
        assertEquals(Integer.toString(42), member.getId());
        assertEquals("Bienenstich", member.getTeam().getName());
    }
}