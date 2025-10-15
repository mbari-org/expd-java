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
     * @param expeditionId The expedition ID.
     * @param accomplishments The accomplishments of the expedition.
     * @param chiefScientist The name of the chief scientist.
     * @param endDate The date the expedition ended.
     * @param equipmentDescription The equipment description.
     * @param operatorsComments The comments from the operators.
     * @param participants The participants in the expedition.
     * @param plannedTrackDescription The planned track description.
     * @param principalInvestigator The name of the principal investigator.
     * @param purpose The purpose of the expedition.
     * @param regionDescription The region description.
     * @param scheduledEndDate The date the expedition was scheduled to end.
     * @param scheduledStartDate The date the expedition was scheduled to start.
     * @param scienceObjectivesMet The science objectives met.
     * @param scientistComments The comments from the scientists.
     * @param sequenceNumber The sequence number of the expedition.
     * @param shipName The name of the ship.
     * @param startDate The date the expedition started.
     * @param updatedBy The name of the user who last updated this expedition.
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


    /**
     *
     * @return The expedition ID.
     */
    public Integer getExpeditionId() {
        return expeditionId;
    }

    /**
     * @return The accomplishments of the expedition.
     */
    @Override
    public String getAccomplishments() {
        return accomplishments;
    }

    /**
     * @return The name of the chief scientist.
     */
    @Override
    public String getChiefScientist() {
        return chiefScientist;
    }

    /**
     * @return The date the expedition ended.
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @return The equipment description.
     */
    @Override
    public String getEquipmentDescription() {
        return equipmentDescription;
    }

    /**
     * @return The comments from the operators.
     */
    @Override
    public String getOperatorsComments() {
        return operatorsComments;
    }

    /**
     * @return The participants in the expedition.
     */
    @Override
    public String getParticipants() {
        return participants;
    }

    /**
     * @return The planned track description.
     */
    @Override
    public String getPlannedTrackDescription() {
        return plannedTrackDescription;
    }

    /**
     * @return The name of the principal investigator.
     */
    @Override
    public String getPrincipalInvestigator() {
        return principalInvestigator;
    }

    /**
     * @return The purpose of the expedition.
     */
    @Override
    public String getPurpose() {
        return purpose;
    }

    /**
     * @return The region description.
     */
    @Override
    public String getRegionDescription() {
        return regionDescription;
    }

    /**
     * @return The date the expedition was scheduled to end.
     */
    @Override
    public Date getScheduledEndDate() {
        return scheduledEndDate;
    }

    /**
     * @return The date the expedition was scheduled to start.
     */
    @Override
    public Date getScheduledStartDate() {
        return scheduledStartDate;
    }

    /**
     * @return The science objectives met.
     */
    @Override
    public String getScienceObjectivesMet() {
        return scienceObjectivesMet;
    }

    /**
     * @return The comments from the scientists.
     */
    @Override
    public String getScientistComments() {
        return scientistComments;
    }

    /**
     * @return The sequence number of the expedition.
     */
    @Override
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @return The name of the ship.
     */
    @Override
    public String getShipName() {
        return shipName;
    }

    /**
     * @return The date the expedition started.
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @return The name of the user who last updated this expedition.
     */
    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }
}
