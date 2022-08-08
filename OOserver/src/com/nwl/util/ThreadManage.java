package com.nwl.util;

import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;

public class ThreadManage {
    public static Dictionary<String,ChatThread> threadDictionary=new Hashtable<String,ChatThread>();

    public static Dictionary<String,Socket> socketDictionary=new Hashtable<String,Socket>();
}
