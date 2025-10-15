/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

import org.mbari.expd.model.VideoTime;

import java.util.Date;
import java.util.List;

/**
 *
 * @author brian
 */
public interface CameraDatumDAO {

    List<CameraDatum> fetchCameraData(Dive dive, Boolean isHD);

    VideoTime interpolateTimecodeToDate(String cameraIdentifier, Date date, int millisecTolerance, double frameRate);

    List<CameraDatum> fetchCameraData(Dive dive, List<Date> dates, double toleranceSec,  Boolean isHD);

    List<CameraDatum> findAllBetweenDates(String cameraIdentifier, Date startDate, Date endDate);
}
