package uk.co.netbuilder.academy.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.http.HttpStatus;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import uk.co.netbuilder.academy.models.people.GC;
import uk.co.netbuilder.academy.services.GCService;
import uk.co.netbuilder.academy.utils.Utils;


@SlingServlet(paths = "/bin/findGCs", methods = "GET", extensions="json")
@Properties({
	@Property(name = "service.pid", 
			value = "uk.co.netbuilder.servlets.GCSkillFinderServlet", 
			propertyPrivate = false),
			@Property(name = "service.description", 
			value = "Retrieve a list of GC with the defined skill, returning the result in JSON format.", 
			propertyPrivate = false),
			@Property(name = "service.vendor", 
			value = "NETBuilder", 
			propertyPrivate = false)
})
public class GCSkillFinderServlet extends SlingAllMethodsServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GCSkillFinderServlet.class);
    
	@Reference
	private GCService gcService;

	@Override
	public void doGet (SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException{

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();
		
		//TODO: Research why the OWASP dependency can not be resolved.
		//Utils.assertValidRequestParam("Checking params from GCSkillFinderServlet", request, parameters.keySet());

		PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
		
		List<GC> availableGCsSkill = new ArrayList<GC>();
		
		if (pageManager != null) {

			Page gcFinderPage = pageManager.getPage(Utils.GCFINDERPAGE_PATH);
			if (gcFinderPage != null) {
				List<GC> availableGCs = gcService.listAllAvailableGC(gcFinderPage);
				
				if (parameters.containsKey(Utils.SKILLS_PROPERTY)) {
					String[] skills = parameters.get(Utils.SKILLS_PROPERTY);
					availableGCsSkill = gcService.listAllGCWithSkill(availableGCs, skills);
				}
			}
		}

		try {
          response.setHeader("Dispatcher", "no-cache");
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(getJSONFromGCs(availableGCsSkill).toString());
		} catch (JSONException e) {
			response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            LOGGER.warn("Unable to filter GCs", e.getMessage());
		}
		
	}
	
	private JSONArray getJSONFromGCs (List<GC> GCs) throws JSONException {
		JSONArray JSONGCs = new JSONArray();
		for (GC gc : GCs) {
			JSONObject o = new JSONObject();
			o.put("name", gc.getName());
			JSONGCs.put(o);
		}
		return JSONGCs;
	}
}


