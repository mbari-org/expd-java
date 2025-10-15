/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.actions;

import java.util.Collection;
import java.util.Map;
import org.mbari.expd.UberDatum;

/**
 * For coallating some object by nearest neighbor to an {@link UberDatum}
 * @author brian
 * @param <T>
 */
public interface CollateFunction<T> {

     Map<T, UberDatum> apply(Collection<T> keyList, Collection<UberDatum> uberDataList, long offsetMillisec);

}
