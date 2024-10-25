package com.letianpai.robot.components.parser.appstatus

/**
 * app消息
 */
class AutoAppInfo {
    var app_id: Int = 0
    var app_version: String? = null
    var upgrade_status: Int = 0
    var packageName: String? = null

    override fun toString(): String {
        return "{" +
                "app_id:" + app_id +
                ", app_version:'" + app_version + '\'' +
                ", upgrade_status:" + upgrade_status +
                ", packageName:'" + packageName + '\'' +
                '}'
    }
}
