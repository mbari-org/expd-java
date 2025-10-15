/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

import java.util.Date;
import org.mbari.expd.Dive;

/**
 * The DiveImpl class provides an implementation of the Dive interface, representing
 * detailed information about a single dive operation. This class encapsulates properties
 * such as the dive ID, ROV name, dive number, start and end dates, location coordinates,
 * the chief scientist, and brief accomplishments of the dive.
 *
 * This class supports equality checks, hash value computation, and a descriptive string
 * representation based on the ROV name, dive number, and time-related properties.
 */
public class DiveImpl implements Dive {

    private Integer diveId;
    private String rovName;
    private Integer diveNumber;
    private Date startDate;
    private Date endDate;
    private String chiefScientist;
    private String briefAccomplishments;
    private Double latitude;
    private Double longitude;
    
    public DiveImpl() {
        
    }

    public DiveImpl(Integer diveId, String rovName, Integer diveNumber, Date startDate, Date endDate,
                    String chiefScientist, String briefAccomplishments, Double latitude, Double longitude) {
        this.diveId = diveId;
        this.rovName = rovName;
        this.diveNumber = diveNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chiefScientist = chiefScientist;
        this.briefAccomplishments = briefAccomplishments;
    }

    public Integer getDiveId() {
        return diveId;
    }

    @Override
    public String getRovName() {
        return rovName;
    }

    @Override
    public String getBriefAccomplishments() {
        return briefAccomplishments;
    }

    @Override
    public String getChiefScientist() {
        return chiefScientist;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public Integer getDiveNumber() {
        return diveNumber;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiveImpl other = (DiveImpl) obj;
        if ((this.rovName == null) ? (other.rovName != null) : !this.rovName.equals(other.rovName)) {
            return false;
        }
        if (this.diveNumber != other.diveNumber && (this.diveNumber == null || !this.diveNumber.equals(other.diveNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.rovName != null ? this.rovName.hashCode() : 0);
        hash = 23 * hash + (this.diveNumber != null ? this.diveNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(rovName = " + rovName + ", diveNumber = " + diveNumber +
                ", startDate = " + startDate + ", endDate = " + endDate + ")";
    }   

}
