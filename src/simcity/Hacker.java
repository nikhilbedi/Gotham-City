package simcity;

import java.lang.reflect.*;

public class Hacker {
	public Field[] getClassPrivates(){
		return null;
	}
	
	//current application for PersonAgent.java
	public Object getPrivateVariable(PersonAgent object, String item) {
		Class personClass = object.getClass();
		Field fields[] = personClass.getDeclaredFields();
		
		try {
			for(Field f : fields) {
				if(f.getName().equals(item)){
					f.setAccessible(true);
					return f.getDouble(f);
				}
			}
		}
		
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println("Illegal argument exists.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}