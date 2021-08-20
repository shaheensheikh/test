package com.task;

import java.util.*;

public class TradeScheduler extends TimerTask {

    @Override
    public void run() {
        System.out.println("Scheduler started");
        Map<String, List<Trade>> map=TradeStore.tradeStoreMap;
        Set<String> keys=map.keySet();
        for(String key:keys){
            List<Trade> list=TradeStore.tradeStoreMap.get(key);
            ArrayList<Trade> tempTradeList=new ArrayList<>(list);
            for(Trade t:list){
                if(t.getExpired_flag().equalsIgnoreCase("Y")){
                    System.out.println("deleted trade : "+t.toString());
                    tempTradeList.remove(t);
                }
                if(tempTradeList.size()==0){
                    TradeStore.tradeStoreMap.remove(key);
                }else{
                    TradeStore.tradeStoreMap.put(key,tempTradeList);
                }
            }
        }
        System.out.println("Scheduler run successfully....");
    }
	
}
