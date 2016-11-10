package com.maanlake.objects;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.awt.Point;

public abstract class GameObject {

	private StringBuffer Name = new StringBuffer("Void");
	private StringBuffer Description = new StringBuffer("You are in the Void");
	private ObjectTypeEnum Type = ObjectTypeEnum.Dummy;
	private HashMap<String, GameObject> Objects = new HashMap<String, GameObject>();
	private HashMap<String, Method> Methods = new HashMap<String, Method>();
	private int Power = 0;
	private int MaxPower = 100;
	private String draw_res = "";
	private String icon_res = "";
	private int x = 0;
	private int y = 0;
	private int z = 0;
	private double angle = 0;
	private boolean iswearable = false;
	private boolean isequipped = false;
	private boolean isautomaticusable = false;
	public boolean isIsautomaticusable() {
		return isautomaticusable;
	}
	
	public void setIsautomaticusable(boolean isautomaticusable) {
		this.isautomaticusable = isautomaticusable;
	}

	private int objectlevel = 0;
	private Point onScreenPosition = new Point();
	private boolean isOnScreen = false;

	public boolean isOnScreen() {
		return isOnScreen;
	}

	public void setOnScreen(boolean isOnScreen) {
		this.isOnScreen = isOnScreen;
	}

	public Point getOnScreenPosition() {
		return onScreenPosition;
	}

	public void setOnScreenPosition(Point onScreenPosition) {
		if (onScreenPosition.x!=0 && onScreenPosition.y!=0) this.setOnScreen(true);
		this.onScreenPosition = onScreenPosition;
	}

	public int getObjectLevel() {
		return objectlevel;
	}

	public void setObjectLevel(int level) {
		this.objectlevel = level;
	}

	public boolean isIsequipped() {
		return isequipped;
	}

	public void setIsequipped(boolean isequipped) {
		this.isequipped = isequipped;
	}

	public boolean isIswearable() {
		return iswearable;
	}

	public void setIswearable(boolean iswearable) {
		this.iswearable = iswearable;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public double getRotation() {
		return angle;
	}

	public void setRotation(double angle) {
		this.angle = angle;
	}
	
	
	
	public int getMaxPower() {
		return MaxPower;
	}

	public void setMaxPower(int maxPower) {
		MaxPower = maxPower;
	}

	public ObjectTypeEnum getType() {
		return Type;
	}

	public void setType(ObjectTypeEnum type) {
		Type = type;
	}

	public StringBuffer getName() {
		return Name;
	}

	public void setName(StringBuffer name) {
		Name = name;
	}

	public HashMap<String, GameObject> getObjects() {
		return Objects;
	}

	public void setObjects(HashMap<String, GameObject> objects) {
		Objects = objects;
	}

	public void addObject(GameObject TheObject) {
		Objects.put(TheObject.getName().toString(), TheObject);
	}

	public void removeObject(GameObject TheObject) {
		Objects.remove(TheObject.getName().toString());
	}

	public GameObject getObject(String Key) {
		return Objects.get(Key);
	}

	public StringBuffer getDescription() {
		return Description;
	}

	public void setDescription(StringBuffer description) {
		Description = description;
	}

	public HashMap<String, Method> getMethods() {
		return Methods;
	}

	public void setMethods(HashMap<String, Method> methods) {
		Methods = methods;
	}

	public Method getMethod(String Key) {
		return Methods.get(Key);
	}

	public void addMethod(String Description, Method TheMethod) {
		Methods.put(Description, TheMethod);
	}
	
	public HashMap<String, Method> getMethodList() {
		HashMap<String, Method> MList = new HashMap<String, Method>();
		return MList;
	}

	public List<String> getPureMethodsList() {
		List<String> methodsList = new ArrayList<String>();
		methodsList = new ArrayList<String>(Methods.keySet());
		return methodsList;
	}

	public int getPowerStatus() {
		return Power;
	}

	public void setPowerStatus(int power) {
		Power = power;
	}

	public void chargePower(int charge) {
		Power = Power + charge;
		if (Power > MaxPower)
			Power = MaxPower;
	}

	public boolean drainPower(int drain) {
		Power = Power - drain;

		if (Power < drain) {
			Power = 0;
			return false;
		} else {
			return true;
		}
	}

	public String getDraw_Resource() {
		return draw_res;
	}

	public void setDraw_Resource(String draw_res) {
		this.draw_res = draw_res;
	}

	public String getIcon_Resource() {
		return icon_res;
	}

	public void setIcon_Resource(String icon_res) {
		this.icon_res = icon_res;
	}

	public List<GameObject> getChildsfromType(ObjectTypeEnum type) {
		List<GameObject> TypeObjects = new ArrayList<GameObject>();
		for (Iterator<Entry<String, GameObject>> AllObjectsIterator = Objects
				.entrySet().iterator(); AllObjectsIterator.hasNext();) {
			Entry<String, GameObject> e = AllObjectsIterator.next();
			GameObject actualObject = (GameObject) e.getValue();
			if (actualObject.getType().equals(type)) {
				TypeObjects.add(actualObject);
			}
		}
		return TypeObjects;
	}


}
