package com.blazeey.sentimentanalysis.Model;

/**
 * Created by venki on 24/3/18.
 */

public class State {
    public enum Result { POSITIVE,NEGATIVE,NEUTRAL,NONE }

    private String name;
    private Integer positive,negative,neutral;
    private Result result;
    private Integer imagePositive,imageNegative,imageNeutral,imageNone;

    public State(String name, Integer positive, Integer negative, Integer neutral,Integer noData) {
        this.name = name;
        this.positive=0;
        this.negative=0;
        this.neutral=0;
        this.imagePositive = positive;
        this.imageNegative = negative;
        this.imageNeutral = neutral;
        this.imageNone = noData;
    }

    public Integer getImageNone() {
        return imageNone;
    }

    public void calculate(){
        if(positive==0&&negative==0&&neutral==0)
            this.result = Result.NONE;
        else if(this.positive>=this.negative&&this.positive>=this.neutral)
            this.result = Result.POSITIVE;
        else if(this.negative>=this.neutral)
            this.result = Result.NEGATIVE;
        else
            this.result = Result.NEUTRAL;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }

    public void setNeutral(Integer neutral) {
        this.neutral = neutral;
    }

    public String getName() {
        return name;
    }

    public Integer getPositive() {
        return positive;
    }

    public Integer getNegative() {
        return negative;
    }

    public Integer getNeutral() {
        return neutral;
    }

    public Result getResult() {
        return result;
    }

    public Integer getImagePositive() {
        return imagePositive;
    }

    public Integer getImageNegative() {
        return imageNegative;
    }

    public Integer getImageNeutral() {
        return imageNeutral;
    }
}
