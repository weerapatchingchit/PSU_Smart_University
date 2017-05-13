package com.weerapat.psu_smart_university.Object;

/**
 * Created by Destiny'Jame on 11/6/2016.
 */

public class EventData {

    public String priKey,eventName,detail,faculty,place,date,startTime,endTime,phone,extLink,latitude,longitude,addId;

    public EventData(String priKey
                     , String eventName
                     , String detail
                     , String faculty
                     , String place
                     , String date
                     , String startTime
                     , String endTime
                     , String phone
                     , String extLink
                     , String latitude
                     , String longitude
                     , String addId )
    {

        this.priKey = priKey;
        this.eventName = eventName;
        this.detail = detail;
        this.faculty = faculty;
        this.place = place;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.phone = phone;
        this.extLink = extLink;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addId = addId;
    }


}
