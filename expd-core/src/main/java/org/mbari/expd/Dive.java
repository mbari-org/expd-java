/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

/*-
 * #%L
 * org.mbari.expd:expd-core
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

/**
 *
 * CREATE TABLE [dbo].[Dive]  (
    [DiveID]              	int NOT NULL,
    [DeviceID]            	int NULL,
    [RovName]             	varchar(4) NULL,
    [DiveNumber]          	int NOT NULL,
    [ExpeditionID_FK]     	int NULL,
    [DiveStartDtg]        	datetime NULL,
    [DiveEndDtg]          	datetime NULL,
    [DiveChiefScientist]  	varchar(50) NULL,
    [BriefAccomplishments]	varchar(5000) NULL,
    [DiveStartTimecode]   	char(11) NULL,
    [DiveEndTimecode]     	char(11) NULL,
    [DiveLatMid]          	decimal(12,8) NULL,
    [DiveLonMid]          	decimal(14,8) NULL,
    [DiveDepthMid]        	decimal(7,1) NULL,
    [rowguid]             	uniqueidentifier ROWGUIDCOL NULL CONSTRAINT [DF_Dive_rowguid]  DEFAULT (newid()),
    CONSTRAINT [aaaaaDive_PK] PRIMARY KEY([DiveID])

WITH FILLFACTOR = 90)
GO
 * @author brian
 */
public interface Dive {

    String getRovName();
    
    Integer getDiveNumber();
    
    Date getStartDate();
    
    Date getEndDate();
    
    String getChiefScientist();

    /**
     *
     * @return The nominal latitude for the dive
     */
    Double getLatitude();

    /**
     *
     * @return The nominal longitude for the dive
     */
    Double getLongitude();

    /**
     *
     * @return A description of the purpose of the dive
     */
    String getBriefAccomplishments();
    
}
