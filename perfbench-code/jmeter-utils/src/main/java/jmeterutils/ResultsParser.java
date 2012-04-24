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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResultsParser {

    public static void main(String[] args) {
        Document doc = Utils.parseXml(args[0]);
        NodeList elements = doc.getDocumentElement().getChildNodes();
        int size = elements.getLength();
        Map<String, Map<Integer, Integer>> resultsMap = new LinkedHashMap<String, Map<Integer, Integer>>();
        for(int i = 0; i < size; i++) {
            Node node = elements.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element e = (Element) node;
            String label = e.getAttribute("label");
            Map<Integer, Integer> map = resultsMap.get(label);
            if(map == null) {
                map = new TreeMap<Integer, Integer>();
                resultsMap.put(label, map);
            }
            Integer threadCount = new Integer(e.getAttribute("threadCount"));
            Integer time = new Integer(e.getAttribute("average"));
            map.put(threadCount, time);
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, Map<Integer, Integer>> entry : resultsMap.entrySet()) {
            Map<Integer, Integer> map = entry.getValue();
            if(first) {
                sb.append("action");
                for(Integer key : map.keySet()) {
                    sb.append(',').append("users-").append(key);
                }
                sb.append('\n');
                first = false;
            }
            String label = entry.getKey();
            sb.append(label);
            for(Integer time : map.values()) {
                sb.append(',').append(time);
            }
            sb.append('\n');
        }
        Utils.writeFile(sb.toString(), args[1], false);
        Utils.writeFile(Utils.getSystemInfo(), args[2], false);
    }

}
