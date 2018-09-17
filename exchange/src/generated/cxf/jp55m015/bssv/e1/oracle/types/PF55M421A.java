
package jp55m015.bssv.e1.oracle.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>PF55M421A complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PF55M421A"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://oracle.e1.bssv.JP55M015/types/}ValueObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="customerCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="mapOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="techSvrCenter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="contractNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PF55M421A", propOrder = {
    "customerCode",
    "mapOrderNumber",
    "orderDate",
    "techSvrCenter",
    "contractNumber"
})
public class PF55M421A
    extends ValueObject
{

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer customerCode;
    @XmlElement(required = true, nillable = true)
    protected String mapOrderNumber;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderDate;
    @XmlElement(required = true, nillable = true)
    protected String techSvrCenter;
    @XmlElement(required = true, nillable = true)
    protected String contractNumber;

    /**
     * 获取customerCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCustomerCode() {
        return customerCode;
    }

    /**
     * 设置customerCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCustomerCode(Integer value) {
        this.customerCode = value;
    }

    /**
     * 获取mapOrderNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapOrderNumber() {
        return mapOrderNumber;
    }

    /**
     * 设置mapOrderNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapOrderNumber(String value) {
        this.mapOrderNumber = value;
    }

    /**
     * 获取orderDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderDate() {
        return orderDate;
    }

    /**
     * 设置orderDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderDate(XMLGregorianCalendar value) {
        this.orderDate = value;
    }

    /**
     * 获取techSvrCenter属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechSvrCenter() {
        return techSvrCenter;
    }

    /**
     * 设置techSvrCenter属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechSvrCenter(String value) {
        this.techSvrCenter = value;
    }

    /**
     * 获取contractNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * 设置contractNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

}
