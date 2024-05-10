package com.example.lab4_mapv2;

import Domain.*;
import Exceptions.DuplicateEntityExceptions;
import Properties.SettingsReader;
import Repository.BinaryFileRepository;
import Repository.PacientBDRepo;
import Repository.ProgramareBDRepo;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.Repository;
import Service.Service;
import UI.Console;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HelloApplication extends Application {
    TextField idPacientTextField = new TextField();
    TextField numePacientTextField = new TextField();
    TextField prenumePacientTextField = new TextField();
    TextField varstaPacientTextField = new TextField();

    TextField idProgramareTextField = new TextField();
    TextField idPacientProgramareTextField = new TextField();
    TextField dataProgramareTextField = new TextField();
    TextField oraProgramareTextField = new TextField();
    TextField scopProgramareTextField = new TextField();

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException, DuplicateEntityExceptions {
        SettingsReader settingsReader = new SettingsReader("settings.properties");
        String repositoryType = settingsReader.getRepositoryType();
        String patientsFileName = settingsReader.getPatientsFileName();
        String appointmentsFileName = settingsReader.getAppointmentsFileName();
        System.out.println("Repository Type: " + repositoryType);
        System.out.println("Patients File: " + patientsFileName);
        System.out.println("Appointments File: " + appointmentsFileName);


        IRepository<Pacient> repo_pacient = null;
        IRepository<Programare> repo_programare = null;


        if (repositoryType.equals("binary"))
        {
            repo_pacient= new BinaryFileRepository<Pacient>(settingsReader.getPatientsFileName());
            repo_programare= new BinaryFileRepository<Programare>(settingsReader.getAppointmentsFileName());

        }
        if (repositoryType.equals("text"))
        {
            IEntityFactory<Pacient> pacientFactory=new PacientFactory();
            repo_pacient=new FileRepository<>(settingsReader.getPatientsFileName(),pacientFactory);
            IEntityFactory<Programare> programareFactory=new ProgramareFactory();
            repo_programare=new FileRepository<>(settingsReader.getAppointmentsFileName(),programareFactory);
        }
        if (repositoryType.equals("console"))
        {
            repo_pacient = new Repository<Pacient>();
            Pacient pacient1 = new Pacient(1,"Marinescu","Paul",19);
            repo_pacient.add_pacient(pacient1);
            Pacient pacient2 = new Pacient(2,"Nicola","Pavel",20);
            repo_pacient.add_pacient(pacient2);
            Pacient pacient3 = new Pacient(3,"Popescu","Eduard",19);
            repo_pacient.add_pacient(pacient3);
            Pacient pacient4 = new Pacient(4,"Olteanu","Ana",20);
            repo_pacient.add_pacient(pacient4);
            Pacient pacient5 = new Pacient(5,"Candrea","Maria",21);
            repo_pacient.add_pacient(pacient5);


            repo_programare = new Repository<Programare>();
            repo_programare.add_pacient(new Programare(1,(pacient1.getId()),"10/10/2023", "10:00", "Migrene"));
            repo_programare.add_pacient(new Programare(2,pacient2.getId(),"20/07/2023", "10:00", "EKG"));
            repo_programare.add_pacient(new Programare(3,pacient3.getId(),"5/02/2023", "10:00", "Depresie"));
            repo_programare.add_pacient(new Programare(4,pacient4.getId(),"18/05/2023", "10:00", "Evaluare"));
            repo_programare.add_pacient(new Programare(5,pacient5.getId(),"3/12/2023", "10:00", "Ghips picior"));

        }
        if(repositoryType.equals("database"))
        {
            repo_pacient= new PacientBDRepo();
            repo_programare= new ProgramareBDRepo();

        }

        Service service = new Service(repo_pacient, repo_programare);

        HBox mainHorizontalBox = new HBox();
        mainHorizontalBox.setPadding(new Insets(10));

        VBox pacientiVerticalBox = new VBox();
        pacientiVerticalBox.setPadding(new Insets(10));
        mainHorizontalBox.getChildren().add(pacientiVerticalBox);

        ObservableList<Pacient> pacienti = FXCollections.observableArrayList(service.getAll_pacient());
        ObservableList<Programare> programari = FXCollections.observableArrayList(service.getAll_programare());
        ListView<Pacient> pacientiListView = new ListView<>();
        pacientiListView.setPrefWidth(500);
        pacientiListView.setPrefHeight(500);

        pacientiListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Pacient pacient = pacientiListView.getSelectionModel().getSelectedItem();
                idPacientTextField.setText(Integer.toString(pacient.getId()));
                numePacientTextField.setText(pacient.getNume());
                prenumePacientTextField.setText(pacient.getPrenume());
                varstaPacientTextField.setText(Integer.toString(pacient.getVarsta()));
            }
        });
        pacientiVerticalBox.getChildren().add(pacientiListView);


        GridPane pacientiGridPane = new GridPane();

        Label idPacientLabel = new Label();
        idPacientLabel.setText("ID: ");
        idPacientLabel.setPadding(new Insets(10, 5, 10, 0));

        Label numePacientLable = new Label();
        numePacientLable.setText("Nume: ");
        numePacientLable.setPadding(new Insets(10, 5, 10, 0));

        Label prenumePacientLable = new Label();
        prenumePacientLable.setText("Prenume: ");
        prenumePacientLable.setPadding(new Insets(10, 5, 10, 0));

        Label varstaPacientLable = new Label();
        varstaPacientLable.setText("Varsta: ");
        varstaPacientLable.setPadding(new Insets(10, 5, 10, 0));

        pacientiGridPane.add(idPacientLabel, 0, 0);
        pacientiGridPane.add(idPacientTextField, 1, 0);

        pacientiGridPane.add(numePacientLable, 0, 1);
        pacientiGridPane.add(numePacientTextField, 1, 1);

        pacientiGridPane.add(prenumePacientLable, 0, 2);
        pacientiGridPane.add(prenumePacientTextField, 1, 2);

        pacientiGridPane.add(varstaPacientLable, 0, 3);
        pacientiGridPane.add(varstaPacientTextField, 1, 3);

        pacientiVerticalBox.getChildren().add(pacientiGridPane);

        HBox pacientButtonsHorizontalBox = new HBox();
        pacientiVerticalBox.getChildren().add(pacientButtonsHorizontalBox);

        Button addPacientButton = new Button("Add pacient");
        addPacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idPacientTextField.getText());
                    String nume = numePacientTextField.getText();
                    String prenume = prenumePacientTextField.getText();
                    int varsta = Integer.parseInt(varstaPacientTextField.getText());

                    service.add_pacient(id, nume, prenume, varsta);
                    pacienti.setAll(service.getAll_pacient());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        pacientButtonsHorizontalBox.getChildren().add(addPacientButton);


        Button deletePacientButton = new Button("Delete pacient");
        deletePacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idPacientTextField.getText());

                    service.remove_pacient(id);
                    pacienti.setAll(service.getAll_pacient());
                    programari.setAll(service.getAll_programare());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        pacientButtonsHorizontalBox.getChildren().add(deletePacientButton);

        Button updatePacientButton = new Button("Update pacient");
        updatePacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idPacientTextField.getText());
                    String nume = numePacientTextField.getText();
                    String prenume = prenumePacientTextField.getText();
                    int varsta = Integer.parseInt(varstaPacientTextField.getText());

                    service.update_pacient(id, nume, prenume, varsta);
                    pacienti.setAll(service.getAll_pacient());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        pacientButtonsHorizontalBox.getChildren().add(updatePacientButton);


/// ---------------- GUI Programari ----------------------
        VBox programariVerticalBox = new VBox();
        programariVerticalBox.setPadding(new Insets(10));
        mainHorizontalBox.getChildren().add(programariVerticalBox);

//        ObservableList<Programare> programari = FXCollections.observableArrayList(service.getAllProgramari());
        ListView<Programare> programariListView = new ListView<>();
        programariListView.setPrefWidth(500);
        programariListView.setPrefHeight(500);
        programariListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Programare programare = programariListView.getSelectionModel().getSelectedItem();
                idProgramareTextField.setText(Integer.toString(programare.getId()));
                idPacientProgramareTextField.setText(Integer.toString(programare.getPacient()));
                dataProgramareTextField.setText(programare.getData());
                oraProgramareTextField.setText(programare.getOra());
                scopProgramareTextField.setText(programare.getScop());
            }
        });
        programariVerticalBox.getChildren().add(programariListView);


        GridPane programariGridPane = new GridPane();

        Label idProgramareLabel = new Label();
        idProgramareLabel.setText("ID: ");
        idProgramareLabel.setPadding(new Insets(10, 5, 10, 0));

        Label idPacientProgramareLable = new Label();
        idPacientProgramareLable.setText("ID pacient: ");
        idPacientProgramareLable.setPadding(new Insets(10, 5, 10, 0));

        Label dataProgramareLable = new Label();
        dataProgramareLable.setText("Data: ");
        dataProgramareLable.setPadding(new Insets(10, 5, 10, 0));

        Label oraProgramareLable = new Label();
        oraProgramareLable.setText("Ora: ");
        oraProgramareLable.setPadding(new Insets(10, 5, 10, 0));

        Label scopProgramareLable = new Label();
        scopProgramareLable.setText("Scop: ");
        scopProgramareLable.setPadding(new Insets(10, 5, 10, 0));

        programariGridPane.add(idProgramareLabel, 0, 0);
        programariGridPane.add(idProgramareTextField, 1, 0);

        programariGridPane.add(idPacientProgramareLable, 0, 1);
        programariGridPane.add(idPacientProgramareTextField, 1, 1);

        programariGridPane.add(dataProgramareLable, 0, 2);
        programariGridPane.add(dataProgramareTextField, 1, 2);

        programariGridPane.add(oraProgramareLable, 0, 3);
        programariGridPane.add(oraProgramareTextField, 1, 3);

        programariGridPane.add(scopProgramareLable, 0, 4);
        programariGridPane.add(scopProgramareTextField, 1, 4);

        programariVerticalBox.getChildren().add(programariGridPane);

        HBox programareButtonsHorizontalBox = new HBox();
        programariVerticalBox.getChildren().add(programareButtonsHorizontalBox);

        Button addProgramareButton = new Button("Add programare");
        addProgramareButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idProgramareTextField.getText());
                    int id_pacient = Integer.parseInt(idPacientProgramareTextField.getText());
                    String data = dataProgramareTextField.getText();
                    String ora = oraProgramareTextField.getText();
                    String scop = scopProgramareTextField.getText();

                    service.add_programare(id, id_pacient, data, ora, scop);
                    programari.setAll(service.getAll_programare());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        programareButtonsHorizontalBox.getChildren().add(addProgramareButton);


        Button deleteProgramareButton = new Button("Delete programare");
        deleteProgramareButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idProgramareTextField.getText());

                    service.remove_programare(id);
                    programari.setAll(service.getAll_programare());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        programareButtonsHorizontalBox.getChildren().add(deleteProgramareButton);


        Button updateProgramareButton = new Button("Update programare");
        updateProgramareButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idProgramareTextField.getText());
                    int id_pacient = Integer.parseInt(idPacientProgramareTextField.getText());
                    String data = dataProgramareTextField.getText();
                    String ora = oraProgramareTextField.getText();
                    String scop = scopProgramareTextField.getText();

                    service.update_programare(id, id_pacient, data, ora, scop);
                    programari.setAll(service.getAll_programare());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        programareButtonsHorizontalBox.getChildren().add(updateProgramareButton);


        pacientiListView.setItems(pacienti);
        programariListView.setItems(programari);
        Scene scene = new Scene(mainHorizontalBox, 1000, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}