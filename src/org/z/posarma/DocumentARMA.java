package org.z.posarma;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class DocumentARMA  {
	private Document Document;
	private static unit myunit;
	private static Integer size = 10000; 
	private static String[] sqftext = new String[size];
	private static Integer nbline;
	private static String[] codeblocks = new String[size]; // one block per unit
	private static Integer nbunit;
	
	
	public DocumentARMA(Document Doc) {
		this.Document = Doc;
		nbline = 0;	
		nbunit = 0;
	}
	public static String[] getSqftext() {
		return sqftext;
	}
	
	public static String[] getCodeblocks() {
		return codeblocks;
	}
	public static Integer getNbunit() {
		return nbunit;
	}
	private static void sqfunit() {
		if (myunit!=null) {
			
			sqftext[nbline]= "//NewUnit===>" + myunit.getUnitName() ;
			nbline++;
			codeblocks[nbunit] = "_code" + myunit.getUnitName(); 
			nbunit++;
			sqftext[nbline]= "_code" + myunit.getUnitName() + " = {" ;
			nbline++;
			sqftext[nbline]= "_g" + myunit.getUnitName() + " = createGroup " + myunit.getSide() + " ;";
			nbline++;
			sqftext[nbline]= myunit.getUnitName() + " = _g" + myunit.getUnitName() + " createUnit [\""+myunit.getType()+"\", [1,1,0.2], [], 0, 'CAN_COLLIDE'];";
			nbline++;
			sqftext[nbline]= myunit.getUnitName() + " setPosASL " + myunit.getPosition()+";";
			nbline++;
			sqftext[nbline]= "_g" + myunit.getUnitName() + " setFormDir " + myunit.getDir()+";";
			nbline++;
			
			if (myunit.getMagazines().length()>2) {
				sqftext[nbline]= "removeAllWeapons " + myunit.getUnitName() + ";";
				nbline++;
				sqftext[nbline]= "{"+ myunit.getUnitName() +" addWeapon _x;} foreach " + myunit.getWeapons() + ";";
				nbline++;
			}
			if (myunit.getMagazines().length()>2) {
				sqftext[nbline]= "{"+ myunit.getUnitName() +" addMagazine _x;} foreach " + myunit.getMagazines() + ";";
				nbline++;
			}
			
			sqftext[nbline]= "};" ;
			nbline++;
		}
	}
	
	private static void sqfvehicle() {
		if (myunit!=null) {
			
			sqftext[nbline]= "//NewVehicle===>" + myunit.getVehicleName() ;
			nbline++;
			codeblocks[nbunit] = "_code" + myunit.getVehicleName(); 
			nbunit++;
			sqftext[nbline]= "_code" + myunit.getVehicleName() + " = {" ;
			nbline++;
			sqftext[nbline]= myunit.getVehicleName() + " = createVehicle [\""+myunit.getType()+"\", [1,1,0.2], [], 0, 'CAN_COLLIDE'];";
			nbline++;
			sqftext[nbline]= myunit.getVehicleName() + " setPosASL " + myunit.getPosition()+";";
			nbline++;
			sqftext[nbline]= myunit.getVehicleName() + " setDir " + myunit.getDir()+";";
			nbline++;
			sqftext[nbline]= "};" ;
			nbline++;
		}
	}
	
	public static void printDocument(Node node) {
		
		if (node.getNodeType()==Node.ELEMENT_NODE) {
			System.out.println("ELEMENT_NODE==>"+node.getNodeName());
			System.out.println(node.getTextContent());
			if (node.getNodeName()=="type") {
				
				myunit = new unit();
				myunit.setType(node.getTextContent());				
			}
			if (node.getNodeName()=="side") {
				myunit.setSide(node.getTextContent());				
			}
			if (node.getNodeName()=="number") {
				myunit.setNumber(node.getTextContent());				
			}
			if (node.getNodeName()=="setPosASL") {
				myunit.setPosition(node.getTextContent());				
			}
			if (node.getNodeName()=="setDir") {
				myunit.setDir(node.getTextContent());				
			}
			if (node.getNodeName()=="addWeapon") {
				myunit.setWeapons(node.getTextContent());				
			}
			if (node.getNodeName()=="addMagazine") {
				myunit.setMagazines(node.getTextContent());				
			}
			
		}
		
		Node nextChild = node.getFirstChild();
		while (nextChild!=null) {
			printDocument(nextChild);
			nextChild=nextChild.getNextSibling();
		}
		if (node.getNodeName()=="createUnit") {
			sqfunit();			
		}
		if (node.getNodeName()=="createVehicle") {
			sqfvehicle();			
		}	
	}

	public Document getDocument() {
		return Document;
	}

}
