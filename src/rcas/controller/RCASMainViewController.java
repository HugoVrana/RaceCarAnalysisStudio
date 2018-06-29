package rcas.controller;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import rcas.model.MagicFormulaTireModel;
import rcas.model.RaceCar;
import rcas.model.TireModel;
import rcas.util.CorneringAnalyserUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;


public class RCASMainViewController {

    // region Variables
    @FXML
    private GridPane mainPane;

    @FXML
    private GridPane detailsPane;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private TabPane tabPaneCar;

    @FXML
    private TableView carsTableView;

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
    private Tab tabCar;

    @FXML
    private Tab tabAllCars;

    private ArrayList<RaceCar> raceCars = new ArrayList<>();

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

        BindCarsGrid();

        tabPaneCar.setVisible(false);
        btnAdd.setVisible(true);
        btnCancel.setVisible(false);
        btnSave.setVisible(false);
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

    private void BindCarsGrid() {
        for (RaceCar car : raceCars) {
            carsTableView.getItems().add(car);
        }
    }
    // endregion

    // region Event-Handlers
    @FXML
    public void btnAddClicked() {
        tabCar.setDisable(true);
        btnAdd.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
    }

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

            // tire models
            RaceCar raceCar = new RaceCar(cornerWeightFL, cornerWeightFR, cornerWeightRL, cornerWeightRR);
            raceCar.setName(txtName.getText());

            // front roll track
            raceCar.setFrontTrack(track);
            // rear roll track
            raceCar.setRearTrack(track);

            raceCar.setWheelbase(wheelbase);
            raceCar.setCogHeight(cogHeight);
            raceCar.setFrontRollDist(frontRollDist);

            raceCars.add(raceCar);
            detailsPane.setVisible(false);
            BindCarsGrid();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Saving unsuccessful");
            alert.showAndWait();
        }
    }

    @FXML
    private void btnCancelClicked() {
        txtName.setText("");
        txtWheelbase.setText("");
        txtCogHeight.setText("");
        txtTrack.setText("");
        txtFrontRollDist.setText("");
        txtCornerWeightFL.setText("");
        txtCornerWeightFR.setText("");
        txtCornerWeightRL.setText("");
        txtCornerWeightRR.setText("");
        txtFrontAxleTireModel.setText("");
        txtFrontAxleSlipAngel.setText("");
        txtFrontAxleLoad.setText("");
        txtRearAxleTireModel.setText("");
        txtRearAxleSlipAngel.setText("");
        txtRearAxleLoad.setText("");
    }

    @FXML
    private void carsTableViewMouseClicked(Event event) {
        Object carObj = carsTableView.getSelectionModel().getSelectedItem();
    }
    // endregion
}