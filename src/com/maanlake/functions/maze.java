package com.maanlake.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import java.util.logging.Logger;




public class maze {
	
	int[][] themaze;
	HashMap<String,room> rooms = new HashMap<String,room>();
	
	// Logging
	private final static Logger LOGGER = Logger.getLogger(maze.class.getName());
	
	
	public HashMap<String, room> getRooms() {
		return rooms;
	}

	public class node {
		
		public int x=0;
		public int y=0;
		int xp=0;
		int yp=0;
		
		public node(int i, int j) {
			this.x=i;
			this.y=j;
		}
		
		public void prevNode(int i, int j) {
			this.xp=i;
			this.yp=j;
		}
		public int getx() {
			return this.x;
		}
		public int gety() {
			return this.y;
		}
		
	}
	
	public class room {
		node position=new node(-1,-1);
		List<node> doors = new ArrayList<node>();
		int direction=0;
		int creatureLevel=0;
		
		public int getCreatureLevel() {
			return creatureLevel;
		}

		public void setCreatureLevel(int creatureLevel) {
			this.creatureLevel = creatureLevel;
		}

		public int getDirection() {
			return direction;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}

		public node getPosition(){
			return this.position;
		}
		
		public void setPostion(int x, int y) {
			this.position.x=x;
			this.position.y=y;
		}
		
		public void addDoor(node room) {
			this.doors.add(room);
		}

		public List<node> getDoors() {
			return doors;
		}
		
		
		
	}

	public void create(int x, int y,int startx, int starty) {		//,List<Integer> code
		
		themaze = new int[x][y];
		Collection<node> frontiers = new ArrayList<node>();
		node actualnode = new node(startx,starty);
		room actualroom = new room();
		actualroom.setPostion(actualnode.x, actualnode.y);
		node mydoor = new node(actualnode.xp,actualnode.yp);
		actualroom.addDoor(mydoor);
		rooms.put(String.valueOf(actualnode.x)+"-"+String.valueOf(actualnode.y), actualroom);
		themaze[actualnode.getx()][actualnode.gety()]=1;
		makeFrontiers(actualnode,frontiers,x,y);
		int mazedistance =0;
		while(!frontiers.isEmpty()) {
			// search a random node from Frontiers List
			int nr = (int) (Math.random() * frontiers.size());
			// choose actual node from Frontier list by random
			actualnode = (node) frontiers.toArray()[nr];
			// set room
			if (rooms.containsKey(String.valueOf(actualnode.x)+"-"+String.valueOf(actualnode.y))) {
				actualroom = rooms.get(String.valueOf(actualnode.x)+"-"+String.valueOf(actualnode.y));
				mydoor = new node(actualnode.xp,actualnode.yp);
				actualroom.addDoor(mydoor);
			} else {
				actualroom = new room();
				actualroom.setPostion(actualnode.x, actualnode.y);
				mydoor = new node(actualnode.xp,actualnode.yp);
				actualroom.addDoor(mydoor);
				// add a enemy 
				double hascreature = Math.random();
				//if (hascreature==1) actualroom.setCreatureLevel((int)((mazedistance/5)*Math.random()));
				if (hascreature>=0.2d) actualroom.setCreatureLevel((int)((actualnode.x+actualnode.y)/2));
				rooms.put(String.valueOf(actualnode.x)+"-"+String.valueOf(actualnode.y), actualroom);
			}
			// mark room inside the maze
			themaze[actualnode.getx()][actualnode.gety()]=1;
			// remove frontier node from list
			frontiers.remove(actualnode);
			// add new frontiers
			makeFrontiers(actualnode,frontiers,x,y);
			mazedistance++;
		}
		System.out.println("maze.maanlake.com:"+ "maze distance = "+String.valueOf(mazedistance));
		
	}
	
	private void makeFrontiers(node actualnode,Collection<node> frontiers,int x,int y) {
		//down
				if (actualnode.gety()-1>=0) {
					if (themaze[actualnode.getx()][actualnode.gety()-1]==0) {
						themaze[actualnode.getx()][actualnode.gety()-1]=2;
						node f = new node(actualnode.getx(),actualnode.gety()-1);
						f.prevNode(actualnode.x,actualnode.y);
						frontiers.add(f);
					}
				}
				//top
				if (actualnode.gety()+1<y) {
					if (themaze[actualnode.getx()][actualnode.gety()+1]==0) {
						themaze[actualnode.getx()][actualnode.gety()+1]=2;
						node f = new node(actualnode.getx(),actualnode.gety()+1);
						f.prevNode(actualnode.x,actualnode.y);
						frontiers.add(f);
					}
				}
				//left
				if (actualnode.getx()-1>=0) {
					if (themaze[actualnode.getx()-1][actualnode.gety()]==0) {
						themaze[actualnode.getx()-1][actualnode.gety()]=2;
						node f = new node(actualnode.getx()-1,actualnode.gety());
						f.prevNode(actualnode.x,actualnode.y);
						frontiers.add(f);
					}
				}
				//right
				if (actualnode.getx()+1<x) {
					if (themaze[actualnode.getx()+1][actualnode.gety()]==0) {
						themaze[actualnode.getx()+1][actualnode.gety()]=2;
						node f = new node(actualnode.getx()+1,actualnode.gety());
						f.prevNode(actualnode.x,actualnode.y);
						frontiers.add(f);
					}
				}
	}
	
}
