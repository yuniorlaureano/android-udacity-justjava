package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.NumberFormat;




/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheck = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        EditText editTextName = (EditText) findViewById(R.id.edit_text_name);


        String name = editTextName.getText().toString();

        boolean whippedCream = whippedCreamCheck.isChecked();
        boolean chocolate = chocolateCheck.isChecked();

        int price = calculatePrice(quantity,whippedCream, chocolate);
        String message = createOrderSummary(price,quantity, whippedCream, chocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, "data: "+message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param quant is the number of cups of coffee ordered
     */
    private int calculatePrice(int quant, boolean whippedCream, boolean chocolate) {
        int price = 0;

        if (whippedCream){
            price += 1;
        }

        if (chocolate){
            price += 2;
        }

        price *= 5;

        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public  void incrementValue(View view){

        if(quantity == 100){
            Toast.makeText(this, "Your cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public  void descrementValue(View view){
        if(quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
    *@param amount
    *
     */
    private void displayQuantity(int amount){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+ amount);
    }

    public String createOrderSummary(int price,int cuantity, boolean whippedCream, boolean chocolate, String name){
        String whippedC = whippedCream ? "true": "false";
        String chocolateC = chocolate ? "true": "false";
        String message = "";

        message +=  getString(R.string.order_summary, name) + "\n";
        message +=  getString(R.string.add_whipped, whippedC)+"\n";
        message +=  getString(R.string.add_chocolate, chocolateC)+"\n";
        message +=  getString(R.string.quantity, String.valueOf(cuantity))+"\n";
        message +=  getString(R.string.total, NumberFormat.getCurrencyInstance().format(price))+"\n";
        message +=  getString(R.string.thank_you)+"\n";

        return message;
    }
}