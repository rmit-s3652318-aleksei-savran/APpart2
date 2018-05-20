package gui;

////Author: Romina Sharifpour

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Set;
import java.io.BufferedReader;

////setting up the menu and all the options
public class MiniNet {
	public static void main(String[] args) {
		Boolean showMenu = true;
		Driver driver = new Driver(new DataReader());

		while (showMenu) {
			try {
				showMenu = createMenu(driver);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	private static Boolean createMenu(Driver driver) throws Exception {
		System.out.println();
		System.out.println("        MiniNet Menu           ");
		System.out.println("*******************************");
		System.out.print(" Please select an option from 0-8\r\n");
		System.out.println("");
		System.out.println("0. Add a profile to the network");
		System.out.println("1. List all the profiles");
		System.out.println("2. Look up a person");
		System.out.println("3. View the Person's profile");
		System.out.println("4. Are this profiles connected");
		System.out.println("5. Add a friend");
		System.out.println("6. Remove a friend");
		System.out.println("7. Show relatives");
		System.out.println("8. Exit the menu");
		System.out.println();
		System.out.println();

		// creating the menu
		BufferedReader menu = new BufferedReader(new InputStreamReader(System.in));

		try {

			int input = Integer.parseInt(menu.readLine());
			System.out.println("You have entered " + input + "\r\n");

			DataReader reader = new DataReader();
			Set<Profile> profiles;
			Profile profile = null;
			Profile profile2 = null;
			Collection<Profile> collection = reader.loadAllProfiles();

			String name;
			String name1;
			String name2;

			switch (input) {
			case 0:
				System.out.println("Enter the name:");
				name = menu.readLine();

				System.out.println("Enter the status:");
				String status = menu.readLine();

				System.out.println("Enter the age:");
				int age = Integer.parseInt(menu.readLine());

				Boolean success = false;
				if (age > 16) {
					success = driver.createProfile(name, status, age);
				} else {
					System.out.println("Enter the dad's name");
					String nameDad = menu.readLine();

					Profile parent1 = driver.searchProfile(nameDad);

					if (parent1 != null) {
						System.out.println("Enter the mom's name");
						String nameMom = menu.readLine();

						Profile parent2 = driver.searchProfile(nameMom);

						if (parent2 != null) {
							success = driver.createChild(name, status, age, (Adult) parent1, (Adult) parent2);
						} else {
							System.out.println("Profile " + nameMom + " doesn't exist");
						}
					} else {
						System.out.println("Profile " + nameDad + " doesn't exist");
					}
				}
				if (success) {
					System.out.println("Successfully created new profile");
				} else {
					System.out.println("This profile already exists");
				}
				break;

			case 1: //// listing all the profiles
				collection = driver.listProfiles();
				for (Profile p : collection) {
					System.out.println(p.toString());
				}
				break;

			case 2: //// Looking up a person in the network
				System.out.println("Enter the name:");
				name = menu.readLine();

				profile = driver.searchProfile(name);

				if (profile != null) {
					System.out.println(" ");
					System.out.println(profile.getname());
				} else {
					System.out.println(" ");
					System.out.println("Profile doesn't exist");
				}
				break;

			case 3:///// viewing a persons profile
				System.out.println("Enter the name:");
				name = menu.readLine();

				String friendlist = "";
				profile = driver.searchProfile(name);
				if (profile != null) {
					System.out.println(" ");
					System.out.println(profile.toString());
					friendlist = driver.Diplayfriendlist(name);

					if (!friendlist.equals("")) {
						System.out.println(friendlist);
					} else {
						System.out.println(" ");
						System.out.println(name + " has no friends");
					}

				} else {
					System.out.println(" ");
					System.out.println("Profile doesn't exist");
				}
				break;

			case 4:///// are the profile connected?
				System.out.println("Enter the first name");
				name1 = menu.readLine();

				profile = driver.searchProfile(name1);

				if (profile != null) {
					System.out.println("Enter the name of a friend");
					name2 = menu.readLine();

					profile2 = driver.searchProfile(name2);

					if (profile2 != null) {
						Boolean isConnected = driver.areProfilesConnected(profile, profile2);
						System.out.println(" ");
						System.out.println(profile.getname() + " and " + profile2.getname() + " are"
								+ (isConnected ? "" : " not") + " connected");
					} else {
						System.out.println(" ");
						System.out.println("Profile " + name2 + " doesn't exist");
					}
				} else {

					System.out.println("Profile " + name1 + " doesn't exist");
				}
				break;

			case 5://// Adding a profile to your network
				System.out.println("Enter your first name");
				name1 = menu.readLine();

				profile = driver.searchProfile(name1);
				if (profile != null) {

					System.out.println("Enter the first name of your friend");
					name2 = menu.readLine();

					profile2 = driver.searchProfile(name2);

					if (profile2 != null) {
						success = driver.AddFriend(profile, profile2);

						if (success) {
							friendlist = driver.Diplayfriendlist(name1);
							System.out.println(
									profile.getname() + " and " + profile2.getname() + " are friends now, congratz!");
						}
					} else {
						System.out.println(
								"this profile doesn't exists. You can only connect the prfoles exisiting in the network");
					}

				}

				else {
					System.out.println(
							"this profile doesn't exists. You can only connect the prfoles exisiting in the network");

				}
				break;

			case 6://// removing a friend from your network
				System.out.println("Enter your name");
				name1 = menu.readLine();

				profile = driver.searchProfile(name1);

				System.out.println("Enter the name of the friend you want remove");
				name2 = menu.readLine();

				profile2 = driver.searchProfile(name2);

				if (profile != null) {
					if (profile2 != null && profile.getfriendlist().contains(profile2)) {
						driver.removeFriend(profile, profile2);

						System.out.println(
								profile.getname() + " and " + profile2.getname() + " are not friends anymore, T_T");
					} else {
						System.out.println("Such profile doesn't exist in your friend list");
					}
				} else {
					System.out.println(
							"this profile doesn't exists. You can only search and remove the profiles existing in the network");

				}
				break;

			case 7://// showing one's parent and children
				System.out.println("Enter the first name");
				name1 = menu.readLine();

				profile = driver.searchProfile(name1);

				if (profile != null) {
					Set<Profile> relatives = driver.showRelatives(profile);
					if (!relatives.isEmpty()) {
						for (Profile relative : relatives) {
							Boolean isChild = relative instanceof Child;
							System.out.println((isChild ? "Child: " : "Parent: ") + relative.getname());
						}
					} else {
						System.out.println(name1 + " doesn't have any relatives in the network");
					}
				} else {
					System.out.println("Profile " + name1 + " doesn't exist");
				}
				break;

			case 8:///// exiting the menu
				System.out.println("You have exited the program\r\n");
				return false;
			default:
				System.out
						.println("You have entered an invalid selection, please choose from the following options\r\n");
				break;
			}

		} catch (IOException ioe) {
			System.out.println("IO error trying to read your input!\r\n");
			System.exit(1);
		}
		return true;
	}
}
