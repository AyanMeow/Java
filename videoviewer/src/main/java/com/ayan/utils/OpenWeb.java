package com.ayan.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class OpenWeb {

    public static void openWeb(String videoBVCode,String proxySever)throws Exception
    {
        int[] videoInfo=getTime("https://api.bilibili.com/x/web-interface/view?bvid="+videoBVCode, "utf-8");
        System.setProperty("webdriver.edge.driver", "C:\\Users\\SuzikazeSakula\\Desktop\\system\\msedgedriver.exe");
        EdgeOptions edgeOptions=new EdgeOptions();
        edgeOptions.addArguments("--proxy-server="+proxySever);
        //edgeOptions.addArguments("--Referer=www.bilibili.com");
        WebDriver edgeDriver= new EdgeDriver(edgeOptions);
        try {
            Duration timeoDuration=Duration.ofSeconds(60);
            edgeDriver.manage().timeouts().pageLoadTimeout(timeoDuration);
            //edgeDriver.get("http://httpbin.org/get");
            //edgeDriver.get("https://upos-sz-mirrorhw.bilivideo.com/upgcxcode/93/42/788994293/788994293-1-32.flv?e=ig8euxZM2rNcNbRV7WdVhwdlhWdBhwdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=\u0026uipk=5\u0026nbs=1\u0026deadline=1659713526\u0026gen=playurlv2\u0026os=hwbv\u0026oi=1863538129\u0026trid=06cb803009424f37afc6241a48594666u\u0026mid=0\u0026platform=pc\u0026upsig=69d85e853f60bfc170bd16d97a70ece3\u0026uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform\u0026bvc=vod\u0026nettype=0\u0026orderid=0,3\u0026agrr=0\u0026bw=105172\u0026logo=80000000");
            edgeDriver.get("https://www.bilibili.com/video/"+videoBVCode);
            if(!edgeDriver.getTitle().equals("www.bilibili.com")){
                //WebElement doubleSpeed=edgeDriver.findElement(By.xpath("//button[@class='bilibili-player-video-btn-speed-name']"));
                edgeDriver.manage().window().minimize();
                Thread.sleep(videoInfo[0]*1000+20000);
                logOut(videoInfo, edgeDriver.getTitle());
            }
        } catch (Exception e) {
            //TODO: handle exception
            //e.printStackTrace();
        }finally{
            edgeDriver.quit(); 
        }
        
    }

    private static int[] getTime(String urlstr,String encode){
        //int durationTime=0;
        int[] videoInfo=new int[3];
        String temp = "";
	    StringBuffer pageContent = new StringBuffer();
        Pattern durationPattern=Pattern.compile("duration\":\\d+[0-9],");
        Pattern viewPattern=Pattern.compile("view\":\\d+[0-9],");
        Pattern cornPattern=Pattern.compile("corn\":\\d+[0-9],");
        Matcher durationMatcher,viewMatcher,conrMatcher;
        try {
            URL url=new URL(urlstr);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                           url.openStream(),Charset.forName(encode)));
		while((temp=br.readLine())!=null) {
			pageContent.append(temp);
		}   
        } catch (Exception e) {
            //TODO: handle exception
        }

        durationMatcher=durationPattern.matcher(pageContent.toString());
        viewMatcher=viewPattern.matcher(pageContent.toString());
        conrMatcher=cornPattern.matcher(pageContent.toString());
        while(durationMatcher.find()){
            videoInfo[0]=Integer.parseInt(durationMatcher.group()
                                .substring(10, durationMatcher.group().length()-1));
        }
        while(viewMatcher.find()){
            videoInfo[1]=Integer.parseInt(viewMatcher.group()
                                .substring(6,viewMatcher.group().length()-1));
        }
        while(conrMatcher.find()){
            videoInfo[2]=Integer.parseInt(conrMatcher.group()
                                .substring(6,conrMatcher.group().length()-1));                   
        }
        return videoInfo;
    }

    private static void logOut(int[] videoInfo,String videoTitle){
        String info="[-]video["+videoTitle+"]: view: "+videoInfo[1]+" corn: "+videoInfo[2]+";";
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String nowDate=format.format(date);
        File logFile=new File("LOG.txt");
        try {
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(logFile));
            bufferedWriter.newLine();
            bufferedWriter.write(nowDate+"  "+info);
        } catch (Exception e) {
            //TODO: handle exception
        }

    }
}
