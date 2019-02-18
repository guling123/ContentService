package cn.people.service.view.obj;

/**
 * 提取html中的文本信息，为了给正文中的自动图片加alt属性
 */
public class HTML2TextNew {

	public static String convert(String source){
		String result = "";
		try {
			if (source != null) {
				result = source.replaceAll("(?is)<script[^>]*>.*?</script>", "");
				result = result.replaceAll("(?is)<style[^>]*>.*?</style>", "");
				result = result.replaceAll("(?i)<[ |/]*br[^>]*[ |/]*>", "\n");
				result = result.replaceAll("(?is)< *[\\w/!]+[^<>]*>", "");
				result = result.replaceAll("&nbsp;", " ");
				result = result.replaceAll("&#160;", " ");
				result = result.replaceAll("&lt;", "<");
				result = result.replaceAll("&#60;", "<");
				result = result.replaceAll("&gt;", ">");
				result = result.replaceAll("&#62;", ">");
				result = result.replaceAll("&amp;", "&");
				result = result.replaceAll("&#38;", "&");
				result = result.replaceAll("&ldquo;", "“");
				result = result.replaceAll("&rdquo;", "”");
				result = result.replaceAll("&mdash;", "—");
				result = result.replaceAll("&quot;", "\"");
				result = result.replaceAll("&#34;", "\"");

			}
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	public static String convertAlt(String source){
		String result = "";
		try {
			if (source != null) {
				result = convert(source);
				result = result.replaceAll("\n", "&#13;");
				result = result.replaceAll("\"", "&quot;");
			}
		} catch (Exception e) {
			return "";
		}
		return result;
	}

}

