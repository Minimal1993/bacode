/*
 * $Id: ClassicBarcodeLogicHandler.java,v 1.1 2003-12-13 20:23:42 jmaerki Exp $
 * ============================================================================
 * The Krysalis Patchy Software License, Version 1.1_01
 * Copyright (c) 2002-2003 Nicola Ken Barozzi.  All rights reserved.
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
package org.krysalis.barcode4j;

/**
 * This interface defines methods to build classic 1D barcodes.
 * 
 * @author Jeremias Maerki
 */
public interface ClassicBarcodeLogicHandler extends BarcodeLogicHandler {
    
    /**
     * This method is called when a new group of bars is about to start. A
     * group of bars may be generated by a character (or group of characters) 
     * of the message to be encoded or by a start/end control group.
     * @param type a constant indicating the type of group. The meaning of the
     * integer values is implementation-dependant.
     * @param submsg In case of parts of the message to be encoded this 
     * parameter holds the character or characters being encoded.
     */
    void startBarGroup(BarGroup type, String submsg);
    
    /**
     * This method indicates a new bar to be painted.
     * @param black True if the bar is to be painted black, False if 
     * white/transparent
     * @param weight logical weight of the bar.  Concrete implementations are
     * free to define this to be height or width.  For example, 1 may be interpreted 
     * as "narrow bar", 2 as "wide bar" for certain barcodes.  While other barcodes
     * might define 1 to be "short bar", 2 as "tall bar".
     * Also, this should not be confused with the effective painting width, or the 
     * actual physical size of the bar. 
     */
    void addBar(boolean black, int weight);
    
    /**
     * This method indicates the end of a previously started bar group. The
     * method calls to startBarGroup and endBarGroup form a stack.
     */
    void endBarGroup();
    
}
