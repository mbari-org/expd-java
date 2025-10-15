/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;


import java.util.Collection;
import java.util.Date;

import org.mbari.expd.math.Envelope;

/**
 *
 * @author brian
 */
public interface DiveDAO {

    Collection<Dive> findByPlatformAndTrackingNumber(String platform, Integer trackingNumber);

    Dive findByPlatformAndDiveNumber(String platform, Integer diveNumber);

    Collection<Dive> findByPlatform(String platform);

    Dive findByPlatformAndDate(String platform, Date date);

    Collection<Dive> findAllWithinEnvelope(Envelope<Double> envelope);

    Collection<Dive> findAll();

    Collection<Dive> findAllByExpedition(Expedition expedition);

}
