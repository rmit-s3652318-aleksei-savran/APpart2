package gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataReader {

	private BufferedReader breader;
	private ArrayList<Profile> profiles;

	public void setBreader(BufferedReader breader) {
		this.breader = breader;
	}

	public ArrayList<Profile> loadAdults() {

		try {
			setBreader(new BufferedReader(new FileReader("data/people.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		ArrayList<Profile> adults = new ArrayList<Profile>();

		try {
			String line = breader.readLine();
			while (line != null) {

				if (line.startsWith("#")) { // Skipping the comment lines
					line = breader.readLine();
					continue;
				}

				String[] tokens = line.split("\\|");
				String name = tokens[0];
				String imagePath = tokens[1];
				String status = tokens[2];
				String sex = tokens[3];
				String ageT = tokens[4];
				int age = Integer.parseInt(ageT);
				String state = tokens[5];

				if (age > 16) {
					adults.add(new Adult(name, imagePath, status, sex, age, state));
				}

				line = breader.readLine();

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return adults;

	}

//	public ArrayList<Profile> loadChildren() throws Exception {
//
//		try {
//			setBreader(new BufferedReader(new FileReader("data/people.txt")));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			return null;
//		}
//
//		ArrayList<Profile> children = new ArrayList<Profile>();
//
//		try {
//			String line = breader.readLine();
//			while (line != null) {
//
//				if (line.startsWith("#")) { // Skipping the comment lines
//					line = breader.readLine();
//					continue;
//				}
//
//				String[] tokens = line.split("\\|");
//				String name = tokens[0];
//				String imagePath = tokens[1];
//				String status = tokens[2];
//				String sex = tokens[3];
//				String ageT = tokens[4];
//				int age = Integer.parseInt(ageT);
//				String state = tokens[5];
//
//				if (age > 2 && age < 17) {
//					children.add(new Child(name, imagePath, status, sex, age, state, null, null));
//				}
//
//				line = breader.readLine();
//
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return children;
//
//	}

//	public ArrayList<Profile> loadKids() throws Exception {
//
//		try {
//			setBreader(new BufferedReader(new FileReader("data/people.txt")));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		ArrayList<Profile> kids = new ArrayList<Profile>();
//
//		try {
//			String line = breader.readLine();
//			while (line != null) {
//
//				if (line.startsWith("#")) { // Skipping the comment lines
//					line = breader.readLine();
//					continue;
//				}
//
//				String[] tokens = line.split("\\|");
//				String name = tokens[0];
//				String imagePath = tokens[1];
//				String status = tokens[2];
//				String sex = tokens[3];
//				String ageT = tokens[4];
//				int age = Integer.parseInt(ageT);
//				String state = tokens[5];
//
//				if (age < 3) {
//					kids.add(new YoungChild(name, imagePath, status, sex, age, state, null, null));
//				}
//
//				line = breader.readLine();
//
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//
//		return kids;

//	}

	public ArrayList<Profile> loadAllProfiles() throws Exception {

		try {
			setBreader(new BufferedReader(new FileReader("data/people.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		ArrayList<Profile> allProfiles = new ArrayList<Profile>();

		try {
			String line = breader.readLine();
			while (line != null) {

				if (line.startsWith("#")) { // Skipping the comment lines
					line = breader.readLine();
					continue;
				}

				String[] tokens = line.split("\\|");
				String name = tokens[0];
				String imagePath = tokens[1];
				String status = tokens[2];
				String sex = tokens[3];
				String ageT = tokens[4];
				int age = Integer.parseInt(ageT);
				String state = tokens[5];

				if (age > 16) {
					allProfiles.add(new Adult(name, imagePath, status, sex, age, state));
				} else if (age > 3) {
					//allProfiles.add(new Child(name, imagePath, status, sex, age, state, null, null));
				} else {
					//allProfiles.add(new YoungChild(name, imagePath, status, sex, age, state, null, null));
				}
				line = breader.readLine();

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return allProfiles;

	}

	public ArrayList<Profile> getAllProfiles() throws Exception {
		profiles = null;
		for (Profile a : loadAdults()) {
			profiles.add(a);
		}
		//for (Profile c : loadChildren()) {
		//	profiles.add(c);
		//}
		//for (Profile k : loadKids()) {
		//	profiles.add(k);
		//}
		return profiles;
	}

	public Profile searchProfile(String name) throws Exception {
		Profile searchingProfile = null;
		for (Profile p : getAllProfiles()) {
			if (p.getname().equals(name)) {
				searchingProfile = p;
			}
		}
		return searchingProfile;
	}
	
	public Adult searchAdultProfile(String name) throws Exception {
		Adult searchingProfile = null;
		for (Profile p : getAllProfiles()) {
			if (p.getname().equals(name)) {
				if(p instanceof Adult) {
					searchingProfile = new Adult(p.getname(), p.get_imagePath(), p.getstatus(), p.get_sex(), p.getage(), p.get_state());
				}
			}
		}
		return searchingProfile;
	}
	
	public Child searchChildProfile(String name) throws Exception {
		Child searchingProfile = null;
		for (Profile p : getAllProfiles()) {
			if (p.getname().equals(name)) {
				if(p instanceof Child) {
					searchingProfile = new Child(p.getname(), p.get_imagePath(), p.getstatus(), p.get_sex(), p.getage(), p.get_state(), null, null);
				}
			}
		}
		return searchingProfile;
	}
	
	public YoungChild searchYoungChildProfile(String name) throws Exception {
		YoungChild searchingProfile = null;
		for (Profile p : getAllProfiles()) {
			if (p.getname().equals(name)) {
				if(p instanceof YoungChild) {
					searchingProfile = new YoungChild(p.getname(), p.get_imagePath(), p.getstatus(), p.get_sex(), p.getage(), p.get_state(), null, null);
				}
			}
		}
		return searchingProfile;
	}
	
	public void SetSpouses() throws Exception {

		try {
			setBreader(new BufferedReader(new FileReader("data/relations.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}

		try {
			String line = breader.readLine();
			while (line != null) {

				if (line.startsWith("#")) { // Skipping the comment lines
					line = breader.readLine();
					continue;
				}

				String[] tokens = line.split("\\|");
				String name = tokens[0];
				String name2 = tokens[1];
				String relation = tokens[2];
				
				if (relation == "Couple") {
					for (Profile p : loadAdults()) {
						if (p.getname().equals(name)) {
							if (p instanceof Adult)
							((Adult) p).setSpouse(searchAdultProfile(name2)); 
							searchAdultProfile(name2).setSpouse((Adult) p);
							((Adult) p).addfriend(searchAdultProfile(name2), true); 
							searchAdultProfile(name2).addfriend(p, true);
						}
					}
				}

				line = breader.readLine();

			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	public void SetColleagues() throws Exception {

		try {
			setBreader(new BufferedReader(new FileReader("data/relations.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}

		try {
			String line = breader.readLine();
			while (line != null) {

				if (line.startsWith("#")) { // Skipping the comment lines
					line = breader.readLine();
					continue;
				}

				String[] tokens = line.split("\\|");
				String name = tokens[0];
				String name2 = tokens[1];
				String relation = tokens[2];
				
				if (relation == "Colleagues") {
					for (Profile p : loadAdults()) {
						if (p.getname().equals(name)) {
							if (p instanceof Adult)
							((Adult) p).addfriend(searchAdultProfile(name2), false);
							searchAdultProfile(name2).addfriend(p, false);
						}
					}
				}

				line = breader.readLine();

			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
//	public void SetChildren() throws Exception {
//
//		try {
//			setBreader(new BufferedReader(new FileReader("data/relations.txt")));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			
//		}
//
//		try {
//			String line = breader.readLine();
//			while (line != null) {
//
//				if (line.startsWith("#")) { // Skipping the comment lines
//					line = breader.readLine();
//					continue;
//				}
//
//				String[] tokens = line.split("\\|");
//				String name = tokens[0];
//				String name2 = tokens[1];
//				String relation = tokens[2];
//				
//				if (relation == "Dad") {
//					searchChildProfile(name2).set_parent2(searchAdultProfile(name));
//				} if (relation == "Mom") {
//					searchChildProfile(name2).set_parent1(searchAdultProfile(name));
//				}
//
//				line = breader.readLine();
//
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//		
//	}
	
	public void SetClassmates() throws Exception {//array of classmates needs to be added

		try {
			setBreader(new BufferedReader(new FileReader("data/relations.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}

		try {
			String line = breader.readLine();
			while (line != null) {

				if (line.startsWith("#")) { // Skipping the comment lines
					line = breader.readLine();
					continue;
				}

				String[] tokens = line.split("\\|");
				String name = tokens[0];
				String name2 = tokens[1];
				String relation = tokens[2];
				
				if (relation == "Dad") { // change to classmate relation
					searchChildProfile(name2).set_parent2(searchAdultProfile(name));
				} if (relation == "Mom") {
					searchChildProfile(name2).set_parent1(searchAdultProfile(name));
				}

				line = breader.readLine();

			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
}
