/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

import java.util.Date;

/**
 *
 * @author brian
 */
public interface CameraDatum {
    
    String getPlatformName();
    Date getDate();
    String getTimecode();
    String getAlternativeTimecode();
    Float getFocus();
    Float getZoom();
    Float getIris();

}
