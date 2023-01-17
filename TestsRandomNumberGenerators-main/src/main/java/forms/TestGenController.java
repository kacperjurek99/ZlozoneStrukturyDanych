package forms;

import Sample.NumberSample;
import generators.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import testsGen.ParamsTest;
import testsGen.TestsSample;

import java.io.*;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;

public class TestGenController {

    public ComboBox<String> loadComboBox;
    public ComboBox<String> genNumComboBox;
    public ComboBox<String> initNumComboBox;
    public Spinner<Integer> capacityNumSpinner;
    public Spinner<Integer> initNumberSpinner;
    public Spinner<Integer> sizeSampleSpinner;
    public Spinner<Integer> numberSamplesSpinner;
    public Tab mainTab;
    public Label loadLabel;
    public Label genLabel;
    public Label sizeLabel;
    public Label initNumLabel;
    public Label capacityLabel;
    public Label numberSamplesLabel;
    public Label initNumberLabel;
    public TextArea resTextArea;
    public Tab graphicTab;
    public Canvas graphTestCanvas;
    public StackedBarChart diagramBarChart;
    public Tab diagramTab;
    public Tab resTab;
    public TextField fileTextField;
    public Button chooseButton;
    public Button saveFileButton;
    public Button mainButton;
    public ListView<Integer> randListView;
    public Button testButton;
    public Label infoLabel;
    public CheckBox loadCheckBox;
    public CategoryAxis intervalCategoryAxis;
    public NumberAxis chastotaNumberAxis;
    public Tab graphicDopTab;
    public Canvas graphDopTestCanvas;
    public AnchorPane grafDopAnchorPane;
    public Label resLabel;
    public Label resProcLabel;
    public Label aLabel;
    public Spinner<Double> aSpinner;
    private File fileObject;
    private ChangeListener<Integer> changeL;
    private int k = 0;
    private NumberSample numberSample;
    private ChangeListener<Boolean> checkL;

    @FXML
    public void initialize() {
        loadComboBox.getItems().addAll("Program", "Plik");
        loadComboBox.getSelectionModel().select("Program");
        genNumComboBox.getItems().addAll("Algorytm_GOST_ISO_24153", "Generator GFSR z 3 parametrami",
                "Generator GFSR z 5 parametrami", "Generator_Tausworth", "Generator_Twister", "LCG_generator_V1",
                "LCG_generator_V2", "Multiplicative_generator_V1", "Multiplicative_generator_V2", "Multiplicative_generator_V3", "Multiplicative_generator_V4", "Random_geneгator", "SecureRandom_generator",
                "SplittableRandom_generator");
        genNumComboBox.getSelectionModel().select("LCG_generator_V1");
        initNumComboBox.getItems().addAll("Ręcznie", "Generator nasion", "Domyślna wartość");
        initNumComboBox.getSelectionModel().select("Ręcznie");
        SpinnerValueFactory<Integer> capacityNumValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 31, 31);
        capacityNumSpinner.setValueFactory(capacityNumValue);
        SpinnerValueFactory<Integer> sizeSampleValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(100, Integer.MAX_VALUE, 131072, 2);
        sizeSampleSpinner.setValueFactory(sizeSampleValue);
        SpinnerValueFactory<Integer> numberSamplesValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4096, 100);
        numberSamplesSpinner.setValueFactory(numberSamplesValue);
        SpinnerValueFactory<Integer> initNumberValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 19660809);
        initNumberSpinner.setValueFactory(initNumberValue);
        SpinnerValueFactory<Double> aSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.001, 0.05, 0.01, 0.01);
        aSpinner.setValueFactory(aSpinnerValue);
        genLabel.setVisible(true);
        genNumComboBox.setVisible(true);
        initNumLabel.setVisible(true);
        initNumComboBox.setVisible(true);
        capacityLabel.setVisible(true);
        capacityNumSpinner.setVisible(true);
        sizeLabel.setVisible(true);
        sizeSampleSpinner.setVisible(true);
        numberSamplesLabel.setVisible(true);
        numberSamplesSpinner.setVisible(true);
        initNumberLabel.setVisible(true);
        initNumberSpinner.setVisible(true);
        fileTextField.setVisible(false);
        chooseButton.setVisible(false);
        testButton.setDisable(true);
    }

    public void chooseButtonHandler(ActionEvent actionEvent) {
        fileTextField.clear();
        Node source = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        fileChooser.setTitle("Wybierz przykładowy plik");
        fileObject = fileChooser.showOpenDialog(primaryStage);
        try {
            fileTextField.setText(fileObject.getPath());
            Scanner sc = new Scanner(fileObject);
            k = 0;
            if (sc.hasNextInt()) {
                while (sc.hasNextInt()) {
                    k++;
                    sc.nextInt();
                }
            } else if (sc.hasNextDouble()) {
                while (sc.hasNextDouble()) {
                    k++;
                    sc.nextDouble();
                }
            }
            SpinnerValueFactory<Integer> sizeSampleValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, k, k, 2);
            sizeSampleSpinner.setValueFactory(sizeSampleValue);
            numberSamplesSpinner.getValueFactory().setValue(1);
            loadCheckBox.setDisable(false);
            mainButton.setDisable(false);
            numberSamplesSpinner.setDisable(false);
            sizeSampleSpinner.setDisable(false);
            testButton.setDisable(true);
            sc.close();
        } catch (Exception e) {
            loadCheckBox.setSelected(false);
            loadCheckBox.setDisable(true);
            capacityNumSpinner.setVisible(false);
            capacityLabel.setVisible(false);
            mainButton.setDisable(true);
            numberSamplesSpinner.setDisable(true);
            sizeSampleSpinner.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Błąd wyboru pliku!");
            alert.setContentText("Wybierz plik!");
            alert.showAndWait();
        }
    }

    public void loadComboBoxHandler(ActionEvent actionEvent) {
        if (loadComboBox.getSelectionModel().getSelectedItem().equals("Program")) {
            numberSamplesSpinner.valueProperty().removeListener(changeL);
            loadCheckBox.selectedProperty().removeListener(checkL);
            SpinnerValueFactory<Integer> sizeSampleValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(100, Integer.MAX_VALUE, 131072, 2);
            sizeSampleSpinner.setValueFactory(sizeSampleValue);
            SpinnerValueFactory<Integer> numberSamplesValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4096, 100);
            numberSamplesSpinner.setValueFactory(numberSamplesValue);
            loadCheckBox.setVisible(true);
            loadCheckBox.setDisable(false);
            loadCheckBox.setSelected(false);
            loadCheckBox.setText("Ścieżka pliku \"testSample.txt\" na pulpicie");
            genLabel.setVisible(true);
            genNumComboBox.setVisible(true);
            initNumLabel.setVisible(true);
            initNumComboBox.setVisible(true);
            capacityLabel.setVisible(true);
            capacityNumSpinner.setVisible(true);
            sizeLabel.setVisible(true);
            sizeSampleSpinner.setVisible(true);
            numberSamplesLabel.setVisible(true);
            numberSamplesSpinner.setVisible(true);
            mainButton.setDisable(false);
            aLabel.setDisable(true);
            aSpinner.setDisable(true);
            if (initNumComboBox.getSelectionModel().getSelectedItem().equals("Ręcznie")) {
                initNumberLabel.setVisible(true);
                initNumberSpinner.setVisible(true);
            } else {
                initNumberLabel.setVisible(false);
                initNumberSpinner.setVisible(false);
            }
            infoLabel.setText("Wygenerowane liczby losowe:");
            capacityLabel.setText("Głębia bitowa generowanych liczb:");
            numberSamplesSpinner.setDisable(false);
            sizeSampleSpinner.setDisable(false);
            testButton.setDisable(true);
            fileTextField.setVisible(false);
            chooseButton.setVisible(false);
            randListView.getItems().clear();
        } else if (loadComboBox.getSelectionModel().getSelectedItem().equals("Plik")) {
            checkL = (observable, oldValue, newValue) -> {
                if (newValue) {
                    capacityLabel.setVisible(true);
                    capacityNumSpinner.setVisible(true);
                } else {
                    capacityLabel.setVisible(false);
                    capacityNumSpinner.setVisible(false);
                }
            };
            loadCheckBox.selectedProperty().addListener(checkL);
            loadCheckBox.setText("Ładowanie liczb rzeczywistych od 0 do 1 ");
            loadCheckBox.setDisable(true);
            loadCheckBox.setSelected(false);
            numberSamplesSpinner.getValueFactory().setValue(1);
            infoLabel.setText("Załadowane liczby losowe:");
            capacityLabel.setText("Głębia bitowa załadowanych liczb:");
            genLabel.setVisible(false);
            genNumComboBox.setVisible(false);
            initNumLabel.setVisible(false);
            initNumComboBox.setVisible(false);
            capacityLabel.setVisible(false);
            capacityNumSpinner.setVisible(false);
            initNumberLabel.setVisible(false);
            initNumberSpinner.setVisible(false);
            mainButton.setDisable(true);
            sizeLabel.setVisible(true);
            sizeSampleSpinner.setVisible(true);
            numberSamplesLabel.setVisible(true);
            numberSamplesSpinner.setVisible(true);
            fileTextField.setVisible(true);
            chooseButton.setVisible(true);
            testButton.setDisable(true);
            numberSamplesSpinner.setDisable(true);
            sizeSampleSpinner.setDisable(true);
            aLabel.setDisable(true);
            aSpinner.setDisable(true);
            randListView.getItems().clear();
            changeL = (obs, oldValue, newValue) -> {
                int res;
                if ((res = newValue - 1) > 0) {
                    SpinnerValueFactory<Integer> sizeSampleValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, k / (res + 1), k / (res + 1), 2);
                    sizeSampleSpinner.setValueFactory(sizeSampleValue);
                } else if ((res = newValue - 1) <= 0) {
                    res = Math.abs(res);
                    SpinnerValueFactory<Integer> sizeSampleValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, k * (res + 1), k * (res + 1), 2);
                    sizeSampleSpinner.setValueFactory(sizeSampleValue);
                }
            };
            numberSamplesSpinner.valueProperty().addListener(changeL);
        }
    }

    public void initNumComboBoxHandler(ActionEvent actionEvent) {
        if (initNumComboBox.getSelectionModel().getSelectedItem().equals("Ręcznie")) {
            initNumberLabel.setVisible(true);
            initNumberSpinner.setVisible(true);
        } else {
            initNumberLabel.setVisible(false);
            initNumberSpinner.setVisible(false);
        }
    }

    public void saveFileButtonHandler(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.setInitialFileName("resultTests");
        fileChooser.setTitle("Wybierz miejscie zapisania pliku");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("All files", "*.*"));
        File fileSave = fileChooser.showSaveDialog(primaryStage);
        try (PrintWriter pw = new PrintWriter(fileSave)) {
            pw.println(resLabel.getText());
            pw.println(resProcLabel.getText());
            pw.println();
            pw.println(resTextArea.getText());
            pw.flush();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Błąd zapisu pliku!");
            alert.setContentText("Spróbuj ponownie!");
            alert.showAndWait();
        }
    }

    public void mainButtonHandler(ActionEvent actionEvent) throws IOException {
        int size = 5000000;
        randListView.getItems().clear();
        ObservableList<Integer> numsRand;
        if (loadComboBox.getSelectionModel().getSelectedItem().equals("Program")) {
            if ((capacityNumSpinner.getValue() != null) && (sizeSampleSpinner.getValue() != null) &&
                    (numberSamplesSpinner.getValue() != null)) {
                testButton.setDisable(false);
                PrintWriter pw = null;
                if (loadCheckBox.isSelected()) {
                    pw = new PrintWriter(new FileWriter(System.getProperty("user.home") + "/Desktop/testSample.txt"));
                }
                numsRand = FXCollections.observableArrayList();
                numberSample = new NumberSample(numberSamplesSpinner.getValue(), sizeSampleSpinner.getValue());
                numberSample.setCapacity(capacityNumSpinner.getValue());
                switch (genNumComboBox.getSelectionModel().getSelectedItem()) {
                    case "Algorytm_GOST_ISO_24153" -> {
                        AlgGostPISO24153 algGostPISO24153 = new AlgGostPISO24153();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    algGostPISO24153.setSeed(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas wypełniania pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wprowadzania");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator nasion":
                                try {
                                    algGostPISO24153.setSeed(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd generowania nasion!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Domyślna wartość":
                                algGostPISO24153.setSeed(19660809);
                                break;
                        }
                        algGostPISO24153.setSeed2(algGostPISO24153.getSeed());//rng параметры функции начального числа
                        algGostPISO24153.setIJ(-1);//rng парамметры функциии инициализации
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, (int) Math.floor(algGostPISO24153.U() *
                                        (int) Math.pow(2, numberSample.getCapacity())));//масштабированный выход(1:nn)
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, załadowana tylko do tablicy");
                        }
                    }
                    case "Generator GFSR z 3 parametrami" -> {
                        GfsrGeneratorPar3 gfsrGeneratorPar3 = new GfsrGeneratorPar3();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    gfsrGeneratorPar3.initGfsr(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas wypełniania pól!!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wprowadzania");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator nasion":
                                try {
                                    gfsrGeneratorPar3.initGfsr(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd generowania nasion!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Domyślna wartość":
                                gfsrGeneratorPar3.initGfsr(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, gfsrGeneratorPar3.gfsr31(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, załadowana tylko do tablicy");
                        }
                    }
                    case "Generator GFSR z 5 parametrami" -> {
                        GfsrGeneratorPar5 gfsrGeneratorPar5 = new GfsrGeneratorPar5();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    gfsrGeneratorPar5.initGfsr5(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas wypełniania pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wprowadzania");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator nasion":
                                try {
                                    gfsrGeneratorPar5.initGfsr5(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd generowania nasion");
                                    alert.showAndWait();
                                }
                                break;
                            case "Domyślna wartość":
                                gfsrGeneratorPar5.initGfsr5(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, gfsrGeneratorPar5.gfsr531(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowanie liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "Generator_Tausworth" -> {
                        TausworthGenerator tausworthGenerator = new TausworthGenerator();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    tausworthGenerator.taus88(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    tausworthGenerator.taus88(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                tausworthGenerator.taus88(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, tausworthGenerator.tausCapacity(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowanie liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "Генератор_Твистера" -> {
                        TwisterGenerator twisterGenerator = new TwisterGenerator();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    twisterGenerator.initGenrand(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    twisterGenerator.initGenrand(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                twisterGenerator.initGenrand(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, twisterGenerator.genrand31(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                    infoLabel.setText("Wygenerowane liczby losowe:");
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "LCG_generator_V1" -> {
                        LCGeneratorV1 lcGeneratorV1 = new LCGeneratorV1();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    lcGeneratorV1.initLcong32(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    lcGeneratorV1.initLcong32(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                lcGeneratorV1.initLcong32(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, lcGeneratorV1.lcong32_31(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "LCG_generator_V2" -> {
                        LCGeneratorV2 lcGeneratorV2 = new LCGeneratorV2();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    lcGeneratorV2.initLcong31(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    lcGeneratorV2.initLcong31(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                lcGeneratorV2.initLcong31(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, lcGeneratorV2.lcong31(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "Multiplicative_generator_V1" -> {
                        MultiplicativeGeneratorV1 multiplicativeGeneratorV1 = new MultiplicativeGeneratorV1();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    multiplicativeGeneratorV1.initMul(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowychа":
                                try {
                                    multiplicativeGeneratorV1.initMul(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                multiplicativeGeneratorV1.initMul(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, multiplicativeGeneratorV1.Mul32_31(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "Multiplicative_generator_V2" -> {
                        MultiplicativeGeneratorV2 multiplicativeGeneratorV2 = new MultiplicativeGeneratorV2();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    multiplicativeGeneratorV2.initMul(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    multiplicativeGeneratorV2.initMul(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                multiplicativeGeneratorV2.initMul(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, multiplicativeGeneratorV2.Mul32_31(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "Multiplicative_generator_V3" -> {
                        MultiplicativeGeneratorV3 multiplicativeGeneratorV3 = new MultiplicativeGeneratorV3();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    multiplicativeGeneratorV3.initMul(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    multiplicativeGeneratorV3.initMul(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                multiplicativeGeneratorV3.initMul(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, multiplicativeGeneratorV3.Mul32_31(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "Multiplicative_generator_V4" -> {
                        MultiplicativeGeneratorV4 multiplicativeGeneratorV4 = new MultiplicativeGeneratorV4();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    multiplicativeGeneratorV4.initMul(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    multiplicativeGeneratorV4.initMul(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                multiplicativeGeneratorV4.initMul(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, multiplicativeGeneratorV4.Mul32_31(numberSample.getCapacity()));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "Random_generator" -> {
                        Random r = new Random();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    r.setSeed(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    r.setSeed(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                r.setSeed(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, r.nextInt((int) Math.pow(2, numberSample.getCapacity())));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "SecureRandom_generator" -> {
                        SecureRandom sr = new SecureRandom();
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    sr.setSeed(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    sr.setSeed(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                sr.setSeed(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, sr.nextInt((int) Math.pow(2, numberSample.getCapacity())));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                    case "SplittableRandom_generator" -> {
                        SplittableRandom splittableRandom = null;
                        switch (initNumComboBox.getSelectionModel().getSelectedItem()) {
                            case "Ręcznie":
                                if (initNumberSpinner.getValue() != null) {
                                    splittableRandom = new SplittableRandom(initNumberSpinner.getValue());
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd w wypełnianiu pól!");
                                    alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                                    alert.showAndWait();
                                }
                                break;
                            case "Generator liczb początkowych":
                                try {
                                    splittableRandom = new SplittableRandom(SeedGenerator.SeedGen());
                                } catch (ParseException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Błąd!");
                                    alert.setHeaderText("Błąd podczas generowania numeru początkowego!");
                                    alert.showAndWait();
                                }
                                break;
                            case "Wartość domyślna":
                                splittableRandom = new SplittableRandom(19660809);
                                break;
                        }
                        for (int i = 0; i < numberSample.getCountSample(); i++) {
                            for (int j = 0; j < numberSample.getNSample(); j++) {
                                numberSample.setItemSample(i, j, splittableRandom != null ? splittableRandom.nextInt((int) Math.pow(2, numberSample.getCapacity())) : 0);
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                                if (loadCheckBox.isSelected()) {
                                    assert pw != null;
                                    pw.println(numberSample.getItemSample(i, j));
                                }
                            }
                        }
                        if (numberSample.getNSampleMas() <= size) {
                            infoLabel.setText("Wygenerowane liczby losowe:");
                            randListView.setItems(numsRand);
                        } else {
                            infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                        }
                    }
                }
                if (loadCheckBox.isSelected()) {
                    assert pw != null;
                    pw.flush();
                    pw.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd!");
                alert.setHeaderText("Błąd w wypełnianiu pól!");
                alert.setContentText("Wypełnij wszystkie pola na stronie wejściowej");
                alert.showAndWait();
            }
        } else if (loadComboBox.getSelectionModel().getSelectedItem().equals("Plik")) {
            numsRand = FXCollections.observableArrayList();
            numberSample = new NumberSample(numberSamplesSpinner.getValue(), sizeSampleSpinner.getValue());
            Scanner sc1;
            try {
                sc1 = new Scanner(fileObject);
                if (!loadCheckBox.isSelected() && sc1.hasNextInt()) {
                    for (int i = 0; i < numberSample.getCountSample(); i++) {
                        for (int j = 0; j < numberSample.getNSample(); j++) {
                            if (sc1.hasNextInt()) {
                                numberSample.setItemSample(i, j, sc1.nextInt());
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                            } else {
                                sc1.next();
                            }
                        }
                    }
                } else if (loadCheckBox.isSelected() || sc1.hasNextDouble()) {
                    int max = (int) Math.pow(2, capacityNumSpinner.getValue()) - 1;
                    for (int i = 0; i < numberSample.getCountSample(); i++) {
                        for (int j = 0; j < numberSample.getNSample(); j++) {
                            if (sc1.hasNextDouble()) {
                                numberSample.setItemSample(i, j, (int) (sc1.nextDouble() * max));
                                if (numberSample.getNSampleMas() <= size) {
                                    numsRand.add(numberSample.getItemSample(i, j));
                                }
                            } else {
                                sc1.next();
                            }
                        }
                    }
                }
                if (numberSample.getNSampleMas() <= size) {
                    infoLabel.setText("Wygenerowane liczby losowe:");
                    randListView.setItems(numsRand);
                } else {
                    infoLabel.setText("Próbka jest ogromna, ładowana tylko do tablicy");
                }
                numberSample.setCapacity((int) Math.ceil(Math.log10(Arrays.stream(numberSample.matrToMas()).parallel().max().getAsInt()) / Math.log10(2)));
                testButton.setDisable(false);
                sc1.close();
            } catch (FileNotFoundException e) {
                testButton.setDisable(true);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd!");
                alert.setHeaderText("Błąd odczytu pliku!");
                alert.setContentText("Nie znaleziono pliku");
                alert.showAndWait();
            }
        }
        aLabel.setDisable(false);
        aSpinner.setDisable(false);
    }

    public void testButtonHandler(ActionEvent actionEvent) throws InterruptedException {
        diagramTab.setDisable(false);
        graphicTab.setDisable(false);
        graphicDopTab.setDisable(false);
        resTab.setDisable(false);
        diagramBarChart.getData().clear();

        ProgressForm pForm = new ProgressForm();

        ParamsTest paramsTest = new ParamsTest();
        TestsSample testsSample = new TestsSample(numberSample, paramsTest);
        paramsTest.setA(aSpinner.getValue());

        Thread thread1 = new Thread(() -> {
            testsSample.testFun1Double();
            testsSample.testFun2Double();
            testsSample.testFun3Double();
        });
        Thread thread2 = new Thread(() -> {
            testsSample.testFun4();
            testsSample.testFun5();
        });
        Thread thread3 = new Thread(() -> {
            testsSample.testFun6();
            testsSample.testFun7();
        });
        Thread thread4 = new Thread(() -> {
            testsSample.testFun8();
            testsSample.testFun9();
        });
        Thread thread5 = new Thread(() -> {
            testsSample.testFun11();
            testsSample.testFun12();
        });
        Thread thread6 = new Thread(testsSample::testFunGraphicDouble);
        List<Thread> listThread = new ArrayList<>();
        listThread.add(thread1);
        listThread.add(thread2);
        listThread.add(thread3);
        listThread.add(thread4);
        listThread.add(thread5);
        listThread.add(thread6);
        for (var th :
                listThread) {
            try {
                th.start();
            } catch (OutOfMemoryError e) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Okienko błędu");
                alert1.setHeaderText("Przepełnienie pamięci");
                alert1.setContentText("Test Kołmogorowa-Smirnowa nie zostanie przeprowadzony");
                alert1.show();
            }
        }
        setPixels();
        testsSample.testFun10();
        for (var th :
                listThread) {
            th.join();
        }
        listThread.clear();
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        for (int i = 0; i < paramsTest.getHeights().length; i++) {
            dataSeries1.getData().add(new XYChart.Data<>(String.valueOf(i + 1), paramsTest.getParamHeights(i)));
        }
        dataSeries1.setName("Częstotliwość");
        diagramBarChart.getData().add(dataSeries1);
        writeText(testsSample);
        pForm.getDialogStage().setTitle("TESTOWANIE SIĘ SKOŃCZYŁO!");
    }

    public void setPixels() {
        GraphicsContext gc = graphTestCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, graphTestCanvas.getWidth(), graphTestCanvas.getHeight());
        GraphicsContext gc1 = graphDopTestCanvas.getGraphicsContext2D();
        gc1.clearRect(0, 0, graphTestCanvas.getWidth(), graphTestCanvas.getHeight());
        int size = numberSample.getNSampleMas() - 4;
        if (size > 8388608) {
            size = 8388608;
        }
        int max = (int) (Math.pow(2, numberSample.getCapacity()) - 1);
        int[] pixelsMas = numberSample.matrToMas();
        for (int i = 0; i < size; i++) {
            gc.getPixelWriter().setColor(
                    (int) (graphTestCanvas.getWidth() * (double) pixelsMas[i] / max),
                    (int) (graphTestCanvas.getHeight() * (double) pixelsMas[i + 1] / max),
                    new Color((double) pixelsMas[i + 4] / max,
                            (double) pixelsMas[i + 3] / max,
                            (double) pixelsMas[i + 2] / max, 1));
            if (i != size - 1)
                gc1.getPixelWriter().setColor(
                        (int) (graphDopTestCanvas.getWidth() * 0.5 * ((double) pixelsMas[i] / max + (double) pixelsMas[i + 1] / max)),
                        (int) (graphDopTestCanvas.getHeight() * 0.5 * ((double) pixelsMas[i + 1] / max + (double) pixelsMas[i + 2] / max)),
                        new Color(0.5 * ((double) pixelsMas[i + 4] / max + (double) pixelsMas[i + 5] / max),
                                0.5 * ((double) pixelsMas[i + 3] / max + (double) pixelsMas[i + 4] / max),
                                0.5 * ((double) pixelsMas[i + 2] / max + (double) pixelsMas[i + 3] / max), 1));
        }
    }

    public void writeText(TestsSample testsSample) {
        resTextArea.clear();
        resTextArea.appendText("Test 1. Testowanie hipotezy o rozkładzie jednorodnym zmiennej losowej za pomocą przedziału ufności:\n");
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[0] + "\n");
        if (testsSample.getTest()[0]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Test nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 2. Testowanie hipotezy o rozkładzie jednorodnym zmiennej losowej za pomocą testu chi-kwadrat:\n");
        if (testsSample.getTestPval()[1])
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[1] + "\n");
        if (testsSample.getTest()[1] && testsSample.getTestPval()[1]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 3. Oszacowanie matematycznego oczekiwania każdej próbki liczb losowych:\n");
        resTextArea.appendText("Eksperymentalnie zmierzone matematyczne oczekiwanie każdej próbki: " + (double) Math.round(testsSample.getExpMean() * 100000.0) / 100000.0 + "\n");
        resTextArea.appendText("Teoretyczne oczekiwanie matematyczne: " + 0.5 + "\n");
        resTextArea.appendText("Procentowe odchylenie obliczonej wartości oczekiwanej matematycznej od teoretycznej: " + (double) Math.round(testsSample.getDol()[2] * 100000.0) / 100000.0 + "%" + "\n");
        if (testsSample.getTest()[2]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 4. Test monobitowy częstotliwości:\n");
        if (testsSample.getTestPval()[3])
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[3] + "\n");
        if (testsSample.getTest()[3] && testsSample.getTestPval()[3]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 5. Test częstotliwości w podciągach:\n");
        if (testsSample.getTestPval()[4])
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[4] + "\n");
        if (testsSample.getTest()[4]&& testsSample.getTestPval()[4]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 6. Test częstotliwości dla długich sekwencji:\n");
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[5] + "\n");
        if (testsSample.getTest()[5]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 7. Sprawdź sekwencję identycznych bitów:\n");
        if (testsSample.getTestPval()[6])
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[6] + "\n");
        if (testsSample.getTest()[6] && testsSample.getTestPval()[6]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 8. Test sprawdzający jednorodność podciągów:\n");
        if (testsSample.getTestPval()[7])
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[7] + "\n");
        if (testsSample.getTest()[7] && testsSample.getTestPval()[7]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 9. Test pokera:\n");
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[8] + "\n");
        if (testsSample.getTest()[8]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 10. Test Kołmogorowa-Smirnowa:\n");
        if (testsSample.getTestPval()[9])
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[9] + "\n");
        if (testsSample.getTest()[9] && testsSample.getTestPval()[9]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 11. Test do sprawdzenia serii bitów:\n");
        if (testsSample.getTestPval()[10])
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[10] + "\n");
        if (testsSample.getTest()[10] && testsSample.getTestPval()[10]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        resTextArea.appendText("-----------------------------------------------------------------------------------------------------------------------\n");
        resTextArea.appendText("Test 12. Uniwersalny test statystyczny Maurera:\n");
        if (testsSample.getTestPval()[11])
        resTextArea.appendText("Procent sekwencji, które przeszły test: " + testsSample.getDol()[11] + "\n");
        if (testsSample.getTest()[11] && testsSample.getTestPval()[11]) {
            resTextArea.appendText("Test zaliczony\n");
        } else {
            resTextArea.appendText("Тest nieudany\n");
        }
        if (90 <= testsSample.getResTests() && testsSample.getResTests() <= 100) {
            resLabel.setText("Kolejność z dużym prawdopodobieństwem będzie przypadkowa");
        }
        else {
            resLabel.setText("Kolejność nie jest przypadkowa");
        }
        resProcLabel.setText("Procent zdanych testów: " + testsSample.getResTests() + "%");
    }
}
