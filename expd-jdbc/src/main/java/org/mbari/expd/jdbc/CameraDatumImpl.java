/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

import java.util.Date;
import org.mbari.expd.CameraDatum;

/**
 * The CameraDatumImpl class is an implementation of the CameraDatum interface that represents
 * various attributes of a camera capture datum, such as its platform name, recording date,
 * timecode, focus, zoom, and iris levels.
 *
 * This class also implements the Comparable interface to allow comparisons based on the date.
 *
 * Features:
 * - Encapsulation of metadata related to camera captures.
 * - Comparison of instances by date.
 * - Overrides the equals, hashCode, and toString methods for proper equality checks and meaningful string representation.
 */
public class CameraDatumImpl implements CameraDatum, Comparable<CameraDatum> {

    private final String platformName;
    private final Date date;
    private final String timecode;
    private final String alternativeTimecode;
    private final Float focus;
    private final Float zoom;
    private final Float iris;

    /**
     * Constructs ...
     * @param platformName The name of the platform that took the image.
     * @param date The date the sample was taken.
     * @param timecode The timecode of the video player
     * @param alternativeTimecode The alternative timecode of the video player
     * @param focus The focus level of the camera
     * @param zoom The zoom level of the camera
     * @param iris The iris level of the camera
     */
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


    /**
     * Returns the platform name.
     * @return the platform name
     */
    @Override
    public String getPlatformName() {
        return platformName;
    }

    /**
     * Returns the date.
     * @return the date
     */
    @Override
    public Date getDate() {
        return date;
    }

    /**
     * Returns the timecode.
     * @return the timecode
     */
    @Override
    public String getTimecode() {
        return timecode;
    }

    /**
     * Returns the alternative timecode.
     * @return the alternative timecode
     */
    @Override
    public String getAlternativeTimecode() {
        return alternativeTimecode;
    }

    /**
     * Returns the focus.
     * @return the focus
     */
    @Override
    public Float getFocus() {
        return focus;
    }

    /**
     * Returns the zoom.
     * @return the zoom
     */
    @Override
    public Float getZoom() {
        return zoom;
    }

    /**
     * Returns the iris.
     * @return the iris
     */
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
