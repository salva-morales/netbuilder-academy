package uk.co.netbuilder.academy.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.PropertyType;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.apache.sling.jcr.resource.JcrResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.netbuilder.academy.models.people.GC;
import uk.co.netbuilder.academy.services.GCService;
import uk.co.netbuilder.academy.utils.Utils;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;

@Component(metatype = true, label = "Netbuilder Academy - GC Service", description = "Service for managing GCs")
@Service(GCService.class)
@Properties({ @Property(name = "service.pid", value = "uk.co.netbuilder.academy.services.impl.GCServiceImpl", propertyPrivate = false),
		@Property(name = "service.vendor", value = "NETBuilder", propertyPrivate = false) })

public class GCServiceImpl implements GCService {
	
	private static final Logger LOG = LoggerFactory.getLogger(GCServiceImpl.class);

	@Reference
	SlingRepository repository;

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
	
	
	final public List<GC> listAllAvailableGC(Page gcFinderPage) {
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
	

	public List<GC> listAllGCWithSkill(List<GC> allAvailableGCs, String[] skills) {
		List<GC> allGCWithSkill = new ArrayList<GC>();
		Iterator<GC> allGCs = allAvailableGCs.iterator();
		while (allGCs.hasNext()) {
			GC gc = allGCs.next();
			if (gc.hasSkills(skills)) {
				allGCWithSkill.add(gc);
			}
		}
		return allGCWithSkill;
	}


	public void createGC(Map<String, String[]> parameters, String fullGCPath) {
		Session session = null;
		try {
			session = repository.loginAdministrative(null);

			if (session.itemExists(fullGCPath)) {
				session.removeItem(fullGCPath);
			}
			createGCPage(parameters, fullGCPath, session);

			session.save();
		} catch (RepositoryException e) {
			LOG.error("Error accessing the repo");
		} finally {
			if (session != null) {
				session.logout();
				LOG.info("Session closed for saving");
			}
		}
	}
	
	private void createGCPage(Map<String, String[]> parameters, String fullGCPath, Session session) {
		try {
			JcrResourceUtil.createPath(fullGCPath, null, NameConstants.NT_PAGE, session, true);

			Node gcPageJcrContent = JcrResourceUtil.createPath(fullGCPath + "/" + JcrConstants.JCR_CONTENT, null, "cq:PageContent", session, true);

			gcPageJcrContent.setProperty(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, Utils.GC_PAGE_RENDERER_PATH);

			gcPageJcrContent.setProperty(NameConstants.NN_TEMPLATE, Utils.GC_TEMPLATE_PATH);

			gcPageJcrContent.setProperty(NameConstants.PN_DESIGN_PATH, Utils.NB_DESIGN_PATH);

			gcPageJcrContent.setProperty(JcrConstants.JCR_TITLE, parameters.get(Utils.NAME_PROPERTY)[0]);

			for (String key : parameters.keySet()) {
				if (key.equals(Utils.SKILLS_PROPERTY)) {
					gcPageJcrContent.setProperty(key, parameters.get(key), PropertyType.STRING);
				} else {
					gcPageJcrContent.setProperty(key, parameters.get(key)[0]);
				}
			}

		} catch (RepositoryException e) {
			LOG.error("Error accessing the repo in createReportPage");
		}
		
	}
	
}
