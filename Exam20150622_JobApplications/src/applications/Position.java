package applications;

import java.util.*;
import java.util.stream.Collectors;

public class Position {

	
	private String name;
	private ArrayList<Skill> skills;
	private ArrayList<Applicant> applicants;
	private String winner = "";
	public Position(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
	}
	public void addSkill(Skill newSkill){
		this.skills.add(newSkill);
	}
	public void addApplicant(Applicant nA){
		this.applicants.add(nA);
	}

	public String getName() {return this.name;}
	
	public List<String> getApplicants() {
		// 		return this.positions.stream().sorted((p,q) -> p.getName().compareTo(q.getName())).collect(Collectors.toList());
		return this.applicants.stream().map(s -> s.getName()).sorted().collect(Collectors.toList());
	}
	
	public String getWinner() {
		return this.winner;
	}
	public ArrayList<Skill> getSkills() {
		
		return this.skills;
	}
	public void setWinner(String applicantName) {
		// TODO Auto-generated method stub
		this.winner = applicantName;
	}
}