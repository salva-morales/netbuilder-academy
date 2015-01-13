package uk.co.netbuilder.academy.utils;

import java.util.Set;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
//import org.owasp.esapi.ESAPI;
//import org.owasp.esapi.errors.IntrusionException;
//import org.owasp.esapi.errors.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Utils {
	
	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
	
	public static final String PATH_TO_GC_CREATOR = "/etc/netbuilder/gc-creator.html";
	
	public static final String GCFINDERPAGE_PATH = "/content/nb-academy/en_GB/staff/gc-finder";
	
	public static final String PUBLISH_RUNMODE = "dispatcher";
	
	public static final String NB_DESIGN_PATH = "/etc/designs/nb-design";
	
	public static final String GC_PAGE_RENDERER_PATH = "nb-academy/components/pages/people/gc-page";
	public static final String GC_TEMPLATE_PATH = "/apps/nb-academy/templates/gc-page";
	
	public static final String NAME_PROPERTY = "name";
	public static final String AGE_PROPERTY = "age";
	public static final String AVAILABILITY_PROPERTY = "availability";
	public static final String NATIONALITY_PROPERTY = "nationality";
	public static final String SKILLS_PROPERTY = "skills";
	
	public static boolean isPublishRunmode(Set<String> runmodes) {
		for (String mode : runmodes) {
			if (mode.equals(PUBLISH_RUNMODE)) {
				return true;
			}
		}
		return false;
	}

	public static String getRunmodeAwarePageUrl(Set<String> runmodes, ResourceResolver rr, String url) {
		if (isPublishRunmode(runmodes)) {
			return rr.map(url);
		} else {
			return rr.map(url) + ".html";
		}
	}
	
	public static String jcrClean(String string) {
		if (string != null) {
			String stringJcrCleaned = string;
			stringJcrCleaned = stringJcrCleaned.toLowerCase();
			stringJcrCleaned = stringJcrCleaned.replace("&amp;", "and");
			stringJcrCleaned = stringJcrCleaned.replace("&", "and");
			stringJcrCleaned = stringJcrCleaned.replaceAll("[^A-Za-z0-9 ]", " ");
			stringJcrCleaned = stringJcrCleaned.replace(" ", "-");
			return stringJcrCleaned;
		}
		return "";
	}
	
	//TODO: Research why the OWASP dependency can not be resolved.
//	public static void assertValidRequestParam (String context, SlingHttpServletRequest request, Set<String> parameters){
//		try {
//			ESAPI.validator().assertValidHTTPRequestParameterSet(context, request, parameters, null);
//		} catch (ValidationException e) {
//			LOG.error("The parameters in the request do not match the validation rules");
//			e.printStackTrace();
//		} catch (IntrusionException e) {
//			LOG.error("Attack in progress!!!! Please, stop :)");
//			e.printStackTrace();
//		}
//	}
}
