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

/**
 *
 * @author brian
 */
public interface UberDatum {

    CameraDatum getCameraDatum();
    void setCameraDatum(CameraDatum cameraDatum);

    NavigationDatum getNavigationDatum();
    void setNavigationDatum(NavigationDatum navigationDatum);

    CtdDatum getCtdDatum();
    void setCtdDatum(CtdDatum ctdDatum);

}
