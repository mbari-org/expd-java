/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

/*-
 * #%L
 * org.mbari.expd:expd-jdbc
 * %%
 * Copyright (C) 2008 - 2026 Monterey Bay Aquarium Research Institute
 * %%
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
 * #L%
 */

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

    /**
     * Constructs ...
     */
    public DiveImpl() {
        
    }

    /**
     * Constructs ...
     * @param diveId The dive ID.
     * @param rovName The name of the Rover that took the dive.
     * @param diveNumber The dive number.
     * @param startDate The date the dive began.
     * @param endDate The date the dive ended.
     * @param chiefScientist The name of the chief scientist.
     * @param briefAccomplishments A brief description of the accomplishments of the dive.
     * @param latitude The latitude of the dive site.
     * @param longitude The longitude of the dive site.
     */
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

    /**
     * Returns the dive ID.
     * @return the diveId
     */
    public Integer getDiveId() {
        return diveId;
    }

    /**
     * Returns the name of the ROV used for the dive.
     * @return the rovName
     */
    @Override
    public String getRovName() {
        return rovName;
    }

    /**
     * Returns a brief description of the accomplishments of the dive.
     * @return the briefAccomplishments
     */
    @Override
    public String getBriefAccomplishments() {
        return briefAccomplishments;
    }

    /**
     * Returns the name of the chief scientist.
     * @return the chiefScientist
     */
    @Override
    public String getChiefScientist() {
        return chiefScientist;
    }

    /**
     *
     * @return The latitude of the dive site.
     */
    @Override
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return The longitude of the dive site.
     */
    @Override
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @return The dive number.
     */
    @Override
    public Integer getDiveNumber() {
        return diveNumber;
    }

    /**
     *
     * @return The date the dive ended.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *
     * @return The date the dive began.
     */
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
