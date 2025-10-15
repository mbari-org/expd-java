/*
 * @(#)Expedition.java   2012.06.20 at 01:55:11 PDT
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



package org.mbari.expd;

import java.util.Date;

/**
 *
 *
CREATE TABLE [dbo].[Expedition]  (
    [ExpeditionID]              int NOT NULL,
    [DeviceID]                  int NULL,
    [ShipName]                  varchar(4) NULL,
    [ShipSeqNum]                int NULL,
    [Purpose]                   varchar(4096) NULL,
    [StatCode]                  varchar(30) NULL,
    [ExpdChiefScientist]        varchar(50) NULL,
    [ExpdPrincipalInvestigator] varchar(50) NULL,
    [ScheduledStartDtg]         datetime NULL,
    [ScheduledEndDtg]           datetime NULL,
    [EquipmentDesc]             varchar(2048) NULL,
    [Participants]              varchar(2048) NULL,
    [RegionDesc]                varchar(2048) NULL,
    [PlannedTrackDesc]          varchar(6144) NULL,
    [StartDtg]                  datetime NULL,
    [EndDtg]                    datetime NULL,
    [Accomplishments]           varchar(6144) NULL,
    [ScientistComments]         varchar(2048) NULL,
    [SciObjectivesMet]          varchar(50) NULL,
    [OperatorComments]          varchar(2048) NULL,
    [AllEquipmentFunctioned]    varchar(50) NULL,
    [OtherComments]             varchar(2048) NULL,
    [UpdatedBy]                 varchar(50) NULL,
    [ismodified]                int NULL CONSTRAINT [DF_Expedition_modified_1]  DEFAULT (0),
    CONSTRAINT [PK_Expedition_1] PRIMARY KEY([ExpeditionID])

WITH FILLFACTOR = 90)
GO
 * @author brian
 */
public interface Expedition {

    String getAccomplishments();

    String getChiefScientist();

    Date getEndDate();

    String getEquipmentDescription();

    String getOperatorsComments();

    String getParticipants();

    String getPlannedTrackDescription();

    String getPrincipalInvestigator();

    String getPurpose();

    String getRegionDescription();

    Date getScheduledEndDate();

    Date getScheduledStartDate();

    String getScienceObjectivesMet();

    String getScientistComments();

    Integer getSequenceNumber();

    String getShipName();

    Date getStartDate();

    String getUpdatedBy();
}
