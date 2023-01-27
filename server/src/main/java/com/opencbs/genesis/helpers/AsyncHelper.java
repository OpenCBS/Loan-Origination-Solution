package com.opencbs.genesis.helpers;


/**
 * Created by alopatin on 12-Apr-17.
 */
public class AsyncHelper {
    public static void runAsync(Runnable r){
        new Thread(r).start();
    }
}
