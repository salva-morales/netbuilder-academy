package uk.co.netbuilder.academy.taglibs;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.scripting.jsp.util.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.netbuilder.academy.models.people.GC;

public class ListAvailableGC extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(ListAvailableGC.class);

	public int doStartTag() {

		// retrieve the service using the helper
		SlingHttpServletRequest request = TagUtil.getRequest(pageContext);
		SlingBindings bindings = (SlingBindings) request.getAttribute(SlingBindings.class.getName());
		SlingScriptHelper helper = bindings.getSling();
		//FilterService availableFiltersService = helper.getService(FilterService.class);

		GC gc = new GC();

		return SKIP_BODY;
	}
}
