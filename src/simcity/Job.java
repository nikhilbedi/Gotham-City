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
			/*weekDayOnWork = dayOpen;
			weekDayOffWork = (dayClose-dayOpen)/2 + dayOpen;
			weekEndOnWork = endOpen;
			weekEndOffWork = (endClose-endOpen)/2 + endOpen;*/
			weekDayOnWork = 2; // 8am
			weekDayOffWork = 13; // military hours - 17 == 5pm
			weekEndOnWork = 13;
			weekEndOffWork = 2;

			/*
			weekDayOnWork = w.weekdayOpen;
			weekDayOffWork = w.weekdayClose;
			weekEndOnWork = w.weekendOpen;
			weekEndOffWork = w.weekendClose;*/
		}
			//Never goes to work
			else if(shift == 2) {
				weekDayOnWork = 13; // 8am
				weekDayOffWork = 1; // military hours - 17 == 5pm
				weekEndOnWork = 1;
				weekEndOffWork = 13;
			/*	weekDayOnWork = (dayClose-dayOpen)/2 + dayOpen;
				weekDayOffWork = dayClose;
				weekEndOnWork = (endClose-endOpen)/2 + endOpen;
				weekEndOffWork = endClose;*/
			}
		}

		//Job Constructor without shifts
		//People who will spawn work all day every day
		public Job(Role r, Building w) {
			role = r;
			workplace = w;
			weekDayOnWork = 3; // 8am
			weekDayOffWork = 25; // military hours - 17 == 5pm
			weekEndOnWork = 3;
			weekEndOffWork = 25;
			/*weekDayOnWork = w.weekdayOpen;
			weekDayOffWork = w.weekdayClose;
			weekEndOnWork = w.weekendOpen;
			weekEndOffWork = w.weekendClose;*/
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