/*
 * @(#)ExpeditionImpl.java   2012.06.20 at 01:59:25 PDT
 *
 * Copyright 2009 MBARI
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.mbari.expd.jdbc;

import org.mbari.expd.Expedition;

import java.util.Date;

/**
 * Implementation of the Expedition interface, representing specific details about an expedition.
 *
 * This class provides access to detailed information about an expedition, including its ID,
 * scientific goals, participants, timeframes, and associated comments or descriptions.
 * It serves as a concrete implementation of the Expedition interface, providing required getters
 * for each property defined in the interface.
 */
public class ExpeditionImpl implements Expedition {

    Integer expeditionId;
    String accomplishments;
    String chiefScientist;
    Date endDate;
    String equipmentDescription;
    String operatorsComments;
    String participants;
    String plannedTrackDescription;
    String principalInvestigator;
    String purpose;
    String regionDescription;
    Date scheduledEndDate;
    Date scheduledStartDate;
    String scienceObjectivesMet;
    String scientistComments;
    Integer sequenceNumber;
    String shipName;
    Date startDate;
    String updatedBy;

    /**
     * Constructs ...
     *
     * @param expeditionId
     * @param accomplishments
     * @param chiefScientist
     * @param endDate
     * @param equipmentDescription
     * @param operatorsComments
     * @param participants
     * @param plannedTrackDescription
     * @param principalInvestigator
     * @param purpose
     * @param regionDescription
     * @param scheduledEndDate
     * @param scheduledStartDate
     * @param scienceObjectivesMet
     * @param scientistComments
     * @param sequenceNumber
     * @param shipName
     * @param startDate
     * @param updatedBy
     */
    public ExpeditionImpl(Integer expeditionId, String accomplishments, String chiefScientist, Date endDate, String equipmentDescription,
                          String operatorsComments, String participants, String plannedTrackDescription,
                          String principalInvestigator, String purpose, String regionDescription,
                          Date scheduledEndDate, Date scheduledStartDate, String scienceObjectivesMet,
                          String scientistComments, Integer sequenceNumber, String shipName, Date startDate,
                          String updatedBy) {

        this.expeditionId = expeditionId;
        this.accomplishments = accomplishments;
        this.chiefScientist = chiefScientist;
        this.endDate = endDate;
        this.equipmentDescription = equipmentDescription;
        this.operatorsComments = operatorsComments;
        this.participants = participants;
        this.plannedTrackDescription = plannedTrackDescription;
        this.principalInvestigator = principalInvestigator;
        this.purpose = purpose;
        this.regionDescription = regionDescription;
        this.scheduledEndDate = scheduledEndDate;
        this.scheduledStartDate = scheduledStartDate;
        this.scienceObjectivesMet = scienceObjectivesMet;
        this.scientistComments = scientistComments;
        this.sequenceNumber = sequenceNumber;
        this.shipName = shipName;
        this.startDate = startDate;
        this.updatedBy = updatedBy;
    }


    public Integer getExpeditionId() {
        return expeditionId;
    }

    /**
     * @return
     */
    @Override
    public String getAccomplishments() {
        return accomplishments;
    }

    /**
     * @return
     */
    @Override
    public String getChiefScientist() {
        return chiefScientist;
    }

    /**
     * @return
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @return
     */
    @Override
    public String getEquipmentDescription() {
        return equipmentDescription;
    }

    /**
     * @return
     */
    @Override
    public String getOperatorsComments() {
        return operatorsComments;
    }

    /**
     * @return
     */
    @Override
    public String getParticipants() {
        return participants;
    }

    /**
     * @return
     */
    @Override
    public String getPlannedTrackDescription() {
        return plannedTrackDescription;
    }

    /**
     * @return
     */
    @Override
    public String getPrincipalInvestigator() {
        return principalInvestigator;
    }

    /**
     * @return
     */
    @Override
    public String getPurpose() {
        return purpose;
    }

    /**
     * @return
     */
    @Override
    public String getRegionDescription() {
        return regionDescription;
    }

    /**
     * @return
     */
    @Override
    public Date getScheduledEndDate() {
        return scheduledEndDate;
    }

    /**
     * @return
     */
    @Override
    public Date getScheduledStartDate() {
        return scheduledStartDate;
    }

    /**
     * @return
     */
    @Override
    public String getScienceObjectivesMet() {
        return scienceObjectivesMet;
    }

    /**
     * @return
     */
    @Override
    public String getScientistComments() {
        return scientistComments;
    }

    /**
     * @return
     */
    @Override
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @return
     */
    @Override
    public String getShipName() {
        return shipName;
    }

    /**
     * @return
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @return
     */
    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }
}
