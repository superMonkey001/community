package cn.hncj.community.advice;

import cn.hncj.community.dto.ResultDTO;
import cn.hncj.community.exception.CustomizeErrorCode;
import cn.hncj.community.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.error((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.error(CustomizeErrorCode.SYS_ERROR);
            }
            PrintWriter writer = null;
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(200);
                writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally {
                writer.close();
            }
            return null;
        } else {
            if (e instanceof CustomizeException) {
                model.addAttribute("msg", e.getMessage());
            } else {
                model.addAttribute("msg", CustomizeErrorCode.SYS_ERROR.getMessage());
            }

            return new ModelAndView("error");
        }
    }

}
