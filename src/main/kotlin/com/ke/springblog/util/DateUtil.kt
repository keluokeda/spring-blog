package com.ke.springblog.util

import java.util.*
import java.util.Calendar
import java.text.SimpleDateFormat
import jdk.nashorn.internal.objects.NativeDate.getDay
import jdk.nashorn.internal.objects.NativeDate.getYear
import jdk.nashorn.internal.objects.NativeDate.getDay


object DateUtil {


	fun dateformatTime(date: Date): String {
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//转换为日期
		val time = date.time
		if (isThisYear(date)) {//今年
			val simpleDateFormat = SimpleDateFormat("HH:mm")
			if (isToday(date)) { //今天
				var minute = minutesAgo(time)
				return if (minute < 60) {//1小时之内
					if (minute <= 1) {//一分钟之内，显示刚刚
						"刚刚"
					} else {
						"$minute 分钟前"
					}
				} else {
					simpleDateFormat.format(date)
				}
			} else {
				when {
					isYestYesterday(date) -> //昨天，显示昨天
						return "昨天 " + simpleDateFormat.format(date)
					isThisWeek(date) -> {//本周,显示周几
						var weekday: String? = null
						if (date.day === 1) {
							weekday = "周一"
						}
						if (date.day === 2) {
							weekday = "周二"
						}
						if (date.day === 3) {
							weekday = "周三"
						}
						if (date.day === 4) {
							weekday = "周四"
						}
						if (date.day === 5) {
							weekday = "周五"
						}
						if (date.day === 6) {
							weekday = "周六"
						}
						if (date.day === 0) {
							weekday = "周日"
						}
						return weekday + " " + simpleDateFormat.format(date)
					}
					else -> {
						val sdf = SimpleDateFormat("MM-dd HH:mm")
						return sdf.format(date)
					}
				}
			}
		} else {
			val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
			return sdf.format(date)
		}
	}

	private fun minutesAgo(time: Long): Int {
		return ((System.currentTimeMillis() - time) / 60000).toInt()
	}

	private fun isToday(date: Date): Boolean {
		val now = Date()
		return date.year === now.year && date.month === now.month && date.date === now.date
	}

	private fun isYestYesterday(date: Date): Boolean {
		val now = Date()
		return date.year === now.year && date.month === now.month && date.date + 1 === now.date
	}

	private fun isThisWeek(date: Date): Boolean {
		val now = Date()
		if (date.year === now.year && date.month === now.month) {
			if (now.day - date.day < now.day && now.date - date.date > 0 && now.date - date.date < 7) {
				return true
			}
		}
		return false
	}

	private fun isThisYear(date: Date): Boolean {
		val now = Date()
		return date.year === now.year
	}
}