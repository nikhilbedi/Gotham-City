//imports
package agent;

import simcity.PersonAgent;

public class Role {
        /**
         * Base class for simple roles
         */
        public Role(PersonAgent person){
                myPerson = person;
        }
        
        PersonAgent myPerson;
        
        public PersonAgent getPersonAgent() {
                return myPerson;
        }
        
        public void setPerson(PersonAgent a){
                myPerson = a;
        }
        
        private void stateChanged() {
                myPerson.stateChanged();
        }
        
        //Everyone needs to provide an @Override to their pickAndExecuteAnAction
        public boolean pickAndExecuteAnAction() {
                return true;
        }
}