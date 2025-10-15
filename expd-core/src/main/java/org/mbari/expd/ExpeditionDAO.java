/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

import java.util.Collection;

/**
 *
 * @author brian
 */
public interface ExpeditionDAO {

    Collection<Expedition> findByDive(Dive dive);

    Collection<Expedition> findAll();

}
