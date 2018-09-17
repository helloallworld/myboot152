
package jp55m015.bssv.e1.oracle.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PSaleOrderCloseInput complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PSaleOrderCloseInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://oracle.e1.bssv.JP55M015/types/}ValueObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="list" type="{http://oracle.e1.bssv.JP55M015/types/}PF55M421A" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PSaleOrderCloseInput", propOrder = {
    "list"
})
public class PSaleOrderCloseInput
    extends ValueObject
{

    @XmlElement(nillable = true)
    protected List<PF55M421A> list;

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
     * {@link PF55M421A }
     * 
     * 
     */
    public List<PF55M421A> getList() {
        if (list == null) {
            list = new ArrayList<PF55M421A>();
        }
        return this.list;
    }

}
