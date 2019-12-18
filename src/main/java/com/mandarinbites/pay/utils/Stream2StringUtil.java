package com.mandarinbites.pay.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-19 00:52
 **/
public class Stream2StringUtil {

    public static String parseStream2XML(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }
}
