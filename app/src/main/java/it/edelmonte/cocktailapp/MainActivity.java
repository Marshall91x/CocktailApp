package it.edelmonte.cocktailapp;

import static org.koin.core.context.DefaultContextExtKt.startKoin;

import static it.edelmonte.cocktailapp.util.KoinInjectorKt.koinInjector;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;
import org.koin.core.context.GlobalContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Strating koin and injecting module
        KoinApplication koin = KoinAndroidApplication.create(this.getApplicationContext()).modules(koinInjector);
        if(GlobalContext.INSTANCE.getOrNull() == null){
            startKoin(koin);
        }
        setContentView(R.layout.activity_main);
    }
}