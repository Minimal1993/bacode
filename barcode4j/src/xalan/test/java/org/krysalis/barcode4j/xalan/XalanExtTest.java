/*
 * $Id: XalanExtTest.java,v 1.3 2004-08-31 18:44:29 jmaerki Exp $
 * ============================================================================
 * The Krysalis Patchy Software License, Version 1.1_01
 * Copyright (c) 2003-2004 Nicola Ken Barozzi.  All rights reserved.
 *
 * This Licence is compatible with the BSD licence as described and
 * approved by http://www.opensource.org/, and is based on the
 * Apache Software Licence Version 1.1.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed for project
 *        Krysalis (http://www.krysalis.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Krysalis" and "Nicola Ken Barozzi" and
 *    "Barcode4J" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact nicolaken@krysalis.org.
 *
 * 5. Products derived from this software may not be called "Krysalis"
 *    or "Barcode4J", nor may "Krysalis" appear in their name,
 *    without prior written permission of Nicola Ken Barozzi.
 *
 * 6. This software may contain voluntary contributions made by many
 *    individuals, who decided to donate the code to this project in
 *    respect of this licence, and was originally created by
 *    Jeremias Maerki <jeremias@maerki.org>.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE KRYSALIS PROJECT OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.krysalis.barcode4j.xalan;

import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.krysalis.barcode4j.AbstractBarcodeTestCase;

/**
 * Test class for the Xalan-J extension.
 * 
 * @author Jeremias Maerki
 */
public class XalanExtTest extends AbstractBarcodeTestCase {
    
    public XalanExtTest(String name) {
        super(name);
    }
    
    public void testXalanExtGenerate() throws Exception {
        innerXalanExt("xalan-test1.xsl");
    }

    public void testXalanExtBarcodeElement() throws Exception {
        innerXalanExt("xalan-test2.xsl");
    }

    public void innerXalanExt(String xslt) throws Exception {
        Class clazz = Class.forName("org.apache.xalan.processor.TransformerFactoryImpl");
        TransformerFactory factory = (TransformerFactory)clazz.newInstance();
        Transformer trans = factory.newTransformer(new StreamSource(
                new File(getBaseDir(), "src/xalan/test/xml/" + xslt)));
        Source src = new StreamSource(
                new File(getBaseDir(), "src/test/xml/xslt-test.xml"));
        StringWriter writer = new StringWriter();
        Result res = new StreamResult(writer);
        
        trans.transform(src, res);
        String output = writer.getBuffer().toString();
        assertTrue(output.indexOf("svg") >= 0);
        //System.out.println(writer.getBuffer());
    }

    public void testXalanExtSAXOutputGenerate() throws Exception {
        innerXalanExtSAXOutput("xalan-test1.xsl");
    }

    public void testXalanExtSAXOutputBarcodeElement() throws Exception {
        innerXalanExtSAXOutput("xalan-test2.xsl");
        //System.out.println("Skipping test for Xalan barcode element extension because of Xalan bug #24220");
    }

    /* This test is done because FOP reacts with an NPE when endDocument is
     * called twice.
     */
    public void innerXalanExtSAXOutput(String xslt) throws Exception {
        Class clazz = Class.forName("org.apache.xalan.processor.TransformerFactoryImpl");
        TransformerFactory factory = (TransformerFactory)clazz.newInstance();
        Transformer trans = factory.newTransformer(new StreamSource(
                new File(getBaseDir(), "src/xalan/test/xml/" +xslt)));
        Source src = new StreamSource(
                new File(getBaseDir(), "src/test/xml/xslt-test.xml"));
        Result res = new SAXResult(new DefaultHandler() {
            private boolean endDocumentCalled = false;
            
            public void endDocument() throws SAXException {
                if (!this.endDocumentCalled) {
                    this.endDocumentCalled = true;
                } else throw new SAXException("endDocument() called twice. "
                    + "This may be due to this Xalan-J bug: "
                    + "http://nagoya.apache.org/bugzilla/show_bug.cgi?id=24220");
            }
        });
        trans.transform(src, res);
    }

}
