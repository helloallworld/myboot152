
package jp55m015.bssv.e1.oracle.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PShowF55M421B complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PShowF55M421B"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://oracle.e1.bssv.JP55M015/types/}ValueObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mapOrderDetailID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ediSuccessfullyProcess" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lineNumber" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PShowF55M421B", propOrder = {
    "mapOrderDetailID",
    "ediSuccessfullyProcess",
    "lineNumber"
})
public class PShowF55M421B
    extends ValueObject
{

    @XmlElement(required = true, nillable = true)
    protected String mapOrderDetailID;
    @XmlElement(required = true, nillable = true)
    protected String ediSuccessfullyProcess;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal lineNumber;

    /**
     * 获取mapOrderDetailID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapOrderDetailID() {
        return mapOrderDetailID;
    }

    /**
     * 设置mapOrderDetailID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapOrderDetailID(String value) {
        this.mapOrderDetailID = value;
    }

    /**
     * 获取ediSuccessfullyProcess属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdiSuccessfullyProcess() {
        return ediSuccessfullyProcess;
    }

    /**
     * 设置ediSuccessfullyProcess属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdiSuccessfullyProcess(String value) {
        this.ediSuccessfullyProcess = value;
    }

    /**
     * 获取lineNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLineNumber() {
        return lineNumber;
    }

    /**
     * 设置lineNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLineNumber(BigDecimal value) {
        this.lineNumber = value;
    }

}
