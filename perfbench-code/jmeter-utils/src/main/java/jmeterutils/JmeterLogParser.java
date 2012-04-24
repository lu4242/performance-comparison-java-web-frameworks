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

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JmeterLogParser {

    public static void main(String[] args) {        
        Document doc = Utils.parseXml(args[0]);
        int threadCount = Integer.parseInt(args[1]);
        NodeList elements = doc.getDocumentElement().getChildNodes();
        int size = elements.getLength();
        List<HttpSample> samples = new ArrayList<HttpSample>();                
        Map<String, List<HttpSample>> labelSamples = new LinkedHashMap<String, List<HttpSample>>();
        for(int i = 0; i < size; i++) {
            Node node = elements.item(i);            
            if(node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element e = (Element) node;
            HttpSample sample = new HttpSample(e);
            samples.add(sample);
            String label = sample.getLabel();
            List<HttpSample> list = labelSamples.get(label);
            if(list == null) {
                list = new ArrayList<HttpSample>();
                labelSamples.put(label, list);
            }
            list.add(sample);
        }           
        File resultsFile = new File(args[2]);
        Document resultsDoc = null;
        Element root = null;
        if(resultsFile.exists()) {
            resultsDoc = Utils.parseXml(resultsFile);
            root = resultsDoc.getDocumentElement();
        } else {
            resultsDoc = Utils.getNewXmlDocument();
            root = resultsDoc.createElement("results");
            resultsDoc.appendChild(root);
        }
        for(Map.Entry<String, List<HttpSample>> entry : labelSamples.entrySet()) {
            String label = entry.getKey();
            int min = -1;
            int max = 0;
            int success = 0;
            int totalTimeForLabel = 0;
            int totalBytesForLabel = 0;
            for(HttpSample sample : entry.getValue()) {
                int time = sample.getTime();
                totalTimeForLabel += time;
                if(time > max) {
                    max = time;
                }
                if(min == -1 || time < min) {
                    min = time;
                }
                if(sample.isSuccess()) {
                    success++;
                }
                totalBytesForLabel = totalBytesForLabel += sample.getBytes();
            }
            int countForLabel = entry.getValue().size();
            int successPercentage = 100 * (success / countForLabel);
            int averageTimeForLabel = totalTimeForLabel / countForLabel;
            int averageBytesForLabel = totalBytesForLabel / countForLabel;
            Element e = resultsDoc.createElement("result");
            root.appendChild(e);
            e.setAttribute("label", label);
            e.setAttribute("threadCount", threadCount + "");
            e.setAttribute("samples", countForLabel + "");
            e.setAttribute("success", successPercentage + "%");
            e.setAttribute("average", averageTimeForLabel + "");
            e.setAttribute("min", min + "");
            e.setAttribute("max", max + "");
            e.setAttribute("bytes", averageBytesForLabel + "");
        }        
        String xmlContent = Utils.getAsString(resultsDoc);
        Utils.writeFile(xmlContent, args[2], false);
    }

}
