package com.letianpai.robot.components.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.letianpai.robot.components.charging.BatteryReceiver;

/**
 * 广播管理器
 *
 * @author liujunbin
 */
public class BroadcastReceiverManager {

    public Context mContext;
    private static BroadcastReceiverManager instance;

    public static BroadcastReceiverManager getInstance(Context context) {
        synchronized (BroadcastReceiverManager.class) {
            if (instance == null) {
                instance = new BroadcastReceiverManager(context.getApplicationContext());
            }
            return instance;
        }
    }

    public BroadcastReceiverManager(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        // TODO 增加需要监听的广播进行初始化
        // TODO 此处为需要监听状态的统一入口，唯一的监听位置，后续需要状态的，统一在此处进行监听后进行分发
        setBatteryListener();
    }

    //电池监听
    private void setBatteryListener() {
        BatteryReceiver batteryReceiver = new BatteryReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        mContext.registerReceiver(batteryReceiver, intentFilter);
    }

//    private void setNetWorkChangeListener() {
//        NetWorkChangeReceiver netChangeReceiver = new NetWorkChangeReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
////        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        mContext.registerReceiver(netChangeReceiver, intentFilter);
//    }
//
//    private void setWifiChangeListener() {
//        WifiReceiver wifiReceiver = new WifiReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        mContext.registerReceiver(wifiReceiver, intentFilter);
//    }
//
//    private void setTimeListener() {
//        TimerReceiver mTimeReceiver = new TimerReceiver();
//        IntentFilter timeFilter = new IntentFilter();
//        timeFilter.addAction(Intent.ACTION_TIME_TICK);
//        mContext.registerReceiver(mTimeReceiver, timeFilter);
//    }




}
