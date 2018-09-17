
package jp55m015.bssv.e1.oracle.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PSaleOrderCloseOutput complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PSaleOrderCloseOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://oracle.e1.bssv.JP55M015/types/}ValueObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="result" type="{http://oracle.e1.bssv.JP55M015/types/}PShowF55M421C" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PSaleOrderCloseOutput", propOrder = {
    "result"
})
public class PSaleOrderCloseOutput
    extends ValueObject
{

    @XmlElement(nillable = true)
    protected List<PShowF55M421C> result;

    /**
     * Gets the value of the result property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the result property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PShowF55M421C }
     * 
     * 
     */
    public List<PShowF55M421C> getResult() {
        if (result == null) {
            result = new ArrayList<PShowF55M421C>();
        }
        return this.result;
    }

}
