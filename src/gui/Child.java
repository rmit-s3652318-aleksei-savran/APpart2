package gui;

////Author: Aleksey Savran

import java.util.HashSet;
import exceptions.*;
import java.util.Set;

////Dependent class inherits the profile and is used specifically for child objects

public class Child extends Profile {
	Adult _parent1;
	Adult _parent2;
	// int age;
	Set<Child> _classmate = new HashSet<>();

	public Child(String name, String imagePath, String status, String sex, Integer age, String state, Adult MumParent,
			Adult DadParent) throws Exception {
		super(name, imagePath, status, sex, age, state);

		this._parent1 = MumParent;
		this._parent2 = DadParent;

		if (MumParent == null || DadParent == null || !MumParent.getSpouse().equals(DadParent)) {
			throw new NoParentException("A Child can only be one couple dependent, including not only one parent");
		}
		try {
			this.addfriend(DadParent, true);
			this.addfriend(MumParent, true);

			this._parent1.addfriend(this, true);
			this._parent2.addfriend(this, true);
		} catch (Exception ex) {

		}
	}

	//// overriding addfreind method from profile class in here
	@Override
	public Boolean addfriend(Profile profile, Boolean isRelative) throws Exception {
		if (isRelative) {
			_friendlist.add(profile);
			return true;
		}

		// if (profile.getage() <= 2 && this.getage() <= 2) {
		// System.out.println("Younger than two year old, can not have any friend");
		// return false;
		// }

		/// to maintain the age difference condition

		int agediff = Math.abs(this.getage() - profile.getage());
		if (profile.getage() < 16 && agediff < 3) {
			_friendlist.add(profile);
		} else {
			throw new NotToBeFriendsException(
					"You are not allowed to add a friend more than 3 years older than yourself");
		}
		return true;
	}

	/// a set to maintain the list of parents of a dependent

	public Set<Profile> getRelatives() {
		Set<Profile> parents = new HashSet<>();
		parents.add(_parent1);
		parents.add(_parent2);
		return parents;
	}

	public String getMumname() {
		return _parent1.getname();

	}

	public String getDadname() {
		return _parent2.getname();

	}

	public void setclassmate(Set<Child> classmate) {
		_classmate = classmate;
	}

	public void addClassmate(Child Child) {
		if (this.getstatus() == Child.getstatus() && !this._classmate.contains(Child)) {
			_classmate.add(Child);
			Child.addClassmate(this);
		}
	}

	public Set<Child> getclassmate() {
		return _classmate;

	}
}
