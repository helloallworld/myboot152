
package jp55m015.bssv.e1.oracle.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PShowF55M421C complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PShowF55M421C"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://oracle.e1.bssv.JP55M015/types/}ValueObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="list" type="{http://oracle.e1.bssv.JP55M015/types/}PShowF55M421B" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="mapOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lineType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ediSuccessfullyProcess" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PShowF55M421C", propOrder = {
    "list",
    "mapOrderNumber",
    "lineType",
    "ediSuccessfullyProcess"
})
public class PShowF55M421C
    extends ValueObject
{

    @XmlElement(nillable = true)
    protected List<PShowF55M421B> list;
    @XmlElement(required = true, nillable = true)
    protected String mapOrderNumber;
    @XmlElement(required = true, nillable = true)
    protected String lineType;
    @XmlElement(required = true, nillable = true)
    protected String ediSuccessfullyProcess;

    /**
     * Gets the value of the list property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the list property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PShowF55M421B }
     * 
     * 
     */
    public List<PShowF55M421B> getList() {
        if (list == null) {
            list = new ArrayList<PShowF55M421B>();
        }
        return this.list;
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
     * 获取lineType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineType() {
        return lineType;
    }

    /**
     * 设置lineType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineType(String value) {
        this.lineType = value;
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

}
