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

public interface AnnotationSummary {

  Integer getDiveNumber();
  String getRovName();
  String getCamera();
  String getNotes();
  String getMission();
  String getApplication();
  String getAnnotators();
  Date getDateAnnotated();
  String getAnnotationFileName();
  Boolean isRealTimeEdited();
  String getStyle();
  Double getHoursAnnotated();
  Integer getStandardDefTapeCount();
  Integer getHighDefTapeCount();
  Integer getVideoSegmentCount();
  Double getHoursOfVideo();

}
