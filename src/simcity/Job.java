package simcity;

import simcity.PersonAgent.JobState;
import agent.Role;

public class Job {
		public JobState state = JobState.OffWork;
		int onWork = 8; // 8am
		int offWork = 17; // military hours - 17 == 5pm
		Role role;
		String type;
		Building workplace;
		int wage = 0;

		// Job Constructor
		public Job(Role r, Building w, int shift) {
			role = r;
			workplace = w;
			/*
			 * The workplace will have shift hours,
			 * which will dictate what hours this person works
			 */
		}

		//TODO this needs to be deleted
		public Job(Role r, String type, Building w) {
			role = r;
			this.type = type;
			workplace = w;
		}

		//TODO This is a stupid function and I plan on deleting it
		public void setJob(Role r, Building w) {
			role = r;
			workplace = w;
		}

	}