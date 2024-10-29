package com.letianpai.robot.components.storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.Context
import java.util.ArrayList
import java.util.HashSet

/**
 * 机器人 偏好设置管理器
 * @author liujunbin
 */
class RobotSubConfigManager private constructor(private val mContext: Context) :
    RobotSubConfigConst {
    private val mRobotSharedPreference = RobotSubSharedPreference(
        mContext,
        RobotSubSharedPreference.Companion.SHARE_PREFERENCE_NAME,
        RobotSubSharedPreference.Companion.ACTION_INTENT_CONFIG_CHANGE
    )
    private val gson = Gson()


    private fun initKidSmartConfigState() {
    }

    fun commit(): Boolean {
        return mRobotSharedPreference.commit()
    }

    var updateTime: Long
        get() = mRobotSharedPreference.getLong(
            RobotSubConfigConst.Companion.KEY_UPDATE_ROBOT_TIME,
            0L
        )
        set(time) {
            mRobotSharedPreference.putLong(
                RobotSubConfigConst.Companion.KEY_UPDATE_ROBOT_TIME,
                time
            )
        }

    val uploadFrequency: Long
        get() = mRobotSharedPreference.getInt(RobotSubConfigConst.Companion.KEY_UPLOAD_FREQUENCY, 1)
            .toLong()

    fun setUploadFrequency(frequency: Int) {
        mRobotSharedPreference.putInt(RobotSubConfigConst.Companion.KEY_UPLOAD_FREQUENCY, frequency)
    }

    var uploadFrequencyInternalTime: Long
        get() = mRobotSharedPreference.getLong(
            RobotSubConfigConst.Companion.KEY_UPLOAD_FREQUENCY_INTERNAL_TIME,
            0L
        )
        set(time) {
            mRobotSharedPreference.putLong(
                RobotSubConfigConst.Companion.KEY_UPLOAD_FREQUENCY_INTERNAL_TIME,
                time
            )
        }

    var uploadDataTime: Long
        get() = mRobotSharedPreference.getLong(
            RobotSubConfigConst.Companion.KEY_UPLOAD_DATA_TIME,
            0L
        )
        set(time) {
            mRobotSharedPreference.putLong(RobotSubConfigConst.Companion.KEY_UPLOAD_DATA_TIME, time)
        }

    var appList: String?
        get() = mRobotSharedPreference.getString(
            RobotSubConfigConst.Companion.KEY_SAVE_APP_LIST,
            RobotSubConfigConst.Companion.VALUE_DEFAULT_APP_LIST
        )
        set(appList) {
            mRobotSharedPreference.putString(
                RobotSubConfigConst.Companion.KEY_SAVE_APP_LIST,
                appList!!
            )
        }

    var speechCommandSwitch: Boolean
        get() = true
        //        return mRobotSharedPreference.getBoolean(KEY_SPEECH_COMMAND_SWITCH,true);
        set(speechCommandStatus) {
            mRobotSharedPreference.putBoolean(
                RobotSubConfigConst.Companion.KEY_SPEECH_COMMAND_SWITCH,
                speechCommandStatus
            )
        }

    var openMainViewTime: Long
        get() = mRobotSharedPreference.getLong(RobotSubConfigConst.Companion.KEY_SAVE_OPEN_TIME, 0)
        set(currentTimeMillis) {
            mRobotSharedPreference.putLong(
                RobotSubConfigConst.Companion.KEY_SAVE_OPEN_TIME,
                currentTimeMillis
            )
        }

    val isNeedRegisterWifi: Boolean
        get() {
            val currentTime = System.currentTimeMillis()
            return if ((currentTime - openMainViewTime) > 10 * 1000) {
                true
            } else {
                false
            }
        }

    fun saveUserPackageList(list: List<String?>?) {
        val json = gson.toJson(list)
        mRobotSharedPreference.putString(RobotSubConfigConst.Companion.KEY_SAVE_PACKAGE_LIST, json)
    }

    fun addUserPackage(packageName: String?) {
        var packageList = userPackageList
        if (packageList == null) {
            packageList = ArrayList()
        }
        if (!packageList.contains(packageName)) {
            packageList.add(packageName)
            saveUserPackageList(packageList)
        }
    }

    fun removeUserPackage(packageName: String?) {
        var packageList = userPackageList
        if (packageList == null) {
            packageList = ArrayList()
        }
        packageList.remove(packageName)
        saveUserPackageList(packageList)
    }

    val userPackageList: MutableList<String?>
        get() {
            val json = mRobotSharedPreference.getString(
                RobotSubConfigConst.Companion.KEY_SAVE_PACKAGE_LIST,
                null
            )
            val gson = Gson()
            val type = object : TypeToken<ArrayList<String?>?>() {}.type
            return gson.fromJson(json, type)
        }
    val userPackageListSize: Int
        get() {
            val userList: List<String?> = userPackageList
            return userList?.size ?: 0
        }

    fun resetUserPackageList() {
        val userAppList = HashSet<String?>()
        val getUserPackageList: List<String?> = userPackageList
        if (getUserPackageList != null && getUserPackageList.size > 0) {
            for (i in getUserPackageList.indices) {
                userAppList.add(getUserPackageList[i])
            }
            val appList: MutableList<String?> = ArrayList()

            for (value in userAppList) {
                appList.add(value)
            }
            saveUserPackageList(appList)
            commit()
        }
    }


    companion object {
        private var mRobotConfigManager: RobotSubConfigManager? = null
        fun getInstance(context: Context?): RobotSubConfigManager? {
            if (mRobotConfigManager == null) {
                mRobotConfigManager = RobotSubConfigManager(context!!)
                mRobotConfigManager!!.initKidSmartConfigState()
                mRobotConfigManager!!.commit()
            }
            return mRobotConfigManager
        }
    }
}