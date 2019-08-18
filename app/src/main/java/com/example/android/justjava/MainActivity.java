/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameForOrder = (EditText) findViewById(R.id.name_for_order);
        String nameForOrderString = nameForOrder.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean whippedCreamChecked = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        Boolean chocolateChecked = chocolateCheckBox.isChecked();

        int price = calculatePrice(whippedCreamChecked, chocolateChecked);

        createOrderSummary(price, whippedCreamChecked, chocolateChecked, nameForOrderString);
    }

    public void createOrderSummary(int price, boolean whippedCreamChecked, boolean chocolateChecked, String nameForOrderString) {
        String priceMessage =
                "Name: " + nameForOrderString +
                        "\nQauantity: " + quantity +
                        "\nAdd whipped cream? " + whippedCreamChecked +
                        "\nAdd chocolate? " + chocolateChecked +
                        "\nTotal: $" + price + "\nThank you!";

        String subject = "JustJava order for " + nameForOrderString;

        composeEmail(subject, priceMessage);

//      displayMessage(priceMessage);
    }

    public int calculatePrice(Boolean whippedCreamChecked, Boolean chocolateChecked) {
        int itemPrice = 5;

        if (whippedCreamChecked) {
            itemPrice = itemPrice + 1;
        }

        if (chocolateChecked) {
            itemPrice = itemPrice + 2;
        }

        int totalPrice = quantity * itemPrice;

        return totalPrice;
    }

    public void increment(View view) {
        quantity = quantity + 1;

        if (quantity > 100) {
            quantity = 100;
        }

        displayQuantity(quantity);

    }

    public void decrement(View view) {
        quantity = quantity - 1;

        if (quantity < 0) {
            quantity = 0;
        }

        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}