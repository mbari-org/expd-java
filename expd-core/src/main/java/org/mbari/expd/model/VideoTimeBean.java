package org.mbari.expd.model;

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
 * @author brian
 */
public class VideoTimeBean implements VideoTime {

    private Date date;
    private String timecode;

    public VideoTimeBean() { /* Default constructor */ }

    public VideoTimeBean(Date date, String timecode) {
        this.date = date;
        this.timecode = timecode;
    }

    public Date getDate() {
        return date;
    }

    public String getTimecode() {
        return timecode;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @param timecode Should be 'hh:mm:ss:ff' format. This class does not
     * check the format though
     */
    public void setTimecode(String timecode) {
        this.timecode = timecode;
    }

}
