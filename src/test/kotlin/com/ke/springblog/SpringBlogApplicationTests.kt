package com.ke.springblog

import com.ke.springblog.util.DateUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.text.SimpleDateFormat

@RunWith(SpringRunner::class)
@SpringBootTest
class SpringBlogApplicationTests {



	@Test
	fun contextLoads() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")



		val s1 = DateUtil.dateformatTime(simpleDateFormat.parse("2018-05-28 18:00:01"))

		val s2 = DateUtil.dateformatTime(simpleDateFormat.parse("2018-05-28 20:00:01"))

		val s3 = DateUtil.dateformatTime(simpleDateFormat.parse("2018-05-27 18:00:01"))

	}

}
