package com.fassi.vorwerkApp.controllers.products;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.enumerations.EProductCategory;
import com.fassi.vorwerkApp.enumerations.EProductType;
import com.fassi.vorwerkApp.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class UpdateProductController implements Initializable {


    private Product product;
    @FXML
    private TextField productNameTextField;
    @FXML
    private Text productNameTextFieldValidation;
    @FXML
    private TextField productPriceSpinner;
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
    private DialogResult<Product> dialogResult;


    public UpdateProductController(DialogResult<Product> dialogResult, Product product) {
        this.product = product;
        this.dialogResult = dialogResult;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productNameTextField.setText(this.product.getName());
        this.productTypeComboBox.getSelectionModel().select(this.product.getType().ordinal());
        this.productCategoryComboBox.getSelectionModel().select(product.getCategory().ordinal());
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
        TextFormatter<Integer> priceFormatter = new TextFormatter(new IntegerStringConverter(), this.product.getQuantity(), filter);
        this.productQuantitySpinner.setEditable(true);
        this.productQuantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0));
        this.productQuantitySpinner.getEditor().setTextFormatter(priceFormatter);

    }

    private boolean validate() {
        boolean isValid = true;
        if (this.productNameTextField.getText().length() == 0) {
            isValid = false;
            this.productNameTextFieldValidation.setText("Produkname erforderlich!");
        } else this.productNameTextFieldValidation.setText("");

        if (Double.parseDouble(this.productPriceSpinner.getText())<= 0) {
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
            this.dialogResult.onAccept(new Product(productNameTextField.getText(),Double.parseDouble(this.productPriceSpinner.getText()), productQuantitySpinner.getValue(), this.productCategoryComboBox.getSelectionModel().getSelectedIndex() > 0 ? EProductCategory.STAUBSAUGER : EProductCategory.MIXER, this.productTypeComboBox.getSelectionModel().getSelectedIndex() > 0 ? EProductType.ErsatzTeil : EProductType.Produkt));

    }

    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }

}
