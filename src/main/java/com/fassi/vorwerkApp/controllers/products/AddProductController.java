package com.fassi.vorwerkApp.controllers.products;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.enumerations.EProductCategory;
import com.fassi.vorwerkApp.enumerations.EProductType;
import com.fassi.vorwerkApp.models.Product;
import com.fassi.vorwerkApp.utils.validators.utils.ValidatorsUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class AddProductController implements Initializable {
    private DialogResult<Product> dialogResult;
    @FXML
    private TextField productNameTextField;
    @FXML
    private Text productNameTextFieldValidation;
    @FXML
    private Spinner<Double> productPriceSpinner;
    @FXML
    private Text productPriceSpinnerValidation;
    @FXML
    private Spinner<Integer> productQuantitySpinner;
    @FXML
    private Text productQuantitySpinnerValidation;
    @FXML
    private ComboBox productTypeComboBox;
    @FXML
    private Text productTypeComboBoxValidation;
    @FXML
    private ComboBox productCategoryComboBox;
    @FXML
    private Text productCategoryComboBoxValidation;
    public AddProductController(DialogResult<Product> dialogResult) {
        this.dialogResult = dialogResult;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productTypeComboBox.getSelectionModel().selectFirst();
        this.productCategoryComboBox.getSelectionModel().selectFirst();
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 || parsePosition.getIndex() < c.getControlNewText().length())
                    return null;
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(new IntegerStringConverter(), 0, filter);
        this.productQuantitySpinner.setEditable(true);
        this.productQuantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0));
        this.productQuantitySpinner.getEditor().setTextFormatter(priceFormatter);
        this.productPriceSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.00, 0.00));
    }

    private boolean validate() {
        boolean isValid = true;
        if (this.productNameTextField.getText().length() == 0) {
            isValid = false;
            this.productNameTextFieldValidation.setText("Produkname erforderlich! ");
        } else this.productNameTextFieldValidation.setText("");

        if (this.productPriceSpinner.getValue() <= 0) {
            isValid = false;
            this.productPriceSpinnerValidation.setText("Produktpreis erfoerderlich!");
        } else this.productPriceSpinnerValidation.setText("");

        if (this.productQuantitySpinner.getValue() == 0) {
            isValid = false;
            this.productQuantitySpinnerValidation.setText("Produktverfügbarkeit erforderlich!");
        } else this.productQuantitySpinnerValidation.setText("");
        return isValid;
    }

    @FXML
    void onAccept(ActionEvent event) {
        if (this.validate())
            this.dialogResult.onAccept(new Product(productNameTextField.getText(), productPriceSpinner.getValue(), productQuantitySpinner.getValue(), this.productCategoryComboBox.getSelectionModel().getSelectedIndex() > 0 ? EProductCategory.STAUBSAUGER : EProductCategory.MIXER, this.productTypeComboBox.getSelectionModel().getSelectedIndex() > 0 ? EProductType.ErsatzTeil : EProductType.Produkt));
    }

    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }

}
