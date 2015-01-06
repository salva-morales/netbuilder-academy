package uk.co.netbuilder.academy.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.netbuilder.academy.models.people.GC;
import uk.co.netbuilder.academy.services.GCService;

import com.day.cq.wcm.api.Page;

@Component(metatype = true, label = "Netbuilder Academy - GC Service", description = "Service for managing GCs")
@Service(GCService.class)
@Properties({ @Property(name = "service.pid", value = "uk.co.netbuilder.academy.services.impl.GCServiceImpl", propertyPrivate = false),
		@Property(name = "service.vendor", value = "NETBuilder", propertyPrivate = false) })
public class GCServiceImpl implements GCService {
	
	private static final Logger LOG = LoggerFactory.getLogger(GCServiceImpl.class);

	@Reference
	SlingRepository repository;

	ResourceResolver resourceResolver;

	Session session;

	public List<GC> listAllGC(Page gcFinderPage) {
		List<GC> allGC = new ArrayList<GC>();
		String dummyPath = gcFinderPage.getPath();
		Iterator<Page> allGCsPages = gcFinderPage.listChildren();
		while (allGCsPages.hasNext()){
			String dummyName = gcFinderPage.getName();
			Page gcPage = allGCsPages.next();
			GC gc = new GC(gcPage);
			allGC.add(gc);
		}
		return allGC;
	}
	
	
	public List<GC> listAllAvailableGC(Page gcFinderPage) {
		List<GC> allGC = new ArrayList<GC>();
		Iterator<Page> allGCsPagesIt = gcFinderPage.listChildren();
		if (allGCsPagesIt != null){
			while (allGCsPagesIt.hasNext()){
				Page gcPage = allGCsPagesIt.next();
				GC gc = new GC(gcPage);
				if (gc.getAvailability()) {
					allGC.add(gc);
				}
			}
		} else {
			LOG.error("iterator is null");
		}
		return allGC;
	}
	

	public List<GC> listAllGCWithSkill(List<GC> allAvailableGCs, String skill) {
		List<GC> allGCWithSkill = new ArrayList<GC>();
		Iterator<GC> allGCs = allAvailableGCs.iterator();
		while (allGCs.hasNext()) {
			GC gc = allGCs.next();
			if (gc.getSkills().contains(skill)) {
				allGCWithSkill.add(gc);
			}
		}
		LOG.error("GCSkillFinderServlet: the list of allGCWithSkill is size: " + allGCWithSkill.size());
		return allGCWithSkill;
	}
	
}
