package uk.co.netbuilder.academy.taglibs;

import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.scripting.jsp.util.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.netbuilder.academy.models.people.GC;
import uk.co.netbuilder.academy.services.GCService;
import uk.co.netbuilder.academy.utils.Utils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

// Our class extends TagSupport
public class ListAvailableGC extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(ListAvailableGC.class);

	public int doStartTag() {

		// We retrieve the request by using the Object TagUtil which is available thanks to TagSupport Class
		SlingHttpServletRequest request = TagUtil.getRequest(pageContext);

		// retrieve the GCService using the helper
		SlingBindings bindings = (SlingBindings) request.getAttribute(SlingBindings.class.getName());
		SlingScriptHelper helper = bindings.getSling();
		GCService gcService = helper.getService(GCService.class);

		// Always some guard to check if the object is null
		if (gcService != null) {
			
			PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
			
			if (pageManager != null) {
				
				Page gcFinderPage = pageManager.getPage(Utils.GCFINDERPAGE_PATH);
				
				if (gcFinderPage != null) {
					
					// Set the list of available GC
					List<GC> availableGCs = gcService.listAllAvailableGC(gcFinderPage);
					// Set this list into the pageContext to be used within the JSP
					pageContext.setAttribute("availableGCs", availableGCs);
					
				} else {
					LOG.error("gcFinderPage is null!");
				}
			} else {
				LOG.error("PageManager is null");
			}
		} else {
			LOG.error("gcServicegcService is null");
		}

		return SKIP_BODY;
	}
}
