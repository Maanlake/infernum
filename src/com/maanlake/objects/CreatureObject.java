package com.maanlake.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.maanlake.functions.Base;

public class CreatureObject extends GameObject{
	
private GameObject Location; // Location of the Creature
private List<DamageGFX> Messages = new ArrayList<DamageGFX>();
private Boolean isDead = false;
private Boolean ready = false;
private Boolean levelUp = false;
private GameObject Root;
private String LocationString="";
public int movePower=0;
private long XP=0;
private float CPU_Speed=1.0f;

public float getCPU_Speed() {
	return CPU_Speed;
}

public void setCPU_Speed(float cPU_Speed) {
	CPU_Speed = cPU_Speed;
}

public String getLocationString() {
	return LocationString;
}

public void setLocationString(String locationString) {
	LocationString = locationString;
}

public GameObject getRoot() {
	return Root;
}

public void setRoot(GameObject root) {
	Root = root;
}

public Boolean getLevelUp() {
	return levelUp;
}

public void setLevelUp(Boolean levelUp) {
	this.levelUp = levelUp;
}




public void addXP(int newXP) {
	this.XP = this.XP+newXP;
}

public long getXP() {
	return this.XP;
}

public int getCreatureLevel(){
	return (int) (Math.log(this.getXP()/3));
}

public void setCreatureLevel(int level){
	this.XP=(long)(Math.exp((double)level)*3)+1;
}


public List<DamageGFX> getMessages() {
	return Messages;
}

public void setMessages(List<DamageGFX> messages) {
	Messages = messages;
}



public Boolean getReady() {
	return ready;
}

public void setReady(Boolean ready) {
	this.ready = ready;
}

public Boolean getIsDead() {
	return isDead;
}

public void setIsDead(Boolean isDead) {
	this.isDead = isDead;
}

public GameObject getLocation() {
	return Location;
}

public void setLocation(Base location) {
	location.setVisited(true);
	Location = location;
	this.setLocationString(location.getName().toString());
}

public void setLocationByLink(String link) {
	Location = this.getRoot().getObject(link);
}

public void addMessagetoStack(DamageGFX msg) {
	Messages.add(msg);
}

public DamageGFX getLastMessagefromStack() {
	DamageGFX msg= new DamageGFX();
	if (Messages.size()>0) {
		Iterator<DamageGFX> i = Messages.iterator();
		msg = (DamageGFX) i.next();
		Messages.remove(msg);
	}
	return msg;
}

public void dead(CreatureObject loser) {
	
	// remove from Location
	this.getLocation().removeObject(this);		
	// drop all Elements
	dropItems(loser);
	this.setIsDead(true);
}

private void dropItems(CreatureObject creature) {
	for(Iterator<Entry<String, GameObject>> ItemList = creature.getObjects().entrySet().iterator(); ItemList.hasNext();) {
		Entry<String, GameObject> Item = ItemList.next();
		this.getLocation().addObject(Item.getValue());
		//creature.addMessagetoStak("You found:"+Item.getKey());
	}
}


}
