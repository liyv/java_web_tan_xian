package com.liyv.framework;

import com.liyv.framework.bean.Data;
import com.liyv.framework.bean.Handler;
import com.liyv.framework.bean.Param;
import com.liyv.framework.bean.View;
import com.liyv.framework.helper.*;
import com.liyv.framework.utils.JsonUtil;
import com.liyv.framework.utils.ReflectionUtil;
import com.liyv.framework.utils.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关 Helper 类
        HelperLoader.init();
        //获取 ServletContext 对象 ，用于注册 Servlet
        ServletContext servletContext = config.getServletContext();
        //注册处理 JSP 的 Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req, resp);
        try {


            //获取请求方法与请求路径
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();
            System.out.println(req.getContextPath());
            if (requestPath.equals("/favicon.ico")) {
                return;
            }

            //获取 Action 处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (null != handler) {
                //获取 Controller 类及其 Bean 实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);//实例

                Param param;
                if (UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }
                //调用  Action 方法
                Method actionMethod = handler.getActionMethod();
                Object result;
                if (param.isEmpty()) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                //处理 Action 方法返回值
                if (result instanceof View) {
                    //返回 JSP 页面
                    View view = (View) result;
                    handleViewResult(view, req, resp);
                } else if (result instanceof Data) {
                    //返回 JSON 数据
                    Data data = (Data) result;
                    handleDataResult(data, resp);
                }
            }
        } finally {
            ServletHelper.destroy();
        }
    }

    private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (null != model) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
