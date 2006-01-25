/*
 * Copyright 2005 Jeremias Maerki.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.krysalis.barcode4j.fop;

import java.awt.geom.Point2D;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.fop.apps.FOPException;
import org.apache.fop.fo.FONode;
import org.apache.fop.fo.PropertyList;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.tools.ConfigurationUtil;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 * Class representing bc:barcode extension element object.
 * 
 * @author Jeremias Maerki
 * @version $Id: BarcodeElement.java,v 1.3 2006-01-25 09:21:41 jmaerki Exp $
 */
public class BarcodeElement extends BarcodeObj {

    /** @see org.apache.fop.fo.FONode#FONode(FONode) */
    public BarcodeElement(FONode parent) {
        super(parent);
    }

    /**
     * @see org.apache.fop.fo.FONode#processNode
     */
    public void processNode(String elementName, 
                            Locator locator, 
                            Attributes attlist, 
                            PropertyList propertyList) throws FOPException {
        super.processNode(elementName, locator, attlist, propertyList);
        init();
    }

    private void init() {
        createBasicDocument();
    }

    public Point2D getDimension(Point2D view) {
        Configuration cfg = ConfigurationUtil.buildConfiguration(this.doc);
        try {
            String msg;
            try {
                msg = cfg.getAttribute("message");
            } catch (ConfigurationException ce) {
                try {
                    msg = cfg.getAttribute("msg"); //for compatibility
                } catch (ConfigurationException ce1) {
                    throw ce;
                }
            }

            BarcodeGenerator bargen = BarcodeUtil.getInstance().
                    createBarcodeGenerator(cfg);
            String expandedMsg = VariableUtil.getExpandedMessage(null, msg);
            BarcodeDimension bardim = bargen.calcDimensions(expandedMsg);
            float w = (float)UnitConv.mm2pt(bardim.getWidthPlusQuiet());
            float h = (float)UnitConv.mm2pt(bardim.getHeightPlusQuiet());
            return new Point2D.Float(w, h);
        } catch (ConfigurationException ce) {
            ce.printStackTrace();
        } catch (BarcodeException be) {
            be.printStackTrace();
        }
        return null;
    }


}
