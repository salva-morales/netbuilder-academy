package uk.co.netbuilder.academy.taglibs;

import java.util.List;

import javax.jcr.Node;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.scripting.jsp.util.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ee.aem.newdevices.models.Filter;
import uk.co.ee.aem.newdevices.services.FilterService;

public class ListFiltersReport extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ListAvailableFilters.class);

	@Override
	public int doStartTag() {

		// retrieve the service using the helper
		SlingHttpServletRequest request = TagUtil.getRequest(pageContext);
		SlingBindings bindings = (SlingBindings) request.getAttribute(SlingBindings.class.getName());
		SlingScriptHelper helper = bindings.getSling();
		FilterService availableFiltersService = helper.getService(FilterService.class);

		String path = request.getPathInfo();
		path = path.replace(".html", "");
		
		Resource reportResource = request.getResourceResolver().resolve(path);

		// use the available filter service to retrieve a list of the available
		// filters and return it to page scope
		if (availableFiltersService != null) {

			List<Filter> availableFiltersFromTags = availableFiltersService.getListOfAvailableFiltersFromTags(true, true);
			List<Filter> availableFiltersFromCustomFilters = availableFiltersService.getListOfAvailableFiltersFromCustomFilters();

			List<Filter> populatedFiltersFromTags = availableFiltersService.populateFiltersFromTags(availableFiltersFromTags, reportResource);
			List<Filter> populatedFiltersFromCustomFilters = availableFiltersService.populateFiltersFromCustomFilters(availableFiltersFromCustomFilters, reportResource);

			populatedFiltersFromTags.addAll(populatedFiltersFromCustomFilters);

			pageContext.setAttribute("populatedFilters", populatedFiltersFromTags);
		} else {
			LOGGER.error("EE-NEW-DEVICES: ListAvailableFiltersTag: Unable to use the FiltersService because it is null");
		}
		return SKIP_BODY;
	}
}
