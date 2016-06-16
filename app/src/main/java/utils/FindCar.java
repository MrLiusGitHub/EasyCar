package utils;

import java.util.List;

import bean.Car;

/**
 * Created by ldc on 2016/6/7.
 */
public class FindCar {
    public static String getLogoUrl(List<Car> mList, int rawPosition){
        if(rawPosition>0&&rawPosition<8){
            return mList.get(rawPosition-2).logoUrl;
        }else if(rawPosition>8 && rawPosition <28){
            return mList.get(rawPosition-3).logoUrl;
        }else if(rawPosition>28 && rawPosition<38){
            return mList.get(rawPosition-4).logoUrl;
        }else if(rawPosition>38 && rawPosition<52){
            return mList.get(rawPosition-5).logoUrl;
        }else if(rawPosition>52 && rawPosition<60){
            return mList.get(rawPosition-6).logoUrl;
        }else if(rawPosition>60 && rawPosition<69){
            return mList.get(rawPosition-7).logoUrl;
        }else if(rawPosition>69 && rawPosition<82){
            return mList.get(rawPosition-8).logoUrl;
        }else if(rawPosition>82 && rawPosition<94){
            return mList.get(rawPosition-9).logoUrl;
        }else if(rawPosition>94 && rawPosition<105){
            return mList.get(rawPosition-10).logoUrl;
        }else if(rawPosition>105 && rawPosition<124){
            return mList.get(rawPosition-11).logoUrl;
        }else if(rawPosition>124 && rawPosition<132){
            return mList.get(rawPosition-12).logoUrl;
        }else if(rawPosition>133 && rawPosition<135){
            return mList.get(rawPosition-13).logoUrl;
        }else if(rawPosition>135 && rawPosition<140){
            return mList.get(rawPosition-14).logoUrl;
        }else if(rawPosition>140 && rawPosition<143){
            return mList.get(rawPosition-15).logoUrl;
        }else if(rawPosition>143 && rawPosition<149){
            return mList.get(rawPosition-16).logoUrl;
        }else if(rawPosition>149 && rawPosition<152){
            return mList.get(rawPosition-17).logoUrl;
        }else if(rawPosition>152 && rawPosition<165){
            return mList.get(rawPosition-18).logoUrl;
        }else if(rawPosition>165 && rawPosition<169){
            return mList.get(rawPosition-19).logoUrl;
        }else if(rawPosition>169 && rawPosition<177){
            return mList.get(rawPosition-20).logoUrl;
        }else if(rawPosition>177 && rawPosition<184){
            return mList.get(rawPosition-21).logoUrl;
        }else if(rawPosition>184 && rawPosition<195){
            return mList.get(rawPosition-22).logoUrl;
        }else if(rawPosition>195 && rawPosition<205){
            return mList.get(rawPosition-23).logoUrl;
        }
        return "";
    }
}
