package com.maanlake.objects;

import java.util.Iterator;
import java.util.List;

import com.maanlake.functions.ExitsImpl;

public class roomDoors {
	Boolean leftDoor = false;
	Boolean frontDoor = false;
	Boolean rigthDoor = false;
	Boolean rearDoor = false;

	public Boolean getLeftDoor() {
		return leftDoor;
	}

	public void setLeftDoor(Boolean leftDoor) {
		this.leftDoor = leftDoor;
	}

	public Boolean getFrontDoor() {
		return frontDoor;
	}

	public void setFrontDoor(Boolean frontDoor) {
		this.frontDoor = frontDoor;
	}

	public Boolean getRigthDoor() {
		return rigthDoor;
	}

	public void setRigthDoor(Boolean rigthDoor) {
		this.rigthDoor = rigthDoor;
	}

	public Boolean getRearDoor() {
		return rearDoor;
	}

	public void setRearDoor(Boolean rearDoor) {
		this.rearDoor = rearDoor;
	}
	
	public roomDoors getDoorsfromRoom(GameObject theroom, GameObject GameWorld) {
		// get Player room
		roomDoors DoorthisRoom = new roomDoors();
		GameObject actualRoom = theroom;
		// get all Exits
		List<GameObject> thisRoomDoors = actualRoom
				.getChildsfromType(ObjectTypeEnum.Exit);
		// check Directs and add style
		for (Iterator<GameObject> di = thisRoomDoors.iterator(); di.hasNext();) {
			ExitsImpl nextDoor = (ExitsImpl) di.next();
			// GameObject RoombehindDoor = nextDoor.getObject("destination");
			GameObject RoombehindDoor = GameWorld.getObject(nextDoor
					.getLinktoDestination());
			if (actualRoom.getX() > RoombehindDoor.getX()) {
				DoorthisRoom.setLeftDoor(true);
			}
			if (actualRoom.getX() < RoombehindDoor.getX()) {
				DoorthisRoom.setRigthDoor(true);
			}
			if (actualRoom.getY() < RoombehindDoor.getY()) {
				DoorthisRoom.setFrontDoor(true);
			}
			if (actualRoom.getY() > RoombehindDoor.getY()) {
				DoorthisRoom.setRearDoor(true);
			}

		}
		return DoorthisRoom;
	}
	
	
}