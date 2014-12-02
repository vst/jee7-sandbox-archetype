/*
 * Copyright (c) 2013-2014 Vehbi Sinan Tunalioglu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vsthost.jee7.sandbox.restapi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Provides generic formatters for the REST API.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class Formatter {

    /**
     * Defines the default DATETIME format.
     */
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * Formats a date/time object.
     *
     * @param datetime The date/time to be formatted.
     * @return A string representing the provided date/time.
     */
    public static String DatetimeFormatter (Calendar datetime) {
        return new SimpleDateFormat(FORMAT_DATETIME).format(datetime.getTime());

    }
}
