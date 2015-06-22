package applications;

import java.util.*;

public class HandleApplications {

	private Map<String,Skill> skills = new HashMap<String,Skill>();
	private Map<String,Position> positions = new HashMap<String,Position>();
	private Map<String,Applicant> applicants = new HashMap<String,Applicant>();
	private Map<String, ArrayList<String>> positionsPerApplicant = new HashMap<String,ArrayList<String>>();
	private Map<String, ArrayList<String>> applicantPerPosition = new HashMap<String,ArrayList<String>>();
	private Map<String, String> winnerPerPosition = new HashMap<String,String>();
	
	
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
				if(!this.skills.containsKey(skillname)){
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
		ArrayList<Skill> reqSkills = this.positions.get(positionName).getSkills();
		Applicant applic = this.applicants.get(applicantName);
		if(this.applicantPerPosition.get(positionName).contains(applicantName)){
			throw new ApplicationException();
		}
		for(Skill skill : reqSkills){
			if(!applic.getSkills().contains(skill.getName())){
				throw new ApplicationException();
			}
		}
		ArrayList<String> a = this.positionsPerApplicant.getOrDefault(applicantName,new ArrayList<String>());
		a.add(positionName);
		this.positionsPerApplicant.put(applicantName,a);
		
		ArrayList<String> b = this.applicantPerPosition.getOrDefault(positionName,new ArrayList<String>());
		b.add(applicantName);
		this.applicantPerPosition.put(positionName,b);
		Position p = this.positions.get(positionName);
		p.addApplicant(applic);
		this.positions.replace(positionName, p);
	}
	
	public int setWinner(String applicantName, String positionName) throws ApplicationException {
		if(!this.applicantPerPosition.containsKey(applicantName)){
			throw new ApplicationException();
		}
		if(!this.winnerPerPosition.containsKey(positionName)){
			throw new ApplicationException();
		}
		Applicant app = this.applicants.get(applicantName);
		Position pos = this.positions.get(positionName);
		int retr = 0;
		int level = 0;
		int allLevel = 0;
		for(Skill s : app.getSkills()){
			level = 0;
			for(Skill sk : pos.getSkills()){
				if(sk.getName() == s.getName())
				{
					level += s.getLevel();
				}
			}
			allLevel += s.getLevel();
			retr += level;
		}
		int skillreqCnt = 0;
		for(Skill sk : pos.getSkills()){
			skillreqCnt += 1;
		}

		if(retr <= 6*skillreqCnt){
			throw new ApplicationException();
		}
		this.winnerPerPosition.put(positionName, applicantName);
		pos.setWinner(applicantName);
		this.positions.replace(positionName,pos);
		return allLevel;
	}
	
	public SortedMap<String, Long> skill_nApplicants() {
		SortedMap<String,Long> retr = new TreeMap<String,Long>();
		Long num;
		for(String s : this.skills.keySet()){
			num = retr.getOrDefault(s,0L);			
			num += 1;
			retr.put(s, num);
		}
		return retr;
	}
	public String maxPosition() {
		String mNa = "";
		Integer mNu = -1;
		Integer cnt = 0;
		for(String p : this.applicantPerPosition.keySet()){
			mNu = 0;
			cnt = 0;
			for ( String app : this.applicantPerPosition.get(p)){
				cnt += 1;
			}
			if(cnt > mNu){
				mNa = p;
				mNu = cnt;
			}
		}
		return mNa;
	}
}

