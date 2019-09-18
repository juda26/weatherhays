package com.test.appweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    RecyclerView reyclerViewWeather;
    Button updateButton;
    CompositeDisposable disposable = new CompositeDisposable();
    private WeatherAdapter temperatureAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateButton = (Button) findViewById(R.id.btn_update);
        reyclerViewWeather = (RecyclerView) findViewById(R.id.reyclerViewWeather);
        reyclerViewWeather.setLayoutManager(new LinearLayoutManager(this));
        temperatureAdapter = new WeatherAdapter(this);
        reyclerViewWeather.setAdapter(temperatureAdapter);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButton.setEnabled(false);
                    Observable<List<ItemData>> titleObservable = Observable.fromCallable(new Callable<List<ItemData>>() {
                    @Override
                    public List<ItemData> call() throws Exception {
                        return new RestClient().getTemperatures();
                    }
                });
                Disposable subscriber = titleObservable.
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Consumer<List<ItemData>>() {
                            @Override
                            public void accept(@NonNull List<ItemData> strings) throws Exception {
                                reloadRecyclerView(strings);
                                updateButton.setEnabled(true);
                            }
                        });
                disposable.add(subscriber);
            }
        });

    }



    private void reloadRecyclerView(List<ItemData> strings) {
        temperatureAdapter.swap(strings);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
