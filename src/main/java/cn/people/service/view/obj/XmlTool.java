package cn.people.service.view.obj;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlTool {
	public static String getNodeValue(Element root, String nodename) {
		String result = null;
		if (root != null && nodename != null && nodename.length() > 0) {
			NodeList temp = root.getElementsByTagName(nodename);
			if (temp.getLength() > 0) {
				result = temp.item(0).getFirstChild() == null ? null : temp.item(0).getFirstChild().getNodeValue();
			}
		}
		return result;
	}

	public static String getNodeValue(Node node, String nodename) {
		String result = null;
		if (node != null && nodename != null && nodename.length() > 0) {
			NodeList temp = node.getOwnerDocument().getElementsByTagName(nodename);
			if (temp.getLength() > 0) {
				result = temp.item(0).getFirstChild() == null ? null : temp.item(0).getFirstChild().getNodeValue();
			}
		}
		return result;
	}

	// 将指定节点下的多个值取出，每个值的attributeValue不同
	public static LinkedHashMap getNodeValueMap(Node node, String nodename, String attributeName) {
		LinkedHashMap result = new LinkedHashMap();
		if (node != null && nodename != null && nodename.length() > 0) {
			NodeList temp = node.getOwnerDocument().getElementsByTagName(nodename);
			if (temp.getLength() > 0) {
				Node tempnode = temp.item(0);
				if (tempnode.hasChildNodes()) {
					temp = tempnode.getChildNodes();
					for (int i = 0; i < temp.getLength(); i++) {
						tempnode = temp.item(i);
						if (tempnode.hasAttributes()) {
							Node attribute = tempnode.getAttributes().getNamedItem(attributeName);
							if (attribute != null) {
								String attributeValue = attribute.getNodeValue();
								if (attributeValue != null) {
									String nodeValue = tempnode.getFirstChild() == null ? "" : tempnode.getFirstChild().getNodeValue();
									result.put(attributeValue, nodeValue);
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	// 将指定节点下的多个子节点的值取出
	public static Vector getNodeValues(Node node, String nodename) {
		Vector result = new Vector();
		if (node != null && nodename != null && nodename.length() > 0) {
			NodeList temp = node.getOwnerDocument().getElementsByTagName(nodename);
			if (temp.getLength() > 0) {
				Node tempnode = temp.item(0);
				if (tempnode.hasChildNodes()) {
					temp = tempnode.getChildNodes();
					for (int i = 0; i < temp.getLength(); i++) {
						tempnode = temp.item(i);
						if (tempnode.getNodeType() == Node.ELEMENT_NODE) {
							String nodeValue = tempnode.getFirstChild() == null ? "" : tempnode.getFirstChild().getNodeValue();
							result.add(nodeValue);
						}
					}
				}
			}
		}
		return result;
	}

	// 将Node节点转化为字符串
	public static String getNodeString(Node node, String charset) {
		return getNodeString(node, charset, false);
	}

	// 将Node节点转化为字符串 OmitXMLDeclaration 是否省略描述信息
	public static String getNodeString(Node node, String charset, boolean OmitXMLDeclaration) {
		String result = "";
		/*
		 * try { TransformerFactory tfactory = TransformerFactory.newInstance(); Transformer transformer = tfactory.newTransformer(); // 将DOM对象转化为DOMSource类对象，该对象表现为转化成别的表达形式的信息容器。 DOMSource source = new DOMSource(node); ByteArrayOutputStream out = new ByteArrayOutputStream(); // 获得一个StreamResult类对象，该对象是DOM文档转化成的其他形式的文档的容器，可以是XML文件，文本文件，HTML文件。这里为一个XML文件。 StreamResult resultout = new StreamResult(out); // 调用API，将DOM文档转化成XML文件。 // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); transformer.setOutputProperty(OutputKeys.ENCODING, charset); transformer.setOutputProperty(OutputKeys.METHOD, "xml"); transformer.setOutputProperty(OutputKeys.INDENT, "yes"); transformer.transform(source, resultout); out.close(); result = out.toString(charset); } catch (Exception e) { e.printStackTrace(); }
		 */
		try {
			if (node instanceof Element) {
				OutputFormat format = new OutputFormat();
				format.setEncoding(charset);
				format.setIndenting(true);
				format.setOmitXMLDeclaration(OmitXMLDeclaration);
				StringWriter out = new StringWriter();
				XMLSerializer xmlout = new XMLSerializer(out, format);
				xmlout.serialize((Element) node);
				result = out.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String arg[]) {
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
		xml += "<mmscontent>";
		xml += "	<contenttype>news</contenttype>";
		xml += "	<operationtype>add/delete/update</operationtype>";
		xml += "	<target>";
		xml += "		<cmsnode>1,5</cmsnode>";
		xml += "		<cmsfile>55</cmsfile>";
		xml += "	</target>";
		xml += "	<content>";
		xml += "		<contentinfo>\n";
		xml += "			<title>aa</title>\n";
		xml += "			<description>desc...</description>\n";
		xml += "			<keyword>keywd</keyword>\n";
		xml += "			<shouldernote>aa肩标题</shouldernote>\n";
		xml += "			<subtitle>aa子标题</subtitle>";
		xml += "			<author>aa作者</author>";
		xml += "			<source>aa</source>";
		xml += "			<editor>aa</editor>";
		xml += "			<newsvideo>";
		xml += "				<video name=\"xx\">http://xxxerer.jspa</video>";
		xml += "				<video name=\"ll\">http://ryrty.jspa</video>";
		xml += "			</newsvideo>";
		xml += "			<programtext>sdfsdfsdfgferrtergsdrewrt...</programtext>";
		xml += "			<programpicture>";
		xml += "				<pic name=\"aa\">http://srewrwer.jpg</pic>";
		xml += "				<pic name=\"bb\">http://srewrwer.jpg</pic>";
		xml += "			</programpicture>";
		xml += "			<cmspublishtime>1231346546</cmspublishtime>";
		xml += "		</contentinfo>";
		xml += "	</content>";
		xml += "</mmscontent>";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder doc_builder = dbf.newDocumentBuilder();
			Document doc = doc_builder.parse(new ByteArrayInputStream(xml.getBytes("GBK")));
			// Get root node
			Element root = doc.getDocumentElement();
			if (root != null) {
				String contenttype = XmlTool.getNodeValue(root, "contenttype");
				String operationtype = XmlTool.getNodeValue(root, "operationtype");
				String channels = XmlTool.getNodeValue(root, "cmsnode");
				String contentID = XmlTool.getNodeValue(root, "cmsfile");
				Node contentinfo = null;
				NodeList temp = root.getElementsByTagName("contentinfo");
				if (temp != null) {
					contentinfo = temp.item(0);
					String description = XmlTool.getNodeValue(contentinfo, "description");
					LinkedHashMap map = XmlTool.getNodeValueMap(contentinfo, "newsvideo", "name");
//					System.out.println(map);
//					System.out.println(contenttype);
//					System.out.println(description);
//					System.out.println(getNodeString(contentinfo, "UTF-8"));
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

