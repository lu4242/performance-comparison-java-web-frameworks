/*
 * Copyright 2002-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jmeterutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JvmLogParser {

    private static final Pattern DATE_TIME = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2} )?(\\d{2}:\\d{2}:\\d{2}).(\\d{3})");

    private static final Pattern JIT_MSG = Pattern.compile("^\\s*(\\d+?|-+)([%!sn\\s]+)\\s");

    private static final Pattern MESSAGE = Pattern.compile("(made[^)]+\\)\\s+)?([^(]+)[^)]+");

    private static final Pattern BOOKING = Pattern.compile("New booking: (\\d+)");

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader("../target/warmup.log"));
        String line;
        int i = 0;
        int booking = 0;
        long startTime = 0;
        long time = 0;
        FileWriter writer = new FileWriter("../target/warmup-data.log", false);
        while((line = reader.readLine()) != null) {
//            if(i++ > 1000) {
//                break;
//            }
            Matcher matcher = DATE_TIME.matcher(line);
            if(matcher.find()) {
                String timeString = matcher.group(2);
                String milliString = matcher.group(3);
                long millis = Time.valueOf(timeString).getTime() + Integer.valueOf(milliString);
                if(startTime == 0) {
                    startTime = millis;

                }
                time = millis - startTime;
                matcher = BOOKING.matcher(line);
                if(matcher.find()) {
                    booking = Integer.valueOf(matcher.group(1));                    
                }
                continue;
            }
            matcher = JIT_MSG.matcher(line);
            if(matcher.find()) {
                String id = matcher.group(1);
                String flags = matcher.group(2).replaceAll(" ", "");
                String text = line.substring(matcher.end());
                Matcher textMatcher = MESSAGE.matcher(text);
                textMatcher.find();
                String made = textMatcher.group(1);
                if(made == null) {
                    made = "";
                }
                String method = textMatcher.group(2);
                writer.write(flags + '|' + time + '|' + booking + '|' + id + '|' + method + '|' + made + '\n');
            }
        }
        writer.close();
    }

}
