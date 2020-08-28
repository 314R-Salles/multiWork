package com.psalles.multiWork.Commons.Utils;

import org.zalando.problem.Problem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;

public class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static String getParamsStringList(Map<String, List<String>> params) {
        StringBuilder result = new StringBuilder();
        try {
            for (Map.Entry<String, List<String>> entry : params.entrySet()) {
                for (String paramValue : entry.getValue()) {
                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(paramValue, "UTF-8"));
                    result.append("&");
                }
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;

        } catch (UnsupportedEncodingException e) {
            throw Problem.builder()
                    .withStatus(INTERNAL_SERVER_ERROR)
                    .withDetail("UnsupportedEncodingException was thrown")
                    .build();
        }
    }

    public static String getParamsStringListCommaSeparated(Map<String, List<String>> params) {
        StringBuilder result = new StringBuilder();
        try {
            for (Map.Entry<String, List<String>> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                for (String paramValue : entry.getValue()) {
                    result.append(URLEncoder.encode(paramValue, "UTF-8"));
                    result.append(",");
                }
                result.deleteCharAt(result.length() - 1);
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;

        } catch (UnsupportedEncodingException e) {
            throw Problem.builder()
                    .withStatus(INTERNAL_SERVER_ERROR)
                    .withDetail("UnsupportedEncodingException was thrown")
                    .build();
        }
    }

}