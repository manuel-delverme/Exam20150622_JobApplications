package applications;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Skill {

	
	private String name;
	private Integer level;
	
	private ArrayList<Position> positions;
	public Skill(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
	}
	public Skill(String name, Integer level) {
		this.name = name;
		this.level = level;
		// TODO Auto-generated constructor stub
	}
	public Integer getLevel()
	{
		return this.level;
	}
	public String getName() {return this.name;}
	public void addPosition(Position p){
		this.positions.add(p);
	}
	public List<Position> getPositions() {
		return this.positions.stream().sorted((p,q) -> p.getName().compareTo(q.getName())).collect(Collectors.toList());
	}
}