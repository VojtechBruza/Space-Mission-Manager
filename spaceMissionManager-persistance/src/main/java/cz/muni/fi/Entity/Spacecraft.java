package cz.muni.fi.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Spacecraft {

	@Id
	private Long id;

	private String type; //	Probes vs astronauts? type not needed

	private String name;
	@OneToMany
	private List<CraftComponent> allComponents;

//	private int unfinishedComponents; //rather use method?


	public Spacecraft(List<CraftComponent> allComponents) {
		this.allComponents = allComponents;
	}


	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CraftComponent> getAllComponents() {
		return allComponents;
	}

	public int unfinishedComponentsCount(){
		int count = allComponents.size();
		for (CraftComponent c : allComponents){
			if(c.isReadyToUse()){
				count--;
			}
		}
		return count;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Spacecraft)) return false;
		Spacecraft spacecraft = (Spacecraft) o;
		return getId() != null ? getId().equals(spacecraft.getId()) : spacecraft.getId() == null;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}