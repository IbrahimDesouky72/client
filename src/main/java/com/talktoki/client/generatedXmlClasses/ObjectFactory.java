//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.18 at 12:22:17 PM PST 
//


package com.talktoki.client.generatedXmlClasses;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.talktoki.client.generatedXmlClasses package. 
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

    private final static QName _Messages_QNAME = new QName("", "Messages");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.talktoki.client.generatedXmlClasses
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MessagesType }
     * 
     */
    public MessagesType createMessagesType() {
        return new MessagesType();
    }

    /**
     * Create an instance of {@link FontType }
     * 
     */
    public FontType createFontType() {
        return new FontType();
    }

    /**
     * Create an instance of {@link MessageType }
     * 
     */
    public MessageType createMessageType() {
        return new MessageType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MessagesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Messages")
    public JAXBElement<MessagesType> createMessages(MessagesType value) {
        return new JAXBElement<MessagesType>(_Messages_QNAME, MessagesType.class, null, value);
    }

}
