package org.z.posarma;

public class unit {
	private String type;
	private String position;
	private String dir;
	private String number;
	private String weapons;
	private String magazines;
	
	
	
	public unit(String type, String position) {		
		this.type = type;
		this.position = position;
	}
	public unit() {
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public void setNumber(String number) {
		this.number =  number;
	}
	public String getNumber() {
		return number;
	}
	public String getUnitName() {
		return "_unit"+number;
	}
	public String getVehicleName() {
		return "_vehicle"+number;
	}
	public void setWeapons(String weapons) {
		this.weapons = weapons;
	}
	public String getWeapons() {
		return weapons;
	}
	public void setMagazines(String magazines) {
		this.magazines = magazines;
	}
	public String getMagazines() {
		return magazines;
	}
	
	
	
}
