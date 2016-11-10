package com.maanlake.functions;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;


import com.maanlake.objects.GameObject;
import com.maanlake.objects.ObjectTypeEnum;
import com.maanlake.objects.CreatureObject;

public class ExitsImpl extends GameObject {
	
	String linktoSource ="";
	public String getLinktoSource() {
		return linktoSource;
	}

	public void setLinktoSource(String linktoSource) {
		this.linktoSource = linktoSource;
	}

	public String getLinktoDestination() {
		return linktoDestination;
	}

	public void setLinktoDestination(String linktoDestination) {
		this.linktoDestination = linktoDestination;
	}

	String linktoDestination ="";

	
	public Boolean CreateExit(GameObject Source, GameObject Destination) {
		/*HashMap<String, GameObject> ExitObjects = this.getObjects();
		ExitObjects.put("source",Source);
		ExitObjects.put("destination",Destination);
		this.setObjects(ExitObjects);*/
		this.linktoSource = Source.getName().toString();
		this.linktoDestination = Destination.getName().toString();
		return true;
	}

	public String UseExit(ExitsImpl Exit,CreatureObject Player) {
		if (Player.drainPower(Player.movePower)) {
		//Player.setLocation(Exit.getObject("destination"));
		Player.setLocationByLink(Exit.getLinktoDestination());
		return Exit.getLinktoDestination(); 
		}
		return null;
	}


	// not in use, delete?
	public HashMap<String, GameObject> ShowExits(GameObject Location) {
		HashMap<String, GameObject> ExitList = new HashMap<String, GameObject>();
		for (Iterator<GameObject> AllObjectsIterator = Location.getObjects().values().iterator();AllObjectsIterator.hasNext();) {
			GameObject actualObject = AllObjectsIterator.next();
			if(actualObject.getType().equals(ObjectTypeEnum.Exit)){
				ExitList.put(actualObject.getName().toString(), actualObject);
			}
		}
		return ExitList;
	}

	@Override
	public HashMap<String, Method> getMethodList() {
		HashMap<String, Method> MList = new HashMap<String, Method>();
		try {
			MList.put("Use Exit", ExitsImpl.class.getMethod("UseExit",ExitsImpl.class,CreatureObject.class));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MList;
	}
	
	


}
