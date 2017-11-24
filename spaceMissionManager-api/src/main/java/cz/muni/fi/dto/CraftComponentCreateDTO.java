package cz.muni.fi.dto;


import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;


public class CraftComponentCreateDTO {

	@NotNull
	@Size(min = 3, max = 50)
	private String name;

	private boolean readyToUse;

	@NotNull
	private SpacecraftDTO spacecraft;

	@Future
	@NotNull
	private ZonedDateTime readyDate;


	public boolean isReadyToUse() {
		return readyToUse;
	}

	public void setReadyToUse(boolean readyToUse) {
		this.readyToUse = readyToUse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SpacecraftDTO getSpacecraft() {
		return spacecraft;
	}

	public void setSpacecraft(SpacecraftDTO spacecraft) {
		this.spacecraft = spacecraft;
	}

	public ZonedDateTime getReadyDate() {
		return readyDate;
	}

	public void setReadyDate(ZonedDateTime readyDate) {
		this.readyDate = readyDate;
	}
}
