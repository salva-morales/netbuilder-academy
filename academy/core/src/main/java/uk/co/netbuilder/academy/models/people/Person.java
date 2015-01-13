package uk.co.netbuilder.academy.models.people;

import org.apache.sling.api.resource.ValueMap;

import uk.co.netbuilder.academy.utils.Utils;

import com.day.cq.wcm.api.Page;

public class Person {
	
	private String name;
	private int age;
	private String nationality;
	
	public Person (Page page){
		ValueMap properties = page.getProperties();
		this.name = this.nationality = "unknown";
		this.age = 0;
		if (properties.containsKey(Utils.NAME_PROPERTY)) {
			this.name = properties.get(Utils.NAME_PROPERTY).toString();
		}
		if (properties.containsKey(Utils.AGE_PROPERTY)) {
			this.age = Integer.parseInt(properties.get(Utils.AGE_PROPERTY).toString());
		}
		if (properties.containsKey(Utils.NATIONALITY_PROPERTY)) {
			this.nationality = properties.get(Utils.NATIONALITY_PROPERTY).toString();
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	
}
