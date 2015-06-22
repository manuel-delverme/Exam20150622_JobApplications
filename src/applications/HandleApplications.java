package applications;

import java.util.*;

public class HandleApplications {

	private Map<String,Skill> skills = new HashMap<String,Skill>();
	private Map<String,Position> positions = new HashMap<String,Position>();
	private Map<String,Applicant> applicants = new HashMap<String,Applicant>();
	
	public void addSkills(String... names) throws ApplicationException {
		for(String name: names){
			if(this.skills.containsKey(name)){
				throw new ApplicationException();
			} else {
				this.skills.put(name, new Skill(name));
			}
		}
	}
	public void addPosition(String name, String... skillNames) throws ApplicationException {
		if(this.positions.containsKey(name)){
			throw new ApplicationException();
		} else {
			
			Position newPosition = new Position(name);
			for(String skillname: skillNames){
				if(!this.skills.containsKey(name)){
					throw new ApplicationException();
				}
				Skill sk = this.skills.get(skillname);
				newPosition.addSkill(sk);
				sk.addPosition(newPosition);
				this.skills.replace(skillname,sk);
			}		
			this.positions.put(name, newPosition);
		}
	}
	
	public Skill getSkill(String name) {
		return this.skills.getOrDefault(name, null);
	}
	public Position getPosition(String name) {
		return this.positions.getOrDefault(name,null);
	}
	
	public void addApplicant(String name, String capabilities) throws ApplicationException {
		if(this.applicants.containsKey(name)){
			throw new ApplicationException();
		}
		ArrayList<Skill> appSkills = new ArrayList<Skill>();
		String[] skills = capabilities.split(",");
		for(String skill : skills){
			String[] arrSkill = skill.split(":");
			if(arrSkill.length != 2){
				throw new ApplicationException();
			}	
			Integer level;
			try{
				level = Integer.valueOf(arrSkill[1]);
			} catch(Exception e) {
				throw new ApplicationException();
			}
			String skillName = arrSkill[0];
			if(level > 10 || level < 1){
				throw new ApplicationException();
			}
			appSkills.add(new Skill(skillName,level));
		}
		this.applicants.put(name, new Applicant(name,appSkills));
	}
	public String getCapabilities(String applicantName) throws ApplicationException {
		if(this.applicants.containsKey(applicantName)){
			return this.applicants.get(applicantName).getCapabilities();
		} else {
			throw new ApplicationException();
		}
	}
	
	public void enterApplication(String applicantName, String positionName) throws ApplicationException {
		
	}
	
	public int setWinner(String applicantName, String positionName) throws ApplicationException {
		return 0;
	}
	
	public SortedMap<String, Long> skill_nApplicants() {
		return null;
	}
	public String maxPosition() {
		return null;
	}
}

