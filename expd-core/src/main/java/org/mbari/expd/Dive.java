/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

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
