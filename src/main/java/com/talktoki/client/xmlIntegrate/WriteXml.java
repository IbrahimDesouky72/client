/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talktoki.client.xmlIntegrate;

import com.talktoki.chatinterfaces.commans.Message;
import com.talktoki.client.generatedXmlClasses.MessagesType;
import com.talktoki.client.generatedXmlClasses.FontType;
import com.talktoki.client.generatedXmlClasses.MessageType;
import com.talktoki.client.generatedXmlClasses.ObjectFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author bassem
 */
public class WriteXml {

    public void Write(List<Message> msgsList, File outputFile, String chatOwner) {
        try {
            JAXBContext context = JAXBContext.newInstance("com.talktoki.client.generatedXmlClasses");

            ObjectFactory factory = new ObjectFactory();
            MessagesType fullMsgNode = factory.createMessagesType();
            fullMsgNode.setOwner(chatOwner);
            for (Message saveMsg : msgsList) {
                MessageType newMessage = factory.createMessageType();
                newMessage.setFrom(saveMsg.getFrom());
                newMessage.setTo(saveMsg.getTo());
                newMessage.setBody(saveMsg.getText());
                newMessage.setDate(saveMsg.getDate());
                newMessage.setColor(saveMsg.getTextColor());
                FontType msgFont = new FontType();
                msgFont.setFontFamily(saveMsg.getFontFamily());
                msgFont.setFontSize(saveMsg.getFontSize());
                msgFont.setFontType(saveMsg.getFontWeight());
                newMessage.setFont(msgFont);
                fullMsgNode.getMessage().add(newMessage);
            }

            JAXBElement msgElement = factory.createMessages(fullMsgNode);
            Marshaller marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml-stylesheet type='text/xsl' href='" + outputFile.getParent() + "/MessageXsltDesign.xsl' ?>");
            marsh.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, outputFile.getParent() + "/MessageSchema.xsd");
            FileOutputStream outputXml = new FileOutputStream(outputFile);
            saveFileInternal(getClass().getResource("/xmlResources/MessageXsltDesign.xsl").openStream(), outputFile.getParent() + "/MessageXsltDesign.xsl");
            saveFileInternal(getClass().getResource("/xmlResources/MessageSchema.xsd").openStream(), outputFile.getParent() + "/MessageSchema.xsd");
            marsh.marshal(msgElement, outputXml);

            //transformToHtml(locationUrl,fileName, xmlFileName);
        } catch (JAXBException ex) {
            Logger.getLogger(WriteXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WriteXml.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveFileInternal(InputStream is, String path) {
        Thread threadOne = new Thread(() -> {

            FileOutputStream os = null;
            try {
                File newFile = new File(path);
                os = new FileOutputStream(newFile);
                int readByte;
                while ((readByte = is.read()) != -1) {
                    os.write(readByte);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        threadOne.start();

    }

    private static void transformToHtml(String LocationUrl, String fileName, String xmlFile) {

        try {
            DocumentBuilderFactory docBuildfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuildfactory.newDocumentBuilder();
            Document document = docBuilder.parse(new InputSource(new InputStreamReader(new FileInputStream(LocationUrl + "/" + fileName + ".xml"))));
            TransformerFactory xformer = TransformerFactory.newInstance();

            Source xslDoc = new StreamSource("src/main/resources/xmlResources/MessageXsltDesign.xsl");
            //Read From Old File That we created
            //Source xmlDoc=new StreamSource("src/main/java/com/itico/xmlchat/MessageXml.xml");
            //Read From New file the app create
            Source xmlDoc = new StreamSource(xmlFile);
            String outputFileName = LocationUrl + "/" + fileName + ".html";

            OutputStream htmlFile = new FileOutputStream(outputFileName);
            Transformer trasform = xformer.newTransformer(xslDoc);
            trasform.transform(xmlDoc, new StreamResult(htmlFile));
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException ex) {
            Logger.getLogger(WriteXml.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
