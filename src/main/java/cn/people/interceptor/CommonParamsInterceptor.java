package cn.people.interceptor;

import cn.people.commons.constants.CodeConstants;
import cn.people.controller.vo.ResultVO;
import cn.people.request.CommonParamsSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 公共参数拦截器
 * <p>
 * Created by wilson on 2018-10-09.
 */
@Component
public class CommonParamsInterceptor implements HandlerInterceptor {

    /**
     * 在进入Controller之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 设置公共参数，如果成功则返回true，失败则返回false并报告请求参数错误
        if (!CommonParamsSession.setParams(request)) {
            ResultVO resultVO = ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "缺少必要公共请求参数");

            PrintWriter writer = null;
            try {
                //response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                writer = response.getWriter();
                ObjectMapper mapper = new ObjectMapper();
                writer.print(mapper.writeValueAsString(resultVO));
            } catch (Exception ignored) {
            } finally {
                if (writer != null)
                    writer.close();
            }

            return false;
        } else {
            return true;
        }

    }

    /**
     * 处理请求完成后视图渲染之前的处理操作
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
