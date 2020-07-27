/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */


	private class Chopstick{
		public boolean pickedUp; //to determine whether the chopstick is picked up or not
		public int lastPickedUp; //to show who is the last one who picked up the chopstick

		public Chopstick(){
			pickedUp = false; //means the chopstick is still available
			lastPickedUp = 0; //no one picks up the chopstick
		}

		//to determine whether a philosopher (which is passed into parameters) is the
		//last one who picked up the chopstick
		public boolean lastPickedUpBy(final int TID){
			return lastPickedUp == TID;
		}

		//to determine whether the philosopher (is passed into parameters) is the one
		//who is picking up the chopstick
		public boolean pickedUpBy(final int TID){
			return (lastPickedUp != TID) && pickedUp;
		}

		//method that let the philosopher picks up the chopstick
		public void pickUp(final int TID){
			pickedUp = true;
			lastPickedUp = TID;
		}

		//method that let whoever philosopher puts
		public void putDown(){
			pickedUp = false;
		}

	}

	int numOfPhil; //number of philosophers
	boolean talking; //boolean variable to determine whether someone is talking
	Chopstick[] chopsticks;  //chopsticks on the table

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		numOfPhil = piNumberOfPhilosophers;
		chopsticks = new Chopstick[piNumberOfPhilosophers];
		for(int i= 0; i<chopsticks.length; i++){
			chopsticks[i] = new Chopstick();
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		// ...
		int leftChop = piTID -1; //the left-side chopstick of a philosopher
		int rightChop = (leftChop+1)%numOfPhil; //the right-side chopstick of a philosopher

		while (true){
			//if that philosopher just has only one chopstick, left or right
			if (chopsticks[leftChop].pickedUpBy(piTID) || chopsticks[rightChop].pickedUpBy(piTID)){

				//case 1, that philosopher does not have the left chopstick and left chopstick is not last picked up by that philosopher
				if (!chopsticks[leftChop].pickedUp && !chopsticks[leftChop].lastPickedUpBy(piTID)){
					chopsticks[leftChop].pickUp(piTID); //so that philosopher needs to pick up the left chopstick
				}

				//case 2: same as case 1 but with the right chopstick
				else if (!chopsticks[rightChop].pickedUpBy(piTID) && !chopsticks[rightChop].lastPickedUpBy(piTID)){
					chopsticks[rightChop].pickUp(piTID);
				}
			}

			else{ //that philosopher have both chopsticks, so he/she now can pick up them
				chopsticks[leftChop].pickUp(piTID);
				chopsticks[rightChop].pickUp(piTID);
				break;
			}

			//Else, force the philosopher to wait
			try{
				wait();
			}
			catch (InterruptedException e){
				System.err.println("Monitor.pickUp():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		// ...
		int leftChop = piTID-1;
		int rightChop = (leftChop+1)%numOfPhil;

		chopsticks[leftChop].putDown();
		chopsticks[rightChop].putDown();

		//let others know they are available
		notifyAll();
	}

	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
		while (talking){ //while someone is talking
			try{
				wait();
			}
			catch(InterruptedException e){
				System.err.println("Monitor.requestTalk():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}

		talking = true;

	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
		talking = false;
		notifyAll();
	}
}

// EOF
