package uk.co.netbuilder.academy.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.co.netbuilder.academy.models.people.GC;

import com.day.cq.wcm.api.Page;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GCServiceImpl.class})
public class GCServiceImplTest {

	private final String nameProperty ="name";
	private final String ageProperty = "age";
	private final String nationalityProperty = "nationality";
	private final String skillsProperty = "skills";
	private final String availabilityProperty = "availability";

	/**
	 * Mocked pages
	 */
	@Mock
	Page gcFinderPage, gc1Page, gc2Page;
	
	/**
	 * Mocked properties
	 */
	@Mock
	ValueMap propertiesGC1, propertiesGC2;

	/**
	 * Mocked skills
	 */
	@Mock
	List<String> skillsGC1, skillsGC2;

	/**
	 * Mocked iterator
	 */
	@Mock
	Iterator<Page> allGCsPages;
	
	/**
	 * Spied classes
	 */
	@Mock
	GC gc1, gc2;
	
	/**
	 * Class to test
	 */
	private GCServiceImpl gcServiceImpl;

	@Before
	public void setUp () {
		gcServiceImpl = new GCServiceImpl();

		// Mocking two GCs
		when(gcFinderPage.listChildren()).thenReturn(allGCsPages);
		when(allGCsPages.hasNext()).thenReturn(true,true,false);
		when(allGCsPages.next()).thenReturn(gc1Page, gc2Page);

		// Mocking data for GC1
		when(gc1Page.getProperties()).thenReturn(propertiesGC1);
		when(propertiesGC1.containsKey(anyString())).thenReturn(true);
		when(propertiesGC1.get(nameProperty)).thenReturn("Salvador");
		when(propertiesGC1.get(ageProperty)).thenReturn("28");
		when(propertiesGC1.get(nationalityProperty)).thenReturn("Spanish");
		when(propertiesGC1.get(availabilityProperty)).thenReturn(true);
		when(propertiesGC1.get(skillsProperty)).thenReturn("java");

		// Mocking data for GC2
		when(gc2Page.getProperties()).thenReturn(propertiesGC2);
		when(propertiesGC2.containsKey(nameProperty)).thenReturn(true);
		when(propertiesGC2.containsKey(ageProperty)).thenReturn(false);
		when(propertiesGC2.containsKey(nationalityProperty)).thenReturn(false);
		when(propertiesGC2.containsKey(availabilityProperty)).thenReturn(true);
		when(propertiesGC2.get(nameProperty)).thenReturn("Antonio");
		when(propertiesGC2.get(availabilityProperty)).thenReturn(false);
		when(propertiesGC2.get(skillsProperty)).thenReturn(skillsGC2);
		when(skillsGC2.contains("java")).thenReturn(false);
	}

	@Test 
	public void listAllGCTest(){
		List<GC> allGC = gcServiceImpl.listAllGC(gcFinderPage);
		verify(gcFinderPage).getPath();
		verify(gcFinderPage, times(2)).getName();
		assertEquals(allGC.size(), 2);
		Iterator<GC> allGCIt = allGC.iterator();
		boolean fail = false;
		while (allGCIt.hasNext() && !fail){
			GC gc = allGCIt.next();
			if (! (gc.getName().equals("Salvador") || gc.getName().equals("Antonio"))){
				fail = true;
			}
		}
		assertFalse(fail);
	}

//	@Test
//	public void listAllGCWithSkillTest() {
//		List<GC> allGCAvailable = gcServiceImpl.listAllGCWithSkill(gcFinder, "java");
//		assertEquals(allGCAvailable.size(), 1);
//		assertTrue(allGCAvailable.get(0).getName().equals("Salvador"));
//		
//	}
}
