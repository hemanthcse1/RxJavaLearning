package com.hemanth.rxjavalearning;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasicExampleActivity extends AppCompatActivity {

    private static final String TAG = "BasicExampleActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_example_activity);


        // Basic Example

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTaskList())
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Throwable {
                        Log.d(TAG, "test: "+Thread.currentThread().getName());
                        Thread.sleep(3000);
                        return task.isComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());


        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: "+Thread.currentThread().getName());
                Log.d(TAG, "onNext: "+task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: ",e );
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });

    }
}
