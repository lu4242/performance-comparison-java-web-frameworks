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
import java.io.FileWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class Utils {

    public static Document parseXml(String fileName) {
        return parseXml(new File(fileName));
    }

    public static Document parseXml(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            return factory.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Document getNewXmlDocument() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAsString(Document document) {
        Source source = new DOMSource(document);
        StringWriter writer = new StringWriter();
        Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(source, new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public static void writeFile(String content, String fileName, boolean append) {
		FileWriter writer = null;
		try {
			try {
				writer = new FileWriter(fileName, append);
				writer.write(content);
			} finally {
				writer.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    public static String getSystemInfo() {
        String[] keys = { "java.version", "java.runtime.version",
            "java.vm.name", "java.vm.info", "java.vendor",
            "os.name", "os.version", "sun.os.patch.level", "os.arch", "sun.cpu.isalist" };
        StringBuilder sb = new StringBuilder();
        for(String key : keys) {
            sb.append(key + " : " + System.getProperty(key) + "\n");
        }
        sb.append("processors (cores) : " + Runtime.getRuntime().availableProcessors() + "\n");
        // http://forums.sun.com/thread.jspa?messageID=10376073#10376073
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
                Object value;
                try {
                    value = method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    value = e;
                }
                sb.append(method.getName() + " = " + value + "\n");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getSystemInfo());
    }

}
