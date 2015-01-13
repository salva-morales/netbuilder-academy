package uk.co.netbuilder.academy.servlets;

import java.io.IOException;
import java.util.Map;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.netbuilder.academy.services.GCService;
import uk.co.netbuilder.academy.utils.Utils;



@SlingServlet(resourceTypes = "nb-academy/components/gc-creator", methods = "POST", selectors="generate")
@Properties({
	@Property(name = "service.pid", 
			value = "uk.co.netbuilder.servlets.GCCreatorServlet", 
			propertyPrivate = false),
			@Property(name = "service.description", 
			value = "Handle the creation of a GC", 
			propertyPrivate = false),
			@Property(name = "service.vendor", 
			value = "NETBuilder", 
			propertyPrivate = false)
})
public class GCCreatorServlet extends SlingAllMethodsServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GCCreatorServlet.class);


	@Reference
	private SlingSettingsService slingSettingsService;


	@Reference
	private GCService gcService;
	

	@Override
	public void doPost (SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException{

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();
		
		//TODO: Research why the OWASP dependency can not be resolved.
		//Utils.assertValidRequestParam("Checking params from GCCreatorServlet", request, parameters.keySet());

		String redirectUrl = Utils.PATH_TO_GC_CREATOR;
		
		if (parameters.containsKey(Utils.NAME_PROPERTY)){
			LOGGER.error("parameters.containsKey(Utils.NAME_PROPERTY)");
			// We are going to use the name of the GC (We hope it is unique) to define the path of the page
			// that represents the GC.
			String gcJCRNormalizedName = Utils.jcrClean(parameters.get(Utils.NAME_PROPERTY)[0]);
			final String fullGCPath = Utils.GCFINDERPAGE_PATH + "/" + gcJCRNormalizedName;

			// Let's call the service to carry with the BL
			gcService.createGC(parameters,fullGCPath);
			redirectUrl = fullGCPath + ".html";
		}

		// It does the redirect
		response.setContentType("application/html");
		response.setCharacterEncoding("utf-8");
		response.sendRedirect(redirectUrl);
	}

}

