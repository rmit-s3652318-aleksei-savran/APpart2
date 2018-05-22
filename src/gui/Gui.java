package gui;

import java.io.File;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import sql.*;
import exceptions.*;

public class Gui extends Application {
	private Driver _driver;
	private ObservableList<Profile> _names;
	private Profile _selectedProfile;
	private Button _btnDisplay;
	private Button _btnDelete;
	private Button _btnAdd;
	private Button _btnCompare;

	public Gui() {
		_driver = new Driver(new DataReader());
		_btnDisplay = new Button("Display");
		_btnDelete = new Button("Delete");
		_btnAdd = new Button("Add");
		_btnCompare = new Button("Compare");
		
		_names = FXCollections.<Profile>observableArrayList();
	}

	@Override // Override the start method from the superclass
	public void start(Stage primaryStage) throws Exception {
		GridPane root = new GridPane();

		root.add(_btnDisplay, 1, 1, 2, 2);
		root.add(_btnDelete, 1, 5, 2, 2);
		root.add(_btnAdd, 1, 10, 2, 2);
		root.add(_btnCompare, 1, 12, 2, 2);
		root.setAlignment(Pos.TOP_LEFT);

		Label searchLabel = new Label("search the profile");
		TextField searchText = new TextField();
		Button btnSearch = new Button("Search");
		root.add(searchLabel, 7, 1, 2, 2);
		root.add(searchText, 9, 1, 4, 2);
		root.add(btnSearch, 13, 1, 2, 2);

		_names.addAll(_driver.listProfiles());
		//_names.addAll(ShowAllUsers.userShowAll());
		ListView<Profile> lstNames = new ListView<>(_names);
		lstNames.setOrientation(Orientation.VERTICAL);
		lstNames.setPrefSize(300, 200);
		lstNames.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lstNames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profile>() {
			@Override
			public void changed(ObservableValue<? extends Profile> ov, final Profile oldvalue, final Profile newvalue) {
				// Allow selection of maximum two profiles.
				if (lstNames.getSelectionModel().getSelectedItems().size() > 2) {
					lstNames.getSelectionModel().clearSelection();
					lstNames.getSelectionModel().select(oldvalue);
					lstNames.getSelectionModel().select(newvalue);
				}
				_selectedProfile = newvalue;

				ChangeButtonStatus(lstNames.getSelectionModel().getSelectedItems().size());
			}
		});

		root.add(lstNames, 7, 3, 2, 2);

		root.setHgap(10);
		root.setVgap(10);

		setAddButtonAction(primaryStage);
		setSearchButtonAction(btnSearch, searchText);
		setDeleteButtonAction();
		setDisplayButtonAction(primaryStage);
		setCompareButtonAction(primaryStage, lstNames.getSelectionModel().getSelectedItems());
		
		root.setPadding(new Insets(30));
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setTitle("Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void ChangeButtonStatus(int numberOfProfilesSelected) {
		_btnDelete.setDisable(numberOfProfilesSelected > 1);
		_btnDisplay.setDisable(numberOfProfilesSelected > 1);
		_btnCompare.setDisable(numberOfProfilesSelected < 2);
	}

	private void setAddButtonAction(Stage primaryStage) {
		_btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage dialogStage = new Stage();
				dialogStage.initModality(Modality.APPLICATION_MODAL);
				dialogStage.initOwner(primaryStage);
				dialogStage.setTitle("Create a new profile");

				GridPane addPopupGrid = new GridPane();
				Label nameLabel = new Label("Name");
				TextField nameText = new TextField();
				Label ageLabel = new Label("Age");
				TextField ageText = new TextField();
				Label statusLabel = new Label("Status");
				TextField statusText = new TextField();
				Label sexLabel = new Label("Sex");
				TextField sexText = new TextField();
				Label stateLabel = new Label("State");
				TextField stateText = new TextField();

				addPopupGrid.add(nameLabel, 1, 1, 2, 2);
				addPopupGrid.add(nameText, 3, 1, 2, 2);
				addPopupGrid.add(ageLabel, 1, 3, 2, 2);
				addPopupGrid.add(ageText, 3, 3, 2, 2);
				addPopupGrid.add(statusLabel, 1, 5, 2, 2);
				addPopupGrid.add(statusText, 3, 5, 2, 2);
				addPopupGrid.add(sexLabel, 1, 7, 2, 2);
				addPopupGrid.add(sexText, 3, 7, 2, 2);
				addPopupGrid.add(stateLabel, 1, 9, 2, 2);
				addPopupGrid.add(stateText, 3, 9, 2, 2);
				

				Label momNameLabel = new Label("Mother's name");
				TextField momNameText = new TextField();

				addPopupGrid.add(momNameLabel, 1, 11, 2, 2);
				addPopupGrid.add(momNameText, 3, 11, 2, 2);

				Label dadNameLabel = new Label("Father's name");
				TextField dadNameText = new TextField();

				addPopupGrid.add(dadNameLabel, 1, 13, 2, 2);
				addPopupGrid.add(dadNameText, 3, 13, 2, 2);

				Button btnCreate = new Button("Create");
				Button btnCancel = new Button("Cancel");

				addPopupGrid.add(btnCreate, 1, 17, 2, 2);
				addPopupGrid.add(btnCancel, 3, 17, 2, 2);

				btnCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dialogStage.close();
					}
				});

				btnCreate.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Boolean success = false;
						try {
							int age = Integer.parseInt(ageText.getText());
							//success = _driver.createProfile(nameText.getText(), statusText.getText(), age);
							CreateQueries.createNewUser(nameText.getText(), statusText.getText(), sexText.getText(), age, stateText.getText());
							success = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (success) {
							dialogStage.close();
						}
					}
				});

				addPopupGrid.setAlignment(Pos.TOP_LEFT);

				addPopupGrid.setHgap(10);
				addPopupGrid.setVgap(10);

				Scene dialogScene = new Scene(addPopupGrid, 600, 400);
				dialogStage.setScene(dialogScene);
				dialogStage.show();
			}
		});
	}

	private void setSearchButtonAction(Button search, TextField searchText) {
		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Profile profile = _driver.searchProfile(searchText.getText());
				Profile profile;
				try {
					profile = SearchQueries.userSearch(searchText.getText());
					if (profile != null) {
						_names.add(profile);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	private void setDisplayButtonAction(Stage primaryStage) {
		_btnDisplay.setOnAction(new EventHandler<ActionEvent>() {
			private ImageView imv;

			@Override
			public void handle(ActionEvent event) {
				final Stage DisplayStage = new Stage();
				DisplayStage.initModality(Modality.APPLICATION_MODAL);
				DisplayStage.initOwner(primaryStage);
				DisplayStage.setTitle("Profile Display");

				GridPane DisplayPopupGrid = new GridPane();

				Label nameLabel = new Label("Name:");
				Label nameText = new Label(_selectedProfile.getname());
				Label ageLabel = new Label("Age:");
				Label ageText = new Label(_selectedProfile.getage() + "");
				Label statusLabel = new Label("Status:");
				Label statusText = new Label(_selectedProfile.getstatus());
				try {
					SearchQueries.userSearchImage(_selectedProfile.getname());
					//imv = null;
					Image image = new Image("Bilbo.jpg");
					imv = new ImageView(image);
					
					//Image image = new Image("data/" + _selectedProfile.get_imagePath());
					//Image image = new Image("data/Bilbo.jpg");
					//Image imageNF = new Image("data/noimagefound.jpg");
//					if (image != null) {
//						imv.setImage(image);
//					} 
//					else {
//						imv.setImage(imageNF);
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			    


				ObservableList<Profile> listoffriends = FXCollections
						.<Profile>observableArrayList(_selectedProfile.getfriendlist());

				ListView<Profile> _friendlist = new ListView<>(listoffriends);
				_friendlist.setOrientation(Orientation.VERTICAL);
				_friendlist.setPrefSize(300, 200);
				_friendlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profile>() {
					public void changed(ObservableValue<? extends Profile> ov, final Profile oldvalue,
							final Profile newvalue) {
						_selectedProfile = newvalue;
					}
				});

				DisplayPopupGrid.add(nameLabel, 1, 1, 2, 2);
				DisplayPopupGrid.add(nameText, 3, 1, 2, 2);
				DisplayPopupGrid.add(ageLabel, 1, 3, 2, 2);
				DisplayPopupGrid.add(ageText, 3, 3, 2, 2);
				DisplayPopupGrid.add(statusLabel, 1, 5, 2, 2);
				DisplayPopupGrid.add(statusText, 3, 5, 2, 2);
				DisplayPopupGrid.add(_friendlist, 1, 8, 2, 2);
				DisplayPopupGrid.add(imv, 1, 11, 2, 2);

				Scene DisplayScene = new Scene(DisplayPopupGrid, 600, 400);
				DisplayStage.setScene(DisplayScene);
				DisplayStage.show();
			}
		});
	}

	private void setCompareButtonAction(Stage primaryStage, ObservableList<Profile> selectedProfiles) {
		_btnCompare.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage DisplayStage = new Stage();
				DisplayStage.initModality(Modality.APPLICATION_MODAL);
				DisplayStage.initOwner(primaryStage);
				DisplayStage.setTitle("Profile Display");
				
				Profile firstProfile = selectedProfiles.get(0);
				
				GridPane compareGrid = new GridPane();

				Label header = new Label("Profile 1:");
				Label nameLabel = new Label("Name:");
				Label nameText = new Label(firstProfile.getname());
				Label ageLabel = new Label("Age:");
				Label ageText = new Label(firstProfile.getage() + "");
				Label statusLabel = new Label("Status:");
				Label statusText = new Label(firstProfile.getstatus());
				
				compareGrid.add(header, 1, 1, 2, 2);
				compareGrid.add(nameLabel, 1, 3, 2, 2);
				compareGrid.add(nameText, 3, 3, 2, 2);
				compareGrid.add(ageLabel, 1, 5, 2, 2);
				compareGrid.add(ageText, 3, 5, 2, 2);
				compareGrid.add(statusLabel, 1, 7, 2, 2);
				compareGrid.add(statusText, 3, 7, 2, 2);

				
				Profile secondProfile = selectedProfiles.get(1);
				
				Label secondProfileHeader = new Label("Profile 2:");
				Label secondProfileNameLabel = new Label("Name:");
				Label secondProfileNameText = new Label(secondProfile.getname());
				Label secondProfileAgeLabel = new Label("Age:");
				Label secondProfileAgeText = new Label(secondProfile.getage() + "");
				Label secondProfileStatusLabel = new Label("Status:");
				Label secondProfileStatusText = new Label(secondProfile.getstatus());
				
				compareGrid.add(secondProfileHeader, 8, 1, 2, 2);
				compareGrid.add(secondProfileNameLabel, 8, 3, 2, 2);
				compareGrid.add(secondProfileNameText, 10, 3, 2, 2);
				compareGrid.add(secondProfileAgeLabel, 8, 5, 2, 2);
				compareGrid.add(secondProfileAgeText, 10, 5, 2, 2);
				compareGrid.add(secondProfileStatusLabel, 8, 7, 2, 2);
				compareGrid.add(secondProfileStatusText, 10, 7, 2, 2);
				
				Label relationshipTypeLabel = new Label("Relationship Type:");
				Label relationshipTypeText = new Label(firstProfile.getRelationship(secondProfile));
				
				compareGrid.add(relationshipTypeLabel, 1, 12, 2, 2);
				compareGrid.add(relationshipTypeText, 3, 12, 2, 2);


				Scene DisplayScene = new Scene(compareGrid, 600, 400);
				DisplayStage.setScene(DisplayScene);
				DisplayStage.show();
			}
		});
	}

	private void setDeleteButtonAction() {
		_btnDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (_selectedProfile != null) {
						//_driver.DeleteProfile(_selectedProfile.getname());
						_names.remove(_selectedProfile);
						DeleteQuery.userDelete(_selectedProfile.getname());
						_selectedProfile = null;
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}