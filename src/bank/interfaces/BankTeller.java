package bank.interfaces;

<<<<<<< HEAD
=======
import simcity.interfaces.Person;

>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
public interface BankTeller {

	public abstract void setAvailable(boolean b);

	public abstract boolean isAvailable();

	public abstract void msgNeedATransaction(BankCustomer bankCustomer, String type, double amount);

	public abstract void msgDoneAndLeaving(BankCustomer bankCustomer);
<<<<<<< HEAD

	public abstract int getIndex();

	public abstract void setIndex(int i);
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
}
