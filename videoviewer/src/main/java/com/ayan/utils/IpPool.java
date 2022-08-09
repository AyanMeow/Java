package com.ayan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class IpPool {
    public List<String> proxyIpList=new ArrayList<>();
    
    private String url89Proxy="http://api.89ip.cn/tqdl.html?api=1&num=50&port=&address=&isp=";
    private String urlKuai="https://free.kuaidaili.com/free/intr/";
    private String urlYun="http://www.ip3366.net/free/?stype=1&page=";
    private String urlQiYun="https://proxy.ip3366.net/free/?action=china&page=";
    private String urlQiYunVip="http://dev.qydailiip.com/api/?apikey=89c83413de6d6c80436be92b46891a081f052bc6&num=300&type=text&line=win&proxy_type=putong&sort=rand&model=all&protocol=https&address=&kill_address=&port=&kill_port=&today=true&abroad=1&isp=&anonymity=";
    //private String urlTaiYang="https://www.tyhttp.com/free/page";//cant use
    private String urlZdaye="https://www.zdaye.com/free/";
    private String urlNima="http://www.nimadaili.com/https/";

    private String usingURLforTable=urlQiYun;
    private String usingURLforTXT=url89Proxy;

    public IpPool(){
        
    }
    public void getTextProxyIp() throws IOException{
        Document document=Jsoup.connect(usingURLforTXT).get();
        Element element=document.body();
        String temp=element.text();
        String[] ips=temp.split(" ");
        for (int i = 0; i < ips.length-1; i++) {
            proxyIpList.add(ips[i]);
        }

        File localFile=new File("http.txt");
        BufferedReader bufferedReader=new BufferedReader(new FileReader(localFile));
        String ip=null;
        try {
            while((ip=bufferedReader.readLine())!=null){
                proxyIpList.add(ip);
            }
        } catch (Exception e) {
            //TODO: handle exception
        }finally{
            bufferedReader.close();
        }

        System.out.println("[-]Local ips load complete;");
    }

    public void getProxyIp() throws IOException{
        System.out.print("[-]Start Initialization ...");
        for(int j=1;j<=50;j++){
            System.out.print(".");
            //Document document=Jsoup.connect(urlKuai+j+"/").get();
            Document document=Jsoup.connect(usingURLforTable+j).get();
            Elements trElements=document.select("table").select("tr");
            for (int i = 1; i < trElements.size(); i++) {
                Elements tdElements=trElements.get(i).select("td");
                String ip_port=tdElements.get(0).text()+":"+tdElements.get(1).text();
                if(usingURLforTable.equals(urlNima)){
                    ip_port=tdElements.get(0).text();
                }
                proxyIpList.add(ip_port);
            }
        }
        System.out.println("complete.");
    }
    
    public void ipCheck() {
        System.out.print("[-]Start Checking IpPool ...");
        int stauateCode=0;
        for (int i=0;i<proxyIpList.size();i++) {
            System.out.print(".");
            String[] temp=proxyIpList.get(i).split(":");
            stauateCode=0;
            try {
                HttpHost proxy=new HttpHost(temp[0], Integer.parseInt(temp[1]));
                CloseableHttpClient httpClient=HttpClients.createDefault();
                RequestConfig requestConfig=RequestConfig.custom().setConnectTimeout(5000)
                                            .setSocketTimeout(5000)
                                            .setProxy(proxy).build();
                HttpGet httpget = new HttpGet("http://www.bilibili.com/");
                httpget.setConfig(requestConfig);
                HttpGet httpInfo= new HttpGet("http://httpbin.org/get");
                httpInfo.setConfig(requestConfig);
                HttpResponse infoResponse=httpClient.execute(httpInfo);
                HttpEntity infoEntity=infoResponse.getEntity();
                String infoContent=EntityUtils.toString(infoEntity, "utf-8");
                System.out.println(infoContent);
                HttpResponse httpResponse= httpClient.execute(httpget);
                stauateCode = httpResponse.getStatusLine().getStatusCode();

                if(stauateCode!=200){
                    proxyIpList.remove(i);
                }  
            } catch (Exception e) {
                //TODO: handle exception
                //e.printStackTrace();
                proxyIpList.remove(i);
            }
        }
        System.out.println("complete.Remain ip :"+proxyIpList.size());
    }
}
