package com.wumin.boot152.common.util.jxsl;

import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/6/28.
 */
public class JxlsUtil {

    static {
        //添加自定义指令（可覆盖jxls原指令）
        XlsCommentAreaBuilder.addCommandMapping("each", EachCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("merge", MergeCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("link", LinkCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("image", ImageCommand.class);
    }

    /**
     * jxls模版文件目录
     */
    private final static String TEMPLATE_PATH = "excel";

    /**
     * 导出excel 删除Sheet1页签
     *
     * @param is - excel文件流
     * @param os - 生成模版输出流
     * @throws IOException
     */
    public static void exportExcelDeleteSheet1(InputStream is, OutputStream os, Context context) throws IOException {
        List data=(List) context.getVar("data");
        List<String> sheetNames=(List<String>)context.getVar("sheetNames") ;
        data.add(0,data.get(0));
        sheetNames.add(0,"Sheet1");
        JxlsHelper jxlsHelper = MyJxlsHelper.getInstance();
        Transformer transformer = jxlsHelper.createTransformer(is, os);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> funcs = new HashMap<String, Object>();
        funcs.put("jx", new JxlsUtil());    //添加自定义功能
        evaluator.getJexlEngine().setFunctions(funcs);

        jxlsHelper.setUseFastFormulaProcessor(false).processTemplate(context, transformer);
    }
    /**
     * 导出excel
     *
     * @param is - excel文件流
     * @param os - 生成模版输出流
     * @throws IOException
     */
    public static void exportExcel(InputStream is, OutputStream os, Context context) throws IOException {
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer = jxlsHelper.createTransformer(is, os);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> funcs = new HashMap<String, Object>();
        funcs.put("jx", new JxlsUtil());    //添加自定义功能
        evaluator.getJexlEngine().setFunctions(funcs);
        jxlsHelper.processTemplate(context, transformer);
    }
    /**
     * 导出excel
     *
     * @param xlsPath excel文件
     * @param outPath 输出文件
     * @param beans   模版中填充的数据
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void exportExcel(String xlsPath, String outPath, Context context) throws FileNotFoundException, IOException {
        exportExcel(new FileInputStream(xlsPath), new FileOutputStream(outPath), context);
    }

    /**
     * 导出excel
     *
     * @param xls   excel文件
     * @param out   输出文件
     * @param beans 模版中填充的数据
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void exportExcel(File xls, File out, Context context) throws FileNotFoundException, IOException {
        exportExcel(new FileInputStream(xls), new FileOutputStream(out), context);
    }

    /**
     * 获取jxls模版文件
     */
    public static File getTemplate(String name) {
        String templatePath = JxlsUtil.class.getClassLoader().getResource(TEMPLATE_PATH).getPath();
        File template = new File(templatePath, name);
        if (template.exists()) {
            return template;
        }
        return null;
    }
}
