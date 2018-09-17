package com.wumin.boot152.common.util.jxsl;

import org.jxls.area.Area;
import org.jxls.area.CommandData;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.command.GridCommand;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.expression.ExpressionEvaluator;
import org.jxls.expression.ExpressionEvaluatorFactory;
import org.jxls.formula.FastFormulaProcessor;
import org.jxls.formula.FormulaProcessor;
import org.jxls.formula.StandardFormulaProcessor;
import org.jxls.template.SimpleExporter;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsConfigProvider;
import org.jxls.util.JxlsHelper;
import org.jxls.util.ServiceFactory;
import org.jxls.util.TransformerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yaodong01 on 2018/7/18.
 */
public class MyJxlsHelper extends JxlsHelper {

    private boolean hideTemplateSheet = false;
    private boolean deleteTemplateSheet = true;
    private boolean processFormulas = true;
    private boolean useFastFormulaProcessor = true;
    private String expressionNotationBegin;
    private String expressionNotationEnd;
    private FormulaProcessor formulaProcessor;
    private SimpleExporter simpleExporter = new SimpleExporter();
    private AreaBuilder areaBuilder = new XlsCommentAreaBuilder();
    private static final ServiceFactory SERVICE_FACTORY;
    private static final JxlsConfigProvider CONFIG_PROVIDER;

    private static <T> T loadService(Class<T> interfaceClass) {
        T ret = SERVICE_FACTORY.createService(interfaceClass, (T) null);
        return ret;
    }

    public static JxlsHelper getInstance() {
        return new MyJxlsHelper();
    }

    public MyJxlsHelper() {
    }

    public AreaBuilder getAreaBuilder() {
        return this.areaBuilder;
    }

    public JxlsHelper setAreaBuilder(AreaBuilder areaBuilder) {
        this.areaBuilder = areaBuilder;
        return this;
    }

    public FormulaProcessor getFormulaProcessor() {
        return this.formulaProcessor;
    }

    public JxlsHelper setFormulaProcessor(FormulaProcessor formulaProcessor) {
        this.formulaProcessor = formulaProcessor;
        return this;
    }

    public boolean isProcessFormulas() {
        return this.processFormulas;
    }

    public JxlsHelper setProcessFormulas(boolean processFormulas) {
        this.processFormulas = processFormulas;
        return this;
    }

    public boolean isHideTemplateSheet() {
        return this.hideTemplateSheet;
    }

    public JxlsHelper setHideTemplateSheet(boolean hideTemplateSheet) {
        this.hideTemplateSheet = hideTemplateSheet;
        return this;
    }

    public boolean isDeleteTemplateSheet() {
        return this.deleteTemplateSheet;
    }

    public JxlsHelper setDeleteTemplateSheet(boolean deleteTemplateSheet) {
        this.deleteTemplateSheet = deleteTemplateSheet;
        return this;
    }

    public boolean isUseFastFormulaProcessor() {
        return this.useFastFormulaProcessor;
    }

    public JxlsHelper setUseFastFormulaProcessor(boolean useFastFormulaProcessor) {
        this.useFastFormulaProcessor = useFastFormulaProcessor;
        return this;
    }

    public JxlsHelper buildExpressionNotation(String expressionNotationBegin, String expressionNotationEnd) {
        this.expressionNotationBegin = expressionNotationBegin;
        this.expressionNotationEnd = expressionNotationEnd;
        return this;
    }

    public JxlsHelper processTemplate(InputStream templateStream, OutputStream targetStream, Context context) throws IOException {
        Transformer transformer = this.createTransformer(templateStream, targetStream);
        this.processTemplate(context, transformer);
        return this;
    }

    @Override
    public void processTemplate(Context context, Transformer transformer) throws IOException {
        this.areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = this.areaBuilder.build();
        Iterator var4 = xlsAreaList.iterator();

        Area xlsArea;
        while(var4.hasNext()) {
            xlsArea = (Area)var4.next();
            xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), context);
        }

        if(this.processFormulas) {
            var4 = xlsAreaList.iterator();

            while(var4.hasNext()) {
                xlsArea = (Area)var4.next();
                this.setFormulaProcessor(xlsArea);
                xlsArea.processFormulas();
            }
        }
        transformer.deleteSheet("Sheet1");
        transformer.write();
    }

    public static String getProperty(String key, String defaultValue) {
        return CONFIG_PROVIDER.getProperty(key, defaultValue);
    }

    public ExpressionEvaluatorFactory getExpressionEvaluatorFactory() {
        return MyJxlsHelper.ExpressionEvaluatorFactoryHolder.INSTANCE;
    }

    public ExpressionEvaluator createExpressionEvaluator(String expression) {
        return MyJxlsHelper.ExpressionEvaluatorFactoryHolder.INSTANCE.createExpressionEvaluator(expression);
    }

    private Area setFormulaProcessor(Area xlsArea) {
        FormulaProcessor fp = this.formulaProcessor;
        if(fp == null) {
            if(this.useFastFormulaProcessor) {
                fp = new FastFormulaProcessor();
            } else {
                fp = new StandardFormulaProcessor();
            }
        }

        xlsArea.setFormulaProcessor((FormulaProcessor)fp);
        return xlsArea;
    }

    public JxlsHelper processTemplateAtCell(InputStream templateStream, OutputStream targetStream, Context context, String targetCell) throws IOException {
        Transformer transformer = this.createTransformer(templateStream, targetStream);
        this.areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = this.areaBuilder.build();
        if(xlsAreaList.isEmpty()) {
            throw new IllegalStateException("No XlsArea were detected for this processing");
        } else {
            Area firstArea = (Area)xlsAreaList.get(0);
            CellRef targetCellRef = new CellRef(targetCell);
            firstArea.applyAt(targetCellRef, context);
            if(this.processFormulas) {
                this.setFormulaProcessor(firstArea);
                firstArea.processFormulas();
            }

            String sourceSheetName = firstArea.getStartCellRef().getSheetName();
            if(!sourceSheetName.equalsIgnoreCase(targetCellRef.getSheetName())) {
                if(this.hideTemplateSheet) {
                    transformer.setHidden(sourceSheetName, true);
                }

                if(this.deleteTemplateSheet) {
                    transformer.deleteSheet(sourceSheetName);
                }
            }

            transformer.write();
            return this;
        }
    }

    public JxlsHelper processGridTemplate(InputStream templateStream, OutputStream targetStream, Context context, String objectProps) throws IOException {
        Transformer transformer = this.createTransformer(templateStream, targetStream);
        this.areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = this.areaBuilder.build();
        Iterator var7 = xlsAreaList.iterator();

        while(var7.hasNext()) {
            Area xlsArea = (Area)var7.next();
            GridCommand gridCommand = (GridCommand)((CommandData)xlsArea.getCommandDataList().get(0)).getCommand();
            gridCommand.setProps(objectProps);
            this.setFormulaProcessor(xlsArea);
            xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), context);
            if(this.processFormulas) {
                xlsArea.processFormulas();
            }
        }

        transformer.write();
        return this;
    }

    public void processGridTemplateAtCell(InputStream templateStream, OutputStream targetStream, Context context, String objectProps, String targetCell) throws IOException {
        Transformer transformer = this.createTransformer(templateStream, targetStream);
        this.areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = this.areaBuilder.build();
        Area firstArea = (Area)xlsAreaList.get(0);
        CellRef targetCellRef = new CellRef(targetCell);
        GridCommand gridCommand = (GridCommand)((CommandData)firstArea.getCommandDataList().get(0)).getCommand();
        gridCommand.setProps(objectProps);
        firstArea.applyAt(targetCellRef, context);
        if(this.processFormulas) {
            this.setFormulaProcessor(firstArea);
            firstArea.processFormulas();
        }

        String sourceSheetName = firstArea.getStartCellRef().getSheetName();
        if(!sourceSheetName.equalsIgnoreCase(targetCellRef.getSheetName())) {
            if(this.hideTemplateSheet) {
                transformer.setHidden(sourceSheetName, true);
            }

            if(this.deleteTemplateSheet) {
                transformer.deleteSheet(sourceSheetName);
            }
        }

        transformer.write();
    }

    public JxlsHelper registerGridTemplate(InputStream inputStream) throws IOException {
        this.simpleExporter.registerGridTemplate(inputStream);
        return this;
    }

    public JxlsHelper gridExport(Collection headers, Collection dataObjects, String objectProps, OutputStream outputStream) {
        this.simpleExporter.gridExport(headers, dataObjects, objectProps, outputStream);
        return this;
    }

    public Transformer createTransformer(InputStream templateStream, OutputStream targetStream) {
        Transformer transformer = TransformerFactory.createTransformer(templateStream, targetStream);
        if(transformer == null) {
            throw new IllegalStateException("Cannot load XLS transformer. Please make sure a Transformer implementation is in classpath");
        } else {
            if(this.expressionNotationBegin != null && this.expressionNotationEnd != null) {
                transformer.getTransformationConfig().buildExpressionNotation(this.expressionNotationBegin, this.expressionNotationEnd);
            }

            return transformer;
        }
    }

    static {
        SERVICE_FACTORY = (ServiceFactory) ServiceFactory.DEFAULT.createService(ServiceFactory.class, ServiceFactory.DEFAULT);
        CONFIG_PROVIDER = (JxlsConfigProvider)loadService(JxlsConfigProvider.class);
    }

    private static final class ExpressionEvaluatorFactoryHolder {
        private static final ExpressionEvaluatorFactory INSTANCE = (ExpressionEvaluatorFactory)MyJxlsHelper.loadService(ExpressionEvaluatorFactory.class);

        private ExpressionEvaluatorFactoryHolder() {
        }
    }
}
