package gui;

import exceptions.*;

public class YoungChild extends Child {

	public YoungChild(String name, String imagePath, String status, String sex, Integer age, String state,
			Adult MumParent, Adult DadParent) throws Exception {
		super(name, imagePath, status, sex, age, state, MumParent, DadParent);
	}

	public Boolean addfriend(Profile profile, Boolean isRelative) throws Exception {
		if (isRelative && this.getage() <= 2) {
			_friendlist.add(profile);
			return true;
		} else {
			throw new TooYoungException("Younger than two year old, can not have add any friend");
		}
	}

}
