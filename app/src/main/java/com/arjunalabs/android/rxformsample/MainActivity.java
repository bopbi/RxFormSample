package com.arjunalabs.android.rxformsample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // prepare all the widgets
        EditText input1 = (EditText) findViewById(R.id.edit_input1);
        EditText input2 = (EditText) findViewById(R.id.edit_input2);
        final Button loginButton = (Button) findViewById(R.id.login_button);

        // observe the state of input1
        Observable<Boolean> input1Observable = WidgetObservable.text(input1).map(new Func1<OnTextChangeEvent, String>() {
            @Override
            public String call(OnTextChangeEvent onTextChangeEvent) {
                return onTextChangeEvent.text().toString();
            }
        }).map(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.length() > 4;
            }
        });

        // observe the state of input2
        Observable<Boolean> input2Observable = WidgetObservable.text(input2).map(new Func1<OnTextChangeEvent, String>() {
            @Override
            public String call(OnTextChangeEvent onTextChangeEvent) {
                return onTextChangeEvent.text().toString();
            }
        }).map(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.length() > 4;
            }
        });

        // response when the state is change on both input
        Observable.combineLatest(input1Observable, input2Observable, new Func2<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean, Boolean aBoolean2) {
                return aBoolean && aBoolean2;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                loginButton.setEnabled(aBoolean);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
