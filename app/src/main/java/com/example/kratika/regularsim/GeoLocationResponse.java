package com.example.kratika.regularsim;

public class GeoLocationResponse
{
    private String result;
    private GeoLocationData data;

    public GeoLocationData getData()
    {
        return data;
    }

    public void setData(GeoLocationData data)
    {
        this.data = data;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
