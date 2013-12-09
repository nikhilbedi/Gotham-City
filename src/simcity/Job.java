package simcity;

import simcity.PersonAgent.JobState;
import agent.Role;

public class Job {
		public JobState state = JobState.OffWork;
		int weekDayOnWork = 2; // 8am
		int weekDayOffWork = 24; // military hours - 17 == 5pm
		int weekEndOnWork = 2;
		int weekEndOffWork = 24;
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
			int dayOpen = workplace.getWeekdayOpen();
			int dayClose = workplace.getWeekdayClose();
			int endOpen = workplace.getWeekendOpen();
			int endClose = workplace.getWeekendClose();
			
			if(shift == 1) {
				weekDayOnWork = dayOpen;
				weekDayOffWork = (dayClose-dayOpen)/2 + dayOpen;
				weekEndOnWork = endOpen;
				weekEndOffWork = (endClose-endOpen)/2 + endOpen;
			}
			else if(shift == 2) {
				weekDayOnWork = (dayClose-dayOpen)/2 + dayOpen;
				weekDayOffWork = dayClose;
				weekEndOnWork = (endClose-endOpen)/2 + endOpen;
				weekEndOffWork = endClose;
			}
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