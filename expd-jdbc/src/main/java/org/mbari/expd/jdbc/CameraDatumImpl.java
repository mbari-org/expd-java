/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

import java.util.Date;
import org.mbari.expd.CameraDatum;

/**
 *
 * @author brian
 */
public class CameraDatumImpl implements CameraDatum, Comparable<CameraDatum> {

    private final String platformName;
    private final Date date;
    private final String timecode;
    private final String alternativeTimecode;
    private final Float focus;
    private final Float zoom;
    private final Float iris;

    public CameraDatumImpl(String platformName, Date date, String timecode,
            String alternativeTimecode, Float focus, Float zoom, Float iris) {
        this.platformName = platformName;
        this.date = date;
        this.timecode = timecode;
        this.alternativeTimecode = alternativeTimecode;
        this.focus = focus;
        this.zoom = zoom;
        this.iris = iris;
    }


    @Override
    public String getPlatformName() {
        return platformName;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getTimecode() {
        return timecode;
    }

    @Override
    public String getAlternativeTimecode() {
        return alternativeTimecode;
    }

    @Override
    public Float getFocus() {
        return focus;
    }

    @Override
    public Float getZoom() {
        return zoom;
    }

    @Override
    public Float getIris() {
        return iris;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CameraDatumImpl other = (CameraDatumImpl) obj;
        if ((this.platformName == null) ? (other.platformName != null) : !this.platformName.equals(other.platformName)) {
            return false;
        }
        if (this.date != other.date && (this.date == null || !this.date.equals(other.date))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.platformName != null ? this.platformName.hashCode() : 0);
        hash = 53 * hash + (this.date != null ? this.date.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(CameraDatum o) {
        return date.compareTo(o.getDate());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(platformName = " + platformName +
                ", date = " + date + ", timecode = " + timecode +
                ", alternativeTimecde = " + alternativeTimecode + ")";
    }



}
