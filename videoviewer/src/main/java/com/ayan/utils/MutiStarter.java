package com.ayan.utils;

public class MutiStarter implements Runnable {

    private static int listCount=-1;
    
    public IpPool ipPool;
    public String codeBV;

    {
        listCount++;
    }

    public MutiStarter(String bv, IpPool ips){
        codeBV=bv;
        ipPool=ips;
    }

    public void run(){
        String proxy=ipPool.proxyIpList.get(listCount);
        try {
            OpenWeb.openWeb(codeBV, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getListCount() {
        return listCount;
    }
    
}