package com.blazeey.sentimentanalysis.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blazeey.sentimentanalysis.Fragment.AnalyzeFragment;
import com.blazeey.sentimentanalysis.Fragment.ChartFragment;
import com.blazeey.sentimentanalysis.Fragment.SearchFragment;
import com.blazeey.sentimentanalysis.R;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by venki on 24/3/18.
 */

public class FirstActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        isFirstTime();
        SearchFragment searchFragment = new SearchFragment();
        AnalyzeFragment analyzeFragment = new AnalyzeFragment();
        ChartFragment chartFragment = new ChartFragment();

        addSlide(searchFragment);
        addSlide(analyzeFragment);
        addSlide(chartFragment);

        setBarColor(getResources().getColor(R.color.light_shade));
        setSeparatorColor(getResources().getColor(R.color.dark_grey));

        setVibrate(true);
        setVibrateIntensity(30);

        setFlowAnimation();
    }

    @Override
    public void onDonePressed() {
        finish();
    }


}
