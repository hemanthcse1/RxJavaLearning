package com.hemanth.rxjavalearning.operators;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hemanth.rxjavalearning.DataSource;
import com.hemanth.rxjavalearning.R;
import com.hemanth.rxjavalearning.Task;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OperatorsActivity extends AppCompatActivity {

    private static final String TAG = "OperatorsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operators_activity);


        // Basic example of observerble and observer creation
        //basicExample();


        // Create Operator
        //createOperator();


        // Just Operator
        //justOperator();
        
        
        // Range Operator
       // rangeOperator();

        // Repeat Operator
        repeatOperator();


    }

    private void repeatOperator() {

        Observable<Integer> observable = Observable
                .range(0,3)
                .subscribeOn(Schedulers.io())
                .repeat(3)
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: "+integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rangeOperator() {
        
        Observable<Task> observable = Observable
                .range(0,9)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Task>() {
                    @Override
                    public Task apply(Integer integer) throws Throwable {
                        Log.d(TAG, "apply: "+Thread.currentThread().getName());
                        return new Task("High priority to check integer"+String.valueOf(integer),false,integer);
                    }
                })
                .takeWhile(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Throwable {
                        return task.getPriority() < 9;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        
        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: "+task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    private void justOperator() {
        // we can pass upto max of 10 fields
        final Task task = new Task("Morning walk",false,3);

        List<Task> tasks = DataSource.createTaskList();

        Observable<List<Task>> listObservable = Observable
                .just(tasks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        listObservable.subscribe(new Observer<List<Task>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Task> tasks) {
                Log.d(TAG, "onNext: "+tasks.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

       /* Observable<Task> taskObservable = Observable
                .just(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: "+task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");

            }
        });*/


    }

    private void createOperator() {

        // final Task task = new Task("Morning walk",false,3);

        List<Task> tasks = DataSource.createTaskList();

        Observable<Task> taskObservable = Observable
                .create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Task> emitter) throws Throwable {

                        // this is for adding sing task
                        /*if (!emitter.isDisposed()){
                            emitter.onNext(task);
                            emitter.onComplete();
                        }*/

                        // this is for adding list of task to observeble
                        for (Task task: tasks){
                            if (!emitter.isDisposed()){
                                emitter.onNext(task);
                            }
                        }

                        if (!emitter.isDisposed()){
                            emitter.onComplete();
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: "+task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void basicExample() {

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTaskList())
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Throwable {
                        Thread.sleep(1000);
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
