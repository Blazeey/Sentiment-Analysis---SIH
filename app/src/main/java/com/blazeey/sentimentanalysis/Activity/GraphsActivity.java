package com.blazeey.sentimentanalysis.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.blazeey.sentimentanalysis.Model.Constants;
import com.blazeey.sentimentanalysis.Model.IndiaMap;
import com.blazeey.sentimentanalysis.Model.State;
import com.blazeey.sentimentanalysis.Model.TweetsAdapter;
import com.blazeey.sentimentanalysis.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GraphsActivity extends AppCompatActivity {

    public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:m:s", Locale.getDefault());
    public static final SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final String TAG = "JSON Parsing";

    //LineChart------------------------------------------------------------------
    private LineChart lineChart;
    private List<Entry> positive = new ArrayList<>();
    private List<Entry> negative = new ArrayList<>();
    private List<Entry> neutral = new ArrayList<>();
    private LineDataSet positiveLineDataSet, negativeLineDataSet, neutralLineDataSet;
    private List<ILineDataSet> chartDataSets = new ArrayList<>();
    private Map<String, Map<String, Integer>> positiveCount = new HashMap<>();
    private Map<String, Map<String, Integer>> negativeCount = new HashMap<>();
    private Map<String, Map<String, Integer>> neutralCount = new HashMap<>();
    private List<Integer> posCount = new ArrayList<>();
    private List<Integer> negCount = new ArrayList<>();
    private List<Integer> neuCount = new ArrayList<>();
    //LineChart------------------------------------------------------------------

    //IndiaMap-------------------------------------------------------------------
    private ImageView indiaView;
    private List<State> statesList = new ArrayList<>();
    private IndiaMap indiaMap;
    private Bitmap indiaMapImage;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            indiaMapImage = indiaMap.createMap();
            indiaView.setImageBitmap(indiaMapImage);
        }
    };
    //IndiaMap-------------------------------------------------------------------

    //Barchart-------------------------------------------------------------------
    private BarChart barChart;
    private List<BarEntry> positiveBar = new ArrayList<>();
    private List<BarEntry> negativeBar = new ArrayList<>();
    private List<BarEntry> neutralBar = new ArrayList<>();
    private BarDataSet positiveSet, negativeSet, neutralSet;
    private Handler barHandler = new Handler();
    private Runnable barChartRunnable = new Runnable() {
        @Override
        public void run() {
            barChart.animateX(3000);
            barHandler.postDelayed(barChartRunnable, 3000);
        }
    };
    //Barchart-------------------------------------------------------------------

    //PieChart-------------------------------------------------------------------
    private PieChart pieChart;
    private List<PieEntry> pieEntryList = new ArrayList<>();
    private PieDataSet pieDataSet;
    private Handler pieHandler = new Handler();
    private Runnable pieChartRunnable = new Runnable() {
        @Override
        public void run() {
            pieChart.animateX(3000);
            pieHandler.postDelayed(pieChartRunnable, 3000);
        }
    };
    //PieChart-------------------------------------------------------------------

    //Tweets---------------------------------------------------------------------
    private List<String> positiveTweets, negativeTweets, neutralTweets;
    private RecyclerView posTweetsView, negTweetsView, neuTweetsView;
    private TweetsAdapter posAdapter, negAdapter, neuAdapter;
    private DividerItemDecoration dividerItemDecoration, dividerItemDecoration2;
    //Tweets---------------------------------------------------------------------
    private String[] intervalOneDay = {"24hrs","20hrs","16hrs","12hrs","8hrs","4hrs"};
    private String[] intervalTwoDay = {"48hrs","40hrs","32hrs","24hrs","16hrs","8hrs"};
    private int formatter = 0;

    IAxisValueFormatter xAxisFormatterOneDay = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String text = "";
            text = intervalOneDay[(int)value/4];
            Log.v("Formatter",text+":"+value);
            return text;
        }
    };

    IAxisValueFormatter xAxisFormatterTwoDay = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String text = "";
            text = intervalTwoDay[(int)value/10];
//            Log.v("Formatter",value+":"+text);
            return text;
        }
    };
    IAxisValueFormatter ethnicityFormatter = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
//            Log.v("formatterxx",value+"");
            if (value == 0.0)
                return "Male";
            else if (value == 0.90000004f)
                return "Female";
            else
                return "";
        }
    };

    NestedScrollView nestedScrollView;
    TextView search;
    CardView pieCard, barCard;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        context = this;

        //Line Chart-----------
        lineChart = findViewById(R.id.line_chart);
//        addChart();
        //Line Chart-----------

        //India Map------------
        indiaView = findViewById(R.id.india_map);
        //India Map------------

        //BarChart-------------
        barChart = findViewById(R.id.bar_chart);
        //BarCHart-------------

        //PieChart-------------
        pieChart = findViewById(R.id.pie_chart);
        //PieChart-------------

        search = findViewById(R.id.search_result);
//        searchBar.setInputType(InputType.TYPE_NULL);

        nestedScrollView = findViewById(R.id.nested);
        pieCard = findViewById(R.id.pie_card);
        barCard = findViewById(R.id.bar_card);
        posTweetsView = findViewById(R.id.positive_tweets);
        negTweetsView = findViewById(R.id.negative_tweets);

        positiveTweets = new ArrayList<>();
        negativeTweets = new ArrayList<>();
        neutralTweets = new ArrayList<>();
        posAdapter = new TweetsAdapter(this, positiveTweets);
        negAdapter = new TweetsAdapter(this, negativeTweets);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        posTweetsView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), linearLayoutManager.getOrientation());
        posTweetsView.addItemDecoration(dividerItemDecoration);
        posTweetsView.setHasFixedSize(true);
        posTweetsView.setAdapter(posAdapter);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        negTweetsView.setLayoutManager(linearLayoutManager1);
        dividerItemDecoration2 = new DividerItemDecoration(getApplicationContext(), linearLayoutManager1.getOrientation());
        negTweetsView.addItemDecoration(dividerItemDecoration2);
        negTweetsView.setHasFixedSize(true);
        negTweetsView.setAdapter(negAdapter);

        String query = getIntent().getStringExtra("result");
        search.setText(getIntent().getStringExtra("key"));
        try {
            parseData(query);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        String query = "{'key':'mkbhd'}";
//        parsing.execute(query);

//        try {
//            search.setText(getIntent().getStringExtra("key"));
//            String data = openJson();
//            parseData(data);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    LineDataSet modifyDataSet(LineDataSet dataSet, int color) {
        dataSet.setColor(color);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setDrawCircles(false);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        return dataSet;
    }

    List<Entry> chartEntries(List<Integer> data) {
        List<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++)
            lineEntries.add(new Entry(i, data.get(i)));
        return lineEntries;
    }

    void addLineDataSet(LineDataSet dataSet) {
        chartDataSets.add(dataSet);
    }

    void axisFormatter() {

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        if(posCount.size()>24)
            xAxis.setValueFormatter(xAxisFormatterTwoDay);
        else
            xAxis.setValueFormatter(xAxisFormatterOneDay);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        YAxis yAxis = lineChart.getAxisLeft();
//        yAxis.setDrawLabels(false);
//        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0f);

    }

    void addChart() {

//        lineChartFormatter();
        shrink();
        Log.v("Positive", positiveCount.size() + "");
        positive = chartEntries(posCount);
        Log.v("Pos", posCount.size() + "");
        negative = chartEntries(negCount);
        Log.v("Neg", negCount.size() + "");
        neutral = chartEntries(neuCount);
        Log.v("Neu", neuCount.size() + "");

        positiveLineDataSet = new LineDataSet(positive, "Positive Tweets");
        positiveLineDataSet = modifyDataSet(positiveLineDataSet, getResources().getColor(R.color.positive));
        addLineDataSet(positiveLineDataSet);

        negativeLineDataSet = new LineDataSet(negative, "Negative Tweets");
        negativeLineDataSet = modifyDataSet(negativeLineDataSet, getResources().getColor(R.color.negative));
        addLineDataSet(negativeLineDataSet);

        neutralLineDataSet = new LineDataSet(neutral, "Neutral Tweets");
        neutralLineDataSet = modifyDataSet(neutralLineDataSet, getResources().getColor(R.color.neutral));
        addLineDataSet(neutralLineDataSet);

        LineData lineData = new LineData(chartDataSets);
        lineData.setDrawValues(false);

        axisFormatter();

        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.getAxisRight().setEnabled(false);
//        lineChart.getAxisLeft().setEnabled(false);
        lineChart.setData(lineData);
        lineChart.animateX(7000);

//        Legend legend = lineChart.getLegend();
//        legend.setForm(LegendForm.LINE);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setTextSize(13f);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        legend.setTextSize(12f);
//        legend.setTextColor(Color.BLACK);
//        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
//        legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis


    }

    void addBarChart() {

        positiveSet = new BarDataSet(positiveBar, "Positive");
        negativeSet = new BarDataSet(negativeBar, "Negative");
        neutralSet = new BarDataSet(neutralBar, "Neutral");

        positiveSet.setColor(getResources().getColor(R.color.positive));
        negativeSet.setColor(getResources().getColor(R.color.negative));
        neutralSet.setColor(getResources().getColor(R.color.neutral));

        BarData barData = new BarData(positiveSet, negativeSet, neutralSet);

        float groupSpace = 0.2f;
        float barSpace = 0.04f; // x2 dataset
        float barWidth = 0.2f; // x2 dataset

        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        barData.setBarWidth(barWidth);

        Description description = new Description();
        description.setText("");

        barChart.setDescription(description);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextSize(12f);
//        xAxis.setGranularity(5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(ethnicityFormatter);
        xAxis.setDrawAxisLine(false);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        yAxis = barChart.getAxisRight();
        yAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        barChart.setData(barData);
//        barChart.setFitBars(true);
        barChart.groupBars(-0.5f, groupSpace, barSpace);

        Legend legend = barChart.getLegend();
        legend.setForm(LegendForm.CIRCLE);
        legend.setFormSize(15f);
        legend.setFormToTextSpace(5f);
        legend.setXEntrySpace(10f);
        legend.setTextSize(15f);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        barChart.animateY(7000, Easing.EasingOption.EaseInBounce);
//        barChartRunnable.run();
    }

    void addPieChart(List<Integer> data) {

        pieEntryList = pieChartEntry(data);
        pieDataSet = new PieDataSet(pieEntryList, "");
        pieDataSet.setColors(new int[]{R.color.positive, R.color.negative, R.color.neutral}, this);
        pieDataSet.setSelectionShift(10f);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setDrawValues(false);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawSlicesUnderHole(true);
        pieChart.setUsePercentValues(true);
        pieChart.setData(pieData);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);

        Legend legend = pieChart.getLegend();
        legend.setForm(LegendForm.CIRCLE);
        legend.setFormSize(15f);
        legend.setFormToTextSpace(5f);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setStackSpace(0f);
        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        legend.setYEntrySpace(4f); // set the space between the legend entries on the y-axis

//        pieChartRunnable.run();
        pieChart.animateY(7000, Easing.EasingOption.EaseInBounce);
    }

    List<PieEntry> pieChartEntry(List<Integer> data) {
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(data.get(0), "Positive"));
        pieEntries.add(new PieEntry(data.get(1), "Negative"));
        pieEntries.add(new PieEntry(data.get(2), "Neutral"));
        return pieEntries;
    }


    String openJson() throws IOException {
        String data = "", temp;
        InputStream inputStream = getAssets().open("response.json");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((temp = bufferedReader.readLine()) != null) {
            data += temp;
        }

        return data;
    }

    JSONObject parseData(String query) throws JSONException {

        JSONObject jsonObject = new JSONObject(query);

        JSONObject timeline = jsonObject.getJSONObject("contents").getJSONObject("timeline");
        JSONObject positiveObject = timeline.getJSONObject("pos");
        JSONObject negativeObject = timeline.getJSONObject("neg");
        JSONObject neutralObject = timeline.getJSONObject("neu");
        positiveCount = countJSON(positiveObject);
        negativeCount = countJSON(negativeObject);
        neutralCount = countJSON(neutralObject);
        addChart();

        JSONObject totalSentiment = jsonObject.getJSONObject("contents").getJSONObject("total_sentiment");
        addPieChart(totalPie(totalSentiment));

        JSONObject ethnicityObject = jsonObject.getJSONObject("contents").getJSONObject("gender_details");

        JSONObject male = ethnicityObject.getJSONObject("M");
        JSONObject female = ethnicityObject.getJSONObject("F");
//        JSONObject christian = ethnicityObject.getJSONObject("Christian");

        positiveBar.add(new BarEntry(0, (float) ((float) Integer.parseInt(String.valueOf(male.get("pos"))) * 1.0)));
        positiveBar.add(new BarEntry(1, (float) ((float) Integer.parseInt(String.valueOf(female.get("pos"))) * 1.0)));
//        positiveBar.add(new BarEntry(2, (float) ((float) Integer.parseInt(String.valueOf(christian.get("pos"))) * 1.0)));

        negativeBar.add(new BarEntry(0, (float) ((float) Integer.parseInt(String.valueOf(male.get("neg"))) * 1.0)));
        negativeBar.add(new BarEntry(1, (float) ((float) Integer.parseInt(String.valueOf(female.get("neg"))) * 1.0)));
//        negativeBar.add(new BarEntry(2, (float) ((float) Integer.parseInt(String.valueOf(christian.get("neg"))) * 1.0)));

        neutralBar.add(new BarEntry(0, (float) ((float) Integer.parseInt(String.valueOf(male.get("neu"))) * 1.0)));
        neutralBar.add(new BarEntry(1, (float) ((float) Integer.parseInt(String.valueOf(female.get("neu"))) * 1.0)));
//        neutralBar.add(new BarEntry(2, (float) ((float) Integer.parseInt(String.valueOf(christian.get("neu"))) * 1.0)));
        addBarChart();

        JSONObject geographic = jsonObject.getJSONObject("contents").getJSONObject("geographic_sentiments");
        drawMap(geographic);

        showTweets(jsonObject.getJSONObject("contents").getJSONObject("important_tweets"));
        return jsonObject;
    }

    List<Integer> mapToList(Map<String, Map<String, Integer>> data) {

        List<Integer> countList = new ArrayList<>();

        for (String key : data.keySet()) {
            Map<String, Integer> tempData = data.get(key);
            countList.addAll(tempData.values());
        }

        Log.v("LineChart", countList.size() + "");
//        countList = reduceList(countList);
        return countList;
    }

    void shrink() {
        posCount = mapToList(positiveCount);
        negCount = mapToList(negativeCount);
        neuCount = mapToList(neutralCount);
        for (int i = 1; i < Math.max(posCount.size(), negCount.size()); i++) {
            posCount.set(i, posCount.get(i) + posCount.get(i - 1));
            negCount.set(i, negCount.get(i) + negCount.get(i - 1));
            neuCount.set(i, neuCount.get(i) + neuCount.get(i - 1));
        }
    }

    void showTweets(JSONObject jsonObject) {

        try {
            JSONArray posTweets = jsonObject.getJSONArray("pos");
            JSONArray negTweets = jsonObject.getJSONArray("neg");
            JSONArray neuTweets = jsonObject.getJSONArray("neu");

            positiveTweets = getTweets(posTweets);
            negativeTweets = getTweets(negTweets);
            negativeTweets = getTweets(neuTweets);

            Log.v("pos tweets", positiveTweets.size() + "");
            Log.v("neg tweets", negativeTweets.size() + "");
            Log.v("neu tweets", neutralTweets.size() + "");

            posAdapter = new TweetsAdapter(this, positiveTweets);
            posTweetsView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
//            posTweetsView.setItemAnimator(new DefaultItemAnimator());
            posTweetsView.setAdapter(posAdapter);
            negAdapter = new TweetsAdapter(this, negativeTweets);
            negTweetsView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
            negTweetsView.setAdapter(negAdapter);


            Log.v("tag", posAdapter.getItemCount() + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<String> getTweets(JSONArray array) throws JSONException {
        List<String> tempList = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            String string = StringEscapeUtils.unescapeJava(array.getJSONObject(i).getString("original_text"));
            tempList.add(string);
        }
        return tempList;
    }

    void drawMap(JSONObject object) {

        Map<String, State> states = Constants.statesMap;
        Log.v("States No : ", states.size() + "");
        for (State state : states.values()) {
            String name = state.getName();
            Log.v(name, "here");
            try {
                JSONObject jsonObject;
                if (object.has(state.getName())) {
                    jsonObject = object.getJSONObject(name);

                    state.setPositive(jsonObject.getInt("pos"));
                    state.setNegative(jsonObject.getInt("neg"));
                    state.setNeutral(jsonObject.getInt("neu"));
                    state.calculate();
                    statesList.add(state);

                    Log.v(state.getName(), state.getResult().toString());

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        indiaMap = new IndiaMap(this, statesList);
        Log.v("States No : ", statesList.size() + "");
        runnable.run();
    }

    Map<String, Map<String, Integer>> countJSON(JSONObject jsonObject) {

        Map<String, Map<String, Integer>> lineCounts = new HashMap<>();
        JSONArray array = jsonObject.names();
        int i, j;
        for (i = 0; i < array.length(); i++) {
            try {
                String s = String.valueOf(array.get(i));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(df.parse(s));

                String key = dateFromat.format(calendar.getTime());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                if (!lineCounts.containsKey(key)) {
                    Map<String, Integer> temp = initialize();
                    lineCounts.put(key, temp);
                } else {
                    Map<String, Integer> temp = lineCounts.get(key);
                    int count = temp.get(hour + "");
                    temp.put(hour + "", count + Integer.parseInt(String.valueOf(jsonObject.get(s))));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return lineCounts;
    }

    List<Integer> totalPie(JSONObject jsonObject) {
        List<Integer> total = new ArrayList<>();
        try {
            total.add((Integer) jsonObject.get("pos"));
            total.add((Integer) jsonObject.get("neg"));
            total.add((Integer) jsonObject.get("neu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return total;
    }

    Map<String, Integer> initialize() {
        Map<String, Integer> temp = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            temp.put(i + "", 0);
        }
        return temp;
    }

    }