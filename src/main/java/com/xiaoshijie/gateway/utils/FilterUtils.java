package com.xiaoshijie.gateway.utils;

import com.netflix.zuul.context.RequestContext;
import com.xiaoshijie.gateway.http.entity.ApiGate;
import io.micrometer.core.instrument.util.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class FilterUtils {
    private static Logger logger = LoggerFactory.getLogger(FilterUtils.class);
    public static boolean isSuccess(HttpServletResponse res) {
        return (res != null) && (res.getStatus() < 300);
    }

    public static String getResponseContent(RequestContext rtx){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse res = ctx.getResponse();
        if (FilterUtils.isSuccess(res)) {
            InputStream sourceInputStream = ctx.getResponseDataStream();
            try {
                if (ctx.getResponseGZipped()) {
                    sourceInputStream = new GZIPInputStream(sourceInputStream);
                }
                return StreamUtils.copyToString(sourceInputStream, Charset.forName("UTF-8"));
            } catch (IOException e) {
                logger.warn("Error reading body", e);
            }
        }
        return null;
    }

    private static ApiGate getGateInfo( Map<String, List<String>> requestQueryParams){
        ApiGate apiGate = new ApiGate();
        String appId = "app_key";
        if (requestQueryParams.containsKey(appId)){
            apiGate.setMethod(requestQueryParams.get(appId).get(0));
        }
        String method = "method";
        if (requestQueryParams.containsKey(method)){
            apiGate.setMethod(requestQueryParams.get(method).get(0));
        }
        apiGate.setType(1);
        String version = "v";
        if (requestQueryParams.containsKey(version)){
            apiGate.setVersion(Integer.parseInt(requestQueryParams.get(version).get(0)));
        }
        String format = "format";
        if (requestQueryParams.containsKey(format)){
            apiGate.setMethod(requestQueryParams.get(format).get(0));
        }
        return apiGate;
    }
    public static ApiGate buildApiGate(RequestContext ctx){

        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        ApiGate gateInfo = getGateInfo(requestQueryParams);
        String postContent = getPostData(ctx);
        gateInfo.setParams(postContent);
        return  gateInfo;
    }
    private static String getPostData(RequestContext ctx){
        String body = Strings.EMPTY;
        if (!ctx.isChunkedRequestBody()) {
            ServletInputStream inp;
            try {
                inp = ctx.getRequest().getInputStream();
                body = null;
                if (inp != null) {
                    body = IOUtils.toString(inp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }
    public static String getServiceName(RequestContext ctx){
        return ctx.getRequest().getRequestURI().split("/")[0];
    }
}
