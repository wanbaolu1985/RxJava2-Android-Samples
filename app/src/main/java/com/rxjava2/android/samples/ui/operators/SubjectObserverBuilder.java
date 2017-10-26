package com.rxjava2.android.samples.ui.operators;

import android.util.Log;
import android.widget.TextView;

import com.rxjava2.android.samples.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * SubjectObserverBuilder
 * Created by nibbleswan on 2017/10/11.
 */

public class SubjectObserverBuilder {
    private final String TAG;
    private final TextView textView;
    public SubjectObserverBuilder(String TAG, TextView textView) {
        this.TAG = TAG;
        this.textView = textView;
    }

    private int indentCount;
    public void resetIndentCount() {
        indentCount = 0;
    }

    public Observer<Integer> buildObserver(final String name) {
        final int lastIndentCount = indentCount;
        ++indentCount;

        return new Observer<Integer>() {

            private String buildIntent(int indentCount) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < indentCount; ++i) {
                    sb.append('\t');
                }
                return sb.toString();
            }

            private final String indent = buildIntent(lastIndentCount);

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " " + indent + name + " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" " + indent + name + " onNext : value : " + value);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " " + indent + name + " onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" " + indent + name + " onError : " + e.getMessage());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " " + indent + name + " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" " + indent + name + " onComplete");
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " " + indent + name + " onComplete");
            }
        };
    }
}
