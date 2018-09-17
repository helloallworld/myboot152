
package jp55m015.bssv.e1.oracle.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the jp55m015.bssv.e1.oracle.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SaleOrderCloseMAPElement_QNAME = new QName("http://oracle.e1.bssv.JP55M015/types/", "SaleOrderCloseMAPElement");
    private final static QName _SaleOrderCloseMAPResponseElement_QNAME = new QName("http://oracle.e1.bssv.JP55M015/types/", "SaleOrderCloseMAPResponseElement");
    private final static QName _BusinessServiceExceptionElement_QNAME = new QName("http://oracle.e1.bssv.JP55M015/types/", "BusinessServiceExceptionElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: jp55m015.bssv.e1.oracle.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PSaleOrderCloseInput }
     * 
     */
    public PSaleOrderCloseInput createPSaleOrderCloseInput() {
        return new PSaleOrderCloseInput();
    }

    /**
     * Create an instance of {@link PSaleOrderCloseOutput }
     * 
     */
    public PSaleOrderCloseOutput createPSaleOrderCloseOutput() {
        return new PSaleOrderCloseOutput();
    }

    /**
     * Create an instance of {@link BusinessServiceException }
     * 
     */
    public BusinessServiceException createBusinessServiceException() {
        return new BusinessServiceException();
    }

    /**
     * Create an instance of {@link PF55M421A }
     * 
     */
    public PF55M421A createPF55M421A() {
        return new PF55M421A();
    }

    /**
     * Create an instance of {@link ValueObject }
     * 
     */
    public ValueObject createValueObject() {
        return new ValueObject();
    }

    /**
     * Create an instance of {@link PShowF55M421C }
     * 
     */
    public PShowF55M421C createPShowF55M421C() {
        return new PShowF55M421C();
    }

    /**
     * Create an instance of {@link PShowF55M421B }
     * 
     */
    public PShowF55M421B createPShowF55M421B() {
        return new PShowF55M421B();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PSaleOrderCloseInput }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oracle.e1.bssv.JP55M015/types/", name = "SaleOrderCloseMAPElement")
    public JAXBElement<PSaleOrderCloseInput> createSaleOrderCloseMAPElement(PSaleOrderCloseInput value) {
        return new JAXBElement<PSaleOrderCloseInput>(_SaleOrderCloseMAPElement_QNAME, PSaleOrderCloseInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PSaleOrderCloseOutput }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oracle.e1.bssv.JP55M015/types/", name = "SaleOrderCloseMAPResponseElement")
    public JAXBElement<PSaleOrderCloseOutput> createSaleOrderCloseMAPResponseElement(PSaleOrderCloseOutput value) {
        return new JAXBElement<PSaleOrderCloseOutput>(_SaleOrderCloseMAPResponseElement_QNAME, PSaleOrderCloseOutput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessServiceException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oracle.e1.bssv.JP55M015/types/", name = "BusinessServiceExceptionElement")
    public JAXBElement<BusinessServiceException> createBusinessServiceExceptionElement(BusinessServiceException value) {
        return new JAXBElement<BusinessServiceException>(_BusinessServiceExceptionElement_QNAME, BusinessServiceException.class, null, value);
    }

}
