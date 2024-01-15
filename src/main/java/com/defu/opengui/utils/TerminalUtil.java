package com.defu.opengui.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 命令行工具类
 */
public class TerminalUtil {

    public static String exec(String cmd, String pwd) throws IOException, InterruptedException {
        List<String> cmds = new ArrayList<>();
        cmds.add("bash");
        cmds.add("-c");
        String sourceCMD = cmd;
        if (pwd == null || "".equals(pwd))
            cmd = cmd;
        else
            cmd = "echo '" + pwd + "' | sudo -S " + cmd;
        cmds.add(cmd);
        System.out.println("cmd:"+sourceCMD);
        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        Process start = processBuilder.start();

        StreamGobbler errorGobbler = new
                StreamGobbler(start.getErrorStream(), "ERROR");

        StreamGobbler outputGobbler = new
                StreamGobbler(start.getInputStream(), "OUTPUT");

        errorGobbler.start();
        outputGobbler.start();
        final int i = start.waitFor();
        return String.valueOf(i);
    }

    /**
     * 命令行工具
     * @param cmd 命令
     * @param pwd sudo 权限密码
     * @return true：命令执行成功；false：命令执行失败
     * @date 2021-12-21
     * @author liudf
     */
    public static ReturnMsgUtil execCmd(String cmd, String pwd) throws IOException, InterruptedException {
        String exec = exec(cmd, pwd);
        System.out.println("result:{}"+exec);
        Boolean res = "0".equals(exec);
        return new ReturnMsgUtil(res,exec,cmd);
    }

    /**
     * 命令行工具-统计文件个数
     * @param cmd 命令
     * @param pwd sudo 权限密码
     * @return true：命令执行成功；false：命令执行失败
     * @date 2021-12-21
     * @author liudf
     */
    public static ReturnMsgUtil execCmdFileNum(String cmd, String pwd) throws IOException, InterruptedException {
        List<String> cmds = new ArrayList<>();
        cmds.add("bash");
        cmds.add("-c");
        String sourceCMD = cmd;
        if (pwd == null || "".equals(pwd))
            cmd = cmd + " ; echo $?";
        else
            cmd = "echo '" + pwd + "' | sudo -S " + cmd + " ; echo $?";
        cmds.add(cmd);
        System.out.println("cmd:{}"+sourceCMD);
        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        Process start = processBuilder.start();
        start.waitFor();
        InputStream inputStream = start.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\t");
        }
        InputStream errorStream = start.getErrorStream();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        StringBuilder errorSB = new StringBuilder();
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null){
            errorSB.append(errorLine).append("\t");
        }

        start.destroy();
        br.close();
        errorReader.close();
        System.out.println("process:"+ sb);
        System.out.println("error:"+ errorSB);
        String result = formatString(sb.toString());
        System.out.println("result:"+ result);
        Boolean res = "0".equals(result);
        return new ReturnMsgUtil(res,sb.toString(),sb.toString()).setCode(formatStringFileNum(sb.toString()));
    }

    //处理输出，去掉换行，空格，制表符
    private static String formatString(String str){
        String temp = str
                .replaceAll( "\\s", "" )
                .replaceAll( "\\t", "" )
                .replaceAll( "\\r", "" );
        return temp.substring(temp.length()-1);
    }

    //处理输出，去掉换行，空格，制表符-获取倒数第二位
    private static String formatStringFileNum(String str){
        String temp = str
                .replaceAll( "\\s", "" )
                .replaceAll( "\\t", "" )
                .replaceAll( "\\r", "" );
        return temp.substring(0, temp.length()-1);
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        ReturnMsgUtil s = execCmd("python3 /opt/mytest/finalreport2vcf.py /opt/mytest/final.map /opt/mytest/test1/20211018_FinalReport.txt /opt/mytest/test3/20211018_FinalReport","Liudefu!@#");
        System.out.println("result" + s);
    }
}
