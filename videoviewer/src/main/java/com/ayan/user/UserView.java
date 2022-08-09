package com.ayan.user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import com.ayan.utils.*;;

public class UserView {
    private static final int threadMAX=3;
    private static List<String> videoBVcodes=new ArrayList<>();
    public static void main(String[] args) {
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(new File("bvcode.txt")));
            String bv=null;
            while((bv=bufferedReader.readLine())!=null){
                videoBVcodes.add(bv);
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        IpPool ipPool=new IpPool();
        try {
            ipPool.getProxyIp();
            ipPool.getTextProxyIp();
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }

        //test
        System.out.println("-----------TEST PART---------");
        for (String ip : ipPool.proxyIpList) {
            try {
                OpenWeb.openWeb(videoBVcodes.get(0), ip);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        System.out.println("-----------STAT RUNNING---------");
        if(ipPool.proxyIpList.size()!=0){
            List<Thread> threads=new ArrayList();
            int bvCount=0;
            while(true){

                for(int i=threads.size();i<threadMAX;i++){
                    threads.add(new Thread(new MutiStarter(videoBVcodes.get((bvCount++)%videoBVcodes.size()),ipPool)));
                    threads.get(i).start();
                }

                for (int i = 0; i < threads.size(); i++) {
                    if(!threads.get(i).isAlive()){
                        threads.remove(i);
                    }
                }

                if(MutiStarter.getListCount()>=ipPool.proxyIpList.size())
                    break;
            }
        }
}

}
