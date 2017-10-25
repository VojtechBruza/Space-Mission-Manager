package cz.muni.fi.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ArchivedMission {

	private String missionInformation;
	private String astronautInformation;
	private String spacecraftInformation;
	@Id
	private Long id;

}