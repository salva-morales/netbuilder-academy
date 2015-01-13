package uk.co.netbuilder.academy.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.jcr.resource.JcrResourceUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import uk.co.netbuilder.academy.models.people.GC;
import uk.co.netbuilder.academy.utils.Utils;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JcrResourceUtil.class})
public class GCServiceImplTest {

	/**
	 * Mocked pages
	 */
	@Mock
	Page gcFinderPage, gc1Page, gc2Page, gc3Page;
	
	/**
	 * Mocked properties
	 */
	@Mock
	ValueMap propertiesGC1, propertiesGC2, propertiesGC3;
	
	/**
	 * Mocked iterator
	 */
	@Mock
	Iterator<Page> allGCsPages;
	
	/**
	 * Mocked Repository
	 */
	@Mock
	SlingRepository repository;
	
	/**
	 * Mocked Session
	 */
	@Mock
	Session session;
	
	@Mock
	JcrResourceUtil jcrResourceUtil;
	
	
	String[] skillsGC1 = new String[] {"AEM" , "java" , "CQ"};
	
	String[] skillsGC2 = new String[] {"HTML", "CSS", "JavaScript"};
	
	String[] skillsGC3 = new String[] {"none"};
	/**
	 * Class to test
	 */
	private GCServiceImpl gcServiceImpl;

	@Before
	public void setUp () throws RepositoryException {
		gcServiceImpl = new GCServiceImpl();

		// Mocking two GCs
		when(gcFinderPage.listChildren()).thenReturn(allGCsPages);
		when(allGCsPages.hasNext()).thenReturn(true,true,true,false);
		when(allGCsPages.next()).thenReturn(gc1Page, gc2Page, gc3Page);

		// Mocking data for GC1
		when(gc1Page.getProperties()).thenReturn(propertiesGC1);
		when(propertiesGC1.containsKey(anyString())).thenReturn(true);
		when(propertiesGC1.get(Utils.NAME_PROPERTY)).thenReturn("Salvador");
		when(propertiesGC1.get(Utils.AGE_PROPERTY)).thenReturn("28");
		when(propertiesGC1.get(Utils.NATIONALITY_PROPERTY)).thenReturn("Spanish");
		when(propertiesGC1.get(Utils.AVAILABILITY_PROPERTY)).thenReturn("true");
		when(propertiesGC1.get(Utils.SKILLS_PROPERTY, String[].class)).thenReturn(skillsGC1);

		// Mocking data for GC2
		when(gc2Page.getProperties()).thenReturn(propertiesGC2);
		when(propertiesGC2.containsKey(anyString())).thenReturn(true);
		when(propertiesGC2.get(Utils.NAME_PROPERTY)).thenReturn("Antonio");
		when(propertiesGC2.get(Utils.AGE_PROPERTY)).thenReturn("35");
		when(propertiesGC2.get(Utils.NATIONALITY_PROPERTY)).thenReturn("British");
		when(propertiesGC2.get(Utils.AVAILABILITY_PROPERTY)).thenReturn("true");
		when(propertiesGC2.get(Utils.SKILLS_PROPERTY, String[].class)).thenReturn(skillsGC2);
		
		// Mocking data for GC2
		when(gc3Page.getProperties()).thenReturn(propertiesGC3);
		when(propertiesGC3.containsKey(anyString())).thenReturn(true);
		when(propertiesGC3.get(Utils.NAME_PROPERTY)).thenReturn("Lazy");
		when(propertiesGC3.get(Utils.AGE_PROPERTY)).thenReturn("1");
		when(propertiesGC3.get(Utils.NATIONALITY_PROPERTY)).thenReturn("Moon");
		when(propertiesGC3.get(Utils.AVAILABILITY_PROPERTY)).thenReturn("false");
		when(propertiesGC3.get(Utils.SKILLS_PROPERTY, String[].class)).thenReturn(skillsGC3);
		
		when(repository.loginAdministrative(null)).thenReturn(session);

		//Whitebox uses reflection to inspect the gcServiceImpl object and initialize a memeber of it.
		Whitebox.setInternalState(gcServiceImpl, "repository", repository);

	}

	@Test 
	public void listAllGCTest(){
		List<GC> allGC = gcServiceImpl.listAllGC(gcFinderPage);
		verify(gcFinderPage).getPath();
		verify(gcFinderPage, times(3)).getName();
		assertEquals(allGC.size(), 3);
		assertTrue(allGC.get(0).getName().equals("Salvador"));
		assertTrue(allGC.get(1).getName().equals("Antonio"));
		assertTrue(allGC.get(2).getName().equals("Lazy"));
	}
	
	@Test
	public void listAllGCAvailableTest() {
		List<GC> allGCAvailable = gcServiceImpl.listAllAvailableGC(gcFinderPage);
		assertEquals(allGCAvailable.size(), 2);
		assertTrue(allGCAvailable.get(0).getName().equals("Salvador"));
		assertTrue(allGCAvailable.get(1).getName().equals("Antonio"));
		
	}	

	@Test 
	public void listAllGCWithSkillWhenMatchesTest() {
		String[] skillsToTest = {"skill1", "java", "skill3"};
		List<GC> allGCAvailable = gcServiceImpl.listAllAvailableGC(gcFinderPage);
		List<GC> allGCwithSkills = gcServiceImpl.listAllGCWithSkill(allGCAvailable, skillsToTest);
		assertEquals(allGCwithSkills.size(), 1);
		assertTrue(allGCwithSkills.get(0).getName().equals("Salvador"));
	}
	
	@Test
	public void listAllGCWithSkillWhenMatchesAndGcNoAvailableTest() {
		String[] skillsToTest = {"none"};
		List<GC> allGCAvailable = gcServiceImpl.listAllAvailableGC(gcFinderPage);
		List<GC> allGCwithSkills = gcServiceImpl.listAllGCWithSkill(allGCAvailable, skillsToTest);
		assertEquals(allGCwithSkills.size(), 0);
	}

	@Test
	public void createGCTest() throws RepositoryException {
		Map<String, String[]> properties = new HashMap<String, String[]>();
		String[] val1 = new String[] {"val1"};
		String[] val2 = new String[] {"val2"};
		String[] nameProperty = new String[] {"blah"};

		properties.put("prop1",val1);
		properties.put(Utils.SKILLS_PROPERTY,val2);
		properties.put(Utils.NAME_PROPERTY, nameProperty);
				
		String fullGCPath = Utils.GCFINDERPAGE_PATH + "/salvador";

		Node gcPageJcrContent = mock(Node.class);
		PowerMockito.mockStatic(JcrResourceUtil.class);
		when(JcrResourceUtil.createPath(fullGCPath + "/" + JcrConstants.JCR_CONTENT, null, "cq:PageContent", session, true)).thenReturn(gcPageJcrContent);

		gcServiceImpl.createGC(properties, fullGCPath);
		
		PowerMockito.verifyStatic();
		JcrResourceUtil.createPath(fullGCPath, null, NameConstants.NT_PAGE, session, true);
		JcrResourceUtil.createPath(fullGCPath + "/" + JcrConstants.JCR_CONTENT, null, NameConstants.NT_PAGE, session, true);
		
		verify(gcPageJcrContent, times(6)).setProperty(Mockito.anyString(),Mockito.anyString());
		verify(gcPageJcrContent).setProperty(Utils.SKILLS_PROPERTY,val2, PropertyType.STRING);
		
		verify(session).save();
		verify(session).logout();
		
	}
	
}
