package com.strong.utils.remind


import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.text.TextUtils
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/** 日历 */
class CalendarReminderUtils {

    private val TAG = "CalendarReminderUtils"

    private val CALENDER_URL = "content://com.android.calendar/calendars"
    private val CALENDER_EVENT_URL = "content://com.android.calendar/events"
    private val CALENDER_REMINDER_URL = "content://com.android.calendar/reminders"

    private val CALENDARS_NAME = "strong"
    private val CALENDARS_ACCOUNT_NAME = "strong@qq.com"
    private val CALENDARS_ACCOUNT_TYPE = "com.android.mq"
    private val CALENDARS_DISPLAY_NAME = "strong"

    //时间正则 yyyy-MM-dd HH:mm
    private val REGEX_DATE =
        "\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2} ([0-1]?[0-9]|2[0-3]):([0-5][0-9])\$"

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    // BYDAY MO（周一）， TU（周二）， WE（周三）， TH（周四）， FR（周五）， SA（周六）， SU（周日）
    private val byDayArray = arrayOf("MO", "TU", "WE", "TH", "FR", "SA", "SU")

    // BYMONTHDAY 月-天
    private val byMonthDayArray = arrayOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31
    )

    // BYMONTH 年-月
    private val byMonthArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    private var isLog: Boolean = true

    companion object {
        private val utils by lazy { CalendarReminderUtils() }
        fun getInstance(): CalendarReminderUtils = utils
    }

    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
     * 获取账户成功返回账户id，否则返回-1
     */
    private fun checkAndAddCalendarAccount(context: Context): Int {
        val oldId: Int = checkCalendarAccount(context)
        log("checkAndAddCalendarAccount oldId $oldId")
        return if (oldId >= 0) {
            oldId
        } else {
            val addId: Long = addCalendarAccount(context)
            if (addId >= 0) {
                checkCalendarAccount(context)
            } else {
                -1
            }
        }
    }

    /**
     * 检查是否存在现有账户，存在则返回账户id，否则返回-1
     */
    @SuppressLint("Range")
    private fun checkCalendarAccount(context: Context): Int {
        val userCursor =
            context.contentResolver.query(Uri.parse(CALENDER_URL), null, null, null, null)
        return userCursor.use { userCursor ->
            //部分手机没权限(会返回null)，系统会返回有权限
            if (userCursor == null) {
                return -1
            }
            val count = userCursor.count
            if (count > 0) { //存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst()
                userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID))
            } else {
                -1
            }
        }
    }

    /**
     * 添加日历账户，账户创建成功则返回账户id，否则返回-1
     */
    private fun addCalendarAccount(context: Context): Long {
        val timeZone: TimeZone = TimeZone.getDefault()
        val value = ContentValues()
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME)
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME)
        value.put(CalendarContract.Calendars.VISIBLE, 1)//设置日历可见
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
        //使用的权限等级
        value.put(
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.CAL_ACCESS_OWNER
        )
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1)//同步到系统
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID())
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME)
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)
        var calendarUri = Uri.parse(CALENDER_URL)
        calendarUri = calendarUri.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
            .build()
        val result = context.contentResolver.insert(calendarUri, value)
        log("addCalendarAccount result $result")
        return if (result == null) -1 else ContentUris.parseId(result)
    }

    /**
     * 添加日历
     */
    fun addCalendar(context: Context?): Boolean {
        var b = false
        val dateStr = "2022-03-10 12:00"
        val date = parse(dateStr)
        if (date != null) {
            b = addCalendarEvent(
                context,
                "提醒标题",
                "提醒描述",
                date.time,
                getRepeatEventVal(RemindRepeatType.EVERY_DAY.value)
            )
        }
        return b
    }

    /**
     *  获取重复规则
    FREQ:重复类型
    INTERVAL:重复间隔数
    UNTIL:通过日期-时间值来限制重复
    COUNT:通过次数来限制重复
    WKST:每周开始于SU周日
    BYDAY: MO（周一），TU（周二），WE（周三），TU（周四），FR（周五），SA（周六），SU（周日）多个值用逗号分隔
    BYMONTHDAY: 月-天 1、2、3...
    BYMONTH: 年-月 1、2、3...
     */
    private fun getRepeatEventVal(
        repeatType: Int,
        customRepeatFreq: Int? = 1,
        customRepeatInterval: Int? = 0,
        customRepeatItems: List<Int>? = null
    ): String? {
        log("repeatType $repeatType")
        val rule = StringBuilder()
        when (repeatType) {
            //永不
            RemindRepeatType.NEVER.value -> {
                return null
            }
            //每小时
            RemindRepeatType.EVERY_HOUR.value -> {
                rule.append("FREQ=HOURLY;INTERVAL=1")
            }
            //每天
            RemindRepeatType.EVERY_DAY.value -> {
                rule.append("FREQ=DAILY;INTERVAL=1")
            }
            //工作日
            RemindRepeatType.EVERY_WORK_DAY.value -> {
                rule.append("FREQ=WEEKLY;INTERVAL=1")
                for (i in 0 until 5) {
                    if (i <= byDayArray.size) {
                        if (i == 0) {
                            rule.append(";BYDAY=${byDayArray[i]}")
                        } else {
                            rule.append(",${byDayArray[i]}")
                        }
                    }
                }
            }
            //周末
            RemindRepeatType.EVERY_WEEKEND.value -> {
                // MO（周一）， TU（周二）， WE（周三）， TH（周四）， FR（周五）， SA（周六）， SU（周日）
                rule.append("FREQ=WEEKLY;INTERVAL=1;BYDAY=${byDayArray[5]},${byDayArray[6]}")
            }
            //每周
            RemindRepeatType.EVERY_WEEK.value -> {
                rule.append("FREQ=WEEKLY;INTERVAL=1")
            }
            //每两周
            RemindRepeatType.EVERY_TWO_WEEKS.value -> {
                rule.append("FREQ=WEEKLY;INTERVAL=2")
            }
            //每月
            RemindRepeatType.EVERY_MONTH.value -> {
                rule.append("FREQ=MONTHLY;INTERVAL=1")
            }
            //每3个月
            RemindRepeatType.EVERY_THREE_MONTHS.value -> {
                rule.append("FREQ=MONTHLY;INTERVAL=3")
            }
            //每6个月
            RemindRepeatType.EVERY_SIX_MONTHS.value -> {
                rule.append("FREQ=MONTHLY;INTERVAL=6")
            }
            //每年
            RemindRepeatType.EVERY_YEAR.value -> {
                rule.append("FREQ=YEARLY;INTERVAL=1")
            }
            //自定义
            RemindRepeatType.CUSTOM.value -> {
                log("customRepeatFreq $customRepeatFreq")
                //0：小时 1：日 2：周 3：月 4：年
                when (customRepeatFreq) {
                    //0 -> rule.append("FREQ=HOURLY;INTERVAL=$customRepeatInterval")
                    1 -> rule.append("FREQ=DAILY;INTERVAL=$customRepeatInterval")
                    2 -> {
                        rule.append("FREQ=WEEKLY;INTERVAL=$customRepeatInterval")
                        customRepeatItems?.let {
                            for (i in it.indices) {
                                val index = it[i]-1
                                if (index <= byDayArray.size) {
                                    if (i == 0) {
                                        rule.append(";BYDAY=${byDayArray[index]}")
                                    } else {
                                        rule.append(",${byDayArray[index]}")
                                    }
                                }
                            }
                        }
                    }
                    3 -> {
                        rule.append("FREQ=MONTHLY;INTERVAL=$customRepeatInterval")
                        customRepeatItems?.let {
                            for (i in it.indices) {
                                val index = it[i]-1
                                if (index <= byMonthDayArray.size) {
                                    if (i == 0) {
                                        rule.append(";BYMONTHDAY=${byMonthDayArray[index]}")
                                    } else {
                                        rule.append(",${byMonthDayArray[index]}")
                                    }
                                }
                            }
                        }
                    }
                    4 -> {
                        rule.append("FREQ=YEARLY;INTERVAL=$customRepeatInterval")
                        customRepeatItems?.let {
                            for (i in it.indices) {
                                val index = it[i]-1
                                if (index <= byMonthArray.size) {
                                    if (i == 0) {
                                        rule.append(";BYMONTH=${byMonthArray[index]}")
                                    } else {
                                        rule.append(",${byMonthArray[index]}")
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        //每周开始，必须加
        rule.append(";WKST=SU")
        return rule.toString()
    }

    /**
     * 添加日历事件
     */
    private fun addCalendarEvent(
        context: Context?,
        reminderTitle: String?,
        description: String?,
        reminderTime: Long,
        rule: String? = null,
    ): Boolean {
        log("addCalendarEvent $rule")
        if (context == null) {
            return false
        }
        val calId = checkAndAddCalendarAccount(context) //获取日历账户的id
        log("addCalendarEvent calId $calId")
        if (calId < 0) { //获取账户id失败直接返回，添加日历事件失败
            return false
        }

        //添加日历事件
        val mCalendar = Calendar.getInstance()
        mCalendar.timeInMillis = reminderTime //设置开始时间
        val start = mCalendar.time.time
//        mCalendar.timeInMillis = start + 10 * 60 * 1000 //设置终止时间，开始时间加10分钟
//        val end = mCalendar.time.time
        val event = ContentValues()
        event.put("title", reminderTitle)
        event.put("description", description)
        event.put("calendar_id", calId) //插入账户的id
        event.put(CalendarContract.Events.DTSTART, start)//开始时间
        //结束时间 ，如果事件是每天/周,那么就没有结束时间
        event.put(CalendarContract.Events.DTEND, start)
        event.put(CalendarContract.Events.HAS_ALARM, 1) //设置有闹钟提醒
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id) //时区
        if (rule != null) {
            event.put(CalendarContract.Events.RRULE, rule)
        }
        val calendarEvent = context.contentResolver.insert(Uri.parse(CALENDER_EVENT_URL), event)
            ?: return false
        log("addCalendarEvent newEvent $calendarEvent")
        //事件提醒的设定
        val values = ContentValues()
        values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(calendarEvent))
        values.put(CalendarContract.Reminders.MINUTES, 0) // 提前几分钟提醒

        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)//提醒次数
        val remindUri = context.contentResolver.insert(Uri.parse(CALENDER_REMINDER_URL), values)
        log("addCalendarEvent uri $remindUri")
        return remindUri != null
    }

    /**
     * 检查日历事件
     */
    @SuppressLint("Range")
    fun checkCalendarEvent(
        context: Context?,
        title: String?,
        description: String?,
        startTime: Long,
        endTime: Long
    ): Boolean {
        if (context == null) {
            return false
        }
        val eventCursor: Cursor? =
            context.contentResolver.query(Uri.parse(CALENDER_EVENT_URL), null, null, null, null)
        eventCursor.use { eventCursor ->
            if (eventCursor == null) { //查询返回空值
                return false
            }
            if (eventCursor.count > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                var eventTitle: String
                var eventDescription: String
                var eventStartTime: Long
                var eventEndTime: Long
                while (eventCursor.moveToNext()) {
                    eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"))
                    eventDescription =
                        eventCursor.getString(eventCursor.getColumnIndex("description"))
                    eventStartTime =
                        eventCursor.getString(eventCursor.getColumnIndex("dtstart")).toLong()
                    eventEndTime =
                        eventCursor.getString(eventCursor.getColumnIndex("dtend")).toLong()
                    if (title != null &&
                        title == eventTitle &&
                        description != null &&
                        description == eventDescription &&
                        startTime == eventStartTime &&
                        endTime == eventEndTime
                    ) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 删除日历事件
     */
    @SuppressLint("Range")
    fun deleteCalendarEvent(context: Context?, title: String): Boolean {
        if (context == null) {
            return false
        }
        val eventCursor =
            context.contentResolver.query(Uri.parse(CALENDER_EVENT_URL), null, null, null, null)
        eventCursor.use { eventCursor ->
            //部分手机没权限(会返回null)，系统会返回有权限
            if (eventCursor == null) {
                return false
            }
            log("deleteCalendarEvent eventCursor $eventCursor")
            if (eventCursor.count > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                eventCursor.moveToFirst()
                while (!eventCursor.isAfterLast) {
                    val eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"))
                    log("deleteCalendarEvent eventTitle $eventTitle")
                    //删除此标题的日程
                    if (!TextUtils.isEmpty(title) && title == eventTitle) {
                        val id =
                            eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID)) //取得id
                        val deleteUri =
                            ContentUris.withAppendedId(Uri.parse(CALENDER_EVENT_URL), id.toLong())
                        val rows = context.contentResolver.delete(deleteUri, null, null)
                        if (rows == -1) { //事件删除失败
                            return false
                        }
                        //return true
                    }
                    eventCursor.moveToNext()
                }
            }
        }
        return false
    }

    fun update() {

    }

    /** 是否为时间格式 */
    private fun isDate(dateStr: String): Boolean {
        val isDate = Pattern.matches(REGEX_DATE, dateStr)
        log("isDate dateStr $dateStr isDate $isDate")
        return isDate
    }

    fun parse(dateStr: String): Date? {
        var date: Date? = null
        if (isDate(dateStr)) {
            date = dateFormat.parse(dateStr)
        }
        return date
    }

    private fun log(str: String) {
        if (isLog) {
            Log.v(TAG, str)
        }
    }

    enum class RemindRepeatType(val value: Int) {
        NEVER(0),
        EVERY_HOUR(1),
        EVERY_DAY(2),
        EVERY_WORK_DAY(3),
        EVERY_WEEKEND(4),
        EVERY_WEEK(5),
        EVERY_TWO_WEEKS(6),
        EVERY_MONTH(7),
        EVERY_THREE_MONTHS(8),
        EVERY_SIX_MONTHS(9),
        EVERY_YEAR(10),
        CUSTOM(11);
    }

}