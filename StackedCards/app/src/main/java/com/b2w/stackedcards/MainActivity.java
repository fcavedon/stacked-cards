package com.b2w.stackedcards;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    ExpandableLayout expandableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableLayout = (ExpandableLayout) findViewById(R.id.card_stack);

        SampleCard card1 = new SampleCard(this, 0);
        SampleCard card2 = new SampleCard(this, 1);
        SampleCard card3 = new SampleCard(this, 2);

        card1.setNext(card2);
        card2.setPrevious(card1);
        card2.setNext(card3);
        card3.setPrevious(card2);

        card1.setText("Card 1");
        card2.setText("Card 2");
        card3.setText("Card 3");

        expandableLayout.addView(card1);
        expandableLayout.addView(card2);
        expandableLayout.addView(card3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
