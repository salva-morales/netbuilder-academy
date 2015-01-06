package uk.co.netbuilder.academy.models.people;

import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;

public class GC extends Person{
	
	private boolean availability;
	private String skills;
	
	public GC (Page page){
		super(page);
		ValueMap properties = page.getProperties();
		this.setAvailability(false);
		if (properties.containsKey("availability")) {
			this.setAvailability(Boolean.parseBoolean(properties.get("availability").toString()));
		}
		if (properties.containsKey("skills")) {
			this.skills = properties.get("skills").toString();
		}

		
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

}
