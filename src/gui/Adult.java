package gui;

import java.util.HashSet;
import exceptions.*;
import java.util.Set;

public class Adult extends Profile {

	Adult Spouse;
	Set<Adult> _colleague = new HashSet<>();

//	public Adult(String name, String status, int age) {
//		super(name, status, age);
//	}
	
	public Adult(String name, String imagePath, String status, String sex, Integer age, String state) {
		super(name, imagePath, status, sex, age, state);
	}


	protected Set<Child> _dependents = new HashSet<>();

	public Boolean addfriend(Profile profile, Boolean isRelative) throws Exception {
		if (isRelative || !(profile instanceof Child)) {
			System.out.println("Adding friend " + profile.getname());
			_friendlist.add(profile);
		} else {
			throw new NotToBeFriendsException("An Adult is not allowed to add a Child");

		}
		return true;
	}

	public boolean isParent() {
		return getRelatives().size() > 0;
	}

	public void marry(Adult adult) throws Exception {
		if ((adult.getSpouse() != null || Spouse != null) && !adult.getSpouse().getname().equals(this.getname())) {
			throw new NoAvailableException("This person is already married to someone else and is unavailble");
		} else {
			Spouse = adult;
		}
	}

	public Boolean isMarried() {
		return Spouse != null;
	}

	public Adult getSpouse() {
		return Spouse;
	}

	public Set<Profile> getRelatives() {
		Set<Profile> dependents = new HashSet<>();
		for (Profile friend : _friendlist) {
			if (friend instanceof Child || friend instanceof YoungChild) {
				dependents.add(friend);
			}
		}
		if (isMarried()) {
			dependents.add(Spouse);
		}
		return dependents;
	}

	public void setColleague(Set<Adult> colleague) {
		_colleague = colleague;
	}

	public void addColleague(Adult adult) {
		if (this.getstatus() == adult.getstatus() && !this._colleague.contains(adult)) {
			_colleague.add(adult);
			adult.addColleague(this);
		}
	}

}

