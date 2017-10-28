package cz.muni.fi.Entity;

import java.time.ZonedDateTime;

public class CraftComponent {

	private boolean readyToUse;
	private String name;
	private ZonedDateTime estimatedReadyDate;
	private Long id;

	public boolean isReadyToUse() {
		return readyToUse;
	}
}