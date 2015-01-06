package uk.co.netbuilder.academy.models.people;

import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;

public class Person {
	
	private String name;
	private int age;
	private String nationality;
	
	public Person (Page page){
		ValueMap properties = page.getProperties();
		this.name = this.nationality = "unknown";
		this.age = 0;
		if (properties.containsKey("name")) {
			this.name = properties.get("name").toString();
		}
		if (properties.containsKey("age")) {
			this.age = Integer.parseInt(properties.get("age").toString());
		}
		if (properties.containsKey("nationality")) {
			this.nationality = properties.get("nationality").toString();
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
