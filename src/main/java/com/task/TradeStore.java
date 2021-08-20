package com.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TradeStore {

    public static Map<String, List<Trade>> tradeStoreMap=new HashMap<String,List<Trade>>();
    public static DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/YYYY");


    static{
        LocalDate maturityDate=LocalDate.parse("2021-08-19");
        tradeStoreMap.put("T1", Arrays.asList(
                new Trade("T1",1,"CP-1","B1",maturityDate,LocalDate.now(),"N"),
                new Trade("T1",2,"CP-2","B2",maturityDate,LocalDate.now(),"Y")));
        tradeStoreMap.put("T2", Arrays.asList(new Trade("T2",1,"CP-3","B2",maturityDate,LocalDate.now(),"Y")));
    }

    public static void main(String args[]){
        LocalDate maturityDate=LocalDate.parse("2021-08-20");
        Trade trade=new Trade("T2",2,"CP-2","B2",maturityDate,LocalDate.now(),"Y");
        processTrade(trade);
        System.out.println("Map >>> "+tradeStoreMap);

        // Scheduler for deleting trade which has flag value 'Y'
        TradeScheduler tradeScheduler=new TradeScheduler();

        Timer timer=new Timer();
        timer.scheduleAtFixedRate(tradeScheduler,5000,36000);
    }

    public static void processTrade(Trade trade){
        if(versionIdValidation(trade)){
            if(maturityDateValidation(trade.getMaturity_date())){
                String tradeId=trade.getTrade_id();
                int versionId=trade.getVersion();
                List<Trade> oldTradeList=new ArrayList<Trade>();
                if(tradeStoreMap.containsKey(tradeId)){
                    List<Trade> tempTradeList=tradeStoreMap.get(tradeId);
                    oldTradeList=new ArrayList<>(tempTradeList);
                    if(!tempTradeList.isEmpty()){
                        for(int i=0;i<tempTradeList.size();i++){
                            Trade t=tempTradeList.get(i);
                            if(t.getVersion()==versionId){
                                oldTradeList.remove(i);
                            }
                        }
                    }
                }
                oldTradeList.add(trade);
                tradeStoreMap.put(tradeId,oldTradeList);
                System.out.println("Trade Successfully stored..");
            }else{
                throw new RuntimeException("maturity date should not be less");
            }
        }else{
            throw new RuntimeException("version should be greater");
        }
    }

    public static boolean versionIdValidation(Trade trade){
        if(tradeStoreMap.containsKey(trade.getTrade_id())){
            List<Trade> trades=tradeStoreMap.get(trade.getTrade_id());
            int version=trades.stream().mapToInt(t->t.getVersion()).max().orElse(0);
            for(Trade t:trades){
                if(trade.getVersion()<version ||(trade.getVersion()!=version && trade.getVersion()<version)){
                    return false;
                }else{
                    return true;
                }
            }
        }else{
            return true;
        }
        return false;
    }

    public static boolean maturityDateValidation(LocalDate maturityDate){
        if(maturityDate.isBefore(LocalDate.now())){
            return false;
        }
        return true;
    }

}
