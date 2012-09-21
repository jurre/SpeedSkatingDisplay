package com.example;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/20/12
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class Schedule {
    private List<LapData> lapDatas;
    private String name;
    
    public Schedule (List<LapData> lapDatas, String name)
    {
    	this.lapDatas = lapDatas;
    	this.setName(name);    	
    }

    public String[] getRoundNumbers(){
        String[] rounds= new String[lapDatas.size()];
        for(int i = 0; i<lapDatas.size(); i++){
            rounds[i] = lapDatas.get(i).getRoundnumber();
        }
        return rounds;
    }
    
    public LapData getRound(int roundNumber)
    {
    	return lapDatas.get(roundNumber);
    }

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
   
}
