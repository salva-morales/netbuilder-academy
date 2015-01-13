package uk.co.netbuilder.academy.models.people;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.sling.api.resource.ValueMap;

import uk.co.netbuilder.academy.utils.Utils;

import com.day.cq.wcm.api.Page;

public class GC extends Person{
	
	private boolean availability;
	
	private Set<String> skills;
	
	public GC (Page page){
		super(page);
		ValueMap properties = page.getProperties();
		this.setAvailability(false);
		if (properties.containsKey(Utils.AVAILABILITY_PROPERTY)) {
			this.setAvailability(Boolean.parseBoolean(properties.get(Utils.AVAILABILITY_PROPERTY).toString()));
		}
		if (properties.containsKey(Utils.SKILLS_PROPERTY)) {
			setSkills(properties.get(Utils.SKILLS_PROPERTY, String[].class));
		}
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(String[] skillsToFind) {
		this.skills = new HashSet<String>(Arrays.asList(skillsToFind));
	}
	
	public boolean hasSkills (String[] skillsToFind) {
		
		for (String skill : skillsToFind){
			if (this.skills.contains(skill.toLowerCase()) || this.skills.contains(skill.toUpperCase())) {
				return true;
			}
		}
		
		return false;
	}

}
