package rcas.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import rcas.model.MagicFormulaTireModel;
import rcas.model.RaceCar;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Nina on 29.06.18.
 */
public class RCASSecondWindowController {

    // region Variables
    @FXML
    private GridPane mainPane;

    @FXML
    private GridPane detailsPane;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private LineChart<Number, Number> mainChart;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTrack;

    @FXML
    private TextField txtWheelbase;

    @FXML
    private TextField txtCogHeight;

    @FXML
    private TextField txtFrontRollDist;

    @FXML
    private TextField txtCornerWeightFL;

    @FXML
    private TextField txtCornerWeightFR;

    @FXML
    private TextField txtCornerWeightRL;

    @FXML
    private TextField txtCornerWeightRR;

    @FXML
    private TextField txtFrontAxleTireModel;

    @FXML
    private TextField txtFrontAxleSlipAngel;

    @FXML
    private TextField txtFrontAxleLoad;

    @FXML
    private TextField txtRearAxleTireModel;

    @FXML
    private TextField txtRearAxleSlipAngel;

    @FXML
    private TextField txtRearAxleLoad;

    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        int i = 0;
    }

    public RaceCar raceCar;


    @FXML
    private void btnSaveClicked() {
        try {
            Double track;
            try {
                track = Double.parseDouble(txtTrack.getText());
            } catch (Exception ex) {
                track = Double.valueOf(-1);
            }

            Double wheelbase;
            try {
                wheelbase = Double.parseDouble(txtWheelbase.getText());
            } catch (Exception ex) {
                wheelbase = Double.valueOf(-1);
            }

            Double cogHeight;
            try {
                cogHeight = Double.parseDouble(txtCogHeight.getText());
            } catch (Exception ex) {
                cogHeight = Double.valueOf(-1);
            }

            Double frontRollDist;
            try {
                frontRollDist = Double.parseDouble(txtFrontRollDist.getText());
            } catch (Exception ex) {
                frontRollDist = Double.valueOf(-1);
            }

            // Advanced
            Double cornerWeightFL;
            try {
                cornerWeightFL = Double.parseDouble(txtCornerWeightFL.getText());
            } catch (Exception ex) {
                cornerWeightFL = Double.valueOf(-1);
            }

            Double cornerWeightFR;
            try {
                cornerWeightFR = Double.parseDouble(txtCornerWeightFR.getText());
            } catch (Exception ex) {
                cornerWeightFR = Double.valueOf(-1);
            }

            Double cornerWeightRL;
            try {
                cornerWeightRL = Double.parseDouble(txtCornerWeightRL.getText());
            } catch (Exception ex) {
                cornerWeightRL = Double.valueOf(-1);
            }

            Double cornerWeightRR;
            try {
                cornerWeightRR = Double.parseDouble(txtCornerWeightRR.getText());
            } catch (Exception ex) {
                cornerWeightRR = Double.valueOf(-1);
            }

            RaceCar raceCar = new RaceCar(cornerWeightFL, cornerWeightFR, cornerWeightRL, cornerWeightRR);
            raceCar.setName(txtName.getText());

            raceCar.setFrontTrack(track);
            raceCar.setRearTrack(track);

            raceCar.setWheelbase(wheelbase);
            raceCar.setCogHeight(cogHeight);
            raceCar.setFrontRollDist(frontRollDist);

            MagicFormulaTireModel frontAxleTireModel = (MagicFormulaTireModel) raceCar.getFrontAxleTireModel();
            raceCar.setFrontAxleTireModel(frontAxleTireModel);

            MagicFormulaTireModel rearAxleTireModel = (MagicFormulaTireModel) raceCar.getRearAxleTireModel();
            raceCar.setRearAxleTireModel(rearAxleTireModel);

//            RCASMainViewController mainViewController = new RCASMainViewController();
//            mainViewController.saveCar(raceCar);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Saving unsuccessful");
            alert.showAndWait();
        }
    }

    @FXML
    private void btnCancelClicked() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
//        txtName.setText("");
//        txtWheelbase.setText("");
//        txtCogHeight.setText("");
//        txtTrack.setText("");
//        txtFrontRollDist.setText("");
//        txtCornerWeightFL.setText("");
//        txtCornerWeightFR.setText("");
//        txtCornerWeightRL.setText("");
//        txtCornerWeightRR.setText("");
//        txtFrontAxleTireModel.setText("");
//        txtFrontAxleSlipAngel.setText("");
//        txtFrontAxleLoad.setText("");
//        txtRearAxleTireModel.setText("");
//        txtRearAxleSlipAngel.setText("");
//        txtRearAxleLoad.setText("");
    }

    @FXML
    private void carsTableViewMouseClicked(Event event) {
//        Object carObj = carsTableView.getSelectionModel().getSelectedItem();
    }
    // endregion

    }
