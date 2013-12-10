package simcity.restaurants.restaurant5.interfaces;


public interface Cook {
		public abstract String getName();
	
		//v2
		public abstract void hereIsAnOrder(Waiter w, String choice, int table);

		public abstract void notifyOrderAvailable();
		
		//v2.1
		//public abstract void hereIsFood(Market m, String s, int amount);
		
		//public abstract void hereIsSomeFood(Market m, String s, int amount);

}
