package simcity.restaurants;

import agent.Role;
import simcity.Building;

/**
 * This base class is necessary so we can hold all of our restaurants as similar objects, 
 * they are all very different
 * @author nikhil
 *
 */
public class Restaurant extends Building {

        protected Role host;
        protected Role cashier;
        
        public Restaurant(String type, int entranceX, int entranceY, int guiX, int guiY) {
                super(type, entranceX, entranceY, guiX, guiY);
        }
        
        public Restaurant(String type, int entranceX, int entranceY, int guiX, int guiY, String address) {
                super(type, entranceX, entranceY, guiX, guiY);
        }
        
        public void setHost(Role role) {
                this.host = host;
        }
        
        public Role getHost() {
                return host;
        }
        
        public void setCashier(Role role) {
                this.cashier = cashier;
        }
        
        public Role getCashier() {
                return cashier;
        }
        
       
}