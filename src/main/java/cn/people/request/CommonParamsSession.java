package cn.people.request;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共参数session
 * <p>
 * Created by wilson on 2018-12-06.
 */
public class CommonParamsSession {

    private static ThreadLocal<CommonParams> COMMON_PARAMS_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取公共参数
     *
     * @return 公共参数
     */
    public static CommonParams getParams() {
        return COMMON_PARAMS_THREAD_LOCAL.get();
    }

    /**
     * 设置公共参数
     * 如果是必要参数丢失则返回false，非必要参数留空不设值就可以了
     * 不建议把某接口的特定参数放在这里
     */
    public static boolean setParams(HttpServletRequest request) {
        CommonParams params = new CommonParams();

        // ************ 公共参数处理逻辑 **************

        // 获取操作人id
        String operatorid = "12";
        if (StringUtils.isNotBlank(operatorid)) {
            params.setOperatorid(operatorid);
        } else {
            return false;
        }

        // 示例：udid
        // String udid = request.getParameter("udid");
        // if (StringUtils.isNotBlank(udid)) {
        //     params.setUdid(udid);
        // }

        // ********** 公共参数处理逻辑结束 ************

        COMMON_PARAMS_THREAD_LOCAL.set(params);
        return true;
    }

}
