package uk.co.netbuilder.academy.models.people;

import java.util.List;

import com.day.cq.wcm.api.Page;

public class Trainee extends Person{
	public Trainee(Page page) {
		super(page);
	}
	private boolean availability;
	private List<String> skills;

}
