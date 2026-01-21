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
