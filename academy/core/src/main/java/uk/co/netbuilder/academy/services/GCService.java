package uk.co.netbuilder.academy.services;

import java.util.List;

import uk.co.netbuilder.academy.models.people.GC;

public interface GCService {
	
	List<GC> listAllGC ();
	
	//List<GC> listAllAvailableGC (List<GC> allGCs);
	
	List<GC> listAllGCWithSkill (String skill);
	
	

}
