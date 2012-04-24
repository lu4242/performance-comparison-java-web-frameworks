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

import org.w3c.dom.Element;

public class HttpSample {

    private int time;
    private int latency;
    private long timeStamp;
    private boolean success;
    private String label;
    private String responseMessage;
    private int responseCode;
    private String threadName;
    private String dataType;
    private int bytes;

    public HttpSample(Element e) {
        time = Integer.parseInt(e.getAttribute("t"));
        latency = Integer.parseInt(e.getAttribute("lt"));
        timeStamp = Long.parseLong(e.getAttribute("ts"));
        success = Boolean.parseBoolean(e.getAttribute("s"));
        label = e.getAttribute("lb");
        responseMessage = e.getAttribute("rm");
        responseCode = Integer.parseInt(e.getAttribute("rc"));
        threadName = e.getAttribute("tn");
        dataType = e.getAttribute("dt");
        bytes = Integer.parseInt(e.getAttribute("by"));
    }

    public int getTime() {
        return time;
    }

    public int getLatency() {
        return latency;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getLabel() {
        return label;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getDataType() {
        return dataType;
    }

    public int getBytes() {
        return bytes;
    }    

}
