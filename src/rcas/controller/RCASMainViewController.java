package rcas.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rcas.model.MagicFormulaTireModel;
import rcas.model.RaceCar;
import rcas.model.TireModel;
import rcas.util.CorneringAnalyserUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;


public class RCASMainViewController {
    private static ArrayList<RaceCar> raceCars = new ArrayList<>();
    @FXML
    private GridPane mainPane;

    @FXML
    private GridPane detailsPane;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
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
    private TextField txtFrontAxleSlipAngelC;
    @FXML
    private TextField txtFrontAxleSlipAngelB;
    @FXML
    private TextField txtFrontAxleSlipAngelE;
    @FXML
    private TextField txtFrontAxleLoadKA;
    @FXML
    private TextField txtFrontAxleLoadKB;
    @FXML
    private TextField txtRearAxleSlipAngelC;
    @FXML
    private TextField txtRearAxleSlipAngelB;
    @FXML
    private TextField txtRearAxleSlipAngelE;
    @FXML
    private TextField txtRearAxleLoadKA;
    @FXML
    private Label lblFrontAxleLateralTireForce_Value;
    @FXML
    private Label lblRearAxleLateralTireForce_Value;
    @FXML
    private TextField txtRearAxleLoadKB;
    @FXML
    private TabPane tabs;
    @FXML
    private Tab tabCar;
    @FXML
    private Tab tabAllCars;
    @FXML
    private ListView<RaceCar> lvCars;
    // endregion

    // region Methods
    @FXML
    public void initialize() {
        // create race cars and calculate a chart.
        RaceCar myRaceCar_1 = new RaceCar(1, 420, 2, 3);
        TireModel myTireModel_1 = new MagicFormulaTireModel();
        myRaceCar_1.setFrontRollDist(0.55);
        myRaceCar_1.setFrontAxleTireModel(myTireModel_1);
        myRaceCar_1.setRearAxleTireModel(myTireModel_1);
        myRaceCar_1.setName("Car STD (blue)");

        RaceCar myRaceCar_2 = new RaceCar(420, 5, 634, 370);
        TireModel myTireModel_2_Fr = new MagicFormulaTireModel(1.3, 15.2, -1.6, 1.6, 0.000075);
        TireModel myTireModel_2_Rr = new MagicFormulaTireModel(1.3, 15.2, -1.6, 1.8, 0.000075);
        myRaceCar_2.setFrontRollDist(0.55);
        myRaceCar_2.setFrontAxleTireModel(myTireModel_2_Fr);
        myRaceCar_2.setRearAxleTireModel(myTireModel_2_Rr);
        myRaceCar_2.setName("Car MOD (red)");

        CorneringAnalyserUtil corneringUtil = new CorneringAnalyserUtil();

        // show what the toString() methods print out.
        System.out.println(myRaceCar_1.toString());
        System.out.println(myRaceCar_2.toString());

        // show balance, grip, control and stability values of the cars.
        this.printRaceCarCorneringValues(myRaceCar_1, corneringUtil);
        this.printRaceCarCorneringValues(myRaceCar_2, corneringUtil);

        ObservableList<XYChart.Series<Number, Number>> dataList_1 = corneringUtil.generateMMMChartData(myRaceCar_1);
        mainChart.getData().addAll(dataList_1);
        // Set the style of the series after adding the data to the chart.
        // Otherwise no there is no reference "Node" which leads to a
        // NullPointerException.
        this.setSeriesStyle(dataList_1, ".chart-series-line", "-fx-stroke: blue; -fx-stroke-width: 1px;");

        ObservableList<XYChart.Series<Number, Number>> dataList_2 = corneringUtil.generateMMMChartData(myRaceCar_2);
        mainChart.getData().addAll(dataList_2);
        this.setSeriesStyle(dataList_2, ".chart-series-line", "-fx-stroke: red; -fx-stroke-width: 1px;");

        raceCars.add(myRaceCar_1);
        raceCars.add(myRaceCar_2);

        for (RaceCar car : raceCars) {
            lvCars.getItems().add(car);
        }

        setReadOnly(true);
        mainPane.setVisible(true);

        // set filters
        // setting textboxes only nummeric
        String regexOnlyNumbers = "[-+]?([0-9]*\\.[0-9]+|[0-9]+)";
        String regexChars = "[a-zA-Z]";

        ArrayList<TextField> nummericTextFields = new ArrayList<TextField>();
        nummericTextFields.add(txtWheelbase);
        nummericTextFields.add(txtTrack);
        nummericTextFields.add(txtCogHeight);
        nummericTextFields.add(txtFrontRollDist);
        nummericTextFields.add(txtCornerWeightFL);
        nummericTextFields.add(txtCornerWeightFR);
        nummericTextFields.add(txtCornerWeightRL);
        nummericTextFields.add(txtCornerWeightRR);
        nummericTextFields.add(txtFrontAxleLoad);
        nummericTextFields.add(txtFrontAxleLoadKA);
        nummericTextFields.add(txtFrontAxleLoadKB);
        nummericTextFields.add(txtFrontAxleSlipAngel);
        nummericTextFields.add(txtFrontAxleSlipAngelB);
        nummericTextFields.add(txtFrontAxleSlipAngelC);
        nummericTextFields.add(txtFrontAxleSlipAngelE);
        nummericTextFields.add(txtRearAxleLoad);
        nummericTextFields.add(txtRearAxleLoadKA);
        nummericTextFields.add(txtRearAxleLoadKB);
        nummericTextFields.add(txtRearAxleSlipAngel);
        nummericTextFields.add(txtRearAxleSlipAngelB);
        nummericTextFields.add(txtRearAxleSlipAngelC);
        nummericTextFields.add(txtRearAxleSlipAngelE);

        for (TextField txt : nummericTextFields) {
            txt.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches(regexOnlyNumbers)) {
                    txt.setText(newValue.replaceAll(regexChars, ""));
                }
            });
        }
    }

    private void setSeriesStyle(ObservableList<XYChart.Series<Number, Number>> dataList_1, String styleSelector, String lineStyle) {
        for (Iterator<XYChart.Series<Number, Number>> iterator = dataList_1.iterator(); iterator.hasNext(); ) {
            XYChart.Series<Number, Number> curve = iterator.next();
            curve.getNode().lookup(styleSelector).setStyle(lineStyle);
        }
    }

    private void printRaceCarCorneringValues(RaceCar raceCar, CorneringAnalyserUtil util) {
        System.out.println(String.format(
                "%s: Grip = %.2f G\tBalance = %.2f Nm\tControl(entry) = %.2f Nm/deg\t"
                        + "Control(middle) = %.2f Nm/deg\tStability(entry) = %.2f Nm/deg\t"
                        + "Stability(middle) = %.2f Nm/deg",
                raceCar.getName(), util.getMMMGripValue(raceCar) / 9.81, util.getMMMBalanceValue(raceCar),
                util.getMMMControlValue(raceCar, 0.0, 0.0, 10.0), util.getMMMControlValue(raceCar, -5.0, 20.0, 30.0),
                util.getMMMStabilityValue(raceCar, 0.0, 0.0, 1.0),
                util.getMMMStabilityValue(raceCar, 20.0, -5.0, -4.0)));
    }

    private void bindTabCar(RaceCar raceCar) {
        txtName.setText(raceCar.getName());
        txtTrack.setText(raceCar.getFrontTrack() + " " + raceCar.getRearTrack());
        txtWheelbase.setText(raceCar.getWheelbase().toString());
        txtCogHeight.setText(raceCar.getCogHeight().toString());
        txtFrontRollDist.setText(raceCar.getFrontRollDist().toString());
        txtCornerWeightFL.setText(raceCar.getCornerWeightFL().toString());
        txtCornerWeightFR.setText(raceCar.getCornerWeightFR().toString());
        txtCornerWeightRL.setText(raceCar.getCornerWeightRL().toString());
        txtCornerWeightRR.setText(raceCar.getCornerWeightRR().toString());

        // front tire configuration
        MagicFormulaTireModel frontTireModel = (MagicFormulaTireModel) raceCar.getFrontAxleTireModel();
        txtFrontAxleTireModel.setText(frontTireModel.getName());
        txtFrontAxleLoad.setText("0");
        txtFrontAxleSlipAngel.setText("0");
        txtFrontAxleSlipAngelB.setText(Double.toString(frontTireModel.getSlipAngleCoefficientB()));
        txtFrontAxleSlipAngelC.setText(Double.toString(frontTireModel.getSlipAngleCoefficientC()));
        txtFrontAxleSlipAngelE.setText(Double.toString(frontTireModel.getSlipAngleCoefficientE()));
        txtFrontAxleLoadKA.setText(Double.toString(frontTireModel.getLoadCoefficientKA()));
        txtFrontAxleLoadKB.setText(Double.toString(frontTireModel.getLoadCoefficientKB()));
        Double frontLateralTireForce = frontTireModel.getLateralTireForce(frontTireModel.getSlipAngleCoefficientB(), frontTireModel.getLoadCoefficientKA());
        lblFrontAxleLateralTireForce_Value.setText(frontLateralTireForce.toString());

        // rear tire configuration
        MagicFormulaTireModel rearTireModel = (MagicFormulaTireModel) raceCar.getRearAxleTireModel();
        txtRearAxleTireModel.setText(rearTireModel.getName());
        txtRearAxleLoad.setText("0");
        txtRearAxleSlipAngel.setText("0");
        txtRearAxleSlipAngelB.setText(Double.toString(rearTireModel.getSlipAngleCoefficientB()));
        txtRearAxleSlipAngelC.setText(Double.toString(rearTireModel.getSlipAngleCoefficientC()));
        txtRearAxleSlipAngelE.setText(Double.toString(rearTireModel.getSlipAngleCoefficientE()));
        txtRearAxleLoadKA.setText(Double.toString(rearTireModel.getLoadCoefficientKA()));
        txtRearAxleLoadKB.setText(Double.toString(rearTireModel.getLoadCoefficientKB()));
        Double rearLateralTireForce = rearTireModel.getLateralTireForce(rearTireModel.getSlipAngleCoefficientB(), rearTireModel.getLoadCoefficientKA());
        lblRearAxleLateralTireForce_Value.setText(rearLateralTireForce.toString());

        CorneringAnalyserUtil corneringAnalyserUtil = new CorneringAnalyserUtil();
        ObservableList<XYChart.Series<Number, Number>> dataList = corneringAnalyserUtil.generateMMMChartData(raceCar);
        mainChart.getData().addAll(dataList);
        this.setSeriesStyle(dataList, ".chart-series-line", "-fx-stroke: red; -fx-stroke-width: 1px;");
    }
    // endregion

    // region Event-Handlers
    @FXML
    public void btnAddClicked() {
        try {
            FXMLLoader e = new FXMLLoader(RCASMainViewController.class.getResource("../../RCASAddCarWindowView.fxml"));
            ResourceBundle resourceBundle = ResourceBundle.getBundle("RCASResources");
            e.setResources(resourceBundle);
            Pane mainPane = e.load();
            Scene mainScene = new Scene(mainPane, mainPane.getPrefWidth(), mainPane.getPrefHeight());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Add car");
            stage.setScene(mainScene);
            stage.show();
        } catch (Exception var6) {
            var6.printStackTrace();
        }
    }


    public void saveCar(RaceCar raceCar) {
        try {
            raceCars.add(raceCar);
//            lvCars.getItems().add(raceCar);

        } catch (Exception ex) {
            throw ex;
        } finally {
//            tabs.getSelectionModel().select(tabAllCars);
        }
    }

    @FXML
    private void btnCancelClicked() {
        lvCars.setSelectionModel(null);
        clearDetailView();
        tabs.getSelectionModel().select(tabAllCars);
        bindTabCar(new RaceCar(0, 0, 0, 0));
        setReadOnly(true);
        btnAdd.setVisible(true);
    }

    @FXML
    private void btnEditClicked() {
        setReadOnly(false);
        btnAdd.setVisible(false);
        btnEdit.setVisible(false);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(RCASMainViewController.class.getResource("../../RCASAddCarWindowView.fxml"));
            ResourceBundle resourceBundle = ResourceBundle.getBundle("RCASResources");
            fxmlLoader.setResources(resourceBundle);

            RCASSecondWindowController controller = fxmlLoader.getController();
            Pane mainPane = fxmlLoader.load();
            RaceCar selectedCar = lvCars.getSelectionModel().getSelectedItem();
            RCASSecondWindowController.raceCar = selectedCar;

            Scene mainScene = new Scene(mainPane, mainPane.getPrefWidth(), mainPane.getPrefHeight());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Edit car");
            stage.setScene(mainScene);
            stage.show();
        } catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    @FXML
    private void lvCars_OnMouseClicked(Event event) {
        RaceCar car = lvCars.getSelectionModel().getSelectedItem();
        bindTabCar(car);
        tabs.getTabs().add(tabCar);
        tabCar.setDisable(false);
        tabCar.getContent().setVisible(true);
        detailsPane.setVisible(true);
        setReadOnly(true);
    }

    @FXML
    private void tabCarClosed() {
        tabs.getSelectionModel().select(tabAllCars);
        tabs.getTabs().remove(tabCar);
        SimpleBooleanProperty b = new SimpleBooleanProperty();
        b.setValue(false);
    }

    private void clearDetailView() {
        RaceCar raceCar = new RaceCar(0.0, 0.0, 0.0, 0.0);
        txtName.setText(raceCar.getName());
        txtWheelbase.setText(raceCar.getWheelbase().toString());
        txtCogHeight.setText(raceCar.getCogHeight().toString());
        txtTrack.setText(raceCar.getFrontTrack().toString());
        txtFrontRollDist.setText(raceCar.getFrontRollDist().toString());
        txtCornerWeightFL.setText(raceCar.getCornerWeightFL().toString());
        txtCornerWeightFR.setText(raceCar.getCornerWeightFR().toString());
        txtCornerWeightRL.setText(raceCar.getCornerWeightRL().toString());
        txtCornerWeightRR.setText(raceCar.getCornerWeightRR().toString());

        MagicFormulaTireModel tireModel = new MagicFormulaTireModel();

        txtFrontAxleTireModel.setText(tireModel.getName());

        txtFrontAxleSlipAngel.setText("");
        txtFrontAxleLoad.setText("");
        txtFrontAxleSlipAngelB.setText(Double.toString(tireModel.getSlipAngleCoefficientB()));
        txtFrontAxleSlipAngelC.setText(Double.toString(tireModel.getSlipAngleCoefficientC()));
        txtFrontAxleSlipAngelE.setText(Double.toString(tireModel.getSlipAngleCoefficientE()));
        txtFrontAxleLoadKA.setText(Double.toString(tireModel.getLoadCoefficientKA()));
        txtFrontAxleLoadKB.setText(Double.toString(tireModel.getLoadCoefficientKB()));
        lblFrontAxleLateralTireForce_Value.setText(Double.toString(tireModel.getLateralTireForce(tireModel.getSlipAngleCoefficientB(), tireModel.getLoadCoefficientKB())));

        txtRearAxleTireModel.setText(tireModel.getName());
        txtRearAxleSlipAngel.setText("");
        txtRearAxleLoad.setText("");
        txtRearAxleSlipAngelB.setText(Double.toString(tireModel.getSlipAngleCoefficientB()));
        txtRearAxleSlipAngelC.setText(Double.toString(tireModel.getSlipAngleCoefficientC()));
        txtRearAxleSlipAngelE.setText(Double.toString(tireModel.getSlipAngleCoefficientE()));
        txtRearAxleLoadKA.setText(Double.toString(tireModel.getLoadCoefficientKA()));
        txtRearAxleLoadKB.setText(Double.toString(tireModel.getLoadCoefficientKB()));
        lblRearAxleLateralTireForce_Value.setText(Double.toString(tireModel.getLateralTireForce(tireModel.getSlipAngleCoefficientB(), tireModel.getLoadCoefficientKB())));
    }

    private void setReadOnly(Boolean readOnly) {
        txtName.setEditable(!readOnly);
        txtWheelbase.setEditable(!readOnly);
        txtCogHeight.setEditable(!readOnly);
        txtTrack.setEditable(!readOnly);
        txtFrontRollDist.setEditable(!readOnly);
        txtCornerWeightFL.setEditable(!readOnly);
        txtCornerWeightFR.setEditable(!readOnly);
        txtCornerWeightRL.setEditable(!readOnly);
        txtCornerWeightRR.setEditable(!readOnly);

        txtFrontAxleTireModel.setEditable(!readOnly);

        txtFrontAxleSlipAngel.setEditable(!readOnly);
        txtFrontAxleLoad.setEditable(!readOnly);
        txtFrontAxleSlipAngelB.setEditable(!readOnly);
        txtFrontAxleSlipAngelC.setEditable(!readOnly);
        txtFrontAxleSlipAngelE.setEditable(!readOnly);
        txtFrontAxleLoadKA.setEditable(!readOnly);
        txtFrontAxleLoadKB.setEditable(!readOnly);

        txtRearAxleTireModel.setEditable(!readOnly);
        txtRearAxleSlipAngel.setEditable(!readOnly);
        txtRearAxleLoad.setEditable(!readOnly);
        txtRearAxleSlipAngelB.setEditable(!readOnly);
        txtRearAxleSlipAngelC.setEditable(!readOnly);
        txtRearAxleSlipAngelE.setEditable(!readOnly);
        txtRearAxleLoadKA.setEditable(!readOnly);
        txtRearAxleLoadKB.setEditable(!readOnly);

        btnAdd.setVisible(readOnly);
        btnAdd.setManaged(readOnly);
        btnEdit.setVisible(readOnly);
        btnEdit.setManaged(readOnly);
    }
    // endregion
}