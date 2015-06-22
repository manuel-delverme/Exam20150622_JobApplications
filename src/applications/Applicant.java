package applications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Applicant {

	private ArrayList<Skill> skills;
	private String name;
	private Integer lala = 0;

	public Applicant(String name, ArrayList<Skill> appSkills) {
		// TODO Auto-generated constructor stub
		this.name = name;
		if(!appSkills.isEmpty()){
			this.lala = 1;
		}
		this.skills = appSkills;
	}
	public ArrayList<Skill> getSkills(){
		return this.skills;
	}
	public String getCapabilities() {
		// TODO Auto-generated method stub
		if(this.lala == 0){
			return "";
		}
		List<Skill> l =  this.skills.stream().sorted((p,q) -> p.getName().compareTo(q.getName())).collect(Collectors.toList());
		String retr = new String();
		for(Skill s : l){
			retr = retr.concat(","+s.getName()+":"+s.getLevel());
		}
		return retr.substring(1);
	}
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
