package com.course.kafka.util;

import java.time.format.DateTimeFormatter;

public interface DateConstant
{
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
