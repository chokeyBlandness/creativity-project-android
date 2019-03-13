package usst.dysrc.kaoyan.utils

import java.util.*

class DateUtil{
    companion object {
        /**
         * get the last day of the current month
         * @return the number of the last day
         */
        fun getCalendarMonthLastDay(calendar: Calendar): Int {
            calendar.set(Calendar.DATE, 1)//把日期设置为当月第一天
            calendar.roll(Calendar.DATE, -1)//日期回滚一天，也就是最后一天
            return calendar.get(Calendar.DATE)
        }

        /**
         * get the week number of the first day of current month
         * @return the week number
         */
        fun getFirstDayOfMonth(calendar: Calendar): Int {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        /**
         * get the month of the current date
         * @return the number of the month
         */
        fun getCalendarMonth(calendar: Calendar): Int {
            return calendar.get(Calendar.MONTH)
        }

        /**
         * get the year of the current date
         * @return the number of the year
         */
        fun getCalendarYear(calendar: Calendar): Int {
            return calendar.get(Calendar.YEAR)
        }
    }


}